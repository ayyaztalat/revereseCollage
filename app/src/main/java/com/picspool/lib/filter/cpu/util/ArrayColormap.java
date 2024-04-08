package com.picspool.lib.filter.cpu.util;

/* loaded from: classes3.dex */
public class ArrayColormap implements Colormap, Cloneable {
    protected int[] map;

    public ArrayColormap() {
        this.map = new int[256];
    }

    public ArrayColormap(int[] iArr) {
        this.map = iArr;
    }

    public Object clone() {
        try {
            ArrayColormap arrayColormap = (ArrayColormap) super.clone();
            arrayColormap.map = (int[]) this.map.clone();
            return arrayColormap;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public void setMap(int[] iArr) {
        this.map = iArr;
    }

    public int[] getMap() {
        return this.map;
    }

    @Override // com.picspool.lib.filter.cpu.util.Colormap
    public int getColor(float f) {
        int i = (int) (f * 255.0f);
        if (i < 0) {
            i = 0;
        } else if (i > 255) {
            i = 255;
        }
        return this.map[i];
    }

    public void setColorInterpolated(int i, int i2, int i3, int i4) {
        int[] iArr = this.map;
        int i5 = iArr[i2];
        int i6 = iArr[i3];
        for (int i7 = i2; i7 <= i; i7++) {
            this.map[i7] = ImageMath.mixColors((i7 - i2) / (i - i2), i5, i4);
        }
        for (int i8 = i; i8 < i3; i8++) {
            this.map[i8] = ImageMath.mixColors((i8 - i) / (i3 - i), i4, i6);
        }
    }

    public void setColorRange(int i, int i2, int i3, int i4) {
        for (int i5 = i; i5 <= i2; i5++) {
            this.map[i5] = ImageMath.mixColors((i5 - i) / (i2 - i), i3, i4);
        }
    }

    public void setColorRange(int i, int i2, int i3) {
        while (i <= i2) {
            this.map[i] = i3;
            i++;
        }
    }

    public void setColor(int i, int i2) {
        this.map[i] = i2;
    }
}
