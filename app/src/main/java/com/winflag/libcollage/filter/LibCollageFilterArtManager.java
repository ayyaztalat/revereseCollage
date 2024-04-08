package com.winflag.libcollage.filter;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class LibCollageFilterArtManager implements DMWBManager {
    private Context mContext;
    private List<GPUFilterRes> resList;
    private String strValue;

    @Override // org.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public LibCollageFilterArtManager(Context context) {
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        this.mContext = context;
        arrayList.add(initAssetItem("ori", "ori.png", GPUFilterType.NOFILTER));
        this.resList.add(initAssetItem("buenos_aires", "filter/Dat/lan_diao.jpg", GPUFilterType.DAT_LANDIAO));
        this.resList.add(initAssetItem("denim", "filter/Dat/a_bao.jpg", GPUFilterType.ABAO));
        this.resList.add(initAssetItem("denim02", "filter/Dat/xiao_zhen.jpg", GPUFilterType.DAT_XIAOZHEN));
        this.resList.add(initAssetItem("royalblue", "filter/Dat/lomo.jpg", GPUFilterType.DAT_LOMO));
        this.resList.add(initAssetItem("smoky", "filter/Dat/ri_xi.jpg", GPUFilterType.RIXI));
        this.resList.add(initAssetItem("1974", "filter/Dat/hei_bai.jpg", GPUFilterType.DAT_HEIBAI));
        this.resList.add(initAssetItem("absinth02", "filter/Dat/wei_mei.jpg", GPUFilterType.DAT_WEIMEI));
        this.resList.add(initAssetItem("denim", "filter/Dat/qing_xin.jpg", GPUFilterType.DAT_QINGXIN));
        this.resList.add(initAssetItem("denim02", "filter/Dat/fen_nen.jpg", GPUFilterType.DAT_FENNEN));
        this.resList.add(initAssetItem("Gloss", "filter/Classic/Gloss.jpg", GPUFilterType.AMARO));
        this.resList.add(initAssetItem("Icy", "filter/Classic/Icy.jpg", GPUFilterType.HUDSON));
        this.resList.add(initAssetItem("Drama", "filter/Classic/Drama.jpg", GPUFilterType.BRANNAN));
        this.resList.add(initAssetItem("Alone", "filter/Classic/Alone.jpg", GPUFilterType.KELVIN));
        this.resList.add(initAssetItem("Agx100", "filter/Film/Agx100.jpg", GPUFilterType.LOFI));
        this.resList.add(initAssetItem("1970", "filter/Era/1970.jpg", GPUFilterType.Y1970));
    }

    protected GPUFilterRes initAssetItem(String str, String str2, GPUFilterType gPUFilterType) {
        GPUFilterRes gPUFilterRes = new GPUFilterRes();
        gPUFilterRes.setContext(this.mContext);
        gPUFilterRes.setName(str);
        gPUFilterRes.setIconFileName(str2);
        gPUFilterRes.setIconType(DMWBRes.LocationType.FILTERED);
        gPUFilterRes.setFilterType(gPUFilterType);
        gPUFilterRes.setIsShowText(true);
        gPUFilterRes.setShowText(getDesStringValue(gPUFilterType));
        return gPUFilterRes;
    }

    public String getDesStringValue(GPUFilterType gPUFilterType) {
        if (gPUFilterType == GPUFilterType.NOFILTER) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_nofilter);
        } else if (gPUFilterType == GPUFilterType.DAT_LANDIAO) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_landiao);
        } else if (gPUFilterType == GPUFilterType.ABAO) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_abao);
        } else if (gPUFilterType == GPUFilterType.DAT_XIAOZHEN) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_xiaozhen);
        } else if (gPUFilterType == GPUFilterType.DAT_LOMO) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_lomo);
        } else if (gPUFilterType == GPUFilterType.RIXI) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_rixi);
        } else if (gPUFilterType == GPUFilterType.DAT_HEIBAI) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_heibai);
        } else if (gPUFilterType == GPUFilterType.DAT_WEIMEI) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_weimei);
        } else if (gPUFilterType == GPUFilterType.DAT_QINGXIN) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_qingxin);
        } else if (gPUFilterType == GPUFilterType.DAT_FENNEN) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_fennen);
        } else if (gPUFilterType == GPUFilterType.AMARO) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_amaro);
        } else if (gPUFilterType == GPUFilterType.HUDSON) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_hudson);
        } else if (gPUFilterType == GPUFilterType.BRANNAN) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_brannan);
        } else if (gPUFilterType == GPUFilterType.KELVIN) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_kelvin);
        } else if (gPUFilterType == GPUFilterType.LOFI) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_lofi);
        } else if (gPUFilterType == GPUFilterType.Y1970) {
            this.strValue = this.mContext.getResources().getString(R.string.crop_y1970);
        }
        return this.strValue;
    }

    @Override // org.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        return this.resList.size();
    }

    @Override // org.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(int i) {
        return this.resList.get(i);
    }

    @Override // org.picspool.lib.resource.manager.DMWBManager
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
