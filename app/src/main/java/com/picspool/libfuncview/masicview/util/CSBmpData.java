package com.picspool.libfuncview.masicview.util;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public class CSBmpData {
    public static Bitmap sAcneRegionBmp;
    public static Bitmap sFaceRegionBmp;
    public static Bitmap sHairRegionBmp;
    public static Bitmap sSrcBmp;

    public static void recycleSrcBmp() {
        Bitmap bitmap = sSrcBmp;
        if (bitmap != null) {
            bitmap.recycle();
            sSrcBmp = null;
        }
    }

    public static void recycleFaceRegionBmp() {
        Bitmap bitmap = sFaceRegionBmp;
        if (bitmap != null) {
            bitmap.recycle();
            sFaceRegionBmp = null;
        }
    }

    public static void recycleHairRegionBmp() {
        Bitmap bitmap = sHairRegionBmp;
        if (bitmap != null) {
            bitmap.recycle();
            sHairRegionBmp = null;
        }
    }

    public static void recycleAcneRegionBmp() {
        Bitmap bitmap = sAcneRegionBmp;
        if (bitmap != null) {
            bitmap.recycle();
            sAcneRegionBmp = null;
        }
    }

    public static void recycleAllCacheBmp() {
        recycleSrcBmp();
        recycleFaceRegionBmp();
        recycleHairRegionBmp();
        recycleAcneRegionBmp();
    }
}
