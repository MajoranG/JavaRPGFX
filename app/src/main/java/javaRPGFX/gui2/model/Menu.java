package javaRPGFX.gui2.model;

public enum Menu {
    FIGHT(0,0),
    MAGIC(0,26),MAGIC1(180,-12),MAGIC2(180,14),
    TARGET1(90,55),TARGET2(90+87*1,55),TARGET3(90+87*2,55),TARGET4(90+87*3,55);

    public final double positionX;
    public final double positionY;

    Menu(double positionX,double positionY){
        this.positionX = positionX;
        this.positionY = positionY;
    }
}