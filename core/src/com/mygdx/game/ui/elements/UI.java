package com.mygdx.game.ui.elements;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.ui.graphic_types.BlankGT;
import com.mygdx.game.ui.graphic_types.GraphicType;

import java.util.ArrayList;

public class UI {
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 3;
    public static final int BOTTOM = 4;
    public static final int STRETCH = 5;

    protected float marginX, marginY;
    protected float paddingX, paddingY;
    protected float centerX, centerY;
    protected float width, height;

    protected GraphicType graphicType;

    protected boolean vertical = true;
    protected boolean fillContentsVertical = false;
    protected boolean fillContentsHorizontal = false;
    protected boolean fitContentsVertical = false;
    protected boolean fitContentsHorizontal = false;
    protected int contentAlignVertical = CENTER;
    protected int contentAlignHorizontal = CENTER;

    protected boolean active = false;

    protected UI parent = null;
    protected ArrayList<UI> children;

    public UI() {
        this.centerX = 0;
        this.centerY = 0;
        this.width = 0;
        this.height = 0;
        this.marginX = 0;
        this.marginY = 0;
        this.paddingX = 0;
        this.paddingY = 0;
        this.graphicType = new BlankGT();

        children = new ArrayList<UI>();
    }

    public UI setGraphicType(GraphicType graphicType) {
        this.graphicType = graphicType;
        return this;
    }

    public void setActive(boolean active) {
        this.active = active;
        for(UI child: children)
            child.setActive(active);
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
                width = maxWidth + paddingX * 2;
            } else {
                float totalWidth = 0;
                for(UI child2: children) {
                    totalWidth += child2.getOuterWidth();
                }
                width = totalWidth + paddingX * 2;
            }
        }
        if(fitContentsVertical) {
            if(!vertical) {
                float maxHeight = 0;
                for(UI child2: children) {
                    maxHeight = Math.max(maxHeight, child2.getOuterHeight());
                }
                height = maxHeight + paddingY * 2;
            } else {
                float totalHeight = 0;
                for(UI child2: children) {
                    totalHeight += child2.getOuterHeight();
                }
                height = totalHeight + paddingY * 2;
            }
        }
        child.setParent(this);

        return this;
    }

    public void removeChildren() {
        children.clear();
    }

    public void setParent(UI parent) {
        this.parent = parent;
    }

    public void format() {
        if(vertical) {
            //set child width if fill
            if(fillContentsHorizontal) {
                for(UI child: children) {
                    if(child.getWidth() == 0)
                        child.setWidth(getInnerWidth() - child.getMarginX()*2);
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
                int numUnsizedChildren = 0;
                for(UI child: children) {
                    innerHeight -= child.getOuterHeight();
                    if(child.getHeight() == 0)
                        numUnsizedChildren++;
                }

                if(numUnsizedChildren > 0) {
                    float childHeight = innerHeight / numUnsizedChildren;
                    for(UI child : children) {
                        if(child.getHeight() == 0)
                            child.setHeight(childHeight);
                    }
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
                    if(child.getHeight() == 0)
                        child.setHeight(getInnerHeight() - child.getMarginY()*2);
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
                int numUnsizedChildren = 0;
                for(UI child: children) {
                    innerWidth -= child.getOuterWidth();
                    if(child.getWidth() == 0)
                        numUnsizedChildren++;
                }

                if(numUnsizedChildren > 0) {
                    float childWidth = innerWidth / numUnsizedChildren;
                    for(UI child : children) {
                        if(child.getWidth() == 0)
                            child.setWidth(childWidth);
                    }
                }
            }

            //set child x positions
            if(contentAlignHorizontal == RIGHT) {
                float rightX = centerX + getInnerWidth()/2;
                for(int i = children.size()-1; i >= 0; i--) {
                    UI child = children.get(i);

                    child.setCenterX(rightX - child.getOuterWidth()/2);
                    rightX -= child.getOuterWidth();
                }
            } else if(contentAlignHorizontal == CENTER) {
                float totalWidth = 0;
                for(int i = children.size()-1; i >= 0; i--) {
                    UI child = children.get(i);

                    totalWidth += child.getOuterWidth();
                }

                float rightX = centerX + totalWidth/2;
                for(int i = children.size()-1; i >= 0; i--) {
                    UI child = children.get(i);

                    child.setCenterX(rightX - child.getOuterWidth()/2);
                    rightX -= child.getOuterWidth();
                }
            } else if(contentAlignHorizontal == LEFT) {
                float leftX = centerX - getInnerWidth()/2;
                for(UI child: children) {
                    child.setCenterX(leftX + child.getOuterWidth()/2);
                    leftX += child.getOuterWidth();
                }
            } else if(contentAlignHorizontal == STRETCH) {
                if(children.size() == 1) {
                    for(int i = children.size()-1; i >= 0; i--) {
                        UI child = children.get(i);

                        child.setCenterX(centerX);
                    }
                } else {
                    UI left = children.get(0);
                    UI right = children.get(children.size()-1);
                    ArrayList<UI> mid = new ArrayList<UI>();
                    for(int i = children.size()-2; i >= 1; i--) {
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

    public void update(double delta) {
        updateChildren(delta);
    }

    public void draw(SpriteBatch batch) {
        graphicType.draw(batch, centerX, centerY, width, height);
        drawChildren(batch);
    }

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

    public UI setMargin(float marginX, float marginY) {
        this.marginX = marginX;
        this.marginY = marginY;
        return this;
    }

    public UI setMarginX(float marginX) {
        this.marginX = marginX;
        return this;
    }

    public UI setMarginY(float marginY) {
        this.marginY = marginY;
        return this;
    }

    public UI setPadding(float paddingX, float paddingY) {
        this.paddingX = paddingX;
        this.paddingY = paddingY;
        return this;
    }

    public UI setPaddingX(float paddingX) {
        this.paddingX = paddingX;
        return this;
    }

    public UI setPaddingY(float paddingY) {
        this.paddingY = paddingY;
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

    public boolean isActive() {
        return active;
    }

    public float getMarginX() {
        return marginX;
    }

    public float getMarginY() {
        return marginY;
    }

    public float getPaddingX() {
        return paddingX;
    }

    public float getPaddingY() {
        return paddingY;
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
        return width + marginX*2;
    }

    public float getInnerWidth() {
        return width - paddingX*2;
    }

    public float getOuterHeight() {
        return height + marginY*2;
    }

    public float getInnerHeight() {
        return height - paddingY*2;
    }

    public GraphicType getGraphicType() {
        return graphicType;
    }
}
