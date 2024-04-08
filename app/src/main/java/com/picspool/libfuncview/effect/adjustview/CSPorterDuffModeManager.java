package com.picspool.libfuncview.effect.adjustview;

import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSPorterDuffModeManager {
    private List<DMWBRes> filterResList;

    public CSPorterDuffModeManager() {
        ArrayList arrayList = new ArrayList();
        this.filterResList = arrayList;
        arrayList.add(initFilterRes("COLOR_BURN", GPUFilterType.BLEND_COLOR_BURN));
        this.filterResList.add(initFilterRes("COLOR_DODGE", GPUFilterType.BLEND_COLOR_DODGE));
        this.filterResList.add(initFilterRes("DARKEN", GPUFilterType.BLEND_DARKEN));
        this.filterResList.add(initFilterRes("DIFFERENCE", GPUFilterType.BLEND_DIFFERENCE));
        this.filterResList.add(initFilterRes("DISSOLVE", GPUFilterType.BLEND_DISSOLVE));
        this.filterResList.add(initFilterRes("EXCLUSION", GPUFilterType.BLEND_EXCLUSION));
        this.filterResList.add(initFilterRes("SOURCE_OVER", GPUFilterType.BLEND_SOURCE_OVER));
        this.filterResList.add(initFilterRes("HARD_LIGHT", GPUFilterType.BLEND_HARD_LIGHT));
        this.filterResList.add(initFilterRes("LIGHTEN", GPUFilterType.BLEND_LIGHTEN));
        this.filterResList.add(initFilterRes("ADD", GPUFilterType.BLEND_ADD));
        this.filterResList.add(initFilterRes("DIVIDE", GPUFilterType.BLEND_DIVIDE));
        this.filterResList.add(initFilterRes("MULTIPLY", GPUFilterType.BLEND_MULTIPLY));
        this.filterResList.add(initFilterRes("OVERLAY", GPUFilterType.BLEND_OVERLAY));
        this.filterResList.add(initFilterRes("SCREEN", GPUFilterType.BLEND_SCREEN));
        this.filterResList.add(initFilterRes("HUE", GPUFilterType.BLEND_HUE));
        this.filterResList.add(initFilterRes("SATURATION", GPUFilterType.BLEND_SATURATION));
        this.filterResList.add(initFilterRes("LUMINOSITY", GPUFilterType.BLEND_LUMINOSITY));
        this.filterResList.add(initFilterRes("LINEAR_BURN", GPUFilterType.BLEND_LINEAR_BURN));
        this.filterResList.add(initFilterRes("SOFT_LIGHT", GPUFilterType.BLEND_SOFT_LIGHT));
        this.filterResList.add(initFilterRes("SUBTRACT", GPUFilterType.BLEND_SUBTRACT));
        this.filterResList.add(initFilterRes("CHROMA_KEY", GPUFilterType.BLEND_CHROMA_KEY));
        this.filterResList.add(initFilterRes("NORMAL", GPUFilterType.BLEND_NORMAL));
    }

    private GPUFilterRes initFilterRes(String str, GPUFilterType gPUFilterType) {
        GPUFilterRes gPUFilterRes = new GPUFilterRes();
        gPUFilterRes.setShowText(str);
        gPUFilterRes.setFilterType(gPUFilterType);
        return gPUFilterRes;
    }

    public List<DMWBRes> getFilterResList() {
        return this.filterResList;
    }
}
