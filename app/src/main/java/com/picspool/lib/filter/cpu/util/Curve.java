package com.picspool.lib.filter.cpu.util;

/* loaded from: classes3.dex */
public class Curve {

    /* renamed from: x */
    public float[] f1959x;

    /* renamed from: y */
    public float[] f1960y;

    public Curve() {
        this.f1959x = new float[]{0.0f, 1.0f};
        this.f1960y = new float[]{0.0f, 1.0f};
    }

    public Curve(Curve curve) {
        this.f1959x = (float[]) curve.f1959x.clone();
        this.f1960y = (float[]) curve.f1960y.clone();
    }

    public int addKnot(float f, float f2) {
        int length = this.f1959x.length;
        int i = length + 1;
        float[] fArr = new float[i];
        float[] fArr2 = new float[i];
        int i2 = 0;
        int i3 = -1;
        for (int i4 = 0; i4 < length; i4++) {
            if (i3 == -1 && this.f1959x[i4] > f) {
                fArr[i2] = f;
                fArr2[i2] = f2;
                i3 = i2;
                i2++;
            }
            fArr[i2] = this.f1959x[i4];
            fArr2[i2] = this.f1960y[i4];
            i2++;
        }
        if (i3 == -1) {
            fArr[i2] = f;
            fArr2[i2] = f2;
        } else {
            i2 = i3;
        }
        this.f1959x = fArr;
        this.f1960y = fArr2;
        return i2;
    }

    public void removeKnot(int i) {
        int length = this.f1959x.length;
        if (length <= 2) {
            return;
        }
        int i2 = length - 1;
        float[] fArr = new float[i2];
        float[] fArr2 = new float[i2];
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            if (i4 == i) {
                i3++;
            }
            fArr[i4] = this.f1959x[i3];
            fArr2[i4] = this.f1960y[i3];
            i3++;
        }
        this.f1959x = fArr;
        this.f1960y = fArr2;
    }

    private void sortKnots() {
        int length = this.f1959x.length;
        for (int i = 1; i < length - 1; i++) {
            for (int i2 = 1; i2 < i; i2++) {
                float[] fArr = this.f1959x;
                if (fArr[i] < fArr[i2]) {
                    float f = fArr[i];
                    fArr[i] = fArr[i2];
                    fArr[i2] = f;
                    float[] fArr2 = this.f1960y;
                    float f2 = fArr2[i];
                    fArr2[i] = fArr2[i2];
                    fArr2[i2] = f2;
                }
            }
        }
    }

    public int[] makeTable() {
        float[] fArr = this.f1959x;
        int length = fArr.length;
        int i = length + 2;
        float[] fArr2 = new float[i];
        float[] fArr3 = new float[i];
        System.arraycopy(fArr, 0, fArr2, 1, length);
        System.arraycopy(this.f1960y, 0, fArr3, 1, length);
        fArr2[0] = fArr2[1];
        fArr3[0] = fArr3[1];
        int i2 = length + 1;
        fArr2[i2] = fArr2[length];
        fArr3[i2] = fArr3[length];
        int[] iArr = new int[256];
        for (int i3 = 0; i3 < 1024; i3++) {
            float f = i3 / 1024.0f;
            iArr[ImageMath.clamp((int) ((ImageMath.spline(f, i, fArr2) * 255.0f) + 0.5f), 0, 255)] = ImageMath.clamp((int) ((ImageMath.spline(f, i, fArr3) * 255.0f) + 0.5f), 0, 255);
        }
        return iArr;
    }
}
