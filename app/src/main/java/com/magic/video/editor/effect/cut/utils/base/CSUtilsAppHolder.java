package com.magic.video.editor.effect.cut.utils.base;

import android.content.Context;

/* loaded from: classes2.dex */
public class CSUtilsAppHolder {
    public static Context appContext;

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
