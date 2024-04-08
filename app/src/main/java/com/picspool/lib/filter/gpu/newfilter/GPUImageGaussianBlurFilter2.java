package com.picspool.lib.filter.gpu.newfilter;

import com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter;

/* loaded from: classes3.dex */
public class GPUImageGaussianBlurFilter2 extends GPUImageTwoPassTextureSamplingFilter {
    public static final String FRAGMENT_SHADER = "uniform sampler2D inputImageTexture;\n\nconst lowp int GAUSSIAN_SAMPLES = 9;\n\nvarying highp vec2 textureCoordinate;\nvarying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n\nvoid main()\n{\n\tlowp vec3 sum = vec3(0.0);\n   lowp vec4 fragColor=texture2D(inputImageTexture,textureCoordinate);\n\t\n\tif(fragColor.a<=0.0001){       sum += texture2D(inputImageTexture, blurCoordinates[0]).rgb * 0.05;\n       sum += texture2D(inputImageTexture, blurCoordinates[1]).rgb * 0.09;\n       sum += texture2D(inputImageTexture, blurCoordinates[2]).rgb * 0.12;\n       sum += texture2D(inputImageTexture, blurCoordinates[3]).rgb * 0.15;\n       sum += texture2D(inputImageTexture, blurCoordinates[4]).rgb * 0.18;\n       sum += texture2D(inputImageTexture, blurCoordinates[5]).rgb * 0.15;\n       sum += texture2D(inputImageTexture, blurCoordinates[6]).rgb * 0.12;\n       sum += texture2D(inputImageTexture, blurCoordinates[7]).rgb * 0.09;\n       sum += texture2D(inputImageTexture, blurCoordinates[8]).rgb * 0.05;\n\t    gl_FragColor = vec4(sum,fragColor.a);\n   }else{ \n       gl_FragColor = fragColor;\n   } \n}";
    public static final String VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nconst int GAUSSIAN_SAMPLES = 9;\n\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\n\nvarying vec2 textureCoordinate;\nvarying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n\nvoid main()\n{\n\tgl_Position = position;\n\ttextureCoordinate = inputTextureCoordinate.xy;\n\t\n\t// Calculate the positions for the blur\n\tint multiplier = 0;\n\tvec2 blurStep;\n   vec2 singleStepOffset = vec2(texelHeightOffset, texelWidthOffset);\n    \n\tfor (int i = 0; i < GAUSSIAN_SAMPLES; i++)\n   {\n\t\tmultiplier = (i - ((GAUSSIAN_SAMPLES - 1) / 2));\n       // Blur in x (horizontal)\n       blurStep = float(multiplier) * singleStepOffset;\n\t\tblurCoordinates[i] = inputTextureCoordinate.xy + blurStep;\n\t}\n}\n";
    protected float mBlurSize;

    public GPUImageGaussianBlurFilter2() {
        this(1.0f);
    }

    public GPUImageGaussianBlurFilter2(float f) {
        super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nconst int GAUSSIAN_SAMPLES = 9;\n\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\n\nvarying vec2 textureCoordinate;\nvarying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n\nvoid main()\n{\n\tgl_Position = position;\n\ttextureCoordinate = inputTextureCoordinate.xy;\n\t\n\t// Calculate the positions for the blur\n\tint multiplier = 0;\n\tvec2 blurStep;\n   vec2 singleStepOffset = vec2(texelHeightOffset, texelWidthOffset);\n    \n\tfor (int i = 0; i < GAUSSIAN_SAMPLES; i++)\n   {\n\t\tmultiplier = (i - ((GAUSSIAN_SAMPLES - 1) / 2));\n       // Blur in x (horizontal)\n       blurStep = float(multiplier) * singleStepOffset;\n\t\tblurCoordinates[i] = inputTextureCoordinate.xy + blurStep;\n\t}\n}\n", FRAGMENT_SHADER, "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n\nconst int GAUSSIAN_SAMPLES = 9;\n\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\n\nvarying vec2 textureCoordinate;\nvarying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n\nvoid main()\n{\n\tgl_Position = position;\n\ttextureCoordinate = inputTextureCoordinate.xy;\n\t\n\t// Calculate the positions for the blur\n\tint multiplier = 0;\n\tvec2 blurStep;\n   vec2 singleStepOffset = vec2(texelHeightOffset, texelWidthOffset);\n    \n\tfor (int i = 0; i < GAUSSIAN_SAMPLES; i++)\n   {\n\t\tmultiplier = (i - ((GAUSSIAN_SAMPLES - 1) / 2));\n       // Blur in x (horizontal)\n       blurStep = float(multiplier) * singleStepOffset;\n\t\tblurCoordinates[i] = inputTextureCoordinate.xy + blurStep;\n\t}\n}\n", FRAGMENT_SHADER);
        this.mBlurSize = 1.0f;
        this.mBlurSize = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter
    public float getVerticalTexelOffsetRatio() {
        return this.mBlurSize;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter
    public float getHorizontalTexelOffsetRatio() {
        return this.mBlurSize;
    }

    public void setBlurSize(float f) {
        this.mBlurSize = f;
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.newfilter.GPUImageGaussianBlurFilter2.1
            @Override // java.lang.Runnable
            public void run() {
                GPUImageGaussianBlurFilter2.this.initTexelOffsets();
            }
        });
    }
}
