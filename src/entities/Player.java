/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import Components.GamePanelComponent;
import classes.KeyHandler;
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
    public int bodyLength;
    List<BufferedImage> bodyParts;
    public ArrayList<Point> switchPoints = new ArrayList<>(); 
    String prevDirection = "down";
    BufferedImage headImage = null;
    public static int cnt;
    public boolean directionChangedSuccessfully = false;
    public boolean partRotatedSuccesfully;
    private int frameCounter = 0;
    private int rotatedCounter = 0;

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
        bodyLength = 5;
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
                    switchPoints.add(new Point(x, y));
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
                    switchPoints.add(new Point(x, y));
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
                    switchPoints.add(new Point(x, y));
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
                    switchPoints.add(new Point(x, y));
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
        switch(direction){
            case "up":
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                g2.drawString(Integer.toString(bodyLength), x + 45, y + 16);
                break;
            case "down":
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                if(bodyLength > 9){
                    g2.drawString(Integer.toString(bodyLength), x - 40, y + 48);
                }
                else{
                    g2.drawString(Integer.toString(bodyLength), x - 20, y + 48);
                }
                break;
            case "right":
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                g2.drawString(Integer.toString(bodyLength), x, y - 2);
                break;
            case "left":
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                g2.drawString(Integer.toString(bodyLength), x, y - 2);
                break;
        }
        g2.drawImage(headImage, x, y, gp.tileSize, gp.tileSize, null);
    }
    
    public void drawBody(Graphics2D g2){
        if(pointSwitchDirection != null){
            DrawBodyAfterFirstDirectionChange(g2);
        }
        else{
            //Pierwsze rysowanie węża przed pierwszym skrętem
            for(int i = 1; i <= bodyLength; i++){
                g2.drawImage(downBody, x, y - i * 48, gp.tileSize, gp.tileSize, null);
            }
        }       
    }
    
    public int RecursiveNewDirectionSnakeDrawing(int bodylen, Graphics2D g2){
        for(int i = 0; i < bodylen; i++){
            switch(direction){
                //Rysowanie węża całkowicie w nowym kierunku na prosto podczas skrętu
                case "right":
                    g2.drawImage(rightBody, x - gp.tileSize * i - gp.tileSize, pointSwitchDirection.y, gp.tileSize, gp.tileSize, null);
                    break;
                case "left":
                    g2.drawImage(leftBody, x + gp.tileSize * i + gp.tileSize, pointSwitchDirection.y, gp.tileSize, gp.tileSize, null);
                    break;
                case "up":
                    g2.drawImage(upBody, pointSwitchDirection.x, y + gp.tileSize * i + gp.tileSize, gp.tileSize, gp.tileSize, null); 
                    break;
                case "down":
                    g2.drawImage(downBody, pointSwitchDirection.x, y - gp.tileSize * i - gp.tileSize, gp.tileSize, gp.tileSize, null);
                    break;

            }
        }
        if(bodylen == 0){
            return 0;
        }
        else{
            return RecursiveNewDirectionSnakeDrawing(bodylen - 1, g2);
        }
    }
    
    public void DrawBodyAfterFirstDirectionChange(Graphics2D g2){
        if(cnt <= gp.tileSize * bodyLength){
            for(int i = 1; i <= bodyLength; i++){
                switch(prevDirection){
                    //Rysowanie członów węża podczas skrętu w starym kierunku
                    case "right":
                        if((pointSwitchDirection.x - gp.tileSize * i + cnt + speed) < pointSwitchDirection.x){
                            g2.drawImage(rightBody, pointSwitchDirection.x - gp.tileSize * i + cnt + speed, pointSwitchDirection.y, gp.tileSize, gp.tileSize, null);
                        }
                        break;
                    case "left":
                        if((pointSwitchDirection.x + gp.tileSize * i - cnt - speed) > pointSwitchDirection.x){
                            g2.drawImage(leftBody, pointSwitchDirection.x + gp.tileSize * i - cnt - speed, pointSwitchDirection.y, gp.tileSize, gp.tileSize, null);
                        }
                        break;
                    case "up":
                        if((pointSwitchDirection.y + gp.tileSize * i - cnt - speed) > pointSwitchDirection.y){
                            g2.drawImage(upBody, pointSwitchDirection.x, pointSwitchDirection.y + gp.tileSize * i - cnt - speed, gp.tileSize, gp.tileSize, null);
                        }
                        break;
                    case "down":
                        if((pointSwitchDirection.y - gp.tileSize * i + cnt + speed) < pointSwitchDirection.y){
                            g2.drawImage(downBody, pointSwitchDirection.x, pointSwitchDirection.y - gp.tileSize * i + cnt + speed, gp.tileSize, gp.tileSize, null);
                        }
                        break;
                }
            }
            cnt += speed;
            if(cnt % gp.tileSize == 0){
                rotatedCounter++;
            }
            
            for(int i = 1; i <= rotatedCounter; i++){
                RecursiveNewDirectionSnakeDrawing(i, g2);
            }
            
        }
        else{
            rotatedCounter = 0;
            for(int i = 0; i < bodyLength; i++){
                switch(direction){
                    //Rysowanie węża całkowicie w nowym kierunku na prosto
                    case "right":
                        g2.drawImage(rightBody, x - gp.tileSize * i - gp.tileSize, pointSwitchDirection.y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "left":
                        g2.drawImage(leftBody, x + gp.tileSize * i + gp.tileSize, pointSwitchDirection.y, gp.tileSize, gp.tileSize, null);
                        break;
                    case "up":
                        g2.drawImage(upBody, pointSwitchDirection.x, y + gp.tileSize * i + gp.tileSize, gp.tileSize, gp.tileSize, null); 
                        break;
                    case "down":
                        g2.drawImage(downBody, pointSwitchDirection.x, y - gp.tileSize * i - gp.tileSize, gp.tileSize, gp.tileSize, null);
                        break;

                }
            }
        }
    }
    
    
}
