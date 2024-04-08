package com.picspool.instafilter.resource.manager;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes3.dex */
public class BlendManager implements DMWBManager {
    private Context mContext;
    List<GPUFilterRes> resList;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public BlendManager(Context context) {
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        this.mContext = context;
        arrayList.add(initAssetItem("blend1", "blend/overlay.png", GPUFilterType.BLEND_NORMAL));
        this.resList.add(initAssetItem("blend2", "blend/overlay.png", GPUFilterType.BLEND_OVERLAY));
        this.resList.add(initAssetItem("blend3", "blend/screen.png", GPUFilterType.BLEND_SCREEN));
        this.resList.add(initAssetItem("blend4", "blend/multiply.png", GPUFilterType.BLEND_MULTIPLY));
        this.resList.add(initAssetItem("blend5", "blend/multiply.png", GPUFilterType.BLEND_ADD));
        this.resList.add(initAssetItem("blend7", "blend/multiply.png", GPUFilterType.BLEND_CHROMA_KEY));
        this.resList.add(initAssetItem("blend9", "blend/multiply.png", GPUFilterType.BLEND_COLOR_BURN));
        this.resList.add(initAssetItem("blend10", "blend/multiply.png", GPUFilterType.BLEND_COLOR_DODGE));
        this.resList.add(initAssetItem("blend11", "blend/multiply.png", GPUFilterType.BLEND_DARKEN));
        this.resList.add(initAssetItem("blend12", "blend/multiply.png", GPUFilterType.BLEND_DIFFERENCE));
        this.resList.add(initAssetItem("blend14", "blend/multiply.png", GPUFilterType.BLEND_DIVIDE));
        this.resList.add(initAssetItem("blend15", "blend/multiply.png", GPUFilterType.BLEND_EXCLUSION));
        this.resList.add(initAssetItem("blend16", "blend/multiply.png", GPUFilterType.BLEND_HARD_LIGHT));
        this.resList.add(initAssetItem("blend18", "blend/multiply.png", GPUFilterType.BLEND_LIGHTEN));
        this.resList.add(initAssetItem("blend19", "blend/multiply.png", GPUFilterType.BLEND_LINEAR_BURN));
        this.resList.add(initAssetItem("blend20", "blend/multiply.png", GPUFilterType.BLEND_LUMINOSITY));
        this.resList.add(initAssetItem("blend22", "blend/multiply.png", GPUFilterType.BLEND_SOFT_LIGHT));
        this.resList.add(initAssetItem("blend23", "blend/multiply.png", GPUFilterType.BLEND_SUBTRACT));
    }

    protected GPUFilterRes initAssetItem(String str, String str2, GPUFilterType gPUFilterType) {
        GPUFilterRes gPUFilterRes = new GPUFilterRes();
        gPUFilterRes.setContext(this.mContext);
        gPUFilterRes.setName(str);
        gPUFilterRes.setIconFileName(str2);
        gPUFilterRes.setIconType(DMWBRes.LocationType.ASSERT);
        gPUFilterRes.setFilterType(gPUFilterType);
        return gPUFilterRes;
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
