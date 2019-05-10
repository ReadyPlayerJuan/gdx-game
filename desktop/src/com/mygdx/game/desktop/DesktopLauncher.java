package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		//uncap fps
		config.vSyncEnabled = false;
		config.foregroundFPS = 0;

		//anti aliasing
		//config.samples = 4;

		config.width = MyGdxGame.WIDTH;
		config.height = MyGdxGame.HEIGHT;
		config.title = MyGdxGame.TITLE;
		config.resizable = false;

		new LwjglApplication(new MyGdxGame(), config);
	}
}
