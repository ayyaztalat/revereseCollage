package com.picspool.lib.filter.cpu.util;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/* loaded from: classes3.dex */
public class ImageMath {
    public static final float HALF_PI = 1.5707964f;

    /* renamed from: PI */
    public static final float f1964PI = 3.1415927f;
    public static final float QUARTER_PI = 0.7853982f;
    public static final float TWO_PI = 6.2831855f;
    private static final float m00 = -0.5f;
    private static final float m01 = 1.5f;
    private static final float m02 = -1.5f;
    private static final float m03 = 0.5f;
    private static final float m10 = 1.0f;
    private static final float m11 = -2.5f;
    private static final float m12 = 2.0f;
    private static final float m13 = -0.5f;
    private static final float m20 = -0.5f;
    private static final float m21 = 0.0f;
    private static final float m22 = 0.5f;
    private static final float m23 = 0.0f;
    private static final float m30 = 0.0f;
    private static final float m31 = 1.0f;
    private static final float m32 = 0.0f;
    private static final float m33 = 0.0f;

    public static float bias(float f, float f2) {
        return f / ((((1.0f / f2) - m12) * (1.0f - f)) + 1.0f);
    }

    public static int bilinearInterpolate(float f, float f2, int i, int i2, int i3, int i4) {
        float f3 = 1.0f - f;
        float f4 = 1.0f - f2;
        return ((int) ((f4 * (((i & 255) * f3) + ((i2 & 255) * f))) + (f2 * ((f3 * (i3 & 255)) + ((i4 & 255) * f))))) | (((int) ((((((i >> 24) & 255) * f3) + (((i2 >> 24) & 255) * f)) * f4) + (((((i3 >> 24) & 255) * f3) + (((i4 >> 24) & 255) * f)) * f2))) << 24) | (((int) ((((((i >> 16) & 255) * f3) + (((i2 >> 16) & 255) * f)) * f4) + (((((i3 >> 16) & 255) * f3) + (((i4 >> 16) & 255) * f)) * f2))) << 16) | (((int) ((((((i >> 8) & 255) * f3) + (((i2 >> 8) & 255) * f)) * f4) + (((((i3 >> 8) & 255) * f3) + (((i4 >> 8) & 255) * f)) * f2))) << 8);
    }

    public static int brightnessNTSC(int i) {
        return (int) ((((i >> 16) & 255) * 0.299f) + (((i >> 8) & 255) * 0.587f) + ((i & 255) * 0.114f));
    }

