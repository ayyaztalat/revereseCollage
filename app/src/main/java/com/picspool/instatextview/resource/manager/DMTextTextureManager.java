package com.picspool.instatextview.resource.manager;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import com.picspool.lib.onlinestore.resource.WBMaterialFactory;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes3.dex */
public class DMTextTextureManager implements DMWBManager {
    private static DMTextTextureManager instance;
    private List<DMWBImageRes> resList = new ArrayList();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public static DMTextTextureManager getSingletonInstance(Context context) {
        if (instance == null) {
            instance = new DMTextTextureManager(context.getApplicationContext());
        }
        return instance;
    }

    public static boolean isInteger(String str) {
        return Pattern.compile("^[-\\+]?[\\d]*$").matcher(str).matches();
    }

    public DMTextTextureManager(Context context) {
        try {
            String[] list = context.getResources().getAssets().list("text/texture");
            ArrayList arrayList = null;
            int i = 0;
            if (list != null && list.length > 0) {
                arrayList = new ArrayList();
                for (String str : list) {
                    str = str.contains(".png") ? str.replaceAll(".png", "") : str;
                    if (str.compareTo(WBMaterialFactory.MaterialIconName) != 0 && isInteger(str) && isInteger(str)) {
                        arrayList.add(Integer.valueOf(str));
                    }
                }
            }
            if (arrayList != null) {
                Collections.sort(arrayList);
                if (arrayList == null || arrayList.size() <= 0) {
                    return;
                }
                while (i < arrayList.size()) {
                    List<DMWBImageRes> list2 = this.resList;
                    StringBuilder sb = new StringBuilder();
                    sb.append("text_texture_");
                    i++;
                    sb.append(i);
                    list2.add(initAssetItem(sb.toString(), "text/texture/icon/" + i + ".png", "text/texture/" + i + ".png", context));
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
        List<DMWBImageRes> list = this.resList;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < this.resList.size(); i++) {
                DMWBImageRes dMWBImageRes = this.resList.get(i);
                if (dMWBImageRes.getName().compareTo(str) == 0) {
                    return dMWBImageRes;
                }
            }
        }
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBImageRes getRes(int i) {
        List<DMWBImageRes> list = this.resList;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return this.resList.get(i);
    }

    protected DMWBImageRes initAssetItem(String str, String str2, String str3, Context context) {
        DMWBImageRes dMWBImageRes = new DMWBImageRes();
        dMWBImageRes.setContext(context);
        dMWBImageRes.setName(str);
        dMWBImageRes.setIconFileName(str2);
        dMWBImageRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMWBImageRes.setImageFileName(str3);
        dMWBImageRes.setImageType(DMWBRes.LocationType.ASSERT);
        return dMWBImageRes;
    }
}
