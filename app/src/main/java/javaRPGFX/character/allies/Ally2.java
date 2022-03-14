package javaRPGFX.character.allies;
import javaRPGFX.character.Character;
import java.util.Random;

public class Ally2 extends Character {
    private int mpCost;     //消費MP
    private final String magicName1 = "スノーストーム";
    private final String magicInfo1 = "てきひとりに\nしょうひMP10";
    private final String magicName2 = "MPヒール";
    private final String magicInfo2 = "みかたひとりに\nしょうひMP0";
    private String tmp;

    @Override
    public String magic1(Character c){
        tmp = "";
        this.mpCost = 10;
        //System.out.println(String.format("%sはブリザドをこころみた!",super.getName()));
        if((super.getMp() - mpCost) >= 0){
            super.setMp(super.getMp() - mpCost);
            tmp = c.damaged(c,80);
        }
        else {
            tmp ="しかしMPがたりなかった.";
            //System.out.println("しかしMPがたりなかった．");
        }
        return tmp;
    }

    @Override
    public String magic2(Character c){
        tmp = "";
        this.mpCost = 0;
        //System.out.println(String.format("%sはエリクサーをつかった!",super.getName()));
        Random random = new Random();
        int healValue = random.nextInt(10) + 5;
        if((super.getMp() - mpCost) >= 0){
            super.setMp(super.getMp() - mpCost);
            //System.out.println(String.format("%sのMPが%dかいふくする!",c.getName(),healValue));
            tmp = String.format("%sのMPが%dかいふくする!",c.getName(),healValue);
            c.healMP(healValue);
        }
        else System.out.println("しかしMPがたりないわけがなかった.");
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
    public Ally2(){
        super.setName("クロウ");
        super.setMagic1Name(magicName1);
        super.setMagic2Name(magicName2);
        super.setHp(300);
        super.setAp(50);
        super.setMp(100);
        super.setMaxHp(300);
        super.setMaxMp(20);
    }
    public Ally2(String name){
        super.setName(name);
        super.setMagic1Name(magicName1);
        super.setMagic2Name(magicName2);
        super.setHp(300);
        super.setAp(50);
        super.setMp(100);
        super.setMaxHp(300);
        super.setMaxMp(20);
    }

    @Override
    public String magic1(Character[] c) {
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
