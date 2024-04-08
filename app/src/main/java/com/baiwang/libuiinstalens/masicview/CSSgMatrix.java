package com.baiwang.libuiinstalens.masicview;

import android.opengl.Matrix;

/* loaded from: classes.dex */
public class CSSgMatrix {
    private float[] value;

    public CSSgMatrix() {
        this.value = new float[16];
        reset();
    }

    public CSSgMatrix(CSSgMatrix cSSgMatrix) {
        this.value = cSSgMatrix.getValue();
    }

    public void setTranslate(float f, float f2) {
        setTranslate(f, f2, 0.0f);
    }

    public void setTranslate(float f, float f2, float f3) {
        reset();
        Matrix.translateM(this.value, 0, f, f2, f3);
    }

    public void postTranslate(float f, float f2) {
        postTranslate(f, f2, 0.0f);
    }

    public void postTranslate(float f, float f2, float f3) {
        CSSgMatrix cSSgMatrix = new CSSgMatrix();
        cSSgMatrix.setTranslate(f, f2, f3);
        postConcat(cSSgMatrix);
    }

    public void setScale(float f, float f2) {
        setScale(f, f2, 1.0f);
    }

    public void setScale(float f, float f2, float f3) {
        reset();
        Matrix.scaleM(this.value, 0, f, f2, f3);
    }

    public void postScale(float f, float f2) {
        postScale(f, f2, 1.0f);
    }

    public void postScale(float f, float f2, float f3) {
        CSSgMatrix cSSgMatrix = new CSSgMatrix();
        cSSgMatrix.setScale(f, f2, f3);
        postConcat(cSSgMatrix);
    }

    public void setScale(float f, float f2, float f3, float f4) {
        setScale(f, f2, 1.0f, f3, f4, 0.0f);
    }

    public void setScale(float f, float f2, float f3, float f4, float f5, float f6) {
        reset();
        postTranslate(-f4, -f5, -f6);
        postScale(f, f2, f3);
        postTranslate(f4, f5, f6);
    }

    public void postScale(float f, float f2, float f3, float f4) {
        postScale(f, f2, 1.0f, f3, f4, 0.0f);
    }

    public void postScale(float f, float f2, float f3, float f4, float f5, float f6) {
        postTranslate(-f4, -f5, -f6);
        postScale(f, f2, f3);
        postTranslate(f4, f5, f6);
    }

    public void postConcat(CSSgMatrix cSSgMatrix) {
        Matrix.multiplyMM(this.value, 0, cSSgMatrix.getValue(), 0, this.value, 0);
    }

    public float[] mapPoint(float[] fArr) {
        float[] fArr2 = {0.0f, 0.0f, 0.0f, 1.0f};
        System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
        Matrix.multiplyMV(fArr2, 0, this.value, 0, fArr2, 0);
        System.arraycopy(fArr2, 0, fArr, 0, fArr.length);
        return fArr;
    }

    public void reset() {
        Matrix.setIdentityM(this.value, 0);
    }

    public float[] getValue() {
        return this.value;
    }
}
