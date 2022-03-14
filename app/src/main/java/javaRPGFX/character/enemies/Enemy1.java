package javaRPGFX.character.enemies;
import javaRPGFX.character.Character;
import java.util.Random;

public class Enemy1 extends Character{
    private int mpCost;     //消費MP
    private String tmp;
    private final String magicName1 = "かえんほうしゃ";
    private final String kindName = "へんなつっぱり";

    @Override
    public String magic1(Character c[]){
        tmp ="";
        this.mpCost = 5;
        //System.out.println(String.format("%sはPKファイヤーαをこころみた!",super.getName()));
        if((super.getMp() - mpCost) >= 0){
            super.setMp(super.getMp() - mpCost);
            tmp += super.attackAll(c,0);
        }
        else {
            //System.out.println("しかしMPがたりなかった．");
            tmp = "しかしMPがたりなかった.";
        }
        return tmp;
    }

    public void magic2(){

    }

    @Override public boolean getLuck(){
        Random random = new Random();
        int tmp = random.nextInt(50);
        if(tmp < 10) return false;
        else return true;
    }

    @Override
    public String talk(){                             //敵はしゃべれる
        tmp = "";
        //System.out.println(String.format("てきの%sはとうとつにしゃべりはじめた．",super.getName()));
        //tmp += String.format("てきの%sはとうとつにしゃべりはじめた.",super.getName());
        Random random = new Random();
        int a = random.nextInt(10);
        switch (a){
            case 0: tmp+="マッキンレーでもマッキンリーでもどっちでもいい。 好きなほうを使いなさい。 どう頭を働かせるかわかりませんが。"; break;
            case 1: tmp+="いっちいちいち"; break;
            case 2: tmp+="こんなに美しい写真を見たことがありませぇん。"; break;
            case 3: tmp+="勉強して…しなさいよ(女)"; break;
            case 4: tmp+=("ゆっくりゆっくり冷静に考えてみ"); break;
            case 5: tmp+=("誰でもわかる小学生でもわかる大丈夫か"); break;
            case 6: tmp+=("大事なとっても大事な"); break;
            case 7: tmp+=("今日は晴れてて、 天気はなにで"); break;
            default: tmp+=("オトト"); break;
        }
        return tmp;
    }

    public Enemy1(){
        super.setName("へんなつっぱり");
        super.setMagic1Name(magicName1);
        super.setHp(300);
        super.setAp(70);
        super.setMp(5);
        super.setMaxHp(300);
        super.setMaxMp(5);
    }
    public Enemy1(String name){
        super.setName(name);
        super.setMagic1Name(magicName1);
        super.setHp(300);
        super.setAp(70);
        super.setMp(5);
        super.setMaxHp(300);
        super.setMaxMp(5);
    }

    @Override
    public String magic1(Character c) {
        return null;
    }

    @Override
    public String magic2(Character c) {
        return null;
    }

    @Override
    public String magic2(Character[] c) {
        return null;
    }

    @Override
    public String getMagicInfo1Txt() {
        return this.magicName1;
    }

    @Override
    public String getMagicInfo2Txt() {
        return null;
    }
    
    @Override
    public String getKindName() {
        return this.kindName;
    }
}