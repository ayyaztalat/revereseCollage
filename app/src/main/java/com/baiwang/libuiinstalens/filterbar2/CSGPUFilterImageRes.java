package com.baiwang.libuiinstalens.filterbar2;

import android.graphics.Bitmap;

import com.picspool.instafilter.GPUFilter;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSGPUFilterImageRes extends DMWBImageRes {
    private GPUFilterType filterType = GPUFilterType.NOFILTER;
    private Bitmap src = null;
    private Bitmap filtered = null;

    public void setFilterType(GPUFilterType gPUFilterType) {
        this.filterType = gPUFilterType;
    }

    public GPUFilterType getFilterType() {
        return this.filterType;
    }

    public void setSRC(Bitmap bitmap) {
        this.src = bitmap;
    }

    public Bitmap getSRC() {
        return this.src;
    }

    @Override // org.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        if (getIconType() != DMWBRes.LocationType.FILTERED) {
            return getIconType() == DMWBRes.LocationType.RES ? DMBitmapDbUtil.getImageFromResourceFile(this.context, getIconID()) : DMBitmapUtil.getImageFromAssetsFile(getResources(), getIconFileName());
        }
        this.asyncIcon = true;
        return this.src;
    }

    @Override // org.picspool.lib.resource.DMWBRes
    public void getAsyncIconBitmap(final DMWBAsyncPostIconListener dMWBAsyncPostIconListener) {
        if (getFiltered() != null && !getFiltered().isRecycled()) {
            dMWBAsyncPostIconListener.postIcon(getFiltered());
            return;
        }
        try {
                GPUFilter.asyncFilterForType(this.context, this.src, this.filterType, bitmap -> {
                    CSGPUFilterImageRes.this.setFiltered(bitmap);
                    dMWBAsyncPostIconListener.postIcon(CSGPUFilterImageRes.this.getFiltered());
                });
        } catch (Throwable ignored) {}
    }

    public void dispose() {
        Bitmap bitmap = this.filtered;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.filtered.recycle();
        }
        this.filtered = null;
    }

    public Bitmap getFiltered() {
        return this.filtered;
    }

    public void setFiltered(Bitmap bitmap) {
        this.filtered = bitmap;
    }
}
