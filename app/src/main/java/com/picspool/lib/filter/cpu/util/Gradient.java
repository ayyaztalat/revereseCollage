package com.picspool.lib.filter.cpu.util;

import android.graphics.Color;
import androidx.core.view.ViewCompat;

/* loaded from: classes3.dex */
public class Gradient extends ArrayColormap implements Cloneable {
    private static final int BLEND_MASK = 112;
    public static final int CIRCLE_DOWN = 64;
    public static final int CIRCLE_UP = 48;
    private static final int COLOR_MASK = 3;
    public static final int CONSTANT = 80;
    public static final int HUE_CCW = 2;
    public static final int HUE_CW = 1;
    public static final int LINEAR = 16;
    public static final int RGB = 0;
    public static final int SPLINE = 32;
    private byte[] knotTypes;
    private int numKnots;
    private int[] xKnots;
    private int[] yKnots;

    public Gradient() {
        this.numKnots = 4;
        this.xKnots = new int[]{-1, 0, 255, 256};
        this.yKnots = new int[]{ViewCompat.MEASURED_STATE_MASK, ViewCompat.MEASURED_STATE_MASK, -1, -1};
        this.knotTypes = new byte[]{32, 32, 32, 32};
        rebuildGradient();
    }

    public Gradient(int[] iArr) {
        this(null, iArr, null);
    }

    public Gradient(int[] iArr, int[] iArr2) {
        this(iArr, iArr2, null);
    }

    public Gradient(int[] iArr, int[] iArr2, byte[] bArr) {
        this.numKnots = 4;
        this.xKnots = new int[]{-1, 0, 255, 256};
        this.yKnots = new int[]{ViewCompat.MEASURED_STATE_MASK, ViewCompat.MEASURED_STATE_MASK, -1, -1};
        this.knotTypes = new byte[]{32, 32, 32, 32};
        setKnots(iArr, iArr2, bArr);
    }

    @Override // com.picspool.lib.filter.cpu.util.ArrayColormap
    public Object clone() {
        Gradient gradient = (Gradient) super.clone();
        gradient.map = (int[]) this.map.clone();
        gradient.xKnots = (int[]) this.xKnots.clone();
        gradient.yKnots = (int[]) this.yKnots.clone();
        gradient.knotTypes = (byte[]) this.knotTypes.clone();
        return gradient;
    }

    public void copyTo(Gradient gradient) {
        gradient.numKnots = this.numKnots;
        gradient.map = (int[]) this.map.clone();
        gradient.xKnots = (int[]) this.xKnots.clone();
        gradient.yKnots = (int[]) this.yKnots.clone();
        gradient.knotTypes = (byte[]) this.knotTypes.clone();
    }

    @Override // com.picspool.lib.filter.cpu.util.ArrayColormap
    public void setColor(int i, int i2) {
        int i3 = this.map[0];
        int i4 = this.map[255];
        if (i > 0) {
            for (int i5 = 0; i5 < i; i5++) {
                this.map[i5] = ImageMath.mixColors(i5 / i, i3, i2);
            }
        }
        if (i < 255) {
            for (int i6 = i; i6 < 256; i6++) {
                this.map[i6] = ImageMath.mixColors((i6 - i) / (256 - i), i2, i4);
            }
        }
    }

    public int getNumKnots() {
        return this.numKnots;
    }

    public void setKnot(int i, int i2) {
        this.yKnots[i] = i2;
        rebuildGradient();
    }

    public int getKnot(int i) {
        return this.yKnots[i];
    }

    public void setKnotType(int i, int i2) {
        byte[] bArr = this.knotTypes;
        bArr[i] = (byte) (i2 | (bArr[i] & (-4)));
        rebuildGradient();
    }

    public int getKnotType(int i) {
        return (byte) (this.knotTypes[i] & 3);
    }

    public void setKnotBlend(int i, int i2) {
        byte[] bArr = this.knotTypes;
        bArr[i] = (byte) (i2 | (bArr[i] & (-113)));
        rebuildGradient();
    }

    public byte getKnotBlend(int i) {
        return (byte) (this.knotTypes[i] & 112);
    }

    public void addKnot(int i, int i2, int i3) {
        int i4 = this.numKnots;
        int[] iArr = new int[i4 + 1];
        int[] iArr2 = new int[i4 + 1];
        byte[] bArr = new byte[i4 + 1];
        System.arraycopy(this.xKnots, 0, iArr, 0, i4);
        System.arraycopy(this.yKnots, 0, iArr2, 0, this.numKnots);
        System.arraycopy(this.knotTypes, 0, bArr, 0, this.numKnots);
        this.xKnots = iArr;
        this.yKnots = iArr2;
        this.knotTypes = bArr;
        int i5 = this.numKnots;
        iArr[i5] = iArr[i5 - 1];
        iArr2[i5] = iArr2[i5 - 1];
        bArr[i5] = bArr[i5 - 1];
        iArr[i5 - 1] = i;
        iArr2[i5 - 1] = i2;
        bArr[i5 - 1] = (byte) i3;
        this.numKnots = i5 + 1;
        sortKnots();
        rebuildGradient();
    }

