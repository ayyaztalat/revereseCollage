package com.picspool.lib.onlinestore.resource.manager;

import android.content.Context;
import java.util.Date;
import com.picspool.lib.onlinestore.resource.WBMaterialFactory;
import com.picspool.lib.onlinestore.resource.WBMaterialRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes3.dex */
public class DMClearCacheMaterial {
    private static long interval = 604800000;
    private static String material_store_prfes = "material_cache_time";

    private static void recordHaveRequestRec(Context context) {
        DMPreferencesUtil.save(context, material_store_prfes, "last_time", String.valueOf(new Date().getTime()));
    }

    public static boolean isFitTimeConditon(Context context) {
        String str = DMPreferencesUtil.get(context, material_store_prfes, "last_time");
        if (str == null) {
            recordHaveRequestRec(context);
            return false;
        }
        if (new Date().getTime() - Long.parseLong(str) >= interval) {
            recordHaveRequestRec(context);
            return true;
        }
        return false;
    }

    public static void clearCache(Context context) {
        for (WBMaterialRes wBMaterialRes : WBMaterialFactory.CreateMaterialFromFilesDir(context)) {
            if (!wBMaterialRes.isContentExist()) {
                wBMaterialRes.delMaterialFromFile();
            }
        }
    }
}
