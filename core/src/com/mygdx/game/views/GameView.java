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
import com.mygdx.game.entities.PlayerData;
import com.mygdx.game.entities.enemies.Dummy;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.weapons.WeaponType;

public class GameView extends View {
    private PauseMenuView pauseMenuView;
    private boolean paused = false;

    private EntityManager entityManager;
    private Board board;
    private BoardCamera camera;

    private FrameBuffer gameFrameBuffer;
    private SpriteBatch bufferBatch;

    public GameView(View parentView, int width, int height) {
        super(parentView, width, height);

        PlayerData.setEquippedWeapon(0, WeaponType.generateRandomWeapon());
        PlayerData.setEquippedWeapon(1, WeaponType.generateRandomWeapon());
        PlayerData.addWeaponToInventory(WeaponType.generateRandomWeapon());
        PlayerData.addWeaponToInventory(WeaponType.generateRandomWeapon());
        PlayerData.addWeaponToInventory(WeaponType.generateRandomWeapon());
        PlayerData.addWeaponToInventory(WeaponType.generateRandomWeapon());
        PlayerData.addWeaponToInventory(WeaponType.generateRandomWeapon());
        PlayerData.addWeaponToInventory(WeaponType.generateRandomWeapon());
        PlayerData.addWeaponToInventory(WeaponType.generateRandomWeapon());
        PlayerData.addWeaponToInventory(WeaponType.generateRandomWeapon());
        PlayerData.addWeaponToInventory(WeaponType.generateRandomWeapon());

        pauseMenuView = new PauseMenuView(this, width, height);

        entityManager = new EntityManager();
        board = new BoardPreset1(this);
        camera = new BoardCamera(width, height);

        gameFrameBuffer = new FrameBuffer(Pixmap.Format.RGB888, width, height, false);
        //gameFrameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        bufferBatch = new SpriteBatch();

        Player p = new Player();
        p.setBoardCamera(camera);
        camera.follow(p, 16);

        new Dummy(100, 100);
        new Dummy(100, 150);
        new Dummy(100, 200);
        new Dummy(100, 250);
        new Dummy(100, 300);
    }

    @Override
    public void update(double delta) {
        if(InputManager.keyPressed(ControlMapping.PAUSE_GAME)) {
            if(paused) {
                paused = false;
                pauseMenuView.setFocused(false);
            } else {
                paused = true;
                pauseMenuView.setFocused(true);
                pauseMenuView.processViewAction("reset pause menu");
            }
        }

        if(!paused) {
            board.update(delta);
            entityManager.updateEntities(delta, board);
            camera.update(delta);
        }

        pauseMenuView.update(delta);
    }

    @Override
    public void preDraw() {
        gameFrameBuffer.bind();
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT |
                (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));

        bufferBatch.setProjectionMatrix(camera.combined);
        bufferBatch.begin();
        board.draw(bufferBatch);
        entityManager.draw(bufferBatch);
        bufferBatch.end();

        FrameBuffer.unbind();

        pauseMenuView.preDraw();
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(gameFrameBuffer.getColorBufferTexture(), 0, 0);

        pauseMenuView.draw(batch);
    }

    @Override
    public void processViewAction(String action) {
        sendViewAction(action); //pass action up the chain
    }

    @Override
    public void dispose() {
        super.dispose();
        gameFrameBuffer.dispose();
        bufferBatch.dispose();
    }
}