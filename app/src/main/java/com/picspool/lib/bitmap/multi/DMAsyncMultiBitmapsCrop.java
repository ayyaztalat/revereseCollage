package com.picspool.lib.bitmap.multi;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapCrop;

/* loaded from: classes3.dex */
public class DMAsyncMultiBitmapsCrop {
    private Context context;
    private final Handler handler = new Handler();
    private OnMultiBitmapCropListener listener;
    private int maxSize;
    private List<Uri> uris;

    /* loaded from: classes3.dex */
    public interface OnMultiBitmapCropListener {
        void onMultiBitmapCriopFaile();

        void onMultiBitmapCropStart();

        void onMultiBitmapCropSuccess(List<Bitmap> list);
    }

    public static void AsyncMutiBitmapCropExecute(Context context, List<Uri> list, int i, OnMultiBitmapCropListener onMultiBitmapCropListener) {
        DMAsyncMultiBitmapsCrop dMAsyncMultiBitmapsCrop = new DMAsyncMultiBitmapsCrop(context, list, i);
        dMAsyncMultiBitmapsCrop.setOnMultiBitmapCropListener(onMultiBitmapCropListener);
        dMAsyncMultiBitmapsCrop.execute();
    }

    public DMAsyncMultiBitmapsCrop(Context context, List<Uri> list, int i) {
        this.context = context;
        this.uris = list;
        this.maxSize = i;
    }

    public void setOnMultiBitmapCropListener(OnMultiBitmapCropListener onMultiBitmapCropListener) {
        this.listener = onMultiBitmapCropListener;
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.picspool.lib.bitmap.multi.DMAsyncMultiBitmapsCrop.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (DMAsyncMultiBitmapsCrop.this.listener != null) {
                        DMAsyncMultiBitmapsCrop.this.listener.onMultiBitmapCropStart();
                    }
                    final ArrayList arrayList = new ArrayList();
                    for (Uri uri : DMAsyncMultiBitmapsCrop.this.uris) {
                        if (!uri.toString().startsWith("file://")) {
                            DMBitmapDbUtil.imagelPathFromURI(DMAsyncMultiBitmapsCrop.this.context, uri);
                        } else {
                            uri.getPath();
                        }
                        arrayList.add(DMBitmapCrop.CropItemImage(DMAsyncMultiBitmapsCrop.this.context, uri, DMAsyncMultiBitmapsCrop.this.maxSize));
                    }
                    DMAsyncMultiBitmapsCrop.this.handler.post(new Runnable() { // from class: com.picspool.lib.bitmap.multi.DMAsyncMultiBitmapsCrop.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (DMAsyncMultiBitmapsCrop.this.listener == null || arrayList == null) {
                                return;
                            }
                            DMAsyncMultiBitmapsCrop.this.listener.onMultiBitmapCropSuccess(arrayList);
                        }
                    });
                } catch (Exception unused) {
                    if (DMAsyncMultiBitmapsCrop.this.listener != null) {
                        DMAsyncMultiBitmapsCrop.this.listener.onMultiBitmapCriopFaile();
                    }
                }
            }
        }).start();
    }
}
