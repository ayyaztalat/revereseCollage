package com.picspool.lib.sticker.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;

/* loaded from: classes3.dex */
public class DM_ImageBackground {
    public int backgroundColor;
    public Bitmap bitmap;
    private int borderColor;
    private int mHeight;
    private Paint mPaint;
    private int mWidth;
    private int mActualWidth = 640;
    private int mActualHeight = 640;
    private boolean isTile = false;

    public DM_ImageBackground(Bitmap bitmap) {
        this.bitmap = bitmap;
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setDither(true);
        this.mPaint.setFilterBitmap(true);
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(int i) {
        this.backgroundColor = i;
    }

    public void draw(Canvas canvas) {
        int i;
        int i2 = this.mWidth;
        if (i2 <= 0 || (i = this.mHeight) <= 0) {
            return;
        }
        this.mActualHeight = (int) (this.mActualWidth * (i / i2));
        if (this.bitmap == null) {
            new Rect(0, 0, this.mWidth, this.mHeight);
            canvas.drawColor(this.backgroundColor);
        } else if (this.isTile) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(this.bitmap);
            bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            bitmapDrawable.setDither(true);
            bitmapDrawable.setBounds(new Rect(0, 0, this.mWidth, this.mHeight));
            bitmapDrawable.draw(canvas);
        } else {
            canvas.drawBitmap(this.bitmap, new Rect(0, 0, this.mWidth, this.mHeight), new Rect(0, 0, this.mWidth, this.mHeight), this.mPaint);
        }
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int i) {
        this.mWidth = i;
        if (i > this.mActualWidth) {
            this.mActualWidth = i;
        }
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int i) {
        this.mHeight = i;
        if (i > this.mActualHeight) {
            this.mActualHeight = i;
        }
    }

    public int getActualWidth() {
        return this.mActualWidth;
    }

    public int getActualHeight() {
        return this.mActualHeight;
    }

    public int getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(int i) {
        this.borderColor = i;
    }

    public boolean getIsTile() {
        return this.isTile;
    }

    public void setIsTile(boolean z) {
        this.isTile = z;
    }
}
