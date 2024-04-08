package com.touch.android.library.imagezoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.picspool.lib.collagelib.DMColorFilterGenerator;
import com.touch.android.library.imagezoom.utils.IDisposable;
//import org.picspool.lib.collagelib.DMColorFilterGenerator;
//import touch.android.library.imagezoom.utils.IDisposable;

/* loaded from: classes3.dex */
public class LightImageView extends androidx.appcompat.widget.AppCompatImageView implements IDisposable {
    int mAlpha;
    Bitmap mBitmap;
    int mHue;
    Paint mPaint;
    PaintFlagsDrawFilter mPaintFlagsDrawFilter;
    PorterDuffXfermode mPorterDuffXfermode;

    public LightImageView(Context context) {
        super(context);
        this.mAlpha = 255;
        this.mHue = 0;
        this.mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
    }

    public LightImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LightImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mAlpha = 255;
        this.mHue = 0;
        this.mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
    }

    @Override // touch.android.library.imagezoom.utils.IDisposable
    public void dispose() {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mBitmap.recycle();
        }
        this.mBitmap = null;
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != bitmap && bitmap2 != null && !bitmap2.isRecycled()) {
            this.mBitmap.recycle();
            this.mBitmap = null;
        }
        this.mBitmap = bitmap;
        invalidate();
    }

    public Bitmap getImageBitmap() {
        return this.mBitmap;
    }

    public Paint getLeakPaint() {
        return this.mPaint;
    }

    public void setPaintXfermode(PorterDuffXfermode porterDuffXfermode) {
        if (this.mPaint == null) {
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            this.mPaint.setDither(true);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setFilterBitmap(true);
        }
        this.mPorterDuffXfermode = porterDuffXfermode;
        this.mPaint.setXfermode(porterDuffXfermode);
        invalidate();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap;
        if (this.mPaint == null) {
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            this.mPaint.setDither(true);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setFilterBitmap(true);
        }
        canvas.setDrawFilter(this.mPaintFlagsDrawFilter);
        if (getWidth() <= 0 || getHeight() <= 0 || (bitmap = this.mBitmap) == null) {
            return;
        }
        canvas.drawBitmap(bitmap, (Rect) null, new Rect(0, 0, getWidth(), getHeight()), this.mPaint);
    }

    public void SetAlpha(int i) {
        this.mAlpha = i;
        if (this.mPaint == null) {
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            this.mPaint.setDither(true);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setFilterBitmap(true);
        }
        this.mPaint.setAlpha(i);
        invalidate();
    }

    public void SetHueColorFilter(int i) {
        this.mHue = i;
        if (this.mPaint == null) {
            Paint paint = new Paint();
            this.mPaint = paint;
            paint.setAntiAlias(true);
            this.mPaint.setDither(true);
            this.mPaint.setAntiAlias(true);
            this.mPaint.setFilterBitmap(true);
        }
        this.mPaint.setColorFilter(DMColorFilterGenerator.adjustHue(i));
        invalidate();
    }
}
