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

    //サウンド
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
    //敵の種類
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
    //フラグ処理...
    private Flug e1DeadPopUp;
    private Flug e2DeadPopUp;
    private Flug a1DeadPopUp;
    private Flug a2DeadPopUp;
    private Flug a3DeadPopUp;
    private Flug a4DeadPopUp;
    private Flug e1Dead;
    private Flug e2Dead;
    //リスト
    private ArrayList<String> ally1Action; 
    private ArrayList<String> ally2Action;
    private ArrayList<String> ally3Action;
    private ArrayList<String> ally4Action;
    private ArrayList<String> actionBuffer;
    private List<String> allyIndexList;
    //キュー
    private Deque<ArrayList<String>> actionQueue;

    private Character[] allies = new Character[4];
    private BattleSystem bs;
    private List<String> eventFlug;
    private List<String> actionList;
    private String[] bufferedTxtArr;
    private int handleBuffernum = 0;
    private int handleTxtOrderNum = 0;

    public GameMainController(){
        
        //イベントのフラグと行動のテキストを格納するリスト
        eventFlug = new ArrayList<>();
        actionList = new ArrayList<>();
        //キャラクターのインスタンス
        allies[1] = new Ally2();
        allies[2] = new Ally3();
        allies[3] = new Ally4();
        //this.magicQuan = 2;
        this.allyName2String =allies[1].getName();
        this.allyName3String =allies[2].getName();
        this.allyName4String =allies[3].getName();

        //仲間の行動を文字列で格納する配列
        //0:誰が(番号) 1:at(物理攻撃)or mg(魔法)　2:e or a(敵か味方に) 3:対象番号
        ally1Action = new ArrayList<String>();
        ally2Action = new ArrayList<String>();
        ally3Action = new ArrayList<String>();
        ally4Action = new ArrayList<String>();
        actionBuffer = new ArrayList<String>();
        //最初は一人目を参照する
        this.allyNum = 1;
        this.allyQuan = 4;
        //ターン数
        this.turnNum = 1;
        this.s_state = STATE.MENU_OPERATION;
        this.t_state = TXT.NoBuffer;
        //行動格納のキュー
        actionQueue = new ArrayDeque<>();
        //フラグ
        e1Dead = Flug.First;
        e2Dead = Flug.First;
        a1DeadPopUp = Flug.First;
        a2DeadPopUp = Flug.First;
        a3DeadPopUp = Flug.First;
        a4DeadPopUp = Flug.First;
        //仲間のインデックス
        allyIndexList = new ArrayList<String>(Arrays.asList("1","2","3","4"));
    }

    //画面の状態を示す列挙型
    private enum STATE{
        MENU_OPERATION,
        TEXT_OPERATION,
        SELECT_COMPLETE,
        OPERATION_END;
    }
    
    //仲間の状態を表す列挙型
    private enum AllyState{
        ALIVE,DEAD;
    }
    //敵の状態を表す列挙型
    private enum EnemyState{
        ALIVE,DEAD;
    }
    //テキストの表示が連続して2以上の時に使う
    private enum TXT{
        Buffer,NoBuffer,First;
    }
    //フラグの列挙型
    private enum Flug{
        First,notFirst;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        //soundPlayerFX = new SoundPlayerFX();
        soundPlayerFX = SoundPlayerFX.getSoundPlayerClass();
        
        var gameOverAnm = animation.handleFadeInAnimation(canvas);
        //リスナーを追加
        isAllDead.addListener((observableValue,oldV,newV) ->{
            //ゲームオーバー処理
            if(newV == true){
            bgm.stop();
            //キーイベント消去
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
            gameSceneController.playTextAnimation("たたかいにやぶれた...");
            soundPlayerFX.playSFX(10);
            gameOverAnm.play();
            }
        });
        isWin.addListener(observation ->{
            //クリア処理
            bgm.stop();
            //キーイベント消去
            Scene scene = gameMainPane.getScene();
            scene.setOnKeyPressed(null);
            gameSceneController.setTextBoxVisualize(true);
            gameSceneController.playTextAnimation("たたかいにかった!");
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
        //最初はメニューを隠す
        gameMenuController.setMenuVisualize(false);
        hardModeTxt.setVisible(false);
        hai.setVisible(false);
        iie.setVisible(false);
        levelSelectBox.setVisible(false);
        //ハードモード用
        levelSelectBox.setFocusTraversable(true);
        levelSelectBox.setOnKeyPressed(event -> this.exKeyPressed(event));
        //アニメーション用の透明なキャンバスを追加(自動でtoFront()されている
        gameMainPane.getChildren().add(canvas);
        gameMainPane.getChildren().add(canvas2);
        //フェードアウトアニメーションを再生
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

        //テキストboxに表示させる
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
                case Z:  //決定ボタン
                soundPlayerFX.playSFX(3);
                if(gameSceneController.getTxtBIsVisible()){
                    gameSceneController.setTextBoxVisualize(false);
                    setTextInMenu(this.allyNum);
                    gameMenuController.setMenuVisualize(true,1);
                }
                else if(gameMenuController.getCursorPositionY()==Menu.FIGHT.positionY
                && gameMenuController.getCursorPositionX()==Menu.FIGHT.positionX){
                    //"誰が"をセット
                    actionBuffer.add(Integer.valueOf(this.allyNum).toString());
                    //"たたかう"をセット
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
                    //"誰が"をセット
                    actionBuffer.add(Integer.valueOf(this.allyNum).toString());
                    //魔法をセット();
                    actionBuffer.add("mg1");
                    if(this.allyNum == 1 || this.allyNum == 4){
                        actionBuffer.add("eall");
                        this.targetQuan = 1;
                        gameMenuController.setTargetName("てきぜんいん","","","");
                    }
                    else {
                        actionBuffer.add("e");
                        confirmAndSetTargetName();
                    }
                    gameMenuController.setMenuVisualize(true,4);
                }
                else if(gameMenuController.getCursorPositionY()==Menu.MAGIC2.positionY
                && gameMenuController.getCursorPositionX()==Menu.MAGIC2.positionX){
                    //"誰が"をセット
                    actionBuffer.add(Integer.valueOf(this.allyNum).toString());
                    //魔法をセット();
                    actionBuffer.add("mg2");
                    if(this.allyNum == 3){
                        actionBuffer.add("aall");
                        this.targetQuan = 1;
                        gameMenuController.setTargetName("みかたぜんいん","","","");
                    }
                    else{
                        actionBuffer.add("a");
                        this.targetQuan = this.allyQuan;
                        gameMenuController.setTargetName(allyName1String, allyName2String, allyName3String, allyName4String);
                    }
                    gameMenuController.setMenuVisualize(true,4);
                }
                //ターゲット選択
                //ターゲット選択後一つ次の仲間に更新
                else if(gameMenuController.getCursorPositionY()==Menu.TARGET1.positionY){
                    if(gameMenuController.getCursorPositionX()==Menu.TARGET1.positionX){
                        //ターゲット1にセット
                        actionBuffer.add("1");
                    }
                    else if(gameMenuController.getCursorPositionX()==Menu.TARGET2.positionX){
                        //ターゲット2にセット
                        actionBuffer.add("2");
                    }
                    else if(gameMenuController.getCursorPositionX()==Menu.TARGET3.positionX){
                        //ターゲット3にセット
                        actionBuffer.add("3");
                    }
                    else if(gameMenuController.getCursorPositionX()==Menu.TARGET4.positionX){
                        //ターゲット4にセット
                        actionBuffer.add("4");
                    }
                    //行動を格納
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
                    //選択中の仲間の魔法情報を表示
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
                    //選択中の仲間の魔法情報を表示
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
                case X:  //戻る
                if(gameSceneController.getTxtBIsVisible()==false){
                    soundPlayerFX.playSFX(2);
                    //カーソルがMenu.FIGHT.positionXのときにXが押されたら一つ前の仲間に更新する処理
                    //現在選択中の仲間の行動の配列の中身を消す
                    if(gameMenuController.getCursorPositionX()==Menu.FIGHT.positionX
                    && gameMenuController.getCursorPositionY()==Menu.FIGHT.positionY ){
                        updateAllyNum(-1);
                        setTextInMenu(this.allyNum);
                        //キューからも削除する
                        if(actionQueue.peek()!=null){
                            actionQueue.removeLast();
                        }
                    }
                    //配列を消す
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
    //-------------------------------------------Initializeの後に実行-----------------------------------------------//
    //前シーンから名前を受け取る HPとMPを表示する
    public void displayName(String name){
        gameMenuController.displayName(name,allyName2String,allyName3String,allyName4String);
        this.allyName1String = name;
        allies[0] = new Ally1(name);
        gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
        gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
    }
    //ゲーム画面のセッティング(敵の種類，BGM)
    public void setGameScene(int enemyKind){
        //bgmをセット
        bgm = soundPlayerFX.playBGM(0);
        //種類を格納
        this.enemyKind = enemyKind;
        //敵の数を生成
        Random random = new Random();
        int num = random.nextInt(2)+1;
        this.enemyQuan = num;
        if(enemyKind == 2){
            this.enemyQuan = 1;
            bgm = soundPlayerFX.playBGM(1);
        }
        //バトルシステムのインスタンスを生成する
        bs = new BattleSystem(allies[0],allies[1],allies[2],allies[3],enemyQuan,enemyKind);
        this.enemyNameString = bs.getEnemyKindName();
        //敵が一人なら
        if(this.enemyQuan==1){
            this.e1DeadPopUp = Flug.First;
            e2State = EnemyState.DEAD;
            e2DeadPopUp = Flug.notFirst;
        }
        else{
            this.e1DeadPopUp = Flug.First;
            this.e2DeadPopUp = Flug.First;
        }
        //背景をセット
        gameBackController.settingBackground(enemyKind);
        //敵を配置
        gameSceneController.putEnemyImg(this.enemyQuan,enemyKind);
        gameSceneController.playTextAnimation(String.format("%sが%d体あらわれた!",this.enemyNameString,this.enemyQuan));
        //BGMを再生
        bgm.play();
    }
    //-----------------------------------------------------------------------------------------------------------------//
    //敵の行動を格納する
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
    //メニューの名前と魔法をセットする
    public void setTextInMenu(int num){
        
        gameMenuController.displayNameMenu(allies[num-1].getName());
        gameMenuController.setMagicActionName(allies[num-1].getMagic1Name(), allies[num-1].getMagic2Name());
    }
    //選択中の仲間のインデックスを更新する(1:次-1:前)
    private void updateAllyNum(int n){
        switch(n){
            case 1:
            if(this.allyNum<4){
                //死んでたら再帰呼び出し
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
                //死んでたら再帰呼び出し
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
    //仲間の行動を順に格納
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
    //キューから順番に行動を取りだす
    private void manageTxtBoxOperation(){
        //System.out.println(actionQueue);
        ArrayList<String> arrlist = actionQueue.peek();
        if(arrlist != null){
            if(handleTxtOrderNum==0){
                //仲間の行動の場合
                if(arrlist.size()==4){
                    //キューからピークした時すでに死んでたら行動を破棄
                    int tmp = Integer.parseInt(arrlist.get(0));
                    if(allies[tmp-1].getHp()==0){
                        arrlist.clear();
                        actionQueue.poll();
                        bufferedTxtArr = null;
                        manageTxtBoxOperation();
                        return;
                    }
                    //敵が死んでたら対象を変える
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
                //敵の行動の場合
                else if(arrlist.size()==3){
                    //キューからピークした時すでに死んでたら行動を破棄
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
                        //味方が死んでたら対象を変える
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
                //ターン数更新
                if(this.turnNum!=(allyQuan+enemyQuan))
                    this.turnNum += 1;
            }
        }
        if(actionQueue.peek()==null){
            handleTxtOrderNum = 0;
            //バッファ中でない場合
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
    //Mapからフラグとテキストを受け取って前処理
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
            //攻撃宣言のサウンド再生
            soundPlayerFX.playSFX(0);
            //gameSceneController.playTextAnimation(actionList.get(i));
            gameSceneController.setText(actionList.get(i));
            return;
        }
        else if(eventFlug.get(i)=="atae"){
            //敵攻撃宣言のサウンド再生
            soundPlayerFX.playSFX(8);
            //
            gameSceneController.setText(actionList.get(i));
            return;
        }
        else if(eventFlug.get(i)=="mg"){
            //魔法宣言のサウンドを再生
            soundPlayerFX.playSFX(11);
            //gameSceneController.playTextAnimation(actionList.get(i));
            gameSceneController.setText(actionList.get(i));
            return;
        }
        else if(eventFlug.get(i)=="mge"){
            //敵魔法宣言のサウンド再生
            soundPlayerFX.playSFX(18);
            gameSceneController.setText(actionList.get(i));
        }
        else if(eventFlug.get(i)=="talka"){
            //トーク宣言のサウンド(?)
            soundPlayerFX.playSFX(13);
            gameSceneController.setText(actionList.get(i));
        }
        else if(eventFlug.get(i)=="at"){
            //攻撃サウンドを再生
            soundPlayerFX.playSFX(7);
            gameSceneController.enemyDamaged(enemyTargetTmp);
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
                //gameSceneController.playTextAnimation(bufferedTxtArr[0]);
            }
            else{
                gameSceneController.setText(actionList.get(i));
                //HPMP更新
                gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
                gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
                confirmCharacterStatus();
            }
            return;
        }
        else if(eventFlug.get(i)=="ate"){
            //敵攻撃サウンドを再生
            soundPlayerFX.playSFX(1);
            gameMenuController.playAllyDamagedAnm();
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
                //gameSceneController.playTextAnimation(bufferedTxtArr[0]);
            }
            else {
                gameSceneController.setText(actionList.get(i));
                //HPMP更新
                gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
                gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
                confirmCharacterStatus();
            }
            return;
        }
        else if(eventFlug.get(i) =="mg1" || eventFlug.get(i) =="mg1_all"){
            //まほうアニメーション再生→setOnfinishedでdidplayBufferdTxt
            //gameSceneController.playTextAnimation(actionList.get(i));
            //仮
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
                //HPMP更新
                gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
                gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
                confirmCharacterStatus();
            }
            return;
        }
        else if(eventFlug.get(i) =="mg2"){
            soundPlayerFX.playSFX(9);
            gameSceneController.playHealAnm();
            //まほうアニメーション再生→setOnfinishedでdidplayBufferdTxt
            //gameSceneController.playTextAnimation(actionList.get(i));
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
            }
            else {
                gameSceneController.setText(actionList.get(i));
                //HPMP更新
                gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
                gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
                confirmCharacterStatus();
            }
            return;
        }
        else if(eventFlug.get(i) == "mgea"){
            //敵から魔法攻撃を受けたとき
            soundPlayerFX.playSFX(1);
            gameMenuController.playAllyDamagedAnm();
            //まほうアニメーション再生→setOnfinishedでdidplayBufferdTxt
            //gameSceneController.playTextAnimation(actionList.get(i));
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
            }
            else {
                gameSceneController.setText(actionList.get(i));
                //HPMP更新
                gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
                gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
                confirmCharacterStatus();
            }
            return;
        }
        else if(eventFlug.get(i) =="talk"){
            //トーク
            if(bufferedTxtArr.length>1){
                t_state = TXT.Buffer;
                gameSceneController.setText(bufferedTxtArr[0]);
            }
            else gameSceneController.setText(actionList.get(i));
            return;
        }
        else if(eventFlug.get(i)=="fail"){
            //からぶり
            soundPlayerFX.playSFX(12);
            gameSceneController.setText(actionList.get(i));
        }
    }
    private void displayBufferdTxt(int i){
        if(i<bufferedTxtArr.length){
            //gameSceneController.playTextAnimation(bufferedTxtArr[i]);
            gameSceneController.setText(bufferedTxtArr[i]);
            //HPMP更新
            gameMenuController.setAlliesHP(allies[0].getHp(), allies[1].getHp(), allies[2].getHp(), allies[3].getHp());
            gameMenuController.setAlliesMP(allies[0].getMp(), allies[1].getMp(), allies[2].getMp(), allies[3].getMp());
            confirmCharacterStatus();
        }
        else{
            t_state = TXT.NoBuffer;
            this.handleBuffernum = 0;
            this.handleTxtOrderNum = 0;
            //バッファ中のメニュー移行条件
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
    //主に死んだとき
    private void confirmCharacterStatus(){
        if(bs.getEnemy1Hp()==0 && e1DeadPopUp==Flug.First){
            e1DeadPopUp = Flug.notFirst;
            e1State = EnemyState.DEAD;
            //敵死亡サウンド
            soundPlayerFX.playSFX(6);
            gameSceneController.enemyDead(1);
        }
        if(bs.getEnemy2Hp()==0 && e2DeadPopUp==Flug.First){
            e2DeadPopUp = Flug.notFirst;
            e2State = EnemyState.DEAD;
            //敵死亡サウンド
            soundPlayerFX.playSFX(6);
            gameSceneController.enemyDead(2);
        }
        if(allies[0].getHp()==0 && a1DeadPopUp==Flug.First){
            a1DeadPopUp = Flug.notFirst;
            a1State = AllyState.DEAD;
            allyIndexList.remove(allyIndexList.indexOf("1"));
            //味方死亡サウンド
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
        //生き返ったとき--------------------------------------------
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
    //敵が攻撃対象を選択する
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