package com.picspool.lib.filter.gpu.adjust;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageSoftLightFilter extends GPUImageFilter {
    public static final String SCREEN_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n \n uniform lowp float blurSize;\n uniform lowp float contrast;\n uniform lowp float brightness;\n \n void main()\n {\n    mediump vec4 sum = vec4(0.0);    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y)) * 41.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y)) * 26.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y)) * 26.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y + blurSize)) * 26.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y - blurSize)) * 26.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y + blurSize)) * 16.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y - blurSize)) * 16.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y + blurSize)) * 16.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y - blurSize)) * 16.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + 2.0*blurSize, textureCoordinate.y)) * 7.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - 2.0*blurSize, textureCoordinate.y)) * 7.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y + 2.0*blurSize)) * 7.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y - 2.0*blurSize)) * 7.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y - 2.0*blurSize)) * 4.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y + 2.0*blurSize)) * 4.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + 2.0*blurSize, textureCoordinate.y - blurSize)) * 4.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + 2.0*blurSize, textureCoordinate.y + blurSize)) * 4.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y - 2.0*blurSize)) * 4.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y + 2.0*blurSize)) * 4.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - 2.0*blurSize, textureCoordinate.y - blurSize)) * 4.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - 2.0*blurSize, textureCoordinate.y + blurSize)) * 4.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + 2.0*blurSize, textureCoordinate.y - 2.0*blurSize)) * 1.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + 2.0*blurSize, textureCoordinate.y + 2.0*blurSize)) * 1.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - 2.0*blurSize, textureCoordinate.y - 2.0*blurSize)) * 1.0/273.0;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - 2.0*blurSize, textureCoordinate.y + 2.0*blurSize)) * 1.0/273.0;     sum = vec4((sum.rgb + vec3(brightness)), sum.w);\n     sum = vec4(((sum.rgb - vec3(0.5)) * contrast + vec3(0.5)), sum.w);\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 textureColor2 = sum;\n     mediump vec4 whiteColor = vec4(1.0);\n     gl_FragColor = whiteColor - ((whiteColor - textureColor2) * (whiteColor - textureColor));\n }";
    private float mBlurSize;
    private int mBlurSizeLocation;
    private float mBrightness;
    private int mBrightnessLocation;
    private float mContrast;
    private int mContrastLocation;

    public GPUImageSoftLightFilter() {
        this(0.0f, 0.0f, 1.0f);
    }

    public GPUImageSoftLightFilter(float f, float f2, float f3) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, SCREEN_BLEND_FRAGMENT_SHADER);
        this.mBlurSize = f;
        this.mBrightness = f2;
        this.mContrast = f3;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mBlurSizeLocation = GLES20.glGetUniformLocation(getProgram(), "blurSize");
        this.mBrightnessLocation = GLES20.glGetUniformLocation(getProgram(), "brightness");
        this.mContrastLocation = GLES20.glGetUniformLocation(getProgram(), "contrast");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        this.mBlurSizeLocation = GLES20.glGetUniformLocation(getProgram(), "blurSize");
        this.mBrightnessLocation = GLES20.glGetUniformLocation(getProgram(), "brightness");
        this.mContrastLocation = GLES20.glGetUniformLocation(getProgram(), "contrast");
        setBlurSize(this.mBlurSize);
        setBrightness(this.mBrightness);
        setContrast(this.mContrast);
    }

    public void setBlurSize(float f) {
        this.mBlurSize = f;
        setFloat(this.mBlurSizeLocation, f);
    }

    public void setBrightness(float f) {
        this.mBrightness = f;
        setFloat(this.mBrightnessLocation, f);
    }

    public void setContrast(float f) {
        this.mContrast = f;
        setFloat(this.mContrastLocation, f);
    }
}
