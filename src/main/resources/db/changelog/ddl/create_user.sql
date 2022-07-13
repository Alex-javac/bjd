-- --------------------------------------------------------
--                     users
-- --------------------------------------------------------
create table if not exists users(id bigint auto_increment not null primary key);
alter table users add column firstname varchar(55) not null;
alter table users add column lastname varchar(55);
alter table users add column email varchar(255) not null unique;
alter table users add column phone_number varchar(55);
alter table users add column password varchar(255);
alter table users add column created_at timestamp not null default now();