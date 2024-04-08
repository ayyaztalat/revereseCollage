package com.picspool.instatextview.resource.manager;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes3.dex */
public class DMTextBgManager implements DMWBManager {
    private static DMTextBgManager instance;
    private List<DMTextBgRes> resList;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public static DMTextBgManager getSingletonInstance(Context context) {
        if (instance == null) {
            instance = new DMTextBgManager(context.getApplicationContext());
        }
        return instance;
    }

    public static boolean isInteger(String str) {
        return Pattern.compile("^[-\\+]?[\\d]*$").matcher(str).matches();
    }

    public DMTextBgManager(Context context) {
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        try {
            arrayList.add(initAssetItem("text_bg_none", "textbgRes/text_bubble_none.png", "", context, ""));
            String[] list = context.getResources().getAssets().list("textbgRes");
            ArrayList arrayList2 = null;
            if (list != null && list.length > 0) {
                arrayList2 = new ArrayList();
                for (int i = 0; i < list.length; i++) {
                    if (list[i].compareTo("text_bubble_none.png") != 0 && isInteger(list[i]) && isInteger(list[i])) {
                        arrayList2.add(Integer.valueOf(list[i]));
                    }
                }
            }
            if (arrayList2 != null) {
                Collections.sort(arrayList2);
                if (arrayList2 == null || arrayList2.size() <= 0) {
                    return;
                }
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    String str = "textbgRes/" + arrayList2.get(i2) + "/";
                    this.resList.add(initAssetItem("text_bg_" + arrayList2.get(i2), str + "icon.png", str + "image.png", context, str + "conf.json"));
                }
            }
        } catch (Exception unused) {
        }
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        if (this.resList.size() <= 0) {
            return 0;
        }
        return this.resList.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        List<DMTextBgRes> list = this.resList;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < this.resList.size(); i++) {
                DMTextBgRes dMTextBgRes = this.resList.get(i);
                if (dMTextBgRes.getName().compareTo(str) == 0) {
                    return dMTextBgRes;
                }
            }
        }
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMTextBgRes getRes(int i) {
        List<DMTextBgRes> list = this.resList;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return this.resList.get(i);
    }

    protected DMTextBgRes initAssetItem(String str, String str2, String str3, Context context, String str4) {
        DMTextBgRes dMTextBgRes = new DMTextBgRes();
        dMTextBgRes.setContext(context);
        dMTextBgRes.setName(str);
        dMTextBgRes.setIconFileName(str2);
        dMTextBgRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMTextBgRes.setImageFileName(str3);
        dMTextBgRes.setImageType(DMWBRes.LocationType.ASSERT);
        dMTextBgRes.setJsonFilePath(str4);
        return dMTextBgRes;
    }
}
