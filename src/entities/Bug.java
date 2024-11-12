/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import Components.GamePanelComponent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author aethi
 */
public class Bug extends Entity{
    GamePanelComponent gp;
    BufferedImage image;
    
    
    public Bug(GamePanelComponent gp){
        this.gp = gp;
        
        try{
            this.image = ImageIO.read(Bug.class.getResource("/resources/bug.png"));
            Random rand = new Random();
            x = rand.nextInt(gp.screenWidth - gp.tileSize * 2) + gp.tileSize;
            y = rand.nextInt(gp.screenHeight - gp.tileSize * 2) + gp.tileSize;
        }
        catch(IOException ex){
            
        }
    }
    
    public void draw(Graphics2D g2){
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
