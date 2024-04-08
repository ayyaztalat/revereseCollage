package com.picspool.libfuncview.slimbody;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes.dex */
public class CSGPUImageSlimBodyFilter extends GPUImageFilter {
    private int[] locations;
    private float[] mAdjustDst;
    private int mAdjustDstLocation;
    private float mAdjustRadius;
    private int mAdjustRadiusLocation;
    private float mAdjustRatio;
    private int mAdjustRatioLocation;
    private float[] mAdjustSrc;
    private int mAdjustSrcLocation;
    private PointF[] mPointsValue;
    private float mRatio;
    private int mRatioLocation;
    private int outPutHeight;
    private int outPutWidth;

    public CSGPUImageSlimBodyFilter(String str) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, str);
        this.mAdjustRatio = 0.05f;
        this.mAdjustRadius = 0.01f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mRatioLocation = GLES20.glGetUniformLocation(getProgram(), "ratio");
        this.mAdjustRatioLocation = GLES20.glGetUniformLocation(getProgram(), "adjustRatio");
        this.mAdjustRadiusLocation = GLES20.glGetUniformLocation(getProgram(), "adjustRadius");
        this.mAdjustSrcLocation = GLES20.glGetUniformLocation(getProgram(), "adjustSrc");
        this.mAdjustDstLocation = GLES20.glGetUniformLocation(getProgram(), "adjustDst");
        setAdjustRatioLocation(this.mAdjustRatio);
        setAdjustRadiusLocation(this.mAdjustRadius);
        setAdjustSrcLocation(this.mAdjustSrc);
        setAdjustDstLocation(this.mAdjustDst);
    }

    public void setLocation(int i, float[] fArr) {
        PointF[] pointFArr = this.mPointsValue;
        if (pointFArr[i] == null) {
            pointFArr[i] = new PointF(fArr[0], fArr[1]);
        }
        setFloatVec2(this.locations[i], fArr);
    }

    public void setRatioLocation(float f) {
        this.mRatio = f;
        setFloat(this.mRatioLocation, f);
    }

    public void setAdjustRatioLocation(float f) {
        this.mAdjustRatio = f;
        setFloat(this.mAdjustRatioLocation, f);
    }

    public void setAdjustRadiusLocation(float f) {
        this.mAdjustRadius = f;
        setFloat(this.mAdjustRadiusLocation, f);
    }

    public void setAdjustSrcLocation(float[] fArr) {
        this.mAdjustSrc = fArr;
        setFloatVec2(this.mAdjustSrcLocation, fArr);
    }

    public void setAdjustDstLocation(float[] fArr) {
        this.mAdjustDst = fArr;
        setFloatVec2(this.mAdjustDstLocation, fArr);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        this.outPutWidth = i;
        this.outPutHeight = i2;
    }

    public int getOutPutHeight() {
        return this.outPutHeight;
    }

    public int getOutPutWidth() {
        return this.outPutWidth;
    }
}
