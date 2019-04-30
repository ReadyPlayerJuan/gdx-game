package com.mygdx.game.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.ui.FontManager;
import com.mygdx.game.ui.elements.*;
import com.mygdx.game.ui.pause_menu.WeaponInfoUI;
import com.mygdx.game.weapons.guns.Pistol;
import static com.mygdx.game.util.Util.makeGray;

public class PauseMenuView extends View {
    private UI parentUI;
    private SwapperUI inventoryViewSwapper;
    private WeaponInfoUI weaponLeftInfo, weaponRightInfo;

    private final Color bodyColor =         makeGray(0.85f, 1);
    private final Color darkBodyColor =     makeGray(0.75f, 1);
    private final Color borderColor =       makeGray(0.50f, 1);
    private final Color textColor =         makeGray(0.06f, 1);
    private final Color buttonColor =       bodyColor;
    private final Color buttonHoverColor =  makeGray(0.8f, 1);
    private final Color buttonPressColor =  makeGray(0.7f, 1);
    private final float cornerSize = 15f;
    private final float borderSize = 2f;
    private final float maxWidth = 1300;
    private final float maxHeight = 850;

    public PauseMenuView(View parentView, int width, int height) {
        super(parentView, width, height);

        float screenPaddingX = Math.max(35, (width - maxWidth)/2);
        float screenPaddingY = Math.max(35, (height - maxHeight)/2);

        parentUI = new BlankUI()
                .setPosition(width/2, height/2).setSize(width, height).setPadding(screenPaddingX, screenPaddingY)
                .setVertical(true).setContentFill(true, true);

        UI sectionButtonContainer = new BlankUI()
                .setHeight(72).setPadding(10, 0)
                .setVertical(false).setContentFill(true, true).addToParent(parentUI);

        UI sectionButton1 = new RoundedRectButtonUI(cornerSize, buttonColor, buttonHoverColor, buttonPressColor) {
            @Override
            public void press(double hoverTimer, double pressTimer) {
                inventoryViewSwapper.setAllVisible(false).setViewVisible("equipment", true);
            }
            public void hold(double hoverTimer, double pressTimer) {}
            public void release(double hoverTimer, double pressTimer) {}
            public void mouseOver(double hoverTimer) {}
            public void hover(double hoverTimer) {}
            public void mouseLeave(double hoverTimer) {}
        }.setBorder(borderSize, borderColor).setMargin(5, 0).setPadding(20, 20).addToParent(sectionButtonContainer);
        UI sectionButtonText1 = new TextUI("Equipment", FontManager.aireExterior48, textColor)
                .setTextAlign(Align.center, Align.center).addToParent(sectionButton1);

        UI sectionButton2 = new RoundedRectButtonUI(cornerSize, buttonColor, buttonHoverColor, buttonPressColor) {
            @Override
            public void press(double hoverTimer, double pressTimer) {
                inventoryViewSwapper.setAllVisible(false).setViewVisible("inventory", true);
            }
            public void hold(double hoverTimer, double pressTimer) {}
            public void release(double hoverTimer, double pressTimer) {}
            public void mouseOver(double hoverTimer) {}
            public void hover(double hoverTimer) {}
            public void mouseLeave(double hoverTimer) {}
        }.setBorder(borderSize, borderColor).setMargin(5, 0).setPadding(20, 20).addToParent(sectionButtonContainer);
        UI sectionButtonText2 = new TextUI("Inventory", FontManager.aireExterior48, textColor)
                .setTextAlign(Align.center, Align.center).addToParent(sectionButton2);

        UI sectionButton3 = new RoundedRectButtonUI(cornerSize, buttonColor, buttonHoverColor, buttonPressColor) {
            @Override
            public void press(double hoverTimer, double pressTimer) {
                inventoryViewSwapper.setAllVisible(false).setViewVisible("upgrades", true);
            }
            public void hold(double hoverTimer, double pressTimer) {}
            public void release(double hoverTimer, double pressTimer) {}
            public void mouseOver(double hoverTimer) {}
            public void hover(double hoverTimer) {}
            public void mouseLeave(double hoverTimer) {}
        }.setBorder(borderSize, borderColor).setMargin(5, 0).setPadding(20, 20).addToParent(sectionButtonContainer);
        UI sectionButtonText3 = new TextUI("Upgrades", FontManager.aireExterior48, textColor)
                .setTextAlign(Align.center, Align.center).addToParent(sectionButton3);



        inventoryViewSwapper = new SwapperUI();
        inventoryViewSwapper.addToParent(parentUI);



        UI equipmentSection = new RoundedRectUI(cornerSize, bodyColor)
                .setBorder(borderSize, borderColor).setPadding(20, 20).setVertical(false).setContentFill(true, true);

        weaponLeftInfo = new WeaponInfoUI(cornerSize, borderSize, bodyColor, darkBodyColor, borderColor, textColor);
        weaponLeftInfo.addToParent(equipmentSection);

        UI weaponCenterInfo = new BlankUI()
                .setPadding(20, 20).setMargin(60, 0).addToParent(equipmentSection);

        weaponRightInfo = new WeaponInfoUI(cornerSize, borderSize, bodyColor, darkBodyColor, borderColor, textColor);
        weaponRightInfo.addToParent(equipmentSection);

        weaponLeftInfo.setWeapon(new Pistol());
        weaponRightInfo.setWeapon(new Pistol());



        UI inventorySection = new RoundedRectUI(cornerSize, bodyColor)
                .setBorder(borderSize, borderColor).setPadding(20, 20).setVertical(false)
                .setContentFill(true, true).setContentAlign(UI.CENTER, UI.CENTER);

        UI inventoryTitleText = new TextUI("INVENTORY", FontManager.aireExterior48, textColor)
                .fitText().setTextAlign(Align.center, Align.center).addToParent(inventorySection);



        UI upgradesSection = new RoundedRectUI(cornerSize, bodyColor)
                .setBorder(borderSize, borderColor).setPadding(20, 20).setVertical(false)
                .setContentFill(true, true).setContentAlign(UI.CENTER, UI.CENTER);

        UI upgradesTitleText = new TextUI("UPGRADES", FontManager.aireExterior48, textColor)
                .fitText().setTextAlign(Align.center, Align.center).addToParent(upgradesSection);



        inventoryViewSwapper
                .addChild("equipment", equipmentSection)
                .addChild("inventory", inventorySection)
                .addChild("upgrades", upgradesSection)
                .setAllVisible(false).setViewVisible("equipment", true);


        parentUI.format();
    }

    @Override
    public void setFocused(boolean focused) {
        super.setFocused(focused);

        parentUI.setVisible(focused);
        parentUI.update(0);
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
