package com.baiwang.libuiinstalens.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class CSCropAsyncBitmapCropPool {
    private static int FfromDrawbale = 2;
    private static int FromURI = 1;
    private static CSCropAsyncBitmapCropPool loader;
    private Context context;
    private ExecutorService executorService;
    private Uri itemUri;
    CSCropOnBitmapCropListener listener;
    private int maxSize;
    private int resId;
    private int type = FromURI;
    private final Handler handler = new Handler();

    public static CSCropAsyncBitmapCropPool getInstance() {
        return loader;
    }

    public static void initCropLoader(Context context) {
        if (loader == null) {
            loader = new CSCropAsyncBitmapCropPool();
        }
        loader.initExecutor();
    }

    public static void shutdownCropLoder() {
        CSCropAsyncBitmapCropPool cSCropAsyncBitmapCropPool = loader;
        if (cSCropAsyncBitmapCropPool != null) {
            cSCropAsyncBitmapCropPool.shutDownExecutor();
        }
        loader = null;
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
        this.context = null;
    }

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
        this.executorService.submit(new Runnable() { // from class: com.baiwang.libuiinstalens.crop.CSCropAsyncBitmapCropPool.1
            @Override // java.lang.Runnable
            public void run() {
                final Bitmap cropDrawableImage = CSCropAsyncBitmapCropPool.this.type == CSCropAsyncBitmapCropPool.FfromDrawbale ? CSCropBitmapCrop.cropDrawableImage(CSCropAsyncBitmapCropPool.this.context, CSCropAsyncBitmapCropPool.this.resId, CSCropAsyncBitmapCropPool.this.maxSize) : CSCropBitmapCrop.CropItemImage(CSCropAsyncBitmapCropPool.this.context, CSCropAsyncBitmapCropPool.this.itemUri, CSCropAsyncBitmapCropPool.this.maxSize);
                CSCropAsyncBitmapCropPool.this.handler.post(new Runnable() { // from class: com.baiwang.libuiinstalens.crop.CSCropAsyncBitmapCropPool.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (CSCropAsyncBitmapCropPool.this.listener != null) {
                            CSCropAsyncBitmapCropPool.this.listener.onBitmapCropFinish(cropDrawableImage);
                        }
                    }
                });
            }
        });
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }
}
