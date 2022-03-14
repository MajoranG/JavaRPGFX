package javaRPGFX.gui2.game;

import java.net.URL;
import java.util.ResourceBundle;

import javaRPGFX.animation.Animation;
import javaRPGFX.gui2.GUILaunch;
import javaRPGFX.gui2.model.View;
import javaRPGFX.gui2.util.GameViewEvent;
import javaRPGFX.sound.SoundPlayerFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GameClearController extends GameViewEvent implements Initializable{

    @FXML
    private Pane gameClearPane;
    @FXML
    private TextFlow textFlow;
    @FXML
    private Text tyMsg;

    private Animation animation = new Animation();
    private Canvas canvas = new Canvas(600,400);
    private SoundPlayerFX soundPlayerFX = SoundPlayerFX.getSoundPlayerClass();
    private GUILaunch app = GUILaunch.getSingleton();
    private Stage stage;
    private MediaPlayer bgm;
    private BackgroundPosition backgroundPosition;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //キャンバス追加
        gameClearPane.getChildren().add(canvas);
        //左上詰めで配置する
        backgroundPosition = new BackgroundPosition(
	        Side.LEFT, 0, false, Side.TOP, 0, false);
        gameClearPane.toBack();
        Image image = new Image("/asset/back4.gif",600,400,true,true);
        BackgroundImage backgroundimage = new BackgroundImage(
            image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, backgroundPosition, BackgroundSize.DEFAULT);
        Background background2 = new Background(backgroundimage);
        gameClearPane.setBackground(background2);

        Text clearText = new Text("Game Clear!");
        clearText.setFill(Color.YELLOW);
        clearText.setFont(Font.font("Helvetica",FontPosture.ITALIC,50));
        clearText.setStroke(Color.WHITE);
        textFlow.getChildren().add(clearText);
        
        canvas.toFront();
        animation.handleFadeOutAnimation(canvas).play();
        animation.bounceAnm(textFlow);
        bgm = soundPlayerFX.playBGM(4);
        bgm.play();
    }
    
    @Override public void keyPressed(KeyEvent e){
        if(e.getCode()== KeyCode.Z){
            var anim = animation.handleFadeInAnimation(canvas);
            anim.setOnFinished(finish ->{
                bgm.stop();
                Window window = gameClearPane.getScene().getWindow();
                stage = (Stage) window;
                app.activate(View.TITLE,stage);
            });
            anim.play();
        }
    }
}
