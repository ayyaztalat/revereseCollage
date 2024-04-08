package com.picspool.lib.filter.cpu.normal;

import android.graphics.Bitmap;

import com.sky.testproject.GPUImageNativeLibrary;
//import jp.p011co.cyberagent.android.gpuimage.GPUImageNativeLibrary;

/* loaded from: classes3.dex */
public class FastBlurFilter {
    public static Bitmap blur(Bitmap bitmap, int i, boolean z) {
        if (bitmap == null) {
            return null;
        }
        if (!z) {
            bitmap = bitmap.copy(bitmap.getConfig(), true);
        }
        if (i < 1) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] iArr = new int[width * height];
        Bitmap bitmap2 = bitmap;
        bitmap2.getPixels(iArr, 0, width, 0, 0, width, height);
        GPUImageNativeLibrary.blur(iArr, width, height, i);
        bitmap2.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
