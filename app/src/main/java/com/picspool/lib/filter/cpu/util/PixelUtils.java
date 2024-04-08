package com.picspool.lib.filter.cpu.util;

import android.graphics.Color;
import java.util.Random;

/* loaded from: classes3.dex */
public class PixelUtils {
    public static final int ADD = 4;
    public static final int ALPHA = 19;
    public static final int ALPHA_TO_GRAY = 20;
    public static final int AVERAGE = 13;
    public static final int CLEAR = 15;
    public static final int COLOR = 11;
    public static final int DIFFERENCE = 6;
    public static final int DISSOLVE = 17;
    public static final int DST_IN = 18;
    public static final int EXCHANGE = 16;
    public static final int HUE = 8;
    public static final int MAX = 3;
    public static final int MIN = 2;
    public static final int MULTIPLY = 7;
    public static final int NORMAL = 1;
    public static final int OVERLAY = 14;
    public static final int REPLACE = 0;
    public static final int SATURATION = 9;
    public static final int SCREEN = 12;
    public static final int SUBTRACT = 5;
    public static final int VALUE = 10;
    private static Random randomGenerator = new Random();
    private static final float[] hsb1 = new float[3];
    private static final float[] hsb2 = new float[3];

    public static int clamp(int i) {
        if (i < 0) {
            return 0;
        }
        if (i > 255) {
            return 255;
        }
        return i;
    }

    public static int interpolate(int i, int i2, float f) {
        return clamp((int) (i + (f * (i2 - i))));
    }

    public static int brightness(int i) {
        return ((((i >> 16) & 255) + ((i >> 8) & 255)) + (i & 255)) / 3;
    }

    public static boolean nearColors(int i, int i2, int i3) {
        return Math.abs(((i >> 16) & 255) - ((i2 >> 16) & 255)) <= i3 && Math.abs(((i >> 8) & 255) - ((i2 >> 8) & 255)) <= i3 && Math.abs((i & 255) - (i2 & 255)) <= i3;
    }

    public static int combinePixels(int i, int i2, int i3) {
        return combinePixels(i, i2, i3, 255);
    }

    public static int combinePixels(int i, int i2, int i3, int i4, int i5) {
        return combinePixels(i & i5, i2, i3, i4) | ((~i5) & i2);
    }

