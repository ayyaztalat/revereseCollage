package com.picspool.lib.filter.cpu.special;

import android.graphics.Bitmap;
import com.picspool.lib.filter.cpu.normal.MarbleFilter;

/* loaded from: classes3.dex */
public class Filter4 {
    public static Bitmap filter(Bitmap bitmap) {
        MarbleFilter marbleFilter = new MarbleFilter();
        marbleFilter.setXScale(15.0f);
        marbleFilter.setYScale(15.0f);
        marbleFilter.setTurbulence(5.0f);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(marbleFilter.filter(iArr, width, height), width, height, Bitmap.Config.ARGB_8888);
    }
}
