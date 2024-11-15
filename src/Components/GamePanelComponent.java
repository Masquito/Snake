/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import classes.KeyHandler;
import classes.PlayerBodyPart;
import classes.Sound;
import entities.Bug;
import entities.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import tile.TileManager;

/**
 *
 * @author aethi
 */
public class GamePanelComponent extends JPanel implements Runnable{
    
    final int maxScreenColumns = 28;
    final int maxScreenRows = 21;
    public final int tileSize = 48; //48x48 tile
    public final int screenWidth = tileSize * maxScreenColumns;
    public final int screenHeight = tileSize * maxScreenRows;
    
    public final int playState = 0;
    public final int gameOverState = 1;
    public int gameState = 0;
    
    TileManager tm = new TileManager(this);
    KeyHandler keyH = new KeyHandler(); 
    Thread gameThread;
    Sound sound = new Sound();
    Player player = new Player(this, keyH);
    List<Bug> bugs = new ArrayList();
    int bugSpawnCounter = 0;
    int FPS = 60;
    
    public GamePanelComponent(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void StartGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while(gameThread != null){
            
            update();
            
            repaint();
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime);
                
                nextDrawTime += drawInterval;
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanelComponent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void update(){
        player.update();
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        if(!player.colision){
            bugSpawnCounter++;
            if(bugSpawnCounter >= 300){
                bugs.add(new Bug(this));
                bugSpawnCounter = 0;
            }
            tm.draw(g2);
            for(int i = 0; i < bugs.size(); i++){
                bugs.get(i).draw(g2);
            }
            player.draw(g2);
            for(int i = 0; i < bugs.size(); i++){
                if(player.x >= bugs.get(i).x - 36 && player.x <= bugs.get(i).x + 36 && player.y >= bugs.get(i).y - 36 && player.y <= bugs.get(i).y + 36){
                    bugs.remove(i);
                    player.AddPlayerBodyPart();
                    sound.setSound(1);
                    sound.play();
                }
            }
            g2.dispose();
        }
        else{
            g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 120));
            g2.setColor(Color.red);
            g2.drawString("Game Over", 200, 400);
        }
    }
}
