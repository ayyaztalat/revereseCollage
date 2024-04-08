package com.photo.editor.square.pic.splash.libfreecollage.resource;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes2.dex */
public class TplFilterManager implements DMWBManager {
    private Context context;
    private List<TplFilterRes> resList = new ArrayList();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public TplFilterManager(Context context) {
        initList();
        this.context = context;
    }

    private void initList() {
        this.resList.add(initFiterResItem("lens/cc1.png", GPUFilterType.DAT_FENNEN));
        this.resList.add(initFiterResItem("lens/cc2.png", GPUFilterType.Y1975));
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        return this.resList.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(int i) {
        return this.resList.get(i);
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        for (int i = 0; i < this.resList.size(); i++) {
            TplFilterRes tplFilterRes = this.resList.get(i);
            if (tplFilterRes.getName().compareTo(str) == 0) {
                return tplFilterRes;
            }
        }
        return null;
    }

    public TplFilterRes initFiterResItem(String str, GPUFilterType gPUFilterType) {
        TplFilterRes tplFilterRes = new TplFilterRes();
        tplFilterRes.setIconFileName(str);
        tplFilterRes.setIconType(DMWBRes.LocationType.ASSERT);
        tplFilterRes.setImageType(DMWBRes.LocationType.ASSERT);
        tplFilterRes.setGpuType(gPUFilterType);
        tplFilterRes.setContext(this.context);
        return tplFilterRes;
    }
}
