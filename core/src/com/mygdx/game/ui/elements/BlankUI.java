package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BlankUI extends UI {
    public BlankUI(float centerX, float centerY, float width, float height, float margin, float padding) {
        super(centerX, centerY, width, height, margin, padding);
    }

    public BlankUI(float width, float height, float margin, float padding) {
        super(0, 0, width, height, margin, padding);
    }

    public BlankUI(float margin, float padding) {
        super(0, 0, 0, 0, margin, padding);
    }

    public BlankUI() {
        super(0, 0, 0, 0, 0, 0);
    }

    @Override
    public void init() {

    }

    @Override
    public void update(double delta) {
        updateChildren(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        drawChildren(batch);
    }
}
