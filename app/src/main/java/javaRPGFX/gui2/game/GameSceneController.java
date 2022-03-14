package javaRPGFX.gui2.game;

import java.net.URL;
import java.util.ResourceBundle;

import javaRPGFX.animation.Animation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import javafx.geometry.Side;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundImage;
//import javafx.scene.layout.BackgroundPosition;
//import javafx.scene.layout.BackgroundRepeat;
//import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GameSceneController implements Initializable{

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Rectangle upperRectangle;
    @FXML
    private Pane gameScene1Pane;
    @FXML
    private Rectangle lowerRectangle;
    @FXML
    private Rectangle textBoxRect;
    @FXML
    private Text gameEventTxt;

    private Canvas canvas = new Canvas(600,400);
    private Animation animation = new Animation();
    private boolean isVisible;
    private ImageView enemy1ImageView = new ImageView();
    private ImageView enemy2ImageView = new ImageView();
    private Image img;

    public GameSceneController(){}

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //アニメーション用の透明なキャンバスを追加(自動でtoFront()されている)
        gameScene1Pane.getChildren().add(canvas);
        this.isVisible = true;
    }
    public void setTextBoxVisualize(boolean v){
        isVisible = v;
        textBoxRect.setVisible(v);
        gameEventTxt.setVisible(v);
    }
    public void playHealAnm(){
        animation.healAnm(canvas).play();
    }
    //テキストボックスが表示されているか否か
    public boolean getTxtBIsVisible(){
        return this.isVisible;
    }
    //イベント時のメッセージ出力はこのメソッドで行う
    //Too buggy
    public void playTextAnimation(String txt){
        gameEventTxt.setText(null);
        animation.handleTextAnimation(txt, gameEventTxt).play();
    }
    //代替
    public void setText(String txt){
        gameEventTxt.setText(txt);
    }
    public void putEnemyImg(int enemyNum,int kind){
        switch(kind){
            case 1: img = new Image("/asset/enemy1.png"); break;
            case 2: img = new Image("/asset/enemy2.png"); break;
            default: break;
        }
        if(enemyNum>=2){
            enemy1ImageView.setImage(img);
            enemy2ImageView.setImage(img);
            enemy1ImageView.setPreserveRatio(true);
            enemy2ImageView.setPreserveRatio(true);
            enemy1ImageView.setFitHeight(130);
            enemy2ImageView.setFitHeight(130);
            double width = enemy1ImageView.boundsInLocalProperty().get().getWidth();
            enemy1ImageView.setX(600/2 - (width/2)*enemyNum -width/2);
            enemy2ImageView.setX(600/2 + (width/2)*enemyNum -width/2);
            enemy1ImageView.setY(400/2-enemy1ImageView.getFitHeight()/2);
            enemy2ImageView.setY(400/2-enemy1ImageView.getFitHeight()/2);
            gameScene1Pane.getChildren().addAll(enemy1ImageView,enemy2ImageView);
        }
        else{
            enemy1ImageView.setImage(img);
            enemy1ImageView.setPreserveRatio(true);
            enemy1ImageView.setFitHeight(130);
            double width = enemy1ImageView.boundsInLocalProperty().get().getWidth();
            enemy1ImageView.setX(600/2-width/2);
            enemy1ImageView.setY(400/2-enemy1ImageView.getFitHeight()/2);
            gameScene1Pane.getChildren().add(enemy1ImageView);
        }
    }
    public void enemyDead(int enemyNum){
        switch(enemyNum){
            case 1: animation.enemyDeadAnm(enemy1ImageView); break;
            case 2: animation.enemyDeadAnm(enemy2ImageView); break;
            default: break;
        }
    }
    public void enemyDamaged(int enemyNum){
        switch(enemyNum){
            case 1: animation.enemyDamagedAnm(enemy1ImageView); break;
            case 2: animation.enemyDamagedAnm(enemy2ImageView); break;
            default: break;
        }
    }
    public void playWinAnimation(){
        var anim1 = animation.moveShapeToLower(lowerRectangle);
        var anim2 = animation.moveShapeToUpper(upperRectangle);
        anim1.setDelay(Duration.millis(350));
        anim2.setDelay(Duration.millis(350));
        anim1.play();
        anim2.play();
    }
}