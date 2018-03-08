/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kriegdernerds;
import java.lang.Math;
/**
 *
 * @author Giuseppe
 */
public class Calculation {
    
    private Player player;
    private Projectile projectile;
    private Map map;
    private Player activePlayer;
    
    private int angle; //Attribut für den Schusswinkel
   // private int velocity = projectile //Attribut wichtig für Streckfaktor, leicht runtergeregelt
    
    /**
     * Konstruktor
     * 
     * @param player
     * @param projectile
     * @param map
     * @param activePlayer 
     */
    public Calculation(Player player, Projectile projectile, Map map, Player activePlayer) {
        this.player = player;
        this.projectile = projectile;
        this.map = map;
        this.activePlayer = activePlayer;
    }
    
    public void startGame(Player player, Map map) {
        
    }
    
    public void shoot(Player player, Projectile projectile, Map map) {
        
    }
    
    public void calculatePlayerDamage(Player player, Projectile projectile) {
        //Variablen für Funktion der Parabel
        double a = Math.tan(angle); //Streckfaktor
        
        double x; //Abhängige Variable für Funktion
    }
    
    /**
     * Die Methode berechnet die Flugkurve der Parabel, die entsteht
     * wenn ein Projektil abgeschossen wird
     */
    public void calculateParabola(Projectile projectile) {
        
    }
    
    private void calculateMapDamage(Map map, Projectile projectile) {
        
    }
    
    private void calculateTime() {
        
    }
    
    private void setActivePlayer(Player player) {
        
    }
    
    
    
}
