/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kriegdernerds;
import java.lang.Math;

/**
 *
 * @author Justin and Guiseppe
 */
public class Calculation {
    
    //importieren der Refenrenzattribute
    private Player player;
    private Projectile projectile;
    private Map map;
    private Player activePlayer;
    private CharacterSelection characterSelection;
    
    private double velocity = projectile.getVelocity();
    private double angle;
    private double height = player.getPositionY();
    
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
    
    /**
     * Die Methode soll festlegen wo der Spieler an welcher Stelle der Map
     * aufgerufen wird und setzt die Spieler und Projektile auf ihre
     * Default-Einstellungen.
     * @param player
     * @param map 
     * @param projectile
     */
    public void startGame(Player player, Map map, Projectile projectile) {
        
        //Array mit allen Spielern
        Player[] players = characterSelection.getAllPlayers();
        
        /*
        */
        for(int i=1; i<=players.length; i++){
            if(players.length==1){
                player.setActive(true);
                player.setPositionX(10);
                player.setPositionY(10);
            } else {
                player.setActive(false);
                player.setPositionX(50);
                player.setPositionY(10);
            }
            player.setLifepoints(player.getLifepoints());
            player.setMovementSpeed(player.getMovementSpeed());
            
            map.setSizeX(map.getSizeY());
            map.setSizeY(map.getSizeY());

            projectile.setDamage(projectile.getDamage());
            projectile.setExplosionRadius(projectile.getExplosionRadius());
            projectile.setVelocity(projectile.getVelocity());
        }
    }
    
    public void shoot(Player player, Projectile projectile, Map map) {
        
    }
    
    public void calculatePlayerDamage(Player player, Projectile projectile) {
    }
    
    /**
     * Die Methode berechnet die Flugkurve der Parabel, die entsteht
     * wenn ein Projektil abgeschossen wird
     * @param projectile 
     */
    public void calculateParabola(Projectile projectile) {
                
        /*
        Die Variable a ist der Faktor mit der die Parabel beim Schiessen des
        Projektils gestreckt bzw. gestaucht wird. Weil diese nach unten
        geoeffnet sein muss, ist dieser Wert negativ. Damit die Parabel nicht zu
        stark beeinflusst wird, wird diese um dem Quotienten 10 verkleinert.
        
        Die Variable b ist für die Steigung des Projektils am Ort des Spielers
        verantwortlich. Wenn der Schusswinkel angle gegeben ist, so bekommt man
        die Steigung, indem man den Schusswinkel in den Tangens einsetzt. Da die
        Methode Math.tan() mit Werten im Bogenmaß rechnet und auch diese ausgibt,
        muss man den Schusswinkel erst in das Bogenmaß umrechnen, was mit der
        Formel "Winkel geteilt durch 180, und das multipliziert mit Pi" möglich
        ist. Das Ergebnis im Tangens eingesetzt wird dann noch einmal auf zwei
        Stellen gerundet.
        
        Die Variable c entspricht der Variablen height. Sie wird hier doppelt
        initialisiert, damit der Code darunter etwas geordneter aussieht
        */
        double a = -velocity / 10;
        double b = Math.round(Math.tan(angle/180.0*Math.PI)*100.0)/100.0;
        double c = height;
        System.out.println(angle +" "+ b);
        
        /*
        Die Variable impactPoint1 berechnet die Nullstelle der Quadratischen
        Funktion mithilfe der abc-Formel.
        Da eine quadratische Funktion auch zwei Nullstellen haben kann, muessen
        zwei moegliche Nullstellen berechnet werden. Dies geht, indem man das
        Vorzeichen der Wurzel einmal positiv hat und einmal negativ.
        Die Lösung mit dem negativen Vorzeichen vor der Wurzel ist die Variable
        impactPoint2.
        */
        double impactPoint1 = Math.round(((-b + Math.sqrt(b*b - 4*a*c)) / (2*a))*100.0)/100.0;
        double impactPoint2 = Math.round(((-b - Math.sqrt(b*b - 4*a*c)) / (2*a))*100.0)/100.0;
        
        //Die Schleife berechnet alle Punkte der Parabel bis sie am Boden ankommt
        for(double x=0; x <= impactPoint2; x = x+0.1){
            double parabola = a*x*x + b*x + c;
        }
    }
    
    private void calculateMapDamage(Map map, Projectile projectile) {
        
    }
    
    private void calculateTime() {
        
    }
    
    private void setActivePlayer(Player player) {
        
    }
    
    
    
}
