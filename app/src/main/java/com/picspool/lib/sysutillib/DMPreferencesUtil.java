package com.picspool.lib.sysutillib;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class DMPreferencesUtil {
    public static void save(Context context, String str, String str2, String str3) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.putString(str2, str3);
        edit.commit();
    }

    public static Map<String, ?> getAll(Context context, String str) {
        return context.getSharedPreferences(str, 0).getAll();
    }

    public static void remove(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        edit.remove(str2);
        edit.commit();
    }

    public static void save(Context context, String str, HashMap<String, String> hashMap) {
        SharedPreferences.Editor edit = context.getSharedPreferences(str, 0).edit();
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            edit.putString(entry.getKey(), entry.getValue());
        }
        edit.commit();
    }

    public static String get(Context context, String str, String str2) {
        try {
            return context.getSharedPreferences(str, 0).getString(str2, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }
}
