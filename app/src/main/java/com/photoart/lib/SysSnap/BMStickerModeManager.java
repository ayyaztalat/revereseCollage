package com.photoart.lib.SysSnap;

import android.content.Context;

/* loaded from: classes3.dex */
public class BMStickerModeManager {
    private static BMStickerImageManager stickerImageManager1;
    private static BMStickerImageManager stickerImageManager2;
    private static BMStickerImageManager stickerImageManager3;
    private static BMStickerImageManager stickerImageManager4;
    private static BMStickerImageManager stickerImageManager5;
    private static BMStickerImageManager stickerImageManager6;
    private static BMStickerImageManager stickerImageManagerAll;

    /* loaded from: classes3.dex */
    public enum StickerMode {
        STICKERALL,
        STICKER1,
        STICKER2,
        STICKER3,
        STICKER4,
        STICKER5,
        STICKER6
    }

    public static BMStickerImageManager getStickerImageManager(Context context, StickerMode stickerMode) {
        if (stickerMode == StickerMode.STICKERALL) {
            BMStickerImageManager bMStickerImageManager = stickerImageManagerAll;
            if (bMStickerImageManager == null || bMStickerImageManager.getCount() <= 0) {
                stickerImageManagerAll = new BMStickerImageManager(context, stickerMode);
            }
            return stickerImageManagerAll;
        } else if (stickerMode == StickerMode.STICKER1) {
            BMStickerImageManager bMStickerImageManager2 = stickerImageManager1;
            if (bMStickerImageManager2 == null || bMStickerImageManager2.getCount() <= 0) {
                stickerImageManager1 = new BMStickerImageManager(context, stickerMode);
            }
            return stickerImageManager1;
        } else if (stickerMode == StickerMode.STICKER2) {
            BMStickerImageManager bMStickerImageManager3 = stickerImageManager2;
            if (bMStickerImageManager3 == null || bMStickerImageManager3.getCount() <= 0) {
                stickerImageManager2 = new BMStickerImageManager(context, stickerMode);
            }
            return stickerImageManager2;
        } else if (stickerMode == StickerMode.STICKER3) {
            BMStickerImageManager bMStickerImageManager4 = stickerImageManager3;
            if (bMStickerImageManager4 == null || bMStickerImageManager4.getCount() <= 0) {
                stickerImageManager3 = new BMStickerImageManager(context, stickerMode);
            }
            return stickerImageManager3;
        } else if (stickerMode == StickerMode.STICKER4) {
            BMStickerImageManager bMStickerImageManager5 = stickerImageManager4;
            if (bMStickerImageManager5 == null || bMStickerImageManager5.getCount() <= 0) {
                stickerImageManager4 = new BMStickerImageManager(context, stickerMode);
            }
            return stickerImageManager4;
        } else if (stickerMode == StickerMode.STICKER5) {
            BMStickerImageManager bMStickerImageManager6 = stickerImageManager5;
            if (bMStickerImageManager6 == null || bMStickerImageManager6.getCount() <= 0) {
                stickerImageManager5 = new BMStickerImageManager(context, stickerMode);
            }
            return stickerImageManager5;
        } else if (stickerMode == StickerMode.STICKER6) {
            BMStickerImageManager bMStickerImageManager7 = stickerImageManager6;
            if (bMStickerImageManager7 == null || bMStickerImageManager7.getCount() <= 0) {
                stickerImageManager6 = new BMStickerImageManager(context, stickerMode);
            }
            return stickerImageManager6;
        } else {
            return null;
        }
    }
}
