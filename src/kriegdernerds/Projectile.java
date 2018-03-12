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
public class Projectile {
    
    private int velocity = 0;
    private int damage = 0;
    private int explosionRadius = 0;
    //private sprite
    //private shootSound
    //private impactSound
    
    public Projectile(int velocity, int damage, int explosionRadius) {
        this.velocity = velocity;
        this.damage = damage;
        this.explosionRadius = explosionRadius;
        
    }
    
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
    
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public void setExplosionRadius(int explosionRadius) {
        this.explosionRadius = explosionRadius;
    }
    
    public int getVelocity() {
        return this.velocity;
    }
    
    public int getDamage() {
        return this.damage;
    }
    
    public int getExplosionRadius() {
        return this.explosionRadius;
    }
 
}
