package com.picspool.lib.border.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.border.process.DMWBBorderProcess;
import com.picspool.lib.border.res.DMWBBorderRes;

/* loaded from: classes3.dex */
public class DMWBBorderViewProcess extends View {
    private Bitmap bitmap;
    Rect dstRect;
    public int height;
    private boolean isResChange;

    /* renamed from: m */
    Matrix f1941m;
    private Context mContext;

    /* renamed from: p */
    Paint f1942p;
    private DMWBBorderRes res;
    public int width;

    public DMWBBorderViewProcess(Context context) {
        super(context);
        this.bitmap = null;
        this.isResChange = false;
        this.f1942p = new Paint();
        this.f1941m = new Matrix();
        this.mContext = context;
    }

    public DMWBBorderViewProcess(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.bitmap = null;
        this.isResChange = false;
        this.f1942p = new Paint();
        this.f1941m = new Matrix();
        this.mContext = context;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.width = getWidth();
        this.height = getHeight();
        Bitmap bitmap = this.bitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        if (this.dstRect == null) {
            this.dstRect = new Rect(0, 0, this.width, this.height);
        }
        this.dstRect.left = 0;
        this.dstRect.right = this.width;
        this.dstRect.top = 0;
        this.dstRect.bottom = this.height;
        canvas.drawBitmap(this.bitmap, (Rect) null, this.dstRect, (Paint) null);
    }

    public void changeRes(DMWBBorderRes dMWBBorderRes, boolean z) {
        this.res = dMWBBorderRes;
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bitmap.recycle();
        }
        this.bitmap = null;
        if (this.res == null) {
            invalidate();
            return;
        }
        if (dMWBBorderRes.getBorderType() != null && dMWBBorderRes.getBorderType() == DMWBBorderRes.BorderType.IMAGE) {
            Bitmap localImageBitmap = dMWBBorderRes.getLocalImageBitmap();
            if (localImageBitmap == null || localImageBitmap.isRecycled()) {
                invalidate();
                return;
            }
            Bitmap sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(localImageBitmap, this.width);
            this.bitmap = sampeZoomFromBitmap;
            if (localImageBitmap != sampeZoomFromBitmap && !localImageBitmap.isRecycled()) {
                localImageBitmap.recycle();
            }
        } else {
            this.bitmap = DMWBBorderProcess.processNinePathBorderOuter(this.mContext, this.width, this.height, dMWBBorderRes, (Canvas) null);
        }
        this.isResChange = true;
        invalidate();
    }

    public DMWBBorderRes getCurrentRes() {
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
