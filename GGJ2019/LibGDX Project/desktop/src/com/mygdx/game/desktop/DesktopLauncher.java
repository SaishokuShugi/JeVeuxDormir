package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Test";
	    config.width = 1280;
	    config.height = 720;
		config.forceExit = false;
		//config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
	    config.resizable=false;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
