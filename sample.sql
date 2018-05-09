create database playsample;

use playsample;

create table employee(
  employee_number int primary key,
  name varchar(255) not null,
  kana varchar(255) not null,
  mail_address varchar(255) not null,
  password varchar(255) not null
);
