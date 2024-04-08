package com.picspool.lib.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

/* loaded from: classes3.dex */
public class DMAsyncBitmapCrop extends AsyncTask<String, Void, Bitmap> {
    private static int FfromDrawbale = 2;
    private static int FromURI = 1;
    private Context context;
    private Uri itemUri;
    OnBitmapCropListener listener;
    private int maxSize;
    private int resId;
    private int type = FromURI;

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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Bitmap doInBackground(String... strArr) {
        if (this.type == FfromDrawbale) {
            return DMBitmapCrop.cropDrawableImage(this.context, this.resId, this.maxSize);
        }
        return DMBitmapCrop.CropItemImage(this.context, this.itemUri, this.maxSize);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Bitmap bitmap) {
        OnBitmapCropListener onBitmapCropListener = this.listener;
        if (onBitmapCropListener != null) {
            onBitmapCropListener.onBitmapCropFinish(bitmap);
        }
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }
}
