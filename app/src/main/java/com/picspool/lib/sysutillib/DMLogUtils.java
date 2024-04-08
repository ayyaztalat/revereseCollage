package com.picspool.lib.sysutillib;

import android.util.Log;

/* loaded from: classes3.dex */
public class DMLogUtils {
    public static boolean LOGENABLE = false;

    public static void setLOGENABLE(boolean z) {
        LOGENABLE = z;
    }

    /* renamed from: v */
    public static void m1v(String str, String str2) {
        if (LOGENABLE) {
            Log.v(str, str2);
        }
    }

    /* renamed from: i */
    public static void m2i(String str, String str2) {
        if (LOGENABLE) {
            Log.i(str, str2);
        }
    }

    /* renamed from: d */
    public static void m4d(String str, String str2) {
        if (LOGENABLE) {
            Log.d(str, str2);
        }
    }

    /* renamed from: w */
    public static void m0w(String str, String str2) {
        if (LOGENABLE) {
            Log.w(str, str2);
        }
    }

    /* renamed from: e */
    public static void m3e(String str, String str2) {
        if (LOGENABLE) {
            Log.e(str, str2);
        }
    }
}
