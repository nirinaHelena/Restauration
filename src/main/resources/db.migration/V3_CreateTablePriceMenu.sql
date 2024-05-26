create table if not exists price_menu(
    id serial primary key ,
    date timestamp without time zone default now(),
    price double precision not null,
    id_menu int references menu(id)
);