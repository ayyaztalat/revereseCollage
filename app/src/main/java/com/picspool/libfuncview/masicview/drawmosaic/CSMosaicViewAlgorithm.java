package com.picspool.libfuncview.masicview.drawmosaic;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public class CSMosaicViewAlgorithm {
    private static int clamp(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    private static void blur(int[] iArr, int[] iArr2, int i, int i2, int i3) {
        int i4 = i3;
        int i5 = i - 1;
        int i6 = (i4 * 2) + 1;
        int i7 = i6 * 256;
        int[] iArr3 = new int[i7];
        int i8 = 0;
        for (int i9 = 0; i9 < i7; i9++) {
            iArr3[i9] = i9 / i6;
        }
        int i10 = 0;
        int i11 = 0;
        while (i10 < i2) {
            int i12 = 0;
            int i13 = 0;
            int i14 = 0;
            int i15 = 0;
            for (int i16 = -i4; i16 <= i4; i16++) {
                int i17 = iArr[clamp(i16, i8, i5) + i11];
                i12 += (i17 >> 24) & 255;
                i13 += (i17 >> 16) & 255;
                i14 += (i17 >> 8) & 255;
                i15 += i17 & 255;
            }
            int i18 = i10;
            int i19 = 0;
            while (i19 < i) {
                iArr2[i18] = (iArr3[i12] << 24) | (iArr3[i13] << 16) | (iArr3[i14] << 8) | iArr3[i15];
                int i20 = i19 + i4 + 1;
                if (i20 > i5) {
                    i20 = i5;
                }
                int i21 = i19 - i4;
                if (i21 < 0) {
                    i21 = 0;
                }
                int i22 = iArr[i20 + i11];
                int i23 = iArr[i21 + i11];
                i12 += ((i22 >> 24) & 255) - ((i23 >> 24) & 255);
                i13 += ((i22 & 16711680) - (16711680 & i23)) >> 16;
                i14 += ((i22 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) - (65280 & i23)) >> 8;
                i15 += (i22 & 255) - (i23 & 255);
                i18 += i2;
                i19++;
                i4 = i3;
            }
            i11 += i;
            i10++;
            i4 = i3;
            i8 = 0;
        }
    }

    public static Rect getStyleBlurPathRect(int i, int i2, Bitmap bitmap, int i3, int i4) {
        int i5 = 0;
        int i6 = 0;
        int width = bitmap.getWidth();
        int i7 = width / 2;
        int i8 = i - i7;
        int height = bitmap.getHeight() / 2;
        int i9 = i2 - height;
        int i10 = i + i7;
        int i11 = i2 + height;
        int floor = (int) Math.floor(Math.random() * 4.0d);
        if (floor == 0) {
            i5 = width / i3;
        } else {
            if (floor == 1) {
                i6 = width / i3;
            } else if (floor == 2) {
                i5 = width / i4;
            } else {
                if (floor == 3) {
                    i6 = width / i4;
                }
                return new Rect(i8, i9, i10, i11);
            }
            i8 += i6;
            i10 += i6;
            return new Rect(i8, i9, i10, i11);
        }
        i8 -= i5;
        i10 -= i5;
        return new Rect(i8, i9, i10, i11);
    }

    public static Rect getMultiBitmapPathRect(int i, int i2, int i3) {
        int i4 = i3 / 1;
        return new Rect(i - i4, i2 - i4, i + i4, i2 + i4);
    }

    public static float getPathAngle(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            if (i <= 0 || i2 >= 0) {
                if (i >= 0 || i2 <= 0) {
                    if (i >= 0 || i2 >= 0) {
                        if (i != 0 || i2 <= 0) {
                            if (i != 0 || i2 >= 0) {
                                if (i <= 0 || i2 != 0) {
                                    return (i >= 0 || i2 != 0) ? 0.0f : 90.0f;
                                }
                                return -90.0f;
                            }
                            return 180.0f;
                        }
                        return 0.0f;
                    }
                    return ((float) ((Math.atan2(Math.abs(i2), Math.abs(i)) / 3.141592653589793d) * 180.0d)) + 90.0f;
                }
                return 90.0f - ((float) ((Math.atan2(i2, Math.abs(i)) / 3.141592653589793d) * 180.0d));
            }
            return -(((float) ((Math.atan2(Math.abs(i2), i) / 3.141592653589793d) * 180.0d)) + 90.0f);
        }
        return ((float) ((Math.atan2(i2, i) / 3.141592653589793d) * 180.0d)) - 90.0f;
    }

    public static Bitmap setBlurMosaic(Bitmap bitmap, int i, int i2) {
        if (i <= 0 || i2 <= 0 || bitmap == null) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        int width = createBitmap.getWidth();
        int height = createBitmap.getHeight();
        int i3 = width * height;
        int[] iArr = new int[i3];
        int[] iArr2 = new int[i3];
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i4 = 0; i4 < 1; i4++) {
            blur(iArr, iArr2, width, height, 50);
            blur(iArr2, iArr, height, width, 50);
        }
        createBitmap.setPixels(iArr, 0, width, 0, 0, width, height);
        return createBitmap;
    }

    public static Bitmap getCustomBitmap(int i, int i2, Bitmap bitmap) {
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Rect rect = new Rect(0, 0, i, i2);
        Paint paint = new Paint();
        paint.setShader(bitmapShader);
        canvas.drawRect(rect, paint);
        canvas.save();
        bitmap.recycle();
        return createBitmap;
    }

    public static Bitmap getGridMosaic(int i, int i2, int i3, Bitmap bitmap) {
        int i4 = i;
        if (i4 <= 0 || i2 <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i4, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = i3;
        int ceil = (int) Math.ceil(i4 / f);
        int ceil2 = (int) Math.ceil(i2 / f);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        int i5 = 0;
        while (i5 < ceil) {
            int i6 = 0;
            while (i6 < ceil2) {
                int i7 = i3 * i5;
                int i8 = i3 * i6;
                int i9 = i7 + i3;
                if (i9 > i4) {
                    i9 = i4;
                }
                int i10 = i8 + i3;
                if (i10 > i2) {
                    i10 = i2;
                }
                int pixel = bitmap.getPixel(i7, i8);
                Rect rect = new Rect(i7, i8, i9, i10);
                paint.setColor(pixel);
                canvas.drawRect(rect, paint);
                i6++;
                i4 = i;
            }
            i5++;
            i4 = i;
        }
        return createBitmap;
    }

    public static Bitmap getMosaicBitmap(int i, int i2, Bitmap bitmap, Bitmap bitmap2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i3 = width * height;
        int[] iArr = new int[i3];
        if (i <= 0 || i >= bitmap2.getWidth() || i2 <= 0 || i2 >= bitmap2.getHeight()) {
            return bitmap;
        }
        int pixel = bitmap2.getPixel(i, i2);
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i4 = 0; i4 < i3; i4++) {
            if (iArr[i4] != 0) {
                iArr[i4] = Color.argb((iArr[i4] & ViewCompat.MEASURED_STATE_MASK) >> 24, (16711680 & pixel) >> 16, (65280 & pixel) >> 8, pixel & 255);
            }
        }
        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        copy.setPixels(iArr, 0, width, 0, 0, width, height);
        bitmap.recycle();
        return copy;
    }
}
