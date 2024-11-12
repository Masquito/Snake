/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tile;

import Components.GamePanelComponent;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author aethi
 */
public class TileManager {
    GamePanelComponent gp;
    Tile[] tiles;
    
    public TileManager(GamePanelComponent gp){
        this.gp = gp;
        this.tiles = new Tile[10];
        
        getTileImages();
    }
    
    public void getTileImages(){
        try{
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(TileManager.class.getResource("/resources/desert.png"));

        }
        catch(IOException ex){
        }
    }
    
    public void draw(Graphics2D g2){
        for(int i = 0; i < gp.screenWidth; i+=48){
            for(int j = 0; j < gp.screenHeight; j+=48){
                g2.drawImage(tiles[0].image, i, j, gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
