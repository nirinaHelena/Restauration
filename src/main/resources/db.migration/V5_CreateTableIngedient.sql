create table if not exists ingredient(
    id serial primary key ,
    quantity_required double precision not null ,
    id_ingredient_template int not null references ingreditent_template(id),
    id_menu int not null references menu(id)
);