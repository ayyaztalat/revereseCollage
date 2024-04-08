package com.picspool.lib.filter.cpu.father;

import android.graphics.Rect;

/* loaded from: classes3.dex */
public abstract class WholeImageFilter {
    protected Rect originalSpace;
    protected Rect transformedSpace;

    protected abstract int[] filterPixels(int i, int i2, int[] iArr, Rect rect);

    protected void transformSpace(Rect rect) {
    }

    public int[] filter(int[] iArr, int i, int i2) {
        this.originalSpace = new Rect(0, 0, i, i2);
        Rect rect = new Rect(0, 0, i, i2);
        this.transformedSpace = rect;
        transformSpace(rect);
        return filterPixels(i, i2, iArr, this.transformedSpace);
    }
}
