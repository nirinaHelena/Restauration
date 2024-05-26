create table if not exists ingreditent_template(
    id serial primary key ,
    name varchar not null unique ,
    price double precision not null
);