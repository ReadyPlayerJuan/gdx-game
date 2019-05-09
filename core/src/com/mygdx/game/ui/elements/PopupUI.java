package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.views.View;

public class PopupUI extends UI {
    private View view;
    private boolean hover = false;

    public PopupUI(View view) {
        this.view = view;
    }

    @Override
    public void update(double delta) {
        centerX = Math.max(getOuterWidth()/2, Math.min(view.getWidth() - (getOuterWidth()/2), centerX));
        centerY = Math.max(getOuterHeight()/2, Math.min(view.getHeight() - (getOuterHeight()/2), centerY));

        double mouseX = InputManager.getMouseX();
        double mouseY = InputManager.getFlippedMouseY();
        hover = (mouseX > centerX - width/2 && mouseX <= centerX + width/2 && mouseY > centerY - height/2 && mouseY <= centerY + height/2);

        updateChildren(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        graphicType.draw(batch, centerX, centerY, width, height);
        drawChildren(batch);
    }

    public boolean hover() {
        return hover;
    }
}
