package com.picspool.lib.filter.cpu.normal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/* loaded from: classes3.dex */
public class GrayscaleFilter {
    public static Bitmap filter(Bitmap bitmap, Boolean bool) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.0f);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        paint.setAlpha(245);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawBitmap(bitmap, 0.0f, 0.0f, paint);
        if (bool.booleanValue()) {
            bitmap.recycle();
        }
        return createBitmap;
    }
}
