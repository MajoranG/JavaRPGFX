package javaRPGFX.gui2.game;

import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javaRPGFX.animation.Animation;
import javaRPGFX.character.Character;
import javaRPGFX.character.allies.Ally1;
import javaRPGFX.character.allies.Ally2;
import javaRPGFX.character.allies.Ally3;
import javaRPGFX.character.allies.Ally4;
import javaRPGFX.gui2.GUILaunch;
import javaRPGFX.gui2.model.Menu;
import javaRPGFX.gui2.model.View;
import javaRPGFX.gui2.BattleSystem;
import javaRPGFX.sound.SoundPlayerFX;
import javaRPGFX.gui2.util.GameViewEvent;

import javafx.animation.PauseTransition;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

//GameScene,GameMenu nested controller
public class GameMainController extends GameViewEvent implements Initializable{
    
    @FXML
    private Pane gameMainPane;
    @FXML
    private GameSceneController gameSceneController;
    @FXML
    private GameMenuController gameMenuController;
    @FXML
    private GameBackController gameBackController;
    @FXML
    private GameMainController gameMainController;
    @FXML
    private Text hardModeTxt,hai,iie;
    @FXML
    private Rectangle levelSelectBox;

    GUILaunch app = GUILaunch.getSingleton();
    private Stage stage;
    private Canvas canvas = new Canvas(600,400);
    private Canvas canvas2 = new Canvas(600,400);
    private Animation preAnimation = new Animation();
    private Animation animation = new Animation();
    private PauseTransition pause;
    private BooleanProperty isAllDead = new SimpleBooleanProperty(false);
    private BooleanProperty isWin = new SimpleBooleanProperty(false);

    //????????????
    //private SoundPlayerFX soundPlayerFX = SoundPlayerFX.getSoundPlayerClass();
    private SoundPlayerFX soundPlayerFX;
    private MediaPlayer bgm;

    private int allyNum;
    private int enemyNum;
    private int turnNum;
    //private int magicQuan;
    private int targetQuan;
    private int enemyQuan;
    private int allyQuan;
    private String allyName1String;
    private String allyName2String;
    private String allyName3String;
    private String allyName4String;
    private String enemyNameString;
    //????????????
    private int enemyKind;
    private int enemyTargetTmp;

    private STATE s_state;
    private TXT t_state;
    private EnemyState e1State;
    private EnemyState e2State;
    private AllyState a1State;
    private AllyState a2State;
    private AllyState a3State;
    private AllyState a4State;
    //???????????????...
    private Flug e1DeadPopUp;
    private Flug e2DeadPopUp;
    private Flug a1DeadPopUp;
    private Flug a2DeadPopUp;
    private Flug a3DeadPopUp;
    private Flug a4DeadPopUp;
    private Flug e1Dead;
    private Flug e2Dead;
    //?????????
    private ArrayList<String> ally1Action; 
    private ArrayList<String> ally2Action;
    private ArrayList<String> ally3Action;
    private ArrayList<String> ally4Action;
    private ArrayList<String> actionBuffer;
    private List<String> allyIndexList;
    //?????????
    private Deque<ArrayList<String>> actionQueue;

    private Character[] allies = new Character[4];
    private BattleSystem bs;
    private List<String> eventFlug;
    private List<String> actionList;
    private String[] bufferedTxtArr;
    private int handleBuffernum = 0;
    private int handleTxtOrderNum = 0;

    public GameMainController(){
        
        //????????????????????????????????????????????????????????????????????????
        eventFlug = new ArrayList<>();
        actionList = new ArrayList<>();
        //???????????????????????????????????????
        allies[1] = new Ally2();
        allies[2] = new Ally3();
        allies[3] = new Ally4();
        //this.magicQuan = 2;
        this.allyName2String =allies[1].getName();
        this.allyName3String =allies[2].getName();
        this.allyName4String =allies[3].getName();

        //????????????????????????????????????????????????
        //0:??????(??????) 1:at(????????????)or mg(??????)???2:e or a(???????????????) 3:????????????
        ally1Action = new ArrayList<String>();
        ally2Action = new ArrayList<String>();
        ally3Action = new ArrayList<String>();
        ally4Action = new ArrayList<String>();
        actionBuffer = new ArrayList<String>();
        //?????????????????????????????????
        this.allyNum = 1;
        this.allyQuan = 4;
        //????????????
        this.turnNum = 1;
        this.s_state = STATE.MENU_OPERATION;
        this.t_state = TXT.NoBuffer;
        //????????????????????????
        actionQueue = new ArrayDeque<>();
        //?????????
        e1Dead = Flug.First;
        e2Dead = Flug.First;
        a1DeadPopUp = Flug.First;
        a2DeadPopUp = Flug.First;
        a3DeadPopUp = Flug.First;
        a4DeadPopUp = Flug.First;
        //???????????????????????????
        allyIndexList = new ArrayList<String>(Arrays.asList("1","2","3","4"));
    }

