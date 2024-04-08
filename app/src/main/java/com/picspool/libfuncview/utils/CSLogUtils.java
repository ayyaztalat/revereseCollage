package com.picspool.libfuncview.utils;

import android.util.Log;

/* loaded from: classes.dex */
public class CSLogUtils {
    private static final String TAG = "PotoGrid";
    public static boolean isDebug = true;

    private CSLogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /* renamed from: i */
    public static void m61i(String str) {
        if (isDebug) {
            Log.i(TAG, str);
        }
    }

    /* renamed from: d */
    public static void m65d(String str) {
        if (isDebug) {
            Log.d(TAG, str);
        }
    }

    /* renamed from: e */
    public static void m63e(String str) {
        if (isDebug) {
            Log.e(TAG, str);
        }
    }

    /* renamed from: v */
    public static void m59v(String str) {
        if (isDebug) {
            Log.v(TAG, str);
        }
    }

    /* renamed from: w */
    public static void m57w(String str) {
        if (isDebug) {
            Log.w(TAG, str);
        }
    }

    /* renamed from: i */
    public static void m60i(String str, String str2) {
        if (isDebug) {
            Log.i(str, str2);
        }
    }

    /* renamed from: d */
    public static void m64d(String str, String str2) {
        if (isDebug) {
            Log.d(str, str2);
        }
    }

    /* renamed from: e */
    public static void m62e(String str, String str2) {
        if (isDebug) {
            Log.e(str, str2);
        }
    }

    /* renamed from: v */
    public static void m58v(String str, String str2) {
        if (isDebug) {
            Log.v(str, str2);
        }
    }

    /* renamed from: w */
    public static void m56w(String str, String str2) {
        if (isDebug) {
            Log.w(str, str2);
        }
    }
}
