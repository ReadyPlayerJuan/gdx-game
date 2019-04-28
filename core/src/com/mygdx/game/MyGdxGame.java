package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.textures.TextureManager;
import com.mygdx.game.ui.FontManager;
import com.mygdx.game.views.MainView;
import com.mygdx.game.views.View;

public class MyGdxGame extends ApplicationAdapter {
	public static final String TITLE = "Test GDX Game";
	public static final int WIDTH = 1300;
	public static final int HEIGHT = 800;

	private View mainView;
	private SpriteBatch batch;
	
	@Override
	public void create() {
		InputManager.init();
		TextureManager.loadTextures();
		FontManager.init();

		mainView = new MainView(WIDTH, HEIGHT);
		batch = new SpriteBatch();
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();

		mainView.update(delta);
		InputManager.update(delta);

		mainView.preDraw();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		mainView.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		mainView.dispose();
		TextureManager.dispose();
		FontManager.dispose();
	}
}
