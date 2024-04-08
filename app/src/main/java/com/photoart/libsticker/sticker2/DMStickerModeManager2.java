package com.photoart.libsticker.sticker2;

import android.content.Context;
import com.photoart.libsticker.sticker.DMStickerManager;
import com.photoart.libsticker.sticker2.DMStickerModeManager;

/* loaded from: classes2.dex */
public class DMStickerModeManager2 {
    private static DMStickerManager stickerImageManager1;
    private static DMStickerManager stickerImageManager2;
    private static DMStickerManager stickerImageManager3;
    private static DMStickerManager stickerImageManager4;
    private static DMStickerManager stickerImageManager5;
    private static DMStickerManager stickerImageManagerAll;

    public static DMStickerManager getStickerImageManager(Context context, DMStickerModeManager.StickerMode stickerMode) {
        Context applicationContext = context.getApplicationContext();
        if (stickerMode == DMStickerModeManager.StickerMode.STICKERALL) {
            DMStickerManager dMStickerManager = stickerImageManagerAll;
            if (dMStickerManager == null || dMStickerManager.getCount() <= 0) {
                stickerImageManagerAll = new DMStickerManager(applicationContext, stickerMode);
            }
            return stickerImageManagerAll;
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER1) {
            DMStickerManager dMStickerManager2 = stickerImageManager1;
            if (dMStickerManager2 == null || dMStickerManager2.getCount() <= 0) {
                stickerImageManager1 = new DMStickerManager(applicationContext, stickerMode);
            }
            return stickerImageManager1;
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER2) {
            DMStickerManager dMStickerManager3 = stickerImageManager2;
            if (dMStickerManager3 == null || dMStickerManager3.getCount() <= 0) {
                stickerImageManager2 = new DMStickerManager(applicationContext, stickerMode);
            }
            return stickerImageManager2;
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER3) {
            DMStickerManager dMStickerManager4 = stickerImageManager3;
            if (dMStickerManager4 == null || dMStickerManager4.getCount() <= 0) {
                stickerImageManager3 = new DMStickerManager(applicationContext, stickerMode);
            }
            return stickerImageManager3;
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER4) {
            DMStickerManager dMStickerManager5 = stickerImageManager4;
            if (dMStickerManager5 == null || dMStickerManager5.getCount() <= 0) {
                stickerImageManager4 = new DMStickerManager(applicationContext, stickerMode);
            }
            return stickerImageManager4;
        } else if (stickerMode == DMStickerModeManager.StickerMode.STICKER5) {
            DMStickerManager dMStickerManager6 = stickerImageManager5;
            if (dMStickerManager6 == null || dMStickerManager6.getCount() <= 0) {
                stickerImageManager5 = new DMStickerManager(applicationContext, stickerMode);
            }
            return stickerImageManager5;
        } else {
            return null;
        }
    }
}
