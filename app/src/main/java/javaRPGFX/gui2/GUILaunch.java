package javaRPGFX.gui2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

import java.io.IOException;
//import java.lang.reflect.Method;
import java.util.Map;
import java.util.HashMap;

import javaRPGFX.gui2.model.View;
import javaRPGFX.gui2.util.GameViewEvent;
import javaRPGFX.gui2.game.GameMainController;

public class GUILaunch extends Application{

    //for set event
    private static GUILaunch singleton;
    //private Method eventMethod;

    public GUILaunch(){
        singleton = this;
    }

    public static GUILaunch getSingleton(){
        return singleton;
    }

    private Stage stage;
    private Scene scene;

    private static Map<View, Scene> scenesMap = new HashMap<>();

    @Override public void start(Stage primaryStage){
        Image icon = new Image("/asset/pointerTitle.png");
        stage = primaryStage;
        stage.setTitle("JavaRPGFX");
        stage.getIcons().add(icon);
        stage.setResizable(false);
        add(View.TITLE);
        stage.setScene(scenesMap.get(View.TITLE));
        stage.show();
    }

    public void add(View view){
        var loader = new FXMLLoader(getClass().getResource(view.fxmlLocation));

        try {
            Parent root = loader.load();
            GameViewEvent controller = loader.getController();
            /* eventMethod = controller.getClass().getMethod("keyPressed",KeyEvent.class); 
            System.out.println(eventMethod); */

            scene = new Scene(root);
            //キーイベントを登録する
            scene.setOnKeyPressed(event -> controller.keyPressed(event));
            //scene.setOnKeyPressed(event -> controller.eventMethod.invoke(/* ?? */,event));
            //HashMapに入れる
            scenesMap.put(view,scene);
        } catch (IOException e){
            e.printStackTrace();
            Platform.exit();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void addAndSendToGame(View view,String value,int kind) {
        var loader = new FXMLLoader(getClass().getResource(view.fxmlLocation));

        try {
            Parent root = loader.load();
            GameViewEvent controller = loader.getController();

            /* eventMethod = controller.getClass().getMethod("keyPressed",KeyEvent.class); 
            System.out.println(eventMethod); */

            //シーン間でデータの受け渡しをするときにここを変える
            ((GameMainController) controller).displayName(value);
            ((GameMainController) controller).setGameScene(kind);
            scene = new Scene(root);
            //キーイベントを登録する
            scene.setOnKeyPressed(event -> controller.keyPressed(event));
            //scene.setOnKeyPressed(event -> controller.eventMethod.invoke(/* ?? */,event));
            //HashMapに入れる
            scenesMap.put(view,scene);
        } catch (IOException e){
            e.printStackTrace();
            Platform.exit();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void remove(View view){
        scenesMap.remove(view);
    }
    //ステージにシーンをセットする
    public void activate(View view,Stage previousStage){
        previousStage.setScene(scenesMap.get(view));
    }
    //シーンをセットしなおす
    public void reActivate(View view,Stage previousStage){
        scenesMap.clear();
        add(view);
        activate(view,previousStage);
    }
    public Scene getSceneFromLaunch(){
        return this.scene;
    }
    public static void main(String[] args) throws Exception{
        launch(args);
    }
}