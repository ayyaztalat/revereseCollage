package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImagePIPBlurAllFilter extends GPUImageFilter {
    private static final String PIP_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;  \nuniform highp float widthAndHeightRatio;\nconst lowp int GAUSSIAN_SAMPLES = 8;\nvarying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];\nhighp vec4 getBlurColor(highp vec4 textureColor)\n {\n\tlowp vec3 sum = vec3(0.0);\n   lowp vec4 fragColor=texture2D(inputImageTexture,textureCoordinate);\n    sum += texture2D(inputImageTexture, blurCoordinates[0]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[1]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[2]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[3]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[4]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[5]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[6]).rgb * 0.125;\n    sum += texture2D(inputImageTexture, blurCoordinates[7]).rgb * 0.125;\n\t gl_FragColor = vec4(sum,fragColor.a);\n    return gl_FragColor;\n} \nvoid main()\n{\n    highp vec4 srcColor = texture2D(inputImageTexture, textureCoordinate);\n    highp vec4 blurColor = srcColor;\n    blurColor = getBlurColor(srcColor);\n    gl_FragColor = blurColor;\n}\n";
    private static final String VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nvarying vec2 textureCoordinate;\nconst int GAUSSIAN_SAMPLES = 8;\nvarying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n uniform highp float texelWidthOffset; \n uniform highp float texelHeightOffset; \n uniform highp float blurSize;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n\t// Calculate the positions for the blur\n\tint multiplier = 0;\n\tvec2 blurStep;\n   vec2 singleStepOffset = vec2(texelHeightOffset, texelWidthOffset);\n   blurStep = singleStepOffset * blurSize;\n   blurCoordinates[0] = vec2(inputTextureCoordinate.x - blurStep.x,inputTextureCoordinate.y - blurStep.y);   blurCoordinates[1] = vec2(inputTextureCoordinate.x,inputTextureCoordinate.y - blurStep.y);   blurCoordinates[2] = vec2(inputTextureCoordinate.x + blurStep.x,inputTextureCoordinate.y - blurStep.y);   blurCoordinates[3] = vec2(inputTextureCoordinate.x - blurStep.x,inputTextureCoordinate.y);   blurCoordinates[4] = vec2(inputTextureCoordinate.x + blurStep.x,inputTextureCoordinate.y);   blurCoordinates[5] = vec2(inputTextureCoordinate.x - blurStep.x,inputTextureCoordinate.y + blurStep.y);   blurCoordinates[6] = vec2(inputTextureCoordinate.x,inputTextureCoordinate.y + blurStep.y);   blurCoordinates[7] = vec2(inputTextureCoordinate.x + blurStep.x,inputTextureCoordinate.y + blurStep.y);}";
    private float mBlurSize;
    private int mBlurSizeLocation;
    private float mTexelHeightOffset;
    private float mTexelWidthOffset;
    private int texelHeightOffsetLocation;
    private int texelWidthOffsetLocation;

    public GPUImagePIPBlurAllFilter() {
        super(VERTEX_SHADER, PIP_FRAGMENT_SHADER);
        this.mBlurSize = 6.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.texelWidthOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "texelWidthOffset");
        this.texelHeightOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "texelHeightOffset");
        this.mBlurSizeLocation = GLES20.glGetUniformLocation(getProgram(), "blurSize");
        setBlurSize(this.mBlurSize);
        setTexelWidthOffset(this.mTexelWidthOffset);
        setTexelHeightOffset(this.mTexelHeightOffset);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        this.mTexelWidthOffset = 1.0f / i;
        this.mTexelHeightOffset = 1.0f / i2;
    }

    public void setTexelWidthOffset(float f) {
        this.mTexelWidthOffset = f;
        setFloat(this.texelWidthOffsetLocation, f);
    }

    public void setTexelHeightOffset(float f) {
        this.mTexelHeightOffset = f;
        setFloat(this.texelHeightOffsetLocation, f);
    }

    public void setBlurSize(float f) {
        this.mBlurSize = f;
        setFloat(this.mBlurSizeLocation, f);
    }
}
