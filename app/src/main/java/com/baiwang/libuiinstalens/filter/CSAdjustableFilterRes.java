package com.baiwang.libuiinstalens.filter;

import android.content.Context;
import android.graphics.Bitmap;

import com.picspool.instafilter.GPUFilter;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;

/* loaded from: classes.dex */
public class CSAdjustableFilterRes extends GPUFilterRes {
    private int mix = 100;
    private GPUFilterType filterType = GPUFilterType.NOFILTER;
    private Bitmap src = null;
    private Bitmap filtered = null;

    @Override // org.picspool.instafilter.resource.GPUFilterRes
    public void setFilterType(GPUFilterType gPUFilterType) {
        super.setFilterType(gPUFilterType);
        this.filterType = gPUFilterType;
    }

    @Override // org.picspool.instafilter.resource.GPUFilterRes
    public GPUFilterType getFilterType() {
        super.getFilterType();
        return this.filterType;
    }

    @Override // org.picspool.instafilter.resource.GPUFilterRes
    public void setSRC(Bitmap bitmap) {
        super.setSRC(bitmap);
        this.src = bitmap;
    }

    public void setMix(int i) {
        this.mix = i;
    }

    public int getMix() {
        return this.mix;
    }

    @Override // org.picspool.instafilter.resource.GPUFilterRes, org.picspool.lib.resource.DMWBRes
    public void getAsyncIconBitmap(final DMWBAsyncPostIconListener dMWBAsyncPostIconListener) {
        Bitmap bitmap = this.filtered;
        if (bitmap == null || bitmap.isRecycled()) {
            try {
                synchronized (this.src) {
                    asyncFilterForType(this.context, this.src, this.filterType, this.mix, new OnPostFilteredListener() { // from class: com.baiwang.libuiinstalens.filter.CSAdjustableFilterRes.1
                        @Override // org.picspool.lib.filter.listener.OnPostFilteredListener
                        public void postFiltered(Bitmap bitmap2) {
                            CSAdjustableFilterRes.this.filtered = bitmap2;
                            dMWBAsyncPostIconListener.postIcon(CSAdjustableFilterRes.this.filtered);
                        }
                    });
                }
                return;
            } catch (Throwable unused) {
                return;
            }
        }
        dMWBAsyncPostIconListener.postIcon(this.filtered);
    }

    @Override // org.picspool.instafilter.resource.GPUFilterRes
    public void dispose() {
        super.dispose();
        Bitmap bitmap = this.filtered;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.filtered.recycle();
        }
        this.filtered = null;
    }

    public static void asyncFilterForType(Context context, Bitmap bitmap, GPUFilterType gPUFilterType, int i, OnPostFilteredListener onPostFilteredListener) {
        GPUImageFilter createFilterForType = GPUFilter.createFilterForType(context, gPUFilterType);
        createFilterForType.setMix(i / 100.0f);
        GPUFilter.asyncFilterForFilter(bitmap, createFilterForType, onPostFilteredListener);
    }
}
