package com.picspool.snappic.snap;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes.dex */
public class CSBestFrameManager implements DMWBManager {
    private static CSBestFrameManager instance;
    private List<CSBestFrameRes> resList;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public static CSBestFrameManager getSingletonInstance(Context context) {
        if (instance == null) {
            instance = new CSBestFrameManager(context);
        }
        return instance;
    }

    public CSBestFrameManager(Context context) {
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        arrayList.add(initAssetItem("framenone", "snap/0/icon.png", "", context, "snap/0/conf.json"));
        for (int i = 1; i <= 15; i++) {
            this.resList.add(initAssetItem("frame" + i, "snap/" + i + "/icon.png", "snap/" + i + "/image.png", context, "snap/" + i + "/conf.json"));
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
        List<CSBestFrameRes> list = this.resList;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < this.resList.size(); i++) {
                CSBestFrameRes cSBestFrameRes = this.resList.get(i);
                if (cSBestFrameRes.getName().compareTo(str) == 0) {
                    return cSBestFrameRes;
                }
            }
        }
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(int i) {
        List<CSBestFrameRes> list = this.resList;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return this.resList.get(i);
    }

    protected CSBestFrameRes initAssetItem(String str, String str2, String str3, Context context, String str4) {
        CSBestFrameRes cSBestFrameRes = new CSBestFrameRes();
        cSBestFrameRes.setContext(context);
        cSBestFrameRes.setName(str);
        cSBestFrameRes.setIconFileName(str2);
        cSBestFrameRes.setIconType(DMWBRes.LocationType.ASSERT);
        cSBestFrameRes.setImageFileName(str3);
        cSBestFrameRes.setImageType(DMWBRes.LocationType.ASSERT);
        cSBestFrameRes.setJsonFilePath(str4);
        return cSBestFrameRes;
    }
}
