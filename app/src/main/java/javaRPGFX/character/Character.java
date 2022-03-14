package javaRPGFX.character;
import java.util.Random;

public abstract class Character{

    private String name;
    private String magic1Name;
    private String magic2Name;
    private int ap;         //攻撃力
    private int hp;         //体力
    private int maxHp;      //体力最大値
    private int maxMp;      //MP最大値
    private int mp;         //マジックポイント
    private String tmp;

    public String attack(Character c){              //攻撃する時のメソッド
        tmp = "";
        Random rand = new Random();
        int dmgVal = rand.nextInt(20) + this.getAp()-5;        //ダメージ量
        if(c.getHp() != 0){
            //System.out.println(this.getName()+"のこうげき!");
            if(getLuck()){
                tmp = c.damaged(c,dmgVal);
                //System.out.println(String.format("%sに%dのダメージ!",c.getName(),dmgVal));
            }
            else{
                tmp = "からぶりー!";
                //System.out.println("からぶりー!");
            }
        }
        else {
            tmp = "エラー";
            //System.out.println("エラー");
        }
        return tmp;
    }

    public String attackAll(Character c[], int val){           //全体攻撃のメソッド
        tmp = "";
        Random rand = new Random();
        int dmgVal = rand.nextInt(30) + this.getAp();        //ダメージ量
        if (val != 0){
            dmgVal = val;
        }
        for (Character opponent : c){
            if(opponent.getHp() != 0){
                if(getLuck()){
                    tmp += opponent.damaged(opponent,dmgVal);
                    //System.out.println(String.format("%sに%dのダメージ!",opponent.getName(),dmgVal));
                }
                else if(getLuck() == false){
                    tmp +="からぶりー! ";
                    //System.out.println("からぶりー!");
                }
            }
        }
        return tmp;
    }

    public void heal(int healValue){             //回復する時のメソッド
        if((this.hp+healValue) < this.maxHp){
            setHp(this.hp+healValue);
        }
        else setHp(this.maxHp);
    }

    public void healMP(int healValue){          //MP回復のメソッド
        if((this.mp+healValue) < this.maxMp){
            setMp(this.mp+healValue);
        }
        else setMp(this.maxMp);
    }
    public void show(){                         //ステータス表示のメソッド
        System.out.println(String.format("名前: %s,HP: %d, MP: %d,AP: %d",this.name,this.hp,this.mp,this.ap));
    }

    public String damaged(Character chara,int dmgValue){                 //攻撃を受けた時のメソッド
        tmp = "";
        if(this.hp-dmgValue>0){
            //System.out.println(String.format("%sに%dのダメージ!",chara.getName(),dmgValue));
            tmp = String.format("%sに%dのダメージ! ",chara.getName(),dmgValue);
            setHp(this.hp - dmgValue);
        }
        else {
            tmp = String.format("%sに%dのダメージ! ",chara.getName(),dmgValue);
            setHp(0);
            tmp += String.format("%sはちからつきた. ",chara.getName());
        }
        return tmp;
    }

    public abstract String magic1(Character c);              //魔法の抽象メソッド
    public abstract String magic1(Character c[]);            //魔法の抽象メソッド
    public abstract String magic2(Character c);              //魔法の抽象メソッド
    public abstract String magic2(Character c[]);            //魔法の抽象メソッド
    public abstract boolean getLuck();          //運を取得するメソッド
    public abstract String talk();
    public abstract String getMagicInfo1Txt();
    public abstract String getMagicInfo2Txt();

    public String getName(){                //名前ゲッター
        return this.name;
    }
    public String getMagic1Name(){          //魔法の名前ゲッター
        return this.magic1Name;
    }
    public String getMagic2Name(){          //魔法の名前ゲッター
        return this.magic2Name;
    }
    public void setName(String name){       //名前セッター
        this.name = name;
    }
    public void setMagic1Name(String magicName){//魔法の名前セッター
        this.magic1Name = magicName;
    }
    public void setMagic2Name(String magicName){//魔法の名前セッター
        this.magic2Name = magicName;
    }
    public int getMp(){                     //MPゲッター
        return this.mp;
    }
    public void setMp(int mp){              //MPセッター
        this.mp = mp;
    }
    public int getHp(){                     //HPゲッター
        return this.hp;
    }
    public void setHp(int hp){              //HPセッター
        this.hp = hp;
    }
    public int getAp(){                     //APゲッター
        return this.ap;
    }
    public void setAp(int ap){              //APセッター
        this.ap = ap;
    }
    public void setMaxHp(int maxHp){        //体力最大値セット
        this.maxHp = maxHp;
    }
    public void setMaxMp(int maxMp){        //MP最大値セット
        this.maxMp = maxMp;
    }
    public String getKindName(){
        return null;
    }

    public Character(){ } //コンストラクタ
}
