package com.picspool.lib.onlinestore.resource.manager;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.onlinestore.resource.WBMaterialRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes3.dex */
public class DMMaterialResManager implements DMWBManager {
    Context context;
    private List<WBMaterialRes> materialRess;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public DMMaterialResManager(Context context) {
        this.context = context;
        this.materialRess = new ArrayList();
    }

    public DMMaterialResManager(Context context, List<WBMaterialRes> list) {
        this.context = context;
        this.materialRess = list;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        return this.materialRess.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(int i) {
        List<WBMaterialRes> list = this.materialRess;
        if (list == null || list.size() <= i) {
            return null;
        }
        return this.materialRess.get(i);
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        for (int i = 0; i < this.materialRess.size() && str != null; i++) {
            WBMaterialRes wBMaterialRes = this.materialRess.get(i);
            if (wBMaterialRes.getName().compareTo(str) == 0) {
                return wBMaterialRes;
            }
        }
        return null;
    }

    public List<WBMaterialRes> getMaterialRess() {
        return this.materialRess;
    }

    public void setMaterialRess(List<WBMaterialRes> list) {
        this.materialRess = list;
    }
}
