package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageFastBlurFilter extends GPUImageFilter {
    public static final String FRAGMENT_SHADER = "precision highp float;\n\n uniform sampler2D inputImageTexture;\n \n varying highp vec2 centerTextureCoordinate;\n varying highp vec2 oneStepLeftTextureCoordinate;\n varying highp vec2 twoStepsLeftTextureCoordinate;\n varying highp vec2 oneStepRightTextureCoordinate;\n varying highp vec2 twoStepsRightTextureCoordinate;\n \n// const float weight[3] = float[]( 0.2270270270, 0.3162162162, 0.0702702703 );\n \n void main()\n {\n     lowp vec4 fragmentColor = texture2D(inputImageTexture, centerTextureCoordinate) * 0.2270270270;\n     fragmentColor += texture2D(inputImageTexture, oneStepLeftTextureCoordinate) * 0.3162162162;\n     fragmentColor += texture2D(inputImageTexture, oneStepRightTextureCoordinate) * 0.3162162162;\n     fragmentColor += texture2D(inputImageTexture, twoStepsLeftTextureCoordinate) * 0.0702702703;\n     fragmentColor += texture2D(inputImageTexture, twoStepsRightTextureCoordinate) * 0.0702702703;\n     \n     gl_FragColor = fragmentColor;\n }";
    public static final String VERTEX_SHADER = "attribute vec4 position;\n attribute vec2 inputTextureCoordinate;\n\n uniform highp float texelWidthOffset; \n uniform highp float texelHeightOffset; \n uniform highp float blurSize;\n \n varying highp vec2 centerTextureCoordinate;\n varying highp vec2 oneStepLeftTextureCoordinate;\n varying highp vec2 twoStepsLeftTextureCoordinate;\n varying highp vec2 oneStepRightTextureCoordinate;\n varying highp vec2 twoStepsRightTextureCoordinate;\n\n// const float offset[3] = float[]( 0.0, 1.3846153846, 3.2307692308 );\n\n void main()\n {\n     gl_Position = position;\n          \n     vec2 firstOffset = vec2(1.3846153846 * texelWidthOffset, 1.3846153846 * texelHeightOffset) * blurSize;\n     vec2 secondOffset = vec2(3.2307692308 * texelWidthOffset, 3.2307692308 * texelHeightOffset) * blurSize;\n     \n     centerTextureCoordinate = inputTextureCoordinate;\n     oneStepLeftTextureCoordinate = inputTextureCoordinate - firstOffset;\n     twoStepsLeftTextureCoordinate = inputTextureCoordinate - secondOffset;\n     oneStepRightTextureCoordinate = inputTextureCoordinate + firstOffset;\n     twoStepsRightTextureCoordinate = inputTextureCoordinate + secondOffset;\n }";
    protected float mBlurSize;
    private int mBlurSizeLocation;

    public GPUImageFastBlurFilter() {
        this(1.0f);
    }

    public GPUImageFastBlurFilter(float f) {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
        this.mBlurSize = 1.0f;
        this.mBlurSize = f;
    }

    public void setBlurSize(float f) {
        this.mBlurSize = f;
        setFloat(this.mBlurSizeLocation, f);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mBlurSizeLocation = GLES20.glGetUniformLocation(getProgram(), "blurSize");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setBlurSize(this.mBlurSize);
    }
}
