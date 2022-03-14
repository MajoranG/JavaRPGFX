package javaRPGFX.character.allies;
import javaRPGFX.character.Character;
import java.util.Random;

public class Ally4 extends Character{
    private int mpCost;     //消費MP
    private final String magicName1 = "バーストストーム";
    private final String magicInfo1 = "てきぜんいんに\nしょうひMP50";
    private final String magicName2 = "リバイブ";
    private final String magicInfo2 = "みかたひとりに\nしょうひMP15";
    private String tmp;

    @Override
    public String magic1(Character c[]){
        tmp="";
        this.mpCost = 50;
        if((super.getMp() - mpCost) >= 0){
            super.setMp(super.getMp() - mpCost);
            tmp += super.attackAll(c,123);
        }
        else {
            //System.out.println("しかしMPがたりなかった．");
            tmp="しかしMPがたりなかった.";
        }
        return tmp;
    }
    @Override
    public String magic2(Character c){
        tmp ="";
        this.mpCost = 15;
        Random random = new Random();
        int healValue = random.nextInt(30) + 100;
        if((super.getMp() - mpCost) > 0){
            super.setMp(super.getMp() - mpCost);
            //System.out.println(String.format("%sのHPが%dかいふくする!",c.getName(),healValue));
            if(c.getHp()==0){
                c.heal(healValue);
                tmp += String.format("%sはカムバックした!",c.getName());
            }
            else tmp += "しかしなにもおこらなかった.";
        }
        else {
            //System.out.println("しかしMPがたりなかった．");
            tmp="しかしMPがたりなかった.";
        }
        return tmp;
    }

    @Override public boolean getLuck(){
        Random random = new Random();
        int tmp = random.nextInt(50);
        if(tmp < 5) return false;
        else return true;
    }
    public String getMagicInfo1Txt(){
        return this.magicInfo1;
    }
    public String getMagicInfo2Txt(){
        return this.magicInfo2;
    }
    public Ally4(){
        super.setName("プー");
        super.setMagic1Name(magicName1);
        super.setMagic2Name(magicName2);
        super.setHp(200);
        super.setAp(10);
        super.setMp(80);
        super.setMaxHp(200);
        super.setMaxMp(80);
    }
    public Ally4(String name){
        super.setName(name);
        super.setMagic1Name(magicName1);
        super.setMagic2Name(magicName2);
        super.setHp(200);
        super.setAp(10);
        super.setMp(80);
        super.setMaxHp(200);
        super.setMaxMp(80);
    }

    @Override
    public String magic1(Character c) {
        return null;
    }
    @Override
    public String magic2(Character[] c) {
        return null;
    }
    @Override
    public String talk() {
        return null;
    }
}