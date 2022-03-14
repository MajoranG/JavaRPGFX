package javaRPGFX.gui2.game;

import java.net.URL;
import java.util.ResourceBundle;

import javaRPGFX.gui2.model.Menu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import javaRPGFX.animation.Animation;

public class GameMenuController implements Initializable{
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Text magicNameText2;
    @FXML
    private Text magicNameText1;
    @FXML
    private ImageView gameMenucursor;
    @FXML
    private Rectangle targetChoiceBox;
    @FXML
    private Pane gameMenuPane;
    @FXML
    private Rectangle choiceBoxRect;
    @FXML
    private Rectangle magicChoiceBox;
    @FXML
    private Rectangle magicInfoBox;
    @FXML
    private Text menuTx2;
    @FXML
    private Text menuTx1;
    @FXML
    private Text magicInfoTxt;
    @FXML
    private Text allyNameText;
    @FXML
    private Text target1Txt;
    @FXML
    private Text target2Txt;
    @FXML
    private Text target3Txt;
    @FXML
    private Text target4Txt;
    @FXML
    private Text ally1Name;
    @FXML
    private Text ally2Name;
    @FXML
    private Text ally3Name;
    @FXML
    private Text ally4Name;
    @FXML
    private ImageView ally1RectImg,ally2RectImg,ally3RectImg,ally4RectImg;
    @FXML
    private Text ally1HP,ally2HP,ally3HP,ally4HP;
    @FXML
    private Text ally1MP,ally2MP,ally3MP,ally4MP;
    @FXML
    private GameMainController gameMainController;
    
    protected boolean isVisible = false;
    private final double marginY = 26.0;
    private final double marginX = 87.0;
    private Animation animation = new Animation();
    public GameMenuController(){
        
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }
    
    public void displayNameMenu(String name){
        allyNameText.setText(name);
    }
    public void displayName(String name,String name2,String name3,String name4){
        ally1Name.setText(name);
        allyNameText.setText(name);
        ally2Name.setText(name2);
        ally3Name.setText(name3);
        ally4Name.setText(name4);
    }
    public void setMenuVisualize(boolean v){
        isVisible = v;
        choiceBoxRect.setVisible(v);
        magicChoiceBox.setVisible(v);
        magicInfoBox.setVisible(false);
        magicInfoTxt.setVisible(false);
        targetChoiceBox.setVisible(v);
        menuTx1.setVisible(v);
        menuTx2.setVisible(v);
        allyNameText.setVisible(v);
        magicNameText1.setVisible(v);
        magicNameText2.setVisible(v);
        target1Txt.setVisible(v);
        target2Txt.setVisible(v);
        target3Txt.setVisible(v);
        target4Txt.setVisible(v);
        gameMenucursor.setVisible(v);
    }
    public void setMenuVisualize(boolean v,int pattern){
        switch(pattern){
            case 1:
            isVisible = true;
            choiceBoxRect.setVisible(v);
            menuTx1.setVisible(v);
            menuTx2.setVisible(v);
            allyNameText.setVisible(v);
            magicChoiceBox.setVisible(false);
            magicInfoBox.setVisible(false);
            magicInfoTxt.setVisible(false);
            magicNameText1.setVisible(false);
            magicNameText2.setVisible(false);
            targetChoiceBox.setVisible(false);
            target1Txt.setVisible(false);
            target2Txt.setVisible(false);
            target3Txt.setVisible(false);
            target4Txt.setVisible(false);
            gameMenucursor.setX(Menu.FIGHT.positionX);
            gameMenucursor.setY(Menu.FIGHT.positionY);
            gameMenucursor.setVisible(v);
            break;
            case 2:
            isVisible = true;
            choiceBoxRect.setVisible(true);
            menuTx1.setVisible(true);
            menuTx2.setVisible(true);
            allyNameText.setVisible(true);
            targetChoiceBox.setVisible(v);
            target1Txt.setVisible(v);
            target2Txt.setVisible(v);
            target3Txt.setVisible(v);
            target4Txt.setVisible(v);
            gameMenucursor.setX(Menu.TARGET1.positionX);
            gameMenucursor.setY(Menu.TARGET1.positionY);
            gameMenucursor.setVisible(true);
            break;
            case 3:
            isVisible = true;
            choiceBoxRect.setVisible(true);
            menuTx1.setVisible(true);
            menuTx2.setVisible(true);
            allyNameText.setVisible(true);
            magicChoiceBox.setVisible(v);
            magicInfoBox.setVisible(v);
            magicInfoTxt.setVisible(v);
            magicNameText1.setVisible(v);
            magicNameText2.setVisible(v);
            gameMenucursor.setX(Menu.MAGIC1.positionX);
            gameMenucursor.setY(Menu.MAGIC1.positionY);
            gameMenucursor.setVisible(true);
            break;
            case 4:
            isVisible = true;
            gameMenucursor.setX(Menu.TARGET1.positionX);
            gameMenucursor.setY(Menu.TARGET1.positionY);
            setMenuVisualize(true);
            break;
            default:
            setMenuVisualize(false);
            break;
        }
    }
    public void setCursorPosition(int x,int y){
        gameMenucursor.setX(gameMenucursor.getX()+x*marginX);
        gameMenucursor.setY(gameMenucursor.getY()+y*marginY);
    }
    
    public void setMagicActionName(String magic1,String magic2){
        magicNameText1.setText(magic1);
        magicNameText2.setText(magic2);
    }
    public void setMagicInfoTxt(String txt){
        magicInfoTxt.setText(txt);
    }
    public void setTargetName(String t1,String t2,String t3,String t4){
        target1Txt.setText(t1);
        target2Txt.setText(t2);
        target3Txt.setText(t3);
        target4Txt.setText(t4);
    }
    public void setAlliesHP(int v1,int v2,int v3,int v4){
        ally1HP.setText(Integer.valueOf(v1).toString());
        ally2HP.setText(Integer.valueOf(v2).toString());
        ally3HP.setText(Integer.valueOf(v3).toString());
        ally4HP.setText(Integer.valueOf(v4).toString());
    }
    public void setAlliesMP(int v1,int v2,int v3,int v4){
        ally1MP.setText(Integer.valueOf(v1).toString());
        ally2MP.setText(Integer.valueOf(v2).toString());
        ally3MP.setText(Integer.valueOf(v3).toString());
        ally4MP.setText(Integer.valueOf(v4).toString());
    }
    public void setAlly1ToDead(){
        animation.setDeadStatus(ally1RectImg);
    }
    public void setAlly2ToDead(){
        animation.setDeadStatus(ally2RectImg);
    }
    public void setAlly3ToDead(){
        animation.setDeadStatus(ally3RectImg);
    }
    public void setAlly4ToDead(){
        animation.setDeadStatus(ally4RectImg);
    }
    public void setAlly1ToRevive(){
        ally1RectImg.effectProperty().set(null);
    }
    public void setAlly2ToRevive(){
        ally2RectImg.effectProperty().set(null);
    }
    public void setAlly3ToRevive(){
        ally3RectImg.effectProperty().set(null);
    }
    public void setAlly4ToRevive(){
        ally4RectImg.effectProperty().set(null);
    }
    public void playAllyDamagedAnm(){
        Parent root = choiceBoxRect.getParent();
        animation.shakeAnm(root);
    }
    public double getCursorPositionX(){
        return gameMenucursor.getX();
    }
    public double getCursorPositionY(){
        return gameMenucursor.getY();
    }
    public double getMarginX(){
        return this.marginX;
    }
    public double getMarginY(){
        return this.marginY;
    }
    public boolean getMenuStatus(){
        return this.isVisible;
    }
}