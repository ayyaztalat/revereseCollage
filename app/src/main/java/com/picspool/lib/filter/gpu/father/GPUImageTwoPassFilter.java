package com.picspool.lib.filter.gpu.father;

import java.util.ArrayList;

/* loaded from: classes3.dex */
public class GPUImageTwoPassFilter extends GPUImageFilterGroup {
    public GPUImageTwoPassFilter(String str, String str2, String str3, String str4) {
        super(new ArrayList());
        addFilter(new GPUImageFilter(str, str2));
        addFilter(new GPUImageFilter(str3, str4));
    }
}
