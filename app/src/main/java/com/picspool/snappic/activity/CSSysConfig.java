package com.picspool.snappic.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes.dex */
public class CSSysConfig {
    public static int isLowMemoryDevice = 1;
    public static int isMiddleMemoryDevice = 3;
    public static int islargeMemoryDevice = 2;
    static final String prefsName = "Config";

    private static int getMemoryDevice(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager.getMemoryClass() >= 64) {
            return islargeMemoryDevice;
        }
        if (activityManager.getMemoryClass() > 32 && activityManager.getMemoryClass() < 64) {
            return isMiddleMemoryDevice;
        }
        activityManager.getMemoryClass();
        return isLowMemoryDevice;
    }

    public static int getImageQuality(Context context) {
        int memoryClass = 0;
        if (((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() <= 48) {
            memoryClass = 800;
        }
        double sqrt = Math.sqrt(((memoryClass * 0.125f) / 6.0f) * 1024.0f * 1024.0f);
        if (Build.VERSION.SDK_INT >= 26) {
            if (sqrt > 1920.0d) {
                sqrt = 1920.0d;
            }
        } else if (Build.VERSION.SDK_INT >= 23) {
            if (sqrt > 1280.0d) {
                sqrt = 1280.0d;
            }
        } else if (sqrt > 960.0d) {
            sqrt = 960.0d;
        }
        return (int) sqrt;
    }

    public static boolean isShowAd(Context context) {
        return DMScreenInfoUtil.screenHeightDp(context) > 450;
    }

    public static int getAndroidSDKVersion() {
        try {
            return Integer.valueOf(Build.VERSION.SDK).intValue();
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    public static boolean isPadScreenMode(Context context) {
        return DMScreenInfoUtil.screenHeightDp(context) >= 800;
    }
}
