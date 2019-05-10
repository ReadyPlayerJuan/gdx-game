package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FloaterUI extends UI {
    protected boolean snap;
    protected float floatSpeed = 10.0f;
    protected float offsetX, offsetY;
    protected float targetOffsetX, targetOffsetY;

    public FloaterUI() {

    }

    @Override
    public void update(double delta) {
        if(snap) {
            offsetX = targetOffsetX;
            offsetY = targetOffsetY;
        } else {
            offsetX += (float)(Math.signum(targetOffsetX - offsetX) * Math.min(1, delta * floatSpeed) * Math.abs(targetOffsetX - offsetX));
            offsetY += (float)(Math.signum(targetOffsetY - offsetY) * Math.min(1, delta * floatSpeed) * Math.abs(targetOffsetY - offsetY));
        }

        centerX += offsetX;
        centerY += offsetY;
        format();
        centerX -= offsetX;
        centerY -= offsetY;

        updateChildren(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        graphicType.draw(batch, centerX, centerY, width, height);
        drawChildren(batch);
    }

    public FloaterUI setSnap(boolean snap) {
        this.snap = snap;
        return this;
    }

    public FloaterUI setFloatSpeed(float floatSpeed) {
        this.floatSpeed = floatSpeed;
        return this;
    }

    public FloaterUI setTargetOffset(float targetOffsetX, float targetOffsetY) {
        this.targetOffsetX = targetOffsetX;
        this.targetOffsetY = targetOffsetY;
        return this;
    }

    public FloaterUI setOffset(float offsetX, float offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        return this;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }
}
