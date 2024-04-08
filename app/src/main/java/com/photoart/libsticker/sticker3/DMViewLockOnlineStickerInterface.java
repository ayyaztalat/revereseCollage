package com.photoart.libsticker.sticker3;

import android.app.Activity;
import android.content.Context;

/* loaded from: classes2.dex */
public interface DMViewLockOnlineStickerInterface {

    /* loaded from: classes2.dex */
    public interface OnLockAdListener {
        void adClick();
    }

    void initNative(Context context);

    boolean isShowLockView(Activity activity);

    void setOnLockAdListener(OnLockAdListener onLockAdListener);
}
