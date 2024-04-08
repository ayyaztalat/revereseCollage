package com.winflag.libsquare.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;

import com.picspool.instafilter.CPUFilter;
import com.picspool.instafilter.GPUFilter;
import com.picspool.instafilter.resource.CPUFilterRes;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.border.CSBorderReturns;
import com.picspool.lib.border.CSTBorderProcess;
import com.picspool.lib.border.CSTBorderRes;
import com.picspool.lib.filter.listener.OnPostFilteredListener;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes3.dex */
public class CSAsyncSizeProcess {
    Bitmap filtered = null;
    private final Handler handler = new Handler();
    private DMWBRes mBorderRes;
    private Context mContext;
    private DMWBRes mFilterRes;
    private OnPostFilteredListener mListener;
    private Paint mPaint;
    private Bitmap mSrc;
    private DMWBRes mVigRes;

    public static void executeAsyncFilter(Context context, Bitmap bitmap, DMWBRes dMWBRes, DMWBRes dMWBRes2, DMWBRes dMWBRes3, OnPostFilteredListener onPostFilteredListener) {
        CSAsyncSizeProcess cSAsyncSizeProcess = new CSAsyncSizeProcess();
        cSAsyncSizeProcess.setData(context, bitmap, dMWBRes, dMWBRes2, dMWBRes3, onPostFilteredListener);
        cSAsyncSizeProcess.execute();
    }

    public void setData(Context context, Bitmap bitmap, DMWBRes dMWBRes, DMWBRes dMWBRes2, DMWBRes dMWBRes3, OnPostFilteredListener onPostFilteredListener) {
        this.mContext = context;
        this.mSrc = bitmap;
        this.mFilterRes = dMWBRes;
        this.mVigRes = dMWBRes2;
        this.mBorderRes = dMWBRes3;
        this.mListener = onPostFilteredListener;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.winflag.libsquare.view.CSAsyncSizeProcess.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    CSAsyncSizeProcess.this.filtered = CSAsyncSizeProcess.this.mSrc;
                    if (CSAsyncSizeProcess.this.mSrc != null) {
                        if (CSAsyncSizeProcess.this.mFilterRes != null) {
                            CSAsyncSizeProcess.this.filtered = GPUFilter.filterForType(CSAsyncSizeProcess.this.mContext, CSAsyncSizeProcess.this.mSrc, ((GPUFilterRes) CSAsyncSizeProcess.this.mFilterRes).getFilterType());
                        }
                        if (CSAsyncSizeProcess.this.mVigRes != null) {
                            Bitmap filterForType = CPUFilter.filterForType(CSAsyncSizeProcess.this.mContext, CSAsyncSizeProcess.this.filtered, ((CPUFilterRes) CSAsyncSizeProcess.this.mVigRes).getFilterType());
                            if (CSAsyncSizeProcess.this.filtered != CSAsyncSizeProcess.this.mSrc && CSAsyncSizeProcess.this.filtered != filterForType) {
                                CSAsyncSizeProcess.this.filtered.recycle();
                            }
                            CSAsyncSizeProcess.this.filtered = filterForType;
                        }
                        if (CSAsyncSizeProcess.this.mBorderRes != null) {
                            CSTBorderRes cSTBorderRes = (CSTBorderRes) CSAsyncSizeProcess.this.mBorderRes;
                            if (cSTBorderRes.getName() != "b00") {
                                CSBorderReturns processNinePathBorderOuter = CSTBorderProcess.processNinePathBorderOuter(CSAsyncSizeProcess.this.mContext, CSAsyncSizeProcess.this.mSrc.getWidth(), CSAsyncSizeProcess.this.mSrc.getHeight(), cSTBorderRes);
                                int left = processNinePathBorderOuter.getLeft();
                                int right = processNinePathBorderOuter.getRight();
                                Rect rect = new Rect(left, processNinePathBorderOuter.getTop(), CSAsyncSizeProcess.this.mSrc.getWidth() - right, CSAsyncSizeProcess.this.mSrc.getHeight() - processNinePathBorderOuter.getBottom());
                                Bitmap frameBitmap = processNinePathBorderOuter.getFrameBitmap();
                                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(frameBitmap, CSAsyncSizeProcess.this.mSrc.getWidth(), CSAsyncSizeProcess.this.mSrc.getHeight(), false);
                                frameBitmap.recycle();
                                new Canvas(createScaledBitmap).drawBitmap(CSAsyncSizeProcess.this.filtered, (Rect) null, rect, CSAsyncSizeProcess.this.mPaint);
                                if (CSAsyncSizeProcess.this.filtered != CSAsyncSizeProcess.this.mSrc) {
                                    CSAsyncSizeProcess.this.filtered.recycle();
                                }
                                CSAsyncSizeProcess.this.filtered = createScaledBitmap;
                            }
                        }
                    }
                    CSAsyncSizeProcess.this.handler.post(new Runnable() { // from class: com.winflag.libsquare.view.CSAsyncSizeProcess.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (CSAsyncSizeProcess.this.mListener != null) {
                                CSAsyncSizeProcess.this.mListener.postFiltered(CSAsyncSizeProcess.this.filtered);
                            }
                        }
                    });
                } catch (Exception unused) {
                    if (CSAsyncSizeProcess.this.mListener != null) {
                        CSAsyncSizeProcess.this.mListener.postFiltered(CSAsyncSizeProcess.this.mSrc);
                    }
                }
            }
        }).start();
    }
}
