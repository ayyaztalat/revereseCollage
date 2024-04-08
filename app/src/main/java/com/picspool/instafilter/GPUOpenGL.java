package com.picspool.instafilter;

import android.app.ActivityManager;
import android.content.Context;

/* loaded from: classes3.dex */
public class GPUOpenGL {
    public static boolean supportsOpenGLES2(Context context) {
        return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo().reqGlEsVersion >= 131072;
    }
}
