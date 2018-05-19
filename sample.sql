create database playsample;

alter database playsample character set utf8;

use playsample;

create table employee(
  employee_number int primary key,
  name varchar(255) not null,
  kana varchar(255) not null,
  mail_address varchar(255) not null,
  password varchar(255) not null,
  update_date timestamp not null
);

insert into employee values(1, 'サンプル一郎', 'サンプルイチロウ', 'sample1@mail.co.jp', 'test1234', now());
insert into employee values(2, 'サンプル二郎', 'サンプルジロウ', 'sample2@mail.co.jp', 'test1234', now());
insert into employee values(3, 'サンプル三郎', 'サンプルサブロウ', 'sample3@mail.co.jp', 'test1234', now());
insert into employee values(4, 'サンプル四郎', 'サンプルシロウ', 'sample4@mail.co.jp', 'test1234', now());
insert into employee values(5, 'サンプル五郎', 'サンプルゴロウ', 'sample5@mail.co.jp', 'test1234', now());
insert into employee values(6, 'サンプル六郎', 'サンプルロクロウ', 'sample6@mail.co.jp', 'test1234', now());
insert into employee values(7, 'サンプル七郎', 'サンプルナナロウ', 'sample7@mail.co.jp', 'test1234', now());
insert into employee values(8, 'サンプル八郎', 'サンプルハチロウ', 'sample8@mail.co.jp', 'test1234', now());
insert into employee values(9, 'サンプル九郎', 'サンプルクロウ', 'sample9@mail.co.jp', 'test1234', now());
insert into employee values(10, 'サンプル十郎', 'サンプルジュウロウ', 'sample10@mail.co.jp', 'test1234', now());