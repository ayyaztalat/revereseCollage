package com.picspool.lib.filter.cpu.normal;

import com.picspool.lib.filter.cpu.father.TransformFilter;

/* loaded from: classes3.dex */
public class DiffuseFilter extends TransformFilter {
    private float[] cosTable;
    private float scale = 4.0f;
    private float[] sinTable;

    public String toString() {
        return "Distort/Diffuse...";
    }

    public DiffuseFilter() {
        setEdgeAction(1);
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public float getScale() {
        return this.scale;
    }

    @Override // com.picspool.lib.filter.cpu.father.TransformFilter
    protected void transformInverse(int i, int i2, float[] fArr) {
        int random = (int) (Math.random() * 255.0d);
        float random2 = (float) Math.random();
        fArr[0] = i + (this.sinTable[random] * random2);
        fArr[1] = i2 + (random2 * this.cosTable[random]);
    }

    @Override // com.picspool.lib.filter.cpu.father.TransformFilter
    public int[] filter(int[] iArr, int i, int i2) {
        this.sinTable = new float[256];
        this.cosTable = new float[256];
        for (int i3 = 0; i3 < 256; i3++) {
            double d = (i3 * 6.2831855f) / 256.0f;
            this.sinTable[i3] = (float) (this.scale * Math.sin(d));
            this.cosTable[i3] = (float) (this.scale * Math.cos(d));
        }
        return super.filter(iArr, i, i2);
    }
}
