package com.picspool.lib.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes3.dex */
public class DMAsyncThumbnailLoader {
    private static DMAsyncThumbnailLoader loader;
    Context context;
    private ExecutorService executorService;
    private final Handler handler = new Handler();
    DMImageMediaItem item;
    OnImageLoadListener listener;

    /* loaded from: classes3.dex */
    public interface OnImageLoadListener {
        void onLoadFail(DMImageMediaItem dMImageMediaItem);

        void onLoadFinish(DMImageMediaItem dMImageMediaItem, Bitmap bitmap);
    }

    public static DMAsyncThumbnailLoader getInstance() {
        return loader;
    }

    public static void initThumbLoader() {
        if (loader == null) {
            loader = new DMAsyncThumbnailLoader();
        }
        loader.initExecutor();
    }

    public static void shutdownThumbLoder() {
        DMAsyncThumbnailLoader dMAsyncThumbnailLoader = loader;
        if (dMAsyncThumbnailLoader != null) {
            dMAsyncThumbnailLoader.shutDownExecutor();
        }
        loader = null;
    }

    public void initExecutor() {
        if (this.executorService != null) {
            shutDownExecutor();
        }
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void shutDownExecutor() {
        ExecutorService executorService = this.executorService;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public void thumbLoad(final Context context, final DMImageMediaItem dMImageMediaItem, final OnImageLoadListener onImageLoadListener, final boolean z) {
        this.executorService.submit(new Runnable() { // from class: com.picspool.lib.service.DMAsyncThumbnailLoader.1
            @Override // java.lang.Runnable
            public void run() {
                final Bitmap thumbnail;
                try {
                    if (z) {
                        thumbnail = dMImageMediaItem.getGalleryThumbnail(context);
                    } else {
                        int screenWidth = DMScreenInfoUtil.screenWidth(context) / 5;
                        if (screenWidth < 120) {
                            screenWidth = 120;
                        }
                        thumbnail = dMImageMediaItem.getThumbnail(context, screenWidth);
                    }
                    DMAsyncThumbnailLoader.this.handler.post(new Runnable() { // from class: com.picspool.lib.service.DMAsyncThumbnailLoader.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (onImageLoadListener != null) {
                                if (thumbnail != null) {
                                    onImageLoadListener.onLoadFinish(dMImageMediaItem, thumbnail);
                                } else {
                                    onImageLoadListener.onLoadFail(dMImageMediaItem);
                                }
                            }
                        }
                    });
                } catch (Exception unused) {
                    onImageLoadListener.onLoadFail(dMImageMediaItem);
                }
            }
        });
    }
}
