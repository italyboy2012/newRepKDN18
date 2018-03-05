/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kriegdernerds;

/**
 *
 * @author Giuseppe
 */
public class Map {
    
    private int sizeX = 0;
    private int sizeY = 0;
    //private sprite
    //private graphic
    //private backgroundSound
    
    public Map(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        
    }
    
    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }
    
    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }
    
    public int getSizeX() {
        return this.sizeX;
    }
    
    public int getSizeY() {
        return this.sizeY;
    }
    
}
