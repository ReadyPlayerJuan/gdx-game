package com.mygdx.game.ui.pause_menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.ui.FontManager;
import com.mygdx.game.ui.elements.*;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.weapons.stats.WeaponStat;
import static com.mygdx.game.util.Util.makeGray;

public class WeaponInfoUI extends UI {
    /*private final float verticalSpacing = 8;
    private final int weaponTitleMaxChars = 12;

    private RoundedRectEditableTextUI weaponInfoTitle;
    private TextUI weaponInfoSubTitle, weaponStatNames, weaponStatValues, weaponStatBaseValues, weaponStatRolls;
    private UI weaponStatsButtonsContainer;
    private SwapperUI weaponStatsRightSwapper;

    private WeaponStatPopupUI statPopup;
    private int statPopupSide;
    private final double popupHoverDelay = 0.5;

    private Weapon weapon = null;

    public WeaponInfoUI(WeaponStatPopupUI statPopup, int statPopupSide, float cornerSize, float borderSize, Color bodyColor, Color darkBodyColor, Color borderColor, Color textColor) {
        this.statPopup = statPopup;
        this.statPopupSide = statPopupSide;

        setContentFill(true, true);

        UI weaponInfo = new RoundedRectUI(cornerSize, darkBodyColor)
                .setPadding(0, 0).setContentFill(true, true).addToParent(this);

        UI weaponTitleContainer = new RoundedRectUI(cornerSize, bodyColor)
                .setBorder(borderSize, borderColor).setContentFit(true, false).setContentFill(false, true)
                .setPadding(6, 6).setMargin(25, verticalSpacing).addToParent(weaponInfo);

        weaponInfoTitle = new RoundedRectEditableTextUI(cornerSize,
                new Color(0, 0, 0, 0), new Color(0, 0, 0, 0.15f), new Color(0, 0, 0, 0.3f),
                "WEAPON NAME", FontManager.aireExterior48, textColor, weaponTitleMaxChars) {
            @Override
            public void textChanged(String newText) {
                if(weapon != null) {
                    weapon.setName(newText);
                }
            }
        };
        weaponInfoTitle.setPadding(0, 6).setContentFit(true, false);
        weaponInfoTitle.updateText();
        weaponInfoTitle.addToParent(weaponTitleContainer);

        weaponInfoSubTitle = new TextUI("rarity, star rating", FontManager.aireExterior24, textColor);
        weaponInfoSubTitle.fitText().setMargin(0, 2).addToParent(weaponTitleContainer);

        SwapperUI weaponStatsContainerSwapper = new SwapperUI();
        weaponStatsContainerSwapper.addToParent(weaponInfo);

        UI weaponStatsContainer = new BlankUI()
                .setVertical(false).setContentFill(true, true);//.addToParent(weaponInfo);

        weaponStatsButtonsContainer = new BlankUI().setPaddingY(2)
                .setVertical(true).setContentAlign(UI.TOP, UI.CENTER).setContentFill(false, true);//.addToParent(weaponInfo);


        weaponStatsContainerSwapper.addChild("stats", weaponStatsContainer).addChild("buttons", weaponStatsButtonsContainer);
        weaponStatsContainerSwapper.setAllVisible(true);


        UI weaponStatNamesContainerLeft = new BlankUI()
                .setPadding(10, 5).setContentFill(true, true).addToParent(weaponStatsContainer);
        weaponStatNames = new TextUI("aaaaa\nbbbbbb\nccc\ndddddd\neeeeee\nffff", FontManager.aireExterior24, textColor);
        weaponStatNames.setTextAlign(Align.right, Align.top).addToParent(weaponStatNamesContainerLeft);


        UI weaponStatsTextSpacer = new RoundedRectUI(0, borderColor)
                .setWidth(2).addToParent(weaponStatsContainer);


        weaponStatsRightSwapper = new SwapperUI();
        weaponStatsRightSwapper.addToParent(weaponStatsContainer);

        UI weaponStatValuesContainer = new BlankUI()
                .setPadding(10, 5).setContentFill(true, true);
        weaponStatValues = new TextUI("aaaaa\nbbbbbb\nccc\ndddddd\neeeeee\nffff", FontManager.aireExterior24, textColor);
        weaponStatValues.setTextAlign(Align.left, Align.top).addToParent(weaponStatValuesContainer);

        UI weaponStatBaseValuesContainer = new BlankUI()
                .setPadding(10, 5).setContentFill(true, true);
        weaponStatBaseValues = new TextUI("aaaaa\nbbbbbb\nccc\ndddddd\neeeeee\nffff", FontManager.aireExterior24, textColor);
        weaponStatBaseValues.setTextAlign(Align.left, Align.top).addToParent(weaponStatBaseValuesContainer);


        UI weaponStatRollsContainer = new BlankUI()
                .setPadding(10, 5).setContentFill(true, true);
        weaponStatRolls = new TextUI("aaaaa\nbbbbbb\nccc\ndddddd\neeeeee\nffff", FontManager.aireExterior24, textColor);
        weaponStatRolls.setTextAlign(Align.right, Align.top).addToParent(weaponStatRollsContainer);


        weaponStatsRightSwapper.addChild("values", weaponStatValuesContainer).addChild("base values", weaponStatBaseValuesContainer).addChild("rolls", weaponStatRollsContainer);
        weaponStatsRightSwapper.setAllVisible(false).setViewVisible("values", true);

        UI weaponInfoSpacer = new BlankUI().setHeight(verticalSpacing).addToParent(weaponInfo);
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;

        if(weapon != null) {
            weaponInfoTitle.setText(weapon.getName()).updateText();
            //weaponInfoSubTitle.setText()
            weaponStatNames.setText(weapon.getStatNamesString()).updateText();
            weaponStatValues.setText(weapon.getStatValuesString()).updateText();
            weaponStatBaseValues.setText(weapon.getStatBaseValuesString()).updateText();
            weaponStatRolls.setText(weapon.getStatRollsString()).updateText();

            weaponStatsButtonsContainer.removeChildren();

            for(final WeaponStat statType: weapon.getAvailableStats()) {
                final Weapon w = weapon;
                new RoundedRectButtonUI(0, makeGray(0, 0), makeGray(0, 0.15f), makeGray(0, 0.15f)) {
                    public void press(double hoverTimer, double pressTimer) {
                        if(!statPopup.isActive()) {
                            showPopup();
                        }
                    }
                    public void hold(double hoverTimer, double pressTimer) {}
                    public void release(double hoverTimer, double pressTimer) {}
                    public void mouseOver(double hoverTimer) {}
                    public void hover(double hoverTimer) {
                        if(!statPopup.isActive() && hoverTimer > popupHoverDelay) {
                            showPopup();
                        }
                    }
                    public void mouseLeave(double hoverTimer) {
                        if(statPopup.isActive() && statPopup.getStatType() == statType) {
                            statPopup.hide();
                        }
                    }
                    private void showPopup() {
                        statPopup.show(w, statType, getCenterX() + statPopupSide * getOuterWidth()/2, getCenterY() + getOuterHeight()/2, statPopupSide, -1);
                    }
                }.setHeight(21).addToParent(weaponStatsButtonsContainer);
            }
        } else {
            weaponInfoTitle.setText("").updateText();
            //weaponInfoSubTitle.setText()
            weaponStatNames.setText("").updateText();
            weaponStatValues.setText("").updateText();
            weaponStatBaseValues.setText("").updateText();
            weaponStatRolls.setText("").updateText();

            weaponStatsButtonsContainer.removeChildren();
        }
    }

    @Override
    public void update(double delta) {
        if(InputManager.keyHeld(ControlMapping.SHIFT)) {
            weaponStatsRightSwapper.setAllVisible(false).setViewVisible("base values", true).setViewVisible("rolls", true);
        } else {
            weaponStatsRightSwapper.setAllVisible(false).setViewVisible("values", true);
        }

        updateChildren(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        drawChildren(batch);
    }*/
}
