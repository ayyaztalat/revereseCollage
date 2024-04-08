package com.picspool.lib.sephiroth.android.library.imagezoom.graphics;

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

/* loaded from: classes3.dex */
public class FastBitmapDrawable extends Drawable implements IBitmapDrawable {
    protected int mIntrinsicHeight;
    protected int mIntrinsicWidth;
    protected Paint mPaint;
    private float mradius;
    protected Bitmap oriBitmap;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public FastBitmapDrawable(Bitmap bitmap) {
        this.mradius = 0.0f;
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
    }

    public void setBitmap(Bitmap bitmap) {
        this.oriBitmap = bitmap;
    }

    public FastBitmapDrawable(Resources resources, InputStream inputStream) {
        this(BitmapFactory.decodeStream(inputStream));
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Bitmap bitmap = this.oriBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            canvas.drawBitmap(this.oriBitmap, 0.0f, 0.0f, this.mPaint);
        }
    }

    public Bitmap toRoundCorner(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        float f = this.mradius;
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
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

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.graphics.IBitmapDrawable
    public Bitmap getBitmap() {
        return this.oriBitmap;
    }

    public Paint getPaint() {
        return this.mPaint;
    }
}
