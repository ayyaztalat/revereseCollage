package com.winflag.libcollage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;

import com.picspool.instafilter.GPUFilter;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.collagelib.LibDMMaskImageViewTouch;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBRes;

import java.util.List;

/* loaded from: classes.dex */
public class AsyncTemplateProcess {
    private final Handler handler = new Handler();
    private Context mContext;
    private DMWBRes mFilterRes;
    private OnPostFilteredListener mListener;
    private Paint mPaint;
    public List<Bitmap> mTouchBitmaps;
    private LibDMMaskImageViewTouch[] mTouchListView;

    public static void executeAsyncFilter(Context context, LibDMMaskImageViewTouch[] libDMMaskImageViewTouchArr, List<Bitmap> list, DMWBRes dMWBRes, OnPostFilteredListener onPostFilteredListener) {
        AsyncTemplateProcess asyncTemplateProcess = new AsyncTemplateProcess();
        asyncTemplateProcess.setData(context, libDMMaskImageViewTouchArr, list, dMWBRes, onPostFilteredListener);
        asyncTemplateProcess.execute();
    }

    public void setData(Context context, LibDMMaskImageViewTouch[] libDMMaskImageViewTouchArr, List<Bitmap> list, DMWBRes dMWBRes, OnPostFilteredListener onPostFilteredListener) {
        this.mContext = context;
        this.mTouchListView = libDMMaskImageViewTouchArr;
        this.mFilterRes = dMWBRes;
        this.mTouchBitmaps = list;
        this.mListener = onPostFilteredListener;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.winflag.libcollage.view.AsyncTemplateProcess.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (AsyncTemplateProcess.this.mTouchListView != null && AsyncTemplateProcess.this.mTouchListView.length > 0 && AsyncTemplateProcess.this.mFilterRes != null && AsyncTemplateProcess.this.mTouchBitmaps != null && AsyncTemplateProcess.this.mTouchBitmaps.size() > 0) {
                        for (int i = 0; i < AsyncTemplateProcess.this.mTouchListView.length; i++) {
                            final LibDMMaskImageViewTouch libDMMaskImageViewTouch = AsyncTemplateProcess.this.mTouchListView[i];
                            synchronized (libDMMaskImageViewTouch) {
                                try {
                                    Bitmap bitmap = AsyncTemplateProcess.this.mTouchBitmaps.get(i);
                                    if (bitmap != null && !bitmap.isRecycled()) {
                                        final Bitmap filterForType = GPUFilter.filterForType(AsyncTemplateProcess.this.mContext, bitmap, ((GPUFilterRes) AsyncTemplateProcess.this.mFilterRes).getFilterType());
                                        AsyncTemplateProcess.this.handler.post(new Runnable() { // from class: com.winflag.libcollage.view.AsyncTemplateProcess.1.1
                                            @Override // java.lang.Runnable
                                            public void run() {
                                                libDMMaskImageViewTouch.setImageBitmapWithStatKeep(null);
                                                libDMMaskImageViewTouch.setImageBitmap(filterForType, false);
                                            }
                                        });
                                    }
                                } catch (Exception unused) {
                                    return;
                                }
                            }
                        }
                        return;
                    }
                    if (AsyncTemplateProcess.this.mListener != null) {
                        AsyncTemplateProcess.this.mListener.postFiltered(null);
                    }
                } catch (Exception unused2) {
                }
            }
        }).start();
    }
}
