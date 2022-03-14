package javaRPGFX.gui2.model;
//fxml location
public enum View {
    TITLE("/TitleMenu.fxml"),
    TITLESCENE2("/titleScene2.fxml"),
    GAMESCENE1("/gameMain.fxml"),
    GAMESCENE2("/gameMain.fxml"),
    GAMEOVER("/gameOver.fxml"),
    GAMECLEAR("/gameClear.fxml");

    public final String fxmlLocation;

    View(String location){
        this.fxmlLocation = "/fxml" + location;
    }
}