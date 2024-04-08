package com.picspool.lib.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;

/* loaded from: classes3.dex */
public class DMAsyncBitmapCrop23 {
    private static int FfromDrawbale = 2;
    private static int FromURI = 1;
    private Context context;
    private Uri itemUri;
    OnBitmapCropListener listener;
    private int maxSize;
    private int resId;
    private int type = FromURI;
    private final Handler handler = new Handler();
    Bitmap cropBitmap = null;

    public void setData(Context context, Uri uri, int i) {
        this.context = context;
        this.itemUri = uri;
        this.maxSize = i;
        this.type = FromURI;
    }

    public void setData(Context context, int i, int i2) {
        this.context = context;
        this.resId = i;
        this.maxSize = i2;
        this.type = FfromDrawbale;
    }

    public void setOnBitmapCropListener(OnBitmapCropListener onBitmapCropListener) {
        this.listener = onBitmapCropListener;
    }

    public void execute() {
        new myThread(this).start();
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public void run() {
        this.cropBitmap = null;
        if (this.type == FfromDrawbale) {
            this.cropBitmap = DMBitmapCrop.cropDrawableImage(this.context, this.resId, this.maxSize);
        } else {
            this.cropBitmap = DMBitmapCrop.CropItemImage(this.context, this.itemUri, this.maxSize);
        }
        this.handler.post(new Runnable() { // from class: com.picspool.lib.bitmap.DMAsyncBitmapCrop23.1
            @Override // java.lang.Runnable
            public void run() {
                if (DMAsyncBitmapCrop23.this.listener != null) {
                    DMAsyncBitmapCrop23.this.listener.onBitmapCropFinish(DMAsyncBitmapCrop23.this.cropBitmap);
                    DMAsyncBitmapCrop23.this.cropBitmap = null;
                }
                DMAsyncBitmapCrop23.this.listener = null;
            }
        });
    }

    /* loaded from: classes3.dex */
    static class myThread extends Thread {
        DMAsyncBitmapCrop23 mAsy23;

        public myThread(DMAsyncBitmapCrop23 dMAsyncBitmapCrop23) {
            this.mAsy23 = dMAsyncBitmapCrop23;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            this.mAsy23.run();
        }
    }

    /* loaded from: classes3.dex */
    static class MyRunnable implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
        }

        MyRunnable() {
        }
    }
}
