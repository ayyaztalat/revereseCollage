package com.winflag.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/* loaded from: classes.dex */
public class CacheUtils {
    public static String SP_NAME = "user";

    /* renamed from: sp */
    private static SharedPreferences f1729sp;

    public static SharedPreferences getSp(Context context) {
        if (f1729sp == null) {
            f1729sp = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return f1729sp;
    }

    public static boolean getBoolean(Context context, String str) {
        return getSp(context).getBoolean(str, false);
    }

    public static boolean getBoolean(Context context, String str, boolean z) {
        return getSp(context).getBoolean(str, z);
    }

    public static void setBoolean(Context context, String str, boolean z) {
        SharedPreferences.Editor edit = getSp(context).edit();
        edit.putBoolean(str, z);
        edit.commit();
    }

    public static String getString(Context context, String str) {
        return getSp(context).getString(str, null);
    }

    public static String getString(Context context, String str, String str2) {
        return getSp(context).getString(str, str2);
    }

    public static void setString(Context context, String str, String str2) {
        SharedPreferences.Editor edit = getSp(context).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static int getInt(Context context, String str) {
        return getSp(context).getInt(str, -1);
    }

    public static int getInt(Context context, String str, int i) {
        return getSp(context).getInt(str, i);
    }

    public static void setInt(Context context, String str, int i) {
        SharedPreferences.Editor edit = getSp(context).edit();
        edit.putInt(str, i);
        edit.commit();
    }
}
