package com.picspool.lib.filter.cpu.special;

import android.graphics.Bitmap;
import android.graphics.Color;

/* loaded from: classes3.dex */
public class MapBlendFilter {
    public static Bitmap filter(Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap2, width, height, false);
        int i = width * height;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        int[] iArr3 = new int[65536];
        int[] iArr4 = new int[i];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        createScaledBitmap.getPixels(iArr2, 0, width, 0, 0, width, height);
        bitmap3.getPixels(iArr3, 0, 256, 0, 0, 256, 256);
        for (int i2 = 0; i2 < height; i2++) {
            for (int i3 = 0; i3 < width; i3++) {
                int i4 = (i2 * width) + i3;
                iArr4[i4] = Color.rgb(Color.red(iArr3[(Color.red(iArr2[i4]) * 256) + Color.red(iArr[i4])]), Color.green(iArr3[(Color.green(iArr2[i4]) * 256) + Color.green(iArr[i4])]), Color.blue(iArr3[(Color.blue(iArr2[i4]) * 256) + Color.blue(iArr[i4])]));
            }
        }
        return Bitmap.createBitmap(iArr4, width, height, Bitmap.Config.ARGB_8888);
    }
}
