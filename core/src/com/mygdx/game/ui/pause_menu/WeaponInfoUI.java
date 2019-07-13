package com.mygdx.game.ui.pause_menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.entities.Rarity;
import com.mygdx.game.ui.FontManager;
import com.mygdx.game.ui.elements.EditableTextUI;
import com.mygdx.game.ui.elements.SwapperUI;
import com.mygdx.game.ui.elements.TextUI;
import com.mygdx.game.ui.elements.UI;
import com.mygdx.game.ui.graphic_types.RectGT;
import com.mygdx.game.ui.graphic_types.RoundedRectGT;
import com.mygdx.game.views.PauseMenuView;
import com.mygdx.game.weapons.Weapon;

public class WeaponInfoUI extends UI {
    private final PauseMenuView pauseMenuView;
    private final float UI_SCALE;

    private float width, height;

    private Weapon weapon;

    //private SwapperUI frameContainer;
    private UI weaponInfoFrame, weaponInfoStatsContainerBorder;
    private EditableTextUI weaponInfoTitle;
    private TextUI weaponInfoStars, statNamesText, statValuesText;

    private final float cornerSize = 12.0f;
    private final float iconBorderSize = 4.0f;

    public WeaponInfoUI(float UI_SCALE, final PauseMenuView pauseMenuView, float width, float height) {
        this.UI_SCALE = UI_SCALE;
        this.pauseMenuView = pauseMenuView;
        this.width = width;
        this.height = height;
        setSize(width, height);
        setContentFill(true, true);

        SwapperUI frameContainer = new SwapperUI();
        frameContainer.addToParent(this);

        UI weaponInfoShadow = new UI() {
            @Override
            public void draw(SpriteBatch batch) {
                graphicType.draw(batch, centerX + PauseMenuView.SHADOW_OFFSET, centerY - PauseMenuView.SHADOW_OFFSET, width, height);
                drawChildren(batch);
            }
        }.setGraphicType(new RoundedRectGT(cornerSize).setColor(PauseMenuView.SHADOW_COLOR));

        weaponInfoFrame = new UI().setGraphicType(new RoundedRectGT(cornerSize)).setContentFill(true, true);


        UI weaponInfoTitleContainer = new UI().setContentFit(true, false).setContentFill(false, true);
        weaponInfoTitle = new EditableTextUI(Color.BLUE, Color.RED, Color.GREEN, new RoundedRectGT(cornerSize),
                "WEAPON TITLE", FontManager.aireExterior36, Color.BLACK, 16) {
            @Override
            public void textChanged(String newText) {
                if(weapon != null) {
                    weapon.setName(newText);
                    pauseMenuView.updateWeaponData(weapon);
                }
            }
        };
        weaponInfoTitle.setPadding(6, 6).setMargin(12, iconBorderSize * 1.5f);
        weaponInfoTitle.fitText().setWidth(0).addToParent(weaponInfoTitleContainer);

        weaponInfoStars = new TextUI("***", FontManager.aireExterior64, Color.BLACK);
        weaponInfoStars.setOffset(0, -8).setHeight(14).addToParent(weaponInfoTitleContainer);


        weaponInfoStatsContainerBorder = new UI().setGraphicType(new RoundedRectGT(cornerSize).setColor(Color.GRAY))
                .setMargin(iconBorderSize*1.5f, iconBorderSize*1.5f).setContentFill(true, true);
        UI weaponInfoStatsContainer = new UI().setGraphicType(new RoundedRectGT(cornerSize).setColor(PauseMenuView.UI_LIGHT_GRAY))
                .setMargin(iconBorderSize, iconBorderSize).setContentFill(true, true).setVertical(false);


        weaponInfoFrame.addChild(weaponInfoTitleContainer).addChild(weaponInfoStatsContainerBorder);
        weaponInfoStatsContainerBorder.addChild(weaponInfoStatsContainer);

        frameContainer.addChild("shadow", weaponInfoShadow).addChild("frame", weaponInfoFrame)
                .setAllVisible(true);



        SwapperUI leftText = new SwapperUI();
        UI centerLine = new UI().setWidth(2).setMargin(6, 6).setGraphicType(new RectGT().setColor(PauseMenuView.UI_DARK_GRAY));
        SwapperUI rightText = new SwapperUI();

        leftText.setMarginY(10);
        rightText.setMarginY(10);

        statNamesText = new TextUI("aaaa\nbbbbbbbb\ncccccc\ndddddddddd\neeee\nfff", FontManager.aireExterior24, PauseMenuView.UI_DARK_GRAY);
        statNamesText.setTextAlign(Align.right, Align.top);

        statValuesText = new TextUI("aaaa\nbbbbbbbb\ncccccc\ndddddddddd\neeee\nfff", FontManager.aireExterior24, PauseMenuView.UI_DARK_GRAY);
        statValuesText.setTextAlign(Align.left, Align.top);

        leftText.addChild("stat names", statNamesText).setAllVisible(false).setViewVisible("stat names", true);
        rightText.addChild("stat values", statValuesText).setAllVisible(false).setViewVisible("stat values", true);

        weaponInfoStatsContainer.addChild(leftText).addChild(centerLine).addChild(rightText);


        format();
    }

    public void setWeapon(Weapon weapon) {
        if(weapon != null && !weapon.equals(this.weapon)) {
            Rarity rarity = weapon.getRarity();
            weaponInfoFrame.getGraphicType().setColor(rarity.getMainColor());

            weaponInfoTitle.setText(weapon.getName()).setColor(rarity.getMainColor(),
                    rarity.getMainColor().cpy().add(-0.08f, -0.08f, -0.08f, 0),
                    rarity.getMainColor().cpy().add(-0.16f, -0.16f, -0.16f, 0),
                    rarity.getTextColor());
            weaponInfoStars.setText(weapon.getStarRating().getStarString()).setColor(rarity.getTextColor());

            weaponInfoStatsContainerBorder.getGraphicType().setColor(rarity.getBorderColor());

            statNamesText.setText(weapon.getStatNamesString());
            statValuesText.setText(weapon.getStatValuesString());
        }

        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public void update(double delta) {
        updateChildren(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        //graphicType.setColor(currentColor).draw(batch, centerX, centerY, width, height);
        if(weapon != null)
            drawChildren(batch);
    }
}
