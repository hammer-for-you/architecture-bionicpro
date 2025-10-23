create table if not exists customers(
    id serial primary key,
    name varchar,
    email varchar,
    phone varchar,
    address varchar
);

copy customers(id, name, email, phone, address) from '/docker-entrypoint-initdb.d/customers.csv' delimiter ',' csv header;

create table if not exists devices(
    id uuid primary key,
    name varchar,
    user_id bigint references customers(id)
);

copy devices(id, name, user_id) from '/docker-entrypoint-initdb.d/devices.csv' delimiter ',' csv header;