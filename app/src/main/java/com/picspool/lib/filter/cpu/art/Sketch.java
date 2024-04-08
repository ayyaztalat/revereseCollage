package com.picspool.lib.filter.cpu.art;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.cpu.normal.GrayscaleFilter;
import com.picspool.lib.filter.cpu.normal.MaskFilter;

/* loaded from: classes3.dex */
public class Sketch {
    public static Bitmap filter(Resources resources, Bitmap bitmap, String str, String str2) {
        Bitmap filter;
        if (bitmap != null && !bitmap.isRecycled() && (filter = MaskFilter.filter(bitmap, DMBitmapUtil.getImageFromAssetsFile(resources, str), PorterDuff.Mode.MULTIPLY, false)) != null && !filter.isRecycled()) {
            Bitmap filter2 = GrayscaleFilter.filter(filter, true);
            Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(resources, str2);
            if (filter2 != null && !filter2.isRecycled()) {
                return MaskFilter.filter(filter2, imageFromAssetsFile, PorterDuff.Mode.SCREEN, true);
            }
        }
        return null;
    }

    public static Bitmap filter(Resources resources, Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        return filter(resources, bitmap, "art/paper.jpg", "art/pencil.jpg");
    }

    public static Bitmap testfilter(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        paint.setAlpha(90);
        new Rect(bitmap.getWidth() / 2, 0, (bitmap.getWidth() / 2) + 5, bitmap.getHeight());
        Bitmap createBitmap2 = Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2, 0, 5, bitmap.getHeight());
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(15.0f, BlurMaskFilter.Blur.NORMAL);
        paint.setShader(new BitmapShader(createBitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        paint.setMaskFilter(blurMaskFilter);
        canvas.drawRect(bitmap.getWidth() / 2, 0.0f, (bitmap.getWidth() / 2) + 25, bitmap.getHeight(), paint);
        return createBitmap;
    }
}
