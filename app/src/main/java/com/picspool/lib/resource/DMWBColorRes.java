package com.picspool.lib.resource;

/* loaded from: classes3.dex */
public class DMWBColorRes extends DMWBRes {
    private int colorValue;

    public int getColorValue() {
        return this.colorValue;
    }

    public void setColorValue(int i) {
        this.colorValue = i;
    }

    public void setColorID(int i) {
        this.colorValue = getResources().getColor(i);
    }
}
