package javaRPGFX.character.allies;
import java.util.Random;
import javaRPGFX.character.Character;

public class Ally3 extends Character{
    private int mpCost;     //消費MP
    private final String magicName1 = "いあいぎり";
    private final String magicInfo1 = "てきひとりに\nしょうひMP10";
    private final String magicName2 = "ヒールオール";
    private final String magicInfo2 = "みかたぜんいんに\nしょうひMP20";
    private String tmp;

    @Override
    public String magic1(Character c){
        tmp = "";
        this.mpCost = 10;
        if((super.getMp() - mpCost) >= 0){
            super.setMp(super.getMp() - mpCost);
            tmp += c.damaged(c,60);
        }
        else {
            tmp ="しかしMPがたりなかった.";
            //System.out.println("しかしMPがたりなかった．");
        }
        return tmp;
    }

    @Override
    public String magic2(Character c[]){
        tmp = "";
        this.mpCost = 20;
        Random random = new Random();
        int healValue = random.nextInt(20) + 30;
        if((super.getMp() - mpCost) >= 0){
            super.setMp(super.getMp() - mpCost);
            for(Character target : c){
                if(target.getHp()!=0){
                    target.heal(healValue);
                    tmp += String.format("%sのHPが%dかいふくする! ",target.getName(),healValue);
                }
            }
        }
        else tmp ="しかしMPがたりなかった.";
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
    public Ally3(){
        super.setName("えいゆう");
        super.setMagic1Name(magicName1);
        super.setMagic2Name(magicName2);
        super.setHp(250);
        super.setAp(30);
        super.setMp(130);
        super.setMaxHp(250);
        super.setMaxMp(100);
    }
    public Ally3(String name){
        super.setName(name);
        super.setMagic1Name(magicName1);
        super.setMagic2Name(magicName2);
        super.setHp(250);
        super.setAp(30);
        super.setMp(130);
        super.setMaxHp(250);
        super.setMaxMp(100);
    }

    @Override
    public String magic1(Character[] c) {
        return null;
    }

    @Override
    public String magic2(Character c) {
        return null;
    }

    @Override
    public String talk() {
        return null;
    }
}
