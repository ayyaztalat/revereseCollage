package com.picspool.lib.filter.cpu.normal;

import androidx.core.view.ViewCompat;
import com.picspool.lib.filter.cpu.util.ImageMath;

/* loaded from: classes3.dex */
public class ColorHalftoneFilter {
    private float dotRadius = 2.0f;
    private float cyanScreenAngle = (float) Math.toRadians(108.0d);
    private float magentaScreenAngle = (float) Math.toRadians(162.0d);
    private float yellowScreenAngle = (float) Math.toRadians(90.0d);

    public String toString() {
        return "Pixellate/Color Halftone...";
    }

    public void setdotRadius(float f) {
        this.dotRadius = f;
    }

    public float getdotRadius() {
        return this.dotRadius;
    }

    public float getCyanScreenAngle() {
        return this.cyanScreenAngle;
    }

    public void setCyanScreenAngle(float f) {
        this.cyanScreenAngle = f;
    }

    public float getMagentaScreenAngle() {
        return this.magentaScreenAngle;
    }

    public void setMagentaScreenAngle(float f) {
        this.magentaScreenAngle = f;
    }

    public float getYellowScreenAngle() {
        return this.yellowScreenAngle;
    }

    public void setYellowScreenAngle(float f) {
        this.yellowScreenAngle = f;
    }

    public int[] filter(int[] iArr, int i, int i2) {
        int i3;
        float f = this.dotRadius * 2.0f * 1.414f;
        int i4 = 3;
        int i5 = 0;
        float[] fArr = {this.cyanScreenAngle, this.magentaScreenAngle, this.yellowScreenAngle};
        float[] fArr2 = {0.0f, -1.0f, 1.0f, 0.0f, 0.0f};
        float[] fArr3 = {0.0f, 0.0f, 0.0f, -1.0f, 1.0f};
        float f2 = f / 2.0f;
        int[] iArr2 = new int[i];
        int[] iArr3 = new int[i * i2];
        int i6 = 0;
        while (i6 < i2) {
            int i7 = i6 * i;
            int i8 = i7;
            while (i5 < i) {
                iArr2[i5] = (iArr[i8] & ViewCompat.MEASURED_STATE_MASK) | ViewCompat.MEASURED_SIZE_MASK;
                i5++;
                i8++;
            }
            int i9 = 0;
            while (i9 < i4) {
                int i10 = 16 - (i9 * 8);
                int i11 = 255 << i10;
                int i12 = i9;
                double d = fArr[i9];
                float[] fArr4 = fArr;
                float sin = (float) Math.sin(d);
                float cos = (float) Math.cos(d);
                int i13 = 0;
                while (i13 < i) {
                    float f3 = i13;
                    float f4 = i6;
                    float f5 = (f3 * cos) + (f4 * sin);
                    int i14 = i6;
                    float f6 = ((-i13) * sin) + (f4 * cos);
                    int[] iArr4 = iArr3;
                    float mod = (f5 - ImageMath.mod(f5 - f2, f)) + f2;
                    float mod2 = (f6 - ImageMath.mod(f6 - f2, f)) + f2;
                    int i15 = i7;
                    int i16 = 0;
                    float f7 = 1.0f;
                    while (true) {
                        i3 = i13;
                        if (i16 < 5) {
                            float f8 = mod + (fArr2[i16] * f);
                            float f9 = mod2 + (fArr3[i16] * f);
                            float f10 = (f8 * cos) - (f9 * sin);
                            float f11 = f;
                            float f12 = (f8 * sin) + (f9 * cos);
                            float f13 = sin;
                            float f14 = cos;
                            float f15 = ((iArr[(ImageMath.clamp((int) f12, 0, i2 - 1) * i) + ImageMath.clamp((int) f10, 0, i - 1)] >> i10) & 255) / 255.0f;
                            float f16 = (float) ((1.0f - (f15 * f15)) * f2 * 1.414d);
                            float f17 = f3 - f10;
                            float f18 = f4 - f12;
                            float sqrt = (float) Math.sqrt((f17 * f17) + (f18 * f18));
                            f7 = Math.min(f7, 1.0f - ImageMath.smoothStep(sqrt, sqrt + 1.0f, f16));
                            i16++;
                            fArr3 = fArr3;
                            sin = f13;
                            i13 = i3;
                            cos = f14;
                            f = f11;
                            fArr2 = fArr2;
                        }
                    }
//                    iArr2[i3] = (((((int) (f7 * 255.0f)) << i10) ^ (~i11)) | ViewCompat.MEASURED_STATE_MASK) & iArr2[i3];
//                    i13 = i3 + 1;
//                    i6 = i14;
//                    iArr3 = iArr4;
//                    i7 = i15;
//                    f = f;
                }
                i9 = i12 + 1;
                fArr = fArr4;
                i4 = 3;
            }
            float f19 = f;
            float[] fArr5 = fArr;
            float[] fArr6 = fArr2;
            float[] fArr7 = fArr3;
            int[] iArr5 = iArr3;
            int i17 = i6;
            int i18 = i7;
            int i19 = 0;
            for (int i20 = i18; i20 < i18 + i; i20++) {
                iArr5[i20] = iArr2[i19];
                i19++;
            }
            i6 = i17 + 1;
            fArr3 = fArr7;
            fArr = fArr5;
            iArr3 = iArr5;
            f = f19;
            fArr2 = fArr6;
            i4 = 3;
            i5 = 0;
        }
        return iArr3;
    }
}
