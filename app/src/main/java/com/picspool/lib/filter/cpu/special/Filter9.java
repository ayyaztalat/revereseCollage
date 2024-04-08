package com.picspool.lib.filter.cpu.special;

import android.graphics.Bitmap;
import com.picspool.lib.filter.cpu.normal.ColorHalftoneFilter;

/* loaded from: classes3.dex */
public class Filter9 {
    public static Bitmap filter(Bitmap bitmap) {
        ColorHalftoneFilter colorHalftoneFilter = new ColorHalftoneFilter();
        colorHalftoneFilter.setdotRadius(3.0f);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(colorHalftoneFilter.filter(iArr, width, height), width, height, Bitmap.Config.ARGB_8888);
    }
}
