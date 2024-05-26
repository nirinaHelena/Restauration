create table if not exists stock(
    id serial primary key ,
    quantity double precision not null ,
    id_restaurant int not null references restaurant(id),
    id_ingredient_template int not null references ingreditent_template(id),
    date timestamp without time zone default now()
);