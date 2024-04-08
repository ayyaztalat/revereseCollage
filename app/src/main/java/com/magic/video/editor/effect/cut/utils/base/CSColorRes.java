package com.magic.video.editor.effect.cut.utils.base;

/* loaded from: classes2.dex */
public class CSColorRes extends CSStickerRes {
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
