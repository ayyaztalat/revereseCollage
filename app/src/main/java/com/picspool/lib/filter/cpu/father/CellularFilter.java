package com.picspool.lib.filter.cpu.father;

import android.graphics.Rect;
import androidx.core.view.ViewCompat;
import java.util.Random;
import com.picspool.lib.filter.cpu.util.Colormap;
import com.picspool.lib.filter.cpu.util.Function2D;
import com.picspool.lib.filter.cpu.util.Gradient;
import com.picspool.lib.filter.cpu.util.ImageMath;
import com.picspool.lib.filter.cpu.util.PixelUtils;

/* loaded from: classes3.dex */
public class CellularFilter extends WholeImageFilter implements Function2D, Cloneable {
    public static final int HEXAGONAL = 2;
    public static final int OCTAGONAL = 3;
    public static final int RANDOM = 0;
    public static final int SQUARE = 1;
    public static final int TRIANGULAR = 4;
    private static byte[] probabilities;
    protected float angleCoefficient;
    private float gradientCoefficient;
    private float max;
    private float min;
    protected Point[] results;
    protected float scale = 32.0f;
    protected float stretch = 1.0f;
    protected float angle = 0.0f;
    public float amount = 1.0f;
    public float turbulence = 1.0f;
    public float gain = 0.5f;
    public float bias = 0.5f;
    public float distancePower = 2.0f;
    public boolean useColor = false;
    protected Colormap colormap = new Gradient();
    protected float[] coefficients = {1.0f, 0.0f, 0.0f, 0.0f};
    protected Random random = new Random();
    protected float m00 = 1.0f;
    protected float m01 = 0.0f;
    protected float m10 = 0.0f;
    protected float m11 = 1.0f;
    protected float randomness = 0.0f;
    protected int gridType = 2;

    public String toString() {
        return "Texture/Cellular...";
    }

