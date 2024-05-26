create type movement_type as ENUM(
    'SUPPLY',
    'SALE'
    );
create table if not exists movement(
    id serial primary key ,
    date timestamp without time zone default now(),
    id_ingredient_template int references ingreditent_template(id),
    movement_type movement_type not null ,
    quantity double precision not null
);