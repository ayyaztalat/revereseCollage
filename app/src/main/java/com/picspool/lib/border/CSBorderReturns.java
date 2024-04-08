package com.picspool.lib.border;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public class CSBorderReturns {
    public int bottom;
    public Bitmap frameBitmap;
    public int left;
    public int right;
    public int top;

    public CSBorderReturns(Bitmap bitmap, int i, int i2, int i3, int i4) {
        this.frameBitmap = bitmap;
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
    }

    public Bitmap getFrameBitmap() {
        return this.frameBitmap;
    }

    public void setFrameBitmap(Bitmap bitmap) {
        this.frameBitmap = bitmap;
    }

    public int getLeft() {
        return this.left;
    }

    public int getTop() {
        return this.top;
    }

    public int getRight() {
        return this.right;
    }

    public int getBottom() {
        return this.bottom;
    }

    public void recycleFrameBitmap() {
        Bitmap bitmap = this.frameBitmap;
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                this.frameBitmap.recycle();
            }
            this.frameBitmap = null;
        }
    }
}
