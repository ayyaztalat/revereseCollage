package com.picspool.lib.service;

import android.content.Context;
import android.os.AsyncTask;

/* loaded from: classes3.dex */
public class DMAsyncMediaDbScan extends AsyncTask<String, Void, DMMediaBucket> {
    Context mContext;
    OnDMMediaDbScanListener mListener;

    public DMAsyncMediaDbScan(Context context) {
        this.mContext = context;
    }

    public void setOnMediaDbScanListener(OnDMMediaDbScanListener onDMMediaDbScanListener) {
        this.mListener = onDMMediaDbScanListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public DMMediaBucket doInBackground(String... strArr) {
        return new DMMediaDbScan().scan(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(DMMediaBucket dMMediaBucket) {
        OnDMMediaDbScanListener onDMMediaDbScanListener = this.mListener;
        if (onDMMediaDbScanListener != null) {
            onDMMediaDbScanListener.onMediaDbScanFinish(dMMediaBucket);
        }
    }
}
