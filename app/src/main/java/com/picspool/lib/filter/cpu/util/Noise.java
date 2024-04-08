package com.picspool.lib.filter.cpu.util;

import java.lang.reflect.Array;
import java.util.Random;

/* loaded from: classes3.dex */
public class Noise implements Function1D, Function2D, Function3D {

    /* renamed from: B */
    private static final int f1965B = 256;

    /* renamed from: BM */
    private static final int f1966BM = 255;

    /* renamed from: N */
    private static final int f1967N = 4096;
    private static Random randomGenerator = new Random();

    /* renamed from: p */
    static int[] f1971p = new int[514];

    /* renamed from: g3 */
    static float[][] f1970g3 = (float[][]) Array.newInstance(float.class, 514, 3);

    /* renamed from: g2 */
    static float[][] f1969g2 = (float[][]) Array.newInstance(float.class, 514, 2);

    /* renamed from: g1 */
    static float[] f1968g1 = new float[514];
    static boolean start = true;

    public static float lerp(float f, float f2, float f3) {
        return f2 + (f * (f3 - f2));
    }

    private static float sCurve(float f) {
        return f * f * (3.0f - (f * 2.0f));
    }

    @Override // com.picspool.lib.filter.cpu.util.Function1D
    public float evaluate(float f) {
        return noise1(f);
    }

    @Override // com.picspool.lib.filter.cpu.util.Function2D
    public float evaluate(float f, float f2) {
        return noise2(f, f2);
    }

    @Override // com.picspool.lib.filter.cpu.util.Function3D
    public float evaluate(float f, float f2, float f3) {
        return noise3(f, f2, f3);
    }

    public static float turbulence2(float f, float f2, float f3) {
        float f4 = 0.0f;
        for (float f5 = 1.0f; f5 <= f3; f5 *= 2.0f) {
            f4 += Math.abs(noise2(f5 * f, f5 * f2)) / f5;
        }
        return f4;
    }

    public static float turbulence3(float f, float f2, float f3, float f4) {
        float f5 = 0.0f;
        for (float f6 = 1.0f; f6 <= f4; f6 *= 2.0f) {
            f5 += Math.abs(noise3(f6 * f, f6 * f2, f6 * f3)) / f6;
        }
        return f5;
    }

    public static float noise1(float f) {
        if (start) {
            start = false;
            init();
        }
        float f2 = f + 4096.0f;
        int i = (int) f2;
        int i2 = i & 255;
        float f3 = f2 - i;
        float f4 = f3 - 1.0f;
        float sCurve = sCurve(f3);
        float[] fArr = f1968g1;
        int[] iArr = f1971p;
        return lerp(sCurve, f3 * fArr[iArr[i2]], f4 * fArr[iArr[(i2 + 1) & 255]]) * 2.3f;
    }

    public static float noise2(float f, float f2) {
        if (start) {
            start = false;
            init();
        }
        float f3 = f + 4096.0f;
        int i = (int) f3;
        int i2 = i & 255;
        float f4 = f3 - i;
        float f5 = f4 - 1.0f;
        float f6 = f2 + 4096.0f;
        int i3 = (int) f6;
        int i4 = i3 & 255;
        int i5 = (i4 + 1) & 255;
        float f7 = f6 - i3;
        float f8 = f7 - 1.0f;
        int[] iArr = f1971p;
        int i6 = iArr[i2];
        int i7 = iArr[(i2 + 1) & 255];
        int i8 = iArr[i6 + i4];
        int i9 = iArr[i4 + i7];
        int i10 = iArr[i6 + i5];
        int i11 = iArr[i7 + i5];
        float sCurve = sCurve(f4);
        float sCurve2 = sCurve(f7);
        float[][] fArr = f1969g2;
        float[] fArr2 = fArr[i8];
        float[] fArr3 = fArr[i9];
        float lerp = lerp(sCurve, (fArr2[0] * f4) + (fArr2[1] * f7), (fArr3[0] * f5) + (f7 * fArr3[1]));
        float[][] fArr4 = f1969g2;
        float[] fArr5 = fArr4[i10];
        float[] fArr6 = fArr4[i11];
        return lerp(sCurve2, lerp, lerp(sCurve, (f4 * fArr5[0]) + (fArr5[1] * f8), (f5 * fArr6[0]) + (f8 * fArr6[1]))) * 1.5f;
    }

