package com.mygdx.game.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.ui.FontManager;
import com.mygdx.game.ui.elements.*;

public class PauseMenuView extends View {
    private UI parentUI;

    private final Color bodyColor = makeGray(0.85f, 1);
    private final Color borderColor = makeGray(0.60f, 1);
    private final Color textColor = makeGray(0.95f, 1);
    private final float cornerSize = 20f;

    private static Color makeGray(float f, float a) {
        return new Color(f, f, f, a);
    }

    public PauseMenuView(View parentView, int width, int height) {
        super(parentView, width, height);

        parentUI = new BlankUI(width/2, height/2, width, height, 0, 50).setContentFill(true, true);
        //BlankUI sectionButtonContainer = new BlankUI()
        UI mainSection = new RoundedRectUI(0, 20, cornerSize, bodyColor)
                .setBorder(2f, borderColor).addToParent(parentUI);


        parentUI.format();

        /*RoundedRectUI testUI2 = new RoundedRectUI(100, 100, 0, 0, 20, new Color(1, 1, 1, 1));
        testUI2.setBorder(2f, new Color(0, 0, 0, 1));

        RoundedRectUI testUI3 = new RoundedRectUI(100, 100, 0, 0, 20, new Color(1, 1, 1, 1));
        RoundedRectButtonUI testUI4 = new RoundedRectButtonUI(100, 100, 0, 0, 20,
                new Color(1, 1, 1, 1), new Color(0.9f, 0.9f, 0.9f, 1), new Color(0.8f, 0.8f, 0.8f, 1)) {
            public void press(double hoverTimer, double pressTimer) { }
            public void hold(double hoverTimer, double pressTimer) { }
            public void release(double hoverTimer, double pressTimer) { }
            public void mouseOver(double hoverTimer) { }
            public void hover(double hoverTimer) { }
            public void mouseLeave(double hoverTimer) { }
        };
        testUI4.setBorder(2f, new Color(0, 0, 0, 1));

        testUI5 = new TextUI(0, 0, FontManager.debugFont24, "BUTTON", Color.BLACK);
        testUI5.setContentFit(true, true);
        testUI5.setTextAlign(Align.center, Align.center);

        testUI.setVertical(false);
        testUI.setContentAlign(UI.CENTER, UI.STRETCH);
        //testUI.setContentAlign(UI.STRETCH, UI.CENTER);
        testUI.addChild(testUI2);
        testUI.addChild(testUI3);
        testUI.addChild(testUI4);

        testUI4.addChild(testUI5);


        testUI.format();*/
    }

    @Override
    public void update(double delta) {
        parentUI.update(delta);
    }

    @Override
    public void preDraw() {

    }

    @Override
    public void draw(SpriteBatch batch) {
        parentUI.draw(batch);
    }

    @Override
    public void processViewAction(String action) {

    }
}
