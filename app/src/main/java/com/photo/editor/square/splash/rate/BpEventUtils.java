package com.photo.editor.square.splash.rate;

import android.content.Context;
import android.text.TextUtils;
import java.util.HashMap;

/* loaded from: classes2.dex */
public class BpEventUtils {
    public static void fireBaseEvent(Context context, String str) {
    }

    public static void pointItemEvent(Context context, String str, String str2, String str3) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3) && !TextUtils.isEmpty(str)) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(str2, str3);
//                    FlurryAgent.logEvent(str, hashMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void pointItemEvent(Context context, String str) {
        try {
//            FlurryAgent.logEvent(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void pointItemEvent(Context context, String str, String str2) {
        if (context != null) {
            try {
                if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(str, str2);
//                    FlurryAgent.logEvent(str, hashMap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
