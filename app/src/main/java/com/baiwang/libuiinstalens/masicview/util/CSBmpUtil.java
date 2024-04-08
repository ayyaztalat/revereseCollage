package com.baiwang.libuiinstalens.masicview.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.TypedValue;
import java.util.List;

/* loaded from: classes.dex */
public class CSBmpUtil {
    public static void recycleBitmaps(Bitmap... bitmapArr) {
        if (bitmapArr == null) {
            return;
        }
        for (Bitmap bitmap : bitmapArr) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    public static void recycleBitmaps(List<Bitmap> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        for (Bitmap bitmap : list) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
    }

    public static int dp2px(int i, Context context) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics()));
    }

    public static Bitmap flipBitmap(Bitmap bitmap, boolean z) {
        Matrix matrix = new Matrix();
        if (z) {
            matrix.postScale(1.0f, -1.0f);
        } else {
            matrix.postScale(-1.0f, 1.0f);
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
