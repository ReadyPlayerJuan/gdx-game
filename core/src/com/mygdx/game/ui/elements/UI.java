package com.mygdx.game.ui.elements;

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

    protected boolean vertical = true;
    protected boolean fillContentsVertical = false;
    protected boolean fillContentsHorizontal = false;
    protected boolean fitContentsVertical = false;
    protected boolean fitContentsHorizontal = false;
    protected int contentAlignVertical = CENTER;
    protected int contentAlignHorizontal = CENTER;

    protected ArrayList<UI> children;

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

    public UI() {
        this.centerX = 0;
        this.centerY = 0;
        this.width = 0;
        this.height = 0;
        this.margin = 0;
        this.padding = 0;

        children = new ArrayList<UI>();
    }

    public UI addToParent(UI parent) {
        parent.addChild(this);
        return this;
    }

    public UI addChild(UI child) {
        children.add(child);

        if(fitContentsHorizontal) {
            if(vertical) {
                float maxWidth = 0;
                for(UI child2: children) {
                    maxWidth = Math.max(maxWidth, child2.getOuterWidth());
                }
                width = maxWidth + padding * 2;
            } else {
                float totalWidth = 0;
                for(UI child2: children) {
                    totalWidth += child2.getOuterWidth();
                }
                width = totalWidth + padding * 2;
            }
        }
        if(fitContentsVertical) {
            if(!vertical) {
                float maxHeight = 0;
                for(UI child2: children) {
                    maxHeight = Math.max(maxHeight, child2.getOuterHeight());
                }
                height = maxHeight + padding * 2;
            } else {
                float totalHeight = 0;
                for(UI child2: children) {
                    totalHeight += child2.getOuterHeight();
                }
                height = totalHeight + padding * 2;
            }
        }
        return this;
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

            //set child y positions
            if(contentAlignVertical == TOP) {
                float topY = centerY + getInnerHeight()/2;
                for(UI child: children) {
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





        } else { //HORIZONTAL

            //set child height if fill
            if(fillContentsVertical) {
                for(UI child: children) {
                    child.setHeight(getInnerHeight());
                }
            }

            //set child y positions
            if(contentAlignVertical == BOTTOM) {
                for(UI child: children) {
                    child.setCenterY(centerY - getInnerHeight()/2 + child.getOuterHeight()/2);
                }
            } else if(contentAlignVertical == CENTER) {
                for(UI child: children) {
                    child.setCenterY(centerY);
                }
            } else if(contentAlignVertical == TOP) {
                for(UI child: children) {
                    child.setCenterY(centerY + getInnerHeight()/2 - child.getOuterHeight()/2);
                }
            }

            //set child width if fill
            if(fillContentsHorizontal) {
                float innerWidth = getInnerWidth();
                for(UI child: children) {
                    innerWidth -= child.getMargin()*2;
                }

                float childWidth = innerWidth / children.size();
                for(UI child: children) {
                    child.setWidth(childWidth);
                }
            }

            //set child x positions
            if(contentAlignHorizontal == RIGHT) {
                float rightX = centerX + getInnerWidth()/2;
                for(UI child: children) {
                    child.setCenterX(rightX - child.getOuterWidth()/2);
                    rightX -= child.getOuterWidth();
                }
            } else if(contentAlignHorizontal == CENTER) {
                float totalWidth = 0;
                for(UI child: children) {
                    totalWidth += child.getOuterWidth();
                }

                float rightX = centerX + totalWidth/2;
                for(UI child: children) {
                    child.setCenterX(rightX - child.getOuterWidth()/2);
                    rightX -= child.getOuterWidth();
                }
            } else if(contentAlignHorizontal == LEFT) {
                float leftX = centerX - getInnerWidth()/2;
                for(int i = children.size()-1; i >= 0; i--) {
                    UI child = children.get(i);
                    child.setCenterX(leftX + child.getOuterWidth()/2);
                    leftX += child.getOuterWidth();
                }
            } else if(contentAlignHorizontal == STRETCH) {
                if(children.size() == 1) {
                    for(UI child: children) {
                        child.setCenterX(centerX);
                    }
                } else {
                    UI right = children.get(0);
                    UI left = children.get(children.size()-1);
                    ArrayList<UI> mid = new ArrayList<UI>();
                    for(int i = 1; i < children.size()-1; i++) {
                        mid.add(children.get(i));
                    }

                    float remainingWidth = getInnerWidth();
                    float rightX = centerX + getInnerWidth()/2 - right.getOuterWidth();

                    right.setCenterX(centerX + getInnerWidth()/2 - right.getOuterWidth()/2);
                    remainingWidth -= right.getOuterWidth();

                    left.setCenterX(centerX - getInnerWidth()/2 + left.getOuterWidth()/2);
                    remainingWidth -= left.getOuterWidth();

                    if(mid.size() > 0) {
                        for(UI child: mid) {
                            remainingWidth -= child.getOuterWidth();
                        }

                        float gapWidth = remainingWidth / (mid.size() + 1);
                        for(UI child: mid) {
                            rightX -= gapWidth;
                            child.setCenterX(rightX - child.getOuterWidth()/2);
                            rightX -= child.getOuterWidth();
                        }
                    }
                }
            }
        }

        formatChildren();
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

    public UI setPosition(float centerX, float centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
        return this;
    }

    public UI setCenterX(float centerX) {
        this.centerX = centerX;
        return this;
    }

    public UI setCenterY(float centerY) {
        this.centerY = centerY;
        return this;
    }

    public UI setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public UI setWidth(float width) {
        this.width = width;
        return this;
    }

    public UI setHeight(float height) {
        this.height = height;
        return this;
    }

    public UI setContentAlign(int contentAlignVertical, int contentAlignHorizontal) {
        this.contentAlignVertical = contentAlignVertical;
        this.contentAlignHorizontal = contentAlignHorizontal;
        return this;
    }

    public UI setContentFill(boolean fillContentsVertical, boolean fillContentsHorizontal) {
        this.fillContentsVertical = fillContentsVertical;
        this.fillContentsHorizontal = fillContentsHorizontal;
        return this;
    }

    public UI setContentFit(boolean fitContentsVertical, boolean fitContentsHorizontal) {
        this.fitContentsVertical = fitContentsVertical;
        this.fitContentsHorizontal = fitContentsHorizontal;
        return this;
    }

    public UI setVertical(boolean vertical) {
        this.vertical = vertical;
        return this;
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
