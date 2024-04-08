package com.picspool.lib.filter.cpu.father;

import android.graphics.Rect;
import android.util.Log;
import androidx.core.view.ViewCompat;
import com.picspool.lib.filter.cpu.util.ImageMath;
import com.picspool.lib.filter.cpu.util.PixelUtils;

/* loaded from: classes3.dex */
public abstract class TransformFilter {
    public static final int BILINEAR = 1;
    public static final int CLAMP = 1;
    public static final int NEAREST_NEIGHBOUR = 0;
    public static final int RGB_CLAMP = 3;
    public static final int WRAP = 2;
    public static final int ZERO = 0;
    protected int edgeAction = 3;
    protected int interpolation = 1;
    protected Rect originalSpace;
    protected Rect transformedSpace;

    protected abstract void transformInverse(int i, int i2, float[] fArr);

    protected void transformSpace(Rect rect) {
    }

    public void setEdgeAction(int i) {
        this.edgeAction = i;
    }

    public int getEdgeAction() {
        return this.edgeAction;
    }

    public void setInterpolation(int i) {
        this.interpolation = i;
    }

    public int getInterpolation() {
        return this.interpolation;
    }

    public int[] filter(int[] iArr, int i, int i2) {
        int i3;
        float[] fArr;
        int i4;
        int i5;
        int pixel;
        int pixel2;
        int i6;
        int i7;
        TransformFilter transformFilter = this;
        Log.d("DEBUG", "width = " + i + "  height = " + i2);
        char c = 0;
        transformFilter.originalSpace = new Rect(0, 0, i, i2);
        Rect rect = new Rect(0, 0, i, i2);
        transformFilter.transformedSpace = rect;
        transformFilter.transformSpace(rect);
        int[] iArr2 = new int[i * i2];
        if (transformFilter.interpolation == 0) {
            return filterPixelsNN(iArr2, i, i2, iArr, transformFilter.transformedSpace);
        }
        int i8 = i - 1;
        int i9 = i2 - 1;
        int i10 = transformFilter.transformedSpace.right;
        int i11 = transformFilter.transformedSpace.bottom;
        int[] iArr3 = new int[i10];
        int i12 = transformFilter.transformedSpace.left;
        int i13 = transformFilter.transformedSpace.top;
        float[] fArr2 = new float[2];
        int i14 = 0;
        while (i14 < i11) {
            int i15 = 0;
            while (i15 < i10) {
                int i16 = i15;
                transformFilter.transformInverse(i12 + i15, i13 + i14, fArr2);
                int floor = (int) Math.floor(fArr2[c]);
                int floor2 = (int) Math.floor(fArr2[1]);
                int i17 = i14;
                float f = fArr2[0] - floor;
                float f2 = fArr2[1] - floor2;
                if (floor >= 0 && floor < i8 && floor2 >= 0 && floor2 < i9) {
                    int i18 = (i * floor2) + floor;
                    int i19 = iArr[i18];
                    int i20 = iArr[i18 + 1];
                    int i21 = i18 + i;
                    int i22 = iArr[i21];
                    pixel2 = iArr[i21 + 1];
                    pixel = i19;
                    i6 = i20;
                    i4 = i13;
                    i5 = i12;
                    i7 = i22;
                    i3 = i17;
                    fArr = fArr2;
                } else {
                    i3 = i17;
                    fArr = fArr2;
                    i4 = i13;
                    i5 = i12;
                    pixel = getPixel(iArr, floor, floor2, i, i2);
                    int i23 = floor + 1;
                    int pixel3 = getPixel(iArr, i23, floor2, i, i2);
                    int i24 = floor2 + 1;
                    int pixel4 = getPixel(iArr, floor, i24, i, i2);
                    pixel2 = getPixel(iArr, i23, i24, i, i2);
                    i6 = pixel3;
                    i7 = pixel4;
                }
                iArr3[i16] = ImageMath.bilinearInterpolate(f, f2, pixel, i6, i7, pixel2);
                i15 = i16 + 1;
                c = 0;
                i14 = i3;
                fArr2 = fArr;
                i13 = i4;
                i12 = i5;
                transformFilter = this;
            }
            int i25 = i14;
            float[] fArr3 = fArr2;
            int i26 = i13;
            int i27 = i12;
            if (i25 < i2) {
                PixelUtils.setLineRGB(iArr2, i25, i, iArr3);
            }
            i14 = i25 + 1;
            c = 0;
            transformFilter = this;
            fArr2 = fArr3;
            i13 = i26;
            i12 = i27;
        }
        return iArr2;
    }

    private final int getPixel(int[] iArr, int i, int i2, int i3, int i4) {
        if (i < 0 || i >= i3 || i2 < 0 || i2 >= i4) {
            int i5 = this.edgeAction;
            if (i5 != 1) {
                if (i5 != 2) {
                    if (i5 != 3) {
                        return 0;
                    }
                    return iArr[(ImageMath.clamp(i2, 0, i4 - 1) * i3) + ImageMath.clamp(i, 0, i3 - 1)] & ViewCompat.MEASURED_SIZE_MASK;
                }
                return iArr[(ImageMath.mod(i2, i4) * i3) + ImageMath.mod(i, i3)];
            }
            return iArr[(ImageMath.clamp(i2, 0, i4 - 1) * i3) + ImageMath.clamp(i, 0, i3 - 1)];
        }
        return iArr[(i2 * i3) + i];
    }

    protected int[] filterPixelsNN(int[] iArr, int i, int i2, int[] iArr2, Rect rect) {
        int i3;
        int i4 = 0;
        int i5 = rect.right;
        int i6 = rect.bottom;
        int[] iArr3 = new int[i5];
        int i7 = rect.left;
        int i8 = rect.top;
        float[] fArr = new float[2];
        char c = 0;
        int i9 = 0;
        while (i9 < i6) {
            int i10 = 0;
            while (i10 < i5) {
                transformInverse(i7 + i10, i8 + i9, fArr);
                int i11 = (int) fArr[c];
                int i12 = (int) fArr[1];
                if (fArr[c] < 0.0f || i11 >= i || fArr[1] < 0.0f || i12 >= i2) {
                    int i13 = this.edgeAction;
                    if (i13 != 1) {
                        if (i13 == 2) {
                            i4 = iArr2[(ImageMath.mod(i12, i2) * i) + ImageMath.mod(i11, i)];
                        } else if (i13 != 3) {
                            i3 = 0;
                        } else {
                            i4 = iArr2[(ImageMath.clamp(i12, 0, i2 - 1) * i) + ImageMath.clamp(i11, 0, i - 1)] & ViewCompat.MEASURED_SIZE_MASK;
                        }
                        i3 = i4;
                    } else {
                        i3 = iArr2[(ImageMath.clamp(i12, 0, i2 - 1) * i) + ImageMath.clamp(i11, 0, i - 1)];
                    }
                    iArr3[i10] = i3;
                } else {
                    int i14 = (i12 * i) + i11;
                    int i15 = iArr2[i14];
                    iArr3[i10] = iArr2[i14];
                }
                i10++;
                c = 0;
            }
            if (i9 < i2) {
                PixelUtils.setLineRGB(iArr, i9, i, iArr3);
            }
            i9++;
            c = 0;
        }
        return iArr;
    }
}
