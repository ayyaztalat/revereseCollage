package com.photoart.lib.SysSnap;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes3.dex */
public class BMStickerGroupManager implements DMWBManager {
    private static BMStickerGroupManager instance;
    private Context mContext;
    private List<BMStickerImageRes> resList;

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    private BMStickerGroupManager(Context context) {
        this.mContext = context;
        ArrayList arrayList = new ArrayList();
        this.resList = arrayList;
        arrayList.add(initAssetItem("g1", "sticker/group/emoji_one.png", "sticker/group/emoji_one_select.png"));
        this.resList.add(initAssetItem("g2", "sticker/group/emoji_two.png", "sticker/group/emoji_two_select.png"));
        this.resList.add(initAssetItem("g3", "sticker/group/emoji_three.png", "sticker/group/emoji_three_select.png"));
        this.resList.add(initAssetItem("g4", "sticker/group/emoji_four.png", "sticker/group/emoji_four_select.png"));
        this.resList.add(initAssetItem("g5", "sticker/group/emoji_five.png", "sticker/group/emoji_five_select.png"));
        this.resList.add(initAssetItem("g6", "sticker/group/emoji_six.png", "sticker/group/emoji_six_select.png"));
    }

    public static BMStickerGroupManager getSingletManager(Context context) {
        if (instance == null) {
            instance = new BMStickerGroupManager(context);
        }
        return instance;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        List<BMStickerImageRes> list = this.resList;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        List<BMStickerImageRes> list = this.resList;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < this.resList.size(); i++) {
                BMStickerImageRes bMStickerImageRes = this.resList.get(i);
                if (bMStickerImageRes.getName().compareTo(str) == 0) {
                    return bMStickerImageRes;
                }
            }
        }
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(int i) {
        List<BMStickerImageRes> list = this.resList;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return this.resList.get(i);
    }

    protected BMStickerImageRes initAssetItem(String str, String str2, String str3) {
        BMStickerImageRes bMStickerImageRes = new BMStickerImageRes();
        bMStickerImageRes.setContext(this.mContext);
        bMStickerImageRes.setName(str);
        bMStickerImageRes.setIconFileName(str2);
        bMStickerImageRes.setIconType(DMWBRes.LocationType.ASSERT);
        bMStickerImageRes.setIconPressedFileName(str3);
        return bMStickerImageRes;
    }
}
