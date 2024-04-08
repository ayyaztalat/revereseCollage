package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageTwoPassFilter;

/* loaded from: classes3.dex */
public class GPUImageBilateralNewFilter extends GPUImageTwoPassFilter {
    public static final String FRAGMENT_SHADER = "uniform sampler2D inputImageTexture;\nconst lowp int GAUSSIAN_SAMPLES = 9;\nvarying highp vec2 textureCoordinate;\nvarying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n\nuniform mediump float distanceNormalizationFactor;\n\nvoid main()\n{\nlowp vec4 centralColor;\nlowp float gaussianWeightTotal;\nlowp vec4 sum;\nlowp vec4 sampleColor;\nlowp float distanceFromCentralColor;\nlowp float gaussianWeight;\n\ncentralColor = texture2D(inputImageTexture, blurCoordinates[4]);\ngaussianWeightTotal = 0.18;\nsum = centralColor * 0.18;\n\nsampleColor = texture2D(inputImageTexture, blurCoordinates[0]);\ndistanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\ngaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);\ngaussianWeightTotal += gaussianWeight;\nsum += sampleColor * gaussianWeight;\n\nsampleColor = texture2D(inputImageTexture, blurCoordinates[1]);\ndistanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\ngaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);\ngaussianWeightTotal += gaussianWeight;\nsum += sampleColor * gaussianWeight;\n\nsampleColor = texture2D(inputImageTexture, blurCoordinates[2]);\ndistanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\ngaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);\ngaussianWeightTotal += gaussianWeight;\nsum += sampleColor * gaussianWeight;\n\nsampleColor = texture2D(inputImageTexture, blurCoordinates[3]);\ndistanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\ngaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);\ngaussianWeightTotal += gaussianWeight;\nsum += sampleColor * gaussianWeight;\n\nsampleColor = texture2D(inputImageTexture, blurCoordinates[5]);\ndistanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\ngaussianWeight = 0.15 * (1.0 - distanceFromCentralColor);\ngaussianWeightTotal += gaussianWeight;\nsum += sampleColor * gaussianWeight;\n\nsampleColor = texture2D(inputImageTexture, blurCoordinates[6]);\ndistanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\ngaussianWeight = 0.12 * (1.0 - distanceFromCentralColor);\ngaussianWeightTotal += gaussianWeight;\nsum += sampleColor * gaussianWeight;\n\nsampleColor = texture2D(inputImageTexture, blurCoordinates[7]);\ndistanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\ngaussianWeight = 0.09 * (1.0 - distanceFromCentralColor);\ngaussianWeightTotal += gaussianWeight;\nsum += sampleColor * gaussianWeight;\n\nsampleColor = texture2D(inputImageTexture, blurCoordinates[8]);\ndistanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0);\ngaussianWeight = 0.05 * (1.0 - distanceFromCentralColor);\ngaussianWeightTotal += gaussianWeight;\nsum += sampleColor * gaussianWeight;\n\ngl_FragColor = sum / gaussianWeightTotal;\n}\n";
    public static final String VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nconst int GAUSSIAN_SAMPLES = 9;\nuniform float texelWidthOffset;\nuniform float texelHeightOffset;\nvarying vec2 textureCoordinate;\nvarying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\nvoid main()\n{\ngl_Position = position;\ntextureCoordinate = inputTextureCoordinate.xy;\n// Calculate the positions for the blur\nint multiplier = 0;\nvec2 blurStep;\nvec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset);\nfor (int i = 0; i < GAUSSIAN_SAMPLES; i++)\n{\nmultiplier = (i - ((GAUSSIAN_SAMPLES - 1) / 2));\n// Blur in x (horizontal)\nblurStep = float(multiplier) * singleStepOffset;\nblurCoordinates[i] = inputTextureCoordinate.xy + blurStep;\n}\n}\n";
    private float distanceNormalizationFactor;
    private float texelSpacingMultiplier;

    public GPUImageBilateralNewFilter() {
        this(8.0f);
    }

    public GPUImageBilateralNewFilter(float f) {
        super(VERTEX_SHADER, FRAGMENT_SHADER, VERTEX_SHADER, FRAGMENT_SHADER);
        this.distanceNormalizationFactor = 1.0f;
        this.texelSpacingMultiplier = 1.0f;
        this.distanceNormalizationFactor = f;
        this.texelSpacingMultiplier = 4.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilterGroup, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        initTexelOffsets();
    }

    protected void initTexelOffsets() {
        float horizontalTexelOffsetRatio = getHorizontalTexelOffsetRatio();
        GPUImageFilter gPUImageFilter = this.mFilters.get(0);
        gPUImageFilter.setFloat(GLES20.glGetUniformLocation(gPUImageFilter.getProgram(), "distanceNormalizationFactor"), this.distanceNormalizationFactor);
        int glGetUniformLocation = GLES20.glGetUniformLocation(gPUImageFilter.getProgram(), "texelWidthOffset");
        int glGetUniformLocation2 = GLES20.glGetUniformLocation(gPUImageFilter.getProgram(), "texelHeightOffset");
        gPUImageFilter.setFloat(glGetUniformLocation, horizontalTexelOffsetRatio / this.mOutputWidth);
        gPUImageFilter.setFloat(glGetUniformLocation2, 0.0f);
        float verticalTexelOffsetRatio = getVerticalTexelOffsetRatio();
        GPUImageFilter gPUImageFilter2 = this.mFilters.get(1);
        gPUImageFilter2.setFloat(GLES20.glGetUniformLocation(gPUImageFilter2.getProgram(), "distanceNormalizationFactor"), this.distanceNormalizationFactor);
        int glGetUniformLocation3 = GLES20.glGetUniformLocation(gPUImageFilter2.getProgram(), "texelWidthOffset");
        int glGetUniformLocation4 = GLES20.glGetUniformLocation(gPUImageFilter2.getProgram(), "texelHeightOffset");
        gPUImageFilter2.setFloat(glGetUniformLocation3, 0.0f);
        gPUImageFilter2.setFloat(glGetUniformLocation4, verticalTexelOffsetRatio / this.mOutputHeight);
    }

    public void setDistanceNormalizationFactor(float f) {
        this.distanceNormalizationFactor = f;
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.newfilter.GPUImageBilateralNewFilter.1
            @Override // java.lang.Runnable
            public void run() {
                GPUImageBilateralNewFilter.this.initTexelOffsets();
            }
        });
    }

    public void setTexelSpacingMultiplier(float f) {
        this.texelSpacingMultiplier = f;
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.newfilter.GPUImageBilateralNewFilter.2
            @Override // java.lang.Runnable
            public void run() {
                GPUImageBilateralNewFilter.this.initTexelOffsets();
            }
        });
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilterGroup, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        initTexelOffsets();
    }

    public float getVerticalTexelOffsetRatio() {
        return this.texelSpacingMultiplier;
    }

    public float getHorizontalTexelOffsetRatio() {
        return this.texelSpacingMultiplier;
    }
}
