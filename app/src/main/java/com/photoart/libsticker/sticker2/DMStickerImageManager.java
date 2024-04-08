package com.photoart.libsticker.sticker2;

import android.content.Context;

import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes2.dex */
public class DMStickerImageManager implements DMWBManager {
    private Context mContext;
    private List<DMStickerImageRes> resList = new ArrayList();

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public DMStickerImageManager(Context context, DMStickerModeManager.StickerMode stickerMode) {
        this.mContext = context;
        int i = 1;
        if (stickerMode == DMStickerModeManager.StickerMode.STICKERALL) {
            for (int i2 = 1; i2 <= 32; i2++) {
                this.resList.add(initAssetItem("sticker1_" + i2, "sticker/emoji/" + i2 + ".png", "sticker/emoji/" + i2 + ".png"));
            }
            for (int i3 = 1; i3 <= 39; i3++) {
                this.resList.add(initAssetItem("sticker2_" + i3, "sticker/heart/" + i3 + ".png", "sticker/heart/" + i3 + ".png"));
            }
            while (i <= 20) {
                this.resList.add(initAssetItem("sticker7_" + i, "sticker/popular/" + i + ".png", "sticker/popular/" + i + ".png"));
                i++;
            }
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER1) {
            while (i <= 32) {
                this.resList.add(initAssetItem("sticker1_" + i, "sticker/emoji/" + i + ".png", "sticker/emoji/" + i + ".png"));
                i++;
            }
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER2) {
            while (i <= 40) {
                this.resList.add(initAssetItem("sticker2_" + i, "sticker/heart/" + i + ".png", "sticker/heart/" + i + ".png"));
                i++;
            }
        } else if (stickerMode != DMStickerModeManager.StickerMode.STICKER7) {
            DMStickerModeManager.StickerMode stickerMode2 = DMStickerModeManager.StickerMode.ONLINE;
        } else {
            while (i <= 20) {
                this.resList.add(initAssetItem("sticker7_" + i, "sticker/popular/" + i + ".png", "sticker/popular/" + i + ".png"));
                i++;
            }
        }
    }

    public void initOnLineStickerImageList(DMStickerImageRes dMStickerImageRes) {
        int i = 0;
        while (i < dMStickerImageRes.getStickerNumber()) {
            List<DMStickerImageRes> list = this.resList;
            String str = dMStickerImageRes.getName() + i;
            StringBuilder sb = new StringBuilder();
            sb.append(dMStickerImageRes.getStickersUrlBase());
            i++;
            sb.append(i);
            sb.append(".png");
            list.add(initUrlOnLineItem(str, sb.toString(), dMStickerImageRes.getName(), dMStickerImageRes.isNativeJsonSticker()));
        }
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        if (this.resList.size() <= 0) {
            return 0;
        }
        return this.resList.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        List<DMStickerImageRes> list = this.resList;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < this.resList.size(); i++) {
                DMStickerImageRes dMStickerImageRes = this.resList.get(i);
                if (dMStickerImageRes.getName().compareTo(str) == 0) {
                    return dMStickerImageRes;
                }
            }
        }
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMStickerImageRes getRes(int i) {
        List<DMStickerImageRes> list = this.resList;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return this.resList.get(i);
    }

    protected DMStickerImageRes initAssetItem(String str, String str2, String str3) {
        DMStickerImageRes dMStickerImageRes = new DMStickerImageRes();
        dMStickerImageRes.setContext(this.mContext);
        dMStickerImageRes.setName(str);
        dMStickerImageRes.setIconFileName(str2);
        dMStickerImageRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMStickerImageRes.setImageFileName(str3);
        dMStickerImageRes.setImageType(DMWBRes.LocationType.ASSERT);
        return dMStickerImageRes;
    }

    protected DMStickerImageRes initUrlOnLineItem(String str, String str2, String str3, boolean z) {
        DMStickerImageRes dMStickerImageRes = new DMStickerImageRes();
        dMStickerImageRes.setContext(this.mContext);
        dMStickerImageRes.setName(str);
        dMStickerImageRes.setIconFileName(str2);
        dMStickerImageRes.setIconType(DMWBRes.LocationType.ONLINE);
        dMStickerImageRes.setNativeJsonSticker(z);
        dMStickerImageRes.setImageFileName(str3);
        dMStickerImageRes.setImageType(DMWBRes.LocationType.ONLINE);
        return dMStickerImageRes;
    }
}
