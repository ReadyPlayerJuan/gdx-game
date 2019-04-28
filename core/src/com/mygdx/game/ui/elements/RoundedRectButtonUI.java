package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;

public abstract class RoundedRectButtonUI extends RoundedRectUI {
    private Color defaultColor, hoverColor, pressColor;
    private boolean hover = false, prevHover = false;
    private double hoverTimer = 0;
    private boolean pressed = false, prevPressed = false;
    private double pressTimer = 0;

    private boolean pressable = true;

    public RoundedRectButtonUI(float centerX, float centerY, float width, float height, float margin, float padding, float cornerSize, Color color, Color hoverColor, Color pressColor) {
        super(centerX, centerY, width, height, margin, padding, cornerSize, color);

        this.defaultColor = color;
        this.hoverColor = hoverColor;
        this.pressColor = pressColor;
    }

    public RoundedRectButtonUI(float width, float height, float margin, float padding, float cornerSize, Color color, Color hoverColor, Color pressColor) {
        this(0, 0, width, height, margin, padding, cornerSize, color, hoverColor, pressColor);
    }

    public RoundedRectButtonUI(float margin, float padding, float cornerSize, Color color, Color hoverColor, Color pressColor) {
        this(0, 0, 0, 0, margin, padding, cornerSize, color, hoverColor, pressColor);
    }

    public RoundedRectButtonUI setPressable(boolean pressable) {
        this.pressable = pressable;
        return this;
    }

    @Override
    public void update(double delta) {
        updateChildren(delta);

        double mouseX = InputManager.getMouseX();
        double mouseY = InputManager.getFlippedMouseY();
        prevHover = hover;
        prevPressed = pressed;
        if(mouseX >= centerX - width/2 && mouseX <= centerX + width/2 && mouseY >= centerY - height/2 && mouseY <= centerY + height/2) {
            hover = true;

            if(pressable && (InputManager.keyPressed(ControlMapping.CLICK_LEFT) || InputManager.keyHeld(ControlMapping.CLICK_LEFT))) {
                pressed = true;
            } else {
                pressed = false;
            }
        } else {
            hover = false;
            pressed = false;
        }

        if(hover) {
            if(!prevHover)
                mouseOver(hoverTimer);
            else
                hover(hoverTimer);
            hoverTimer += delta;

            if(pressed) {
                if(!prevPressed)
                    press(hoverTimer, pressTimer);
                else
                    hold(hoverTimer, pressTimer);
                pressTimer += delta;
            } else if(prevPressed) {
                release(hoverTimer, pressTimer);

                pressTimer = 0;
            }
        } else if(prevHover) {
            mouseLeave(hoverTimer);

            hoverTimer = 0;
        }

        if(pressed)
            color = pressColor;
        else if(hover)
            color = hoverColor;
        else
            color = defaultColor;
    }

    public abstract void press(double hoverTimer, double pressTimer);
    public abstract void hold(double hoverTimer, double pressTimer);
    public abstract void release(double hoverTimer, double pressTimer);
    public abstract void mouseOver(double hoverTimer);
    public abstract void hover(double hoverTimer);
    public abstract void mouseLeave(double hoverTimer);

    /*@Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch, centerX-width/2, centerY-height/2, width, height);
        drawChildren(batch);
    }*/
}
