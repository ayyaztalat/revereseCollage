package com.picspool.lib.filter.cpu.normal;

import com.picspool.lib.filter.cpu.father.TransformFilter;
import com.picspool.lib.filter.cpu.util.Noise;
import com.picspool.lib.filter.cpu.util.PixelUtils;

/* loaded from: classes3.dex */
public class MarbleFilter extends TransformFilter {
    private float[] cosTable;
    private float[] sinTable;
    private float xScale = 4.0f;
    private float yScale = 4.0f;
    private float amount = 1.0f;
    private float turbulence = 1.0f;

    public String toString() {
        return "Distort/Marble...";
    }

    public MarbleFilter() {
        setEdgeAction(1);
    }

    public void setXScale(float f) {
        this.xScale = f;
    }

    public float getXScale() {
        return this.xScale;
    }

    public void setYScale(float f) {
        this.yScale = f;
    }

    public float getYScale() {
        return this.yScale;
    }

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setTurbulence(float f) {
        this.turbulence = f;
    }

    public float getTurbulence() {
        return this.turbulence;
    }

    private void initialize() {
        this.sinTable = new float[256];
        this.cosTable = new float[256];
        for (int i = 0; i < 256; i++) {
            double d = ((i * 6.2831855f) / 256.0f) * this.turbulence;
            this.sinTable[i] = (float) ((-this.yScale) * Math.sin(d));
            this.cosTable[i] = (float) (this.yScale * Math.cos(d));
        }
    }

    private int displacementMap(int i, int i2) {
        float f = this.xScale;
        return PixelUtils.clamp((int) ((Noise.noise2(i / f, i2 / f) + 1.0f) * 127.0f));
    }

    @Override // com.picspool.lib.filter.cpu.father.TransformFilter
    protected void transformInverse(int i, int i2, float[] fArr) {
        int displacementMap = displacementMap(i, i2);
        fArr[0] = i + this.sinTable[displacementMap];
        fArr[1] = i2 + this.cosTable[displacementMap];
    }

    @Override // com.picspool.lib.filter.cpu.father.TransformFilter
    public int[] filter(int[] iArr, int i, int i2) {
        initialize();
        return super.filter(iArr, i, i2);
    }
}
