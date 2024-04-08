package com.picspool.lib.p017io;

import android.graphics.Bitmap;
import android.os.Handler;

/* renamed from: com.picspool.lib.io.DMAsyncPutPng */
/* loaded from: classes3.dex */
public class DMAsyncPutPng {
    private Bitmap bmp;
    private final Handler handler = new Handler();
    boolean isPut = false;
    private boolean isRecycle;
    private String key;
    private OnPutPngListener listener;

    /* renamed from: com.picspool.lib.io.DMAsyncPutPng$OnPutPngListener */
    /* loaded from: classes3.dex */
    public interface OnPutPngListener {
        void onPutPngProcessFinish(Boolean bool);
    }

    public static void asyncPutPng(String str, Bitmap bitmap, boolean z, OnPutPngListener onPutPngListener) {
        DMAsyncPutPng dMAsyncPutPng = new DMAsyncPutPng();
        dMAsyncPutPng.setData(str, bitmap, z);
        dMAsyncPutPng.setOnPutPngListener(onPutPngListener);
        dMAsyncPutPng.execute();
    }

    public void setData(String str, Bitmap bitmap, boolean z) {
        this.key = str;
        this.bmp = bitmap;
        this.isRecycle = z;
    }

    public void setOnPutPngListener(OnPutPngListener onPutPngListener) {
        this.listener = onPutPngListener;
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.picspool.lib.io.DMAsyncPutPng.1
            @Override // java.lang.Runnable
            public void run() {
                DMAsyncPutPng.this.isPut = false;
                if (DMBitmapIoCache.putPNG(DMAsyncPutPng.this.key, DMAsyncPutPng.this.bmp) != null) {
                    DMAsyncPutPng.this.isPut = true;
                }
                DMAsyncPutPng.this.handler.post(new Runnable() { // from class: com.picspool.lib.io.DMAsyncPutPng.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DMAsyncPutPng.this.listener != null) {
                            DMAsyncPutPng.this.listener.onPutPngProcessFinish(Boolean.valueOf(DMAsyncPutPng.this.isPut));
                        }
                    }
                });
            }
        }).start();
    }
}
