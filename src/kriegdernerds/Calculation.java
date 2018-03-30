/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kriegdernerds;
import java.lang.Math;

/**
 * @author Justin and Guiseppe
 */
public class Calculation {
    
    /*
    Die Klasse berechnet nur alle Werte der Parabel falls die Figur gerade nach
    rechts schaut und demnach auch dorthin schiesst. Wenn es eine Variable in
    der Klasse Player gibt aus der man die Blickrichtung des Players herausfindet,
    dann werde ich das auch implementieren.
    Da in den Javadoc Kommentaren manchmal komische Zeichensaetze zu finden sind,
    schreib ich kurz die Bedeutung der einzelnen Zeichen hier hin:
    
    Unicode-Zeichen:
    \u00e4 = ä (ae)
    \u00f6 = ö (oe)
    \u00fc = ü (ue)
    \u00df = ß (Eszett)
    */
    
    //Importieren der Referenzattribute plus einem Array, das alle Spieler speichert
    private Player player1;
    private Player player2;
    private Player[] allPlayers = {player1,player2};
    private Projectile projectile;
    private Map map;
    private Player activePlayer;
    private CharacterSelection characterSelection;
    
    //Attribute vom Projectile. Wird vielleicht spaeter zur lokalen Variable 
    private double velocity;
    private double angle;
    private double height;
    
    /*
    Parameter der Parabel, a entspricht dem Streckfaktor, b der Steigung am
    Abschussort und c der Hoehe am Abschussort. Gilt dasselbe wie oben.
    */
    private double a;
    private double b;
    private double c;
    
    /*
    impactPoint's sind die Punkte an denen das Projektil auf den Boden aufschlaegt.
    extremePoint's sind die einzelnen Koordinaten am hoechsten Punkt der Parabel 
    */
    private double impactPoint1;
    private double impactPoint2;
    private double extremePointX;
    private double extremePointY;
  
    /**
     * Konstruktor
     * 
     * @param player1 erstes Objekt der Klasse Player
     * @param player2 zweites Objekt der Klasse Player
     * @param projectile Objekt der Klasse Projectile
     * @param map Objekt der Klasse Map
     */
    public Calculation(Player player1, Player player2, Projectile projectile, Map map) {
        this.player1 = player1;
        this.player2 = player2;
        this.height = player1.getPositionY();
        this.projectile = projectile;
        this.velocity = projectile.getVelocity();
        this.map = map;
    }
    
    /**
     * Die Methode soll festlegen wo der Spieler an welcher Stelle der Map
     * abgesetzt wird und setzt die Spieler und Projektile auf ihre
     * Default-Einstellungen.
     * @param player1 Der erste Spieler
     * @param player2 Der zweite Spieler
     * @param map Die ausgew\u00e4hlte Map
     * @param projectile Das ausgew\u00e4hlte Projektil
     */
    public void startGame(Player player1, Player player2, Map map, Projectile projectile) {       
        /*
        Default Lifepoints, movement speed und Position Y; zusaetzlich auch die
        Groesse der Map. Wird später durch die richtigen Werte, anderer Klassen
        ersetzt, falls diese implementiert werden oder ich nehme einen
        Default-Wert von einem Player. Ich bin mir bloss nicht sicher, welches
        ich nehme.
        */
        int lifepoints = 100;
        int movementSpeed = 1;
        int positionY = 10;
        int sizeMap = map.getSizeX();
        
        //Default-Werte, die auf jeden Spieler zutreffen
        for(int i=0;i<allPlayers.length;i++){
            allPlayers[i].setLifepoints(lifepoints);
            allPlayers[i].setMovementSpeed(movementSpeed);
            allPlayers[i].setPositionY(positionY);
        }
        
        /*
        setzt den ersten Spieler auf seine Default-Werte und der erste Spieler
        der activePlayer und wird active gesetzt
        */
        this.player1 = this.activePlayer;        
        this.player1.setActive(true);
        this.player1.setPositionX(player1.getPositionX());
        
        //setzt den anderen Spieler auf seine Default-Werte
        this.player2.setActive(false);
        //Spieler ist auf der anderen Seite der Map, gleichweit von der Wand entfernt
        this.player2.setPositionX(sizeMap - player1.getPositionX());
          
        this.map.setSizeX(map.getSizeX());
        this.map.setSizeY(map.getSizeY());

        this.projectile.setDamage(projectile.getDamage());
        this.projectile.setExplosionRadius(projectile.getExplosionRadius());
        this.projectile.setVelocity(projectile.getVelocity());
    }
    
