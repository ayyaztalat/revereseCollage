package com.picspool.lib.filter.cpu;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import com.picspool.lib.filter.listener.OnPostFilteredListener;

/* loaded from: classes3.dex */
public class AsyncCPUFilter {
    private final Handler handler = new Handler();
    private Context mContext;
    private CPUFilterType mFilterType;
    private OnPostFilteredListener mListener;
    private Bitmap mSrc;

    public static Bitmap filter(Context context, Bitmap bitmap, CPUFilterType cPUFilterType) {
        if (bitmap != null && !bitmap.isRecycled()) {
            try {
                return CPUFilterFactory.filter(context, bitmap, cPUFilterType);
            } catch (Throwable unused) {
            }
        }
        return bitmap;
    }

    public static void executeAsyncFilter(Context context, Bitmap bitmap, CPUFilterType cPUFilterType, OnPostFilteredListener onPostFilteredListener) {
        AsyncCPUFilter asyncCPUFilter = new AsyncCPUFilter();
        asyncCPUFilter.setData(context, bitmap, cPUFilterType, onPostFilteredListener);
        asyncCPUFilter.execute();
    }

    public void setData(Context context, Bitmap bitmap, CPUFilterType cPUFilterType, OnPostFilteredListener onPostFilteredListener) {
        this.mContext = context;
        this.mSrc = bitmap;
        this.mFilterType = cPUFilterType;
        this.mListener = onPostFilteredListener;
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.picspool.lib.filter.cpu.AsyncCPUFilter.1
            @Override // java.lang.Runnable
            public void run() {
                final Bitmap filter = CPUFilterFactory.filter(AsyncCPUFilter.this.mContext, AsyncCPUFilter.this.mSrc, AsyncCPUFilter.this.mFilterType);
                AsyncCPUFilter.this.handler.post(new Runnable() { // from class: com.picspool.lib.filter.cpu.AsyncCPUFilter.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (AsyncCPUFilter.this.mListener != null) {
                            AsyncCPUFilter.this.mListener.postFiltered(filter);
                        }
                    }
                });
            }
        }).start();
    }
}
