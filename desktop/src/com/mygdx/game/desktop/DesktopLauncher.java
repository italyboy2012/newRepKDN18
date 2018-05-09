package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.SuperKoalio;

public class DesktopLauncher {
    
	public static void main (String[] arg) {
            StartMenu sm = new StartMenu(this);
            
            
        }
    
    public void startGame() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	new LwjglApplication(new SuperKoalio(), config);
        
    }
}