    public static float noise3(float f, float f2, float f3) {
        if (start) {
            start = false;
            init();
        }
        float f4 = f + 4096.0f;
        int i = (int) f4;
        int i2 = i & 255;
        float f5 = f4 - i;
        float f6 = f5 - 1.0f;
        float f7 = f2 + 4096.0f;
        int i3 = (int) f7;
        int i4 = i3 & 255;
        int i5 = (i4 + 1) & 255;
        float f8 = f7 - i3;
        float f9 = f8 - 1.0f;
        float f10 = f3 + 4096.0f;
        int i6 = (int) f10;
        int i7 = i6 & 255;
        int i8 = (i7 + 1) & 255;
        float f11 = f10 - i6;
        float f12 = f11 - 1.0f;
        int[] iArr = f1971p;
        int i9 = iArr[i2];
        int i10 = iArr[(i2 + 1) & 255];
        int i11 = iArr[i9 + i4];
        int i12 = iArr[i4 + i10];
        int i13 = iArr[i9 + i5];
        int i14 = iArr[i10 + i5];
        float sCurve = sCurve(f5);
        float sCurve2 = sCurve(f8);
        float sCurve3 = sCurve(f11);
        float[][] fArr = f1970g3;
        float[] fArr2 = fArr[i11 + i7];
        float f13 = (fArr2[0] * f5) + (fArr2[1] * f8) + (fArr2[2] * f11);
        float[] fArr3 = fArr[i12 + i7];
        float lerp = lerp(sCurve, f13, (fArr3[0] * f6) + (fArr3[1] * f8) + (fArr3[2] * f11));
        float[][] fArr4 = f1970g3;
        float[] fArr5 = fArr4[i13 + i7];
        float[] fArr6 = fArr4[i7 + i14];
        float lerp2 = lerp(sCurve2, lerp, lerp(sCurve, (fArr5[0] * f5) + (fArr5[1] * f9) + (fArr5[2] * f11), (fArr6[0] * f6) + (fArr6[1] * f9) + (f11 * fArr6[2])));
        float[][] fArr7 = f1970g3;
        float[] fArr8 = fArr7[i11 + i8];
        float[] fArr9 = fArr7[i12 + i8];
        float lerp3 = lerp(sCurve, (fArr8[0] * f5) + (fArr8[1] * f8) + (fArr8[2] * f12), (fArr9[0] * f6) + (f8 * fArr9[1]) + (fArr9[2] * f12));
        float[][] fArr10 = f1970g3;
        float[] fArr11 = fArr10[i13 + i8];
        float[] fArr12 = fArr10[i14 + i8];
        return lerp(sCurve3, lerp2, lerp(sCurve2, lerp3, lerp(sCurve, (f5 * fArr11[0]) + (fArr11[1] * f9) + (fArr11[2] * f12), (f6 * fArr12[0]) + (f9 * fArr12[1]) + (f12 * fArr12[2])))) * 1.5f;
    }

    private static void normalize2(float[] fArr) {
        float sqrt = (float) Math.sqrt((fArr[0] * fArr[0]) + (fArr[1] * fArr[1]));
        fArr[0] = fArr[0] / sqrt;
        fArr[1] = fArr[1] / sqrt;
    }

    static void normalize3(float[] fArr) {
        float sqrt = (float) Math.sqrt((fArr[0] * fArr[0]) + (fArr[1] * fArr[1]) + (fArr[2] * fArr[2]));
        fArr[0] = fArr[0] / sqrt;
        fArr[1] = fArr[1] / sqrt;
        fArr[2] = fArr[2] / sqrt;
    }

    private static int random() {
        return randomGenerator.nextInt() & Integer.MAX_VALUE;
    }

    private static void init() {
        for (int i = 0; i < 256; i++) {
            f1971p[i] = i;
            f1968g1[i] = ((random() % 512) - 256) / 256.0f;
            for (int i2 = 0; i2 < 2; i2++) {
                f1969g2[i][i2] = ((random() % 512) - 256) / 256.0f;
            }
            normalize2(f1969g2[i]);
            for (int i3 = 0; i3 < 3; i3++) {
                f1970g3[i][i3] = ((random() % 512) - 256) / 256.0f;
            }
            normalize3(f1970g3[i]);
        }
        for (int i4 = 255; i4 >= 0; i4--) {
            int[] iArr = f1971p;
            int i5 = iArr[i4];
            int random = random() % 256;
            iArr[i4] = iArr[random];
            f1971p[random] = i5;
        }
        for (int i6 = 0; i6 < 258; i6++) {
            int[] iArr2 = f1971p;
            int i7 = i6 + 256;
            iArr2[i7] = iArr2[i6];
            float[] fArr = f1968g1;
            fArr[i7] = fArr[i6];
            for (int i8 = 0; i8 < 2; i8++) {
                float[][] fArr2 = f1969g2;
                fArr2[i7][i8] = fArr2[i6][i8];
            }
            for (int i9 = 0; i9 < 3; i9++) {
                float[][] fArr3 = f1970g3;
                fArr3[i7][i9] = fArr3[i6][i9];
            }
        }
    }

    public static float[] findRange(Function1D function1D, float[] fArr) {
        if (fArr == null) {
            fArr = new float[2];
        }
        float f = 0.0f;
        float f2 = 0.0f;
        for (float f3 = -100.0f; f3 < 100.0f; f3 = (float) (f3 + 1.27139d)) {
            float evaluate = function1D.evaluate(f3);
            f = Math.min(f, evaluate);
            f2 = Math.max(f2, evaluate);
        }
        fArr[0] = f;
        fArr[1] = f2;
        return fArr;
    }

    public static float[] findRange(Function2D function2D, float[] fArr) {
        if (fArr == null) {
            fArr = new float[2];
        }
        float f = 0.0f;
        float f2 = 0.0f;
        for (float f3 = -100.0f; f3 < 100.0f; f3 = (float) (f3 + 10.35173d)) {
            for (float f4 = -100.0f; f4 < 100.0f; f4 = (float) (f4 + 10.77139d)) {
                float evaluate = function2D.evaluate(f4, f3);
                f = Math.min(f, evaluate);
                f2 = Math.max(f2, evaluate);
            }
        }
        fArr[0] = f;
        fArr[1] = f2;
        return fArr;
    }
}
