package com.picspool.lib.bitmap;

import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class DMBitmapCut {
    public static List<Bitmap> getNineGridImage(Bitmap bitmap) {
        return createImagePieces(bitmap, 3, 3, 0, 0);
    }

    public static List<Bitmap> getNineGridImage(Bitmap bitmap, int i) {
        return createImagePieces(bitmap, 3, 3, i, i);
    }

    public static List<Bitmap> createImagePieces(Bitmap bitmap, int i, int i2) {
        return createImagePieces(bitmap, i, i2, 0, 0);
    }

    public static List<Bitmap> createImagePieces(Bitmap bitmap, int i, int i2, int i3, int i4) {
        ArrayList arrayList = new ArrayList(i * i2);
        int width = (bitmap.getWidth() - ((i - 1) * i3)) / i;
        int height = (bitmap.getHeight() - ((i2 - 1) * i4)) / i2;
        for (int i5 = 0; i5 < i2; i5++) {
            for (int i6 = 0; i6 < i; i6++) {
                arrayList.add(Bitmap.createBitmap(bitmap, (width + i3) * i6, (height + i4) * i5, width, height));
            }
        }
        return arrayList;
    }
}
