create table if not exists restaurant_menu(
    id serial primary key ,
    id_menu int not null references menu(id),
    id_restaurant int not null references restaurant(id)
);