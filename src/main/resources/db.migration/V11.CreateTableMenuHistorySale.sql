create table if not exists menu_history_sale(
    id serial primary key ,
    date timestamp without time zone default now(),
    id_menu int references menu(id),
    id_restaurant int references restaurant(id)
);