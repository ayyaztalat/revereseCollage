package com.picspool.instafilter;

import android.content.Context;
import android.graphics.Bitmap;
import com.picspool.lib.filter.cpu.AsyncCPUFilter;
import com.picspool.lib.filter.cpu.CPUFilterType;
import com.picspool.lib.filter.listener.OnPostFilteredListener;

/* loaded from: classes3.dex */
public class CPUFilter {
    public static Bitmap filterForType(Context context, Bitmap bitmap, CPUFilterType cPUFilterType) {
        return AsyncCPUFilter.filter(context, bitmap, cPUFilterType);
    }

    public static void asyncFilterForType(Context context, Bitmap bitmap, CPUFilterType cPUFilterType, final OnPostFilteredListener onPostFilteredListener) {
        // from class: com.picspool.instafilter.CPUFilter.1
// com.picspool.lib.filter.listener.OnPostFilteredListener
        AsyncCPUFilter.executeAsyncFilter(context, bitmap, cPUFilterType, bitmap2 -> onPostFilteredListener.postFiltered(bitmap2));
    }
}
