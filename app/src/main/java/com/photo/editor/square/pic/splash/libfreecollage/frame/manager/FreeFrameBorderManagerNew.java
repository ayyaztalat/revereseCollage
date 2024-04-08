package com.photo.editor.square.pic.splash.libfreecollage.frame.manager;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.border.res.DMWBBorderRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes2.dex */
public class FreeFrameBorderManagerNew implements DMWBManager {
    List<DMWBBorderRes> resList = new ArrayList();

    /* loaded from: classes2.dex */
    public enum CollageType {
        BASIC,
        FRAME
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public FreeFrameBorderManagerNew(Context context) {
        this.resList.add(borderresItem(context, "ori", "border/border00/icon.png", "", "", "", "", "", "", "", "", 0, 0, 0, 0, "ORI"));
        for (int i = 1; i <= 12; i++) {
            String str = "border" + i;
            this.resList.add(withAssetNinePitchBorderResWB(context, str, "border/" + str + "/icon.png", "border/" + str + "/l.png", "border/" + str + "/r.png", "border/" + str + "/t.png", "border/" + str + "/b.png", "border/" + str + "/l-t.png", "border/" + str + "/l-b.png", "border/" + str + "/r-t.png", "border/" + str + "/r-b.png"));
        }
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
            DMWBBorderRes dMWBBorderRes = this.resList.get(i);
            if (dMWBBorderRes.getName().compareTo(str) == 0) {
                return dMWBBorderRes;
            }
        }
        return null;
    }

    private DMWBBorderRes withAssetNinePitchBorderResWB(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        DMWBBorderRes dMWBBorderRes = new DMWBBorderRes();
        dMWBBorderRes.setContext(context);
        dMWBBorderRes.setName(str);
        dMWBBorderRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMWBBorderRes.setIconFileName(str2);
        dMWBBorderRes.setLeftUri(str3);
        dMWBBorderRes.setRightUri(str4);
        dMWBBorderRes.setTopUri(str5);
        dMWBBorderRes.setBottomUri(str6);
        dMWBBorderRes.setLeftTopCornorUri(str7);
        dMWBBorderRes.setLeftBottomCornorUri(str8);
        dMWBBorderRes.setRightTopCornorUri(str9);
        dMWBBorderRes.setRightBottomCornorUri(str10);
        dMWBBorderRes.setBorderType(DMWBBorderRes.BorderType.NINE);
        return dMWBBorderRes;
    }

    private DMWBBorderRes borderresItem(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, int i2, int i3, int i4, String str11) {
        DMWBBorderRes dMWBBorderRes = new DMWBBorderRes();
        dMWBBorderRes.setContext(context);
        dMWBBorderRes.setName(str);
        dMWBBorderRes.setIconFileName(str2);
        dMWBBorderRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMWBBorderRes.setImageType(DMWBRes.LocationType.ASSERT);
        dMWBBorderRes.setLeftUri(str3);
        dMWBBorderRes.setRightUri(str4);
        dMWBBorderRes.setTopUri(str5);
        dMWBBorderRes.setBottomUri(str6);
        dMWBBorderRes.setLeftTopCornorUri(str7);
        dMWBBorderRes.setLeftBottomCornorUri(str9);
        dMWBBorderRes.setRightTopCornorUri(str8);
        dMWBBorderRes.setRightBottomCornorUri(str10);
        dMWBBorderRes.setInnerPx(i);
        dMWBBorderRes.setInnerPy(i2);
        dMWBBorderRes.setInnerPx2(i3);
        dMWBBorderRes.setInnerPy2(i4);
        dMWBBorderRes.setShowText(str11);
        dMWBBorderRes.setIsShowText(true);
        dMWBBorderRes.setBorderType(DMWBBorderRes.BorderType.NINE);
        return dMWBBorderRes;
    }
}
