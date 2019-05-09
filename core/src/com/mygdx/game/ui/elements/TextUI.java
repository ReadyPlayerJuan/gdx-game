package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class TextUI extends UI {
    private BitmapFont font;
    private Color color;
    private String text;
    private GlyphLayout glyphLayout;
    private String truncate = null;
    private boolean wrap = false;

    private Color shadowColor;
    private float shadowOffsetX, shadowOffsetY;
    private GlyphLayout shadowGlyphLayout;

    private float textX, textY;
    private int hAlign = Align.center;
    private int vAlign = Align.center;

    public TextUI(String text, BitmapFont font, Color color) {
        this.text = text;
        this.font = font;
        this.color = color;
        glyphLayout = new GlyphLayout();
        updateText();
    }

    public TextUI setShadow(Color shadowColor, float shadowOffsetX, float shadowOffsetY) {
        this.shadowColor = shadowColor;
        this.shadowOffsetX = shadowOffsetX;
        this.shadowOffsetY = shadowOffsetY;
        shadowGlyphLayout = new GlyphLayout();
        updateText();
        return this;
    }

    public TextUI setTextAlign(int hAlign, int vAlign) {
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        return this;
    }

    public TextUI setText(String text) {
        this.text = text;
        return this;
    }

    public TextUI setTruncate(String truncate) {
        this.truncate = truncate;
        return this;
    }

    public TextUI setWrap(boolean wrap) {
        this.wrap = wrap;
        return this;
    }

    public TextUI setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public UI addChild(UI child) {
        System.out.println("Can't add a child to a text field!");
        System.exit(1);
        return this;
    }

    @Override
    public void format() {
        super.format();

        updateText();
    }

    public TextUI fitText() {
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
        if(shadowColor != null)
            shadowGlyphLayout.setText(font, text, 0, text.length(), shadowColor, getInnerWidth(), hAlign, wrap, truncate);
    }

    @Override
    public void update(double delta) {
        updateChildren(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        graphicType.draw(batch, centerX, centerY, width, height);
        if(shadowColor != null)
            font.draw(batch, shadowGlyphLayout, textX + shadowOffsetX, textY + shadowOffsetY);
        font.draw(batch, glyphLayout, textX, textY);
        drawChildren(batch);
    }
}
