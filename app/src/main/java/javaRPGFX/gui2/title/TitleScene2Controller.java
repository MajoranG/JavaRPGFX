package javaRPGFX.gui2.title;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javaRPGFX.animation.Animation;
import javaRPGFX.gui2.GUILaunch;
import javaRPGFX.gui2.model.View;
import javaRPGFX.gui2.util.GameViewEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import javaRPGFX.sound.SoundPlayerFX;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TitleScene2Controller extends GameViewEvent implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView cursor;

    @FXML
    private Text wynText;

    @FXML
    private Text backToButtonTxt;

    @FXML
    private Label nameTextLabel;

    @FXML
    private Text nextToButtonTxt;

    @FXML
    private Label line1,line2,line3,line4;

    @FXML
    private Pane titleScene2Pane;

    private double positionX;
    private double positionY;
    private Stage stage;
    private Scene scene;
    private final double marginX = 29.0;
    private final double marginY = 33.0;
    private final int width = 13;
    private final int height = 4;
    private Map<Double, String> textLocationMap1 = new HashMap<>();
    private Map<Double, String> textLocationMap2 = new HashMap<>();
    private Map<Double, String> textLocationMap3 = new HashMap<>();
    private Map<Double, String> textLocationMap4 = new HashMap<>();
    private double[] cordinateX = new double[width+1];
    GUILaunch app = GUILaunch.getSingleton();
    private Canvas canvas = new Canvas(600,400);
    private Canvas canvas2 = new Canvas(600,400);
    private Animation animation = new Animation();

    //サウンド
    private SoundPlayerFX soundPlayerFX = SoundPlayerFX.getSoundPlayerClass();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        this.positionX = cursor.getX();
        this.positionY = cursor.getY();

        for(int i=0;i<cordinateX.length;i++){
            cordinateX[i] = marginX*i;
        }
        char c = 'A',c2 = 'N',c3 = 'a',c4 = 'n';
        for(int i = 0;i<=('M'-'A');i++){
            textLocationMap1.put(cordinateX[i],String.valueOf(c++));
            textLocationMap3.put(cordinateX[i],String.valueOf(c3++));
        }
        for(int i = 0;i<=('Z'-'M');i++){
            textLocationMap2.put(cordinateX[i],String.valueOf(c2++));
            textLocationMap4.put(cordinateX[i],String.valueOf(c4++));
        }

        titleScene2Pane.getChildren().addAll(canvas,canvas2);
    }

    @FXML
    @Override public void keyPressed(KeyEvent e){

        switch(e.getCode()){
            case Z:  //決定
            soundPlayerFX.playSFX(17);
            if(cursor.getY() == 180){
                if(cursor.getX() == 340){
                    //キーイベント消去
                    e.consume();
                    scene = titleScene2Pane.getScene();
                    scene.setOnKeyPressed(null);
                    //フェードインアニメーションでゲーム画面に遷移する
                    soundPlayerFX.playSFX(15);
                    animation.startBattleAnm(canvas2);
                    canvas.toFront();
                    var anim = animation.handleFadeInAnimation(canvas);
                    anim.setDelay(Duration.millis(2100));
                    anim.setOnFinished(finish ->{
                        app.addAndSendToGame(View.GAMESCENE1,nameTextLabel.getText(),1);
                        Window window = scene.getWindow();
                        stage = (Stage) window;
                        app.activate(View.GAMESCENE1,stage);
                    });
                    anim.play();
                }
                else if(cursor.getX() == -15){
                    Window window = titleScene2Pane.getScene().getWindow();
                    stage = (Stage) window;
                    app.activate(View.TITLE,stage);
                }
            }
            else setText1by1();
            break;
            case UP:
            if(cursor.getY()==180 && cursor.getX()==340){
                cursor.setX(marginX*(width-1));
                cursor.setY(marginY*(height-1));
            }
            else if(cursor.getY()==180 && cursor.getX()==-15){
                cursor.setX(0);
                cursor.setY(marginY*(height-1));
            }
            else if(cursor.getY() > positionY){
                cursor.setY(cursor.getY()-marginY);
            }
            break;
            case DOWN:
            if((positionY+marginY*(height-1))>cursor.getY()){
                cursor.setY(cursor.getY()+marginY);
            }
            else if(cursor.getX()>marginX*((width-1)/2)){
                cursor.setY(180);
                cursor.setX(340);
            }
            else if(cursor.getX()<marginX*((width-1)/2)){
                cursor.setY(180);
                cursor.setX(-15);
            }
            break;
            case RIGHT:
            if(cursor.getY()==180 && cursor.getX()==-15){
                cursor.setY(180);
                cursor.setX(340);
            }
            else if(marginX*(width-1)>cursor.getX()&&cursor.getY()!=180){
                cursor.setX(cursor.getX()+marginX);
            }
            break;
            case LEFT:
            if(cursor.getY()==180 && cursor.getX()==340){
                cursor.setY(180);
                cursor.setX(-15);
            }
            else if(positionX<cursor.getX()){
                cursor.setX(cursor.getX()-marginX);
            }
            break;
            case X:  //戻る
            deleteText1by1();
            break;
            case C:
            soundPlayerFX.playSFX(17);
            if(cursor.getY() == 180){
                if(cursor.getX() == 340){
                    //キーイベント消去
                    e.consume();
                    scene = titleScene2Pane.getScene();
                    scene.setOnKeyPressed(null);
                    //フェードインアニメーションでハードモードに遷移する
                    soundPlayerFX.playSFX(15);
                    animation.startBattleAnm(canvas2);
                    canvas.toFront();
                    var anim = animation.handleFadeInAnimation(canvas);
                    anim.setDelay(Duration.millis(2100));
                    anim.setOnFinished(finish ->{
                        app.addAndSendToGame(View.GAMESCENE1,nameTextLabel.getText(),2);
                        Window window = scene.getWindow();
                        stage = (Stage) window;
                        app.activate(View.GAMESCENE1,stage);
                    });
                    anim.play();
                }
            }
            break;
            default: 
            break;
        }
    }

    private void setText1by1(){
        String previousText = nameTextLabel.getText();
        if(previousText.length()<8){
            if(cursor.getY()==0.0){
                nameTextLabel.setText(previousText+textLocationMap1.get(cursor.getX()));
            }
            else if(cursor.getY()==marginY){
                nameTextLabel.setText(previousText+textLocationMap2.get(cursor.getX()));
            }
            else if(cursor.getY()==marginY*2){
                nameTextLabel.setText(previousText+textLocationMap3.get(cursor.getX()));
            }
            else if(cursor.getY()==marginY*3){
                nameTextLabel.setText(previousText+textLocationMap4.get(cursor.getX()));
            }
        }
    }

    private void deleteText1by1(){
        String previousText = nameTextLabel.getText();
        if(cursor.getY()==180){ }
        else{
            if(previousText != ""){
                nameTextLabel.setText(previousText.substring(0, previousText.length()-1));
            }
        }
    }
}