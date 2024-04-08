package com.picspool.lib.filter.cpu.special;

import android.graphics.Bitmap;
import com.picspool.lib.filter.cpu.normal.DiffuseFilter;

/* loaded from: classes3.dex */
public class Filter3 {
    public static Bitmap filter(Bitmap bitmap) {
        DiffuseFilter diffuseFilter = new DiffuseFilter();
        diffuseFilter.setScale(7.0f);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(diffuseFilter.filter(iArr, width, height), width, height, Bitmap.Config.ARGB_8888);
    }
}
