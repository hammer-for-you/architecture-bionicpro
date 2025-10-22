from datetime import datetime, timedelta
from airflow import DAG
from airflow.providers.postgres.hooks.postgres import PostgresHook
from airflow_clickhouse_plugin.hooks.clickhouse import ClickHouseHook
from airflow.operators.python import PythonOperator
import pandas as pd
import logging

default_args = {
    'owner': 'airflow',
    'depends_on_past': False,
    'start_date': datetime(2024, 1, 1),
    'email_on_failure': False,
    'email_on_retry': False,
    'retries': 1,
    'retry_delay': timedelta(seconds=30)
}

def extract_customer_data():
    postgres_hook = PostgresHook(postgres_conn_id='crm_db')

    query = """
            SELECT c.id      as user_id,
                   c.name    as user_name,
                   c.email   as user_email,
                   c.phone   as user_phone,
                   c.address as user_address,
                   d.id      as device_id,
                   d.name    as device_name
            FROM customers c
                     JOIN devices d ON c.id = d.user_id \
            """

    df = postgres_hook.get_pandas_df(query)
    logging.info(f"Извлечено {len(df)} пользователей")
    return df


def extract_telemetry():
    clickhouse_hook = ClickHouseHook(clickhouse_conn_id='telemetry_db')

    query = """
            SELECT user_email,
                   device_id,
                   metric_value,
                   metric_unit,
                   metric_timestamp
            FROM telemetry \
            """

    records = clickhouse_hook.execute(query)
    logging.info(f"Извлечено {len(records)} записей телеметрии")

    if records:
        columns = ['user_email', 'device_id', 'metric_value', 'metric_unit', 'metric_timestamp']
        df = pd.DataFrame(records, columns=columns)
        # Конверсия в строку для сериализации
        df['device_id'] = df['device_id'].astype(str)
        df['metric_timestamp'] = df['metric_timestamp'].astype(str)
        return df.to_dict('records')
    else:
        return []


def transform_and_join_data(**kwargs):
    ti = kwargs['ti']

    customer_data = ti.xcom_pull(task_ids='extract_customer_data')
    telemetry_data = ti.xcom_pull(task_ids='extract_telemetry')

    df_customers = pd.DataFrame(customer_data)
    df_telemetry = pd.DataFrame(telemetry_data)

    merged_df = pd.merge(
        df_telemetry,
        df_customers,
        on=['user_email', 'device_id'],
        how='inner'
    )

    logging.info(f"После объединения: {len(merged_df)} записей")
    logging.info(f"Колонки после объединения: {list(merged_df.columns)}")

    result_data = merged_df.to_dict('records')
    return result_data

def load_to_report(**kwargs):
    ti = kwargs['ti']
    transformed_data = ti.xcom_pull(task_ids='transform_and_join_data')

    if not transformed_data:
        logging.warning("Нет данных для загрузки")
        return

    clickhouse_hook = ClickHouseHook(clickhouse_conn_id='olap_db')

    insert_query = """
                   INSERT INTO report (user_name,
                                       user_email,
                                       user_phone,
                                       user_address,
                                       device_id,
                                       device_name,
                                       metric_value,
                                       metric_unit,
                                       metric_timestamp)
                   VALUES \
                   """

    values = []
    for record in transformed_data:
        value = (
            record['user_name'].replace("'", "''"),
            record['user_email'].replace("'", "''"),
            record['user_phone'].replace("'", "''"),
            record['user_address'].replace("'", "''"),
            record['device_id'],
            record['device_name'].replace("'", "''"),
            record['metric_value'].replace("'", "''"),
            record['metric_unit'].replace("'", "''"),
            datetime.strptime(record['metric_timestamp'], '%Y-%m-%d %H:%M:%S')
        )
        values.append(value)

    try:
        clickhouse_hook.execute(insert_query, values)
        logging.info(f"Успешно загружено {len(transformed_data)} записей в таблицу report")
    except Exception as e:
        logging.error(f"Ошибка при загрузке данных отчёта: {str(e)}")
        raise


with DAG(
        'bionicpro-report-dag',
        default_args=default_args,
        description='BionicPRO ETL: генерация отчётов по извлечённым данным пользователей и телеметрии',
        schedule_interval=timedelta(minutes=5),
        catchup=False,
        tags=['etl', 'postgres', 'clickhouse', 'reports']
) as dag:
    extract_extract_task = PythonOperator(
        task_id='extract_customer_data',
        python_callable=extract_customer_data,
        provide_context=True,
    )

    extract_telemetry_task = PythonOperator(
        task_id='extract_telemetry',
        python_callable=extract_telemetry,
        provide_context=True,
    )

    transform_join_task = PythonOperator(
        task_id='transform_and_join_data',
        python_callable=transform_and_join_data,
        provide_context=True,
    )

    load_report_task = PythonOperator(
        task_id='load_to_report',
        python_callable=load_to_report,
        provide_context=True,
    )

    [extract_extract_task, extract_telemetry_task] >> transform_join_task >> load_report_task
