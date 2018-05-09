# Playframework2.6 SPA Sample

Playframework2.6, RiotJS, SemanticUIを使ったサンプルアプリです。

## Running

MySQLサーバのインストールが必須です。
https://dev.mysql.com/downloads/file/?id=478031

MySQLの準備ができたら、以下のSQLを実行します。

sample.sql
```
create database playsample;

alter database playsample character set utf8;

use playsample;

create table employee(
  employee_number int primary key,
  name varchar(255) not null,
  kana varchar(255) not null,
  mail_address varchar(255) not null,
  password varchar(255) not null
);
```

次にコマンドプロンプトを開いてプロジェクトルートまで移動後、下記コマンドを実行します。

```
sbt run
```

ブラウザでlocalhost:9000にアクセス！トップページが表示されれば完了です。
