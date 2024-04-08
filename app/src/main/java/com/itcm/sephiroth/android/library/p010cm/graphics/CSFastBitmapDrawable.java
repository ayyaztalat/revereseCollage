package com.itcm.sephiroth.android.library.p010cm.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import java.io.InputStream;

/* renamed from: itcm.sephiroth.android.library.cm.graphics.CSFastBitmapDrawable */
/* loaded from: classes3.dex */
public class CSFastBitmapDrawable extends Drawable implements CSIBitmapDrawable {
    protected int mIntrinsicHeight;
    protected int mIntrinsicWidth;
    protected Paint mPaint;
    protected Rect mRect;
    protected RectF mRectF;
    private float mxRadius;
    private float myRadius;
    protected Bitmap oriBitmap;
    protected PorterDuffXfermode srcInDuffXfermode;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void setRoundRadius(float f, float f2) {
    }

    public CSFastBitmapDrawable(Bitmap bitmap) {
        this.srcInDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.mxRadius = 100.0f;
        this.myRadius = 100.0f;
        this.oriBitmap = bitmap;
        if (bitmap != null) {
            this.mIntrinsicWidth = bitmap.getWidth();
            this.mIntrinsicHeight = this.oriBitmap.getHeight();
        } else {
            this.mIntrinsicWidth = 0;
            this.mIntrinsicHeight = 0;
        }
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setDither(true);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
        this.mRect = new Rect(0, 0, this.mIntrinsicWidth, this.mIntrinsicHeight);
        this.mRectF = new RectF(0.5f, 0.5f, this.mIntrinsicWidth - 0.5f, this.mIntrinsicHeight - 0.5f);
    }

    public void setBitmap(Bitmap bitmap) {
        this.oriBitmap = bitmap;
    }

    public CSFastBitmapDrawable(Resources resources, InputStream inputStream) {
        this(BitmapFactory.decodeStream(inputStream));
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Bitmap bitmap = this.oriBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        Bitmap bitmap2 = this.oriBitmap;
        Rect rect = this.mRect;
        canvas.drawBitmap(bitmap2, rect, rect, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return this.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return this.mIntrinsicHeight;
    }

    public void setAntiAlias(boolean z) {
        this.mPaint.setAntiAlias(z);
        invalidateSelf();
    }

    @Override // itcm.sephiroth.android.library.p010cm.graphics.CSIBitmapDrawable
    public Bitmap getBitmap() {
        return this.oriBitmap;
    }

    public Paint getPaint() {
        return this.mPaint;
    }
}
