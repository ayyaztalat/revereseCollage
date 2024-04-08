package com.baiwang.libuiinstalens.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;

/* loaded from: classes.dex */
public class CSCropAsyncBitmapCrop23 {
    private static int FfromDrawbale = 2;
    private static int FromURI = 1;
    private Context context;
    private Uri itemUri;
    CSCropOnBitmapCropListener listener;
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

    public void setOnBitmapCropListener(CSCropOnBitmapCropListener cSCropOnBitmapCropListener) {
        this.listener = cSCropOnBitmapCropListener;
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
            this.cropBitmap = CSCropBitmapCrop.cropDrawableImage(this.context, this.resId, this.maxSize);
        } else {
            this.cropBitmap = CSCropBitmapCrop.CropItemImage(this.context, this.itemUri, this.maxSize);
        }
        this.handler.post(new Runnable() { // from class: com.baiwang.libuiinstalens.crop.CSCropAsyncBitmapCrop23.1
            @Override // java.lang.Runnable
            public void run() {
                if (CSCropAsyncBitmapCrop23.this.listener != null) {
                    CSCropAsyncBitmapCrop23.this.listener.onBitmapCropFinish(CSCropAsyncBitmapCrop23.this.cropBitmap);
                    CSCropAsyncBitmapCrop23.this.cropBitmap = null;
                }
                CSCropAsyncBitmapCrop23.this.listener = null;
            }
        });
    }

    /* loaded from: classes.dex */
    static class MyRunnable implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
        }

        MyRunnable() {
        }
    }

    /* loaded from: classes.dex */
    static class myThread extends Thread {
        CSCropAsyncBitmapCrop23 mAsy23;

        public myThread(CSCropAsyncBitmapCrop23 cSCropAsyncBitmapCrop23) {
            this.mAsy23 = cSCropAsyncBitmapCrop23;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            this.mAsy23.run();
        }
    }
}