    /**
     * Die Methode l\u00e4sst das Geschoss fliegen und markiert bis zu ihrem
     * Scheitelpunkt die Flugbahn, falls das in dieser Klasse m\u00f6glich ist.
     * @param player Der aktive Spieler
     * @param projectile Das ausgew\u00e4hlte Projektil
     * @param map Die ausgew\u00e4hlte Map
     */
    public void shoot(Player player, Projectile projectile, Map map) {
       for(double i=0;i<=impactPoint2;i = i+0.01){
           double parabola = a*i*i + b*i + c;
           if(i<=extremePointX){
                //hier muesste dann ein Punkt oder aehnliches gesetzt werden 
           }           
       }
    }
    
    public void calculatePlayerDamage(Player player, Projectile projectile) {
    }
    
    /**
     * Die Methode berechnet die Flugkurve der Parabel, die entsteht
     * wenn ein Projektil abgeschossen wird
     * @param projectile Das entsprechende Projektil, das abgeschossen wird
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
        a = -velocity / 10;
        b = Math.round(Math.tan(angle/180.0*Math.PI)*100.0)/100.0;
        c = height;
        
        /*
        Die Variable impactPoint1 berechnet die Nullstelle der Quadratischen
        Funktion mithilfe der abc-Formel.
        Da eine quadratische Funktion auch zwei Nullstellen haben kann, muessen
        zwei moegliche Nullstellen berechnet werden. Dies geht, indem man das
        Vorzeichen der Wurzel einmal positiv hat und einmal negativ.
        Die Lösung mit dem negativen Vorzeichen vor der Wurzel ist die Variable
        impactPoint2.
        */
        impactPoint1 = Math.round(((-b + Math.sqrt(b*b - 4*a*c)) / (2*a))*100.0)/100.0;
        impactPoint2 = Math.round(((-b - Math.sqrt(b*b - 4*a*c)) / (2*a))*100.0)/100.0;
    }
    
    private void calculateMapDamage(Map map, Projectile projectile) {
        
    }
    
    private void calculateTime() {
        
    }
    
    /**
     * Die Methode setzt das Attribut active des Spielers, der dran ist auf true
     * und setzt das des Spielers, der gerade eben dran gewesen war auf false.
     * @param player Der Spieler der jetzt dran ist
     */
    private void setActivePlayer(Player player) {
        
    }
    
    /**
     * get-Methode, die den ersten Punkt angibt, an der das Projektil auf den Boden aufschl\u00e4gt
     * @return impactPoint1 - erster Nullpunkt der Parabel
     */
    private double getImpactPoint1() {
        return impactPoint1;
    }

    /**
     * get-Methode, die den zweiten Punkt angibt, an der das Projektil auf den Boden aufschl\u00e4gt
     * @return impactPoint2 - zweiter Nullpunkt der Parabel
     */
    private double getImpactPoint2() {
        return impactPoint2;
    }
    
    /**
     * get-Methode, die den X-Wert des Extrempunktes der Parabel zur\u00fcckgibt
     * @return extremePointX
     */
    private double getExtremePointX() {
        return extremePointX;
    }
    
    /**
     * get-Methode, die den Y-Wert des Extrempunktes der Parabel zur\u00fcckgibt
     * @return extremePointY
     */
    private double getExtremePointY() {
        return extremePointY;
    }
    
    /**
     * get-Methode, die die Funktionsgleichung der Parabelfunktion als String zur\u00fcckgibt
     * @return ax²+bx+c - Funktionsgleichung mit eingesetzten Parametern der Funktion
     */
    private String getParabola(){
        return a + "x² + " +b+ "x + " +c;
    }   
}