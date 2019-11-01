insert into roles(name)
  select 'ROLE_EMPLOYEE'
where not exists (
  select 1 from roles where name = 'ROLE_EMPLOYEE'
  );