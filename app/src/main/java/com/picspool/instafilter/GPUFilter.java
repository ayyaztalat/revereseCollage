package com.picspool.instafilter;

import android.content.Context;
import android.graphics.Bitmap;
import com.picspool.lib.filter.gpu.AsyncGPUFilter23;
import com.picspool.lib.filter.gpu.AsyncGpuFliterUtil;
import com.picspool.lib.filter.gpu.GPUFilterFactory;
import com.picspool.lib.filter.gpu.GPUFilterType;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;

/* loaded from: classes3.dex */
public class GPUFilter {
    public static Bitmap filterForType(Context context, Bitmap bitmap, GPUFilterType gPUFilterType) {
        GPUImageFilter createFilterForType = createFilterForType(context, gPUFilterType);
        Bitmap filterForFilter = filterForFilter(bitmap, createFilterForType);
        recycleTexture(createFilterForType);
        return filterForFilter;
    }

    public static Bitmap filterForType(Context context, Bitmap bitmap, GPUFilterType gPUFilterType, float f) {
        GPUImageFilter createFilterForType = createFilterForType(context, gPUFilterType);
        createFilterForType.setMix(f);
        Bitmap filterForFilter = filterForFilter(bitmap, createFilterForType);
        recycleTexture(createFilterForType);
        return filterForFilter;
    }

    public static void asyncFilterForType(Context context, Bitmap bitmap, GPUFilterType gPUFilterType, OnPostFilteredListener onPostFilteredListener) {
        asyncFilterForFilter(bitmap, createFilterForType(context, gPUFilterType), onPostFilteredListener);
    }

    public static Bitmap filterForFilter(Bitmap bitmap, GPUImageFilter gPUImageFilter) {
        return filterForFilter(bitmap, gPUImageFilter, true);
    }

    public static Bitmap filterForFilter(Bitmap bitmap, GPUImageFilter gPUImageFilter, boolean z) {
        Bitmap filter = AsyncGpuFliterUtil.filter(bitmap, gPUImageFilter);
        recycleTexture(gPUImageFilter, z);
        return filter;
    }

    public static void asyncFilterForFilter(Bitmap bitmap, final GPUImageFilter gPUImageFilter, final OnPostFilteredListener onPostFilteredListener) {
        AsyncGPUFilter23.executeAsyncFilter(bitmap, gPUImageFilter, new OnPostFilteredListener() { // from class: com.picspool.instafilter.GPUFilter.1
            @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
            public void postFiltered(Bitmap bitmap2) {
                GPUFilter.recycleTexture(gPUImageFilter);
                onPostFilteredListener.postFiltered(bitmap2);
            }
        });
    }

    public static void asyncFilterForFilterNotRecycle(Bitmap bitmap, GPUImageFilter gPUImageFilter, final OnPostFilteredListener onPostFilteredListener) {
        AsyncGPUFilter23.executeAsyncFilter(bitmap, gPUImageFilter, new OnPostFilteredListener() { // from class: com.picspool.instafilter.GPUFilter.2
            @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
            public void postFiltered(Bitmap bitmap2) {
                onPostFilteredListener.postFiltered(bitmap2);
            }
        });
    }

    public static GPUImageFilter createFilterForType(Context context, GPUFilterType gPUFilterType) {
        return GPUFilterFactory.createFilterForType(context, gPUFilterType);
    }

    public static GPUImageFilter createFilterForBlendType(Context context, GPUFilterType gPUFilterType, Bitmap bitmap) {
        GPUImageTwoInputFilter gPUImageTwoInputFilter = (GPUImageTwoInputFilter) GPUFilterFactory.createFilterForType(context, gPUFilterType);
        if (gPUImageTwoInputFilter == null || bitmap == null || bitmap.isRecycled()) {
            return new GPUImageFilter();
        }
        gPUImageTwoInputFilter.setBitmap(bitmap);
        return gPUImageTwoInputFilter;
    }

    public static void recycleTexture(GPUImageFilter gPUImageFilter) {
        recycleTexture(gPUImageFilter, true);
    }

    public static void recycleTexture(GPUImageFilter gPUImageFilter, boolean z) {
        AsyncGpuFliterUtil.recycleTexture(gPUImageFilter, z);
    }
}
