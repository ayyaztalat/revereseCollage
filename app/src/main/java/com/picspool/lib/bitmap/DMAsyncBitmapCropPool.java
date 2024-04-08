package com.picspool.lib.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes3.dex */
public class DMAsyncBitmapCropPool {
    private static int FfromDrawbale = 2;
    private static int FromURI = 1;
    private static DMAsyncBitmapCropPool loader;
    private Context context;
    private ExecutorService executorService;
    private Uri itemUri;
    OnBitmapCropListener listener;
    private int maxSize;
    private int resId;
    private int type = FromURI;
    private final Handler handler = new Handler();

    public static DMAsyncBitmapCropPool getInstance() {
        return loader;
    }

    public static void initCropLoader(Context context) {
        if (loader == null) {
            loader = new DMAsyncBitmapCropPool();
        }
        loader.initExecutor();
    }

    public static void shutdownCropLoder() {
        DMAsyncBitmapCropPool dMAsyncBitmapCropPool = loader;
        if (dMAsyncBitmapCropPool != null) {
            dMAsyncBitmapCropPool.shutDownExecutor();
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

    public void setOnBitmapCropListener(OnBitmapCropListener onBitmapCropListener) {
        this.listener = onBitmapCropListener;
    }

    public void execute() {
        this.executorService.submit(new Runnable() { // from class: com.picspool.lib.bitmap.DMAsyncBitmapCropPool.1
            @Override // java.lang.Runnable
            public void run() {
                final Bitmap cropDrawableImage = DMAsyncBitmapCropPool.this.type == DMAsyncBitmapCropPool.FfromDrawbale ? DMBitmapCrop.cropDrawableImage(DMAsyncBitmapCropPool.this.context, DMAsyncBitmapCropPool.this.resId, DMAsyncBitmapCropPool.this.maxSize) : DMBitmapCrop.CropItemImage(DMAsyncBitmapCropPool.this.context, DMAsyncBitmapCropPool.this.itemUri, DMAsyncBitmapCropPool.this.maxSize);
                DMAsyncBitmapCropPool.this.handler.post(new Runnable() { // from class: com.picspool.lib.bitmap.DMAsyncBitmapCropPool.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DMAsyncBitmapCropPool.this.listener != null) {
                            DMAsyncBitmapCropPool.this.listener.onBitmapCropFinish(cropDrawableImage);
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
