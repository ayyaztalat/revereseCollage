package com.picspool.lib.filter.cpu.father;

/* loaded from: classes3.dex */
public abstract class PointFilter {
    protected boolean canFilterIndexColorModel = false;
    private int height;
    private int width;

    public abstract int filterRGB(int i, int i2, int i3);

    public int[] filter(int[] iArr, int i, int i2) {
        int i3;
        this.width = i;
        this.height = i2;
        setDimensions(i, i2);
        int i4 = this.width;
        int[] iArr2 = new int[i4];
        int[] iArr3 = new int[i4 * this.height];
        for (int i5 = 0; i5 < this.height; i5++) {
            int i6 = this.width * i5;
            int i7 = 0;
            while (true) {
                int i8 = this.width;
                if (i6 >= (i5 * i8) + i8) {
                    break;
                }
                iArr2[i7] = iArr[i6];
                i7++;
                i6++;
            }
            int i9 = 0;
            while (true) {
                i3 = this.width;
                if (i9 >= i3) {
                    break;
                }
                iArr2[i9] = filterRGB(i9, i5, iArr2[i9]);
                i9++;
            }
            int i10 = i3 * i5;
            int i11 = 0;
            while (true) {
                int i12 = this.width;
                if (i10 < (i5 * i12) + i12) {
                    iArr3[i10] = iArr2[i11];
                    i11++;
                    i10++;
                }
            }
        }
        return iArr3;
    }

    public void setDimensions(int i, int i2) {
        this.width = i;
        this.height = i2;
    }
}