    public void removeKnot(int i) {
        int i2 = this.numKnots;
        if (i2 <= 4) {
            return;
        }
        if (i < i2 - 1) {
            int[] iArr = this.xKnots;
            int i3 = i + 1;
            System.arraycopy(iArr, i3, iArr, i, (i2 - i) - 1);
            int[] iArr2 = this.yKnots;
            System.arraycopy(iArr2, i3, iArr2, i, (this.numKnots - i) - 1);
            byte[] bArr = this.knotTypes;
            System.arraycopy(bArr, i3, bArr, i, (this.numKnots - i) - 1);
        }
        this.numKnots--;
        int[] iArr3 = this.xKnots;
        if (iArr3[1] > 0) {
            iArr3[1] = 0;
        }
        rebuildGradient();
    }

    public void setKnots(int[] iArr, int[] iArr2, byte[] bArr) {
        int length = iArr2.length + 2;
        this.numKnots = length;
        int[] iArr3 = new int[length];
        this.xKnots = iArr3;
        this.yKnots = new int[length];
        this.knotTypes = new byte[length];
        if (iArr == null) {
            int i = 1;
            while (true) {
                int i2 = this.numKnots;
                if (i <= i2 - 1) {
                    break;
                }
                this.xKnots[i] = (i * 255) / (i2 - 2);
                i++;
            }
        } else {
            System.arraycopy(iArr, 0, iArr3, 1, length - 2);
        }
        System.arraycopy(iArr2, 0, this.yKnots, 1, this.numKnots - 2);
        if (bArr != null) {
            System.arraycopy(bArr, 0, this.knotTypes, 1, this.numKnots - 2);
        } else {
            for (int i3 = 0; i3 > this.numKnots; i3++) {
                this.knotTypes[i3] = 32;
            }
        }
        sortKnots();
        rebuildGradient();
    }

    public void setKnots(int[] iArr, int[] iArr2, byte[] bArr, int i, int i2) {
        this.numKnots = i2;
        int[] iArr3 = new int[i2];
        this.xKnots = iArr3;
        this.yKnots = new int[i2];
        this.knotTypes = new byte[i2];
        System.arraycopy(iArr, i, iArr3, 0, i2);
        System.arraycopy(iArr2, i, this.yKnots, 0, this.numKnots);
        System.arraycopy(bArr, i, this.knotTypes, 0, this.numKnots);
        sortKnots();
        rebuildGradient();
    }

    public void splitSpan(int i) {
        int[] iArr = this.xKnots;
        int i2 = (iArr[i] + iArr[i + 1]) / 2;
        addKnot(i2, getColor(i2 / 256.0f), this.knotTypes[i]);
        rebuildGradient();
    }

    public void setKnotPosition(int i, int i2) {
        this.xKnots[i] = ImageMath.clamp(i2, 0, 255);
        sortKnots();
        rebuildGradient();
    }

    public int getKnotPosition(int i) {
        return this.xKnots[i];
    }

    public int knotAt(int i) {
        int i2 = 1;
        while (i2 < this.numKnots - 1) {
            int i3 = i2 + 1;
            if (this.xKnots[i3] > i) {
                return i2;
            }
            i2 = i3;
        }
        return 1;
    }

