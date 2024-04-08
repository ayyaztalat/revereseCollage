package com.picspool.lib.filter.gpu.vignette;

import android.graphics.PointF;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageVignetteSharpenFilter extends GPUImageFilter {
    public static final String SHARPEN_VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nuniform float imageWidthFactor; \nuniform float imageHeightFactor; \n\nvarying vec2 textureCoordinate;\nvarying vec2 leftTextureCoordinate;\nvarying vec2 rightTextureCoordinate; \nvarying vec2 topTextureCoordinate;\nvarying vec2 bottomTextureCoordinate;\n\nvoid main()\n{\n    gl_Position = position;\n    \n    mediump vec2 widthStep = vec2(imageWidthFactor, 0.0);\n    mediump vec2 heightStep = vec2(0.0, imageHeightFactor);\n    \n    textureCoordinate = inputTextureCoordinate.xy;\n    leftTextureCoordinate = inputTextureCoordinate.xy - widthStep;\n    rightTextureCoordinate = inputTextureCoordinate.xy + widthStep;\n    topTextureCoordinate = inputTextureCoordinate.xy + heightStep;     \n    bottomTextureCoordinate = inputTextureCoordinate.xy - heightStep;\n    \n}";
    public static final String VIGNETTING_FRAGMENT_SHADER = " uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\nvarying highp vec2 leftTextureCoordinate;\nvarying highp vec2 rightTextureCoordinate; \nvarying highp vec2 topTextureCoordinate;\nvarying highp vec2 bottomTextureCoordinate;\nvarying highp float centerMultiplier;\nvarying highp float edgeMultiplier;\n \n uniform lowp vec2 vignetteCenter;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n uniform highp float sharpnessStart;\n uniform highp float sharpnessEnd;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     lowp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     lowp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     lowp float sharpness = (sharpnessEnd - sharpnessStart) * percent + sharpnessStart;   lowp float centerMultiplier = 1.0 + 4.0 * (sharpness*mixturePercent);\n    lowp float edgeMultiplier = sharpness*mixturePercent;\n    mediump vec3 textureColor = texture2D(inputImageTexture, textureCoordinate).rgb;\n    mediump vec3 leftTextureColor = texture2D(inputImageTexture, leftTextureCoordinate).rgb;\n    mediump vec3 rightTextureColor = texture2D(inputImageTexture, rightTextureCoordinate).rgb;\n    mediump vec3 topTextureColor = texture2D(inputImageTexture, topTextureCoordinate).rgb;\n    mediump vec3 bottomTextureColor = texture2D(inputImageTexture, bottomTextureCoordinate).rgb;\n    lowp vec4 textureColor3  = vec4((textureColor * centerMultiplier - (leftTextureColor * edgeMultiplier + rightTextureColor * edgeMultiplier + topTextureColor * edgeMultiplier + bottomTextureColor * edgeMultiplier)), texture2D(inputImageTexture, bottomTextureCoordinate).w);\n    gl_FragColor = textureColor3; }";
    private int mImageHeightFactorLocation;
    private int mImageWidthFactorLocation;
    private float mSharpnessEnd;
    private int mSharpnessEndLocation;
    private float mSharpnessStart;
    private int mSharpnessStartLocation;
    private PointF mVignetteCenter;
    private int mVignetteCenterLocation;
    private float mVignetteEnd;
    private int mVignetteEndLocation;
    private float mVignetteStart;
    private int mVignetteStartLocation;

    public GPUImageVignetteSharpenFilter() {
        this(new PointF(), 1.0f, -1.0f, 0.3f, 0.75f);
    }

    public GPUImageVignetteSharpenFilter(PointF pointF, float f, float f2, float f3, float f4) {
        super(SHARPEN_VERTEX_SHADER, VIGNETTING_FRAGMENT_SHADER);
        this.mVignetteCenter = pointF;
        this.mVignetteStart = f3;
        this.mVignetteEnd = f4;
        this.mSharpnessStart = f;
        this.mSharpnessEnd = f2;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mVignetteCenterLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteCenter");
        this.mVignetteStartLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteStart");
        this.mVignetteEndLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteEnd");
        this.mSharpnessStartLocation = GLES20.glGetUniformLocation(getProgram(), "sharpnessStart");
        this.mSharpnessEndLocation = GLES20.glGetUniformLocation(getProgram(), "sharpnessEnd");
        this.mImageWidthFactorLocation = GLES20.glGetUniformLocation(getProgram(), "imageWidthFactor");
        this.mImageHeightFactorLocation = GLES20.glGetUniformLocation(getProgram(), "imageHeightFactor");
        setVignetteCenter(this.mVignetteCenter);
        setVignetteStart(this.mVignetteStart);
        setVignetteEnd(this.mVignetteEnd);
        setSharpnessStart(this.mSharpnessStart);
        setSharpnessEnd(this.mSharpnessEnd);
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

    public void setSharpnessStart(float f) {
        this.mSharpnessStart = f;
        setFloat(this.mSharpnessStartLocation, f);
    }

    public void setSharpnessEnd(float f) {
        this.mSharpnessEnd = f;
        setFloat(this.mSharpnessEndLocation, f);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        setFloat(this.mImageWidthFactorLocation, 1.0f / i);
        setFloat(this.mImageHeightFactorLocation, 1.0f / i2);
    }
}
