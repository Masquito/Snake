/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author aethi
 */
public class SwitchDirectionPoint {
   
    public int x;
    public int y;
    public String oldDirection;
    public String newDirection;
    public boolean done;

    public SwitchDirectionPoint(int x, int y, String oldDirection, String newDirection, boolean done) {
        this.x = x;
        this.y = y;
        this.oldDirection = oldDirection;
        this.newDirection = newDirection;
        this.done = done;
    }
    
    
    
}
