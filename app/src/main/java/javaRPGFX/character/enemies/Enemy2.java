package javaRPGFX.character.enemies;
import javaRPGFX.character.Character;
import java.util.Random;

public class Enemy2 extends Character{
    private int mpCost;     //消費MP
    private String tmp;
    private final String magicName1 = "バーストストーム";
    private final String kindName = "ゾンビおとこ";
    
    @Override
    public String magic1(Character c[]){
        tmp ="";
        this.mpCost = 10;
        //System.out.println(String.format("%sはPKスターストームをこころみた!",super.getName()));
        if((super.getMp() - mpCost) > 0){
            super.setMp(super.getMp() - mpCost);
            tmp += super.attackAll(c,153);
        }
        else {
            tmp = "しかしMPがたりなかった.";
            //System.out.println("しかしMPがたりなかった.");
        }
        return tmp;
    }

    public void magic2(){

    }
    @Override
    public String talk(){                             //敵はしゃべれる
        tmp="";
        //System.out.println(String.format("てきの%sはとうとつにしゃべりはじめた．",super.getName()));
        tmp =String.format("てきの%sはとうとつにしゃべりはじめた. ",super.getName());
        Random random = new Random();
        int a = random.nextInt(10);
        switch (a){
            case 0: tmp+=("よっぽどのことがない限り. よっぽどっていうか… あの…わかるよね？"); break;
            case 1: tmp+=("にがさないよ。 ギェッギェッギェッギェッ！"); break;
            case 2: tmp+=("頭ン中ゆっくりゆっくりゆっくり冷静に"); break;
            case 3: tmp+=("おはようございま…せん。"); break;
            case 4: tmp+=("あなたもゾンビさんでちゅか？"); break;
            case 5: tmp+=("どいつもこいつもだ！…そう思わないか？"); break;
            case 6: tmp+=("おれは今、 ヒマでヒマで手がはなせねえのよ。"); break;
            case 7: tmp+=("あだぁだぁデッド"); break;
            default: tmp+=("ヘイヘーイ"); break;
        }
        return tmp;
    }
    @Override public boolean getLuck(){
        Random random = new Random();
        int tmp = random.nextInt(50);
        if(tmp < 7) return false;
        else return true;
    }

    public Enemy2(){
        super.setName("ゾンビおとこ");
        super.setMagic1Name(magicName1);
        super.setHp(1500);
        super.setAp(99);
        super.setMp(100);
        super.setMaxHp(1500);
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
        return null;
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
