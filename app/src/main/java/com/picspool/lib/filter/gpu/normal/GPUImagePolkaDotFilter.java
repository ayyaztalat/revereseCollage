package com.picspool.lib.filter.gpu.normal;

import android.opengl.GLES20;

/* loaded from: classes3.dex */
public class GPUImagePolkaDotFilter extends GPUImagePixelationFilter {
    public static final String POLKA_DOT_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;uniform sampler2D inputImageTexture; uniform highp float fractionalWidthOfPixel;uniform highp float aspectRatio;uniform highp float dotScaling; void main(){highp vec2 sampleDivisor = vec2(fractionalWidthOfPixel,fractionalWidthOfPixel / aspectRatio); highp vec2 samplePos = textureCoordinate - mod(textureCoordinate,sampleDivisor) + 0.5 * sampleDivisor;highp vec2 textureCoordinateToUse = vec2(textureCoordinate.x,(textureCoordinate.y * aspectRatio + 0.5 - 0.5 * aspectRatio));highp vec2 adjustedSamplePos = vec2(samplePos.x, (samplePos.y *aspectRatio + 0.5 - 0.5 * aspectRatio));highp float distanceFromSamplePoint = distance(adjustedSamplePos,textureCoordinateToUse);lowp float checkForPresenceWithinDot = step(distanceFromSamplePoint,(fractionalWidthOfPixel * 0.5) * dotScaling);lowp vec4 inputColor = texture2D(inputImageTexture, samplePos); gl_FragColor = vec4(inputColor.rgb * checkForPresenceWithinDot,inputColor.a);}";
    private float dotScaling;
    private int dotScalingLocation;

    public GPUImagePolkaDotFilter() {
        super(POLKA_DOT_FRAGMENT_SHADER);
        this.dotScaling = 1.0f;
    }

    @Override // com.picspool.lib.filter.gpu.normal.GPUImagePixelationFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.dotScalingLocation = GLES20.glGetUniformLocation(getProgram(), "dotScaling");
    }

    @Override // com.picspool.lib.filter.gpu.normal.GPUImagePixelationFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setDotScaling(this.dotScaling);
    }

    public void setDotScaling(float f) {
        this.dotScaling = f;
        setFloat(this.dotScalingLocation, f);
    }
}
