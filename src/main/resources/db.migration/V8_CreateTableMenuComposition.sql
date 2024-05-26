create table if not exists menu_composition(
    id serial primary key ,
    id_menu int not null references menu(id),
    id_ingredient int not null references ingredient(id)
);