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
public class FilterManager implements DMWBManager {
    private Context mContext;
    private List<GPUFilterRes> resList;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public FilterManager(Context context, int i) {
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        this.mContext = context;
        if (i == 10) {
            arrayList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.TEST));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.ABAO));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.ABAO2));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.RIXI));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_ZIRAN));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_LANDIAO));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_XIAOZHEN));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_LOMO));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_HEIBAI));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_WEIMEI));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_SHENLAN));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_QINGXIN));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_FENNEN));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_QIUSE));
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.DAT_HUIYI));
        }
        if (i == 1) {
            this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.AMARO));
            this.resList.add(initAssetItem("Versa", "filter/Classic/Versa.jpg", GPUFilterType.MAYFAIR));
            this.resList.add(initAssetItem("Listless", "filter/Classic/Listless.jpg", GPUFilterType.RISE));
            this.resList.add(initAssetItem("Icy", "filter/Classic/Icy.jpg", GPUFilterType.HUDSON));
            this.resList.add(initAssetItem("Fade", "filter/Classic/Fade.jpg", GPUFilterType.VALENCIA));
            this.resList.add(initAssetItem("Mild", "filter/Classic/Mild.jpg", GPUFilterType.SIERRA));
            this.resList.add(initAssetItem("Vigour", "filter/Classic/Vigour.jpg", GPUFilterType.TOASTER));
            this.resList.add(initAssetItem("Drama", "filter/Classic/Drama.jpg", GPUFilterType.BRANNAN));
            this.resList.add(initAssetItem("Pale", "filter/Classic/Pale.jpg", GPUFilterType.WALDEN));
            this.resList.add(initAssetItem("Passion", "filter/Classic/Passion.jpg", GPUFilterType.HEFE));
            this.resList.add(initAssetItem("Pizazz", "filter/Classic/Pizazz.jpg", GPUFilterType.NASHVILLE));
            this.resList.add(initAssetItem("Alone", "filter/Classic/Alone.jpg", GPUFilterType.KELVIN));
        }
        if (i == 2) {
            this.resList.add(initAssetItem("1970", "filter/Era/1970.jpg", GPUFilterType.Y1970));
            this.resList.add(initAssetItem("1974", "filter/Era/1974.jpg", GPUFilterType.Y1975));
            this.resList.add(initAssetItem("1977", "filter/Era/1977.jpg", GPUFilterType.Y1977));
        }
        if (i == 2) {
            this.resList.add(initAssetItem("Agx100", "filter/Film/Agx100.jpg", GPUFilterType.LOFI));
            this.resList.add(initAssetItem("Kuc100", "filter/Film/Kuc100.jpg", GPUFilterType.XPRO2));
        }
        if (i == 2) {
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Weson.jpg", GPUFilterType.EARLYBIRD));
            this.resList.add(initAssetItem("SUTRO", "filter/Vintage/Lethe.jpg", GPUFilterType.SUTRO));
        }
        if (i == 3) {
            this.resList.add(initAssetItem("Selium", "filter/BW/Selium.jpg", GPUFilterType.WILLOW));
            this.resList.add(initAssetItem("Kopan", "filter/BW/Kopan.jpg", GPUFilterType.INKWELL));
            this.resList.add(initAssetItem("BWP", "filter/BW/BWRetro.jpg", GPUFilterType.BWRetro));
        }
        if (i == 3) {
            this.resList.add(initAssetItem("Ashby", "filter/BW/Selium.jpg", GPUFilterType.ASHBY));
            this.resList.add(initAssetItem("Charmes", "filter/BW/Kopan.jpg", GPUFilterType.CHARMES));
            this.resList.add(initAssetItem("Clarendon", "filter/BW/Kopan.jpg", GPUFilterType.CLARENDON));
            this.resList.add(initAssetItem("Dogpatch", "filter/BW/Kopan.jpg", GPUFilterType.DOGPATCH));
            this.resList.add(initAssetItem("Gingham", "filter/BW/BWRetro.jpg", GPUFilterType.GINGHAM));
            this.resList.add(initAssetItem("Ginza", "filter/BW/Kopan.jpg", GPUFilterType.GINZA));
        }
        if (i == 300) {
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO1));
            this.resList.add(initAssetItem("SUTRO", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO2));
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO3));
            this.resList.add(initAssetItem("SUTRO", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO4));
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO5));
            this.resList.add(initAssetItem("SUTRO", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO6));
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO7));
            this.resList.add(initAssetItem("SUTRO", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO8));
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO9));
            this.resList.add(initAssetItem("SUTRO", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO10));
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Weson.jpg", GPUFilterType.VSCO11));
            this.resList.add(initAssetItem("SUTRO", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO12));
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO13));
            this.resList.add(initAssetItem("SUTRO", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO14));
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Lethe.jpg", GPUFilterType.VSCO15));
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Weson.jpg", GPUFilterType.VSCO16));
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Weson.jpg", GPUFilterType.VSCO17));
            this.resList.add(initAssetItem("Weson", "filter/Vintage/Weson.jpg", GPUFilterType.VSCO18));
        }
        if (i == 4) {
            this.resList.add(initAssetItem("Brooklyn", "filter/BW/Selium.jpg", GPUFilterType.BROOKLYN));
            this.resList.add(initAssetItem("Helena", "filter/BW/Kopan.jpg", GPUFilterType.HELENA));
            this.resList.add(initAssetItem("Maven", "filter/BW/Kopan.jpg", GPUFilterType.MAVEN));
            this.resList.add(initAssetItem("Moon", "filter/BW/Kopan.jpg", GPUFilterType.MOON));
            this.resList.add(initAssetItem("Skyline", "filter/BW/BWRetro.jpg", GPUFilterType.SKYLINE));
            this.resList.add(initAssetItem("Stinson", "filter/BW/Kopan.jpg", GPUFilterType.STINSON));
            this.resList.add(initAssetItem("Vesper", "filter/BW/Kopan.jpg", GPUFilterType.VESPER));
        }
    }

    protected GPUFilterRes initAssetItem(String str, String str2, GPUFilterType gPUFilterType) {
        GPUFilterRes gPUFilterRes = new GPUFilterRes();
        gPUFilterRes.setContext(this.mContext);
        gPUFilterRes.setName(str);
        gPUFilterRes.setFilterType(gPUFilterType);
        gPUFilterRes.setIconFileName(str2);
        gPUFilterRes.setIconType(DMWBRes.LocationType.FILTERED);
        gPUFilterRes.setImageType(DMWBRes.LocationType.ASSERT);
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
