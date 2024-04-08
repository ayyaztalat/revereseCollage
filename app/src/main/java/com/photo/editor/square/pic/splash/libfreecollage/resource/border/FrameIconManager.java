package com.photo.editor.square.pic.splash.libfreecollage.resource.border;

import android.content.Context;
import com.photo.editor.square.pic.splash.libfreecollage.frame.resource.FreeFrameBorderRes;
import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes2.dex */
public class FrameIconManager implements DMWBManager {
    private Context mContext;
    private List<FreeFrameBorderRes> mFreeFrameBorderResList = new ArrayList();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public FrameIconManager(Context context) {
        this.mContext = context;
        initResources();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        return this.mFreeFrameBorderResList.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(int i) {
        return this.mFreeFrameBorderResList.get(i);
    }

    private void initResources() {
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("ori", "border/border00/icon.png", null, null, null, null, null, null, null, null));
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("border01", "border/border01/icon.png", "border/border01/l.png", "border/border01/r.png", "border/border01/t.png", "border/border01/b.png", "border/border01/l-t.png", "border/border01/l-b.png", "border/border01/r-b.png", "border/border01/r-t.png"));
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("border02", "border/border02/icon.png", "border/border02/l.png", "border/border02/r.png", "border/border02/t.png", "border/border02/b.png", "border/border02/l-t.png", "border/border02/l-b.png", "border/border02/r-t.png", "border/border02/r-b.png"));
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("border03", "border/border03/icon.png", "border/border03/l.png", "border/border03/r.png", "border/border03/t.png", "border/border03/b.png", "border/border03/l-t.png", "border/border03/l-b.png", "border/border03/r-t.png", "border/border03/r-b.png"));
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("border04", "border/border04/icon.png", "border/border04/l.png", "border/border04/r.png", "border/border04/t.png", "border/border04/b.png", "border/border04/l-t.png", "border/border04/l-b.png", "border/border04/r-t.png", "border/border04/r-b.png"));
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("border05", "border/border05/icon.png", "border/border05/l.png", "border/border05/r.png", "border/border05/t.png", "border/border05/b.png", "border/border05/l-t.png", "border/border05/l-b.png", "border/border05/r-t.png", "border/border05/r-b.png"));
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("border06", "border/border06/icon.png", "border/border06/l.png", "border/border06/r.png", "border/border06/t.png", "border/border06/b.png", "border/border06/l-t.png", "border/border06/l-b.png", "border/border06/r-t.png", "border/border06/r-b.png"));
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("border07", "border/border07/icon.png", "border/border07/l.png", "border/border07/r.png", "border/border07/t.png", "border/border07/b.png", "border/border07/l-t.png", "border/border07/l-b.png", "border/border07/r-t.png", "border/border07/r-b.png"));
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("border08", "border/border08/icon.png", "border/border08/l.png", "border/border08/r.png", "border/border08/t.png", "border/border08/b.png", "border/border08/l-t.png", "border/border08/l-b.png", "border/border08/r-t.png", "border/border08/r-b.png"));
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("border09", "border/border09/icon.png", "border/border09/l.png", "border/border09/r.png", "border/border09/t.png", "border/border09/b.png", "border/border09/l-t.png", "border/border09/l-b.png", "border/border09/r-t.png", "border/border09/r-b.png"));
        this.mFreeFrameBorderResList.add(withAssetNinePitchBorderRes("border10", "border/border10/icon.png", "border/border10/l.png", "border/border10/r.png", "border/border10/t.png", "border/border10/b.png", "border/border10/l-t.png", "border/border10/l-b.png", "border/border10/r-t.png", "border/border10/r-b.png"));
    }

    private FreeFrameBorderRes withAssetNinePitchBorderRes(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) {
        FreeFrameBorderRes freeFrameBorderRes = new FreeFrameBorderRes();
        freeFrameBorderRes.setContext(this.mContext);
        freeFrameBorderRes.setName(str);
        freeFrameBorderRes.setIconType(DMWBRes.LocationType.ASSERT);
        freeFrameBorderRes.setIconFileName(str2);
        freeFrameBorderRes.setLeftUri(str3);
        freeFrameBorderRes.setRightUri(str4);
        freeFrameBorderRes.setTopUri(str5);
        freeFrameBorderRes.setBottomUri(str6);
        freeFrameBorderRes.setLeftTopCornorUri(str7);
        freeFrameBorderRes.setLeftBottomCornorUri(str8);
        freeFrameBorderRes.setRightTopCornorUri(str9);
        freeFrameBorderRes.setRightBottomCornorUri(str10);
        return freeFrameBorderRes;
    }
}
