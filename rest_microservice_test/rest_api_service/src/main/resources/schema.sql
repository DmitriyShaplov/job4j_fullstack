create table if not exists passports (
  id serial primary key,
  number char(4) not null,
  series char(6) not null,
  name varchar(255) not null,
  surname varchar(255) not null,
  patronymic varchar(255) not null,
  sex char(1) not null,
  birthday date not null,
  date_of_issue date not null,
  issued varchar(2000) not null,
  code char(6) not null,
  place_of_birth varchar(2000) not null,
  unique (number, series)
);

create table if not exists requests (
  id serial primary key,
  service_name varchar(255) not null,
  status varchar(255) not null,
  created timestamp not null,
  passport_id int references passports(id) not null
);

create table if not exists documents (
  id serial primary key,
  name varchar(255),
  data bytea not null,
  mime_type varchar(255),
  created timestamp,
  request_id int references requests(id)
);

create table if not exists events (
  id serial primary key,
  commited timestamp not null,
  event varchar(255) not null,
  request_id int references requests(id) not null
);
