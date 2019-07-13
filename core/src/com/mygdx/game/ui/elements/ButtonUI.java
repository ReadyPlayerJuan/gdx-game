package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.ui.graphic_types.GraphicType;

public abstract class ButtonUI extends UI {
    protected Color defaultColor, hoverColor, pressColor;
    protected boolean hover = false, prevHover = false;
    protected double hoverTimer = 0;
    protected boolean pressedLeft = false, prevPressedLeft = false, pressedRight = false, prevPressedRight = false;
    protected double pressTimerLeft = 0, pressTimerRight = 0;

    protected Color currentColor;
    protected boolean pressable = true;

    public ButtonUI(Color defaultColor, Color hoverColor, Color pressColor, GraphicType graphicType) {
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        this.pressColor = pressColor;
        this.graphicType = graphicType;

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
        prevPressedLeft = pressedLeft;
        prevPressedRight = pressedRight;
        if(active && mouseX > centerX - width/2 && mouseX <= centerX + width/2 && mouseY > centerY - height/2 && mouseY <= centerY + height/2) {
            hover = true;

            pressedLeft = (pressable &&
                    (InputManager.keyPressed(ControlMapping.CLICK_LEFT) ||
                    (prevPressedLeft && InputManager.keyHeld(ControlMapping.CLICK_LEFT))));

            pressedRight = (pressable &&
                    (InputManager.keyPressed(ControlMapping.CLICK_RIGHT) ||
                            (prevPressedRight && InputManager.keyHeld(ControlMapping.CLICK_RIGHT))));
        } else {
            hover = false;
            pressedLeft = false;
            pressedRight = false;
        }

        if(hover) {
            if(!prevHover)
                mouseOver(hoverTimer);
            else
                hover(hoverTimer);
            hoverTimer += delta;

            if(pressedLeft) {
                if(!prevPressedLeft)
                    press(0, hoverTimer, pressTimerLeft);
                else
                    hold(0, hoverTimer, pressTimerLeft);
                pressTimerLeft += delta;
            } else if(prevPressedLeft) {
                release(0, hoverTimer, pressTimerLeft);

                pressTimerLeft = 0;
            }

            if(pressedRight) {
                if(!prevPressedRight)
                    press(1, hoverTimer, pressTimerRight);
                else
                    hold(1, hoverTimer, pressTimerRight);
                pressTimerRight += delta;
            } else if(prevPressedRight) {
                release(1, hoverTimer, pressTimerRight);

                pressTimerRight = 0;
            }
        } else if(prevHover) {
            mouseLeave(hoverTimer);

            hoverTimer = 0;
        }

        if(pressedLeft || pressedRight)
            currentColor = pressColor;
        else if(hover)
            currentColor = hoverColor;
        else
            currentColor = defaultColor;
    }

    @Override
    public void draw(SpriteBatch batch) {
        graphicType.setColor(currentColor).draw(batch, centerX, centerY, width, height);
        drawChildren(batch);
    }

    public abstract void press(int button, double hoverTimer, double pressTimer);
    public abstract void hold(int button, double hoverTimer, double pressTimer);
    public abstract void release(int button, double hoverTimer, double pressTimer);
    public abstract void mouseOver(double hoverTimer);
    public abstract void hover(double hoverTimer);
    public abstract void mouseLeave(double hoverTimer);

    public boolean isHover() {
        return hover;
    }
}
