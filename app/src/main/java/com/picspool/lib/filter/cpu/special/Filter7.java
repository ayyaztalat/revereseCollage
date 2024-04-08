package com.picspool.lib.filter.cpu.special;

import android.graphics.Bitmap;
import com.picspool.lib.filter.cpu.normal.QuantizeFilter;

/* loaded from: classes3.dex */
public class Filter7 {
    public static Bitmap filter(Bitmap bitmap) {
        QuantizeFilter quantizeFilter = new QuantizeFilter();
        quantizeFilter.setNumColors(3);
        quantizeFilter.setDither(true);
        quantizeFilter.setSerpentine(true);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(quantizeFilter.filter(iArr, width, height), width, height, Bitmap.Config.ARGB_8888);
    }
}
