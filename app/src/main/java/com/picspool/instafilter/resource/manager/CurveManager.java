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
public class CurveManager implements DMWBManager {
    private Context mContext;
    private List<CPUFilterRes> resList;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public CurveManager(Context context) {
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        this.mContext = context;
        arrayList.add(initAssetItem("ori", "ori.png", CPUFilterType.NOFILTER));
        this.resList.add(initAssetItem("7AM", "lens/cc1.png", CPUFilterType.T7AM));
        this.resList.add(initAssetItem("1974", "lens/cc2.png", CPUFilterType.Y1974));
        this.resList.add(initAssetItem("absinth", "lens/cc3.png", CPUFilterType.ABSINTH));
        this.resList.add(initAssetItem("buenos aires", "lens/cc4.png", CPUFilterType.BUENOS_AIRES));
        this.resList.add(initAssetItem("denim", "lens/cc5.png", CPUFilterType.DENIM));
        this.resList.add(initAssetItem("royal blue", "lens/cc6.png", CPUFilterType.ROYAL_BLUE));
        this.resList.add(initAssetItem("smoky", "lens/cc7.png", CPUFilterType.SMOKY));
        this.resList.add(initAssetItem("toaster", "lens/cc8.png", CPUFilterType.TOASTER));
    }

    public CPUFilterRes initAssetItem(String str, String str2, CPUFilterType cPUFilterType) {
        CPUFilterRes cPUFilterRes = new CPUFilterRes();
        cPUFilterRes.setContext(this.mContext);
        cPUFilterRes.setName(str);
        cPUFilterRes.setIconFileName(str2);
        cPUFilterRes.setIconType(DMWBRes.LocationType.FILTERED);
        cPUFilterRes.setFilterType(cPUFilterType);
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
