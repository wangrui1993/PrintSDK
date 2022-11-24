package com.handset.sdktool.event;

public class LabelShape extends Label {
    public static final int SHAP_CIRCLE = 2;
    public static final int SHAP_LINE = 5;
    public static final int SHAP_OVAL = 1;
    public static final int SHAP_RECTANGLE = 3;
    public static final int SHAP_RECTANGLE_ROUND = 4;
    private int borderColor;
    private int borderWidth;
    private boolean dotted;
    private int dottedSpace;
    private boolean fill;
    private int fillColor;
    private int radius;
    private int shapType;

    public LabelShape() {
        this(5);
    }

    public LabelShape(int i) {
        this.borderWidth = 5;
        this.borderColor = -16777216;
        this.fill = false;
        this.fillColor = -16777216;
        this.radius = 10;
        this.shapType = i;
        setX(10.0f);
        setY(10.0f);
    }

    public int getRadius() {
        return this.radius;
    }

    public void setRadius(int i) {
        if (i < 1) {
            i = 1;
        }
        this.radius = i;
    }

    public boolean isDotted() {
        return this.dotted;
    }

    public void setDotted(boolean z) {
        this.dotted = z;
    }

    public int getDottedSpace() {
        return this.dottedSpace;
    }

    public void setDottedSpace(int i) {
        this.dottedSpace = Math.abs(i);
    }

    public int getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(int i) {
        this.borderColor = i;
    }

    public int getBorderWidth() {
        return this.borderWidth;
    }

    public void setBorderWidth(int i) {
        if (i < 1) {
            i = 1;
        }
        this.borderWidth = i;
        if (this.shapType == 5) {
            setHeight((float) (i + 60));
        }
    }

    public boolean isFill() {
        return this.fill;
    }

    public void setFill(boolean z) {
        this.fill = z;
    }

    public int getFillColor() {
        return this.fillColor;
    }

    public void setFillColor(int i) {
        this.fillColor = i;
    }

    public int getShapType() {
        return this.shapType;
    }

    public void setShapType(int i) {
        this.shapType = i;
    }

    @Override // com.handset.data.entity.Label
    public void setWidth(float f) {
        super.setWidth(f);
        if (this.shapType == 2) {
            super.setHeight(f);
        }
    }

    @Override // com.handset.data.entity.Label
    public void setHeight(float f) {
        super.setHeight(f);
        if (this.shapType == 2) {
            super.setWidth(f);
        }
    }
}
