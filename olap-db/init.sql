use bionicpro;

create table if not exists report(
    id UUID default generateUUIDv4(),
    user_name String,
    user_email String,
    user_phone String,
    user_address String,
    device_id UUID,
    device_name String,
    metric_value String,
    metric_unit String,
    metric_timestamp DateTime
)
    ENGINE = MergeTree()
    ORDER BY  (user_email, device_id, metric_timestamp);
