package javaRPGFX.character.allies;
import javaRPGFX.character.Character;
import java.util.Random;

public class Ally1 extends Character{
    private int mpCost;     //消費MP
    private final String magicName1 = "キアイ";
    private final String magicInfo1 = "てきぜんいんに\nしょうひMP10";
    private final String magicName2 = "ライフアップ";
    private final String magicInfo2 = "みかたひとりに\nしょうひMP5";
    private String tmp;

    @Override
    public String magic1(Character c[]){
        tmp="";
        this.mpCost = 10;
        //System.out.println(String.format("%sはPKキアイαをこころみた!",super.getName()));
        if((super.getMp() - mpCost) >= 0){
            super.setMp(super.getMp() - mpCost);
            tmp += super.attackAll(c,0);
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
        this.mpCost = 5;
        //System.out.println(String.format("%sはライフアップβをこころみた!",super.getName()));
        //tmp = String.format("%sはライフアップβをこころみた!\n",super.getName());
        Random random = new Random();
        int healValue = random.nextInt(30) + 40;
        if((super.getMp() - mpCost) > 0){
            if(c.getHp()!=0){
                super.setMp(super.getMp() - mpCost);
                tmp += String.format("%sのHPが%dかいふくする!",c.getName(),healValue);
                c.heal(healValue);
            }
            else tmp+= "しかしなにもおこらなかった.";
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
    public Ally1(){
        super.setName("NATH");
        super.setMagic1Name(magicName1);
        super.setMagic2Name(magicName2);
        super.setHp(250);
        super.setAp(35);
        super.setMp(50);
        super.setMaxHp(250);
        super.setMaxMp(50);
    }
    public Ally1(String name){
        super.setName(name);
        super.setMagic1Name(magicName1);
        super.setMagic2Name(magicName2);
        super.setHp(250);
        super.setAp(35);
        super.setMp(50);
        super.setMaxHp(250);
        super.setMaxMp(50);
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