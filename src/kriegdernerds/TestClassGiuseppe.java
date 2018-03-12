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
    
    private Map map = new Map(100, 120);
    private Player player = new Player("Giuseppe", 20, 2, 3, 100, true);
    private Projectile projectile = new Projectile(50, 20, 10);
    private Calculation calculation = new Calculation(player, projectile, map, player);
    
    public void tests() {
        System.out.println("HEY");
    }
    
    
}
