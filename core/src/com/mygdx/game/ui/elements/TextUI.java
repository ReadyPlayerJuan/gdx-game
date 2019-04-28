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

    private float textX, textY;
    private int hAlign = Align.center;
    private int vAlign = Align.center;

    public TextUI(float centerX, float centerY, float width, float height, float margin, float padding, BitmapFont font, String text, Color color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.margin = margin;
        this.padding = padding;

        this.font = font;
        this.color = color;
        this.text = text;

        glyphLayout = new GlyphLayout();
        updateText();
    }

    public TextUI(float width, float height, float margin, float padding, BitmapFont font, String text, Color color) {
        this(0, 0, width, height, margin, padding, font, text, color);
    }

    public TextUI(float margin, float padding, BitmapFont font, String text, Color color) {
        this(0, 0, 0, 0, margin, padding, font, text, color);
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
            height = textHeight + padding*2;
        }
        if(fitContentsHorizontal) {
            width = textWidth;
        }

        glyphLayout.setText(font, text, 0, text.length(), color, getInnerWidth(), hAlign, wrap, truncate);
    }

    @Override
    public void init() {

    }

    @Override
    public void update(double delta) {
        updateChildren(delta);
    }

    @Override
    public void draw(SpriteBatch batch) {
        //System.out.println(width + " " + height);
        font.draw(batch, glyphLayout, textX, textY);
        drawChildren(batch);
    }
}
