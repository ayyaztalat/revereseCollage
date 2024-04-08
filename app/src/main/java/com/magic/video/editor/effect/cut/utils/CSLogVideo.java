package com.magic.video.editor.effect.cut.utils;

import android.util.Log;

/* loaded from: classes2.dex */
public class CSLogVideo {
    public static boolean isDebug() {
        return false;
    }

    public static void logE(String str, String str2) {
        try {
            if (isDebug()) {
                Log.d(str, str2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
