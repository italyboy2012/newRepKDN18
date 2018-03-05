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
public class GameType {
    
    private boolean local = false;
    private boolean online = false;
    
    public void goBack( ) {
        
    }
    
    public GameType(boolean local, boolean online) {
    this.local = local;
    this.online = online;
    }
    
}
