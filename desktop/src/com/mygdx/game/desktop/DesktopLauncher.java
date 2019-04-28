package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//uncap fps
		config.vSyncEnabled = false;
		config.foregroundFPS = 60;

		config.width = MyGdxGame.WIDTH;
		config.height = MyGdxGame.HEIGHT;
		config.title = MyGdxGame.TITLE;

		new LwjglApplication(new MyGdxGame(), config);
	}
}
