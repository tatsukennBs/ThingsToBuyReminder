# リポジトリ名
買い物リストアプリケーション
# 概要
ついつい買い忘れてしまう日用品の次回購入が必要な日を一覧で表示してくれます。</br>
管理したい品目を登録し、購入したら購入ボタンを押すことで購入日を登録できます。

# デモ
![demo](https://github.com/tatsukennBs/ThingsToBuyReminder/assets/70462631/a7546e5d-af91-4ff2-9969-8bb1e48e52d4)

# 機能一覧
- 一覧表示
	- 品目名、カテゴリ、最終購入日、在庫切れ予測日を表示
	- 購入日は履歴管理されており、最新の購入日を表示
	- 在庫切れ予測日は過去登録された購入日より算出した平均購入間隔をもとに日付を表示

- 品目登録
	- 一覧画面の右上に表示されている「品目登録」から新規品目の登録が可能
	- 品目名、カテゴリは必須入力、最終購入日は登録時点では任意入力

- リスト編集
	- 各品目行の「編集」リンクから品目名、カテゴリ、最終購入日の編集が可能

- リスト削除
	- 各品目行の「削除」リンクから品目名、カテゴリ、最終購入日の編集が可能
# URL
- http://www.thingstobuy.org:8080/ThingsToBuy

# 使用技術
- Java SE 17
- SpringBoot 3.0.6
- MySQL 8.0.31
- thymeleaf 3.1.1
- bootstrap 5.3.0
- AWS
	- VPC
	- EC2
	- RDS
   	- Route53
- ビルドツール:Gradle
- Webサーバ：Apache
- サーブレットコンテナ：Tomcat

# AWS構成
![買い物リストAWS構成](https://github.com/tatsukennBs/ThingsToBuyReminder/assets/70462631/34a3d003-aaa8-457f-9e88-751afb183bc5)

# 使用ツール、ライブラリ
- draw.io<br/>  画面遷移図作成用に使用
- moment.js<br/>  日付計算用にライブラリ使用
