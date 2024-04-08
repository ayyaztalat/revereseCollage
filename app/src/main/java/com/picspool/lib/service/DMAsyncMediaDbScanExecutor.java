package com.picspool.lib.service;

import android.content.Context;
import android.os.Handler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class DMAsyncMediaDbScanExecutor {
    private static DMAsyncMediaDbScanExecutor loader;
    private ExecutorService executorService;
    private final Handler handler = new Handler();
    Context mContext;
    OnDMMediaDbScanListener mListener;
    DMMediaDbScan mScan;

    public static DMAsyncMediaDbScanExecutor getInstance() {
        return loader;
    }

    public static void initThumbLoader(Context context, DMMediaDbScan dMMediaDbScan) {
        if (loader == null) {
            loader = new DMAsyncMediaDbScanExecutor(context, dMMediaDbScan);
        }
        loader.initExecutor();
    }

    public static void shutdownThumbLoder() {
        DMAsyncMediaDbScanExecutor dMAsyncMediaDbScanExecutor = loader;
        if (dMAsyncMediaDbScanExecutor != null) {
            dMAsyncMediaDbScanExecutor.shutDownExecutor();
        }
        loader = null;
    }

    public DMAsyncMediaDbScanExecutor(Context context, DMMediaDbScan dMMediaDbScan) {
        this.mContext = context;
        this.mScan = dMMediaDbScan;
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
        this.mContext = null;
    }

    public void setOnMediaDbScanListener(OnDMMediaDbScanListener onDMMediaDbScanListener) {
        this.mListener = onDMMediaDbScanListener;
    }

    public void execute() {
        this.executorService.submit(new Runnable() { // from class: com.picspool.lib.service.DMAsyncMediaDbScanExecutor.1
            @Override // java.lang.Runnable
            public void run() {
                final DMMediaBucket scan = DMAsyncMediaDbScanExecutor.this.mScan.scan(DMAsyncMediaDbScanExecutor.this.mContext);
                DMAsyncMediaDbScanExecutor.this.handler.post(new Runnable() { // from class: com.picspool.lib.service.DMAsyncMediaDbScanExecutor.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DMAsyncMediaDbScanExecutor.this.mListener != null) {
                            DMAsyncMediaDbScanExecutor.this.mListener.onMediaDbScanFinish(scan);
                        }
                    }
                });
            }
        });
    }
}
