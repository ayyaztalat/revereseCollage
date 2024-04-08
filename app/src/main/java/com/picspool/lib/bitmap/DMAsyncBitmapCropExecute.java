package com.picspool.lib.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/* loaded from: classes3.dex */
public class DMAsyncBitmapCropExecute {
    public static void asyncBitmapCrop(Context context, Uri uri, int i, final OnBitmapCropListener onBitmapCropListener) {
        DMAsyncBitmapCropPool.initCropLoader(context);
        DMAsyncBitmapCropPool dMAsyncBitmapCropPool = DMAsyncBitmapCropPool.getInstance();
        dMAsyncBitmapCropPool.setData(context, uri, i);
        dMAsyncBitmapCropPool.setOnBitmapCropListener(bitmap -> {
            DMAsyncBitmapCropPool.shutdownCropLoder();
            onBitmapCropListener.onBitmapCropFinish(bitmap);
        });
        dMAsyncBitmapCropPool.execute();
    }

    public static void asyncDrawbaleBitmapCrop(Context context, int i, int i2, OnBitmapCropListener onBitmapCropListener) {
        DMAsyncBitmapCrop23 dMAsyncBitmapCrop23 = new DMAsyncBitmapCrop23();
        dMAsyncBitmapCrop23.setData(context, i, i2);
        dMAsyncBitmapCrop23.setOnBitmapCropListener(onBitmapCropListener);
        dMAsyncBitmapCrop23.execute();
    }
}
