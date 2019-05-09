package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.textures.TextureManager;
import com.mygdx.game.ui.FontManager;
import com.mygdx.game.views.MainView;
import com.mygdx.game.views.View;

public class MyGdxGame extends ApplicationAdapter {
	public static final String TITLE = "Test GDX Game";
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;

	private View mainView;
	private SpriteBatch batch;
	
	@Override
	public void create() {
		InputManager.init();
		TextureManager.loadTextures();
		FontManager.init();

		mainView = new MainView(WIDTH, HEIGHT);
		batch = new SpriteBatch();
		batch.enableBlending();
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();

		mainView.update(delta);
		if(InputManager.keyPressed(ControlMapping.ESCAPE))
			Gdx.app.exit();
		InputManager.update(delta);

		mainView.preDraw();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
				(Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

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
