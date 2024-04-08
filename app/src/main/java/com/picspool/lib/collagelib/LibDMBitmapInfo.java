package com.picspool.lib.collagelib;

import android.graphics.Bitmap;
import android.net.Uri;

/* loaded from: classes3.dex */
public class LibDMBitmapInfo {
    private Bitmap mBitmap;
    boolean mIsHorizontalMirror = false;
    private int mRotationDegree = 0;
    private Uri mUri;

    public Uri getUri() {
        return this.mUri;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }

    public Bitmap getSrcBitmap() {
        return this.mBitmap;
    }

    public void setIsHorizontalMirror(boolean z) {
        this.mIsHorizontalMirror = z;
    }

    public boolean getIsHorizontalMirror() {
        return this.mIsHorizontalMirror;
    }

    public int getRotationDegeree() {
        int i = this.mRotationDegree;
        if (i >= 360) {
            this.mRotationDegree = i - 360;
        }
        return this.mRotationDegree;
    }

    public void setRotationDegree(int i) {
        this.mRotationDegree = i;
    }

    public void addRotationDegree(int i) {
        this.mRotationDegree = getRotationDegeree() + i;
    }

    public void changeIsHorizontalMirror() {
        this.mIsHorizontalMirror = !this.mIsHorizontalMirror;
    }
}
