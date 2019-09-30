create table person (
  id serial primary key,
  login varchar(2000),
  password varchar(2000)
);

insert into person (login, password) values
('parsentev', '123'),
('ban', '123'),
('ivan', '123');
