package com.picspool.lib.filter.gpu.normal;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageNoFilter extends GPUImageFilter {
    public GPUImageNoFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, GPUImageFilter.NO_FILTER_FRAGMENT_SHADER);
    }
}
