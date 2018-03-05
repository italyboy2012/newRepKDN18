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
public class Options {
    
    private int volume = 0;
    private int music = 0;
    private boolean parabolaVisible = false;
    private boolean indicatorVisible = false;
    
    public Options(int volume, int music, boolean parabolaVisible, boolean indicatorVisible) {
    this.volume = volume;
    this.music = music;    
    this.parabolaVisible = parabolaVisible;
    this.indicatorVisible = indicatorVisible;
    }
    
}
