package com.baiwang.libuiinstalens.masicview;

import android.content.Context;
import com.baiwang.libuiinstalens.masicview.CSDrawMosaic;
import com.picspool.lib.resource.DMWBRes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CSMasicManager {
    private Context mContext;
    private List<CSMosaicRes> resList;
    public List<DMWBRes> getResList() {
        ArrayList arrayList = new ArrayList();
        for (CSMosaicRes cSMosaicRes : resList) {
            arrayList.add(cSMosaicRes);
        }
        return arrayList;
    }

    public CSMasicManager(Context context) {
        mContext = context;
        resList = new ArrayList<>();
        resList.add(initMasicItem("blur", "mosaic/001blur.jpg", "", CSDrawMosaic.Mode.BLUR));
        resList.add(initMasicItem("normal", "mosaic/002mosaic.jpg", "", CSDrawMosaic.Mode.NORMAL));
        resList.add(initMasicItem("style1_01", "mosaic/003style1.jpg", "mosaic/paint_001.png", CSDrawMosaic.Mode.CUSTOM));
        resList.add(initMasicItem("style1_02", "mosaic/004style2.jpg", "mosaic/paint_002.png", CSDrawMosaic.Mode.CUSTOM));
        resList.add(initMasicItem("style1_03", "mosaic/005style3.jpg", "mosaic/paint_003.png", CSDrawMosaic.Mode.CUSTOM));
        resList.add(initMasicItem("style2_01", "mosaic/015style13.jpg", "mosaic/015style13.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_02", "mosaic/016style14.jpg", "mosaic/016style14.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_03", "mosaic/017style15.jpg", "mosaic/017style15.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_04", "mosaic/018style16.jpg", "mosaic/018style16.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_05", "mosaic/019style17.jpg", "mosaic/019style17.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_06", "mosaic/020style18.jpg", "mosaic/020style18.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_07", "mosaic/021style19.jpg", "mosaic/021style19.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_08", "mosaic/022style20.jpg", "mosaic/022style20.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_09", "mosaic/023style21.jpg", "mosaic/023style21.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_10", "mosaic/024style22.jpg", "mosaic/024style22.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_11", "mosaic/025style23.jpg", "mosaic/025style23.jpg", CSDrawMosaic.Mode.BITMAP));
        resList.add(initMasicItem("style2_12", "mosaic/026style24.jpg", "mosaic/026style24.jpg", CSDrawMosaic.Mode.BITMAP));
    }

    private CSMosaicRes initMasicItem(String str, String str2, String str3, CSDrawMosaic.Mode mode) {
        CSMosaicRes cSMosaicRes = new CSMosaicRes();
        cSMosaicRes.setName(str);
        cSMosaicRes.setIconFileName(str2);
        cSMosaicRes.setIconType(DMWBRes.LocationType.ASSERT);
        cSMosaicRes.setImageFileName(str3);
        cSMosaicRes.setImageType(DMWBRes.LocationType.ASSERT);
        cSMosaicRes.setmMosaicMode(mode);
        return cSMosaicRes;
    }

    private CSMosaicRes initMasicMultiBmpItem(String str, String str2, String str3, CSDrawMosaic.Mode mode) {
        CSMosaicRes cSMosaicRes = new CSMosaicRes();
        cSMosaicRes.setName(str);
        cSMosaicRes.setIconFileName(str2);
        cSMosaicRes.setIconType(DMWBRes.LocationType.ASSERT);
        cSMosaicRes.setmMosaicMode(mode);
        try {
            String[] list = mContext.getAssets().list(str3);
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
