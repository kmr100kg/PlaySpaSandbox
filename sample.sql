create database playsample;

use playsample;

create table employee(
  employee_number int primary key,
  name varchar(255) not null,
  kana varchar(255) not null,
  mail_address varchar(255) not null,
  password varchar(255) not null,
  update_date timestamp not null
);

insert into employee values(1, 'レベッカ宮本', 'レベッカミヤモト', 'rebecca_miyamoto@mail.co.jp', 'test1234', now());
insert into employee values(2, '橘玲', 'タチバナレイ', 'tachibana_rei@mail.co.jp', 'test1234', now());
insert into employee values(3, '片桐姫子', 'カタギリヒメコ', 'katagiri_himeko@mail.co.jp', 'test1234', now());
insert into employee values(4, '桃瀬くるみ', 'モモセクルミ', 'momose_kurumi@mail.co.jp', 'test1234', now());
insert into employee values(5, '上原都', 'ウエハラミヤコ', 'uehara_miyako@mail.co.jp', 'test1234', now());
insert into employee values(6, '一条', 'イチジョウ', 'itijou@mail.co.jp', 'test1234', now());
insert into employee values(7, '鈴木さやか', '鈴木さやか', 'suzuki_sayaka@mail.co.jp', 'test1234', now());
insert into employee values(8, '柏木優麻', 'カシワギユウマ', 'kashiwagi_yuuma@mail.co.jp', 'test1234', now());
insert into employee values(9, '芹沢茜', 'セリザワアカネ', 'serizawa_akane@mail.co.jp', 'test1234', now());
insert into employee values(10, 'メディア', 'メディア', 'media@mail.co.jp', 'test1234', now());