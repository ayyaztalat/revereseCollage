package com.picspool.lib.filter.gpu;

import android.graphics.Bitmap;
import android.os.Handler;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;

/* loaded from: classes3.dex */
public class AsyncGPUFilter23 {
    private final Handler handler = new Handler();
    private GPUImageFilter mFilter;
    private OnPostFilteredListener mListener;
    private Bitmap mSrc;

    public static void executeAsyncFilter(Bitmap bitmap, GPUImageFilter gPUImageFilter, final OnPostFilteredListener onPostFilteredListener) {
        final AsyncGpuFilterPool asyncGpuFilterPool = new AsyncGpuFilterPool();
        asyncGpuFilterPool.initExecutor();
        asyncGpuFilterPool.setData(bitmap, gPUImageFilter, new OnPostFilteredListener() { // from class: com.picspool.lib.filter.gpu.AsyncGPUFilter23.1
            @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
            public void postFiltered(Bitmap bitmap2) {
                asyncGpuFilterPool.shutdownLoder();
                OnPostFilteredListener onPostFilteredListener2 = onPostFilteredListener;
                if (onPostFilteredListener2 != null) {
                    onPostFilteredListener2.postFiltered(bitmap2);
                }
            }
        });
        asyncGpuFilterPool.execute();
    }

    public void setData(Bitmap bitmap, GPUImageFilter gPUImageFilter, OnPostFilteredListener onPostFilteredListener) {
        this.mSrc = bitmap;
        this.mFilter = gPUImageFilter;
        this.mListener = onPostFilteredListener;
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.picspool.lib.filter.gpu.AsyncGPUFilter23.2
            @Override // java.lang.Runnable
            public void run() {
                final Bitmap filter = AsyncGpuFliterUtil.filter(AsyncGPUFilter23.this.mSrc, AsyncGPUFilter23.this.mFilter);
                AsyncGPUFilter23.this.handler.post(new Runnable() { // from class: com.picspool.lib.filter.gpu.AsyncGPUFilter23.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (AsyncGPUFilter23.this.mListener != null) {
                            AsyncGPUFilter23.this.mSrc = null;
                            AsyncGPUFilter23.this.mListener.postFiltered(filter);
                        }
                    }
                });
            }
        }).start();
    }
}
