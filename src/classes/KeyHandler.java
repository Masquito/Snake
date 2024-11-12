/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import entities.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author aethi
 */
public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    private int prevKey;
    public int dirChanged = 1;
    public int prevDir = 0;
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int asciiKeyPressedCode = e.getKeyCode();
        if(asciiKeyPressedCode == KeyEvent.VK_W){
            upPressed = true;
            downPressed = false;
            leftPressed = false;
            rightPressed = false;
        }
        
        if(asciiKeyPressedCode == KeyEvent.VK_S){
            downPressed = true;
            upPressed = false;
            leftPressed = false;
            rightPressed = false;
        }
        
        if(asciiKeyPressedCode == KeyEvent.VK_A){
            leftPressed = true;
            upPressed = false;
            downPressed = false;
            rightPressed = false;
        }
        
        if(asciiKeyPressedCode == KeyEvent.VK_D){
            rightPressed = true;
            upPressed = false;
            downPressed = false;
            leftPressed = false;
        }

        
        prevKey = e.getKeyCode();
    }
    
}
