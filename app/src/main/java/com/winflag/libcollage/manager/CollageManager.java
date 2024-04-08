package com.winflag.libcollage.manager;

import android.content.Context;
import com.winflag.libcollage.resource.TemplateRes;
import java.util.List;

/* loaded from: classes.dex */
class CollageManager {
    CollageManager() {
    }

    public static void attachToList(Context context, int i, int i2, int i3, int i4, List<TemplateRes> list) {
        String str = "template/" + i;
        try {
            for (String str2 : context.getAssets().list(str)) {
                list.add(CollageManagerUtils.initItem("g" + i + "_" + list.size(), str + "/" + str2 + "/", i, i3, i4));
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
