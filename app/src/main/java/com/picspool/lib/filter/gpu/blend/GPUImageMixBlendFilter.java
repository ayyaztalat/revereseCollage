package com.picspool.lib.filter.gpu.blend;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageMixBlendFilter extends GPUImageTwoInputFilter {
    private float mMix;
    private int mMixLocation;

    public GPUImageMixBlendFilter(String str) {
        this(str, 0.5f);
    }

    public GPUImageMixBlendFilter(String str, float f) {
        super(str);
        this.mMix = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mMixLocation = GLES20.glGetUniformLocation(getProgram(), "mixturePercent");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setMix(this.mMix);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void setMix(float f) {
        this.mMix = f;
        setFloat(this.mMixLocation, f);
    }
}
