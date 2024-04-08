package com.picspool.lib.collagelib.resource.collage;

import android.graphics.PointF;

/* loaded from: classes3.dex */
public class LibDMCollageLineInfo {

    /* renamed from: mA */
    private double f1943mA;

    /* renamed from: mB */
    private double f1944mB;
    private boolean mIsXFunction = false;
    private PointF mVerPointF;

    public PointF getVerPointF() {
        return this.mVerPointF;
    }

    public void setVerPointF(PointF pointF) {
        this.mVerPointF = pointF;
    }

    public double getLineA() {
        return this.f1943mA;
    }

    public void setLineA(double d) {
        this.f1943mA = d;
    }

    public double getLineB() {
        return this.f1944mB;
    }

    public void setLineB(double d) {
        this.f1944mB = d;
    }

    public boolean getIsXFunction() {
        return this.mIsXFunction;
    }

    public void setIsXFunction(boolean z) {
        this.mIsXFunction = z;
    }
}
