package com.photo.editor.square.splash.view.view.bean;

import android.graphics.ColorMatrix;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class FiltereTypes {
    private GPUImageFilter adjustImageFilter;
    private ColorMatrix colorMatrix;
    private GPUFilterType filterFilterType;

    public GPUFilterType getFilterFilterType() {
        return this.filterFilterType;
    }

    public void setFilterFilterType(GPUFilterType gPUFilterType) {
        this.filterFilterType = gPUFilterType;
    }

    public GPUImageFilter getAdjustImageFilter() {
        return this.adjustImageFilter;
    }

    public void setAdjustImageFilter(GPUImageFilter gPUImageFilter) {
        this.adjustImageFilter = gPUImageFilter;
    }

    public ColorMatrix getColorMatrix() {
        return this.colorMatrix;
    }

    public void setColorMatrix(ColorMatrix colorMatrix) {
        this.colorMatrix = colorMatrix;
    }
}
