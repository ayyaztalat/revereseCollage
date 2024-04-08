package com.picspool.lib.filter.gpu.vignette;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageVignetteGammaFilter extends GPUImageFilter {
    public static final String VIGNETTING_FRAGMENT_SHADER = " uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\n \n uniform lowp vec2 vignetteCenter;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n uniform highp float gammaStart;\n uniform highp float gammaEnd;\n \n void main()\n {\n     lowp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     lowp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     lowp float gamma = (gammaEnd - gammaStart) * percent + gammaStart;     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     gl_FragColor = vec4(pow(textureColor.rgb, vec3(gamma)), textureColor.w);\n }";
    private float mGammaEnd;
    private int mGammaEndLocation;
    private float mGammaStart;
    private int mGammaStartLocation;
    private PointF mVignetteCenter;
    private int mVignetteCenterLocation;
    private float mVignetteEnd;
    private int mVignetteEndLocation;
    private float mVignetteStart;
    private int mVignetteStartLocation;

    public GPUImageVignetteGammaFilter() {
        this(new PointF(), 0.3f, -0.3f, 0.3f, 0.75f);
    }

    public GPUImageVignetteGammaFilter(PointF pointF, float f, float f2, float f3, float f4) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, " uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\n \n uniform lowp vec2 vignetteCenter;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n uniform highp float gammaStart;\n uniform highp float gammaEnd;\n \n void main()\n {\n     lowp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     lowp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     lowp float gamma = (gammaEnd - gammaStart) * percent + gammaStart;     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     gl_FragColor = vec4(pow(textureColor.rgb, vec3(gamma)), textureColor.w);\n }");
        this.mVignetteCenter = pointF;
        this.mVignetteStart = f3;
        this.mVignetteEnd = f4;
        this.mGammaStart = f;
        this.mGammaEnd = f2;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mVignetteCenterLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteCenter");
        this.mVignetteStartLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteStart");
        this.mVignetteEndLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteEnd");
        this.mGammaStartLocation = GLES20.glGetUniformLocation(getProgram(), "gammaStart");
        this.mGammaEndLocation = GLES20.glGetUniformLocation(getProgram(), "gammaEnd");
        setVignetteCenter(this.mVignetteCenter);
        setVignetteStart(this.mVignetteStart);
        setVignetteEnd(this.mVignetteEnd);
        setGammaStart(this.mGammaStart);
        setGammaEnd(this.mGammaEnd);
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

    public void setGammaStart(float f) {
        this.mGammaStart = f;
        setFloat(this.mGammaStartLocation, f);
    }

    public void setGammaEnd(float f) {
        this.mGammaEnd = f;
        setFloat(this.mGammaEndLocation, f);
    }
}
