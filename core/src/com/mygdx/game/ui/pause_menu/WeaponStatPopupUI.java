package com.mygdx.game.ui.pause_menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.ui.FontManager;
import com.mygdx.game.ui.elements.BlankUI;
import com.mygdx.game.ui.elements.RoundedRectUI;
import com.mygdx.game.ui.elements.TextUI;
import com.mygdx.game.ui.elements.UI;
import com.mygdx.game.views.View;
import com.mygdx.game.weapons.Weapon;
import com.mygdx.game.weapons.stats.WeaponStat;

import java.util.HashMap;
import java.util.Map;

public class WeaponStatPopupUI extends UI {
    private View view;
    private boolean hover = false;

    private UI frame, titleBox, descriptionBox, baseValueContainer;
    private TextUI statNameText;
    private TextUI statDescriptionText;
    private TextUI baseValueText;
    private WeaponRollSliderUI baseValueRollSlider;

    private Weapon weapon;
    private WeaponStat statType;

    private final float POPUP_WIDTH = 300;
    private final float DESCRIPTION_WIDTH = POPUP_WIDTH - 60;
    //private final float POPUP_HEIGHT = 400;

    private Map<WeaponStat, String> statDescriptions = new HashMap<WeaponStat, String>() {{
        put(WeaponStat.BULLET_DAMAGE, "The damage that each bullet deals to enemies.");
        put(WeaponStat.BULLET_SPEED, "The speed at which bullets travel.");
        put(WeaponStat.BULLET_NUM, "The number of bullets shot at once.");
        put(WeaponStat.BULLET_SIZE, "The physical size of each bullet.");
        put(WeaponStat.BULLET_KNOCKBACK, "The knockback enemies receive upon being hit");
        put(WeaponStat.WEAPON_SPREAD, "The amount that the fire angle can randomly vary.");
        put(WeaponStat.WEAPON_FIRE_RATE, "The speed at which bullets are fired.");
        put(WeaponStat.WEAPON_KICK, "The knockback the player takes upon firing.");
    }};

    public WeaponStatPopupUI(View view, float cornerSize, float borderSize, Color bodyColor, Color darkBodyColor, Color borderColor, Color textColor) {
        this.view = view;

        setContentFit(true, true);

        frame = new RoundedRectUI(cornerSize, bodyColor)
                .setBorder(borderSize, borderColor).setWidth(POPUP_WIDTH).setPadding(10, 10)
                .setContentFill(false, true).setContentFit(true, false)
                .setContentAlign(UI.TOP, UI.CENTER);
        titleBox = new RoundedRectUI(cornerSize, bodyColor)
                .setBorder(borderSize, borderColor).setMarginX(24).setPadding(8, 8)
                .setContentFit(true, false);
        statNameText = new TextUI("STAT NAME", FontManager.aireExterior36, textColor);
        statNameText.setTextAlign(Align.center, Align.center);

        descriptionBox = new RoundedRectUI(cornerSize, darkBodyColor)
                .setMargin(8, 8).setPadding(8, 8)
                .setContentFit(true, false).setContentFill(false, true);
        statDescriptionText = new TextUI("stat descrption blah blah blah blah blah blah blah blah blah blah", FontManager.aireExterior24, textColor);
        statDescriptionText.setWrap(true);
        statDescriptionText.setTextAlign(Align.center, Align.top);


        baseValueContainer = new BlankUI().setMargin(8, 8)
                .setContentFit(true, false).setContentAlign(UI.CENTER, UI.CENTER)
                .setVertical(false).addToParent(frame);
        new TextUI("Base Value:", FontManager.aireExterior24, textColor).fitText()
                .addToParent(baseValueContainer);
        new BlankUI().setWidth(15).addToParent(baseValueContainer);
        UI baseValueBox = new RoundedRectUI(cornerSize/2, darkBodyColor).setPadding(6, 6)
                .setContentFit(true, true).addToParent(baseValueContainer);
        baseValueText = new TextUI("99", FontManager.aireExterior24, textColor).fitText();
        baseValueText.addToParent(baseValueBox);
        baseValueBox.format();
        baseValueContainer.format();


        baseValueRollSlider = new WeaponRollSliderUI(textColor);
        baseValueRollSlider.setInfo("-99", "99", -0.5f);
        baseValueRollSlider.setMargin(8, 8).setHeight(20).addToParent(frame);


        formatInfoBox();
    }

    private void formatInfoBox() {
        setSize(0, 0);
        removeChildren();
        frame.setHeight(0).removeChildren();
        titleBox.setHeight(0).removeChildren();
        descriptionBox.setHeight(0).removeChildren();

        statNameText.fitText();
        statNameText.addToParent(titleBox);
        titleBox.format();
        titleBox.addToParent(frame);

        statDescriptionText.setWidth(DESCRIPTION_WIDTH);
        statDescriptionText.fitText().addToParent(descriptionBox);
        descriptionBox.format();
        descriptionBox.addToParent(frame);

        baseValueContainer.addToParent(frame);
        baseValueRollSlider.addToParent(frame);

        frame.addToParent(this);
        format();
    }

    public void show(Weapon weapon, WeaponStat statType, float cornerX, float cornerY, int xdir, int ydir) {
        this.weapon = weapon;
        this.statType = statType;

        double[] stat = weapon.getStat(statType);
        double[] defaultStat = weapon.getDefaultStat(statType);

        statNameText.setText(statType.getName());
        if(statDescriptions.get(statType) != null)
            statDescriptionText.setText(statDescriptions.get(statType));
        else
            statDescriptionText.setText("DESCRIPTION MISSING");

        statDescriptionText.fitText();

        setCenterX(cornerX + xdir * getOuterWidth()/2);
        setCenterY(cornerY + ydir * getOuterHeight()/2);

        formatInfoBox();

        centerX = Math.max(getOuterWidth() / 2, Math.min(view.getWidth() - (getOuterWidth() / 2), centerX));
        centerY = Math.max(getOuterHeight() / 2, Math.min(view.getHeight() - (getOuterHeight() / 2), centerY));

        setActive(true);
        format();
    }

    public void hide() {
        setActive(false);
    }

    @Override
    public void update(double delta) {
        if(active) {
            //centerX = Math.max(getOuterWidth() / 2, Math.min(view.getWidth() - (getOuterWidth() / 2), centerX));
            //centerY = Math.max(getOuterHeight() / 2, Math.min(view.getHeight() - (getOuterHeight() / 2), centerY));

            double mouseX = InputManager.getMouseX();
            double mouseY = InputManager.getFlippedMouseY();
            hover = (mouseX > centerX - width / 2 && mouseX <= centerX + width / 2 && mouseY > centerY - height / 2 && mouseY <= centerY + height / 2);
        }

        updateChildren(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        drawChildren(batch);
    }

    public boolean hover() {
        return hover;
    }

    public WeaponStat getStatType() {
        return statType;
    }
}
