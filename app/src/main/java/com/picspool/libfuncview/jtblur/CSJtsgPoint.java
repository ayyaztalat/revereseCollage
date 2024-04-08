package com.picspool.libfuncview.jtblur;

import android.graphics.PointF;

/* loaded from: classes.dex */
public class CSJtsgPoint {
    private int color;
    private int gray;
    private PointF pointF;

    public float getX() {
        return this.pointF.x;
    }

    public float getY() {
        return this.pointF.y;
    }

    public PointF getPointF() {
        return this.pointF;
    }

    public void setPointF(PointF pointF) {
        this.pointF = pointF;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public int getGray() {
        return this.gray;
    }

    public int getGrayAlhpa() {
        int i = (this.gray * 2) - 255;
        if (i < 1) {
            return 0;
        }
        return i;
    }

    public void setGray(int i) {
        this.gray = i;
    }
}
