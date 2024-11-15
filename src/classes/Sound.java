/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import entities.Player;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author aethi
 */
public class Sound {
    Clip clip;
    URL soundURL[] = new URL[40];
    
    public Sound(){
        soundURL[0] = getClass().getResource("/sounds/GameMusic.wav");
        soundURL[1] = getClass().getResource("/sounds/EatABugSound.wav");
        soundURL[2] = getClass().getResource("/sounds/GameOverSound.wav");
                
    }
    
    public void setSound(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }
        catch(Exception ex){
            
        }
    }
    
    public void play(){
        clip.start();
    }
    
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop(){
        clip.stop();
    }
}
