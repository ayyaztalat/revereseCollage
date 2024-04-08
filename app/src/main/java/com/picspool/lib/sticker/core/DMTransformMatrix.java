package com.picspool.lib.sticker.core;

import android.graphics.Matrix;

/* loaded from: classes3.dex */
public class DMTransformMatrix implements Cloneable {
    public Matrix newScaleTransform = new Matrix();
    public Matrix lastScaleTransform = new Matrix();
    public Matrix newPanTransform = new Matrix();
    public Matrix lastPanTransform = new Matrix();
    public Matrix newRotateTransform = new Matrix();
    public Matrix lastRotateTransform = new Matrix();

    public DMTransformMatrix clone() throws CloneNotSupportedException {
        DMTransformMatrix dMTransformMatrix = (DMTransformMatrix) super.clone();
        dMTransformMatrix.newScaleTransform = new Matrix(this.newScaleTransform);
        dMTransformMatrix.lastScaleTransform = new Matrix(this.lastScaleTransform);
        dMTransformMatrix.newPanTransform = new Matrix(this.newPanTransform);
        dMTransformMatrix.lastPanTransform = new Matrix(this.lastPanTransform);
        dMTransformMatrix.newRotateTransform = new Matrix(this.newRotateTransform);
        dMTransformMatrix.lastRotateTransform = new Matrix(this.lastRotateTransform);
        return dMTransformMatrix;
    }
}
