/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kriegdernerds;

/**
 *
 * @author schueler
 */
public class TestClassGiuseppe {
    
    private Map map;
    private Player player;
    private Projectile projectile;
    private Calculation calculation;
    
    public void createMap(int x, int y) {
        map = new Map(x, y);
        
    }
    
    public void createPlayer(String name, int lp, int x, int y, int msp, boolean active) {
        player = new Player(name, lp, x, y, msp, active);
        
    }
    
    public void createProjectile(int ve, int da, int er) {
        projectile = new Projectile(ve, da, er);
        
    }
    
    public void createCalculation() {
        calculation = new Calculation(player, player, projectile, map);
        
    }
    
    
    
    
}