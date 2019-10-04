alter table person alter column login set not null;
alter table person add constraint login_key unique (login);
alter table person alter column password set not null;

create table roles (
                     id serial primary key,
                     name varchar(200)
);

create table person_roles (
                            person_id int references person (id),
                            roles_id int references roles (id),
                            constraint person_roles_pkey primary key (person_id, roles_id)
);

delete from person;

insert into roles (name) values
('ROLE_ADMIN'), ('ROLE_USER')

insert into person(login, password) values
  ('admin', '$2a$10$cCmtzF.xPbMk8jsJ5dHoK.tJFuBn3icJeDFV.ZW5N1s9iCYjqJr1O');

insert into person_roles(person_id, roles_id) VALUES
(8, 1),
(8, 2);

create table room (
                    id serial primary key,
                    name varchar(200) unique not null
);

create table room_person (
  room_id int references room (id),
  person_id int references person (id),
  constraint room_person_pkey primary key (room_id, person_id)
);

create table message (
  id serial primary key,
  content text,
  created timestamp,
  room_id int references room (id),
  person_id int references person (id)
);