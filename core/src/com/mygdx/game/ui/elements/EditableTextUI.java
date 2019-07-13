package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.input.ControlMapping;
import com.mygdx.game.input.InputManager;
import com.mygdx.game.input.Typeable;
import com.mygdx.game.ui.graphic_types.GraphicType;

public abstract class EditableTextUI extends ButtonUI implements Typeable {
    private BitmapFont font;
    private Color color;
    private String text;
    private GlyphLayout glyphLayout;
    private String truncate = null;
    private boolean wrap = false;
    private float textOffsetX, textOffsetY;

    private Color shadowColor;
    private float shadowOffsetX, shadowOffsetY;
    private GlyphLayout shadowGlyphLayout;

    private float textX, textY;
    private int hAlign = Align.center;
    private int vAlign = Align.center;

    private boolean selected = false;
    private int maxChars;

    public EditableTextUI(Color defaultColor, Color hoverColor, Color pressColor, GraphicType graphicType, String text, BitmapFont font, Color color, int maxChars) {
        super(defaultColor, hoverColor, pressColor, graphicType);

        this.text = text;
        this.font = font;
        this.color = color;
        this.maxChars = maxChars;

        glyphLayout = new GlyphLayout();
        updateText();
    }

    public EditableTextUI setShadow(Color shadowColor, float shadowOffsetX, float shadowOffsetY) {
        this.shadowColor = shadowColor;
        this.shadowOffsetX = shadowOffsetX;
        this.shadowOffsetY = shadowOffsetY;
        shadowGlyphLayout = new GlyphLayout();
        updateText();
        return this;
    }

    public EditableTextUI setOffset(float offsetX, float offsetY) {
        textOffsetX = offsetX;
        textOffsetY = offsetY;
        return this;
    }

    public EditableTextUI setColor(Color defaultColor, Color hoverColor, Color pressColor, Color fontColor) {
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        this.pressColor = pressColor;
        this.color = fontColor;
        return this;
    }

    @Override
    public void format() {
        super.format();

        updateText();
    }

    public EditableTextUI fitText() {
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
    public void draw(SpriteBatch batch) {
        //System.out.println(hover + " " + centerX + " " + centerY + " " + width + " " + height);
        graphicType.setColor(currentColor).draw(batch, centerX, centerY, width, height);

        if(shadowColor != null)
            font.draw(batch, shadowGlyphLayout, textX + textOffsetX + shadowOffsetX, textY + textOffsetY + shadowOffsetY);
        font.draw(batch, glyphLayout, textX + textOffsetX, textY + textOffsetY);
        drawChildren(batch);
    }

    public EditableTextUI setTextAlign(int hAlign, int vAlign) {
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        return this;
    }

    public EditableTextUI setText(String text) {
        this.text = text;
        return this;
    }

    public EditableTextUI setTruncate(String truncate) {
        this.truncate = truncate;
        return this;
    }

    public EditableTextUI setWrap(boolean wrap) {
        this.wrap = wrap;
        return this;
    }

    public EditableTextUI setColor(Color color) {
        this.color = color;
        return this;
    }

    @Override
    public void type(char character) {
        boolean textChanged = false;

        if(("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ").indexOf(character) != -1 && text.length() < maxChars) {
            text += character;
            textChanged = true;
        } else if(character == '\b' && text.length() > 0) {
            text = text.substring(0, text.length()-1);
            textChanged = true;
        }

        if(textChanged) {
            updateText();
            parent.format();
            textChanged(text);
        }
    }

    public abstract void textChanged(String newText);

    @Override
    public void update(double delta) {
        super.update(delta);

        if((!active && selected) || ((InputManager.keyPressed(ControlMapping.CLICK_LEFT) || InputManager.keyPressed(ControlMapping.CLICK_RIGHT)) && hoverTimer == 0)) {
            selected = false;
            InputManager.unsetTypingTarget(this);
        }

        if(selected) {
            currentColor = pressColor;
        }
    }

    @Override
    public void press(int button, double hoverTimer, double pressTimer) {
        selected = true;
        InputManager.setTypingTarget(this);
    }

    @Override
    public void hold(int button, double hoverTimer, double pressTimer) {

    }

    @Override
    public void release(int button, double hoverTimer, double pressTimer) {

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