package com.picspool.libfuncview.masicview.drawmosaic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import com.picspool.libfuncview.masicview.CSDrawMosaic;
import java.util.List;

/* loaded from: classes.dex */
public class CSMosaicViewDraw {
    public static Paint creatDrawPaint(CSDrawMosaic.Mode mode) {
        Paint paint = new Paint(1);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setPathEffect(new CornerPathEffect(20.0f));
        paint.setColor(0);
        if (mode == CSDrawMosaic.Mode.ERASE) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        }
        return paint;
    }

    public static void onEraseDraw(Paint paint, Path path, Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    public static void onStyleBlurDraw(Bitmap bitmap, Float f, Rect rect, Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f.floatValue());
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        canvas.drawBitmap(createBitmap, (Rect) null, rect, (Paint) null);
        createBitmap.recycle();
    }

    public static void onStyleBlurUpdateDraw(CSMyPath cSMyPath, Canvas canvas) {
        List<Bitmap> bitmapList = cSMyPath.getBitmapList();
        List<Float> angleList = cSMyPath.getAngleList();
        List<Rect> rectList = cSMyPath.getRectList();
        for (int i = 0; i < bitmapList.size(); i++) {
            Bitmap bitmap = bitmapList.get(i);
            if (bitmap != null && !bitmap.isRecycled()) {
                onStyleBlurDraw(bitmap, angleList.get(i), rectList.get(i), canvas);
            }
        }
    }

    public static void onNormalDraw(Paint paint, Bitmap bitmap, Path path, Canvas canvas, Bitmap bitmap2) {
        Canvas canvas2 = new Canvas(bitmap2);
        paint.setColor(-16711936);
        canvas2.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas2.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        canvas.drawBitmap(bitmap2, 0.0f, 0.0f, paint);
    }

    public static void onNormalUpdateDraw(Paint paint, CSMyPath cSMyPath, Canvas canvas, int i, int i2) {
        List<Bitmap> bitmapList = cSMyPath.getBitmapList();
        if (bitmapList == null || bitmapList.size() <= 0) {
            return;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        onNormalDraw(paint, bitmapList.get(bitmapList.size() - 1), cSMyPath.getPath(), canvas, createBitmap);
        createBitmap.recycle();
    }

    public static void onMultiBitmapUpdateDraw(CSMyPath cSMyPath, Canvas canvas) {
        List<Bitmap> bitmapList = cSMyPath.getBitmapList();
        List<Rect> rectList = cSMyPath.getRectList();
        if (rectList == null || rectList.size() <= 0 || bitmapList == null || bitmapList.size() <= 0) {
            return;
        }
        int i = 0;
        for (int i2 = 0; i2 < rectList.size(); i2++) {
            canvas.drawBitmap(bitmapList.get(i), (Rect) null, rectList.get(i2), (Paint) null);
            i = i < bitmapList.size() + (-1) ? i + 1 : 0;
        }
    }
}
