package com.picspool.libfuncview.filter;

import android.content.Context;
import android.graphics.Bitmap;
import com.picspool.instafilter.GPUFilter;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;

/* loaded from: classes.dex */
public class CSAdjustableFilterRes extends CSGPUFilterImageRes {
    private int mix = 100;

    public void setMix(int i) {
        this.mix = i;
    }

    public int getMix() {
        return this.mix;
    }

    @Override // com.picspool.libfuncview.filter.CSGPUFilterImageRes, com.picspool.lib.resource.DMWBRes
    public void getAsyncIconBitmap(final DMWBAsyncPostIconListener dMWBAsyncPostIconListener) {
        if (getFiltered() == null || getFiltered().isRecycled()) {
            try {
                synchronized (getSRC()) {
                    asyncFilterForType(this.context, getSRC(), getFilterType(), this.mix, new OnPostFilteredListener() { // from class: com.picspool.libfuncview.filter.CSAdjustableFilterRes.1
                        @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
                        public void postFiltered(Bitmap bitmap) {
                            CSAdjustableFilterRes.this.setFiltered(bitmap);
                            dMWBAsyncPostIconListener.postIcon(CSAdjustableFilterRes.this.getFiltered());
                        }
                    });
                }
                return;
            } catch (Throwable unused) {
                return;
            }
        }
        dMWBAsyncPostIconListener.postIcon(getFiltered());
    }

    public static void asyncFilterForType(Context context, Bitmap bitmap, GPUFilterType gPUFilterType, int i, OnPostFilteredListener onPostFilteredListener) {
        GPUImageFilter createFilterForType = GPUFilter.createFilterForType(context, gPUFilterType);
        createFilterForType.setMix(i / 100.0f);
        GPUFilter.asyncFilterForFilter(bitmap, createFilterForType, onPostFilteredListener);
    }
}