    public static int combinePixels(int i, int i2, int i3, int i4) {
        if (i3 == 0) {
            return i;
        }
        int i5 = (i >> 24) & 255;
        int i6 = (i >> 16) & 255;
        int i7 = (i >> 8) & 255;
        int i8 = i & 255;
        int i9 = (i2 >> 24) & 255;
        int i10 = (i2 >> 16) & 255;
        int i11 = (i2 >> 8) & 255;
        int i12 = i2 & 255;
        switch (i3) {
            case 2:
                i6 = Math.min(i6, i10);
                i7 = Math.min(i7, i11);
                i8 = Math.min(i8, i12);
                break;
            case 3:
                i6 = Math.max(i6, i10);
                i7 = Math.max(i7, i11);
                i8 = Math.max(i8, i12);
                break;
            case 4:
                i6 = clamp(i6 + i10);
                i7 = clamp(i7 + i11);
                i8 = clamp(i8 + i12);
                break;
            case 5:
                i6 = clamp(i10 - i6);
                i7 = clamp(i11 - i7);
                i8 = clamp(i12 - i8);
                break;
            case 6:
                i6 = clamp(Math.abs(i6 - i10));
                i7 = clamp(Math.abs(i7 - i11));
                i8 = clamp(Math.abs(i8 - i12));
                break;
            case 7:
                i6 = clamp((i6 * i10) / 255);
                i7 = clamp((i7 * i11) / 255);
                i8 = clamp((i8 * i12) / 255);
                break;
            case 8:
            case 9:
            case 10:
            case 11:
                Color.RGBToHSV(i6, i7, i8, hsb1);
                Color.RGBToHSV(i6, i7, i8, hsb2);
                switch (i3) {
                    case 8:
                        hsb2[0] = hsb1[0];
                        break;
                    case 9:
                        hsb2[1] = hsb1[1];
                        break;
                    case 10:
                        hsb2[2] = hsb1[2];
                        break;
                    case 11:
                        float[] fArr = hsb2;
                        float[] fArr2 = hsb1;
                        fArr[0] = fArr2[0];
                        fArr[1] = fArr2[1];
                        break;
                }
                int HSVToColor = Color.HSVToColor(hsb2);
                i6 = (HSVToColor >> 16) & 255;
                i7 = (HSVToColor >> 8) & 255;
                i8 = HSVToColor & 255;
                break;
            case 12:
                i6 = 255 - (((255 - i6) * (255 - i10)) / 255);
                i7 = 255 - (((255 - i7) * (255 - i11)) / 255);
                i8 = 255 - (((255 - i8) * (255 - i12)) / 255);
                break;
            case 13:
                i6 = (i6 + i10) / 2;
                i7 = (i7 + i11) / 2;
                i8 = (i8 + i12) / 2;
                break;
            case 14:
                int i13 = 255 - i6;
                i6 = (((255 - (((255 - i10) * i13) / 255)) * i6) + (((i6 * i10) / 255) * i13)) / 255;
                int i14 = 255 - i7;
                i7 = (((255 - (((255 - i11) * i14) / 255)) * i7) + (((i7 * i11) / 255) * i14)) / 255;
                int i15 = 255 - i8;
                i8 = (((255 - (((255 - i12) * i15) / 255)) * i8) + (((i8 * i12) / 255) * i15)) / 255;
                break;
            case 15:
                i8 = 255;
                i6 = 255;
                i7 = 255;
                break;
            case 17:
                if ((randomGenerator.nextInt() & 255) <= i5) {
                    i8 = i12;
                    i6 = i10;
                    i7 = i11;
                    break;
                }
                break;
            case 18:
                int clamp = clamp((i10 * i5) / 255);
                int clamp2 = clamp((i11 * i5) / 255);
                return (clamp << 16) | (clamp((i9 * i5) / 255) << 24) | (clamp2 << 8) | clamp((i12 * i5) / 255);
            case 19:
                return (((i5 * i9) / 255) << 24) | (i10 << 16) | (i11 << 8) | i12;
            case 20:
                int i16 = 255 - i5;
                return i16 | (i5 << 24) | (i16 << 16) | (i16 << 8);
        }
        if (i4 != 255 || i5 != 255) {
            int i17 = (i5 * i4) / 255;
            int i18 = ((255 - i17) * i9) / 255;
            i6 = clamp(((i6 * i17) + (i10 * i18)) / 255);
            i7 = clamp(((i7 * i17) + (i11 * i18)) / 255);
            i8 = clamp(((i8 * i17) + (i12 * i18)) / 255);
            i5 = clamp(i17 + i18);
        }
        return i8 | (i5 << 24) | (i6 << 16) | (i7 << 8);
    }

    public static void getRGB(int[] iArr, int i, int i2, int i3, int i4, int i5, int[] iArr2) {
        int i6 = 0;
        for (int i7 = i2; i7 < i2 + i4; i7++) {
            for (int i8 = i; i8 < i + i3; i8++) {
                iArr2[i6] = iArr[(i7 * i5) + i8];
                i6++;
            }
        }
    }

    public static void setRGB(int[] iArr, int i, int i2, int i3, int i4, int i5, int[] iArr2) {
        int i6 = 0;
        for (int i7 = i2; i7 < i2 + i4; i7++) {
            for (int i8 = i; i8 < i + i3; i8++) {
                iArr[(i7 * i5) + i8] = iArr2[i6];
                i6++;
            }
        }
    }

    public static void setLineRGB(int[] iArr, int i, int i2, int[] iArr2) {
        for (int i3 = 0; i3 < i2; i3++) {
            iArr[(i2 * i) + i3] = iArr2[i3];
        }
    }

    public static void getLineRGB(int[] iArr, int i, int i2, int[] iArr2) {
        for (int i3 = 0; i3 < i2; i3++) {
            iArr2[i3] = iArr[(i2 * i) + i3];
        }
    }
}
