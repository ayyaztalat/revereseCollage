package com.picspool.lib.filter.gpu.normal;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImagePixelationFilter extends GPUImageFilter {
    public static final String PIXELATION_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;uniform sampler2D inputImageTexture;uniform highp float fractionalWidthOfPixel;uniform highp float aspectRatio;void main(){highp vec2 sampleDivisor = vec2(fractionalWidthOfPixel, fractionalWidthOfPixel / aspectRatio);highp vec2 samplePos = textureCoordinate - mod(textureCoordinate, sampleDivisor) + 0.5 * sampleDivisor;gl_FragColor = texture2D(inputImageTexture, samplePos );}";
    private float aspectRatio;
    private int aspectRatioLocation;
    private float fractionalWidthOfPixel;
    private int fractionalWidthOfPixelLocation;
    private boolean isLandscape;
    private boolean isOutBitmap;
    private float showHeight;
    private float showWidth;

    @Deprecated
    public void setPixel(float f) {
    }

    public GPUImagePixelationFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, PIXELATION_FRAGMENT_SHADER);
        this.fractionalWidthOfPixel = 0.1f;
        this.showWidth = 1.0f;
        this.showHeight = 1.0f;
    }

    public GPUImagePixelationFilter(String str) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, str);
        this.fractionalWidthOfPixel = 0.1f;
        this.showWidth = 1.0f;
        this.showHeight = 1.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.fractionalWidthOfPixelLocation = GLES20.glGetUniformLocation(getProgram(), "fractionalWidthOfPixel");
        this.aspectRatioLocation = GLES20.glGetUniformLocation(getProgram(), "aspectRatio");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setFractionalWidthOfPixel(this.fractionalWidthOfPixel);
        adjustAspectRatio();
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        this.showWidth = i;
        this.showHeight = i2;
        adjustAspectRatio();
    }

    public void setFractionalWidthOfPixel(float f) {
        this.fractionalWidthOfPixel = f;
        setFloat(this.fractionalWidthOfPixelLocation, f);
    }

    public void setImageSize(float f, float f2) {
        if (f > f2) {
            this.isLandscape = true;
        } else {
            this.isLandscape = false;
        }
    }

    private void setAspectRatio(float f) {
        this.aspectRatio = f;
        setFloat(this.aspectRatioLocation, f);
    }

    private void adjustAspectRatio() {
        if (this.isOutBitmap) {
            setAspectRatio(this.showHeight / this.showWidth);
        } else if (!this.isLandscape) {
            setAspectRatio(this.showHeight / this.showWidth);
        } else {
            setAspectRatio(this.showWidth / this.showHeight);
        }
    }

    public boolean isOutBitmap() {
        return this.isOutBitmap;
    }

    public void setOutBitmap(boolean z) {
        this.isOutBitmap = z;
    }
}
