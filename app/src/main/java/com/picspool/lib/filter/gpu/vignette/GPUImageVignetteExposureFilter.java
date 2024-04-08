package com.picspool.lib.filter.gpu.vignette;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageVignetteExposureFilter extends GPUImageFilter {
    public static final String VIGNETTING_FRAGMENT_SHADER = " uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\n \n uniform lowp vec2 vignetteCenter;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n uniform highp float exposureStart;\n uniform highp float exposureEnd;\n \n void main()\n {\n     lowp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     lowp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     lowp float exposure = (exposureEnd - exposureStart) * percent + exposureStart;     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     gl_FragColor = vec4(textureColor.rgb * pow(2.0, exposure), textureColor.w);\n }";
    private float mExposureEnd;
    private int mExposureEndLocation;
    private float mExposureStart;
    private int mExposureStartLocation;
    private PointF mVignetteCenter;
    private int mVignetteCenterLocation;
    private float mVignetteEnd;
    private int mVignetteEndLocation;
    private float mVignetteStart;
    private int mVignetteStartLocation;

    public GPUImageVignetteExposureFilter() {
        this(new PointF(), 0.3f, -0.3f, 0.3f, 0.75f);
    }

    public GPUImageVignetteExposureFilter(PointF pointF, float f, float f2, float f3, float f4) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, VIGNETTING_FRAGMENT_SHADER);
        this.mVignetteCenter = pointF;
        this.mVignetteStart = f3;
        this.mVignetteEnd = f4;
        this.mExposureStart = f;
        this.mExposureEnd = f2;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mVignetteCenterLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteCenter");
        this.mVignetteStartLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteStart");
        this.mVignetteEndLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteEnd");
        this.mExposureStartLocation = GLES20.glGetUniformLocation(getProgram(), "exposureStart");
        this.mExposureEndLocation = GLES20.glGetUniformLocation(getProgram(), "exposureEnd");
        setVignetteCenter(this.mVignetteCenter);
        setVignetteStart(this.mVignetteStart);
        setVignetteEnd(this.mVignetteEnd);
        setExposureStart(this.mExposureStart);
        setExposureEnd(this.mExposureEnd);
    }

    public void setVignetteCenter(PointF pointF) {
        this.mVignetteCenter = pointF;
        setPoint(this.mVignetteCenterLocation, pointF);
    }

    public void setVignetteStart(float f) {
        this.mVignetteStart = f;
        setFloat(this.mVignetteStartLocation, f);
    }

    public void setVignetteEnd(float f) {
        this.mVignetteEnd = f;
        setFloat(this.mVignetteEndLocation, f);
    }

    public void setExposureStart(float f) {
        this.mExposureStart = f;
        setFloat(this.mExposureStartLocation, f);
    }

    public void setExposureEnd(float f) {
        this.mExposureEnd = f;
        setFloat(this.mExposureEndLocation, f);
    }
}
