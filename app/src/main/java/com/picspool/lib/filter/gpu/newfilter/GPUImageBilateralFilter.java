package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter;

/* loaded from: classes3.dex */
public class GPUImageBilateralFilter extends GPUImageTwoPassTextureSamplingFilter {
    public static final String FRAGMENT_SHADER = " uniform sampler2D inputImageTexture; \n const lowp int GAUSSIAN_SAMPLES = 9; \n varying highp vec2 textureCoordinate; \n varying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES]; \n \n uniform mediump float distanceNormalizationFactor; \n \n void main() \n { \n     lowp vec4 centralColor; \n     lowp float gaussianWeightTotal; \n     lowp vec4 sum; \n     lowp vec4 sampleColor; \n     lowp float distanceFromCentralColor; \n     lowp float gaussianWeight; \n \n     centralColor = texture2D(inputImageTexture, blurCoordinates[4]); \n     gaussianWeightTotal = 0.18; \n     sum = centralColor * 0.18; \n \n     sampleColor = texture2D(inputImageTexture, blurCoordinates[0]); \n     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0); \n     gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor); \n     gaussianWeightTotal += gaussianWeight; \n     sum += sampleColor * gaussianWeight; \n \n     sampleColor = texture2D(inputImageTexture, blurCoordinates[1]); \n     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0); \n     gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor); \n     gaussianWeightTotal += gaussianWeight; \n     sum += sampleColor * gaussianWeight; \n \n     sampleColor = texture2D(inputImageTexture, blurCoordinates[2]); \n     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0); \n     gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor); \n     gaussianWeightTotal += gaussianWeight; \n     sum += sampleColor * gaussianWeight; \n \n     sampleColor = texture2D(inputImageTexture, blurCoordinates[3]); \n     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0); \n     gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor); \n     gaussianWeightTotal += gaussianWeight; \n     sum += sampleColor * gaussianWeight; \n \n     sampleColor = texture2D(inputImageTexture, blurCoordinates[5]); \n     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0); \n     gaussianWeight = 0.15 * (1.0 - distanceFromCentralColor); \n     gaussianWeightTotal += gaussianWeight; \n     sum += sampleColor * gaussianWeight; \n \n     sampleColor = texture2D(inputImageTexture, blurCoordinates[6]); \n     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0); \n     gaussianWeight = 0.12 * (1.0 - distanceFromCentralColor); \n     gaussianWeightTotal += gaussianWeight; \n     sum += sampleColor * gaussianWeight; \n \n     sampleColor = texture2D(inputImageTexture, blurCoordinates[7]); \n     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0); \n     gaussianWeight = 0.09 * (1.0 - distanceFromCentralColor); \n     gaussianWeightTotal += gaussianWeight; \n     sum += sampleColor * gaussianWeight; \n \n     sampleColor = texture2D(inputImageTexture, blurCoordinates[8]); \n     distanceFromCentralColor = min(distance(centralColor, sampleColor) * distanceNormalizationFactor, 1.0); \n     gaussianWeight = 0.05 * (1.0 - distanceFromCentralColor); \n     gaussianWeightTotal += gaussianWeight; \n     sum += sampleColor * gaussianWeight; \n \n     gl_FragColor = sum / gaussianWeightTotal; \n } \n";
    public static final String VERTEX_SHADER = " attribute vec4 position; \n attribute vec4 inputTextureCoordinate; \n const int GAUSSIAN_SAMPLES = 9; \n uniform float texelWidthOffset; \n uniform float texelHeightOffset; \n varying vec2 textureCoordinate; \n varying vec2 blurCoordinates[GAUSSIAN_SAMPLES]; \n void main() \n { \n     gl_Position = position; \n     textureCoordinate = inputTextureCoordinate.xy; \n \n     int multiplier = 0; \n     vec2 blurStep; \n     vec2 singleStepOffset = vec2(texelWidthOffset, texelHeightOffset); \n \n     for (int i = 0; i < GAUSSIAN_SAMPLES; i++) \n     { \n         multiplier = (i - ((GAUSSIAN_SAMPLES - 1) / 2)); \n         // Blur in x (horizontal) \n         blurStep = float(multiplier) * singleStepOffset; \n         blurCoordinates[i] = inputTextureCoordinate.xy + blurStep; \n     } \n } \n";
    float distanceNormalizationFactor;
    int firstDistanceNormalizationFactorUniform;
    int secondDistanceNormalizationFactorUniform;

    public GPUImageBilateralFilter() {
        super(VERTEX_SHADER, FRAGMENT_SHADER, VERTEX_SHADER, FRAGMENT_SHADER);
        this.distanceNormalizationFactor = 8.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoPassTextureSamplingFilter, com.picspool.lib.filter.gpu.father.GPUImageFilterGroup, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        setDistanceNormalizationFactor(this.distanceNormalizationFactor);
        this.firstDistanceNormalizationFactorUniform = GLES20.glGetUniformLocation(this.mFilters.get(0).getProgram(), "distanceNormalizationFactor");
        this.secondDistanceNormalizationFactorUniform = GLES20.glGetUniformLocation(this.mFilters.get(1).getProgram(), "distanceNormalizationFactor");
    }

    public void setDistanceNormalizationFactor(float f) {
        this.distanceNormalizationFactor = f;
        this.mFilters.get(0).setFloat(this.firstDistanceNormalizationFactorUniform, f);
        this.mFilters.get(1).setFloat(this.secondDistanceNormalizationFactorUniform, f);
    }
}
