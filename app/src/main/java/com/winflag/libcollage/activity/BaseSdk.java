package com.winflag.libcollage.activity;

import android.content.Context;

/* loaded from: classes.dex */
public class BaseSdk {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context2) {
        context = context2.getApplicationContext();
    }
}
