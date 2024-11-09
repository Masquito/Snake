/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author aethi
 */
public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int asciiKeyPressedCode = e.getKeyCode();
        
        if(asciiKeyPressedCode == KeyEvent.VK_W){
            upPressed = true;
        }
        
        if(asciiKeyPressedCode == KeyEvent.VK_S){
            downPressed = true;
        }
        
        if(asciiKeyPressedCode == KeyEvent.VK_A){
            leftPressed = true;
        }
        
        if(asciiKeyPressedCode == KeyEvent.VK_D){
            rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int asciiKeyPressedCode = e.getKeyCode();
        
        if(asciiKeyPressedCode == KeyEvent.VK_W){
            upPressed = false;
        }
        
        if(asciiKeyPressedCode == KeyEvent.VK_S){
            downPressed = false;
        }
        
        if(asciiKeyPressedCode == KeyEvent.VK_A){
            leftPressed = false;
        }
        
        if(asciiKeyPressedCode == KeyEvent.VK_D){
            rightPressed = false;
        }
    }
    
}
