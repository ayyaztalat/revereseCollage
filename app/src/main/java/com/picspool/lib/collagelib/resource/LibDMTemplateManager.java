package com.picspool.lib.collagelib.resource;

import android.content.Context;
import android.graphics.Point;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;
import com.picspool.lib.collagelib.resource.collage.LibDMCollagePoint;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes3.dex */
public class LibDMTemplateManager implements DMWBManager {
    private Context mContext;
    private List<LibDMTemplateRes> resList = new ArrayList();
    private int customResWidth = 0;
    private int customResHight = 0;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public LibDMTemplateManager(Context context) {
        this.mContext = context;
        DMScreenInfoUtil.dip2px(context, 0.0f);
        DMScreenInfoUtil.dip2px(context, 10.0f);
    }

    public LibDMTemplateManager(Context context, int i) {
        this.mContext = context;
        DMScreenInfoUtil.dip2px(context, 0.0f);
        DMScreenInfoUtil.dip2px(context, 10.0f);
    }

    public LibDMTemplateManager(Context context, int i, int i2, int i3) {
        this.mContext = context;
        DMScreenInfoUtil.dip2px(context, 0.0f);
        DMScreenInfoUtil.dip2px(context, 10.0f);
        if (i == 1) {
            if (i2 == 0 || i3 == 0) {
                i2 = 300;
                i3 = 400;
            }
            setCustomRes(i2, i3);
        }
    }

    public LibDMTemplateManager(Context context, int i, int i2) {
        this.mContext = context;
        DMScreenInfoUtil.dip2px(context, 0.0f);
    }

    protected LibDMTemplateRes initAssetItem(String str, DMWBImageRes.FitType fitType, String str2, List<LibDMCollageInfo> list, int i, int i2, int i3) {
        LibDMTemplateRes libDMTemplateRes = new LibDMTemplateRes();
        libDMTemplateRes.setName(str);
        libDMTemplateRes.setIconFileName(str2);
        libDMTemplateRes.setIconType(DMWBRes.LocationType.ASSERT);
        libDMTemplateRes.setOutFrameWidth(i3);
        libDMTemplateRes.setInnerFrameWidth(i3);
        libDMTemplateRes.setCollageInfo(list);
        libDMTemplateRes.setRoundRadius(i);
        libDMTemplateRes.setFrameWidth(i3);
        libDMTemplateRes.setImage(str2);
        libDMTemplateRes.setImageType(DMWBRes.LocationType.ASSERT);
        libDMTemplateRes.setScaleType(fitType);
        return libDMTemplateRes;
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
    public LibDMTemplateRes getRes(String str) {
        for (int i = 0; i < this.resList.size(); i++) {
            LibDMTemplateRes libDMTemplateRes = this.resList.get(i);
            if (libDMTemplateRes.getName().compareTo(str) == 0) {
                return libDMTemplateRes;
            }
        }
        return null;
    }

    public void setCustomRes(int i, int i2) {
        this.customResWidth = i;
        this.customResHight = i2;
    }

    protected LibDMCollagePoint initCollagePoint1024(int i, int i2) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        libDMCollagePoint.setOriPoint(new Point((int) ((i * 2.9882812f) + 0.5f), (int) ((i2 * 2.9882812f) + 0.5f)));
        return libDMCollagePoint;
    }

    protected LibDMCollagePoint initCollagePoint(int i, int i2) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        libDMCollagePoint.setOriPoint(new Point((int) ((i * 1.0f) + 0.5f), (int) ((i2 * 1.0f) + 0.5f)));
        return libDMCollagePoint;
    }

    protected LibDMCollagePoint initCollagePoint1024(int i, int i2, boolean z) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        Point point = new Point((int) ((i * 2.9882812f) + 0.5f), (int) ((i2 * 2.9882812f) + 0.5f));
        if (z) {
            libDMCollagePoint.setIsOutRectLinePoint(1);
        }
        libDMCollagePoint.setOriPoint(point);
        return libDMCollagePoint;
    }

    protected LibDMCollagePoint initCollagePoint(int i, int i2, boolean z) {
        LibDMCollagePoint libDMCollagePoint = new LibDMCollagePoint();
        Point point = new Point((int) ((i * 1.0f) + 0.5f), (int) ((i2 * 1.0f) + 0.5f));
        if (z) {
            libDMCollagePoint.setIsOutRectLinePoint(1);
        }
        libDMCollagePoint.setOriPoint(point);
        return libDMCollagePoint;
    }
}
