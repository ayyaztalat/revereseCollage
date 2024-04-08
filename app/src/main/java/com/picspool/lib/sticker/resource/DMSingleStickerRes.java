package com.picspool.lib.sticker.resource;

import android.graphics.Bitmap;

/* loaded from: classes3.dex */
public class DMSingleStickerRes {
    private Bitmap stickerBmp;
    public int stickerId;
    private String stickerPath;

    /* renamed from: pX */
    private int f1983pX = 0;

    /* renamed from: pY */
    private int f1984pY = 0;
    private int pWidth = 0;
    private int pHeight = 0;

    public Bitmap getStickerBmp() {
        return this.stickerBmp;
    }

    public void setStickerBmp(Bitmap bitmap) {
        this.stickerBmp = bitmap;
    }

    public void setStickerPosition(int i, int i2, int i3, int i4) {
        this.f1983pX = i;
        this.f1984pY = i2;
        this.pWidth = i3;
        this.pHeight = i4;
    }

    public String getStickerPath() {
        return this.stickerPath;
    }

    public void setStickerPath(String str) {
        this.stickerPath = str;
    }

    public int getX() {
        return this.f1983pX;
    }

    public int getY() {
        return this.f1984pY;
    }

    public int getWidth() {
        return this.pWidth;
    }

    public int getHeight() {
        return this.pHeight;
    }
}
