package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.SuperKoalio;

public class DesktopLauncher {
    
    
    public static void main (String[] arg) {
        DesktopLauncher dlNeu = new DesktopLauncher();
        StartMenu sm = new StartMenu(dlNeu);
        sm.setVisible(true);
        //LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	//new LwjglApplication(new SuperKoalio(), config);
    }
    
    public void startGame() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	new LwjglApplication(new SuperKoalio(), config);
    }
    
}