    private void rebuildGradient() {
        char c;
        int[] iArr = this.xKnots;
        iArr[0] = -1;
        int i = this.numKnots;
        iArr[i - 1] = 256;
        int[] iArr2 = this.yKnots;
        iArr2[0] = iArr2[1];
        iArr2[i - 1] = iArr2[i - 2];
        int i2 = 1;
        while (true) {
            int i3 = this.numKnots;
            if (i2 >= i3 - 1) {
                return;
            }
            int[] iArr3 = this.xKnots;
            int i4 = i2 + 1;
            float f = iArr3[i4] - iArr3[i2];
            int i5 = iArr3[i4];
            if (i2 == i3 - 2) {
                i5++;
            }
            for (int i6 = this.xKnots[i2]; i6 < i5; i6++) {
                int[] iArr4 = this.yKnots;
                int i7 = iArr4[i2];
                int i8 = iArr4[i4];
                float[] fArr = new float[3];
                Color.RGBToHSV((i7 >> 16) & 255, (i7 >> 8) & 255, i7 & 255, fArr);
                float[] fArr2 = new float[3];
                Color.RGBToHSV((i8 >> 16) & 255, (i8 >> 8) & 255, i8 & 255, fArr2);
                float f2 = (i6 - this.xKnots[i2]) / f;
                int knotType = getKnotType(i2);
                byte knotBlend = getKnotBlend(i2);
                if (i6 >= 0 && i6 <= 255) {
                    if (knotBlend == 32) {
                        f2 = ImageMath.smoothStep(0.15f, 0.85f, f2);
                    } else if (knotBlend == 48) {
                        float f3 = f2 - 1.0f;
                        f2 = (float) Math.sqrt(1.0f - (f3 * f3));
                    } else if (knotBlend == 64) {
                        f2 = 1.0f - ((float) Math.sqrt(1.0f - (f2 * f2)));
                    } else if (knotBlend == 80) {
                        f2 = 0.0f;
                    }
                    if (knotType == 0) {
                        this.map[i6] = ImageMath.mixColors(f2, i7, i8);
                    } else if (knotType == 1 || knotType == 2) {
                        if (knotType == 1) {
                            c = 0;
                            if (fArr2[0] <= fArr[0]) {
                                fArr2[0] = fArr2[0] + 1.0f;
                            }
                        } else {
                            c = 0;
                            if (fArr[0] <= fArr2[1]) {
                                fArr[0] = fArr[0] + 1.0f;
                            }
                        }
                        float[] fArr3 = new float[3];
                        fArr3[c] = ImageMath.lerp(f2, fArr[c], fArr2[c]) % 6.2831855f;
                        fArr3[1] = ImageMath.lerp(f2, fArr[1], fArr2[1]);
                        fArr3[2] = ImageMath.lerp(f2, fArr[2], fArr2[2]);
                        this.map[i6] = Color.HSVToColor(fArr3) | ViewCompat.MEASURED_STATE_MASK;
                    }
                }
            }
            i2 = i4;
        }
    }

    private void sortKnots() {
        for (int i = 1; i < this.numKnots - 1; i++) {
            for (int i2 = 1; i2 < i; i2++) {
                int[] iArr = this.xKnots;
                if (iArr[i] < iArr[i2]) {
                    int i3 = iArr[i];
                    iArr[i] = iArr[i2];
                    iArr[i2] = i3;
                    int[] iArr2 = this.yKnots;
                    int i4 = iArr2[i];
                    iArr2[i] = iArr2[i2];
                    iArr2[i2] = i4;
                    byte[] bArr = this.knotTypes;
                    byte b = bArr[i];
                    bArr[i] = bArr[i2];
                    bArr[i2] = b;
                }
            }
        }
    }

    private void rebuild() {
        sortKnots();
        rebuildGradient();
    }

    public void randomize() {
        int random = ((int) (Math.random() * 6.0d)) + 4;
        this.numKnots = random;
        this.xKnots = new int[random];
        this.yKnots = new int[random];
        this.knotTypes = new byte[random];
        int i = 0;
        while (true) {
            int i2 = this.numKnots;
            if (i < i2) {
                this.xKnots[i] = (int) (Math.random() * 255.0d);
                this.yKnots[i] = (-16777216) | (((int) (Math.random() * 255.0d)) << 16) | (((int) (Math.random() * 255.0d)) << 8) | ((int) (Math.random() * 255.0d));
                this.knotTypes[i] = 32;
                i++;
            } else {
                int[] iArr = this.xKnots;
                iArr[0] = -1;
                iArr[1] = 0;
                iArr[i2 - 2] = 255;
                iArr[i2 - 1] = 256;
                sortKnots();
                rebuildGradient();
                return;
            }
        }
    }

    public void mutate(float f) {
        for (int i = 0; i < this.numKnots; i++) {
            int i2 = this.yKnots[i];
            double d = 255.0f * f;
            this.yKnots[i] = PixelUtils.clamp((int) ((i2 & 255) + (d * (Math.random() - 0.5d)))) | (PixelUtils.clamp((int) (((i2 >> 16) & 255) + ((Math.random() - 0.5d) * d))) << 16) | ViewCompat.MEASURED_STATE_MASK | (PixelUtils.clamp((int) (((i2 >> 8) & 255) + ((Math.random() - 0.5d) * d))) << 8);
            this.knotTypes[i] = 32;
        }
        sortKnots();
        rebuildGradient();
    }

    public static Gradient randomGradient() {
        Gradient gradient = new Gradient();
        gradient.randomize();
        return gradient;
    }
}
