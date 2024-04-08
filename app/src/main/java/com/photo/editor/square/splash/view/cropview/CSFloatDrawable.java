package com.photo.editor.square.splash.view.cropview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSFloatDrawable extends Drawable {
    private boolean isFreeMode = false;
    private Context mContext;
    private Drawable mCropHPointDrawable;
    private Drawable mCropPointDrawable;
    private Drawable mCropVPointDrawable;
    private Paint mLinePaint;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public CSFloatDrawable(Context context) {
        Paint paint = new Paint();
        this.mLinePaint = paint;
        paint.setColor(-13841942);
        this.mLinePaint.setStrokeWidth(1.0f);
        this.mLinePaint.setStyle(Paint.Style.STROKE);
        this.mLinePaint.setAntiAlias(true);
        this.mLinePaint.setColor(-1);
        this.mContext = context;
        init();
    }

    private void init() {
        this.mCropPointDrawable = this.mContext.getResources().getDrawable(R.drawable.ui_crop_clip_point);
        this.mCropHPointDrawable = null;
        this.mCropVPointDrawable = null;
    }

    public int getClipRectWidth() {
        Drawable drawable = this.mCropHPointDrawable;
        return drawable == null ? getCirleWidth() : drawable.getIntrinsicWidth();
    }

    public int getClipRectHeight() {
        Drawable drawable = this.mCropHPointDrawable;
        return drawable == null ? getCirleHeight() : drawable.getIntrinsicHeight();
    }

    public int getCirleWidth() {
        return this.mCropPointDrawable.getIntrinsicWidth();
    }

    public int getCirleHeight() {
        return this.mCropPointDrawable.getIntrinsicHeight();
    }

    public void setFreeMode(boolean z) {
        this.isFreeMode = z;
    }

    public boolean getFreeMode() {
        return this.isFreeMode;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int i = getBounds().left;
        int i2 = getBounds().top;
        int i3 = getBounds().right;
        int i4 = getBounds().bottom;
        int intrinsicWidth = i + (this.mCropPointDrawable.getIntrinsicWidth() / 2);
        int intrinsicHeight = i2 + (this.mCropPointDrawable.getIntrinsicHeight() / 2);
        int intrinsicWidth2 = i3 - (this.mCropPointDrawable.getIntrinsicWidth() / 2);
        int intrinsicHeight2 = i4 - (this.mCropPointDrawable.getIntrinsicHeight() / 2);
        int i5 = intrinsicWidth2 - intrinsicWidth;
        int i6 = intrinsicHeight2 - intrinsicHeight;
        canvas.drawRect(new Rect(intrinsicWidth, intrinsicHeight, intrinsicWidth2, intrinsicHeight2), this.mLinePaint);
        float f = (i5 / 3) + intrinsicWidth;
        float f2 = intrinsicHeight;
        float f3 = intrinsicHeight2;
        canvas.drawLine(f, f2, f, f3, this.mLinePaint);
        float f4 = intrinsicWidth + ((i5 * 2) / 3);
        canvas.drawLine(f4, f2, f4, f3, this.mLinePaint);
        float f5 = intrinsicWidth;
        float f6 = (i6 / 3) + intrinsicHeight;
        float f7 = intrinsicWidth2;
        canvas.drawLine(f5, f6, f7, f6, this.mLinePaint);
        float f8 = intrinsicHeight + ((i6 * 2) / 3);
        canvas.drawLine(f5, f8, f7, f8, this.mLinePaint);
        Drawable drawable = this.mCropPointDrawable;
        drawable.setBounds(i, i2, drawable.getIntrinsicWidth() + i, this.mCropPointDrawable.getIntrinsicHeight() + i2);
        this.mCropPointDrawable.draw(canvas);
        Drawable drawable2 = this.mCropPointDrawable;
        drawable2.setBounds(i3 - drawable2.getIntrinsicWidth(), i2, i3, this.mCropPointDrawable.getIntrinsicHeight() + i2);
        this.mCropPointDrawable.draw(canvas);
        Drawable drawable3 = this.mCropPointDrawable;
        drawable3.setBounds(i, i4 - drawable3.getIntrinsicHeight(), this.mCropPointDrawable.getIntrinsicWidth() + i, i4);
        this.mCropPointDrawable.draw(canvas);
        Drawable drawable4 = this.mCropPointDrawable;
        drawable4.setBounds(i3 - drawable4.getIntrinsicWidth(), i4 - this.mCropPointDrawable.getIntrinsicHeight(), i3, i4);
        this.mCropPointDrawable.draw(canvas);
        if (!this.isFreeMode || this.mCropHPointDrawable == null || this.mCropVPointDrawable == null) {
            return;
        }
        int i7 = (intrinsicWidth + intrinsicWidth2) / 2;
        int i8 = (intrinsicHeight + intrinsicHeight2) / 2;
        int clipRectWidth = getClipRectWidth() / 3;
        int clipRectHeight = getClipRectHeight() / 3;
        int i9 = i7 - clipRectWidth;
        int i10 = i7 + clipRectWidth;
        this.mCropHPointDrawable.setBounds(i9, intrinsicHeight - clipRectHeight, i10, intrinsicHeight + clipRectHeight);
        this.mCropHPointDrawable.draw(canvas);
        this.mCropHPointDrawable.setBounds(i9, intrinsicHeight2 - clipRectHeight, i10, intrinsicHeight2 + clipRectHeight);
        this.mCropHPointDrawable.draw(canvas);
        int i11 = i8 - clipRectWidth;
        int i12 = i8 + clipRectWidth;
        this.mCropVPointDrawable.setBounds(intrinsicWidth - clipRectHeight, i11, intrinsicWidth + clipRectHeight, i12);
        this.mCropVPointDrawable.draw(canvas);
        this.mCropVPointDrawable.setBounds(intrinsicWidth2 - clipRectHeight, i11, intrinsicWidth2 + clipRectHeight, i12);
        this.mCropVPointDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(Rect rect) {
        super.setBounds(new Rect(rect.left - (getCirleWidth() / 2), rect.top - (getCirleHeight() / 2), rect.right + (getCirleWidth() / 2), rect.bottom + (getCirleHeight() / 2)));
    }
}
