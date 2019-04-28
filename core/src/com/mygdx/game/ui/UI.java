package com.mygdx.game.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public abstract class UI {
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 3;
    public static final int BOTTOM = 4;
    public static final int STRETCH = 5;

    protected float margin;
    protected float padding;
    protected float centerX, centerY;
    protected float width, height;

    private boolean vertical = true;
    private boolean fillContentsVertical = false;
    private boolean fillContentsHorizontal = false;
    private boolean fitContentsVertical = false;
    private boolean fitContentsHorizontal = false;
    private int contentAlignVertical = CENTER;
    private int contentAlignHorizontal = CENTER;

    private ArrayList<UI> children;

    public UI(float centerX, float centerY, float width, float height, float margin, float padding) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.margin = margin;
        this.padding = padding;

        children = new ArrayList<UI>();

        init();
    }

    public UI(float width, float height, float margin, float padding) {
        this(0, 0, width, height, margin, padding);
    }

    public UI(float margin, float padding) {
        this(0, 0, 0, 0, margin, padding);
    }

    public void addChild(UI child) {
        children.add(child);

        if(fitContentsHorizontal && vertical) {
            width = Math.max(width, child.getOuterWidth() + padding*2);
        }
        if(fitContentsVertical && !vertical) {
            height = Math.max(height, child.getOuterHeight() + padding*2);
        }
    }

    public void format() {
        if(vertical) {
            //set child width if fill
            if(fillContentsHorizontal) {
                for(UI child: children) {
                    child.setWidth(getInnerWidth());
                }
            }

            //set child x positions
            if(contentAlignHorizontal == LEFT) {
                for(UI child: children) {
                    child.setCenterX(centerX - getInnerWidth()/2 + child.getOuterWidth()/2);
                }
            } else if(contentAlignHorizontal == CENTER) {
                for(UI child: children) {
                    child.setCenterX(centerX);
                }
            } else if(contentAlignHorizontal == RIGHT) {
                for(UI child: children) {
                    child.setCenterX(centerX + getInnerWidth()/2 - child.getOuterWidth()/2);
                }
            }

            //set child height if fill
            if(fillContentsVertical) {
                float innerHeight = getInnerHeight();
                for(UI child: children) {
                    innerHeight -= child.getMargin()*2;
                }

                float childHeight = innerHeight / children.size();
                for(UI child: children) {
                    child.setHeight(childHeight);
                }
            }

            //set child x positions
            if(contentAlignVertical == TOP) {
                float topY = centerY + getInnerHeight()/2;
                for(UI child: children) {
                    System.out.println(topY);
                    child.setCenterY(topY - child.getOuterHeight()/2);
                    topY -= child.getOuterHeight();
                }
            } else if(contentAlignVertical == CENTER) {
                float totalHeight = 0;
                for(UI child: children) {
                    totalHeight += child.getOuterHeight();
                }

                float topY = centerY + totalHeight/2;
                for(UI child: children) {
                    child.setCenterY(topY - child.getOuterHeight()/2);
                    topY -= child.getOuterHeight();
                }
            } else if(contentAlignVertical == BOTTOM) {
                float botY = centerY - getInnerHeight()/2;
                for(int i = children.size()-1; i >= 0; i--) {
                    UI child = children.get(i);
                    child.setCenterY(botY + child.getOuterHeight()/2);
                    botY += child.getOuterHeight();
                }
            } else if(contentAlignVertical == STRETCH) {
                if(children.size() == 1) {
                    for(UI child: children) {
                        child.setCenterY(centerY);
                    }
                } else {
                    UI top = children.get(0);
                    UI bot = children.get(children.size()-1);
                    ArrayList<UI> mid = new ArrayList<UI>();
                    for(int i = 1; i < children.size()-1; i++) {
                        mid.add(children.get(i));
                    }

                    float remainingHeight = getInnerHeight();
                    float topY = centerY + getInnerHeight()/2 - top.getOuterHeight();

                    top.setCenterY(centerY + getInnerHeight()/2 - top.getOuterHeight()/2);
                    remainingHeight -= top.getOuterHeight();

                    bot.setCenterY(centerY - getInnerHeight()/2 + bot.getOuterHeight()/2);
                    remainingHeight -= bot.getOuterHeight();

                    if(mid.size() > 0) {
                        for(UI child: mid) {
                            remainingHeight -= child.getOuterHeight();
                        }

                        float gapHeight = remainingHeight / (mid.size() + 1);
                        for(UI child: mid) {
                            topY -= gapHeight;
                            child.setCenterY(topY - child.getOuterHeight()/2);
                            topY -= child.getOuterHeight();
                        }
                    }
                }
            }
        } else {
            System.out.println("Horizontal not implemented yet");
        }
    }

    private void formatChildren() {
        for(UI ui: children) {
            ui.format();
        }
    }

    public abstract void init();
    public abstract void update(double delta);
    public abstract void draw(SpriteBatch batch);

    protected void updateChildren(double delta) {
        for(UI child: children) {
            child.update(delta);
        }
    }

    protected void drawChildren(SpriteBatch batch) {
        for(UI child: children) {
            child.draw(batch);
        }
    }

    public void setPosition(float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setOrientation(boolean vertical) {
        this.vertical = vertical;
    }

    public void setContentAlign(int contentAlignVertical, int contentAlignHorizontal) {
        this.contentAlignVertical = contentAlignVertical;
        this.contentAlignHorizontal = contentAlignHorizontal;
    }

    public void setContentFill(boolean fillContentsVertical, boolean fillContentsHorizontal) {
        this.fillContentsVertical = fillContentsVertical;
        this.fillContentsHorizontal = fillContentsHorizontal;
    }

    public void setContentFit(boolean fitContentsVertical, boolean fitContentsHorizontal) {
        this.fitContentsVertical = fitContentsVertical;
        this.fitContentsHorizontal = fitContentsHorizontal;
    }

    public float getMargin() {
        return margin;
    }

    public float getPadding() {
        return padding;
    }

    public float getCenterX() {
        return centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getOuterWidth() {
        return width + margin*2;
    }

    public float getInnerWidth() {
        return width - padding*2;
    }

    public float getOuterHeight() {
        return height + margin*2;
    }

    public float getInnerHeight() {
        return height - padding*2;
    }
}
