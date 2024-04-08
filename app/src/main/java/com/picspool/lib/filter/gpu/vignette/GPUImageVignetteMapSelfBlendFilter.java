package com.picspool.lib.filter.gpu.vignette;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageVignetteMapSelfBlendFilter extends GPUImageTwoInputFilter {
    public static final String VIGNETTING_FRAGMENT_SHADER = " uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture2;\n varying highp vec2 textureCoordinate2;\n \n uniform lowp vec2 vignetteCenter;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     mediump vec4 base = texture2D(inputImageTexture, textureCoordinate);\n     lowp float redCurveValue = texture2D(inputImageTexture2, vec2(base.r, base.r)).r;\n     lowp float greenCurveValue = texture2D(inputImageTexture2, vec2(base.g, base.g)).g;\n     lowp float blueCurveValue = texture2D(inputImageTexture2, vec2(base.b, base.b)).b;\n     mediump vec4 mapColor = vec4(redCurveValue,greenCurveValue,blueCurveValue, base.a);\n     lowp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     lowp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     lowp vec4 textureColor3  = vec4(mix(base.rgb, mapColor.rgb, percent), 1.0);\n     gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), base.a);\n }";
    private PointF mVignetteCenter;
    private int mVignetteCenterLocation;
    private float mVignetteEnd;
    private int mVignetteEndLocation;
    private float mVignetteStart;
    private int mVignetteStartLocation;

    public GPUImageVignetteMapSelfBlendFilter() {
        this(new PointF(), 0.2f, 0.75f);
    }

    public GPUImageVignetteMapSelfBlendFilter(PointF pointF, float f, float f2) {
        super(VIGNETTING_FRAGMENT_SHADER);
        this.mVignetteCenter = pointF;
        this.mVignetteStart = f;
        this.mVignetteEnd = f2;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mVignetteCenterLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteCenter");
        this.mVignetteStartLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteStart");
        this.mVignetteEndLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteEnd");
        setVignetteCenter(this.mVignetteCenter);
        setVignetteStart(this.mVignetteStart);
        setVignetteEnd(this.mVignetteEnd);
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
}
