package com.winflag.libcollage.widget.radioview;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;
import com.winflag.libcollage.resource.RatioRes;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class TemplateRatioManager {
    private Context mContext;
    private List<RatioRes> radtio_list;

    public List<RatioRes> getRadtio_list() {
        return this.radtio_list;
    }

    public TemplateRatioManager(Context context) {
        this.mContext = context;
        initList();
    }

    private void initList() {
        ArrayList arrayList = new ArrayList();
        this.radtio_list = arrayList;
        arrayList.add(initradioitem("1:1", "1:1", 1.0f, "collage_radioview/ratio_1_1_press.png", "collage_radioview/ratio_1_1_press.png", Integer.valueOf(R.drawable.btn_ratio_1_1)));
        this.radtio_list.add(initradioitem("4:5", "4:5", 0.8f, "collage_radioview/ratio_4_5_press.png", "collage_radioview/ratio_4_5_press.png", Integer.valueOf(R.drawable.btn_ratio_4_5)));
        this.radtio_list.add(initradioitem("5:4", "5:4", 1.25f, "collage_radioview/ratio_5_4_press.png", "collage_radioview/ratio_5_4_press.png", Integer.valueOf(R.drawable.btn_ratio_5_4)));
        this.radtio_list.add(initradioitem("3:4", "3:4", 0.75f, "collage_radioview/ratio_3_4_press.png", "collage_radioview/ratio_3_4_press.png", Integer.valueOf(R.drawable.btn_ratio_3_4)));
        this.radtio_list.add(initradioitem("4:3", "4:3", 1.3333334f, "collage_radioview/ratio_4_3_press.png", "collage_radioview/ratio_4_3_press.png", Integer.valueOf(R.drawable.btn_ratio_4_3)));
        this.radtio_list.add(initradioitem("2:3", "2:3", 0.6666667f, "collage_radioview/ratio_2_3_press.png", "collage_radioview/ratio_2_3_press.png", Integer.valueOf(R.drawable.btn_ratio_2_3)));
        this.radtio_list.add(initradioitem("3:2", "3:2", 1.5f, "collage_radioview/ratio_3_2_press.png", "collage_radioview/ratio_3_2_press.png", Integer.valueOf(R.drawable.btn_ratio_3_2)));
        this.radtio_list.add(initradioitem("9:16", "9:16", 0.5625f, "collage_radioview/ratio_9_16_press.png", "collage_radioview/ratio_9_16_press.png", Integer.valueOf(R.drawable.btn_ratio_9_16)));
        this.radtio_list.add(initradioitem("16:9", "16:9", 1.7777778f, "collage_radioview/ratio_16_9_press.png", "collage_radioview/ratio_16_9_press.png", Integer.valueOf(R.drawable.btn_ratio_16_9)));
    }

    private RatioRes initradioitem(String str, String str2, float f, String str3, String str4, Integer num) {
        RatioRes ratioRes = new RatioRes();
        ratioRes.setName(str);
        ratioRes.setRadio(f);
        ratioRes.setRatio_reversal(str2);
        ratioRes.setImageFileName(str3);
        ratioRes.setImagereversalFileName(str4);
        ratioRes.setImageType(DMWBRes.LocationType.ASSERT);
        ratioRes.setInteger(num);
        return ratioRes;
    }
}
