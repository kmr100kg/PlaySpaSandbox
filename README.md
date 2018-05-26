# Playframework2.6 SPA Sandbox

Playframework2.6, RiotJS, SemanticUIを使ったサンプルアプリです。

今のところ以下の機能を実装しています。

* 簡単なCRUD
    * バリデーションあり
    * メッセージを指定した順序通りに出力
    * 更新日時を見る簡単な楽観ロック
    * ajax通信時のCSRFチェック

* ロギング
    * アプリケーションログ(ローテーションあり)
    * アクセスログ(ローテーションあり)

## Running

MySQL5.6サーバとsbtのインストールが必須です。

* MySQL5.6
    * https://dev.mysql.com/downloads/file/?id=478031

* sbt
    * https://www.scala-sbt.org/1.0/docs/ja/Installing-sbt-on-Windows.html


MySQLの準備ができたら、プロジェクトルート配下にある「sample.sql」を実行してください。

次にコマンドプロンプトでプロジェクトルートまで移動後、下記コマンドを実行してください。

```
sbt run
```

コンソールに以下のメッセージが出力されていれば準備完了です。

```
(Server started, use Enter to stop and go back to the console...)
```

ブラウザでlocalhost:9000にアクセス！

トップページ(メニュー)が表示されれば完了です。

※ たぶん初回起動にはすごく時間がかかります
