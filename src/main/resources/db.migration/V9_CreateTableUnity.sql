create table if not exists unity(
    id serial primary key ,
    name varchar not null unique
);
alter table ingreditent_template add column id_unity int  references ingreditent_template(id);