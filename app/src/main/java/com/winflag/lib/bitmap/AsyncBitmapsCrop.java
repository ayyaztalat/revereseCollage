package com.winflag.lib.bitmap;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Handler;

import com.picspool.lib.bitmap.DMBitmapUtil;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class AsyncBitmapsCrop {
    private Context context;

    /* renamed from: cr */
    ContentResolver contentResolver;
    private final Handler handler = new Handler();
    private OnBitmapCropListener listener;
    private int maxSize;
    private List<Uri> uris;

    /* loaded from: classes.dex */
    public interface OnBitmapCropListener {

        public final /* synthetic */ class CC {
            public static int $default$onBitmapRequireItemSize(OnBitmapCropListener onBitmapCropListener, int i) {
                return -1;
            }
        }

        void onBitmapCriopFaile();

        void onBitmapCropStart();

        void onBitmapCropSuccess(List<Bitmap> list);

        int onBitmapRequireItemSize(int i);
    }

    public static void AsyncBitmapCropExecute(Context context, List<Uri> list, int i, OnBitmapCropListener onBitmapCropListener) {
        AsyncBitmapsCrop asyncBitmapsCrop = new AsyncBitmapsCrop(context, list, i);
        asyncBitmapsCrop.setOnBitmapCropListener(onBitmapCropListener);
        asyncBitmapsCrop.execute();
    }

    public AsyncBitmapsCrop(Context context, List<Uri> list, int i) {
        this.context = context;
        this.uris = list;
        this.maxSize = i;
    }

    public void setOnBitmapCropListener(OnBitmapCropListener onBitmapCropListener) {
        this.listener = onBitmapCropListener;
    }

    public void execute() {
        // java.lang.Runnable
        new Thread(() -> {
            try {
                if (AsyncBitmapsCrop.this.listener != null) {
                    AsyncBitmapsCrop.this.listener.onBitmapCropStart();
                }
                ArrayList<Bitmap> arrayList = new ArrayList<>();
                for (int i = 0; i < AsyncBitmapsCrop.this.uris.size(); i++) {
                    Bitmap image = getImage(uris.get(i), i);
                    if (image != null && !image.isRecycled()) {
                        arrayList.add(image);
                    }
                }
                if (AsyncBitmapsCrop.this.listener != null) {
                    AsyncBitmapsCrop.this.listener.onBitmapCropSuccess(arrayList);
                }
            } catch (Exception unused) {
                if (AsyncBitmapsCrop.this.listener != null) {
                    AsyncBitmapsCrop.this.listener.onBitmapCriopFaile();
                }
            }
        }).start();
    }

    public Bitmap getImage(Uri uri, int i) {
        int onBitmapRequireItemSize;
        if (contentResolver == null) {
            contentResolver = this.context.getContentResolver();
        }
        Cursor query = contentResolver.query(uri, null, null, null, null);
        if (query == null || !query.moveToFirst()) {
            return null;
        }
        @SuppressLint("Range") String string = query.getString(query.getColumnIndex("_data"));
        @SuppressLint("Range") String string2 = query.getString(query.getColumnIndex("orientation"));
        query.close();
        if (string != null) {
            int i2 = this.maxSize;
            OnBitmapCropListener onBitmapCropListener = this.listener;
            if (onBitmapCropListener != null && (onBitmapRequireItemSize = onBitmapCropListener.onBitmapRequireItemSize(i)) > 0) {
                i2 = onBitmapRequireItemSize;
            }
            Bitmap sampeZoomFromFile = DMBitmapUtil.sampeZoomFromFile(string, i2);
            int i3 = 0;
            if (string2 != null && !"".equals(string2)) {
                i3 = Integer.parseInt(string2);
            }
            if (i3 != 0) {
                Matrix matrix = new Matrix();
                int width = sampeZoomFromFile.getWidth();
                int height = sampeZoomFromFile.getHeight();
                matrix.setRotate(i3);
                Bitmap createBitmap = Bitmap.createBitmap(sampeZoomFromFile, 0, 0, width, height, matrix, true);
                if (sampeZoomFromFile != createBitmap && sampeZoomFromFile != null && !sampeZoomFromFile.isRecycled()) {
                    sampeZoomFromFile.recycle();
                }
                return createBitmap;
            }
            return sampeZoomFromFile;
        }
        return null;
    }
}
