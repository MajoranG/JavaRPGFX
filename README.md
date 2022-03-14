# JavaRPGFX
JavaFXで某RPG風の戦闘システムを作ろうとしたものです。

- JavaFXのFXMLを利用したカスタムコントロール方式です。
- ゲームループ(JavaFXではAnimationTimerなど)を実装していません。イベント駆動型です。
- マルチレイヤーレンダリングは透明なPaneとCanvasで実現しています。
- アニメーションにはTimelineを使用しています。

## How to run this
### 1. Gradle、Java導入済みの場合
`git clone https://github.com/MajoranG/JavaRPGFX`  
`gradle run`  
または  
`gradle build`  
`Java -jar app/build/libs/app-all.jar`  

### 1. Javaを導入済みの場合(Windows)
gradlew.bat を実行  
`Java -jar app/build/libs/app-all.jar`  

工事中

## Credits
BGM(作曲) : [@devimoll](https://github.com/devimoll)