package com.picspool.lib.filter.cpu.normal;

import android.graphics.Bitmap;

/* loaded from: classes3.dex */
public class WaterFilter {
    /* renamed from: a */
    public static Bitmap m7a(Bitmap bitmap, double d) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = width * height;
        int[] iArr = new int[i];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        int i2 = height * 2;
        Bitmap createBitmap = Bitmap.createBitmap(width, i2, Bitmap.Config.ARGB_8888);
        int[] iArr2 = new int[width * i2];
        createBitmap.getPixels(iArr2, 0, width, 0, 0, width, i2);
        for (int i3 = 0; i3 < i; i3++) {
            iArr2[i3] = iArr[i3];
        }
        double d2 = 2.0d * d;
        int i4 = height - 1;
        int min = Math.min(Math.max(i4 - (((int) (((0 / d2) + 1.0d) * Math.sin((3.141592653589793d / d) + (((height / d2) * (height + 0)) / 1)))) + 0), 0), i4);
        for (int i5 = 0; i5 < width; i5++) {
            iArr2[((0 + height) * width) + i5] = iArr[(min * width) + i5];
        }
        createBitmap.setPixels(iArr2, 0, width, 0, 0, width, i2);
        return createBitmap;
    }
}
