/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import Components.GamePanelComponent;
import classes.KeyHandler;
import classes.PlayerBodyPart;
import classes.SwitchDirectionPoint;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 *
 * @author aethi
 */
public class Player extends Entity{
    
    GamePanelComponent gp;
    KeyHandler keyH;
    public ArrayList<SwitchDirectionPoint> directionSwitchPoints = new ArrayList<>(); 
    public ArrayList<PlayerBodyPart> bodyParts = new ArrayList<>();
    String prevDirection = "down";
    BufferedImage headImage = null;
    public boolean directionChangedSuccessfully = false;
    int cnt = 0;

    public Player(GamePanelComponent gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        getPlayerImage();
        initializePlayer();
        initializeBodyParts();
    }
    
    public void initializePlayer(){
        x = 300;
        y = 300;
        speed = 4;
        direction = "down";
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
    
    public void initializeBodyParts(){
        for(int i = 0; i < 5; i++){
            bodyParts.add(new PlayerBodyPart());
        }
        for(int i = 0; i < 5; i++){
            bodyParts.get(i).direction = "down";
            bodyParts.get(i).x = x;
            bodyParts.get(i).y = y - gp.tileSize * i;
        }
    }
    
    public void update(){
        if(keyH.upPressed){
            if(direction != "down"){   
                if(direction != "up"){
                    directionChangedSuccessfully = true; 
                    directionSwitchPoints.add(new SwitchDirectionPoint(x, y, direction, "up", false));
                }
                prevDirection = direction;
                direction = "up";
                keyH.upPressed = false;
            }
        }
        else if(keyH.downPressed){
            if(direction != "up"){ 
                if(direction != "down"){
                    directionChangedSuccessfully = true;  
                    directionSwitchPoints.add(new SwitchDirectionPoint(x, y, direction, "down", false));
                }
                prevDirection = direction;
                direction = "down";
                keyH.downPressed = false;
            }
        }
        else if(keyH.leftPressed){
            if(direction != "right"){
                if(direction != "left"){
                    directionChangedSuccessfully = true;   
                    directionSwitchPoints.add(new SwitchDirectionPoint(x, y, direction, "left", false));
                }
                prevDirection = direction;
                direction = "left";
                keyH.leftPressed = false;
            }
        }
        else if(keyH.rightPressed){
            if(direction != "left"){
                if(direction != "right"){
                    directionChangedSuccessfully = true; 
                    directionSwitchPoints.add(new SwitchDirectionPoint(x, y, direction, "right", false));
                }
                prevDirection = direction;
                direction = "right";
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
        
    }
    
    public void draw(Graphics2D g2){
        
        switch(direction){
            case "up":
                headImage = upHead;
                break;
            case "down":
                headImage = downHead;
                break;
            case "left":
                headImage = leftHead;
                break;
            case "right":
                headImage = rightHead;
                break;
        }
        
        drawHead(g2);
        drawBody(g2);
        
    }
    
    public void drawHead(Graphics2D g2){
        switch(direction){
            case "up":
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                g2.drawString(Integer.toString(bodyParts.size()), x + 45, y + 16);
                break;
            case "down":
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                if(bodyParts.size() > 9){
                    g2.drawString(Integer.toString(bodyParts.size()), x - 40, y + 48);
                }
                else{
                    g2.drawString(Integer.toString(bodyParts.size()), x - 20, y + 48);
                }
                break;
            case "right":
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                g2.drawString(Integer.toString(bodyParts.size()), x, y - 2);
                break;
            case "left":
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                g2.drawString(Integer.toString(bodyParts.size()), x, y - 2);
                break;
        }
        g2.drawImage(headImage, x, y, gp.tileSize, gp.tileSize, null);
    }
    
    public void drawBody(Graphics2D g2){
        //Podejście "zmierzaj ciałem do najbliższego switchPointa, a jeżeli wszystkie są już done to do głowy"

        //Sprawdzenie czy wszystkie switchPointy zostały obsłużone, jeżeli tak to podążaj za głową
        boolean followhead = true;
        for(int i = 0; i < directionSwitchPoints.size(); i++){
            if(!directionSwitchPoints.get(i).done){
                followhead = false;
                break;
            }
            followhead = true;
        }
        
        if(followhead){
            for(int i = 0; i < bodyParts.size() - 1; i++){
                switch(direction){
                    case "down":
                        g2.drawImage(bodyParts.get(i).downImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        if(i != 0){
                            bodyParts.get(i + 1).x = bodyParts.get(i).x;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y - gp.tileSize; 
                        }  
                        else{
                            bodyParts.get(i).x = x;
                            bodyParts.get(i).y = y - gp.tileSize + speed;
                            bodyParts.get(i + 1).x = bodyParts.get(i).x;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y - gp.tileSize; 
                        }
                        break;
                    case "up":
                        g2.drawImage(bodyParts.get(i).upImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        if(i != 0){
                            bodyParts.get(i + 1).x = bodyParts.get(i).x;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y + gp.tileSize; 
                        }  
                        else{
                            bodyParts.get(i).x = x;
                            bodyParts.get(i).y = y + gp.tileSize - speed;
                            bodyParts.get(i + 1).x = bodyParts.get(i).x;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y + gp.tileSize; 
                        }
                        break;
                    case "left":
                        g2.drawImage(bodyParts.get(i).leftImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        if(i != 0){
                            bodyParts.get(i + 1).x = bodyParts.get(i).x + gp.tileSize;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y; 
                        }  
                        else{
                            bodyParts.get(i).x = x + gp.tileSize - speed;
                            bodyParts.get(i).y = y;
                            bodyParts.get(i + 1).x = bodyParts.get(i).x + gp.tileSize;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y; 
                        }
                        break;
                    case "right":
                        g2.drawImage(bodyParts.get(i).rightImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        if(i != 0){
                            bodyParts.get(i + 1).x = bodyParts.get(i).x - gp.tileSize;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y; 
                        }  
                        else{
                            bodyParts.get(i).x = x - gp.tileSize + speed;
                            bodyParts.get(i).y = y;
                            bodyParts.get(i + 1).x = bodyParts.get(i).x - gp.tileSize;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y; 
                        }
                        break;
                }
            }
        }
        else{
            if(cnt <= gp.tileSize * bodyParts.size()){
                for(int i = 0; i < directionSwitchPoints.size(); i++){
                    for(int part = 0; part < bodyParts.size(); part++){                       
                        if(bodyParts.get(part).x == directionSwitchPoints.get(i).x && bodyParts.get(part).y == directionSwitchPoints.get(i).y){
                            bodyParts.get(part).direction = directionSwitchPoints.get(i).newDirection;
                        }
                        else{                           
                            switch(bodyParts.get(part).direction){
                                case "down":
                                    bodyParts.get(part).y += speed;
                                    break;
                                case "up":
                                    bodyParts.get(part).y -= speed;
                                    break;
                                case "left":
                                    bodyParts.get(part).x -= speed;
                                    break;
                                case "right":
                                    bodyParts.get(part).x += speed;
                                    break;
                            }
                            
                        }

                    }
                }
                cnt += speed;
                
                for(int i = 0; i < bodyParts.size() - 1; i++){
                    switch(bodyParts.get(i).direction){
                        case "down":
                            g2.drawImage(bodyParts.get(i).downImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                            break;
                        case "up":
                            g2.drawImage(bodyParts.get(i).upImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                            break;
                        case "left":
                             g2.drawImage(bodyParts.get(i).leftImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                             break;
                        case "right":
                             g2.drawImage(bodyParts.get(i).rightImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                             break;
                    }
                }
            }
            else{
                directionSwitchPoints.get(0).done = true;
                followhead = true;
            }
        }
    }
}
