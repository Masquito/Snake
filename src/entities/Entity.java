/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.awt.image.BufferedImage;

/**
 *
 * @author aethi
 */
public class Entity {
    public int x;
    public int y;
    public int speed;
    
    public BufferedImage upHead, upBody, rightHead, rightBody, downHead, downBody, leftHead, leftBody;
    public String direction;
}
