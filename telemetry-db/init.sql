create table if not exists telemetry(
    id serial primary key,
    user_email varchar,
    device_id uuid,
    metric_value varchar,
    metric_unit varchar,
    metric_timestamp timestamp
);

copy telemetry(user_email, device_id, metric_value, metric_unit, metric_timestamp)
    from '/docker-entrypoint-initdb.d/telemetry.csv' delimiter ',' csv header;