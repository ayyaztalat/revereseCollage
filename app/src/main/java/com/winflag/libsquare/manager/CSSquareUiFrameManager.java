package com.winflag.libsquare.manager;

import android.content.Context;
import com.picspool.lib.border.CSEResType;
import com.picspool.lib.border.CSTBorderRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class CSSquareUiFrameManager implements DMWBManager {
    List<CSTBorderRes> resList = new ArrayList();

    @Override // org.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public List<CSTBorderRes> getResList() {
        return this.resList;
    }

    public CSSquareUiFrameManager(Context context) {
        CSSquareUiFrameManager cSSquareUiFrameManager = this;
        cSSquareUiFrameManager.resList.add(borderresItem(context, "b00", "border/border00/icon.png", "", "", "", "", "", "", "", "", 0, 0, 0, 0, "ORI"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "border_shadow", "border/border_shadow/icon.png", "", "", "", "", "", "", "", "", 0, 0, 0, 0, "B1"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "border_feather", "border/border_feather/icon.png", "", "", "", "", "", "", "", "", 0, 0, 0, 0, "B2"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "border_overlay", "border/border_overlay/icon.png", "", "", "", "", "", "", "", "", 0, 0, 0, 0, "B3"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "border_white", "border/border04/icon.png", "border/border04/l.png", "border/border04/r.png", "border/border04/t.png", "border/border04/b.png", "border/border04/l-t.png", "border/border04/r-t.png", "border/border04/l-b.png", "border/border04/r-b.png", 8, 8, 8, 8, "B4"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "b_black", "border/border05/icon.png", "border/border05/l.png", "border/border05/r.png", "border/border05/t.png", "border/border05/b.png", "border/border05/l-t.png", "border/border05/r-t.png", "border/border05/l-b.png", "border/border05/r-b.png", 8, 8, 8, 8, "B5"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "b_06", "border/border06/icon.png", "border/border06/l.png", "border/border06/r.png", "border/border06/t.png", "border/border06/b.png", "border/border06/l-t.png", "border/border06/r-t.png", "border/border06/l-b.png", "border/border06/r-b.png", 8, 8, 8, 8, "B6"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "border_07", "border/border07/icon.png", "border/border07/l.png", "border/border07/r.png", "border/border07/t.png", "border/border07/b.png", "border/border07/l-t.png", "border/border07/r-t.png", "border/border07/l-b.png", "border/border07/r-b.png", 8, 8, 8, 8, "B7"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "border_08", "border/border08/icon.png", "border/border08/l.png", "border/border08/r.png", "border/border08/t.png", "border/border08/b.png", "border/border08/l-t.png", "border/border08/r-t.png", "border/border08/l-b.png", "border/border08/r-b.png", 8, 8, 8, 8, "B8"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "b_09", "border/border09/icon.png", "border/border09/l.png", "border/border09/r.png", "border/border09/t.png", "border/border09/b.png", "border/border09/l-t.png", "border/border09/r-t.png", "border/border09/l-b.png", "border/border09/r-b.png", 8, 8, 8, 8, "B9"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "b_10", "border/border10/icon.png", "border/border10/l.png", "border/border10/r.png", "border/border10/t.png", "border/border10/b.png", "border/border10/l-t.png", "border/border10/r-t.png", "border/border10/l-b.png", "border/border10/r-b.png", 30, 30, 20, 20, "B10"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "b_11", "border/border11/icon.png", "border/border11/l.png", "border/border11/r.png", "border/border11/t.png", "border/border11/b.png", "border/border11/l-t.png", "border/border11/r-t.png", "border/border11/l-b.png", "border/border11/r-b.png", 8, 8, 8, 8, "B11"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "b_12", "border/border12/icon.png", "border/border12/l.png", "border/border12/r.png", "border/border12/t.png", "border/border12/b.png", "border/border12/l-t.png", "border/border12/r-t.png", "border/border12/l-b.png", "border/border12/r-b.png", 8, 8, 8, 8, "B12"));
        cSSquareUiFrameManager.resList.add(borderresItem(context, "b_13", "border/border13/icon.png", "border/border13/l.png", "border/border13/r.png", "border/border13/t.png", "border/border13/b.png", "border/border13/l-t.png", "border/border13/r-t.png", "border/border13/l-b.png", "border/border13/r-b.png", 8, 8, 8, 8, "B13"));
        for (int i = 14; i < 20; i++) {
            cSSquareUiFrameManager = this;
            cSSquareUiFrameManager.resList.add(borderresItem(context, "b_" + i, "border/border" + i + "/icon.png", "border/border" + i + "/l.png", "border/border" + i + "/r.png", "border/border" + i + "/t.png", "border/border" + i + "/b.png", "border/border" + i + "/l-t.png", "border/border" + i + "/r-t.png", "border/border" + i + "/l-b.png", "border/border" + i + "/r-b.png", 8, 8, 8, 8, "B" + i));
        }
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
            CSTBorderRes cSTBorderRes = this.resList.get(i);
            if (cSTBorderRes.getName().compareTo(str) == 0) {
                return cSTBorderRes;
            }
        }
        return null;
    }

    private CSTBorderRes borderresItem(Context context, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, int i2, int i3, int i4, String str11) {
        CSTBorderRes cSTBorderRes = new CSTBorderRes(context);
        cSTBorderRes.setContext(context);
        cSTBorderRes.setResType(CSEResType.ASSET);
        cSTBorderRes.setName(str);
        cSTBorderRes.setIconFileName(str2);
        cSTBorderRes.setIconType(DMWBRes.LocationType.ASSERT);
        cSTBorderRes.setImageType(DMWBRes.LocationType.ASSERT);
        cSTBorderRes.setLeftUri(str3);
        cSTBorderRes.setRightUri(str4);
        cSTBorderRes.setTopUri(str5);
        cSTBorderRes.setBottomUri(str6);
        cSTBorderRes.setLeftTopCornorUri(str7);
        cSTBorderRes.setLeftBottomCornorUri(str9);
        cSTBorderRes.setRightTopCornorUri(str8);
        cSTBorderRes.setRightBottomCornorUri(str10);
        cSTBorderRes.setInnerPx(i);
        cSTBorderRes.setInnerPy(i2);
        cSTBorderRes.setInnerPx2(i3);
        cSTBorderRes.setInnerPy2(i4);
        cSTBorderRes.setShowText(str11);
        cSTBorderRes.setIsShowText(true);
        return cSTBorderRes;
    }
}
