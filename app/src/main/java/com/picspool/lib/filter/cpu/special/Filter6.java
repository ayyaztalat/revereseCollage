package com.picspool.lib.filter.cpu.special;

import android.graphics.Bitmap;
import androidx.core.view.ViewCompat;
import com.picspool.lib.filter.cpu.normal.PointillizeFilter;

/* loaded from: classes3.dex */
public class Filter6 {
    public static Bitmap filter(Bitmap bitmap) {
        PointillizeFilter pointillizeFilter = new PointillizeFilter();
        pointillizeFilter.setAmount(0.0f);
        pointillizeFilter.setEdgeColor(ViewCompat.MEASURED_STATE_MASK);
        pointillizeFilter.setScale(14.0f);
        pointillizeFilter.setFuzziness(0.1f);
        pointillizeFilter.setGridType(4);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        return Bitmap.createBitmap(pointillizeFilter.filter(iArr, width, height), width, height, Bitmap.Config.ARGB_8888);
    }
}
