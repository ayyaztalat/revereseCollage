package com.picspool.instafilter.resource.manager;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.resource.CPUFilterRes;
import com.picspool.lib.filter.cpu.CPUFilterType;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes3.dex */
public class ShotManager implements DMWBManager {
    private Context mContext;
    private List<CPUFilterRes> resList = new ArrayList();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public ShotManager(Context context) {
        this.mContext = context;
    }

    public void initShotData() {
        this.resList.add(initAssetItem("1974", "lens/s1.png", CPUFilterType.Y1974_SHOT));
        this.resList.add(initAssetItem("absinth02", "lens/s2.png", CPUFilterType.ABSINTH_SHOT));
        this.resList.add(initAssetItem("buenos_aires", "lens/s3.png", CPUFilterType.BUENOS_AIRES_SHOT));
        this.resList.add(initAssetItem("denim", "lens/s4.png", CPUFilterType.DENIM_SHOT));
        this.resList.add(initAssetItem("denim02", "lens/s5.png", CPUFilterType.DENIM2_SHOT));
        this.resList.add(initAssetItem("royalblue", "lens/s6.png", CPUFilterType.ROYAL_BLUE_SHOT));
        this.resList.add(initAssetItem("smoky", "lens/s7.png", CPUFilterType.SMOKY_SHOT));
        this.resList.add(initAssetItem("toaster", "lens/s8.png", CPUFilterType.TOASTER_SHOT));
    }

    protected CPUFilterRes initAssetItem(String str, String str2, CPUFilterType cPUFilterType) {
        CPUFilterRes cPUFilterRes = new CPUFilterRes();
        cPUFilterRes.setContext(this.mContext);
        cPUFilterRes.setName(str);
        cPUFilterRes.setIconFileName(str2);
        cPUFilterRes.setIconType(DMWBRes.LocationType.FILTERED);
        cPUFilterRes.setFilterType(cPUFilterType);
        cPUFilterRes.setIsShowText(true);
        return cPUFilterRes;
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
            CPUFilterRes cPUFilterRes = this.resList.get(i);
            if (cPUFilterRes.getName().compareTo(str) == 0) {
                return cPUFilterRes;
            }
        }
        return null;
    }
}
