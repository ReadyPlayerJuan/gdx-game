package com.mygdx.game.boards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.textures.TextureManager;
import com.mygdx.game.views.GameView;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class Board {
    public static final double CELL_SIZE = 100.0;

    protected GameView parentView;

    protected CellSlotter<Wall> slottedWalls;

    protected Wall[] walls;
    private Sprite wallSprite;
    /*protected FloatBuffer vertexBuffer;
    protected IntBuffer indexBuffer;
    protected int vertexVbo, vao;*/

    public Board(GameView parentVew) {
        this.parentView = parentVew;
        slottedWalls = new CellSlotter<Wall>();
        wallSprite = new Sprite(TextureManager.getTexture(TextureData.TEST_IMAGE));
    }

    protected void slotWalls() {
        for(Wall wall: walls) {
            wall.updateSlotPositions(Board.CELL_SIZE);
        }

        slottedWalls.clear();
        slottedWalls.addAll(walls);
    }

    public void draw(SpriteBatch batch) {
        for(Wall w: walls) {
            wallSprite.setOrigin((float)w.getLength()/2, 5);
            wallSprite.setSize((float)w.getLength(), 10);
            wallSprite.setRotation((float)Math.toDegrees(w.getAngle()));
            wallSprite.setCenter((float)w.getCenterX(), (float)w.getCenterY());
            wallSprite.draw(batch);
        }
    }

    public void update(double delta) {

    }

    public Wall[] getWalls() {
        return walls;
    }

    public CellSlotter<Wall> getSlottedWalls() {
        return slottedWalls;
    }
}
