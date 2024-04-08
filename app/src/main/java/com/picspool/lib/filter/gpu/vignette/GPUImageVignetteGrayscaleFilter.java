package com.picspool.lib.filter.gpu.vignette;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageVignetteGrayscaleFilter extends GPUImageFilter {
    public static final String VIGNETTING_FRAGMENT_SHADER = " uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\n \n uniform lowp vec2 vignetteCenter;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n uniform highp float saturationStart;\n uniform highp float saturationEnd;\n \n const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\n void main()\n {\n     lowp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     lowp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     lowp float saturation = (saturationEnd - saturationStart) * percent + saturationStart;     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n    lowp float luminance = dot(textureColor.rgb, luminanceWeighting);\n    lowp vec3 grayScaleColor = vec3(luminance);\n    gl_FragColor = vec4(mix(grayScaleColor, textureColor.rgb, saturation), textureColor.w);\n }";
    private float mSaturationEnd;
    private int mSaturationEndLocation;
    private float mSaturationStart;
    private int mSaturationStartLocation;
    private PointF mVignetteCenter;
    private int mVignetteCenterLocation;
    private float mVignetteEnd;
    private int mVignetteEndLocation;
    private float mVignetteStart;
    private int mVignetteStartLocation;

    public GPUImageVignetteGrayscaleFilter() {
        this(new PointF(), 0.3f, -0.3f, 0.3f, 0.75f);
    }

    public GPUImageVignetteGrayscaleFilter(PointF pointF, float f, float f2, float f3, float f4) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, VIGNETTING_FRAGMENT_SHADER);
        this.mVignetteCenter = pointF;
        this.mVignetteStart = f3;
        this.mVignetteEnd = f4;
        this.mSaturationStart = f;
        this.mSaturationEnd = f2;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mVignetteCenterLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteCenter");
        this.mVignetteStartLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteStart");
        this.mVignetteEndLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteEnd");
        this.mSaturationStartLocation = GLES20.glGetUniformLocation(getProgram(), "saturationStart");
        this.mSaturationEndLocation = GLES20.glGetUniformLocation(getProgram(), "saturationEnd");
        setVignetteCenter(this.mVignetteCenter);
        setVignetteStart(this.mVignetteStart);
        setVignetteEnd(this.mVignetteEnd);
        setSaturationStart(this.mSaturationStart);
        setSaturationEnd(this.mSaturationEnd);
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

    public void setSaturationStart(float f) {
        this.mSaturationStart = f;
        setFloat(this.mSaturationStartLocation, f);
    }

    public void setSaturationEnd(float f) {
        this.mSaturationEnd = f;
        setFloat(this.mSaturationEndLocation, f);
    }
}
