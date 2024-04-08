package com.picspool.lib.filter.gpu.normal;

/* loaded from: classes3.dex */
public class GPUImageEmbossFilter extends GPUImage3x3ConvolutionFilter {
    private float mIntensity;

    public GPUImageEmbossFilter() {
        this(1.0f);
    }

    public GPUImageEmbossFilter(float f) {
        this.mIntensity = f;
    }

    @Override // com.picspool.lib.filter.gpu.normal.GPUImage3x3ConvolutionFilter, com.picspool.lib.filter.gpu.normal.GPUImage3x3TextureSamplingFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        setIntensity(this.mIntensity);
    }

    public void setIntensity(float f) {
        this.mIntensity = f;
        float f2 = -f;
        setConvolutionKernel(new float[]{(-2.0f) * f, f2, 0.0f, f2, 1.0f, f, 0.0f, f, f * 2.0f});
    }

    public float getIntensity() {
        return this.mIntensity;
    }
}
