package com.picspool.lib.border.process;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.picspool.lib.border.res.DMWBBorderRes;
import com.picspool.lib.border.res.DMWBBorderReturns;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMWBBorderProcess {
    static int mapSize = 2048;

    protected static float getBorderRate(int i, int i2, DMWBBorderRes dMWBBorderRes) {
        float innerPx = ((mapSize - dMWBBorderRes.getInnerPx()) - dMWBBorderRes.getInnerPx2()) / i;
        float innerPy = ((mapSize - dMWBBorderRes.getInnerPy()) - dMWBBorderRes.getInnerPy2()) / i2;
        return innerPx < innerPy ? innerPx : innerPy;
    }

    protected static Bitmap processBorder(Context context, int i, int i2, DMWBBorderRes dMWBBorderRes, int i3, boolean z) {
        int innerPx;
        int i4;
        Bitmap bitmap;
        int calcWithRate = 0;
        int calcWithRate2 = 0;
        int calcWithRate3 = 0;
        int calcWithRate4 = 0;
        Bitmap rightTopCornorBitmap = null;
        int calcWithRate5 = 0;
        int calcWithRate6 = 0;
        int i5 = 0;
        int i6 = mapSize;
        int i7 = i6 / 2;
        if (z) {
            i7 = (i6 / 2) / i3;
        }
        int i8 = i3 * 2;
        float borderRate = getBorderRate(i, i2, dMWBBorderRes);
        if (i > i2) {
            i4 = (int) ((((borderRate * i2) + dMWBBorderRes.getInnerPy()) + dMWBBorderRes.getInnerPy2()) / i8);
            innerPx = i7;
        } else {
            innerPx = (int) ((((borderRate * i) + dMWBBorderRes.getInnerPx()) + dMWBBorderRes.getInnerPx2()) / i8);
            i4 = i7;
        }
        float f = i7;
        float f2 = f / mapSize;
        if (dMWBBorderRes.getMapSize() > 0) {
            f2 = f / dMWBBorderRes.getMapSize();
        }
        Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), R.drawable.dm_res_tranparent);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeResource, innerPx, i4, false);
        decodeResource.recycle();
        Canvas canvas = new Canvas(createScaledBitmap);
        Paint paint = new Paint();
        try {
            Bitmap leftTopCornorBitmap = dMWBBorderRes.getLeftTopCornorBitmap();
            calcWithRate = calcWithRate(leftTopCornorBitmap.getWidth(), f2);
            calcWithRate2 = calcWithRate(leftTopCornorBitmap.getHeight(), f2);
            canvas.drawBitmap(leftTopCornorBitmap, (Rect) null, new Rect(0, 0, calcWithRate, calcWithRate2), paint);
            leftTopCornorBitmap.recycle();
            Bitmap leftBottomCornorBitmap = dMWBBorderRes.getLeftBottomCornorBitmap();
            calcWithRate3 = calcWithRate(leftBottomCornorBitmap.getWidth(), f2);
            calcWithRate4 = calcWithRate(leftBottomCornorBitmap.getHeight(), f2);
            int i9 = i4 - calcWithRate4;
            canvas.drawBitmap(leftBottomCornorBitmap, (Rect) null, new Rect(0, i9, calcWithRate3, i9 + calcWithRate4), (Paint) null);
            leftBottomCornorBitmap.recycle();
            rightTopCornorBitmap = dMWBBorderRes.getRightTopCornorBitmap();
            calcWithRate5 = calcWithRate(rightTopCornorBitmap.getWidth(), f2);
            calcWithRate6 = calcWithRate(rightTopCornorBitmap.getHeight(), f2);
            i5 = innerPx - calcWithRate5;
            bitmap = createScaledBitmap;
        } catch (OutOfMemoryError e) {
           e.printStackTrace();
            bitmap = createScaledBitmap;
        }
        try {
            canvas.drawBitmap(rightTopCornorBitmap, (Rect) null, new Rect(i5, 0, i5 + calcWithRate5, calcWithRate6), (Paint) null);
            rightTopCornorBitmap.recycle();
            Bitmap rightBottomCornorBitmap = dMWBBorderRes.getRightBottomCornorBitmap();
            int calcWithRate7 = calcWithRate(rightBottomCornorBitmap.getWidth(), f2);
            int calcWithRate8 = calcWithRate(rightBottomCornorBitmap.getHeight(), f2);
            int i10 = innerPx - calcWithRate7;
            int i11 = i4 - calcWithRate8;
            canvas.drawBitmap(rightBottomCornorBitmap, (Rect) null, new Rect(i10, i11, i10 + calcWithRate7, i11 + calcWithRate8), (Paint) null);
            rightBottomCornorBitmap.recycle();
            Bitmap leftBitmap = dMWBBorderRes.getLeftBitmap();
            int calcWithRate9 = calcWithRate(leftBitmap.getWidth(), f2);
            int calcWithRate10 = calcWithRate(leftBitmap.getHeight(), f2);
            int i12 = (i4 - calcWithRate2) - calcWithRate4;
            if (i12 > 0) {
                Bitmap TileBitmapVertical = TileBitmapVertical(leftBitmap, i12, calcWithRate9, calcWithRate10);
                if (TileBitmapVertical != leftBitmap) {
                    leftBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapVertical, (Rect) null, new Rect(0, calcWithRate2, calcWithRate9 + 0, i12 + calcWithRate2), (Paint) null);
                TileBitmapVertical.recycle();
            }
            Bitmap topBitmap = dMWBBorderRes.getTopBitmap();
            int calcWithRate11 = calcWithRate(topBitmap.getHeight(), f2);
            int calcWithRate12 = calcWithRate(topBitmap.getWidth(), f2);
            int i13 = (innerPx - calcWithRate) - calcWithRate5;
            if (i13 > 0) {
                Bitmap TileBitmapHorizon = TileBitmapHorizon(topBitmap, i13, calcWithRate12, calcWithRate11);
                if (TileBitmapHorizon != topBitmap) {
                    topBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapHorizon, (Rect) null, new Rect(calcWithRate, 0, i13 + calcWithRate, calcWithRate11 + 0), (Paint) null);
                TileBitmapHorizon.recycle();
            }
            Bitmap rightBitmap = dMWBBorderRes.getRightBitmap();
            int calcWithRate13 = calcWithRate(rightBitmap.getWidth(), f2);
            int calcWithRate14 = calcWithRate(rightBitmap.getHeight(), f2);
            int i14 = (i4 - calcWithRate6) - calcWithRate8;
            int i15 = innerPx - calcWithRate13;
            if (i14 > 0) {
                Bitmap TileBitmapVertical2 = TileBitmapVertical(rightBitmap, i14, calcWithRate13, calcWithRate14);
                if (TileBitmapVertical2 != rightBitmap) {
                    rightBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapVertical2, (Rect) null, new Rect(i15, calcWithRate6, calcWithRate13 + i15, i14 + calcWithRate6), (Paint) null);
                TileBitmapVertical2.recycle();
            }
            Bitmap bottomBitmap = dMWBBorderRes.getBottomBitmap();
            int calcWithRate15 = calcWithRate(bottomBitmap.getWidth(), f2);
            int calcWithRate16 = calcWithRate(bottomBitmap.getHeight(), f2);
            int i16 = (innerPx - calcWithRate3) - calcWithRate7;
            int i17 = i4 - calcWithRate16;
            if (i16 > 0) {
                Bitmap TileBitmapHorizon2 = TileBitmapHorizon(bottomBitmap, i16, calcWithRate15, calcWithRate16);
                if (TileBitmapHorizon2 != bottomBitmap) {
                    bottomBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapHorizon2, (Rect) null, new Rect(calcWithRate3, i17, calcWithRate3 + i16, calcWithRate16 + i17), (Paint) null);
                TileBitmapHorizon2.recycle();
            }
            return bitmap;
        } catch (OutOfMemoryError e2) {
          e2.printStackTrace();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        return bitmap;
    }

    protected static void processBorder(Context context, int i, int i2, DMWBBorderRes dMWBBorderRes, int i3, Canvas canvas) {
        float borderRate = 1.0f / getBorderRate(i, i2, dMWBBorderRes);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        try {
            Bitmap leftTopCornorBitmap = dMWBBorderRes.getLeftTopCornorBitmap();
            int calcWithRate = calcWithRate(leftTopCornorBitmap.getWidth(), borderRate);
            int calcWithRate2 = calcWithRate(leftTopCornorBitmap.getHeight(), borderRate);
            canvas.drawBitmap(leftTopCornorBitmap, (Rect) null, new Rect(0, 0, calcWithRate, calcWithRate2), paint);
            leftTopCornorBitmap.recycle();
            Bitmap leftBottomCornorBitmap = dMWBBorderRes.getLeftBottomCornorBitmap();
            int calcWithRate3 = calcWithRate(leftBottomCornorBitmap.getWidth(), borderRate);
            int calcWithRate4 = calcWithRate(leftBottomCornorBitmap.getHeight(), borderRate);
            int i4 = i2 - calcWithRate4;
            canvas.drawBitmap(leftBottomCornorBitmap, (Rect) null, new Rect(0, i4, calcWithRate3 + 0, i4 + calcWithRate4), (Paint) null);
            leftBottomCornorBitmap.recycle();
            Bitmap rightTopCornorBitmap = dMWBBorderRes.getRightTopCornorBitmap();
            int calcWithRate5 = calcWithRate(rightTopCornorBitmap.getWidth(), borderRate);
            int calcWithRate6 = calcWithRate(rightTopCornorBitmap.getHeight(), borderRate);
            int i5 = i - calcWithRate5;
            canvas.drawBitmap(rightTopCornorBitmap, (Rect) null, new Rect(i5, 0, i5 + calcWithRate5, calcWithRate6 + 0), (Paint) null);
            rightTopCornorBitmap.recycle();
            Bitmap rightBottomCornorBitmap = dMWBBorderRes.getRightBottomCornorBitmap();
            int calcWithRate7 = calcWithRate(rightBottomCornorBitmap.getWidth(), borderRate);
            int calcWithRate8 = calcWithRate(rightBottomCornorBitmap.getHeight(), borderRate);
            int i6 = i - calcWithRate7;
            int i7 = i2 - calcWithRate8;
            canvas.drawBitmap(rightBottomCornorBitmap, (Rect) null, new Rect(i6, i7, i6 + calcWithRate7, i7 + calcWithRate8), (Paint) null);
            rightBottomCornorBitmap.recycle();
            Bitmap leftBitmap = dMWBBorderRes.getLeftBitmap();
            int calcWithRate9 = calcWithRate(leftBitmap.getWidth(), borderRate);
            int calcWithRate10 = calcWithRate(leftBitmap.getHeight(), borderRate);
            int i8 = (i2 - calcWithRate2) - calcWithRate4;
            if (i8 > 0) {
                Bitmap TileBitmapVertical = TileBitmapVertical(leftBitmap, i8, calcWithRate9, calcWithRate10);
                if (TileBitmapVertical != leftBitmap) {
                    leftBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapVertical, (Rect) null, new Rect(0, calcWithRate2, calcWithRate9 + 0, i8 + calcWithRate2), (Paint) null);
                TileBitmapVertical.recycle();
            }
            Bitmap topBitmap = dMWBBorderRes.getTopBitmap();
            int calcWithRate11 = calcWithRate(topBitmap.getHeight(), borderRate);
            int calcWithRate12 = calcWithRate(topBitmap.getWidth(), borderRate);
            int i9 = (i - calcWithRate) - calcWithRate5;
            if (i9 > 0) {
                Bitmap TileBitmapHorizon = TileBitmapHorizon(topBitmap, i9, calcWithRate12, calcWithRate11);
                if (TileBitmapHorizon != topBitmap) {
                    topBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapHorizon, (Rect) null, new Rect(calcWithRate, 0, i9 + calcWithRate, calcWithRate11 + 0), (Paint) null);
                TileBitmapHorizon.recycle();
            }
            Bitmap rightBitmap = dMWBBorderRes.getRightBitmap();
            int calcWithRate13 = calcWithRate(rightBitmap.getWidth(), borderRate);
            int calcWithRate14 = calcWithRate(rightBitmap.getHeight(), borderRate);
            int i10 = (i2 - calcWithRate6) - calcWithRate8;
            int i11 = i - calcWithRate13;
            if (i10 > 0) {
                Bitmap TileBitmapVertical2 = TileBitmapVertical(rightBitmap, i10, calcWithRate13, calcWithRate14);
                if (TileBitmapVertical2 != rightBitmap) {
                    rightBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapVertical2, (Rect) null, new Rect(i11, calcWithRate6, calcWithRate13 + i11, i10 + calcWithRate6), (Paint) null);
                TileBitmapVertical2.recycle();
            }
            Bitmap bottomBitmap = dMWBBorderRes.getBottomBitmap();
            int calcWithRate15 = calcWithRate(bottomBitmap.getWidth(), borderRate);
            int calcWithRate16 = calcWithRate(bottomBitmap.getHeight(), borderRate);
            int i12 = (i - calcWithRate3) - calcWithRate7;
            int i13 = i2 - calcWithRate16;
            if (i12 > 0) {
                Bitmap TileBitmapHorizon2 = TileBitmapHorizon(bottomBitmap, i12, calcWithRate15, calcWithRate16);
                if (TileBitmapHorizon2 != bottomBitmap) {
                    bottomBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapHorizon2, (Rect) null, new Rect(calcWithRate3, i13, i12 + calcWithRate3, calcWithRate16 + i13), (Paint) null);
                TileBitmapHorizon2.recycle();
            }
        } catch (OutOfMemoryError unused) {
        }
    }

    public static DMWBBorderReturns processNinePathBorderOuter(Context context, int i, int i2, DMWBBorderRes dMWBBorderRes, boolean z) {
        Bitmap bitmap;
        try {
            bitmap = processBorder(context, i, i2, dMWBBorderRes, 1, z);
        } catch (OutOfMemoryError unused) {
            try {
                bitmap = processBorder(context, i, i2, dMWBBorderRes, 2, z);
            } catch (OutOfMemoryError unused2) {
                try {
                    bitmap = processBorder(context, i, i2, dMWBBorderRes, 4, z);
                } catch (OutOfMemoryError unused3) {
                    bitmap = null;
                }
            }
        }
        Bitmap bitmap2 = bitmap;
        float borderRate = getBorderRate(i, i2, dMWBBorderRes);
        return new DMWBBorderReturns(bitmap2, (int) (dMWBBorderRes.getInnerPx() / borderRate), (int) (dMWBBorderRes.getInnerPy() / borderRate), (int) (dMWBBorderRes.getInnerPx2() / borderRate), (int) (dMWBBorderRes.getInnerPy2() / borderRate));
    }

    public static Bitmap processNinePathBorderOuter(Context context, int i, int i2, DMWBBorderRes dMWBBorderRes, Canvas canvas) {
        try {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            processBorder(context, i, i2, dMWBBorderRes, 1, new Canvas(createBitmap));
            return createBitmap;
        } catch (OutOfMemoryError unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x0090 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static Bitmap processNinePathBitmap(Context r17, Bitmap r18, com.picspool.lib.border.res.DMWBBorderRes r19, boolean r20) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.border.process.DMWBBorderProcess.processNinePathBitmap(android.content.Context, android.graphics.Bitmap, com.picspool.lib.border.res.DMWBBorderRes, boolean):android.graphics.Bitmap");
    }

    public static Bitmap fromBmp(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect();
        rect.left = 0;
        rect.right = width;
        rect.top = 0;
        rect.bottom = height;
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    private static Bitmap TileBitmapVertical(Bitmap bitmap, int i, int i2, int i3) {
        int i4 = i / i3;
        if (i % i3 != 0) {
            int i5 = i4 + 1;
            if ((i3 * i5) - i < i - (i3 * i4)) {
                i4 = i5;
            }
        }
        int i6 = i4 * i3;
        if (i6 > 0) {
            i = i6;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i2, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Rect rect = new Rect(0, 0, i2, i3);
        Rect rect2 = new Rect(0, 0, i2, i);
        if (i4 == 0) {
            canvas.drawBitmap(bitmap, (Rect) null, rect2, (Paint) null);
        } else {
            for (int i7 = 0; i7 < i4; i7++) {
                canvas.drawBitmap(bitmap, (Rect) null, rect, (Paint) null);
                rect.top += i3;
                rect.bottom += i3;
            }
        }
        return createBitmap;
    }

    private static Bitmap TileBitmapHorizon(Bitmap bitmap, int i, int i2, int i3) {
        int i4 = i / i2;
        if (i % i2 != 0) {
            int i5 = i4 + 1;
            if ((i2 * i5) - i < i - (i2 * i4)) {
                i4 = i5;
            }
        }
        int i6 = i4 * i2;
        if (i6 > 0) {
            i = i6;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Rect rect = new Rect(0, 0, i2, i3);
        Rect rect2 = new Rect(0, 0, i, i3);
        if (i4 == 0) {
            canvas.drawBitmap(bitmap, (Rect) null, rect2, (Paint) null);
        } else {
            for (int i7 = 0; i7 < i4; i7++) {
                canvas.drawBitmap(bitmap, (Rect) null, rect, (Paint) null);
                rect.left += i2;
                rect.right += i2;
            }
        }
        return createBitmap;
    }

    private static int calcWithRate(int i, float f) {
        float f2 = i * f;
        int i2 = (int) f2;
        return Math.abs(f2 - ((float) i2)) >= 0.5f ? i2 + 1 : i2;
    }
}
