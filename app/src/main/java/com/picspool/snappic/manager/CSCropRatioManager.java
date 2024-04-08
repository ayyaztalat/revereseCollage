package com.picspool.snappic.manager;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSCropRatioManager {
    private List<DMWBRes> bmwbImageRes;
    private Context mContext;

    public List<DMWBRes> getBmwbImageRes() {
        return this.bmwbImageRes;
    }

    public CSCropRatioManager(Context context) {
        this.mContext = context;
        ArrayList arrayList = new ArrayList();
        this.bmwbImageRes = arrayList;
        arrayList.add(initImageRes("crop_ori", "cropbar/img_square_crop_ori.png", R.string.crop_ratio_original));
        this.bmwbImageRes.add(initImageRes("crop_free", "cropbar/img_square_crop_free.png", R.string.crop_ratio_free));
        this.bmwbImageRes.add(initImageRes("crop_gold", "cropbar/img_square_crop_gold.png", R.string.crop_ratio_gold));
        this.bmwbImageRes.add(initImageRes("crop_1to1", "cropbar/img_square_crop_1to1.png", R.string.crop_ratio__1_1));
        this.bmwbImageRes.add(initImageRes("crop_4to3", "cropbar/img_square_crop_4to3.png", R.string.crop_ratio__4_3));
        this.bmwbImageRes.add(initImageRes("crop_3to4", "cropbar/img_square_crop_3to4.png", R.string.crop_ratio__3_4));
        this.bmwbImageRes.add(initImageRes("crop_16to9", "cropbar/img_square_crop_16to9.png", R.string.crop_ratio__16_9));
        this.bmwbImageRes.add(initImageRes("crop_9to16", "cropbar/img_square_crop_9to16.png", R.string.crop_ratio__9_16));
    }

    private DMWBImageRes initImageRes(String str, String str2, int i) {
        DMWBImageRes dMWBImageRes = new DMWBImageRes();
        dMWBImageRes.setName(str);
        dMWBImageRes.setIconFileName(str2);
        dMWBImageRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMWBImageRes.setShowText(this.mContext.getResources().getString(i));
        return dMWBImageRes;
    }
}