    //?????????????????????????????????
    private enum STATE{
        MENU_OPERATION,
        TEXT_OPERATION,
        SELECT_COMPLETE,
        OPERATION_END;
    }
    
    //?????????????????????????????????
    private enum AllyState{
        ALIVE,DEAD;
    }
    //??????????????????????????????
    private enum EnemyState{
        ALIVE,DEAD;
    }
    //????????????????????????????????????2?????????????????????
    private enum TXT{
        Buffer,NoBuffer,First;
    }
    //?????????????????????
    private enum Flug{
        First,notFirst;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        //soundPlayerFX = new SoundPlayerFX();
        soundPlayerFX = SoundPlayerFX.getSoundPlayerClass();
        
        var gameOverAnm = animation.handleFadeInAnimation(canvas);
        //?????????????????????
        isAllDead.addListener((observableValue,oldV,newV) ->{
            //???????????????????????????
            if(newV == true){
            bgm.stop();
            //????????????????????????
            Scene scene = gameMainPane.getScene();
            scene.setOnKeyPressed(null);
            
            gameOverAnm.setDelay(Duration.millis(4000));
            gameOverAnm.setOnFinished(finish ->{
                app.add(View.GAMEOVER);
                Window window = scene.getWindow();
                stage = (Stage) window;
                app.activate(View.GAMEOVER,stage);
            });

            gameSceneController.setTextBoxVisualize(true);
            gameSceneController.playTextAnimation("???????????????????????????...");
            soundPlayerFX.playSFX(10);
            gameOverAnm.play();
            }
        });
        isWin.addListener(observation ->{
            //???????????????
            bgm.stop();
            //????????????????????????
            Scene scene = gameMainPane.getScene();
            scene.setOnKeyPressed(null);
            gameSceneController.setTextBoxVisualize(true);
            gameSceneController.playTextAnimation("????????????????????????!");
            gameSceneController.playWinAnimation();
            if(this.enemyKind==1){
                pause = new PauseTransition(Duration.millis(5000));
                pause.setOnFinished(finish ->{
                    setLevelSelectVisualize(true);
                });
                pause.play();
            }
            else if(this.enemyKind == 2){
                var clearAnm = animation.handleFadeInAnimation(canvas);
                clearAnm.setDelay(Duration.millis(4000));
                clearAnm.setOnFinished(finish ->{
                    app.add(View.GAMECLEAR);
                    Window window = scene.getWindow();
                    stage = (Stage) window;
                    app.activate(View.GAMECLEAR,stage);
                });
                clearAnm.play();
            }
            soundPlayerFX.playSFX(14);
        });
        //??????????????????????????????
        gameMenuController.setMenuVisualize(false);
        hardModeTxt.setVisible(false);
        hai.setVisible(false);
        iie.setVisible(false);
        levelSelectBox.setVisible(false);
        //?????????????????????
        levelSelectBox.setFocusTraversable(true);
        levelSelectBox.setOnKeyPressed(event -> this.exKeyPressed(event));
        //????????????????????????????????????????????????????????????(?????????toFront()???????????????
        gameMainPane.getChildren().add(canvas);
        gameMainPane.getChildren().add(canvas2);
        //???????????????????????????????????????????????????
        var anim = preAnimation.handleFadeOutAnimation(canvas);
        anim.play();
    }

