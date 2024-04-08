package com.photo.editor.square.splash.view.glitch;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;

/* loaded from: classes2.dex */
public class IL20 extends AsyncTask<Void, Void, Bitmap> {
    private GPUImageFilter mFilter;
    private OnPostFilteredListener mListener;
    private Bitmap mSrc;

    public void shutdownLoder() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Bitmap doInBackground(Void... voidArr) {
        return IL18.filter(this.mSrc, this.mFilter);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        OnPostFilteredListener onPostFilteredListener = this.mListener;
        if (onPostFilteredListener != null) {
            this.mSrc = null;
            onPostFilteredListener.postFiltered(bitmap);
        }
    }

    public void setData(Bitmap bitmap, GPUImageFilter gPUImageFilter, OnPostFilteredListener onPostFilteredListener) {
        this.mSrc = bitmap;
        this.mFilter = gPUImageFilter;
        this.mListener = onPostFilteredListener;
    }
}
