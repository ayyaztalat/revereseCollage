package com.picspool.lib.filter.gpu.vignette;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageVignetteContrastFilter extends GPUImageFilter {
    public static final String VIGNETTING_FRAGMENT_SHADER = " uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\n \n uniform lowp vec2 vignetteCenter;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n uniform highp float contrastStart;\n uniform highp float contrastEnd;\n \n void main()\n {\n     lowp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     lowp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     lowp float contrast = (contrastEnd - contrastStart) * percent + contrastStart;     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     gl_FragColor = vec4(((textureColor.rgb - vec3(0.5)) * contrast + vec3(0.5)), textureColor.w);\n }";
    private float mContrastEnd;
    private int mContrastEndLocation;
    private float mContrastStart;
    private int mContrastStartLocation;
    private PointF mVignetteCenter;
    private int mVignetteCenterLocation;
    private float mVignetteEnd;
    private int mVignetteEndLocation;
    private float mVignetteStart;
    private int mVignetteStartLocation;

    public GPUImageVignetteContrastFilter() {
        this(new PointF(), 0.3f, -0.3f, 0.3f, 0.75f);
    }

    public GPUImageVignetteContrastFilter(PointF pointF, float f, float f2, float f3, float f4) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, VIGNETTING_FRAGMENT_SHADER);
        this.mVignetteCenter = pointF;
        this.mVignetteStart = f3;
        this.mVignetteEnd = f4;
        this.mContrastStart = f;
        this.mContrastEnd = f2;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mVignetteCenterLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteCenter");
        this.mVignetteStartLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteStart");
        this.mVignetteEndLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteEnd");
        this.mContrastStartLocation = GLES20.glGetUniformLocation(getProgram(), "contrastStart");
        this.mContrastEndLocation = GLES20.glGetUniformLocation(getProgram(), "contrastEnd");
        setVignetteCenter(this.mVignetteCenter);
        setVignetteStart(this.mVignetteStart);
        setVignetteEnd(this.mVignetteEnd);
        setContrastStart(this.mContrastStart);
        setContrastEnd(this.mContrastEnd);
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

    public void setContrastStart(float f) {
        this.mContrastStart = f;
        setFloat(this.mContrastStartLocation, f);
    }

    public void setContrastEnd(float f) {
        this.mContrastEnd = f;
        setFloat(this.mContrastEndLocation, f);
    }
}
