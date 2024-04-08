package com.picspool.libfuncview.masicview;

import android.content.Context;
import com.picspool.libfuncview.masicview.CSDrawMosaic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSMasicManager {
    private Context mContext;
    private List<CSMosaicRes> resList;

    public List<DMWBRes> getResList() {
        ArrayList arrayList = new ArrayList();
        for (CSMosaicRes cSMosaicRes : this.resList) {
            arrayList.add(cSMosaicRes);
        }
        return arrayList;
    }

    public CSMasicManager(Context context) {
        this.mContext = context;
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        arrayList.add(initMasicItem("blur", "blur", "mosaic/001blur.jpg", "", CSDrawMosaic.Mode.BLUR));
        this.resList.add(initMasicItem("normal", "mosaic", "mosaic/002mosaic.jpg", "", CSDrawMosaic.Mode.NORMAL));
        this.resList.add(initMasicItem("style1_01", "K01", "mosaic/003style1.jpg", "mosaic/paint_001.png", CSDrawMosaic.Mode.CUSTOM));
        this.resList.add(initMasicItem("style1_02", "K02", "mosaic/004style2.jpg", "mosaic/paint_002.png", CSDrawMosaic.Mode.CUSTOM));
        this.resList.add(initMasicItem("style1_03", "K03", "mosaic/005style3.jpg", "mosaic/paint_003.png", CSDrawMosaic.Mode.CUSTOM));
        this.resList.add(initMasicItem("C21", "T01", "mosaic/mosaic_src_21.jpg", "mosaic/mosaic_src_21.jpg", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C09", "T02", "mosaic/mosaic_src_09.jpg", "mosaic/mosaic_src_09.jpg", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C10", "T03", "mosaic/mosaic_src_10.png", "mosaic/mosaic_src_10.png", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C22", "T04", "mosaic/mosaic_src_22.png", "mosaic/mosaic_src_22_main.jpg", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C23", "T32", "mosaic/mosaic_src_23.png", "mosaic/mosaic_src_23.png", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C24", "T33", "mosaic/mosaic_src_24.png", "mosaic/mosaic_src_24.png", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C25", "T34", "mosaic/mosaic_src_25.png", "mosaic/mosaic_src_25.png", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C26", "T35", "mosaic/mosaic_src_26.png", "mosaic/mosaic_src_26.png", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C27", "T36", "mosaic/mosaic_src_27.png", "mosaic/mosaic_src_27_main.jpg", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C28", "T37", "mosaic/mosaic_src_28.png", "mosaic/mosaic_src_28.png", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C04", "K01", "mosaic/mosaic_src_04.png", "mosaic/mosaic_src_04.png", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C05", "K02", "mosaic/mosaic_src_05.jpg", "mosaic/mosaic_src_05.jpg", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C06", "K03", "mosaic/mosaic_src_06.jpg", "mosaic/mosaic_src_06.jpg", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C07", "K04", "mosaic/mosaic_src_07.jpg", "mosaic/mosaic_src_07.jpg", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C08", "K05", "mosaic/mosaic_src_08.jpg", "mosaic/mosaic_src_08.jpg", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C29", "K73", "mosaic/mosaic_src_29.jpg", "mosaic/mosaic_src_29.jpg", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C01", "K74", "mosaic/mosaic_src_01.png", "mosaic/mosaic_src_01.png", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C02", "K75", "mosaic/mosaic_src_02.png", "mosaic/mosaic_src_02.png", CSDrawMosaic.Mode.BITMAP));
        this.resList.add(initMasicItem("C20", "K76", "mosaic/mosaic_src_20.png", "mosaic/mosaic_src_20_main.jpg", CSDrawMosaic.Mode.BITMAP));
    }

    private CSMosaicRes initMasicItem(String str, String str2, String str3, String str4, CSDrawMosaic.Mode mode) {
        CSMosaicRes cSMosaicRes = new CSMosaicRes();
        cSMosaicRes.setName(str);
        cSMosaicRes.setIconFileName(str3);
        cSMosaicRes.setIconType(DMWBRes.LocationType.ASSERT);
        cSMosaicRes.setImageFileName(str4);
        cSMosaicRes.setImageType(DMWBRes.LocationType.ASSERT);
        cSMosaicRes.setmMosaicMode(mode);
        cSMosaicRes.setShowText(str2);
        return cSMosaicRes;
    }

    private CSMosaicRes initMasicMultiBmpItem(String str, String str2, String str3, CSDrawMosaic.Mode mode) {
        CSMosaicRes cSMosaicRes = new CSMosaicRes();
        cSMosaicRes.setName(str);
        cSMosaicRes.setIconFileName(str2);
        cSMosaicRes.setIconType(DMWBRes.LocationType.ASSERT);
        cSMosaicRes.setmMosaicMode(mode);
        try {
            String[] list = this.mContext.getAssets().list(str3);
            if (list != null && list.length > 0) {
                for (int i = 0; i < list.length; i++) {
                    list[i] = str3 + "/" + list[i];
                }
            }
            cSMosaicRes.setBmps_filename(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cSMosaicRes;
    }
}
