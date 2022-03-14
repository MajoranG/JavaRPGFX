package javaRPGFX.gui2;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javaRPGFX.character.Character;

import javaRPGFX.character.enemies.Enemy1;
import javaRPGFX.character.enemies.Enemy2;

public class BattleSystem {

    private Map<String, String> orderAndSenario = new LinkedHashMap<String, String>();
    private Map<String, String> orderAndSenarioE = new LinkedHashMap<String, String>();
    private String[] enemyActionArr;

    private Character[] enemies;
    private Character[] allies = new Character[4];

    public BattleSystem(Character a1,Character a2,Character a3,Character a4, int enemyNum,int enemyKind){
        this.allies[0] = a1;
        this.allies[1] = a2;
        this.allies[2] = a3;
        this.allies[3] = a4;
        enemies = new Character[enemyNum];
        if(enemyKind == 1){
            if(enemyNum == 1){
                enemies[0] = new Enemy1();
            }
            else{
                for(int i=0;i<enemyNum;i++){
                    enemies[i] = new Enemy1("つっぱり"+Integer.valueOf(i+1).toString());
                }
            }
        }
        else if(enemyKind == 2){
            enemies[0] = new Enemy2();
        }
        //敵の行動を格納
        enemyActionArr = new String[3];
    }
    //0:誰が(番号) 1:at(物理攻撃)or mg(魔法)　2:e or a(敵か味方に) 3:対象番号
    //key:: no==何もしない ata==攻撃宣言 at==攻撃エフェクト mg==魔法宣言 mg1...(魔法エフェクト)
    public Map<String,String> getSenarioAndOrder(String arr[]){
        orderAndSenario.clear();
        int anum = Integer.parseInt(arr[0])-1;
        int tnum = Integer.parseInt(arr[3])-1;
        if(arr[1] == "at"){
            orderAndSenario.put("ata",allies[anum].getName()+"のこうげき!\n");
            String tmpC = allies[anum].attack(enemies[tnum]);
            if(tmpC == "からぶりー!"){
                orderAndSenario.put("fail",tmpC);
            }
            else orderAndSenario.put("at",tmpC);
        }
        else if(arr[1]=="mg1"){
            orderAndSenario.put("mg",String.format("%sは%sをこころみた!\n",allies[anum].getName(),allies[anum].getMagic1Name()));
            if(arr[2]=="e"){
                String tmp = allies[anum].magic1(enemies[tnum]);
                if(tmp == "しかしMPがたりなかった."){
                    orderAndSenario.put("fail",tmp);
                }
                else orderAndSenario.put("mg1",tmp);
            }
            else if(arr[2]=="eall"){
                String tmp = allies[anum].magic1(enemies);
                if(tmp=="しかしMPがたりなかった."){
                    orderAndSenario.put("fail",tmp);
                }
                else orderAndSenario.put("mg1_all",tmp);
            }
            else if(arr[2]=="a"){
                String tmp = allies[anum].magic1(allies[tnum]);
                if(tmp == "しかしMPがたりなかった."){
                    orderAndSenario.put("fail",tmp);
                }
                else orderAndSenario.put("mg1",tmp);
            }
            else if(arr[2]=="aall"){
                String tmp = allies[anum].magic1(allies);
                if(tmp=="しかしMPがたりなかった."){
                    orderAndSenario.put("fail",tmp);
                }
                else orderAndSenario.put("mg1",tmp);
            }
        }
        else if(arr[1]=="mg2"){
            orderAndSenario.put("mg",String.format("%sは%sをこころみた!\n",allies[anum].getName(),allies[anum].getMagic2Name()));
            if(arr[2]=="e"){
                String tmp = allies[anum].magic2(enemies[tnum]);
                if(tmp=="しかしMPがたりなかった."){
                    orderAndSenario.put("fail",tmp);
                }
                else orderAndSenario.put("mg2",tmp);
            }
            else if(arr[2]=="eall"){
                String tmp = allies[anum].magic2(enemies);
                if(tmp=="しかしMPがたりなかった."){
                    orderAndSenario.put("fail",tmp);
                }
                else orderAndSenario.put("mg2_all",tmp);
            }
            else if(arr[2]=="a"){
                String tmp = allies[anum].magic2(allies[tnum]);
                if(tmp=="しかしMPがたりなかった."){
                    orderAndSenario.put("fail",tmp);
                }
                else orderAndSenario.put("mg2",tmp);
            }
            else if(arr[2]=="aall"){
                String tmp = allies[anum].magic2(allies);
                if(tmp=="しかしMPがたりなかった."){
                    orderAndSenario.put("fail",tmp);
                }
                else orderAndSenario.put("mg2",tmp);
            }
        }
        return orderAndSenario;
    }
    //key: atae:敵攻撃宣言 ate: 敵攻撃フラグ mge:敵魔法宣言 mgea:魔法攻撃 talka:トーク宣言 talk:トークフラグ
    public Map<String,String> getEnemySenarioAndOrder(int enemyNum,String[] tmpArr){
        orderAndSenarioE.clear();
        //var tmpArr = generateEnemyAction(enemies[enemyNum-1]);
        int tnum = Integer.parseInt(tmpArr[2])-1;
        //仮
        if(tmpArr[0]=="mg1"){
            tmpArr[2] = "aall";
        }
        if(tmpArr[0]=="at"){
            orderAndSenarioE.put("atae",enemies[enemyNum-1].getName()+"のこうげき!\n");
            String tmpC = enemies[enemyNum-1].attack(allies[tnum]);
            if(tmpC == "からぶりー!"){
                orderAndSenarioE.put("fail",tmpC);
            }
            else orderAndSenarioE.put("ate",tmpC);
        }
        else if(tmpArr[0]=="mg1"){
            orderAndSenarioE.put("mge",String.format("%sは%sをこころみた!\n",enemies[enemyNum-1].getName(),enemies[enemyNum-1].getMagic1Name()));
            if(tmpArr[2]=="aall"){
                String tmpC = enemies[enemyNum-1].magic1(allies);
                if(tmpC=="しかしMPがたりなかった."){
                    orderAndSenarioE.put("fail",tmpC);
                }
                else orderAndSenarioE.put("mgea",tmpC);
            }
            else {
                String tmpC = enemies[enemyNum-1].magic1(allies[tnum]);
                if(tmpC=="しかしMPがたりなかった."){
                    orderAndSenarioE.put("fail",tmpC);
                }
                else orderAndSenarioE.put("mgea",tmpC);
            }
        }
        else if(tmpArr[0]=="talk"){
            orderAndSenarioE.put("talka",String.format("てきの%sはとうとつにしゃべりはじめた.",enemies[enemyNum-1].getName()));
            orderAndSenarioE.put("talk",enemies[enemyNum-1].talk());
        }
        return orderAndSenarioE;
    }
    //敵の行動生成
    public String[] generateEnemyAction(int enemyNum,String target){
        
        enemyActionArr[0] = enemies[enemyNum-1].getLuck() ? "at" : "mg1";
        enemyActionArr[1] = "a";
        enemyActionArr[2] = target;
        Random random = new Random();
        int talkFulg = random.nextInt(50);
        if(talkFulg>40){
            enemyActionArr[0] = "talk";
        }
        return enemyActionArr;
    }
    public String getEnemy1Name(){
        return this.enemies[0].getName();
    }
    public String getEnemy2Name(){
        if(this.enemies.length < 2) return "";
        else return this.enemies[1].getName();
    }
    public int getEnemy1Hp(){
        return this.enemies[0].getHp();
    }
    public int getEnemy2Hp(){
        if(this.enemies.length == 1) return 0;
        else return this.enemies[1].getHp();
    }
    public String getEnemyKindName(){
        return enemies[0].getKindName();
    }
}