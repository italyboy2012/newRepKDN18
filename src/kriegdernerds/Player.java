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
public class Player {
    
    private String name = "";
    private int lifepoints = 0;
    //private lifebar
    private int positionX = 0;
    private int positionY = 0;
    private int movementSpeed = 0;
    //private sprite
    //private arrowSprite
    //private hitSound
    //private deathSound
    private boolean active = false;
    
    public Player(String name, int lifepoints, int positionX, int positionY, int movementSpeed, boolean active) {
        this.name= name;
        this.lifepoints = lifepoints;
        this.positionX = positionX;
        this.positionY = positionY;
        this.movementSpeed = movementSpeed;
        this.active = active;
        
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setLifepoints(int lifepoints) {
        this.lifepoints = lifepoints;
    }
    
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    
    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getLifepoints() {
        return this.lifepoints;
    }
    
    public int getPositionX() {
        return this.positionX;
    }
    
    public int getPositionY() {
        return this.positionY;
    }
    
    public int getMovementSpeed() {
        return this.movementSpeed;
    }
    
    public boolean getActive() {
        return this.active;
    }
    
}
