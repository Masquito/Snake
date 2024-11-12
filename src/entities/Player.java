/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import Components.GamePanelComponent;
import classes.KeyHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author aethi
 */
public class Player extends Entity{
    
    GamePanelComponent gp;
    KeyHandler keyH;
    int bodyLength;
    List<BufferedImage> bodyParts;
    Point pointSwitchDirection = null;
    String prevDirection = "down";
    BufferedImage headImage = null;
    public static int cnt;
    public boolean directionChangedSuccessfully = false;

    public Player(GamePanelComponent gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        getPlayerImage();
        initializePlayer();
    }
    
    public void initializePlayer(){
        x = 300;
        y = 300;
        speed = 4;
        direction = "down";
        bodyLength = 2;
        bodyParts = new ArrayList<>(Arrays.asList(downBody, downBody));
    }
    
    public void getPlayerImage(){
        try{
            upHead = ImageIO.read(Player.class.getResource("/resources/SnakeUpHead.png"));
            upBody = ImageIO.read(Player.class.getResource("/resources/SnakeUpBody.png"));
            rightHead = ImageIO.read(Player.class.getResource("/resources/SnakeRightHead.png"));
            rightBody = ImageIO.read(Player.class.getResource("/resources/SnakeRightBody.png"));
            downHead = ImageIO.read(Player.class.getResource("/resources/SnakeDownHead.png"));
            downBody = ImageIO.read(Player.class.getResource("/resources/SnakeDownBody.png"));
            leftHead = ImageIO.read(Player.class.getResource("/resources/SnakeLeftHead.png"));
            leftBody = ImageIO.read(Player.class.getResource("/resources/SnakeLeftBody.png"));
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void update(){
        if(keyH.upPressed){
            if(direction != "down"){   
                if(direction != "up"){
                    directionChangedSuccessfully = true;   
                }
                prevDirection = direction;
                direction = "up";
                pointSwitchDirection = new Point(x, y);
                keyH.upPressed = false;
            }
        }
        else if(keyH.downPressed){
            if(direction != "up"){ 
                if(direction != "down"){
                    directionChangedSuccessfully = true;   
                }
                prevDirection = direction;
                direction = "down";
                pointSwitchDirection = new Point(x, y);
                keyH.downPressed = false;
            }
        }
        else if(keyH.leftPressed){
            if(direction != "right"){
                if(direction != "left"){
                    directionChangedSuccessfully = true;   
                }
                prevDirection = direction;
                direction = "left";
                pointSwitchDirection = new Point(x, y);
                keyH.leftPressed = false;
            }
        }
        else if(keyH.rightPressed){
            if(direction != "left"){
                if(direction != "right"){
                    directionChangedSuccessfully = true;   
                }
                prevDirection = direction;
                direction = "right";
                pointSwitchDirection = new Point(x, y);
                keyH.rightPressed = false;
            }
        }
        
        switch(direction){
            case "up":
                y -= speed;
                break;
            case "down":
                y += speed;
                break;
            case "left":
                x -= speed;
                break;
            case "right":
                x += speed;
                break;
        }
        
        if(directionChangedSuccessfully){
            cnt = 0;
            directionChangedSuccessfully = false;
        }
    }
    
    public void draw(Graphics2D g2){
        
        switch(direction){
            case "up":
                headImage = upHead;
                bodyParts.clear();
                for(int i = 0; i < bodyLength; i++){
                    bodyParts.add(upBody);
                }
                break;
            case "down":
                headImage = downHead;
                bodyParts.clear();
                for(int i = 0; i < bodyLength; i++){
                    bodyParts.add(downBody);
                }
                break;
            case "left":
                headImage = leftHead;
                bodyParts.clear();
                for(int i = 0; i < bodyLength; i++){
                    bodyParts.add(leftBody);
                }
                break;
            case "right":
                headImage = rightHead;
                bodyParts.clear();
                for(int i = 0; i < bodyLength; i++){
                    bodyParts.add(rightBody);
                }
                break;
        }
        
        drawHead(g2);
        drawBody(g2);
        
    }
    
    public void drawHead(Graphics2D g2){
        g2.drawImage(headImage, x, y, gp.tileSize, gp.tileSize, null);
    }
    
    public void drawBody(Graphics2D g2){
        if(pointSwitchDirection != null){
            if(cnt <= gp.tileSize){
                switch(prevDirection){
                    case "right":
                        g2.drawImage(rightBody, pointSwitchDirection.x - gp.tileSize + cnt + speed, pointSwitchDirection.y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "left":
                        g2.drawImage(leftBody, pointSwitchDirection.x + gp.tileSize - cnt - speed, pointSwitchDirection.y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "up":
                        g2.drawImage(upBody, pointSwitchDirection.x, pointSwitchDirection.y + gp.tileSize - cnt - speed, gp.tileSize, gp.tileSize, null);
                        break;
                    case "down":
                        g2.drawImage(downBody, pointSwitchDirection.x, pointSwitchDirection.y - gp.tileSize + cnt + speed, gp.tileSize, gp.tileSize, null);
                        break;
                }
                cnt += speed;
            }
            else{
                switch(direction){
                    case "right":
                        g2.drawImage(rightBody, x - gp.tileSize, pointSwitchDirection.y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "left":
                        g2.drawImage(leftBody, x + gp.tileSize, pointSwitchDirection.y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "up":
                        g2.drawImage(upBody, pointSwitchDirection.x, y + gp.tileSize, gp.tileSize, gp.tileSize, null); 
                        break;
                    case "down":
                        g2.drawImage(downBody, pointSwitchDirection.x, y - gp.tileSize, gp.tileSize, gp.tileSize, null);
                        break;

                }
            }
        }
        else{
            cnt = 0;
            g2.drawImage(downBody, x, y - gp.tileSize, gp.tileSize, gp.tileSize, null);
        }
    }
}
