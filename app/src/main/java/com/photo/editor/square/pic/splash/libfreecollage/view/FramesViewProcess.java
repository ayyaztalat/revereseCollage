package com.photo.editor.square.pic.splash.libfreecollage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.photo.editor.square.pic.splash.libfreecollage.frame.resource.FreeFrameBorderRes;
import com.photo.editor.square.pic.splash.libfreecollage.resource.border.TBorderProcess;
import com.photo.editor.square.pic.splash.libfreecollage.resource.border.TBorderRes;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.border.res.DMWBBorderRes;

/* loaded from: classes2.dex */
public class FramesViewProcess extends View {
    private Bitmap bitmap;
    public int height;
    private boolean isResChange;

    /* renamed from: m */
    Matrix f1595m;
    private Context mContext;

    /* renamed from: p */
    Paint f1596p;
    private FreeFrameBorderRes res;
    public int width;

    public FramesViewProcess(Context context) {
        super(context);
        this.f1596p = new Paint();
        this.f1595m = new Matrix();
        this.bitmap = null;
        this.isResChange = false;
        this.mContext = context;
    }

    public FramesViewProcess(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f1596p = new Paint();
        this.f1595m = new Matrix();
        this.bitmap = null;
        this.isResChange = false;
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
        canvas.drawBitmap(this.bitmap, (Rect) null, new Rect(0, 0, this.width, this.height), this.f1596p);
    }

    public void changeRes(FreeFrameBorderRes freeFrameBorderRes) {
        this.res = freeFrameBorderRes;
        Bitmap bitmap = this.bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.bitmap.recycle();
        }
        this.bitmap = null;
        if (this.res == null) {
            invalidate();
            return;
        }
        TBorderRes tBorderRes = new TBorderRes(getContext());
        tBorderRes.setName(freeFrameBorderRes.getName());
        tBorderRes.setLeftUri(freeFrameBorderRes.getLeftUri());
        tBorderRes.setRightUri(freeFrameBorderRes.getRightUri());
        tBorderRes.setTopUri(freeFrameBorderRes.getTopUri());
        tBorderRes.setBottomUri(freeFrameBorderRes.getBottomUri());
        tBorderRes.setLeftTopCornorUri(freeFrameBorderRes.getLeftTopCornorUri());
        tBorderRes.setLeftBottomCornorUri(freeFrameBorderRes.getLeftBottomCornorUri());
        tBorderRes.setRightTopCornorUri(freeFrameBorderRes.getRightTopCornorUri());
        tBorderRes.setRightBottomCornorUri(freeFrameBorderRes.getRightBottomCornorUri());
        if (freeFrameBorderRes.getBorderType() != null && freeFrameBorderRes.getBorderType() == DMWBBorderRes.BorderType.IMAGE) {
            Bitmap localImageBitmap = freeFrameBorderRes.getLocalImageBitmap();
            Bitmap sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(localImageBitmap, this.width);
            this.bitmap = sampeZoomFromBitmap;
            if (localImageBitmap != sampeZoomFromBitmap && !localImageBitmap.isRecycled()) {
                localImageBitmap.recycle();
            }
        } else {
            this.bitmap = TBorderProcess.processNinePathBorderOuter(this.mContext, this.width, this.height, tBorderRes, null);
        }
        this.isResChange = true;
        invalidate();
    }

    public FreeFrameBorderRes getCurrentRes() {
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
