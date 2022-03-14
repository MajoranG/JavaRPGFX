package javaRPGFX;

import javaRPGFX.gui2.GUILaunch;

public class GameLauncher {
    
    public static void main(String[] args) {
        try {
            GUILaunch.main(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}