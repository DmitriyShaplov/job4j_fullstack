create table if not exists users (
    id serial primary key,
    username varchar(2000) unique not null,
    password varchar(2000) not null
);

create table if not exists roles (
   id serial primary key,
   name varchar(200) unique not null
);

create table if not exists users_roles (
    user_id int references users (id),
    role_id int references roles (id),
    constraint users_roles_pkey primary key (user_id, role_id)
);