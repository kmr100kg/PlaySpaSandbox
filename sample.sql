drop database playsample;

create database playsample;

use playsample;

create table employee(
  employee_number int primary key,
  name varchar(255) not null,
  kana varchar(255) not null,
  mail_address varchar(255) not null,
  update_date timestamp not null
);

create table password(
  employee_number int primary key,
  password varchar(255) not null,
  update_date timestamp not null,
  foreign key (employee_number) references employee(employee_number)
);

create table employee_detail(
  employee_number int primary key,
  age int not null,
  gender varchar(255) not null,
  zip varchar(255) not null,
  address varchar(255) not null,
  foreign key (employee_number) references employee(employee_number)
);

create table department(
  code varchar(255) primary key,
  name varchar(255) not null
);

insert into employee values(1, 'レベッカ宮本', 'レベッカミヤモト', 'rebecca_miyamoto@mail.co.jp', now());
insert into employee values(2, '橘玲', 'タチバナレイ', 'tachibana_rei@mail.co.jp', now());
insert into employee values(3, '片桐姫子', 'カタギリヒメコ', 'katagiri_himeko@mail.co.jp', now());
insert into employee values(4, '桃瀬くるみ', 'モモセクルミ', 'momose_kurumi@mail.co.jp', now());
insert into employee values(5, '上原都', 'ウエハラミヤコ', 'uehara_miyako@mail.co.jp', now());
insert into employee values(6, '一条', 'イチジョウ', 'itijou@mail.co.jp', now());
insert into employee values(7, '鈴木さやか', '鈴木さやか', 'suzuki_sayaka@mail.co.jp', now());
insert into employee values(8, '柏木優麻', 'カシワギユウマ', 'kashiwagi_yuuma@mail.co.jp', now());
insert into employee values(9, '芹沢茜', 'セリザワアカネ', 'serizawa_akane@mail.co.jp', now());
insert into employee values(10, 'メディア', 'メディア', 'media@mail.co.jp', now());

insert into password values(1, 'password', now());
insert into password values(2, 'password', now());
insert into password values(3, 'password', now());
insert into password values(4, 'password', now());
insert into password values(5, 'password', now());
insert into password values(6, 'password', now());
insert into password values(7, 'password', now());
insert into password values(8, 'password', now());
insert into password values(9, 'password', now());
insert into password values(10, 'password', now());

insert into employee_detail values (1, 8, '女性', '105-0011', '東京都港区芝公園４丁目２−８');
insert into employee_detail values (2, 17, '女性', '105-0011', '東京都港区芝公園４丁目２−８');
insert into employee_detail values (3, 17, '女性', '105-0011', '東京都港区芝公園４丁目２−８');
insert into employee_detail values (4, 17, '女性', '105-0011', '東京都港区芝公園４丁目２−８');
insert into employee_detail values (5, 17, '女性', '105-0011', '東京都港区芝公園４丁目２−８');
insert into employee_detail values (6, 17, '女性', '105-0011', '東京都港区芝公園４丁目２−８');
insert into employee_detail values (7, 17, '女性', '105-0011', '東京都港区芝公園４丁目２−８');
insert into employee_detail values (8, 17, '女性', '105-0011', '東京都港区芝公園４丁目２−８');
insert into employee_detail values (9, 17, '女性', '105-0011', '東京都港区芝公園４丁目２−８');
insert into employee_detail values (10, 17, '女性', '105-0011', '東京都港区芝公園４丁目２−８');