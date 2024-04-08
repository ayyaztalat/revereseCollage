package com.picspool.lib.filter.gpu;

import android.graphics.Bitmap;
import android.os.Handler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;

/* loaded from: classes3.dex */
public class AsyncGpuFilterPool {
    private ExecutorService executorService;
    private final Handler handler = new Handler();
    private GPUImageFilter mFilter;
    private OnPostFilteredListener mListener;
    private Bitmap mSrc;

    public void initLoader() {
        initExecutor();
    }

    public void shutdownLoder() {
        shutDownExecutor();
    }

    public void initExecutor() {
        if (this.executorService != null) {
            shutDownExecutor();
        }
        this.executorService = Executors.newFixedThreadPool(1);
    }

    public void shutDownExecutor() {
        ExecutorService executorService = this.executorService;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public void setData(Bitmap bitmap, GPUImageFilter gPUImageFilter, OnPostFilteredListener onPostFilteredListener) {
        this.mSrc = bitmap;
        this.mFilter = gPUImageFilter;
        this.mListener = onPostFilteredListener;
    }

    public void execute() {
        this.executorService.submit(new Runnable() { // from class: com.picspool.lib.filter.gpu.AsyncGpuFilterPool.1
            @Override // java.lang.Runnable
            public void run() {
                final Bitmap filter = AsyncGpuFliterUtil.filter(AsyncGpuFilterPool.this.mSrc, AsyncGpuFilterPool.this.mFilter);
                AsyncGpuFilterPool.this.handler.post(new Runnable() { // from class: com.picspool.lib.filter.gpu.AsyncGpuFilterPool.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (AsyncGpuFilterPool.this.mListener != null) {
                            AsyncGpuFilterPool.this.mSrc = null;
                            AsyncGpuFilterPool.this.mListener.postFiltered(filter);
                        }
                    }
                });
            }
        });
    }
}
