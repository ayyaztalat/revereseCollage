package com.baiwang.libuiinstalens.masicview;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public interface CSSmearMaskPath extends CSActionGesture {

    /* loaded from: classes.dex */
    public interface ResultBmpListener {
        void onReturnResultBmp(Bitmap bitmap);
    }

    void actionEraser();

    void setOnResultBmpListener(ResultBmpListener resultBmpListener);
}