    public void keyPressed(KeyEvent e){

        if(s_state == STATE.SELECT_COMPLETE){
            /* System.out.println(ally1Action);
            System.out.println(ally2Action);
            System.out.println(ally3Action);
            System.out.println(ally4Action); */
            storeEnemyAction();
            s_state = STATE.TEXT_OPERATION;
        }

        //????????????box??????????????????
        if(s_state == STATE.TEXT_OPERATION){
            gameSceneController.setTextBoxVisualize(true);
            gameMenuController.setMenuVisualize(false);
            
            if(e.getCode() == KeyCode.Z || e.getCode() == KeyCode.X){
                if(t_state == TXT.NoBuffer){
                    manageTxtBoxOperation();
                }
                else if(t_state == TXT.Buffer){
                    displayBufferdTxt(++handleBuffernum);
                }
                if(e1State == EnemyState.DEAD && e2State == EnemyState.DEAD && t_state==TXT.NoBuffer){
                    isWin.set(true);
                    s_state = STATE.OPERATION_END;
                    return;
                }
                if(a1State == AllyState.DEAD && a2State == AllyState.DEAD && 
                a3State == AllyState.DEAD && a4State == AllyState.DEAD && t_state==TXT.NoBuffer){
                    isAllDead.set(true);
                    s_state = STATE.OPERATION_END;
                    return;
                }
            }
            return;
        }
        
        if(s_state == STATE.MENU_OPERATION){
            switch(e.getCode()){
                case Z:  //???????????????
                soundPlayerFX.playSFX(3);
                if(gameSceneController.getTxtBIsVisible()){
                    gameSceneController.setTextBoxVisualize(false);
                    setTextInMenu(this.allyNum);
                    gameMenuController.setMenuVisualize(true,1);
                }
                else if(gameMenuController.getCursorPositionY()==Menu.FIGHT.positionY
                && gameMenuController.getCursorPositionX()==Menu.FIGHT.positionX){
                    //"??????"????????????
                    actionBuffer.add(Integer.valueOf(this.allyNum).toString());
                    //"????????????"????????????
                    actionBuffer.add("at");
                    actionBuffer.add("e");
                    confirmAndSetTargetName();
                    gameMenuController.setMenuVisualize(true,2);
                }
                
                else if(gameMenuController.getCursorPositionY()==Menu.MAGIC.positionY
                && gameMenuController.getCursorPositionX()==Menu.MAGIC.positionX){
                    setTextInMenu(this.allyNum);
                    
                    gameMenuController.setMagicInfoTxt(allies[allyNum-1].getMagicInfo1Txt());
                    gameMenuController.setMenuVisualize(true,3);
                }
                else if(gameMenuController.getCursorPositionY()==Menu.MAGIC1.positionY
                && gameMenuController.getCursorPositionX()==Menu.MAGIC1.positionX){
                    //"??????"????????????
                    actionBuffer.add(Integer.valueOf(this.allyNum).toString());
                    //??????????????????();
                    actionBuffer.add("mg1");
                    if(this.allyNum == 1 || this.allyNum == 4){
                        actionBuffer.add("eall");
                        this.targetQuan = 1;
                        gameMenuController.setTargetName("??????????????????","","","");
                    }
                    else {
                        actionBuffer.add("e");
                        confirmAndSetTargetName();
                    }
                    gameMenuController.setMenuVisualize(true,4);
                }
                else if(gameMenuController.getCursorPositionY()==Menu.MAGIC2.positionY
                && gameMenuController.getCursorPositionX()==Menu.MAGIC2.positionX){
                    //"??????"????????????
                    actionBuffer.add(Integer.valueOf(this.allyNum).toString());
                    //??????????????????();
                    actionBuffer.add("mg2");
                    if(this.allyNum == 3){
                        actionBuffer.add("aall");
                        this.targetQuan = 1;
                        gameMenuController.setTargetName("?????????????????????","","","");
                    }
                    else{
                        actionBuffer.add("a");
                        this.targetQuan = this.allyQuan;
                        gameMenuController.setTargetName(allyName1String, allyName2String, allyName3String, allyName4String);
                    }
                    gameMenuController.setMenuVisualize(true,4);
                }
                //?????????????????????
                //???????????????????????????????????????????????????
                else if(gameMenuController.getCursorPositionY()==Menu.TARGET1.positionY){
                    if(gameMenuController.getCursorPositionX()==Menu.TARGET1.positionX){
                        //???????????????1????????????
                        actionBuffer.add("1");
                    }
                    else if(gameMenuController.getCursorPositionX()==Menu.TARGET2.positionX){
                        //???????????????2????????????
                        actionBuffer.add("2");
                    }
                    else if(gameMenuController.getCursorPositionX()==Menu.TARGET3.positionX){
                        //???????????????3????????????
                        actionBuffer.add("3");
                    }
                    else if(gameMenuController.getCursorPositionX()==Menu.TARGET4.positionX){
                        //???????????????4????????????
                        actionBuffer.add("4");
                    }
                    //???????????????
                    setAction(actionBuffer);
                    updateAllyNum(1);
                    if(s_state != STATE.SELECT_COMPLETE){
                        setTextInMenu(this.allyNum);
                        gameMenuController.setMenuVisualize(true,1);
                    }
                    else keyPressed(e);
                }
                break;
                
                case UP:
                if(gameMenuController.getCursorPositionX() == Menu.FIGHT.positionX
                && gameMenuController.getCursorPositionY()>Menu.FIGHT.positionY){
                    gameMenuController.setCursorPosition(0, -1);
                }
                else if(gameMenuController.getCursorPositionX() == Menu.MAGIC1.positionX
                && gameMenuController.getCursorPositionY()>Menu.MAGIC1.positionY){
                    //??????????????????????????????????????????
                    gameMenuController.setMagicInfoTxt(allies[allyNum-1].getMagicInfo1Txt());
                    gameMenuController.setCursorPosition(0, -1);
                }
                break;
                case DOWN:
            
                if(gameMenuController.getCursorPositionX()==Menu.FIGHT.positionX
                && gameMenuController.getCursorPositionY()<Menu.MAGIC.positionY){
                    gameMenuController.setCursorPosition(0, 1);
                }
                else if(gameMenuController.getCursorPositionX()==Menu.MAGIC2.positionX
                && gameMenuController.getCursorPositionY()<Menu.MAGIC2.positionY){
                    //??????????????????????????????????????????
                    gameMenuController.setMagicInfoTxt(allies[allyNum-1].getMagicInfo2Txt());
                    gameMenuController.setCursorPosition(0, 1);
                }
                break;
                case RIGHT:
                if(gameMenuController.getCursorPositionY()==Menu.TARGET1.positionY&&
                gameMenuController.getCursorPositionX()<(Menu.TARGET1.positionX+gameMenuController.getMarginX()*(targetQuan-1))){
                    gameMenuController.setCursorPosition(1, 0);
                }
                break;
                case LEFT:
                if(gameMenuController.getCursorPositionY()==Menu.TARGET1.positionY&&
                gameMenuController.getCursorPositionX()>Menu.TARGET1.positionX){
                    gameMenuController.setCursorPosition(-1, 0);
                }
                break;
                case X:  //??????
                if(gameSceneController.getTxtBIsVisible()==false){
                    soundPlayerFX.playSFX(2);
                    //???????????????Menu.FIGHT.positionX????????????X?????????????????????????????????????????????????????????
                    //????????????????????????????????????????????????????????????
                    if(gameMenuController.getCursorPositionX()==Menu.FIGHT.positionX
                    && gameMenuController.getCursorPositionY()==Menu.FIGHT.positionY ){
                        updateAllyNum(-1);
                        setTextInMenu(this.allyNum);
                        //??????????????????????????????
                        if(actionQueue.peek()!=null){
                            actionQueue.removeLast();
                        }
                    }
                    //???????????????
                    actionBuffer.clear();
                    gameMenuController.setMenuVisualize(true,1);
                }
                break;
                case C:
                break;
                default: 
                break;
            }
        }
    }
    //-------------------------------------------Initialize???????????????-----------------------------------------------//
    //??????????????????????????????????????? HP???MP???????????????
    public void displayName(String name){
        gameMenuController.displayName(name,allyName2String,allyName3String,allyName4String);
        this.allyName1String = name;
        allies[0] = new Ally1(name);
        gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
        gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
    }
    //????????????????????????????????????(???????????????BGM)
    public void setGameScene(int enemyKind){
        //bgm????????????
        bgm = soundPlayerFX.playBGM(0);
        //???????????????
        this.enemyKind = enemyKind;
        //??????????????????
        Random random = new Random();
        int num = random.nextInt(2)+1;
        this.enemyQuan = num;
        if(enemyKind == 2){
            this.enemyQuan = 1;
            bgm = soundPlayerFX.playBGM(1);
        }
        //?????????????????????????????????????????????????????????
        bs = new BattleSystem(allies[0],allies[1],allies[2],allies[3],enemyQuan,enemyKind);
        this.enemyNameString = bs.getEnemyKindName();
        //??????????????????
        if(this.enemyQuan==1){
            this.e1DeadPopUp = Flug.First;
            e2State = EnemyState.DEAD;
            e2DeadPopUp = Flug.notFirst;
        }
        else{
            this.e1DeadPopUp = Flug.First;
            this.e2DeadPopUp = Flug.First;
        }
        //??????????????????
        gameBackController.settingBackground(enemyKind);
        //????????????
        gameSceneController.putEnemyImg(this.enemyQuan,enemyKind);
        gameSceneController.playTextAnimation(String.format("%s???%d??????????????????!",this.enemyNameString,this.enemyQuan));
        //BGM?????????
        bgm.play();
    }
    //-----------------------------------------------------------------------------------------------------------------//
    //???????????????????????????
    private void storeEnemyAction(){
        if(e1State == EnemyState.DEAD){
            this.enemyNum = 2;
            var arr1 = bs.generateEnemyAction(enemyNum,selectTarget());
            ArrayList<String> arrlist1 = new ArrayList<String>(Arrays.asList(arr1));
            actionQueue.add(arrlist1);
        }
        else if(e2State == EnemyState.DEAD){
            this.enemyNum = 1;
            var arr1 = bs.generateEnemyAction(enemyNum,selectTarget());
            ArrayList<String> arrlist1 = new ArrayList<String>(Arrays.asList(arr1));
            actionQueue.add(arrlist1);
        }
        else{
            this.enemyNum = 1;
            var arr1 = bs.generateEnemyAction(enemyNum,selectTarget());
            ArrayList<String> arrlist1 = new ArrayList<String>(Arrays.asList(arr1));
            actionQueue.add(arrlist1);
            if(this.enemyQuan!=1){
                var arr2 = bs.generateEnemyAction(2,selectTarget());
                ArrayList<String> arrlist2 = new ArrayList<String>(Arrays.asList(arr2));
                actionQueue.add(arrlist2);
            }
        }
    }
    //????????????????????????????????????????????????
    public void setTextInMenu(int num){
        
        gameMenuController.displayNameMenu(allies[num-1].getName());
        gameMenuController.setMagicActionName(allies[num-1].getMagic1Name(), allies[num-1].getMagic2Name());
    }
    //??????????????????????????????????????????????????????(1:???-1:???)
    private void updateAllyNum(int n){
        switch(n){
            case 1:
            if(this.allyNum<4){
                //?????????????????????????????????
                if(allies[this.allyNum].getHp()==0){
                    this.allyNum += 1;
                    updateAllyNum(1);
                }
                else this.allyNum += 1;
            }
            else if(this.allyNum == allyQuan){
                gameMenuController.setMenuVisualize(false);
                this.allyNum = 1;
                s_state=STATE.SELECT_COMPLETE;
            }
            break;
            case -1:
            if(this.allyNum-2>0){
                //?????????????????????????????????
                if(allies[this.allyNum-2].getHp()==0){
                    this.allyNum -= 1;
                    updateAllyNum(-1);
                }
                else this.allyNum -= 1;
            }
            else if(this.allyNum == 2){
                if(allies[0].getHp()==0){ }
                else this.allyNum -= 1;
            }
            break;
            default:
            break;
        }
    }
    //??????????????????????????????
    public void setAction(ArrayList<String> buffer){
        switch(this.allyNum){
            case 1:
            ally1Action.clear();
            ally1Action.addAll(buffer);
            actionQueue.add(ally1Action);
            buffer.clear();
            break;
            case 2:
            ally2Action.clear();
            ally2Action.addAll(buffer);
            actionQueue.add(ally2Action);
            buffer.clear();
            break;
            case 3:
            ally3Action.clear();
            ally3Action.addAll(buffer);
            actionQueue.add(ally3Action);
            buffer.clear();
            break;
            case 4:
            ally4Action.clear();
            ally4Action.addAll(buffer);
            actionQueue.add(ally4Action);
            buffer.clear();
            break;
            default:
            break;
        }
    }
    //?????????????????????????????????????????????
    private void manageTxtBoxOperation(){
        //System.out.println(actionQueue);
        ArrayList<String> arrlist = actionQueue.peek();
        if(arrlist != null){
            if(handleTxtOrderNum==0){
                //????????????????????????
                if(arrlist.size()==4){
                    //????????????????????????????????????????????????????????????????????????
                    int tmp = Integer.parseInt(arrlist.get(0));
                    if(allies[tmp-1].getHp()==0){
                        arrlist.clear();
                        actionQueue.poll();
                        bufferedTxtArr = null;
                        manageTxtBoxOperation();
                        return;
                    }
                    //???????????????????????????????????????
                    if(e1State==EnemyState.DEAD && arrlist.get(2)=="e"){
                        arrlist.set(3,"2");
                    }
                    if(e2State==EnemyState.DEAD && arrlist.get(2)=="e"){
                        arrlist.set(3,"1");
                    }
                    enemyTargetTmp = Integer.valueOf(arrlist.get(3));
                    String[] arr = (String[]) arrlist.toArray(new String[4]);
                    //System.out.println(bs.getSenarioAndOrder(arr1));
                    txtPreprocessing(bs.getSenarioAndOrder(arr));
                }
                //?????????????????????
                else if(arrlist.size()==3){
                    //????????????????????????????????????????????????????????????????????????
                    if(bs.getEnemy1Hp()==0 && enemyNum == 1 && e1Dead == Flug.First){
                        e1Dead = Flug.notFirst;
                        arrlist.clear();
                        actionQueue.poll();
                        bufferedTxtArr = null;
                        enemyNum++;
                        manageTxtBoxOperation();
                        return;
                    }
                    else if(bs.getEnemy2Hp()==0 && enemyNum == 2 && e2Dead == Flug.First){
                        e2Dead = Flug.notFirst;
                        arrlist.clear();
                        actionQueue.poll();
                        bufferedTxtArr = null;
                        manageTxtBoxOperation();
                        return;
                    }
                    else{
                        //??????????????????????????????????????????
                        if(arrlist.get(2)!="aall"){
                            int tmp = Integer.parseInt(arrlist.get(2));
                            if(allies[tmp-1].getHp()==0){
                                arrlist.set(2,selectTarget());
                            }
                        }
                        String[] arr = (String[]) arrlist.toArray(new String[3]);
                        txtPreprocessing(bs.getEnemySenarioAndOrder(enemyNum, arr));
                        if(enemyNum<enemyQuan){
                            enemyNum++;
                        }
                    }  
                }
            }
            displayTextAndPlaySound(handleTxtOrderNum);
            handleTxtOrderNum++;
            if(handleTxtOrderNum == 2){
                actionQueue.poll();
                handleTxtOrderNum = 0;
                //??????????????????
                if(this.turnNum!=(allyQuan+enemyQuan))
                    this.turnNum += 1;
            }
        }
        if(actionQueue.peek()==null){
            handleTxtOrderNum = 0;
            //??????????????????????????????
            if(t_state == TXT.NoBuffer){
                this.turnNum = 1;
                if(allies[0].getHp()==0){
                    updateAllyNum(1);
                }
                s_state = STATE.MENU_OPERATION;
            }
            return;
        }
    }
    //Map?????????????????????????????????????????????????????????
    private void txtPreprocessing(Map<String, String> map){
        eventFlug.clear();
        actionList.clear();
        for(String key : map.keySet()){
            eventFlug.add(key);
        }
        for(String value : map.values()){
            actionList.add(value);
        }
        String tmp = actionList.get(1);
        bufferedTxtArr = tmp.split(" ");
    }
    private void displayTextAndPlaySound(int i){
        if(eventFlug.get(i)=="ata"){
            //?????????????????????????????????
            soundPlayerFX.playSFX(0);
            //gameSceneController.playTextAnimation(actionList.get(i));
            gameSceneController.setText(actionList.get(i));
            return;
        }
        else if(eventFlug.get(i)=="atae"){
            //????????????????????????????????????
            soundPlayerFX.playSFX(8);
            //
            gameSceneController.setText(actionList.get(i));
            return;
        }
        else if(eventFlug.get(i)=="mg"){
            //????????????????????????????????????
            soundPlayerFX.playSFX(11);
            //gameSceneController.playTextAnimation(actionList.get(i));
            gameSceneController.setText(actionList.get(i));
            return;
        }
        else if(eventFlug.get(i)=="mge"){
            //????????????????????????????????????
            soundPlayerFX.playSFX(18);
            gameSceneController.setText(actionList.get(i));
        }
        else if(eventFlug.get(i)=="talka"){
            //??????????????????????????????(?)
            soundPlayerFX.playSFX(13);
            gameSceneController.setText(actionList.get(i));
        }
        else if(eventFlug.get(i)=="at"){
            //???????????????????????????
            soundPlayerFX.playSFX(7);
            gameSceneController.enemyDamaged(enemyTargetTmp);
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
                //gameSceneController.playTextAnimation(bufferedTxtArr[0]);
            }
            else{
                gameSceneController.setText(actionList.get(i));
                //HPMP??????
                gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
                gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
                confirmCharacterStatus();
            }
            return;
        }
        else if(eventFlug.get(i)=="ate"){
            //??????????????????????????????
            soundPlayerFX.playSFX(1);
            gameMenuController.playAllyDamagedAnm();
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
                //gameSceneController.playTextAnimation(bufferedTxtArr[0]);
            }
            else {
                gameSceneController.setText(actionList.get(i));
                //HPMP??????
                gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
                gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
                confirmCharacterStatus();
            }
            return;
        }
        else if(eventFlug.get(i) =="mg1" || eventFlug.get(i) =="mg1_all"){
            //???????????????????????????????????????setOnfinished???didplayBufferdTxt
            //gameSceneController.playTextAnimation(actionList.get(i));
            //???
            soundPlayerFX.playSFX(7);
            if(eventFlug.get(i) =="mg1_all"){
                for(int j=enemyTargetTmp;j<=this.enemyQuan;j++){
                    gameSceneController.enemyDamaged(j);
                }
            }
            else gameSceneController.enemyDamaged(enemyTargetTmp);
            
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
            }
            else {
                gameSceneController.setText(actionList.get(i));
                //HPMP??????
                gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
                gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
                confirmCharacterStatus();
            }
            return;
        }
        else if(eventFlug.get(i) =="mg2"){
            soundPlayerFX.playSFX(9);
            gameSceneController.playHealAnm();
            //???????????????????????????????????????setOnfinished???didplayBufferdTxt
            //gameSceneController.playTextAnimation(actionList.get(i));
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
            }
            else {
                gameSceneController.setText(actionList.get(i));
                //HPMP??????
                gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
                gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
                confirmCharacterStatus();
            }
            return;
        }
        else if(eventFlug.get(i) == "mgea"){
            //???????????????????????????????????????
            soundPlayerFX.playSFX(1);
            gameMenuController.playAllyDamagedAnm();
            //???????????????????????????????????????setOnfinished???didplayBufferdTxt
            //gameSceneController.playTextAnimation(actionList.get(i));
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
            }
            else {
                gameSceneController.setText(actionList.get(i));
                //HPMP??????
                gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
                gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
                confirmCharacterStatus();
            }
            return;
        }
        else if(eventFlug.get(i) =="talk"){
            //?????????
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
            }
            else gameSceneController.setText(actionList.get(i));
            return;
        }
        else if(eventFlug.get(i)=="fail"){
            //????????????
            soundPlayerFX.playSFX(12);
            gameSceneController.setText(actionList.get(i));
        }
    }
    private void displayBufferdTxt(int i){
        if(i<bufferedTxtArr.length){
            //gameSceneController.playTextAnimation(bufferedTxtArr[i]);
            gameSceneController.setText(bufferedTxtArr[i]);
            //HPMP??????
            gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
            gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
            confirmCharacterStatus();
        }
        else{
            t_state = TXT.NoBuffer;
            this.handleBuffernum = 0;
            this.handleTxtOrderNum = 0;
            //??????????????????????????????????????????
            if(this.turnNum==(allyQuan+enemyQuan)){
                this.turnNum = 1;
                if(allies[0].getHp()==0){
                    updateAllyNum(1);
                }
                s_state = STATE.MENU_OPERATION;
            }
        }
        return;
    }
    private void confirmAndSetTargetName(){
        if(e1State==EnemyState.DEAD){
            this.enemyQuan = 1;
            this.targetQuan = this.enemyQuan;
            gameMenuController.setTargetName(bs.getEnemy2Name(),"","","");
        }
        else if(e2State==EnemyState.DEAD){
            this.enemyQuan = 1;
            this.targetQuan = this.enemyQuan;
            gameMenuController.setTargetName(bs.getEnemy1Name(),"","","");
        }
        else{
            this.targetQuan = this.enemyQuan;
            gameMenuController.setTargetName(bs.getEnemy1Name(),bs.getEnemy2Name(),"","");
        }
    }
    //?????????????????????
    private void confirmCharacterStatus(){
        if(bs.getEnemy1Hp()==0 && e1DeadPopUp==Flug.First){
            e1DeadPopUp = Flug.notFirst;
            e1State = EnemyState.DEAD;
            //?????????????????????
            soundPlayerFX.playSFX(6);
            gameSceneController.enemyDead(1);
        }
        if(bs.getEnemy2Hp()==0 && e2DeadPopUp==Flug.First){
            e2DeadPopUp = Flug.notFirst;
            e2State = EnemyState.DEAD;
            //?????????????????????
            soundPlayerFX.playSFX(6);
            gameSceneController.enemyDead(2);
        }
        if(allies[0].getHp()==0 && a1DeadPopUp==Flug.First){
            a1DeadPopUp = Flug.notFirst;
            a1State = AllyState.DEAD;
            allyIndexList.remove(allyIndexList.indexOf("1"));
            //????????????????????????
            soundPlayerFX.playSFX(4);
            gameMenuController.setAlly1ToDead();
        }
        if(allies[1].getHp()==0 && a2DeadPopUp==Flug.First){
            a2DeadPopUp = Flug.notFirst;
            a2State = AllyState.DEAD;
            allyIndexList.remove(allyIndexList.indexOf("2"));
            soundPlayerFX.playSFX(4);
            gameMenuController.setAlly2ToDead();
        }
        if(allies[2].getHp()==0 && a3DeadPopUp==Flug.First){
            a3DeadPopUp = Flug.notFirst;
            a3State = AllyState.DEAD;
            allyIndexList.remove(allyIndexList.indexOf("3"));
            soundPlayerFX.playSFX(4);
            gameMenuController.setAlly3ToDead();
        }
        if(allies[3].getHp()==0 && a4DeadPopUp==Flug.First){
            a4DeadPopUp = Flug.notFirst;
            a4State = AllyState.DEAD;
            allyIndexList.remove(allyIndexList.indexOf("4"));
            soundPlayerFX.playSFX(4);
            gameMenuController.setAlly4ToDead();
        }
        //?????????????????????--------------------------------------------
        if(allies[0].getHp()!=0 && a1DeadPopUp==Flug.notFirst){
            a1DeadPopUp = Flug.First;
            a1State = AllyState.ALIVE;
            allyIndexList.add(0,"1");
            gameMenuController.setAlly1ToRevive();
        }
        if(allies[1].getHp()!=0 && a2DeadPopUp==Flug.notFirst){
            a2DeadPopUp = Flug.First;
            a2State = AllyState.ALIVE;
            allyIndexList.add(1,"2");
            gameMenuController.setAlly2ToRevive();
        }
        if(allies[2].getHp()!=0 && a3DeadPopUp==Flug.notFirst){
            a3DeadPopUp = Flug.First;
            a3State = AllyState.ALIVE;
            allyIndexList.add(2,"3");
            gameMenuController.setAlly3ToRevive();
        }
        if(allies[3].getHp()!=0 && a4DeadPopUp==Flug.notFirst){
            a4DeadPopUp = Flug.First;
            a4State = AllyState.ALIVE;
            allyIndexList.add(3,"4");
            gameMenuController.setAlly4ToRevive();
        }
    }
    //?????????????????????????????????
    private String selectTarget(){
        int index = new Random().nextInt(allyIndexList.size());
        String target = allyIndexList.get(index);
        return target;
    }
    private void moveToHardMode(){

        soundPlayerFX.playSFX(15);
        animation.startBattleAnm(canvas2);
        canvas.toFront();
        var anim = animation.handleFadeInAnimation(canvas);
        anim.setDelay(Duration.millis(2100));
        anim.setOnFinished(finish ->{
            app.addAndSendToGame(View.GAMESCENE2, allyName1String, 2);
            Window window = gameMainPane.getScene().getWindow();
            stage = (Stage) window;
            app.activate(View.GAMESCENE2,stage);
        });
        anim.play();
    }
    public void setLevelSelectVisualize(boolean v){
        hardModeTxt.setVisible(v);
        hai.setVisible(v);
        iie.setVisible(v);
        levelSelectBox.setVisible(v);
        levelSelectBox.requestFocus();
    }
    public void exKeyPressed(KeyEvent e){
        switch(e.getCode()){
            case Z:
            levelSelectBox.setOnKeyPressed(null);
            moveToHardMode();
            break;
            case X:
            Window window = gameMainPane.getScene().getWindow();
            stage = (Stage) window;
            app.activate(View.TITLE,stage);
            break;
            default:
            break;
        }
    }
}