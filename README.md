# Apolloライブラリを使ったサンプルアプリ
## Architecture
<img src="https://user-images.githubusercontent.com/62511320/146396561-befbc913-7909-4500-beb0-1a210c73b9cb.png" width=80%>

[自作テンプレート](https://github.com/kuwabara-hirokazu/android_architecture_base_flow)を基盤に作成
## 使用ライブラリ
* Dagger Hilt
* Apollo
* OkHttp3
* Coroutines
* Navigation
* Glide
* Timber
* mockito

## API機能
### Home
* アクセストークンからGithubアカウントを特定
* Githubアカウントに紐づくRepositoryおよびissueを抽出

### WebView
ボタン押下時にissueページに絵文字リアクションする

## 画面
|Home|WebView|
|----|----|
|<img src="https://user-images.githubusercontent.com/62511320/146391947-8b4e94f1-b48a-4a5a-a003-17582527a470.jpg" width=300dp>|<img src="https://user-images.githubusercontent.com/62511320/146391951-05931043-4dc1-46e5-b20b-64f2b5866c22.jpg" width=300dp>|

## Build
### GithubApi認証Key
gradle.propertiesに`githubKey=<アクセストークン>`を設置する
### schema.json 作成コマンド
`apollo-codegen download-schema https://api.github.com/graphql --output schema.json --header "Authorization: Bearer <アクセストークン>"`

## リファレンス
https://docs.github.com/ja/graphql
