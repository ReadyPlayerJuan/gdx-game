package com.mygdx.game.ui.pause_menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.ui.FontManager;
import com.mygdx.game.ui.elements.*;
import com.mygdx.game.weapons.Weapon;

public class WeaponInfoUI extends UI {
    private final float verticalSpacing = 8;
    private final int weaponTitleMaxChars = 12;

    private TextUI weaponInfoSubTitle, weaponTextLeft, weaponTextRight;
    private RoundedRectEditableTextUI weaponInfoTitle;

    public WeaponInfoUI(float cornerSize, float borderSize, Color bodyColor, Color darkBodyColor, Color borderColor, Color textColor) {
        setContentFill(true, true);

        UI weaponInfo = new RoundedRectUI(cornerSize, darkBodyColor)
                .setPadding(0, 0).setContentFill(true, true).addToParent(this);

        UI weaponTitleContainer = new RoundedRectUI(cornerSize, bodyColor)
                .setBorder(borderSize, borderColor).setContentFit(true, false).setContentFill(false, true)
                .setPadding(6, 6).setMargin(25, verticalSpacing).addToParent(weaponInfo);

        weaponInfoTitle = new RoundedRectEditableTextUI(cornerSize,
                new Color(0, 0, 0, 0), new Color(0, 0, 0, 0.1f), new Color(0, 0, 0, 0.2f),
                "WEAPON NAME", FontManager.aireExterior48, textColor, weaponTitleMaxChars);
        weaponInfoTitle.setPadding(0, 6).setContentFit(true, false);
        weaponInfoTitle.updateText();
        weaponInfoTitle.addToParent(weaponTitleContainer);

        weaponInfoSubTitle = new TextUI("rarity, star rating", FontManager.aireExterior24, textColor);
        weaponInfoSubTitle.fitText().setMargin(0, 2).addToParent(weaponTitleContainer);

        UI weaponTextContainer = new BlankUI()
                .setVertical(false).setContentFill(true, true).addToParent(weaponInfo);

        UI weaponTextContainerLeft = new BlankUI()
                .setPadding(10, 5).setContentFill(true, true).addToParent(weaponTextContainer);

        weaponTextLeft = new TextUI("aaaaa\nbbbbbb\nccc\ndddddd\neeeeee\nffff", FontManager.aireExterior24, textColor);
        weaponTextLeft.setTextAlign(Align.right, Align.top).addToParent(weaponTextContainerLeft);

        UI weaponTextSpacer = new RoundedRectUI(0, borderColor)
                .setWidth(2).addToParent(weaponTextContainer);

        UI weaponTextContainerRight = new BlankUI()
                .setPadding(10, 5).setContentFill(true, true).addToParent(weaponTextContainer);

        weaponTextRight = new TextUI("aaaaa\nbbbbbb\nccc\ndddddd\neeeeee\nffff", FontManager.aireExterior24, textColor);
        weaponTextRight.setTextAlign(Align.left, Align.top).addToParent(weaponTextContainerRight);

        UI weaponInfoSpacer = new BlankUI().setHeight(verticalSpacing).addToParent(weaponInfo);
    }

    public void setWeapon(Weapon weapon) {
        weaponInfoTitle.setText(weapon.getName()).updateText();
        //weaponInfoSubTitle.setText()
        weaponTextLeft.setText(weapon.getStatNamesString()).updateText();
        weaponTextRight.setText(weapon.getStatValuesString()).updateText();
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
