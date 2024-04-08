package com.photo.editor.square.pic.splash.libfreecollage.resource.collage;

import android.content.Context;
import com.photo.editor.square.pic.splash.libfreecollage.res.BgScaleType;
import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes2.dex */
public class PlateStyle_Free_Manager implements DMWBManager {
    private Context mContext;
    private List<DMWBRes> resList = new ArrayList();
    private final String ASSET_FOLDER = "platestyle/";
    private final String ASSET_FOLDER_PRESS = "platestyle/press/";

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public PlateStyle_Free_Manager(Context context) {
        this.mContext = context;
    }

    public void initManager(int i, BgScaleType bgScaleType) {
        this.resList.clear();
        if (i == 1) {
            this.resList.add(initItem("p1_square_1", "one_square_1.png"));
            this.resList.add(initItem("p1_square_2", "one_square_2.png"));
            this.resList.add(initItem("p1_square_3", "one_square_3.png"));
        } else if (i == 2) {
            this.resList.add(initItem("p2_square_1", "two_square_1.png"));
            this.resList.add(initItem("p2_square_2", "two_square_2.png"));
            this.resList.add(initItem("p2_square_3", "two_square_3.png"));
            this.resList.add(initItem("p2_square_4", "two_square_4.png"));
            this.resList.add(initItem("p2_square_5", "two_square_5.png"));
            this.resList.add(initItem("p2_square_6", "two_square_6.png"));
        } else if (i == 3) {
            this.resList.add(initItem("p3_square_1", "three_square_1.png"));
            this.resList.add(initItem("p3_square_2", "three_square_2.png"));
            this.resList.add(initItem("p3_square_3", "three_square_3.png"));
            this.resList.add(initItem("p3_square_4", "three_square_4.png"));
            this.resList.add(initItem("p3_square_5", "three_square_5.png"));
            this.resList.add(initItem("p3_square_6", "three_square_6.png"));
        } else if (i == 4) {
            this.resList.add(initItem("p4_square_1", "four_square_1.png"));
            this.resList.add(initItem("p4_square_2", "four_square_2.png"));
            this.resList.add(initItem("p4_square_3", "four_square_3.png"));
            this.resList.add(initItem("p4_square_4", "four_square_4.png"));
            this.resList.add(initItem("p4_square_5", "four_square_5.png"));
            this.resList.add(initItem("p4_square_6", "four_square_6.png"));
        } else if (i == 5) {
            this.resList.add(initItem("p5_square_1", "five_square_1.png"));
            this.resList.add(initItem("p5_square_2", "five_square_2.png"));
            this.resList.add(initItem("p5_square_3", "five_square_3.png"));
            this.resList.add(initItem("p5_square_4", "five_square_4.png"));
            this.resList.add(initItem("p5_square_5", "five_square_5.png"));
            this.resList.add(initItem("p5_square_6", "five_square_6.png"));
        } else if (i == 6) {
            this.resList.add(initItem("p6_square_1", "six_square_1.png"));
            this.resList.add(initItem("p6_square_2", "six_square_2.png"));
            this.resList.add(initItem("p6_square_3", "six_square_3.png"));
            this.resList.add(initItem("p6_square_4", "six_square_4.png"));
            this.resList.add(initItem("p6_square_5", "six_square_5.png"));
            this.resList.add(initItem("p6_square_6", "six_square_6.png"));
        } else if (i == 7) {
            this.resList.add(initItem("p7_square_1", "seven_square_1.png"));
            this.resList.add(initItem("p7_square_2", "seven_square_2.png"));
            this.resList.add(initItem("p7_square_3", "seven_square_3.png"));
            this.resList.add(initItem("p7_square_4", "seven_square_4.png"));
            this.resList.add(initItem("p7_square_5", "seven_square_5.png"));
            this.resList.add(initItem("p7_square_6", "seven_square_6.png"));
        } else if (i == 8) {
            this.resList.add(initItem("p8_square_1", "eight_square_1.png"));
            this.resList.add(initItem("p8_square_2", "eight_square_2.png"));
            this.resList.add(initItem("p8_square_3", "eight_square_3.png"));
            this.resList.add(initItem("p8_square_4", "eight_square_4.png"));
            this.resList.add(initItem("p8_square_5", "eight_square_5.png"));
            this.resList.add(initItem("p8_square_6", "eight_square_6.png"));
        } else if (i == 9) {
            this.resList.add(initItem("p9_square_1", "nine_square_1.png"));
            this.resList.add(initItem("p9_square_2", "nine_square_2.png"));
            this.resList.add(initItem("p9_square_3", "nine_square_3.png"));
            this.resList.add(initItem("p9_square_4", "nine_square_4.png"));
            this.resList.add(initItem("p9_square_5", "nine_square_5.png"));
            this.resList.add(initItem("p9_square_6", "nine_square_6.png"));
        }
    }

    public DMWBImageRes initItem(String str, String str2) {
        DMWBImageRes dMWBImageRes = new DMWBImageRes();
        dMWBImageRes.setContext(this.mContext);
        dMWBImageRes.setName(str);
        dMWBImageRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMWBImageRes.setIconFileName("platestyle/" + str2);
        dMWBImageRes.setImageType(DMWBRes.LocationType.ASSERT);
        dMWBImageRes.setImageFileName("platestyle/press/" + str2);
        return dMWBImageRes;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        List<DMWBRes> list = this.resList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(int i) {
        List<DMWBRes> list = this.resList;
        if (list != null) {
            return list.get(i);
        }
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        for (int i = 0; i < this.resList.size(); i++) {
            DMWBRes dMWBRes = this.resList.get(i);
            if (dMWBRes.getName().compareTo(str) == 0) {
                return dMWBRes;
            }
        }
        return null;
    }
}
