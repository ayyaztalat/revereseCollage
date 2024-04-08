package com.picspool.snappic.manager;

import android.content.Context;
import com.picspool.lib.border.CSEResType;
import com.picspool.lib.border.CSTBorderRes;
import com.picspool.snappic.res.CSSquareBarRes;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSquareBarManager {
    private float bmpratio;
    private boolean isVerticalBmp;
    private Context mContext;
    private List<CSSquareBarRes> resList;

    public CSSquareBarManager(Context context, float f) {
        this.isVerticalBmp = true;
        this.mContext = context;
        this.bmpratio = f;
        this.isVerticalBmp = f < 1.0f;
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        arrayList.add(initResItem("scale"));
        this.resList.add(initResItem("border"));
        this.resList.add(initResItem("gravity"));
        this.resList.add(initResItem("background"));
        this.resList.add(initResItem("tile"));
        this.resList.add(initResItem("mosaic"));
    }

    private CSSquareBarRes initResItem(String str) {
        CSSquareBarRes cSSquareBarRes = new CSSquareBarRes();
        cSSquareBarRes.setName(str);
        if (str.equals("scale")) {
            cSSquareBarRes.addResListItem(initBMWBImageRes("scale_fill", this.mContext.getResources().getString(R.string.squarebar_scale_fill), "square/square_editor_fill.png"));
            cSSquareBarRes.addResListItem(initBMWBImageRes("scale_inside", this.mContext.getResources().getString(R.string.squarebar_scale_inside), "square/square_editor_inside.png"));
        } else if (str.equals("gravity")) {
            cSSquareBarRes.addResListItem(initBMWBImageRes("gravity_center", this.mContext.getResources().getString(R.string.squarebar_gravity_center), "square/square_editor_gravity_center.png"));
            if (this.bmpratio != 1.0f) {
                if (this.isVerticalBmp) {
                    cSSquareBarRes.addResListItem(initBMWBImageRes("gravity_right", this.mContext.getResources().getString(R.string.squarebar_gravity_right), "square/square_editor_gravity_right.png"));
                    cSSquareBarRes.addResListItem(initBMWBImageRes("gravity_left", this.mContext.getResources().getString(R.string.squarebar_gravity_left), "square/square_editor_gravity_left.png"));
                } else {
                    cSSquareBarRes.addResListItem(initBMWBImageRes("gravity_top", this.mContext.getResources().getString(R.string.squarebar_gravity_top), "square/square_editor_gravity_top.png"));
                    cSSquareBarRes.addResListItem(initBMWBImageRes("gravity_bottom", this.mContext.getResources().getString(R.string.squarebar_gravity_bottom), "square/square_editor_gravity_bottom.png"));
                }
            }
        } else if (str.equals("border")) {
            cSSquareBarRes.addResListItem(borderresItem(this.mContext, "b00", "square/0_icon.png", "", "", "", "", "", "", "", "", 0, 0, 0, 0, "ORI"));
            cSSquareBarRes.addResListItem(borderresItem(this.mContext, "border_shadow", "square/1_icon.png", "", "", "", "", "", "", "", "", 0, 0, 0, 0, "B1"));
            cSSquareBarRes.addResListItem(borderresItem(this.mContext, "border_feather", "square/2_icon.png", "", "", "", "", "", "", "", "", 0, 0, 0, 0, "B2"));
            cSSquareBarRes.addResListItem(borderresItem(this.mContext, "border_overlay", "square/3_icon.png", "", "", "", "", "", "", "", "", 0, 0, 0, 0, "B3"));
            cSSquareBarRes.addResListItem(borderresItem(this.mContext, "b_white", "square/4_icon.png", "border/border04/l.png", "border/border04/r.png", "border/border04/t.png", "border/border04/b.png", "border/border04/l-t.png", "border/border04/r-t.png", "border/border04/l-b.png", "border/border04/r-b.png", 8, 8, 8, 8, "B4"));
            cSSquareBarRes.addResListItem(borderresItem(this.mContext, "b_black", "square/5_icon.png", "border/border05/l.png", "border/border05/r.png", "border/border05/t.png", "border/border05/b.png", "border/border05/l-t.png", "border/border05/r-t.png", "border/border05/l-b.png", "border/border05/r-b.png", 8, 8, 8, 8, "B5"));
            return cSSquareBarRes;
        } else if (str.equals("blur")) {
            cSSquareBarRes.addResListItem(initBMWBImageRes("blur", this.mContext.getResources().getString(R.string.squarebar_blur), "square/square_editor_blur.png"));
            return cSSquareBarRes;
        } else if (str.equals("mosaic")) {
            cSSquareBarRes.addResListItem(initBMWBImageRes("mosaic", this.mContext.getResources().getString(R.string.squarebar_mosaic), "square/square_editor_mosaic.png"));
            return cSSquareBarRes;
        } else if (str.equals("tile")) {
            cSSquareBarRes.addResListItem(initBMWBImageRes("tile", this.mContext.getResources().getString(R.string.squarebar_tile), "square/square_editor_tile.png"));
            return cSSquareBarRes;
        } else if (str.equals("background")) {
            cSSquareBarRes.addResListItem(initBMWBImageRes("background", this.mContext.getResources().getString(R.string.squarebar_bg), "square/square_editor_bg.png"));
            return cSSquareBarRes;
        } else if (str.equals("shape")) {
            cSSquareBarRes.addResListItem(initBMWBImageRes("shape", this.mContext.getResources().getString(R.string.squarebar_shape), "square/square_editor_bg.png"));
            return cSSquareBarRes;
        } else {
            return cSSquareBarRes;
        }
        return cSSquareBarRes;
    }

    private DMWBImageRes initBMWBImageRes(String str, String str2, String str3) {
        DMWBImageRes dMWBImageRes = new DMWBImageRes();
        dMWBImageRes.setName(str);
        dMWBImageRes.setShowText(str2);
        dMWBImageRes.setIconFileName(str3);
        dMWBImageRes.setIconType(DMWBRes.LocationType.ASSERT);
        return dMWBImageRes;
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

    public List<CSSquareBarRes> getResList() {
        return this.resList;
    }

    public List<DMWBRes> getBMWBResList() {
        ArrayList arrayList = new ArrayList();
        for (CSSquareBarRes cSSquareBarRes : this.resList) {
            arrayList.add(cSSquareBarRes);
        }
        return arrayList;
    }
}
