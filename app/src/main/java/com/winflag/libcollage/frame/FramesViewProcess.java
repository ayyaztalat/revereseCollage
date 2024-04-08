package com.winflag.libcollage.frame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.picspool.lib.bitmap.DMBitmapUtil;
import com.winflag.libcollage.frame.res.FrameBorderRes;
import com.winflag.libcollage.frame.res.border.TBorderProcess;
import com.winflag.libcollage.frame.res.border.TBorderRes;

/* loaded from: classes.dex */
public class FramesViewProcess extends View {
    private Bitmap bitmap;
    public int height;
    private boolean isResChange;

    /* renamed from: m */
    Matrix f1767m;
    private Context mContext;

    /* renamed from: p */
    Paint f1768p;
    private FrameBorderRes res;
    public int width;

    public FramesViewProcess(Context context) {
        super(context);
        this.bitmap = null;
        this.isResChange = false;
        this.f1768p = new Paint();
        this.f1767m = new Matrix();
        this.mContext = context;
    }

    public FramesViewProcess(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.bitmap = null;
        this.isResChange = false;
        this.f1768p = new Paint();
        this.f1767m = new Matrix();
        this.mContext = context;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        if (this.height == 0) {
            this.height = this.width;
        }
        canvas.drawBitmap(this.bitmap, (Rect) null, new Rect(0, 0, this.width, this.height), this.f1768p);
    }

    public void changeRes(FrameBorderRes frameBorderRes) {
        this.res = frameBorderRes;
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bitmap.recycle();
        }
        this.bitmap = null;
        if (this.res == null) {
            invalidate();
            return;
        }
        TBorderRes tBorderRes = gettBorderRes(frameBorderRes);
        if (frameBorderRes.getBorderType() != null && frameBorderRes.getBorderType() == FrameBorderRes.BorderType.IMAGE) {
            Bitmap localImageBitmap = frameBorderRes.getLocalImageBitmap();
            Bitmap sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(localImageBitmap, this.width);
            this.bitmap = sampeZoomFromBitmap;
            if (localImageBitmap != sampeZoomFromBitmap && !localImageBitmap.isRecycled()) {
                localImageBitmap.recycle();
            }
        } else {
            this.bitmap = TBorderProcess.processNinePathBorderOuter(this.mContext, this.width, this.height, tBorderRes, (Canvas) null);
        }
        this.isResChange = true;
        invalidate();
    }

    @NonNull
    private TBorderRes gettBorderRes(FrameBorderRes frameBorderRes) {
        TBorderRes tBorderRes = new TBorderRes(getContext());
        tBorderRes.setName(frameBorderRes.getName());
        tBorderRes.setLeftUri(frameBorderRes.getLeftUri());
        tBorderRes.setRightUri(frameBorderRes.getRightUri());
        tBorderRes.setTopUri(frameBorderRes.getTopUri());
        tBorderRes.setBottomUri(frameBorderRes.getBottomUri());
        tBorderRes.setLeftTopCornorUri(frameBorderRes.getLeftTopCornorUri());
        tBorderRes.setLeftBottomCornorUri(frameBorderRes.getLeftBottomCornorUri());
        tBorderRes.setRightTopCornorUri(frameBorderRes.getRightTopCornorUri());
        tBorderRes.setRightBottomCornorUri(frameBorderRes.getRightBottomCornorUri());
        return tBorderRes;
    }

    public FrameBorderRes getCurrentRes() {
        return this.res;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void dispose() {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bitmap.recycle();
        }
        this.bitmap = null;
        this.res = null;
    }
}
