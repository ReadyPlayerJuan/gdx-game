package com.mygdx.game.ui.pause_menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.ui.FontManager;
import com.mygdx.game.ui.elements.ButtonUI;
import com.mygdx.game.ui.elements.TextUI;
import com.mygdx.game.ui.elements.UI;
import com.mygdx.game.ui.graphic_types.RectGT;

import static com.mygdx.game.util.Util.makeGray;

public abstract class PopupUI extends UI {
    private static final Color DEFAULT_COLOR = makeGray(0.0f, 1.0f);
    private static final Color HOVER_COLOR = makeGray(0.08f, 1.0f);
    private static final Color PRESS_COLOR = makeGray(0.16f, 1.0f);
    private static final Color TEXT_COLOR = makeGray(0.65f, 1.0f);
    private static final float ROW_HEIGHT = 24f;

    private boolean done = false;
    private ButtonUI source;
    private BitmapFont font;

    protected boolean hover = false;

    public PopupUI(double x, double y, ButtonUI source, String... options) {
        this.source = source;
        font = FontManager.aireExterior24;

        //setContentFill(false, true);
        setContentFit(true, true);

        for(int i = 0; i < options.length; i++) {
            final PopupUI popup = this;
            //final String option = options[i];
            final int index = i;

            ButtonUI optionButton = new ButtonUI(DEFAULT_COLOR, HOVER_COLOR, PRESS_COLOR, new RectGT()) {
                private final PopupUI owner = popup;
                //private final String optionName = option;
                private final int optionIndex = index;

                public void press(int button, double hoverTimer, double pressTimer) {}
                public void hold(int button, double hoverTimer, double pressTimer) {}
                public void release(int button, double hoverTimer, double pressTimer) {
                    owner.done = true;
                    owner.press(optionIndex);
                }
                public void mouseOver(double hoverTimer) {}
                public void hover(double hoverTimer) {}
                public void mouseLeave(double hoverTimer) {}
            };
            optionButton.setHeight(ROW_HEIGHT).setWidth(200);
            optionButton.setPaddingX(4).setContentFill(true, true)
                    .addChild(new TextUI(options[i], font, TEXT_COLOR).setTextAlign(Align.left, Align.center));

            addChild(optionButton);
        }

        setActive(true);
        setPosition((float)x + width/2, (float)y - height/2);
        format();
    }

    public PopupUI(ButtonUI source, String... options) {
        this(InputManager.getMouseX(), InputManager.getFlippedMouseY(), source, options);
    }

    @Override
    public void update(double delta) {
        updateChildren(delta);

        double mouseX = InputManager.getMouseX();
        double mouseY = InputManager.getFlippedMouseY();
        if(active && mouseX > centerX - width/2 && mouseX <= centerX + width/2 && mouseY > centerY - height/2 && mouseY <= centerY + height/2) {
            hover = true;
        } else {
            hover = false;
        }
    }

    public abstract void press(int option);

    public boolean shouldBeDestroyed() {
        return done || (!hover && !source.isHover());
    }
}
