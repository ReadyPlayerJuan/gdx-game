package com.mygdx.game.ui.elements;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.input.Typeable;
import com.mygdx.game.textures.TextureData;
import com.mygdx.game.util.Util;

public class RoundedRectEditableTextUI extends ButtonUI implements Typeable {
    private BitmapFont font;
    private Color color;
    private String text;
    private GlyphLayout glyphLayout;
    private String truncate = null;
    private boolean wrap = false;

    private float textX, textY;
    private int hAlign = Align.center;
    private int vAlign = Align.center;

    private NinePatch sprite;
    private Color borderColor;
    private float borderSize = 0;

    private boolean selected = false;
    private int maxChars;

    public RoundedRectEditableTextUI(float cornerSize, Color defaultColor, Color hoverColor, Color pressColor, String text, BitmapFont font, Color color, int maxChars) {
        super(defaultColor, hoverColor, pressColor);

        sprite = Util.createNinePatch(TextureData.ROUNDED_RECT, cornerSize);

        this.text = text;
        this.font = font;
        this.color = color;
        this.maxChars = maxChars;

        glyphLayout = new GlyphLayout();
        updateText();
    }

    public RoundedRectEditableTextUI setBorder(float size, Color color) {
        borderSize = size;
        borderColor = color;
        return this;
    }

    @Override
    public void format() {
        super.format();

        updateText();
    }

    public RoundedRectEditableTextUI fitText() {
        updateText();
        float textHeight = glyphLayout.height;
        float textWidth = glyphLayout.width;
        height = textHeight + paddingY*2;
        width = textWidth + paddingX*2;
        return this;
    }

    public void updateText() {
        glyphLayout.setText(font, text);
        float textHeight = glyphLayout.height;
        float textWidth = glyphLayout.width;

        textX = centerX - getInnerWidth()/2;

        if(vAlign == Align.top) {
            textY = centerY + getInnerHeight()/2;
        } else if(vAlign == Align.center) {
            textY = centerY + textHeight/2;
        } else {
            textY = centerY - getInnerHeight()/2 + textHeight;
        }

        if(fitContentsVertical) {
            height = textHeight + paddingY*2;
        }
        if(fitContentsHorizontal) {
            width = textWidth + paddingX * 2;
        }

        glyphLayout.setText(font, text, 0, text.length(), color, getInnerWidth(), hAlign, wrap, truncate);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if(borderSize == 0) {
            sprite.setColor(currentColor);
            sprite.draw(batch, centerX - width / 2, centerY - height / 2, width, height);
        } else {
            sprite.setColor(borderColor);
            sprite.draw(batch, centerX - width / 2, centerY - height / 2, width, height);
            sprite.setColor(currentColor);
            sprite.draw(batch, centerX - width / 2 + borderSize, centerY - height / 2 + borderSize, width - borderSize*2, height - borderSize*2);
        }
        font.draw(batch, glyphLayout, textX, textY);
        drawChildren(batch);
    }

    public RoundedRectEditableTextUI setTextAlign(int hAlign, int vAlign) {
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        return this;
    }

    public RoundedRectEditableTextUI setText(String text) {
        this.text = text;
        return this;
    }

    public RoundedRectEditableTextUI setTruncate(String truncate) {
        this.truncate = truncate;
        return this;
    }

    public RoundedRectEditableTextUI setWrap(boolean wrap) {
        this.wrap = wrap;
        return this;
    }

    public RoundedRectEditableTextUI setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public void type(char character) {
        if(("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ").indexOf(character) != -1 && text.length() < maxChars) {
            text += character;
            updateText();
            parent.format();
        } else if(character == '\b' && text.length() > 0) {
            text = text.substring(0, text.length()-1);
            updateText();
            parent.format();
        }
    }

    @Override
    public void update(double delta) {
        super.update(delta);

        if(InputManager.keyPressed(ControlMapping.CLICK_LEFT) || InputManager.keyPressed(ControlMapping.CLICK_RIGHT)) {
            if(hoverTimer == 0) {
                selected = false;
                InputManager.unsetTypingTarget(this);
            }
        }

        if(selected) {
            currentColor = pressColor;
        }
    }

    @Override
    public void press(double hoverTimer, double pressTimer) {
        selected = true;
        InputManager.setTypingTarget(this);
    }

    @Override
    public void hold(double hoverTimer, double pressTimer) {

    }

    @Override
    public void release(double hoverTimer, double pressTimer) {

    }

    @Override
    public void mouseOver(double hoverTimer) {

    }

    @Override
    public void hover(double hoverTimer) {

    }

    @Override
    public void mouseLeave(double hoverTimer) {

    }
}
