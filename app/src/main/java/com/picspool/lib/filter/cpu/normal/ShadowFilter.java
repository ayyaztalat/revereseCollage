package com.picspool.lib.filter.cpu.normal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/* loaded from: classes3.dex */
public class ShadowFilter {
    public static Bitmap filter(Bitmap bitmap, int i, int i2) {
        if (bitmap == null || bitmap.isRecycled() || i2 <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i3 = i2 * 2;
        Bitmap createBitmap = Bitmap.createBitmap(width + i3, i3 + height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setColor(i);
        paint.setShadowLayer(i2, 0.0f, 0.0f, i);
        Rect rect = new Rect(i2, i2, width, height);
        canvas.drawRect(new Rect(rect), paint);
        paint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        canvas.drawBitmap(bitmap, (Rect) null, rect, paint);
        return createBitmap;
    }
}
