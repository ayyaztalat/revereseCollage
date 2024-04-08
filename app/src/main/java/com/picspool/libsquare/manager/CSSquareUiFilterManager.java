package com.picspool.libsquare.manager;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes.dex */
public class CSSquareUiFilterManager implements DMWBManager {
    private Context mContext;
    private List<GPUFilterRes> resList;
    private String strValue;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public CSSquareUiFilterManager(Context context) {
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        this.mContext = context;
        arrayList.add(initAssetItem("F0", GPUFilterType.NOFILTER, "ORI"));
        this.resList.add(initAssetItem("F1", GPUFilterType.F1, "A1"));
        this.resList.add(initAssetItem("F4", GPUFilterType.F4, "A2"));
        this.resList.add(initAssetItem("F7", GPUFilterType.F11, "A3"));
        this.resList.add(initAssetItem("F8", GPUFilterType.F8, "A4"));
        this.resList.add(initAssetItem("F9", GPUFilterType.F9, "A5"));
        this.resList.add(initAssetItem("F11", GPUFilterType.F7, "A6"));
        this.resList.add(initAssetItem("F13", GPUFilterType.F13, "B1"));
        this.resList.add(initAssetItem("F15", GPUFilterType.F15, "B2"));
        this.resList.add(initAssetItem("F35", GPUFilterType.F35, "B3"));
        this.resList.add(initAssetItem("F37", GPUFilterType.F37, "B4"));
        this.resList.add(initAssetItem("F39", GPUFilterType.F39, "B5"));
        this.resList.add(initAssetItem("F47", GPUFilterType.F47, "B6"));
        this.resList.add(initAssetItem("F50", GPUFilterType.F50, "C1"));
        this.resList.add(initAssetItem("F52", GPUFilterType.F52, "C2"));
        this.resList.add(initAssetItem("F56", GPUFilterType.F56, "C3"));
        this.resList.add(initAssetItem("F58", GPUFilterType.F58, "C4"));
        this.resList.add(initAssetItem("F62", GPUFilterType.F62, "C5"));
        this.resList.add(initAssetItem("F63", GPUFilterType.F63, "C6"));
        this.resList.add(initAssetItem("F69", GPUFilterType.F69, "D1"));
        this.resList.add(initAssetItem("F74", GPUFilterType.F74, "D2"));
        this.resList.add(initAssetItem("F81", GPUFilterType.F81, "D3"));
        this.resList.add(initAssetItem("F85", GPUFilterType.F85, "D4"));
    }

    protected GPUFilterRes initAssetItem(String str, GPUFilterType gPUFilterType, String str2) {
        GPUFilterRes gPUFilterRes = new GPUFilterRes();
        gPUFilterRes.setContext(this.mContext);
        gPUFilterRes.setName(str);
        gPUFilterRes.setIconType(DMWBRes.LocationType.FILTERED);
        gPUFilterRes.setFilterType(gPUFilterType);
        gPUFilterRes.setIsShowText(true);
        gPUFilterRes.setShowText(str2);
        return gPUFilterRes;
    }

    public String getDesStringValue(String str) {
        this.strValue = str;
        return str;
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
            GPUFilterRes gPUFilterRes = this.resList.get(i);
            if (gPUFilterRes.getName().compareTo(str) == 0) {
                return gPUFilterRes;
            }
        }
        return null;
    }
}
