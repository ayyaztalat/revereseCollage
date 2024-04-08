package com.picspool.lib.filter.gpu;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;

/* loaded from: classes3.dex */
public class AsyncGPUFilter extends AsyncTask<String, Void, Bitmap> {
    private GPUImageFilter mFilter;
    private OnPostFilteredListener mListener;
    private Bitmap mSrc;

    public static void executeAsyncFilter(Bitmap bitmap, GPUImageFilter gPUImageFilter, OnPostFilteredListener onPostFilteredListener) {
        AsyncGPUFilter asyncGPUFilter = new AsyncGPUFilter();
        asyncGPUFilter.setData(bitmap, gPUImageFilter, onPostFilteredListener);
        asyncGPUFilter.execute(new String[0]);
    }

    public void setData(Bitmap bitmap, GPUImageFilter gPUImageFilter, OnPostFilteredListener onPostFilteredListener) {
        this.mSrc = bitmap;
        this.mFilter = gPUImageFilter;
        this.mListener = onPostFilteredListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Bitmap doInBackground(String... strArr) {
        return AsyncGpuFliterUtil.filter(this.mSrc, this.mFilter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Bitmap bitmap) {
        OnPostFilteredListener onPostFilteredListener = this.mListener;
        if (onPostFilteredListener != null) {
            this.mSrc = null;
            onPostFilteredListener.postFiltered(bitmap);
        }
    }
}
