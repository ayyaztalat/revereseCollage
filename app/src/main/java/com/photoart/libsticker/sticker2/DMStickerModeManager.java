package com.photoart.libsticker.sticker2;

import android.content.Context;

/* loaded from: classes2.dex */
public class DMStickerModeManager {
    private static DMStickerImageManager BMStickerImageManager1;
    private static DMStickerImageManager BMStickerImageManager2;
    private static DMStickerImageManager BMStickerImageManager3;
    private static DMStickerImageManager BMStickerImageManager4;
    private static DMStickerImageManager BMStickerImageManager5;
    private static DMStickerImageManager BMStickerImageManager6;
    private static DMStickerImageManager BMStickerImageManager7;
    private static DMStickerImageManager BMStickerImageManager8;
    private static DMStickerImageManager BMStickerImageManagerAll;
    private static DMStickerImageManager BMStickerImageManagerOnline;

    /* loaded from: classes2.dex */
    public enum StickerMode {
        STICKERALL,
        STICKER1,
        STICKER2,
        STICKER3,
        STICKER4,
        STICKER5,
        STICKER6,
        STICKER7,
        STICKER8,
        ONLINE
    }

    public static StickerMode getStickerModeByName(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == 96632902) {
            if (str.equals("emoji")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 99151942) {
            if (hashCode == 2096669554 && str.equals("Face U")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("heart")) {
                c = 1;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c != 1) {
                if (c == 2) {
                    return StickerMode.STICKER7;
                }
                return StickerMode.ONLINE;
            }
            return StickerMode.STICKER2;
        }
        return StickerMode.STICKER1;
    }

    public static DMStickerImageManager getStickerImageManager(Context context, StickerMode stickerMode, DMStickerImageRes dMStickerImageRes) {
        Context applicationContext = context.getApplicationContext();
        if (stickerMode == StickerMode.STICKERALL) {
            DMStickerImageManager dMStickerImageManager = BMStickerImageManagerAll;
            if (dMStickerImageManager == null || dMStickerImageManager.getCount() <= 0) {
                BMStickerImageManagerAll = new DMStickerImageManager(applicationContext, stickerMode);
            }
            return BMStickerImageManagerAll;
        } else if (stickerMode == StickerMode.STICKER1) {
            DMStickerImageManager dMStickerImageManager2 = BMStickerImageManager1;
            if (dMStickerImageManager2 == null || dMStickerImageManager2.getCount() <= 0) {
                BMStickerImageManager1 = new DMStickerImageManager(applicationContext, stickerMode);
            }
            return BMStickerImageManager1;
        } else if (stickerMode == StickerMode.STICKER2) {
            DMStickerImageManager dMStickerImageManager3 = BMStickerImageManager2;
            if (dMStickerImageManager3 == null || dMStickerImageManager3.getCount() <= 0) {
                BMStickerImageManager2 = new DMStickerImageManager(applicationContext, stickerMode);
            }
            return BMStickerImageManager2;
        } else if (stickerMode == StickerMode.STICKER7) {
            DMStickerImageManager dMStickerImageManager4 = BMStickerImageManager7;
            if (dMStickerImageManager4 == null || dMStickerImageManager4.getCount() <= 0) {
                BMStickerImageManager7 = new DMStickerImageManager(applicationContext, stickerMode);
            }
            return BMStickerImageManager7;
        } else if (stickerMode == StickerMode.ONLINE) {
            DMStickerImageManager dMStickerImageManager5 = new DMStickerImageManager(applicationContext, stickerMode);
            BMStickerImageManagerOnline = dMStickerImageManager5;
            dMStickerImageManager5.initOnLineStickerImageList(dMStickerImageRes);
            return BMStickerImageManagerOnline;
        } else {
            return null;
        }
    }

    public static DMStickerImageManager getStickerImageManager(Context context, StickerMode stickerMode) {
        Context applicationContext = context.getApplicationContext();
        if (stickerMode == StickerMode.STICKERALL) {
            DMStickerImageManager dMStickerImageManager = BMStickerImageManagerAll;
            if (dMStickerImageManager == null || dMStickerImageManager.getCount() <= 0) {
                BMStickerImageManagerAll = new DMStickerImageManager(applicationContext, stickerMode);
            }
            return BMStickerImageManagerAll;
        } else if (stickerMode == StickerMode.STICKER1) {
            DMStickerImageManager dMStickerImageManager2 = BMStickerImageManager1;
            if (dMStickerImageManager2 == null || dMStickerImageManager2.getCount() <= 0) {
                BMStickerImageManager1 = new DMStickerImageManager(applicationContext, stickerMode);
            }
            return BMStickerImageManager1;
        } else if (stickerMode == StickerMode.STICKER2) {
            DMStickerImageManager dMStickerImageManager3 = BMStickerImageManager2;
            if (dMStickerImageManager3 == null || dMStickerImageManager3.getCount() <= 0) {
                BMStickerImageManager2 = new DMStickerImageManager(applicationContext, stickerMode);
            }
            return BMStickerImageManager2;
        } else if (stickerMode == StickerMode.STICKER7) {
            DMStickerImageManager dMStickerImageManager4 = BMStickerImageManager7;
            if (dMStickerImageManager4 == null || dMStickerImageManager4.getCount() <= 0) {
                BMStickerImageManager7 = new DMStickerImageManager(applicationContext, stickerMode);
            }
            return BMStickerImageManager7;
        } else if (stickerMode == StickerMode.ONLINE) {
            DMStickerImageManager dMStickerImageManager5 = BMStickerImageManager8;
            if (dMStickerImageManager5 == null || dMStickerImageManager5.getCount() <= 0) {
                BMStickerImageManager8 = new DMStickerImageManager(applicationContext, stickerMode);
            }
            return BMStickerImageManager8;
        } else {
            return null;
        }
    }
}
