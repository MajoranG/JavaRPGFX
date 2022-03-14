package javaRPGFX.gui2.game;

import java.net.URL;
import java.util.ResourceBundle;

import javaRPGFX.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

public class GameBackController implements Initializable{

    @FXML
    private Pane backgroundPane;

    private Pane layer1;
    private Pane layer2;
    private Animation animation = new Animation();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        layer1 = new Pane();
        layer2 = new Pane();
        layer1.setPrefHeight(400);
        layer2.setPrefHeight(400);
        layer1.setPrefWidth(600);
        layer2.setPrefWidth(600);
    }

    //背景のタイルシートをセットする
    public void settingBackground(int enemyKind){
        //レイヤー追加
        backgroundPane.getChildren().addAll(layer1,layer2);
        //左上詰めで配置する
        BackgroundPosition backgroundPosition = new BackgroundPosition(
	        Side.LEFT, 0, false, Side.TOP, 0, false);
        backgroundPane.toBack();

        switch(enemyKind){
            case 1:
            //Paneをリサイズする
            layer1.setPrefWidth(600+256);
            layer1.setPrefHeight(400+256);
            layer2.setPrefWidth(600+256);
            layer2.setPrefHeight(400+256);

            Image image = new Image("/asset/back3.png");
            Image adImage = new Image("/asset/back2.png");
            
            BackgroundImage backgroundImage = new BackgroundImage(
                image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, backgroundPosition, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            layer1.setBackground(background);

            BackgroundImage adBackgroundImage = new BackgroundImage(
                adImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, backgroundPosition, BackgroundSize.DEFAULT);
            Background adBackground = new Background(adBackgroundImage);
            layer2.setBackground(adBackground);

            layer1.toFront();
            animation.blinkingOpacity(layer1).play();
            animation.backgroundPattern2(layer2).play();
            animation.backgroundPattern2(layer1).play();
            break;

            case 2:
            //Image image2 = new Image("/asset/back.png",600,400,false,true);
            Image image2 = new Image("/asset/back.gif");
            BackgroundImage backgroundImage2 = new BackgroundImage(
                image2, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, backgroundPosition, BackgroundSize.DEFAULT);
            Background background2 = new Background(backgroundImage2);
            layer1.setBackground(background2);
            //背景アニメーションを再生
            animation.backgroundPattern1(layer1).play();
            break;
            default:
            break;
        }
    }
}