    public CellularFilter() {
        float f = 1.0f;
        float f2 = 0.0f;
        int i = 0;
        this.results = null;
        this.results = new Point[3];
        int i2 = 0;
        while (true) {
            Point[] pointArr = this.results;
            if (i2 >= pointArr.length) {
                break;
            }
            pointArr[i2] = new Point();
            i2++;
        }
        if (probabilities == null) {
            probabilities = new byte[8192];
            while (i < 10) {
                f = i > 1 ? f * i : f;
                f2 += (((float) Math.pow(2.5f, i)) * ((float) Math.exp(-2.5f))) / f;
                int i3 = (int) (8192.0f * f2);
                for (int i4 = (int) (f2 * 8192.0f); i4 < i3; i4++) {
                    probabilities[i4] = (byte) i;
                }
                i++;
            }
        }
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public float getScale() {
        return this.scale;
    }

    public void setStretch(float f) {
        this.stretch = f;
    }

    public float getStretch() {
        return this.stretch;
    }

    public void setAngle(float f) {
        this.angle = f;
        double d = f;
        float cos = (float) Math.cos(d);
        float sin = (float) Math.sin(d);
        this.m00 = cos;
        this.m01 = sin;
        this.m10 = -sin;
        this.m11 = cos;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setCoefficient(int i, float f) {
        this.coefficients[i] = f;
    }

    public float getCoefficient(int i) {
        return this.coefficients[i];
    }

    public void setAngleCoefficient(float f) {
        this.angleCoefficient = f;
    }

    public float getAngleCoefficient() {
        return this.angleCoefficient;
    }

    public void setGradientCoefficient(float f) {
        this.gradientCoefficient = f;
    }

    public float getGradientCoefficient() {
        return this.gradientCoefficient;
    }

    public void setF1(float f) {
        this.coefficients[0] = f;
    }

    public float getF1() {
        return this.coefficients[0];
    }

    public void setF2(float f) {
        this.coefficients[1] = f;
    }

    public float getF2() {
        return this.coefficients[1];
    }

    public void setF3(float f) {
        this.coefficients[2] = f;
    }

    public float getF3() {
        return this.coefficients[2];
    }

    public void setF4(float f) {
        this.coefficients[3] = f;
    }

    public float getF4() {
        return this.coefficients[3];
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    public void setRandomness(float f) {
        this.randomness = f;
    }

    public float getRandomness() {
        return this.randomness;
    }

    public void setGridType(int i) {
        this.gridType = i;
    }

    public int getGridType() {
        return this.gridType;
    }

    public void setDistancePower(float f) {
        this.distancePower = f;
    }

    public float getDistancePower() {
        return this.distancePower;
    }

    public void setTurbulence(float f) {
        this.turbulence = f;
    }

    public float getTurbulence() {
        return this.turbulence;
    }

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    /* loaded from: classes3.dex */
    public class Point {
        public float cubeX;
        public float cubeY;
        public float distance;

        /* renamed from: dx */
        public float f1955dx;

        /* renamed from: dy */
        public float f1956dy;
        public int index;

        /* renamed from: x */
        public float f1957x;

        /* renamed from: y */
        public float f1958y;

        public Point() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01c5  */
    /* JADX WARN: Type inference failed for: r4v11, types: [int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float checkCube(float r22, float r23, int r24, int r25, com.picspool.lib.filter.cpu.father.CellularFilter.Point[] r26) {
        /*
            Method dump skipped, instructions count: 526
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.filter.cpu.father.CellularFilter.checkCube(float, float, int, int, com.picspool.lib.filter.cpu.father.CellularFilter$Point[]):float");
    }

    @Override // com.picspool.lib.filter.cpu.util.Function2D
    public float evaluate(float f, float f2) {
        Point[] pointArr;
        int i = 0;
        while (true) {
            pointArr = this.results;
            if (i >= pointArr.length) {
                break;
            }
            pointArr[i].distance = Float.POSITIVE_INFINITY;
            i++;
        }
        int i2 = (int) f;
        int i3 = (int) f2;
        float f3 = f - i2;
        float f4 = f2 - i3;
        float checkCube = checkCube(f3, f4, i2, i3, pointArr);
        if (checkCube > f4) {
            checkCube = checkCube(f3, f4 + 1.0f, i2, i3 - 1, this.results);
        }
        float f5 = 1.0f - f4;
        if (checkCube > f5) {
            checkCube = checkCube(f3, f4 - 1.0f, i2, i3 + 1, this.results);
        }
        float f6 = checkCube;
        if (f6 > f3) {
            float f7 = f3 + 1.0f;
            int i4 = i2 - 1;
            checkCube(f7, f4, i4, i3, this.results);
            if (f6 > f4) {
                f6 = checkCube(f7, f4 + 1.0f, i4, i3 - 1, this.results);
            }
            if (f6 > f5) {
                f6 = checkCube(f7, f4 - 1.0f, i4, i3 + 1, this.results);
            }
        }
        if (f6 > 1.0f - f3) {
            float f8 = f3 - 1.0f;
            int i5 = i2 + 1;
            float checkCube2 = checkCube(f8, f4, i5, i3, this.results);
            if (checkCube2 > f4) {
                checkCube2 = checkCube(f8, f4 + 1.0f, i5, i3 - 1, this.results);
            }
            if (checkCube2 > f5) {
                checkCube(f8, f4 - 1.0f, i5, i3 + 1, this.results);
            }
        }
        float f9 = 0.0f;
        for (int i6 = 0; i6 < 3; i6++) {
            f9 += this.coefficients[i6] * this.results[i6].distance;
        }
        if (this.angleCoefficient != 0.0f) {
            float atan2 = (float) Math.atan2(f2 - this.results[0].f1958y, f - this.results[0].f1957x);
            if (atan2 < 0.0f) {
                atan2 += 6.2831855f;
            }
            f9 += this.angleCoefficient * (atan2 / 12.566371f);
        }
        if (this.gradientCoefficient != 0.0f) {
            return f9 + (this.gradientCoefficient * (1.0f / (this.results[0].f1956dy + this.results[0].f1955dx)));
        }
        return f9;
    }

    public float turbulence2(float f, float f2, float f3) {
        float f4 = 0.0f;
        for (float f5 = 1.0f; f5 <= f3; f5 *= 2.0f) {
            f4 += evaluate(f5 * f, f5 * f2) / f5;
        }
        return f4;
    }

    public int getPixel(int i, int i2, int[] iArr, int i3, int i4) {
        float f = i;
        float f2 = i2;
        float f3 = (this.m00 * f) + (this.m01 * f2);
        float f4 = (this.m10 * f) + (this.m11 * f2);
        float f5 = this.scale;
        float f6 = (f3 / f5) + 1000.0f;
        float f7 = (f4 / (f5 * this.stretch)) + 1000.0f;
        float f8 = this.turbulence;
        float evaluate = (f8 == 1.0f ? evaluate(f6, f7) : turbulence2(f6, f7, f8)) * 2.0f * this.amount;
        Colormap colormap = this.colormap;
        if (colormap != null) {
            int color = colormap.getColor(evaluate);
            if (this.useColor) {
                int i5 = iArr[(ImageMath.clamp((int) ((this.results[0].f1958y - 1000.0f) * this.scale), 0, i4 - 1) * i3) + ImageMath.clamp((int) ((this.results[0].f1957x - 1000.0f) * this.scale), 0, i3 - 1)];
                float f9 = (this.results[1].distance - this.results[0].distance) / (this.results[1].distance + this.results[0].distance);
                float[] fArr = this.coefficients;
                return ImageMath.mixColors(ImageMath.smoothStep(fArr[1], fArr[0], f9), ViewCompat.MEASURED_STATE_MASK, i5);
            }
            return color;
        }
        int clamp = PixelUtils.clamp((int) (evaluate * 255.0f));
        return clamp | (clamp << 16) | ViewCompat.MEASURED_STATE_MASK | (clamp << 8);
    }

    public Object clone() {
        CellularFilter cellularFilter;
        try {
            cellularFilter = (CellularFilter) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            cellularFilter = null;
        }
        cellularFilter.coefficients = (float[]) this.coefficients.clone();
        cellularFilter.results = (Point[]) this.results.clone();
        cellularFilter.random = new Random();
        return cellularFilter;
    }

    @Override // com.picspool.lib.filter.cpu.father.WholeImageFilter
    protected int[] filterPixels(int i, int i2, int[] iArr, Rect rect) {
        int[] iArr2 = new int[i * i2];
        int i3 = 0;
        int i4 = 0;
        while (i4 < i2) {
            int i5 = i3;
            int i6 = 0;
            while (i6 < i) {
                iArr2[i5] = getPixel(i6, i4, iArr, i, i2);
                i6++;
                i5++;
            }
            i4++;
            i3 = i5;
        }
        return iArr2;
    }
}
