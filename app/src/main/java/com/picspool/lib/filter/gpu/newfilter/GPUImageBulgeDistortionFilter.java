package com.picspool.lib.filter.gpu.newfilter;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageBulgeDistortionFilter extends GPUImageFilter {
    public static final String BULGE_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n\nuniform sampler2D inputImageTexture;\n\nuniform highp float aspectRatio;\nuniform highp vec2 center;\nuniform highp float radius;\nuniform highp float scale;\n\nvoid main()\n {\n     highp vec2 textureCoordinateToUse = textureCoordinate;\n     highp float dist = distance(center, textureCoordinate);\n     textureCoordinateToUse -= center;\n     if (dist < radius)\n     {\n         highp float percent = (1.0 + dist * dist * scale) * radius * 2.0 / 3.0;\n         textureCoordinateToUse = textureCoordinateToUse * percent;\n     }\n     textureCoordinateToUse += center;\n\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinateToUse);\n\n }";
    private float mAspectRatio;
    private int mAspectRatioLocation;
    private PointF mCenter;
    private int mCenterLocation;
    private float mRadius;
    private int mRadiusLocation;
    private float mScale;
    private int mScaleLocation;

    public GPUImageBulgeDistortionFilter() {
        this(1.0f, 1.5f, new PointF(0.5f, 0.5f));
    }

    public GPUImageBulgeDistortionFilter(float f, float f2, PointF pointF) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, BULGE_FRAGMENT_SHADER);
        this.mRadius = f;
        this.mScale = f2;
        this.mCenter = pointF;
    }

    public GPUImageBulgeDistortionFilter(String str) {
        this(str, 0.25f, 0.5f, new PointF(0.5f, 0.5f));
    }

    public GPUImageBulgeDistortionFilter(String str, float f, float f2, PointF pointF) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, str);
        this.mRadius = f;
        this.mScale = f2;
        this.mCenter = pointF;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mScaleLocation = GLES20.glGetUniformLocation(getProgram(), "scale");
        this.mRadiusLocation = GLES20.glGetUniformLocation(getProgram(), "radius");
        this.mCenterLocation = GLES20.glGetUniformLocation(getProgram(), "center");
        this.mAspectRatioLocation = GLES20.glGetUniformLocation(getProgram(), "aspectRatio");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setRadius(this.mRadius);
        setScale(this.mScale);
        setCenter(this.mCenter);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        float f = i2 / i;
        this.mAspectRatio = f;
        setAspectRatio(f);
        super.onOutputSizeChanged(i, i2);
    }

    private void setAspectRatio(float f) {
        this.mAspectRatio = f;
        setFloat(this.mAspectRatioLocation, f);
    }

    public void setRadius(float f) {
        this.mRadius = f;
        setFloat(this.mRadiusLocation, f);
    }

    public void setScale(float f) {
        this.mScale = f;
        setFloat(this.mScaleLocation, f);
    }

    public void setCenter(PointF pointF) {
        this.mCenter = pointF;
        setPoint(this.mCenterLocation, pointF);
    }
}
