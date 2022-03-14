//not USED

package javaRPGFX.sound;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
    
    private File sfxDirectory;
    private File bgmDirectory;
    private ArrayList<File> sfx;
    private ArrayList<File> bgm;
    private File[] sfxfiles;
    private File[] bgmfiles;

    public SoundPlayer(){
        sfxDirectory = new File("/javaRPG/sound/sfx/");
        bgmDirectory = new File("/javaRPG/sound/bgm/");
        sfx = new ArrayList<File>();
        bgm = new ArrayList<File>();
        sfxfiles = sfxDirectory.listFiles();
        bgmfiles = bgmDirectory.listFiles();
        if(sfxfiles != null){
            for(File file : sfxfiles){
                sfx.add(file);
            }
        }
        if(bgmfiles != null){
            for(File file : bgmfiles){
                bgm.add(file);
            }
        }
    }

    public Clip createAudioClip(File path) throws IOException, UnsupportedAudioFileException{
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)){

            AudioFormat af = ais.getFormat();

            DataLine.Info dataLine = new DataLine.Info(Clip.class,af);

            Clip c = (Clip)AudioSystem.getLine(dataLine);

            c.open(ais);

            return c;

        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void playBGM() throws IOException, UnsupportedAudioFileException{
        Clip clip = createAudioClip(new File(bgm.get(0).toString()));
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