    public static float clamp(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    public static int clamp(int i, int i2, int i3) {
        return i < i2 ? i2 : i > i3 ? i3 : i;
    }

    public static float gain(float f, float f2) {
        float f3;
        float f4 = ((1.0f / f2) - m12) * (1.0f - (m12 * f));
        if (f < 0.5d) {
            f3 = f4 + 1.0f;
        } else {
            f = f4 - f;
            f3 = f4 - 1.0f;
        }
        return f / f3;
    }

    public static float lerp(float f, float f2, float f3) {
        return f2 + (f * (f3 - f2));
    }

    public static int lerp(float f, int i, int i2) {
        return (int) (i + (f * (i2 - i)));
    }

    public static double mod(double d, double d2) {
        double d3 = d - (((int) (d / d2)) * d2);
        return d3 < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? d3 + d2 : d3;
    }

    public static float mod(float f, float f2) {
        float f3 = f - (((int) (f / f2)) * f2);
        return f3 < 0.0f ? f3 + f2 : f3;
    }

    public static float pulse(float f, float f2, float f3) {
        return (f3 < f || f3 >= f2) ? 0.0f : 1.0f;
    }

    public static float smoothPulse(float f, float f2, float f3, float f4, float f5) {
        if (f5 < f || f5 >= f4) {
            return 0.0f;
        }
        if (f5 < f2) {
            float f6 = (f5 - f) / (f2 - f);
            return f6 * f6 * (3.0f - (f6 * m12));
        } else if (f5 < f3) {
            return 1.0f;
        } else {
            float f7 = (f5 - f3) / (f4 - f3);
            return 1.0f - ((f7 * f7) * (3.0f - (f7 * m12)));
        }
    }

    public static float smoothStep(float f, float f2, float f3) {
        if (f3 < f) {
            return 0.0f;
        }
        if (f3 >= f2) {
            return 1.0f;
        }
        float f4 = (f3 - f) / (f2 - f);
        return f4 * f4 * (3.0f - (f4 * m12));
    }

    public static float step(float f, float f2) {
        return f2 < f ? 0.0f : 1.0f;
    }

    public static float circleUp(float f) {
        float f2 = 1.0f - f;
        return (float) Math.sqrt(1.0f - (f2 * f2));
    }

    public static float circleDown(float f) {
        return 1.0f - ((float) Math.sqrt(1.0f - (f * f)));
    }

    public static int mod(int i, int i2) {
        int i3 = i - ((i / i2) * i2);
        return i3 < 0 ? i3 + i2 : i3;
    }

    public static float triangle(float f) {
        float mod = mod(f, 1.0f);
        if (mod >= 0.5d) {
            mod = 1.0f - mod;
        }
        return mod * m12;
    }

    public static int mixColors(float f, int i, int i2) {
        return lerp(f, i & 255, i2 & 255) | (lerp(f, (i >> 24) & 255, (i2 >> 24) & 255) << 24) | (lerp(f, (i >> 16) & 255, (i2 >> 16) & 255) << 16) | (lerp(f, (i >> 8) & 255, (i2 >> 8) & 255) << 8);
    }

    public static float spline(float f, int i, float[] fArr) {
        int i2 = i - 3;
        if (i2 < 1) {
            throw new IllegalArgumentException("Too few knots in spline");
        }
        float clamp = clamp(f, 0.0f, 1.0f) * i2;
        int i3 = (int) clamp;
        int i4 = i - 4;
        if (i3 > i4) {
            i3 = i4;
        }
        float f2 = clamp - i3;
        float f3 = fArr[i3];
        float f4 = fArr[i3 + 1];
        float f5 = fArr[i3 + 2];
        float f6 = fArr[i3 + 3];
        float f7 = f3 * (-0.5f);
        float f8 = (m01 * f4) + f7 + (m02 * f5) + (f6 * 0.5f);
        float f9 = (f3 * 1.0f) + (m11 * f4) + (m12 * f5) + ((-0.5f) * f6);
        float f10 = f6 * 0.0f;
        return (((((f8 * f2) + f9) * f2) + f7 + (f4 * 0.0f) + (0.5f * f5) + f10) * f2) + (f3 * 0.0f) + (f4 * 1.0f) + (f5 * 0.0f) + f10;
    }

    public static float spline(float f, int i, int[] iArr, int[] iArr2) {
        int i2 = i - 3;
        if (i2 < 1) {
            throw new IllegalArgumentException("Too few knots in spline");
        }
        int i3 = 0;
        int i4 = 0;
        while (i4 < i2) {
            int i5 = i4 + 1;
            if (iArr[i5] > f) {
                break;
            }
            i4 = i5;
        }
        if (i4 <= i2) {
            i2 = i4;
        }
        float f2 = (f - iArr[i2]) / (iArr[i2 + 1] - iArr[i2]);
        int i6 = i2 - 1;
        if (i6 < 0) {
            f2 = 0.0f;
        } else {
            i3 = i6;
        }
        float f3 = iArr2[i3];
        float f4 = iArr2[i3 + 1];
        float f5 = iArr2[i3 + 2];
        float f6 = iArr2[i3 + 3];
        float f7 = f3 * (-0.5f);
        float f8 = (f3 * 1.0f) + (m11 * f4) + (m12 * f5) + ((-0.5f) * f6);
        float f9 = f6 * 0.0f;
        return (((((((m01 * f4) + f7 + (m02 * f5) + (f6 * 0.5f)) * f2) + f8) * f2) + f7 + (f4 * 0.0f) + (0.5f * f5) + f9) * f2) + (f3 * 0.0f) + (f4 * 1.0f) + (f5 * 0.0f) + f9;
    }

    public static int colorSpline(float f, int i, int[] iArr) {
        int i2 = i - 3;
        if (i2 < 1) {
            throw new IllegalArgumentException("Too few knots in spline");
        }
        float clamp = clamp(f, 0.0f, 1.0f) * i2;
        int i3 = (int) clamp;
        int i4 = i - 4;
        if (i3 > i4) {
            i3 = i4;
        }
        float f2 = clamp - i3;
        int i5 = 0;
        for (int i6 = 0; i6 < 4; i6++) {
            int i7 = i6 * 8;
            int i8 = 255;
            float f3 = (iArr[i3] >> i7) & 255;
            float f4 = (iArr[i3 + 1] >> i7) & 255;
            float f5 = (iArr[i3 + 2] >> i7) & 255;
            float f6 = (iArr[i3 + 3] >> i7) & 255;
            float f7 = f3 * (-0.5f);
            float f8 = f7 + (m01 * f4) + (m02 * f5) + (f6 * 0.5f);
            float f9 = (f3 * 1.0f) + (m11 * f4) + (m12 * f5) + ((-0.5f) * f6);
            float f10 = f6 * 0.0f;
            int i9 = (int) ((((((f8 * f2) + f9) * f2) + f7 + (f4 * 0.0f) + (0.5f * f5) + f10) * f2) + (f3 * 0.0f) + (f4 * 1.0f) + (f5 * 0.0f) + f10);
            if (i9 < 0) {
                i8 = 0;
            } else if (i9 <= 255) {
                i8 = i9;
            }
            i5 |= i8 << i7;
        }
        return i5;
    }

    public static int colorSpline(int i, int i2, int[] iArr, int[] iArr2) {
        int i3 = i2 - 3;
        if (i3 < 1) {
            throw new IllegalArgumentException("Too few knots in spline");
        }
        int i4 = 0;
        while (i4 < i3) {
            int i5 = i4 + 1;
            if (iArr[i5] > i) {
                break;
            }
            i4 = i5;
        }
        if (i4 <= i3) {
            i3 = i4;
        }
        float f = (i - iArr[i3]) / (iArr[i3 + 1] - iArr[i3]);
        int i6 = i3 - 1;
        if (i6 < 0) {
            f = 0.0f;
            i6 = 0;
        }
        int i7 = 0;
        for (int i8 = 0; i8 < 4; i8++) {
            int i9 = i8 * 8;
            int i10 = 255;
            float f2 = (iArr2[i6] >> i9) & 255;
            float f3 = (iArr2[i6 + 1] >> i9) & 255;
            float f4 = (iArr2[i6 + 2] >> i9) & 255;
            float f5 = (iArr2[i6 + 3] >> i9) & 255;
            float f6 = f2 * (-0.5f);
            float f7 = (f2 * 1.0f) + (m11 * f3) + (m12 * f4) + ((-0.5f) * f5);
            float f8 = f5 * 0.0f;
            int i11 = (int) ((((((((m01 * f3) + f6 + (m02 * f4) + (f5 * 0.5f)) * f) + f7) * f) + f6 + (f3 * 0.0f) + (0.5f * f4) + f8) * f) + (f2 * 0.0f) + (f3 * 1.0f) + (f4 * 0.0f) + f8);
            if (i11 < 0) {
                i10 = 0;
            } else if (i11 <= 255) {
                i10 = i11;
            }
            i7 |= i10 << i9;
        }
        return i7;
    }

    public static void resample(int[] iArr, int[] iArr2, int i, int i2, int i3, float[] fArr) {
        int i4;
        int i5;
        float f;
        int i6 = i;
        int length = iArr.length;
        float[] fArr2 = new float[i6 + 2];
        int i7 = 0;
        for (int i8 = 0; i8 < i6; i8++) {
            while (true) {
                i5 = i7 + 1;
                f = i8;
                if (fArr[i5] < f) {
                    i7 = i5;
                }
            }
        }
        float f2 = i6;
        fArr2[i6] = f2;
        fArr2[i6 + 1] = f2;
        float f3 = fArr2[1];
        int i9 = iArr[i2];
        int i10 = i2 + i3;
        int i11 = iArr[i10];
        int i12 = i2;
        int i13 = i11;
        int i14 = i11 & 255;
        float f4 = 1.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        float f8 = 0.0f;
        int i15 = (i9 >> 8) & 255;
        int i16 = (i11 >> 16) & 255;
        int i17 = (i11 >> 8) & 255;
        int i18 = (i9 >> 24) & 255;
        int i19 = (i11 >> 24) & 255;
        float f9 = f3;
        int i20 = i10 + i3;
        int i21 = (i9 >> 16) & 255;
        int i22 = i9 & 255;
        float f10 = f9;
        int i23 = 1;
        while (i23 <= i6) {
            float f11 = 1.0f - f4;
            int i24 = i18;
            float f12 = (i18 * f4) + (i19 * f11);
            int i25 = i21;
            float f13 = (i21 * f4) + (i16 * f11);
            int i26 = i15;
            float f14 = (i15 * f4) + (i17 * f11);
            int i27 = i22;
            float f15 = (i22 * f4) + (f11 * i14);
            if (f4 < f10) {
                f5 += f12 * f4;
                f6 += f13 * f4;
                f7 += f14 * f4;
                f8 += f15 * f4;
                f10 -= f4;
                i4 = i20 < length ? iArr[i20] : i13;
                i20 += i3;
                f4 = 1.0f;
                i22 = i14;
                i14 = i4 & 255;
                i15 = i17;
                i17 = (i4 >> 8) & 255;
                i21 = i16;
                i16 = (i4 >> 16) & 255;
                i18 = i19;
                i19 = (i4 >> 24) & 255;
            } else {
                iArr2[i12] = (((int) Math.min((f5 + (f12 * f10)) / f9, 255.0f)) << 24) | (((int) Math.min((f6 + (f13 * f10)) / f9, 255.0f)) << 16) | (((int) Math.min((f7 + (f14 * f10)) / f9, 255.0f)) << 8) | ((int) Math.min((f8 + (f15 * f10)) / f9, 255.0f));
                i12 += i3;
                f4 -= f10;
                int i28 = i23 + 1;
                f10 = fArr2[i28] - fArr2[i23];
                i18 = i24;
                i23 = i28;
                f9 = f10;
                i21 = i25;
                i15 = i26;
                i22 = i27;
                f5 = 0.0f;
                f6 = 0.0f;
                f7 = 0.0f;
                f8 = 0.0f;
                i4 = i13;
            }
            i13 = i4;
            i6 = i;
        }
    }

    public static void premultiply(int[] iArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            int i4 = iArr[i];
            int i5 = (i4 >> 24) & 255;
            float f = i5 * 0.003921569f;
            iArr[i] = ((int) ((i4 & 255) * f)) | (i5 << 24) | (((int) (((i4 >> 16) & 255) * f)) << 16) | (((int) (((i4 >> 8) & 255) * f)) << 8);
            i++;
        }
    }

    public static void unpremultiply(int[] iArr, int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            int i4 = iArr[i];
            int i5 = (i4 >> 24) & 255;
            int i6 = (i4 >> 16) & 255;
            int i7 = (i4 >> 8) & 255;
            int i8 = i4 & 255;
            if (i5 != 0 && i5 != 255) {
                float f = 255.0f / i5;
                int i9 = (int) (i6 * f);
                int i10 = (int) (i7 * f);
                int i11 = (int) (i8 * f);
                if (i9 > 255) {
                    i9 = 255;
                }
                if (i10 > 255) {
                    i10 = 255;
                }
                iArr[i] = (i5 << 24) | (i9 << 16) | (i10 << 8) | (i11 <= 255 ? i11 : 255);
            }
            i++;
        }
    }
}
