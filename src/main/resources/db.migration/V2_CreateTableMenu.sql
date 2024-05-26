create table if not exists menu(
    id serial primary key,
    name varchar not null unique
);