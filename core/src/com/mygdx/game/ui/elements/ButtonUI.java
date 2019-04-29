package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;

public abstract class ButtonUI extends UI {
    protected Color defaultColor, hoverColor, pressColor;
    protected boolean hover = false, prevHover = false;
    protected double hoverTimer = 0;
    protected boolean pressed = false, prevPressed = false;
    protected double pressTimer = 0;

    protected Color currentColor;
    protected boolean pressable = true;

    public ButtonUI(Color defaultColor, Color hoverColor, Color pressColor) {
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        this.pressColor = pressColor;

        currentColor = defaultColor;
    }

    public ButtonUI setPressable(boolean pressable) {
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

            if(pressable && (InputManager.keyPressed(ControlMapping.CLICK_LEFT) || (prevPressed && InputManager.keyHeld(ControlMapping.CLICK_LEFT)))) {
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
            currentColor = pressColor;
        else if(hover)
            currentColor = hoverColor;
        else
            currentColor = defaultColor;
    }

    public abstract void press(double hoverTimer, double pressTimer);
    public abstract void hold(double hoverTimer, double pressTimer);
    public abstract void release(double hoverTimer, double pressTimer);
    public abstract void mouseOver(double hoverTimer);
    public abstract void hover(double hoverTimer);
    public abstract void mouseLeave(double hoverTimer);
}
