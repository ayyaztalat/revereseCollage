package com.picspool.lib.service;

import android.content.Context;
import android.os.Handler;

/* loaded from: classes3.dex */
public class DMAsyncMediaDbScan23 {
    private final Handler handler = new Handler();
    Context mContext;
    OnDMMediaDbScanListener mListener;
    DMMediaDbScan mScan;

    public DMAsyncMediaDbScan23(Context context, DMMediaDbScan dMMediaDbScan) {
        this.mScan = null;
        this.mContext = context;
        this.mScan = dMMediaDbScan;
    }

    public void setOnMediaDbScanListener(OnDMMediaDbScanListener onDMMediaDbScanListener) {
        this.mListener = onDMMediaDbScanListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void run() {
        final DMMediaBucket scan = this.mScan.scan(this.mContext);
        this.handler.post(new Runnable() { // from class: com.picspool.lib.service.DMAsyncMediaDbScan23.1
            @Override // java.lang.Runnable
            public void run() {
                if (DMAsyncMediaDbScan23.this.mListener != null) {
                    DMAsyncMediaDbScan23.this.mListener.onMediaDbScanFinish(scan);
                }
            }
        });
    }

    public void execute() {
        new myThread(this).start();
    }

    /* loaded from: classes3.dex */
    static class myThread extends Thread {
        DMAsyncMediaDbScan23 mAsy23;

        public myThread(DMAsyncMediaDbScan23 dMAsyncMediaDbScan23) {
            this.mAsy23 = dMAsyncMediaDbScan23;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            this.mAsy23.run();
        }
    }
}
