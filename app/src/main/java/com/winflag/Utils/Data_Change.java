package com.winflag.Utils;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public class Data_Change {
    private static Data_Change activity_to_activity;

    /* renamed from: bl */
    public Bitmap f1730bl;

    /* renamed from: br */
    public Bitmap f1731br;
    private int containerHeight;
    private int containerWidth;

    /* renamed from: tl */
    public Bitmap f1732tl;

    /* renamed from: tr */
    public Bitmap f1733tr;

    public int getContainerWidth() {
        return this.containerWidth;
    }

    public void setContainerWidth(int i) {
        this.containerWidth = i;
    }

    public int getContainerHeight() {
        return this.containerHeight;
    }

    public void setContainerHeight(int i) {
        this.containerHeight = i;
    }

    public Bitmap getBl() {
        return this.f1730bl;
    }

    public void setBl(Bitmap bitmap) {
        this.f1730bl = bitmap;
    }

    public Bitmap getBr() {
        return this.f1731br;
    }

    public void setBr(Bitmap bitmap) {
        this.f1731br = bitmap;
    }

    public Bitmap getTl() {
        return this.f1732tl;
    }

    public void setTl(Bitmap bitmap) {
        this.f1732tl = bitmap;
    }

    public Bitmap getTr() {
        return this.f1733tr;
    }

    public void setTr(Bitmap bitmap) {
        this.f1733tr = bitmap;
    }

    public static Data_Change getInstance() {
        if (activity_to_activity == null) {
            synchronized (Data_Change.class) {
                if (activity_to_activity == null) {
                    activity_to_activity = new Data_Change();
                }
            }
        }
        return activity_to_activity;
    }
}
