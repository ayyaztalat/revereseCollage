package com.picspool.lib.filter.cpu.normal;

import android.graphics.Rect;
import com.picspool.lib.filter.cpu.father.WholeImageFilter;
import com.picspool.lib.filter.cpu.util.OctTreeQuantizer;
import com.picspool.lib.filter.cpu.util.PixelUtils;

/* loaded from: classes3.dex */
public class QuantizeFilter extends WholeImageFilter {
    protected static final int[] matrix = {0, 0, 0, 0, 0, 7, 3, 5, 1};
    private boolean dither;
    private int sum = 16;
    private int numColors = 256;
    private boolean serpentine = true;

    public String toString() {
        return "Colors/Quantize...";
    }

    public void setNumColors(int i) {
        this.numColors = Math.min(Math.max(i, 8), 256);
    }

    public int getNumColors() {
        return this.numColors;
    }

    public void setDither(boolean z) {
        this.dither = z;
    }

    public boolean getDither() {
        return this.dither;
    }

    public void setSerpentine(boolean z) {
        this.serpentine = z;
    }

    public boolean getSerpentine() {
        return this.serpentine;
    }

    public void quantize(int[] iArr, int[] iArr2, int i, int i2, int i3, boolean z, boolean z2) {
        int i4;
        int i5;
        OctTreeQuantizer octTreeQuantizer;
        int[] iArr3;
        boolean z3;
        int i6;
        int i7 = i;
        int i8 = i2;
        int i9 = i7 * i8;
        OctTreeQuantizer octTreeQuantizer2 = new OctTreeQuantizer();
        octTreeQuantizer2.setup(i3);
        octTreeQuantizer2.addPixels(iArr, 0, i9);
        int[] buildColorTable = octTreeQuantizer2.buildColorTable();
        if (z) {
            int i10 = 0;
            while (i10 < i8) {
                boolean z4 = z2 && (i10 & 1) == 1;
                if (z4) {
                    i4 = ((i10 * i7) + i7) - 1;
                    i5 = -1;
                } else {
                    i4 = i10 * i7;
                    i5 = 1;
                }
                int i11 = 0;
                while (i11 < i7) {
                    int i12 = iArr[i4];
                    int i13 = buildColorTable[octTreeQuantizer2.getIndexForColor(i12)];
                    iArr2[i4] = i13;
                    int i14 = ((i12 >> 16) & 255) - ((i13 >> 16) & 255);
                    int i15 = ((i12 >> 8) & 255) - ((i13 >> 8) & 255);
                    int i16 = (i12 & 255) - (i13 & 255);
                    int i17 = -1;
                    while (true) {
                        if (i17 <= 1) {
                            int i18 = i17 + i10;
                            if (i18 >= 0 && i18 < i8) {
                                int i19 = -1;
                                for (int i20 = 1; i19 <= i20; i20 = 1) {
                                    int i21 = i19 + i11;
                                    if (i21 < 0 || i21 >= i7) {
                                        octTreeQuantizer = octTreeQuantizer2;
                                        iArr3 = buildColorTable;
                                        z3 = z4;
                                    } else {
                                        if (z4) {
                                            i6 = matrix[(((i17 + 1) * 3) - i19) + 1];
                                        } else {
                                            i6 = matrix[((i17 + 1) * 3) + i19 + 1];
                                        }
                                        if (i6 != 0) {
                                            int i22 = z4 ? i4 - i19 : i4 + i19;
                                            int i23 = iArr[i22];
                                            octTreeQuantizer = octTreeQuantizer2;
                                            iArr3 = buildColorTable;
                                            z3 = z4;
                                            int i24 = this.sum;
                                            iArr[i22] = PixelUtils.clamp((i23 & 255) + ((i6 * i16) / i24)) | (PixelUtils.clamp(((i23 >> 16) & 255) + ((i14 * i6) / i24)) << 16) | (PixelUtils.clamp(((i23 >> 8) & 255) + ((i15 * i6) / i24)) << 8);
                                            i19++;
                                            i7 = i;
                                            octTreeQuantizer2 = octTreeQuantizer;
                                            buildColorTable = iArr3;
                                            z4 = z3;
                                        } else {
                                            octTreeQuantizer = octTreeQuantizer2;
                                            iArr3 = buildColorTable;
                                            z3 = z4;
                                        }
                                    }
                                    i19++;
                                    i7 = i;
                                    octTreeQuantizer2 = octTreeQuantizer;
                                    buildColorTable = iArr3;
                                    z4 = z3;
                                }
                            }
                            i17++;
                            i7 = i;
                            i8 = i2;
                            octTreeQuantizer2 = octTreeQuantizer2;
                            buildColorTable = buildColorTable;
                            z4 = z4;
                        }
                    }
//                    i4 += i5;
//                    i11++;
//                    i7 = i;
//                    i8 = i2;
//                    buildColorTable = buildColorTable;
                }
                i10++;
                i7 = i;
                i8 = i2;
                buildColorTable = buildColorTable;
            }
        } else {
            for (int i25 = 0; i25 < i9; i25++) {
                iArr2[i25] = buildColorTable[octTreeQuantizer2.getIndexForColor(iArr[i25])];
            }
        }
    }

    @Override // com.picspool.lib.filter.cpu.father.WholeImageFilter
    protected int[] filterPixels(int i, int i2, int[] iArr, Rect rect) {
        int[] iArr2 = new int[i * i2];
        quantize(iArr, iArr2, i, i2, this.numColors, this.dither, this.serpentine);
        return iArr2;
    }
}
