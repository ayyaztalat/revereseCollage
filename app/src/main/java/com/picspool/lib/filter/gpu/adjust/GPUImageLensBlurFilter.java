package com.picspool.lib.filter.gpu.adjust;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.alibaba.fastjson2.internal.asm.Opcodes;
import com.picspool.lib.filter.gpu.father.GPUImageThreeInputFilter;

/* loaded from: classes3.dex */
public class GPUImageLensBlurFilter extends GPUImageThreeInputFilter {
    private float aspectRatio;
    private int aspectRatioLocation;
    private float blurMode;
    private int blurModeLocation;
    private float blurSize;
    private int blurSizeLocation;
    private float circleRadius;
    private int circleRadiusLocation;
    private float left;
    protected int mTransformLocation2;
    private int rotate;
    private float scale;
    private float top;
    private float viewHeight;
    private float viewWidth;

    public GPUImageLensBlurFilter(String str, String str2) {
        super(str, str2);
        this.circleRadius = 0.5f;
        this.blurSize = 0.5f;
        this.aspectRatio = 0.5f;
        this.blurMode = 1.0f;
        this.rotate = Opcodes.GETFIELD;
        this.left = 0.5f;
        this.top = 0.5f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageThreeInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mTransformLocation2 = GLES20.glGetUniformLocation(getProgram(), "transformMatrix2");
        this.circleRadiusLocation = GLES20.glGetUniformLocation(getProgram(), "excludeCircleRadius");
        this.blurSizeLocation = GLES20.glGetUniformLocation(getProgram(), "excludeBlurSize");
        this.aspectRatioLocation = GLES20.glGetUniformLocation(getProgram(), "aspectRatio");
        this.blurModeLocation = GLES20.glGetUniformLocation(getProgram(), "blurMobe");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setTransform2(this.rotate, this.left, this.top, this.scale, this.viewWidth, this.viewHeight);
        setCircleRadius(this.circleRadius);
        setBlurSize(this.blurSize);
        setAspectRatio(this.aspectRatio);
        setBlurMode(this.blurMode);
    }

    public void setCircleRadius(float f) {
        float f2 = f + 0.5f;
        this.circleRadius = f2;
        setFloat(this.circleRadiusLocation, f2);
    }

    public void setBlurSize(float f) {
        this.blurSize = f;
        setFloat(this.blurSizeLocation, f);
    }

    public void setAspectRatio(float f) {
        this.aspectRatio = f;
        setFloat(this.aspectRatioLocation, f);
    }

    public void setBlurMode(float f) {
        this.blurMode = f;
        setFloat(this.blurModeLocation, f);
    }

    public void setTransform2(int i, float f, float f2, float f3, float f4, float f5) {
        this.rotate = i;
        this.left = f;
        this.top = f2;
        this.scale = f3;
        this.viewWidth = f4;
        this.viewHeight = f5;
        float f6 = f4 / f5;
        float[] fArr = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        float[] fArr2 = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        float[] fArr3 = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        float[] fArr4 = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        Matrix.setLookAtM(fArr, 0, 0.0f, 0.0f, -3.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        Matrix.frustumM(fArr2, 0, -1.0f, 1.0f, -f6, f6, 1.0f, 7.0f);
        Matrix.multiplyMM(fArr4, 0, fArr2, 0, fArr, 0);
        Matrix.translateM(fArr3, 0, 0.125f, 0.135f, 0.0f);
        Matrix.rotateM(fArr3, 0, i + Opcodes.GETFIELD, 0.0f, 0.0f, 1.0f);
        float f7 = 1.0f - f3;
        Matrix.scaleM(fArr3, 0, f7, f7, 0.0f);
        Matrix.translateM(fArr3, 0, f - 0.5f, f2 - 0.5f, 0.0f);
        Matrix.multiplyMM(fArr4, 0, fArr3, 0, fArr4, 0);
        setUniformMatrix4f(this.mTransformLocation2, fArr4);
    }
}
