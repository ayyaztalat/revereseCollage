package com.picspool.lib.filter.cpu.normal;

import android.content.res.Resources;
import android.graphics.Bitmap;
import com.picspool.lib.filter.cpu.util.ACVFileReader;
import com.picspool.lib.filter.cpu.util.DATFileReader;

/* loaded from: classes3.dex */
public class CommonCurve {
    public static Bitmap filter(Resources resources, Bitmap bitmap, int i, String str, String[] strArr, Boolean bool) {
        CurvesFilter curvesFilter = new CurvesFilter();
        curvesFilter.setCurves(ACVFileReader.getFromInputStream(resources, str));
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i2 = 0; i2 < i; i2++) {
            iArr = curvesFilter.filter(iArr, width, height);
        }
        Bitmap createBitmap = Bitmap.createBitmap(iArr, width, height, Bitmap.Config.ARGB_8888);
        return bool.booleanValue() ? GrayscaleFilter.filter(createBitmap, true) : createBitmap;
    }

    public static Bitmap filter2(Resources resources, Bitmap bitmap, int i, String str, String[] strArr, Boolean bool) {
        DATFileReader dATFileReader = new DATFileReader();
        dATFileReader.loadFile(resources, str);
        CurvesFilter curvesFilter = new CurvesFilter(dATFileReader.f1963r, dATFileReader.f1962g, dATFileReader.f1961b);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i2 = 0; i2 < i; i2++) {
            iArr = curvesFilter.filter(iArr, width, height);
        }
        Bitmap createBitmap = Bitmap.createBitmap(iArr, width, height, Bitmap.Config.ARGB_8888);
        return bool.booleanValue() ? GrayscaleFilter.filter(createBitmap, true) : createBitmap;
    }
}
