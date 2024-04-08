package com.picspool.lib.widget.colorpicker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/* loaded from: classes3.dex */
public class DMAlphaPatternDrawable extends Drawable {
    private Bitmap mBitmap;
    private int mRectangleSize;
    private int numRectanglesHorizontal;
    private int numRectanglesVertical;
    private Paint mPaint = new Paint();
    private Paint mPaintWhite = new Paint();
    private Paint mPaintGray = new Paint();

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    public DMAlphaPatternDrawable(int i) {
        this.mRectangleSize = 10;
        this.mRectangleSize = i;
        this.mPaintWhite.setColor(-1);
        this.mPaintGray.setColor(-3421237);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.mBitmap, (Rect) null, getBounds(), this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        throw new UnsupportedOperationException("Alpha is not supported by this drawwable.");
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException("ColorFilter is not supported by this drawwable.");
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        int height = rect.height();
        this.numRectanglesHorizontal = (int) Math.ceil(rect.width() / this.mRectangleSize);
        this.numRectanglesVertical = (int) Math.ceil(height / this.mRectangleSize);
        generatePatternBitmap();
    }

    private void generatePatternBitmap() {
        if (getBounds().width() <= 0 || getBounds().height() <= 0) {
            return;
        }
        this.mBitmap = Bitmap.createBitmap(getBounds().width(), getBounds().height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(this.mBitmap);
        Rect rect = new Rect();
        boolean z = true;
        for (int i = 0; i <= this.numRectanglesVertical; i++) {
            boolean z2 = z;
            for (int i2 = 0; i2 <= this.numRectanglesHorizontal; i2++) {
                rect.top = this.mRectangleSize * i;
                rect.left = this.mRectangleSize * i2;
                rect.bottom = rect.top + this.mRectangleSize;
                rect.right = rect.left + this.mRectangleSize;
                canvas.drawRect(rect, z2 ? this.mPaintWhite : this.mPaintGray);
                z2 = !z2;
            }
            z = !z;
        }
    }
}
