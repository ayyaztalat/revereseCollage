package com.picspool.lib.filter.cpu.special;

import android.graphics.Bitmap;
import com.picspool.lib.filter.cpu.normal.CrystallizeFilter;

/* loaded from: classes3.dex */
public class Filter8 {
    public static Bitmap filter(Bitmap bitmap) {
        CrystallizeFilter crystallizeFilter = new CrystallizeFilter();
        crystallizeFilter.setEdgeColor(-7829368);
        crystallizeFilter.setEdgeThickness(0.3f);
        crystallizeFilter.setRandomness(0.3f);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(crystallizeFilter.filter(iArr, width, height), width, height, Bitmap.Config.ARGB_8888);
    }
}
