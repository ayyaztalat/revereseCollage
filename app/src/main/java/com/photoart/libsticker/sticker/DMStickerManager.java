package com.photoart.libsticker.sticker;

import android.content.Context;
import android.graphics.BitmapFactory;
import com.photoart.libsticker.sticker2.DMStickerModeManager;
import com.picspool.lib.resource.DMWBRes;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;

/* loaded from: classes2.dex */
public class DMStickerManager implements DMWBManager {
    private Context mContext;
    List<DMStickerRes> resList;

    /* loaded from: classes2.dex */
    public enum EStickerType {
        All,
        STICKER1,
        STICKER2,
        STICKER3,
        STICKER4,
        STICKER5,
        STICKER6
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMWBRes getRes(String str) {
        return null;
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public boolean isRes(String str) {
        return false;
    }

    public DMStickerManager(Context context) {
        this.resList = new ArrayList();
        this.mContext = context;
        initSticker1();
    }

    public DMStickerManager(Context context, EStickerType eStickerType) {
        this.resList = new ArrayList();
        this.mContext = context;
        if (eStickerType == EStickerType.All) {
            initSticker1();
            initSticker2();
            initSticker3();
            initSticker4();
            initSticker5();
            initSticker6();
        }
        if (eStickerType == EStickerType.STICKER1) {
            initSticker1();
        }
        if (eStickerType == EStickerType.STICKER2) {
            initSticker2();
        }
        if (eStickerType == EStickerType.STICKER3) {
            initSticker3();
        }
        if (eStickerType == EStickerType.STICKER4) {
            initSticker4();
        }
        if (eStickerType == EStickerType.STICKER5) {
            initSticker5();
        }
        if (eStickerType == EStickerType.STICKER6) {
            initSticker6();
        }
    }

    public DMStickerManager(Context context, DMStickerModeManager.StickerMode stickerMode) {
        this.resList = new ArrayList();
        this.mContext = context;
        this.resList = new ArrayList();
        String str = "sticker1_";
        if (stickerMode == DMStickerModeManager.StickerMode.STICKERALL) {
            int i = 1;
            for (int i2 = 50; i <= i2; i2 = 50) {
                this.resList.add(initAssetItem(str + i, "sticker/emoji/" + i + ".png", "sticker/emoji/" + i + ".png"));
                i++;
                str = str;
            }
            for (int i3 = 1; i3 <= 40; i3++) {
                this.resList.add(initAssetItem("sticker2_" + i3, "sticker/gesture/" + i3 + ".png", "sticker/gesture/" + i3 + ".png"));
            }
            for (int i4 = 1; i4 <= 54; i4++) {
                this.resList.add(initAssetItem("sticker3_" + i4, "sticker/symbol/" + i4 + ".png", "sticker/symbol/" + i4 + ".png"));
            }
            for (int i5 = 1; i5 <= 32; i5++) {
                this.resList.add(initAssetItem("sticker4_" + i5, "sticker/face/" + i5 + ".png", "sticker/face/" + i5 + ".png"));
            }
            for (int i6 = 1; i6 <= 40; i6++) {
                this.resList.add(initAssetItem("sticker5_" + i6, "sticker/animal/" + i6 + ".png", "sticker/animal/" + i6 + ".png"));
            }
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER1) {
            for (int i7 = 1; i7 <= 50; i7++) {
                this.resList.add(initAssetItem("sticker1_" + i7, "sticker/emoji/" + i7 + ".png", "sticker/emoji/" + i7 + ".png"));
            }
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER2) {
            for (int i8 = 1; i8 <= 40; i8++) {
                this.resList.add(initAssetItem("sticker2_" + i8, "sticker/gesture/" + i8 + ".png", "sticker/gesture/" + i8 + ".png"));
            }
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER3) {
            for (int i9 = 1; i9 <= 54; i9++) {
                this.resList.add(initAssetItem("sticker3_" + i9, "sticker/symbol/" + i9 + ".png", "sticker/symbol/" + i9 + ".png"));
            }
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER4) {
            for (int i10 = 1; i10 <= 32; i10++) {
                this.resList.add(initAssetItem("sticker4_" + i10, "sticker/face/" + i10 + ".png", "sticker/face/" + i10 + ".png"));
            }
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER5) {
            for (int i11 = 1; i11 <= 40; i11++) {
                this.resList.add(initAssetItem("sticker5_" + i11, "sticker/animal/" + i11 + ".png", "sticker/animal/" + i11 + ".png"));
            }
        }
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public int getCount() {
        return this.resList.size();
    }

    @Override // com.picspool.lib.resource.manager.DMWBManager
    public DMStickerRes getRes(int i) {
        List<DMStickerRes> list = this.resList;
        if (list == null || list.size() <= 0) {
            return null;
        }
        return this.resList.get(i);
    }

    private void initSticker1() {
        for (int i = 1; i <= 32; i++) {
            this.resList.add(initAssetItem(this.mContext, "emoji_000" + i, "common_sticker/emoji/" + i + ".png", "common_sticker/emoji/" + i + ".png"));
        }
    }

    private void initSticker2() {
        for (int i = 1; i <= 39; i++) {
            this.resList.add(initAssetItem(this.mContext, "heart_000" + i, "sticker/heart/" + i + ".png", "sticker/heart/" + i + ".png"));
        }
    }

    private void initSticker3() {
        for (int i = 1; i <= 40; i++) {
            this.resList.add(initAssetItem(this.mContext, "gesture_000" + i, "sticker/gesture/" + i + ".png", "sticker/gesture/" + i + ".png"));
        }
    }

    private void initSticker4() {
        for (int i = 1; i <= 54; i++) {
            this.resList.add(initAssetItem(this.mContext, "symbol_000" + i, "sticker/symbol/" + i + ".png", "sticker/symbol/" + i + ".png"));
        }
    }

    private void initSticker5() {
        for (int i = 1; i <= 40; i++) {
            this.resList.add(initAssetItem(this.mContext, "animal_000" + i, "sticker/animal/" + i + ".png", "sticker/animal/" + i + ".png"));
        }
    }

    private void initSticker6() {
        for (int i = 1; i <= 32; i++) {
            this.resList.add(initAssetItem(this.mContext, "face_000" + i, "sticker/face/" + i + ".png", "sticker/face/" + i + ".png"));
        }
    }

    protected DMStickerRes initAssetItem(Context context, String str, String str2, String str3) {
        DMStickerRes dMStickerRes = new DMStickerRes();
        dMStickerRes.setContext(context);
        dMStickerRes.setName(str);
        dMStickerRes.setIconFileName(str2);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        dMStickerRes.setIconConfig(options);
        dMStickerRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMStickerRes.setImageFileName(str3);
        dMStickerRes.setImageType(DMWBRes.LocationType.ASSERT);
        return dMStickerRes;
    }

    protected DMStickerRes initAssetItem(String str, String str2, String str3) {
        DMStickerRes dMStickerRes = new DMStickerRes();
        dMStickerRes.setContext(this.mContext);
        dMStickerRes.setName(str);
        dMStickerRes.setIconFileName(str2);
        dMStickerRes.setIconType(DMWBRes.LocationType.ASSERT);
        dMStickerRes.setImageFileName(str3);
        dMStickerRes.setImageType(DMWBRes.LocationType.ASSERT);
        return dMStickerRes;
    }
}
