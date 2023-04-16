CREATE TABLE persons
(
id serial primary key,
    username     varchar   not null unique ,
    surname     varchar        not null,
    password varchar        not null
);