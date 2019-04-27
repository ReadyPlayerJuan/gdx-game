package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.mygdx.game.boards.Board;
import com.mygdx.game.boards.BoardPreset1;
import com.mygdx.game.boards.BoardCamera;
import com.mygdx.game.entities.EntityManager;
import com.mygdx.game.entities.Player;

public class GameView extends View {
    private EntityManager entityManager;
    private Board board;
    private BoardCamera camera;

    private FrameBuffer gameFrameBuffer;
    private SpriteBatch batch;

    public GameView(View parentView, int width, int height) {
        super(parentView, width, height);

        //PlayerData.setEquippedWeapon(0, new Pistol());

        entityManager = new EntityManager();
        board = new BoardPreset1(this);
        camera = new BoardCamera(width, height);

        gameFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
        batch = new SpriteBatch();

        Player p = new Player();
        p.setBoardCamera(camera);
        camera.follow(p, 16);

        /*new Dummy(100, 100);
        new Dummy(100, 150);
        new Dummy(100, 200);
        new Dummy(100, 250);
        new Dummy(100, 300);*/
    }

    @Override
    public void update(double delta) {
        board.update(delta);

        entityManager.updateEntities(delta, board);

        camera.update(delta);

        /*if(InputManager.keyPressed(ControlMapping.PAUSE_GAME.keyCode)) {
            if(paused) {
                paused = false;
                pauseMenuView.setFocused(false);
            } else {
                paused = true;
                pauseMenuView.setFocused(true);
                pauseMenuView.processViewAction("reset pause menu");
            }
        }

        if(!paused)
            entityManager.updateEntities(delta, board);

        if(!paused)
            boardCamera.update(delta);*/
    }

    @Override
    public void preDraw() {
        gameFrameBuffer.bind();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        board.draw(batch);
        entityManager.draw(batch);
        batch.end();

        FrameBuffer.unbind();
    }

    @Override
    public void draw(SpriteBatch batch) {
        //Gdx.gl.glClearColor(1, 1, 1, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.draw(gameFrameBuffer.getColorBufferTexture(), 0, 0);
        //batch.begin();
        //board.draw(batch);
        //batch.end();
    }

    @Override
    public void processViewAction(String action) {
        sendViewAction(action); //pass action up the chain
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}