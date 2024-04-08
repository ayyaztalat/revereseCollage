package com.photo.editor.square.pic.splash.libfreecollage.resource.border;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class TBorderProcess {
    static int mapSize = 2048;

    protected static float getBorderRate(int i, int i2, TBorderRes tBorderRes) {
        float innerPx = ((mapSize - tBorderRes.getInnerPx()) - tBorderRes.getInnerPx2()) / i;
        float innerPy = ((mapSize - tBorderRes.getInnerPy()) - tBorderRes.getInnerPy2()) / i2;
        return innerPx < innerPy ? innerPx : innerPy;
    }

    protected static Bitmap processBorder(Context context, int i, int i2, TBorderRes tBorderRes, int i3) {
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
        int i6 = mapSize / 2;
        int i7 = i3 * 2;
        float borderRate = getBorderRate(i, i2, tBorderRes);
        if (i > i2) {
            i4 = (int) ((((borderRate * i2) + tBorderRes.getInnerPy()) + tBorderRes.getInnerPy2()) / i7);
            innerPx = i6;
        } else {
            innerPx = (int) ((((borderRate * i) + tBorderRes.getInnerPx()) + tBorderRes.getInnerPx2()) / i7);
            i4 = i6;
        }
        float f = i6;
        float f2 = f / mapSize;
        if (tBorderRes.getMapSize() > 0) {
            f2 = f / tBorderRes.getMapSize();
        }
        Bitmap decodeResource = BitmapFactory.decodeResource(context.getResources(), R.drawable.tranparent);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeResource, innerPx, i4, false);
        decodeResource.recycle();
        Canvas canvas = new Canvas(createScaledBitmap);
        Paint paint = new Paint();
        try {
            Bitmap leftTopCornorBitmap = tBorderRes.getLeftTopCornorBitmap();
            calcWithRate = calcWithRate(leftTopCornorBitmap.getWidth(), f2);
            calcWithRate2 = calcWithRate(leftTopCornorBitmap.getHeight(), f2);
            canvas.drawBitmap(leftTopCornorBitmap, (Rect) null, new Rect(0, 0, calcWithRate, calcWithRate2), paint);
            leftTopCornorBitmap.recycle();
            Bitmap leftBottomCornorBitmap = tBorderRes.getLeftBottomCornorBitmap();
            calcWithRate3 = calcWithRate(leftBottomCornorBitmap.getWidth(), f2);
            calcWithRate4 = calcWithRate(leftBottomCornorBitmap.getHeight(), f2);
            int i8 = i4 - calcWithRate4;
            canvas.drawBitmap(leftBottomCornorBitmap, (Rect) null, new Rect(0, i8, calcWithRate3 + 0, i8 + calcWithRate4), (Paint) null);
            leftBottomCornorBitmap.recycle();
            rightTopCornorBitmap = tBorderRes.getRightTopCornorBitmap();
            calcWithRate5 = calcWithRate(rightTopCornorBitmap.getWidth(), f2);
            calcWithRate6 = calcWithRate(rightTopCornorBitmap.getHeight(), f2);
            i5 = innerPx - calcWithRate5;
            bitmap = createScaledBitmap;
        } catch (OutOfMemoryError e) {
            e = e;
            bitmap = createScaledBitmap;
        }
        try {
            canvas.drawBitmap(rightTopCornorBitmap, (Rect) null, new Rect(i5, 0, i5 + calcWithRate5, calcWithRate6 + 0), (Paint) null);
            rightTopCornorBitmap.recycle();
            Bitmap rightBottomCornorBitmap = tBorderRes.getRightBottomCornorBitmap();
            int calcWithRate7 = calcWithRate(rightBottomCornorBitmap.getWidth(), f2);
            int calcWithRate8 = calcWithRate(rightBottomCornorBitmap.getHeight(), f2);
            int i9 = innerPx - calcWithRate7;
            int i10 = i4 - calcWithRate8;
            canvas.drawBitmap(rightBottomCornorBitmap, (Rect) null, new Rect(i9, i10, i9 + calcWithRate7, i10 + calcWithRate8), (Paint) null);
            rightBottomCornorBitmap.recycle();
            Bitmap leftBitmap = tBorderRes.getLeftBitmap();
            int calcWithRate9 = calcWithRate(leftBitmap.getWidth(), f2);
            int calcWithRate10 = calcWithRate(leftBitmap.getHeight(), f2);
            int i11 = (i4 - calcWithRate2) - calcWithRate4;
            if (i11 > 0) {
                Bitmap TileBitmapVertical = TileBitmapVertical(leftBitmap, i11, calcWithRate9, calcWithRate10);
                if (TileBitmapVertical != leftBitmap) {
                    leftBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapVertical, (Rect) null, new Rect(0, calcWithRate2, calcWithRate9 + 0, i11 + calcWithRate2), (Paint) null);
                TileBitmapVertical.recycle();
            }
            Bitmap topBitmap = tBorderRes.getTopBitmap();
            int calcWithRate11 = calcWithRate(topBitmap.getHeight(), f2);
            int calcWithRate12 = calcWithRate(topBitmap.getWidth(), f2);
            int i12 = (innerPx - calcWithRate) - calcWithRate5;
            if (i12 > 0) {
                Bitmap TileBitmapHorizon = TileBitmapHorizon(topBitmap, i12, calcWithRate12, calcWithRate11);
                if (TileBitmapHorizon != topBitmap) {
                    topBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapHorizon, (Rect) null, new Rect(calcWithRate, 0, i12 + calcWithRate, calcWithRate11 + 0), (Paint) null);
                TileBitmapHorizon.recycle();
            }
            Bitmap rightBitmap = tBorderRes.getRightBitmap();
            int calcWithRate13 = calcWithRate(rightBitmap.getWidth(), f2);
            int calcWithRate14 = calcWithRate(rightBitmap.getHeight(), f2);
            int i13 = (i4 - calcWithRate6) - calcWithRate8;
            int i14 = innerPx - calcWithRate13;
            if (i13 > 0) {
                Bitmap TileBitmapVertical2 = TileBitmapVertical(rightBitmap, i13, calcWithRate13, calcWithRate14);
                if (TileBitmapVertical2 != rightBitmap) {
                    rightBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapVertical2, (Rect) null, new Rect(i14, calcWithRate6, calcWithRate13 + i14, i13 + calcWithRate6), (Paint) null);
                TileBitmapVertical2.recycle();
            }
            Bitmap bottomBitmap = tBorderRes.getBottomBitmap();
            int calcWithRate15 = calcWithRate(bottomBitmap.getWidth(), f2);
            int calcWithRate16 = calcWithRate(bottomBitmap.getHeight(), f2);
            int i15 = (innerPx - calcWithRate3) - calcWithRate7;
            int i16 = i4 - calcWithRate16;
            if (i15 > 0) {
                Bitmap TileBitmapHorizon2 = TileBitmapHorizon(bottomBitmap, i15, calcWithRate15, calcWithRate16);
                if (TileBitmapHorizon2 != bottomBitmap) {
                    bottomBitmap.recycle();
                }
                canvas.drawBitmap(TileBitmapHorizon2, (Rect) null, new Rect(calcWithRate3, i16, calcWithRate3 + i15, calcWithRate16 + i16), (Paint) null);
                TileBitmapHorizon2.recycle();
            }
            return bitmap;
        } catch (OutOfMemoryError e2) {
            e2.printStackTrace();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            throw e2;
        }
    }

    protected static void processBorder(Context context, int i, int i2, TBorderRes tBorderRes, int i3, Canvas canvas) {
        float borderRate = 1.0f / getBorderRate(i, i2, tBorderRes);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        try {
            Bitmap leftTopCornorBitmap = tBorderRes.getLeftTopCornorBitmap();
            int calcWithRate = calcWithRate(leftTopCornorBitmap.getWidth(), borderRate);
            int calcWithRate2 = calcWithRate(leftTopCornorBitmap.getHeight(), borderRate);
            canvas.drawBitmap(leftTopCornorBitmap, (Rect) null, new Rect(0, 0, calcWithRate, calcWithRate2), paint);
            leftTopCornorBitmap.recycle();
            Bitmap leftBottomCornorBitmap = tBorderRes.getLeftBottomCornorBitmap();
            int calcWithRate3 = calcWithRate(leftBottomCornorBitmap.getWidth(), borderRate);
            int calcWithRate4 = calcWithRate(leftBottomCornorBitmap.getHeight(), borderRate);
            int i4 = i2 - calcWithRate4;
            canvas.drawBitmap(leftBottomCornorBitmap, (Rect) null, new Rect(0, i4, calcWithRate3 + 0, i4 + calcWithRate4), (Paint) null);
            leftBottomCornorBitmap.recycle();
            Bitmap rightTopCornorBitmap = tBorderRes.getRightTopCornorBitmap();
            int calcWithRate5 = calcWithRate(rightTopCornorBitmap.getWidth(), borderRate);
            int calcWithRate6 = calcWithRate(rightTopCornorBitmap.getHeight(), borderRate);
            int i5 = i - calcWithRate5;
            canvas.drawBitmap(rightTopCornorBitmap, (Rect) null, new Rect(i5, 0, i5 + calcWithRate5, calcWithRate6 + 0), (Paint) null);
            rightTopCornorBitmap.recycle();
            Bitmap rightBottomCornorBitmap = tBorderRes.getRightBottomCornorBitmap();
            int calcWithRate7 = calcWithRate(rightBottomCornorBitmap.getWidth(), borderRate);
            int calcWithRate8 = calcWithRate(rightBottomCornorBitmap.getHeight(), borderRate);
            int i6 = i - calcWithRate7;
            int i7 = i2 - calcWithRate8;
            canvas.drawBitmap(rightBottomCornorBitmap, (Rect) null, new Rect(i6, i7, i6 + calcWithRate7, i7 + calcWithRate8), (Paint) null);
            rightBottomCornorBitmap.recycle();
            Bitmap leftBitmap = tBorderRes.getLeftBitmap();
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
            Bitmap topBitmap = tBorderRes.getTopBitmap();
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
            Bitmap rightBitmap = tBorderRes.getRightBitmap();
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
            Bitmap bottomBitmap = tBorderRes.getBottomBitmap();
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

    public static BorderReturns processNinePathBorderOuter(Context context, int i, int i2, TBorderRes tBorderRes) {
        Bitmap bitmap;
        try {
            bitmap = processBorder(context, i, i2, tBorderRes, 1);
        } catch (OutOfMemoryError unused) {
            try {
                bitmap = processBorder(context, i, i2, tBorderRes, 2);
            } catch (OutOfMemoryError unused2) {
                try {
                    bitmap = processBorder(context, i, i2, tBorderRes, 4);
                } catch (OutOfMemoryError unused3) {
                    bitmap = null;
                }
            }
        }
        Bitmap bitmap2 = bitmap;
        float borderRate = getBorderRate(i, i2, tBorderRes);
        return new BorderReturns(bitmap2, (int) (tBorderRes.getInnerPx() / borderRate), (int) (tBorderRes.getInnerPy() / borderRate), (int) (tBorderRes.getInnerPx2() / borderRate), (int) (tBorderRes.getInnerPy2() / borderRate));
    }

    public static Bitmap processNinePathBorderOuter(Context context, int i, int i2, TBorderRes tBorderRes, Canvas canvas) {
        try {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            processBorder(context, i, i2, tBorderRes, 1, new Canvas(createBitmap));
            return createBitmap;
        } catch (OutOfMemoryError unused) {
            return null;
        }
    }

    public static Bitmap processNinePathBitmap(Context context, Bitmap bitmap, TBorderRes tBorderRes) {
        Bitmap bitmap2;
        Bitmap createBitmap;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        try {
            try {
                try {
                    bitmap2 = processBorder(context, width, height, tBorderRes, 1);
                } catch (OutOfMemoryError unused) {
                    bitmap2 = null;
                }
            } catch (OutOfMemoryError unused2) {
                bitmap2 = processBorder(context, width, height, tBorderRes, 4);
            }
        } catch (OutOfMemoryError unused3) {
            bitmap2 = processBorder(context, width, height, tBorderRes, 2);
        }
        float borderRate = getBorderRate(width, height, tBorderRes);
        int innerPx = (int) (tBorderRes.getInnerPx() / borderRate);
        int innerPy = (int) (tBorderRes.getInnerPy() / borderRate);
        int i = width + innerPx;
        int innerPx2 = ((int) (tBorderRes.getInnerPx2() / borderRate)) + i;
        int i2 = height + innerPy;
        int innerPy2 = ((int) (tBorderRes.getInnerPy2() / borderRate)) + i2;
        try {
            createBitmap = Bitmap.createBitmap(innerPx2, innerPy2, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError unused4) {
            if (bitmap2 != null) {
                try {
                    if (!bitmap2.isRecycled()) {
                        bitmap2.recycle();
                    }
                } catch (OutOfMemoryError unused5) {
                    if (bitmap2 != null) {
                        try {
                            if (!bitmap2.isRecycled()) {
                                bitmap2.recycle();
                            }
                        } catch (OutOfMemoryError unused6) {
                            if (bitmap2 != null && !bitmap2.isRecycled()) {
                                bitmap2.recycle();
                            }
                            bitmap2 = processBorder(context, width, height, tBorderRes, 8);
                            createBitmap = Bitmap.createBitmap(innerPx2, innerPy2, Bitmap.Config.ARGB_8888);
                        }
                    }
                    bitmap2 = processBorder(context, width, height, tBorderRes, 4);
                    createBitmap = Bitmap.createBitmap(innerPx2, innerPy2, Bitmap.Config.ARGB_8888);
                }
            }
            bitmap2 = processBorder(context, width, height, tBorderRes, 2);
            createBitmap = Bitmap.createBitmap(innerPx2, innerPy2, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, new Rect(0, 0, width, height), new Rect(innerPx, innerPy, i, i2), paint);
        bitmap2.getWidth();
        bitmap2.getHeight();
        canvas.drawBitmap(bitmap2, (Rect) null, new Rect(0, 0, innerPx2, innerPy2), paint);
        bitmap2.recycle();
        return createBitmap;
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
