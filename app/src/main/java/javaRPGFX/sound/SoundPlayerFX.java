//java.lang.IllegalAccessError: class com.sun.media.jfxmediaimpl.NativeMediaManager (in unnamed module @0x5e4d95b4) cannot access class com.sun.glass.utils.NativeLibLoader (in module javafx.graphics) because module javafx.graphics does not export com.sun.glass.utils to unnamed module
package javaRPGFX.sound;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundPlayerFX {
    
    private ArrayList<File> sfx;
    private ArrayList<File> bgm;
    private ArrayList<Path> sfxPathObj;
    private ArrayList<Path> bgmPathObj;
    private File[] sfxfiles;
    private File[] bgmfiles;
    private String path;
    private URI pathURI;
    private final String sfxpath = "sfx/";
    private final String bgmpath = "bgm/";

    private Media media;
    private MediaPlayer mediaPlayer;

    private static SoundPlayerFX soundPlayerFXclass;

    public SoundPlayerFX(){
        soundPlayerFXclass = this;

        sfx = new ArrayList<File>();
        bgm = new ArrayList<File>();
        
        try {
            sfxfiles = getResourceFolder(sfxpath);
            bgmfiles = getResourceFolder(bgmpath);
            if(sfxfiles == null){
                List<Path> list  = getPathInJar(sfxpath);
                sfxPathObj = new ArrayList<Path>(list);
                System.out.println("Sound effect loaded");
            }
            if(bgmfiles == null){
                List<Path> list  = getPathInJar(bgmpath);
                bgmPathObj = new ArrayList<Path>(list);
                System.out.println("BGM loaded");
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(sfxfiles != null){
            for(File file : sfxfiles){
                sfx.add(file);
            }
            System.out.println("Sound effect loaded");
        }
        if(bgmfiles != null){
            for(File file : bgmfiles){
                bgm.add(file);
            }
            System.out.println("BGM loaded");
        }
    }

    public MediaPlayer playBGM(int index){
        if(bgmfiles == null){
            URL resourceURL  = getClass().getResource("/"+bgmPathObj.get(index).toString());
            media = new Media(resourceURL.toExternalForm());
        }
        else media = new Media(bgm.get(index).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(-1);
        return mediaPlayer;
    }
    public void playSFX(int index){
        if(sfxfiles == null){
            URL resourceURL  = getClass().getResource("/"+sfxPathObj.get(index).toString());
            media = new Media(resourceURL.toExternalForm());
        }
        else media = new Media(sfx.get(index).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnError(()->{
            System.out.println("SFXが再生できませんでした!!");
            System.err.println(mediaPlayer.getError().getMessage());
        });
        mediaPlayer.setOnReady(()->{
            mediaPlayer.play();
        });
    }
    //リソースフォルダを読み込む
    private File[] getResourceFolder(String folder) throws URISyntaxException, IOException{
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        URL url = loader.getResource(folder);
        //.jarファイル内を読み込むときに使う
        pathURI = url.toURI();

        path = url.getPath();
        var listFiles = new File(path).listFiles();
        if(listFiles == null){
            System.out.println("You are running .jar! Performance might be down.");
            System.out.println("If you compile into .exe, this s*** never be playable!!");
            return null;
        }
        else {
            Arrays.sort(listFiles);
            return listFiles;
        }
    }
    //.jar内部のパスを取得する
    private List<Path> getPathInJar(String folder) throws URISyntaxException, IOException{
        List<Path> result;
        /* String jarPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        System.out.println(jarPath);
        URI uri = URI.create("jar:file:" + jarPath); */
        try(FileSystem fs = FileSystems.newFileSystem(pathURI,Collections.emptyMap())){
            result = Files.walk(fs.getPath(folder)).filter(Files::isRegularFile).collect(Collectors.toList());
        }
        Collections.sort(result);
        return result;
    }

    public static SoundPlayerFX getSoundPlayerClass(){
        return soundPlayerFXclass;
    }
}