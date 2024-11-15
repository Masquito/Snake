/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import Components.GamePanelComponent;
import classes.KeyHandler;
import classes.PlayerBodyPart;
import classes.Sound;
import classes.SwitchDirectionPoint;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
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
    Sound sound = new Sound();
    public boolean directionChangedSuccessfully = false;
    int cnt = 0;
    public boolean colision = false;

    public Player(GamePanelComponent gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        getPlayerImage();
        initializePlayer();
        initializeBodyParts();
        
        PlayMusic();
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
    
    public void AddPlayerBodyPart(){
        bodyParts.add(new PlayerBodyPart());
        bodyParts.get(bodyParts.size() - 1).direction = bodyParts.get(bodyParts.size() - 2).direction;
        switch(bodyParts.get(bodyParts.size() - 1).direction){
            case "up":
                bodyParts.get(bodyParts.size() - 1).y = bodyParts.get(bodyParts.size() - 2).y + gp.tileSize;
                bodyParts.get(bodyParts.size() - 1).x = bodyParts.get(bodyParts.size() - 2).x;
                System.out.println("Y przedostatniego  " + bodyParts.get(bodyParts.size() - 2).y);
                break;
            case "down":
                bodyParts.get(bodyParts.size() - 1).y = bodyParts.get(bodyParts.size() - 2).y - gp.tileSize;
                bodyParts.get(bodyParts.size() - 1).x = bodyParts.get(bodyParts.size() - 2).x;
                System.out.println("Y przedostatniego  " + bodyParts.get(bodyParts.size() - 2).y);
                break;
            case "left":
                bodyParts.get(bodyParts.size() - 1).y = bodyParts.get(bodyParts.size() - 2).y;
                bodyParts.get(bodyParts.size() - 1).x = bodyParts.get(bodyParts.size() - 2).x + gp.tileSize;
                System.out.println("Y przedostatniego  " + bodyParts.get(bodyParts.size() - 2).y);
                break;
            case "right":
                bodyParts.get(bodyParts.size() - 1).y = bodyParts.get(bodyParts.size() - 2).y;
                bodyParts.get(bodyParts.size() - 1).x = bodyParts.get(bodyParts.size() - 2).x - gp.tileSize;
                System.out.println("Y przedostatniego  " + bodyParts.get(bodyParts.size() - 2).y);
                break;
        }
    }
    
    public void update(){
        Line2D snakeNose = new Line2D.Double();
                
        int minx = bodyParts.get(bodyParts.size() - 1).x;
        int maxx = bodyParts.get(0).x;
        int miny = bodyParts.get(bodyParts.size() - 1).y;
        int maxy = bodyParts.get(0).y;
        
        switch(direction){
            case "up" -> {
                y -= speed;
                snakeNose = new Line2D.Double(new Point(x, y), new Point(x + gp.tileSize, y));
            }
            case "down" -> {
                y += speed;
                snakeNose = new Line2D.Double(new Point(x, y + gp.tileSize), new Point(x + gp.tileSize, y + gp.tileSize));
            }
            case "left" -> {
                x -= speed;
                snakeNose = new Line2D.Double(new Point(x, y), new Point(x, y + gp.tileSize));
            }
            case "right" -> {
                x += speed;
                snakeNose = new Line2D.Double(new Point(x + gp.tileSize, y), new Point(x + gp.tileSize, y + gp.tileSize));
            }
        }
        
        for(int i = 0; i < bodyParts.size(); i++){
            Line2D topEdge = new Line2D.Double(
                    new Point(bodyParts.get(i).x, bodyParts.get(i).y), new Point(bodyParts.get(i).x + gp.tileSize, bodyParts.get(i).y));
            Line2D bottomEdge = new Line2D.Double(
                    new Point(bodyParts.get(i).x, bodyParts.get(i).y + gp.tileSize), new Point(bodyParts.get(i).x + gp.tileSize, bodyParts.get(i).y + gp.tileSize));
            Line2D leftEdge = new Line2D.Double(
                    new Point(bodyParts.get(i).x, bodyParts.get(i).y), new Point(bodyParts.get(i).x, bodyParts.get(i).y + gp.tileSize));
            Line2D rightEdge = new Line2D.Double(
                    new Point(bodyParts.get(i).x + gp.tileSize, bodyParts.get(i).y), new Point(bodyParts.get(i).x + gp.tileSize, bodyParts.get(i).y + gp.tileSize));
            
            
            
            if(snakeNose.intersectsLine(topEdge) || snakeNose.intersectsLine(bottomEdge) || snakeNose.intersectsLine(leftEdge) || snakeNose.intersectsLine(rightEdge)){
               colision = true; 
               StopMusic();
               sound.setSound(2);
               sound.play();
               break;
            }
        }
        
        if(keyH.upPressed){
            if(!"down".equals(direction)){   
                if(!"up".equals(direction)){
                    directionChangedSuccessfully = true; 
                    directionSwitchPoints.add(new SwitchDirectionPoint(x, y, direction, "up", false));
                    cnt = 0;
                }
                prevDirection = direction;
                direction = "up";
                keyH.upPressed = false;
            }
        }
        else if(keyH.downPressed){
            if(!"up".equals(direction)){ 
                if(!"down".equals(direction)){
                    directionChangedSuccessfully = true;  
                    directionSwitchPoints.add(new SwitchDirectionPoint(x, y, direction, "down", false));
                    cnt = 0;
                }
                prevDirection = direction;
                direction = "down";
                keyH.downPressed = false;
            }
        }
        else if(keyH.leftPressed){
            if(!"right".equals(direction)){
                if(!"left".equals(direction)){
                    directionChangedSuccessfully = true;   
                    directionSwitchPoints.add(new SwitchDirectionPoint(x, y, direction, "left", false));
                    cnt = 0;
                }
                prevDirection = direction;
                direction = "left";
                keyH.leftPressed = false;
            }
        }
        else if(keyH.rightPressed){
            if(!"left".equals(direction)){
                if(!"right".equals(direction)){
                    directionChangedSuccessfully = true; 
                    directionSwitchPoints.add(new SwitchDirectionPoint(x, y, direction, "right", false));
                    cnt = 0;
                }
                prevDirection = direction;
                direction = "right";
                keyH.rightPressed = false;
            }
        }
        
    }
    
    public void draw(Graphics2D g2){
        
        switch(direction){
            case "up" -> headImage = upHead;
            case "down" -> headImage = downHead;
            case "left" -> headImage = leftHead;
            case "right" -> headImage = rightHead;
        }
        
        drawHead(g2);
        drawBody(g2);
        
    }
    
    public void drawHead(Graphics2D g2){
        switch(direction){
            case "up" -> {
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                g2.drawString(Integer.toString(bodyParts.size()), x + 45, y + 16);
            }
            case "down" -> {
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                if(bodyParts.size() > 9){
                    g2.drawString(Integer.toString(bodyParts.size()), x - 40, y + 48);
                }
                else{
                    g2.drawString(Integer.toString(bodyParts.size()), x - 20, y + 48);
                }
            }
            case "right" -> {
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                g2.drawString(Integer.toString(bodyParts.size()), x, y - 2);
            }
            case "left" -> {
                g2.setFont(new Font("Joystix Monospace", Font.PLAIN, 25));
                g2.setColor(Color.CYAN);
                g2.drawString(Integer.toString(bodyParts.size()), x, y - 2);
            }
        }
        
        if(y <= 0){
            y = gp.screenHeight;
        }
        else if(y >= gp.screenHeight){
            y = 0;
        }
        else if(x <= 0){
            x = gp.screenWidth;
        }
        else if(x >= gp.screenWidth){
            x = 0;
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
            System.out.println("followHead");
            for(int i = 0; i < bodyParts.size(); i++){
                switch(direction){
                    case "down" -> {
                        g2.drawImage(bodyParts.get(i).downImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        if(i != 0){
                            bodyParts.get(i).x = bodyParts.get(i - 1).x;
                            bodyParts.get(i).y = bodyParts.get(i - 1).y - gp.tileSize + speed;
                        }  
                        else{
                            bodyParts.get(i).x = x;
                            bodyParts.get(i).y = y - gp.tileSize + speed;
                            bodyParts.get(i + 1).x = bodyParts.get(i).x;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y - gp.tileSize; 
                        }
                    }
                    case "up" -> {
                        g2.drawImage(bodyParts.get(i).upImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        if(i != 0){
                            bodyParts.get(i).x = bodyParts.get(i - 1).x;
                            bodyParts.get(i).y = bodyParts.get(i - 1).y + gp.tileSize - speed;
                        }  
                        else{
                            bodyParts.get(i).x = x;
                            bodyParts.get(i).y = y + gp.tileSize - speed;
                            bodyParts.get(i + 1).x = bodyParts.get(i).x;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y + gp.tileSize; 
                        }
                    }
                    case "left" -> {
                        g2.drawImage(bodyParts.get(i).leftImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        if(i != 0){
                            bodyParts.get(i).x = bodyParts.get(i - 1).x + gp.tileSize - speed;
                            bodyParts.get(i).y = bodyParts.get(i - 1).y;
                        }  
                        else{
                            bodyParts.get(i).x = x + gp.tileSize - speed;
                            bodyParts.get(i).y = y;
                            bodyParts.get(i + 1).x = bodyParts.get(i).x + gp.tileSize;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y; 
                        }
                    }
                    case "right" -> {
                        g2.drawImage(bodyParts.get(i).rightImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        if(i != 0){
                            bodyParts.get(i).x = bodyParts.get(i - 1).x - gp.tileSize + speed;
                            bodyParts.get(i).y = bodyParts.get(i - 1).y;
                        }  
                        else{
                            bodyParts.get(i).x = x - gp.tileSize + speed;
                            bodyParts.get(i).y = y;
                            bodyParts.get(i + 1).x = bodyParts.get(i).x - gp.tileSize;
                            bodyParts.get(i + 1).y = bodyParts.get(i).y; 
                        }
                    }
                }
            }
        }
        else{
            if(cnt <= gp.tileSize * bodyParts.size()){
                System.out.println("cnt: " + cnt);
                boolean oneTime = true;
                for(int i = 0; i < directionSwitchPoints.size(); i++){
                    if((directionSwitchPoints.get(i).x == bodyParts.get(bodyParts.size() - 1).x) 
                        && (directionSwitchPoints.get(i).y == bodyParts.get(bodyParts.size() - 1).y)){
                           directionSwitchPoints.remove(i);
                    }
                    for(int j = 0; j < bodyParts.size(); j++){ 
                        if(oneTime){
                            switch(bodyParts.get(j).direction){
                                case "down" -> bodyParts.get(j).y += speed;
                                case "up" -> bodyParts.get(j).y -= speed;
                                case "left" -> bodyParts.get(j).x -= speed;
                                case "right" -> bodyParts.get(j).x += speed;
                            }
                        }
                        
                        if(bodyParts.get(j).x == directionSwitchPoints.get(i).x && bodyParts.get(j).y == directionSwitchPoints.get(i).y){
                            bodyParts.get(j).direction = directionSwitchPoints.get(i).newDirection;
                        }
                    }
                    oneTime = false;
                }
                cnt += speed;
                for(int i = 0; i < bodyParts.size(); i++){
                    switch(bodyParts.get(i).direction){
                        case "down" -> g2.drawImage(bodyParts.get(i).downImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        case "up" -> g2.drawImage(bodyParts.get(i).upImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        case "left" -> g2.drawImage(bodyParts.get(i).leftImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                        case "right" -> g2.drawImage(bodyParts.get(i).rightImage, bodyParts.get(i).x, bodyParts.get(i).y, gp.tileSize, gp.tileSize, null);
                    }
                }
            }
        }
    }
    
    public void PlayMusic(){
        sound.setSound(0);
        sound.play();
        sound.loop();
    }
    
    public void StopMusic(){
        sound.stop();
    }
}
