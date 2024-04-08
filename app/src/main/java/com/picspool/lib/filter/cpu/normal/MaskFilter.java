package com.picspool.lib.filter.cpu.normal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/* loaded from: classes3.dex */
public class MaskFilter {
    public static Bitmap filter(Bitmap bitmap, Bitmap bitmap2, PorterDuff.Mode mode, Boolean bool) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        if (bitmap2 == null || bitmap2.isRecycled()) {
            return bitmap;
        }
        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        if (copy == null) {
            return null;
        }
        Canvas canvas = new Canvas(copy);
        Matrix matrix = new Matrix();
        matrix.postScale(copy.getWidth() / bitmap2.getWidth(), copy.getHeight() / bitmap2.getHeight());
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(mode));
        canvas.drawBitmap(bitmap2, matrix, paint);
        if (bool.booleanValue()) {
            bitmap.recycle();
        }
        bitmap2.recycle();
        return copy;
    }
}
