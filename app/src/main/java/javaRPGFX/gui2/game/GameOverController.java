package javaRPGFX.gui2.game;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javaRPGFX.animation.Animation;
import javaRPGFX.gui2.GUILaunch;
import javaRPGFX.gui2.model.View;
import javaRPGFX.gui2.util.GameViewEvent;
import javaRPGFX.sound.SoundPlayerFX;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class GameOverController extends GameViewEvent implements Initializable{
    @FXML
    private Pane gameOverScenePane;
    @FXML
    private Text gmOverTxt;
    @FXML
    private Text gmOtext;

    private Canvas canvas = new Canvas(600,400);
    private Animation animation = new Animation();
    private SoundPlayerFX soundPlayerFX = SoundPlayerFX.getSoundPlayerClass();
    private GUILaunch app = GUILaunch.getSingleton();
    private Stage stage;
    private MediaPlayer bgm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        gameOverScenePane.getChildren().add(canvas);
        animation.handleFadeOutAnimation(canvas).play();
        Random random = new Random();
        int rand = random.nextInt(50);
        if(rand >40){
            bgm = soundPlayerFX.playBGM(2);
        }
        else bgm = soundPlayerFX.playBGM(3);
        bgm.play();
        animation.blinkingOpacity_short(gmOtext).play();
    }

    @Override public void keyPressed(KeyEvent e){
        if(e.getCode()== KeyCode.Z){
            var anim = animation.handleFadeInAnimation(canvas);
            anim.setOnFinished(finish ->{
                bgm.stop();
                Window window = gameOverScenePane.getScene().getWindow();
                stage = (Stage) window;
                app.activate(View.TITLE,stage);
            });
            anim.play();
        }
    }
}