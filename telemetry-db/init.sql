create table if not exists telemetry(
    id UUID default generateUUIDv4(),
    user_email String,
    device_id UUID,
    metric_value String,
    metric_unit String,
    metric_timestamp DateTime
)
    ENGINE = MergeTree()
    ORDER BY  (user_email, device_id, metric_timestamp);

insert into telemetry(user_email, device_id, metric_value, metric_unit, metric_timestamp)
    from infile '/docker-entrypoint-initdb.d/telemetry.csv'
    format CSVWithNames;
