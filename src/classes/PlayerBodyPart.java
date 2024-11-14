/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import entities.Player;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author aethi
 */
public class PlayerBodyPart {
    public int x;
    public int y;
    public String direction;
    public BufferedImage upImage;
    public BufferedImage downImage;
    public BufferedImage rightImage;
    public BufferedImage leftImage;
    
    public PlayerBodyPart(){
        LoadBodyImages();
    }
    
    public void LoadBodyImages(){
        try{
            upImage = ImageIO.read(Player.class.getResource("/resources/SnakeUpBody.png"));
            rightImage = ImageIO.read(Player.class.getResource("/resources/SnakeRightBody.png"));
            downImage = ImageIO.read(Player.class.getResource("/resources/SnakeDownBody.png"));
            leftImage = ImageIO.read(Player.class.getResource("/resources/SnakeLeftBody.png"));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
