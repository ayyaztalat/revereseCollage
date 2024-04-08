package com.picspool.lib.filter.cpu.special;

import android.graphics.Bitmap;
import com.picspool.lib.filter.cpu.normal.NoiseFilter;

/* loaded from: classes3.dex */
public class Filter5 {
    public static Bitmap filter(Bitmap bitmap) {
        NoiseFilter noiseFilter = new NoiseFilter();
        noiseFilter.setAmount(40);
        noiseFilter.setDensity(60.0f);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(noiseFilter.filter(iArr, width, height), width, height, Bitmap.Config.ARGB_8888);
    }
}
