package com.picspool.lib.sticker.resource.manager;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes3.dex */
public abstract class DMCommonStickersManager implements DMWBManager {
    Context context;
    List<DMWBImageRes> resList = new ArrayList();

    public abstract void init();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        return this.resList.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBImageRes getRes(int i) {
        return this.resList.get(i);
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBImageRes getRes(String str) {
        for (int i = 0; i < this.resList.size(); i++) {
            DMWBImageRes dMWBImageRes = this.resList.get(i);
            if (dMWBImageRes.getName().compareTo(str) == 0) {
                return dMWBImageRes;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public DMWBImageRes initAssetItem(Context context, String str, String str2, String str3) {
        DMWBImageRes dMWBImageRes = new DMWBImageRes();
        dMWBImageRes.setContext(this.context);
        dMWBImageRes.setName(str);
        dMWBImageRes.setIconFileName(str2);
        dMWBImageRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMWBImageRes.setImageFileName(str3);
        dMWBImageRes.setImageType(DMWBRes.LocationType.ASSERT);
        return dMWBImageRes;
    }
}
