--luquibase formatted sql

--changeset shaplov:1
create table payment (
  id serial primary key,
  value numeric
);