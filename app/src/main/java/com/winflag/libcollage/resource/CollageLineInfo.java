package com.winflag.libcollage.resource;

import android.graphics.PointF;

/* loaded from: classes.dex */
public class CollageLineInfo {

    /* renamed from: mA */
    private double f1769mA;

    /* renamed from: mB */
    private double f1770mB;
    private boolean mIsXFunction = false;
    private PointF mVerPointF;

    public PointF getVerPointF() {
        return this.mVerPointF;
    }

    public void setVerPointF(PointF pointF) {
        this.mVerPointF = pointF;
    }

    public double getLineA() {
        return this.f1769mA;
    }

    public void setLineA(double d) {
        this.f1769mA = d;
    }

    public double getLineB() {
        return this.f1770mB;
    }

    public void setLineB(double d) {
        this.f1770mB = d;
    }

    public boolean getIsXFunction() {
        return this.mIsXFunction;
    }

    public void setIsXFunction(boolean z) {
        this.mIsXFunction = z;
    }
}
