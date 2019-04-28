package com.mygdx.game.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.textures.TextureManager;
import com.mygdx.game.ui.UI;
import com.mygdx.game.util.Util;

public class PauseMenuView extends View {
    private UI testUI1, testUI2, testUI3, testUI4;

    public PauseMenuView(View parentView, int width, int height) {
        super(parentView, width, height);

        float pauseMenuMargin = 50f;
        testUI1 = new UI(width/2, height/2, width-pauseMenuMargin*2, height-pauseMenuMargin*2, 0, 20) {
            private NinePatch sprite;
            private Color color;

            @Override
            public void init() {
                sprite = Util.createNinePatch(TextureData.ROUNDED_RECT, 30f);

                color = new Color(0.7f, 0.7f, 0.7f, 1f);
                sprite.setColor(color);
            }

            @Override
            public void update(double delta) {
                updateChildren(delta);
            }

            @Override
            public void draw(SpriteBatch batch) {
                sprite.draw(batch, centerX-width/2, centerY-height/2, width, height);
                drawChildren(batch);
            }
        };

        testUI2 = new UI(100, 100, 0, 0) {
            private NinePatch sprite;
            private Color color;

            @Override
            public void init() {
                sprite = Util.createNinePatch(TextureData.ROUNDED_RECT, 30f);

                color = new Color(1f, 1f, 1f, 1f);
                sprite.setColor(color);
            }

            @Override
            public void update(double delta) {
                updateChildren(delta);
            }

            @Override
            public void draw(SpriteBatch batch) {
                sprite.draw(batch, centerX-width/2, centerY-height/2, width, height);
                drawChildren(batch);
            }
        };

        testUI3 = new UI(100, 100, 0, 0) {
            private NinePatch sprite;
            private Color color;

            @Override
            public void init() {
                sprite = Util.createNinePatch(TextureData.ROUNDED_RECT, 30f);

                color = new Color(1f, 1f, 1f, 1f);
                sprite.setColor(color);
            }

            @Override
            public void update(double delta) {
                updateChildren(delta);
            }

            @Override
            public void draw(SpriteBatch batch) {
                sprite.draw(batch, centerX-width/2, centerY-height/2, width, height);
                drawChildren(batch);
            }
        };

        testUI4 = new UI(100, 100, 0, 0) {
            private NinePatch sprite;
            private Color color;

            @Override
            public void init() {
                sprite = Util.createNinePatch(TextureData.ROUNDED_RECT, 30f);

                color = new Color(1f, 1f, 1f, 1f);
                sprite.setColor(color);
            }

            @Override
            public void update(double delta) {
                updateChildren(delta);
            }

            @Override
            public void draw(SpriteBatch batch) {
                sprite.draw(batch, centerX-width/2, centerY-height/2, width, height);
                drawChildren(batch);
            }
        };

        testUI1.setContentAlign(UI.STRETCH, UI.CENTER);
        testUI1.addChild(testUI2);
        testUI1.addChild(testUI3);
        testUI1.addChild(testUI4);


        testUI1.format();
    }

    @Override
    public void update(double delta) {
        testUI1.update(delta);
    }

    @Override
    public void preDraw() {

    }

    @Override
    public void draw(SpriteBatch batch) {
        testUI1.draw(batch);
    }

    @Override
    public void processViewAction(String action) {

    }
}
