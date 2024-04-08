package com.picspool.lib.text.sticker.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import com.sky.testproject.R;
import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.text.DMTextDrawer;

/* loaded from: classes3.dex */
public class DMSmallTextSticker3 extends DMSticker {
    private DMTextDrawer BMTextDrawer;
    private Bitmap textBitmap;
    private int textPadding;

    public DMSmallTextSticker3(DMTextDrawer dMTextDrawer, int i) {
        super(i);
        this.textPadding = 50;
        this.BMTextDrawer = dMTextDrawer;
        this.textPadding = (int) dMTextDrawer.getContext().getResources().getDimension(R.dimen.show_text_padding);
    }

    public void updateBitmap() {
        int i;
        int i2;
        int actualWidth = this.BMTextDrawer.getActualWidth();
        int actualHeight = this.BMTextDrawer.getActualHeight();
        int width = this.BMTextDrawer.getTextContentRect().width();
        int height = this.BMTextDrawer.getTextContentRect().height();
        if (this.BMTextDrawer.isBackgroundImageDrawable()) {
            i = (int) (((this.BMTextDrawer.mBgW - width) / 2.0f) + this.BMTextDrawer.mBgX);
            i2 = (int) (((this.BMTextDrawer.mBgH - height) / 2.0f) + this.BMTextDrawer.mBgY);
        } else {
            int i3 = this.textPadding;
            actualWidth += i3 * 2;
            actualHeight += i3 * 2;
            i = (actualWidth - width) / 2;
            i2 = (actualHeight - height) / 2;
        }
        if (actualWidth <= 0 || actualHeight <= 0) {
            return;
        }
        Bitmap bitmap = this.textBitmap;
        if (bitmap != null) {
            if (!bitmap.isRecycled()) {
                this.textBitmap.recycle();
            }
            this.textBitmap = null;
        }
        this.textBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(this.textBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        this.BMTextDrawer.drawInCanvas(canvas, i, i2);
    }

    @Override // com.picspool.lib.sticker.core.DMSticker
    public int getWidth() {
        Bitmap bitmap = this.textBitmap;
        if (bitmap != null) {
            return bitmap.getWidth();
        }
        return 0;
    }

    @Override // com.picspool.lib.sticker.core.DMSticker
    public int getHeight() {
        Bitmap bitmap = this.textBitmap;
        if (bitmap != null) {
            return bitmap.getHeight();
        }
        return 0;
    }

    @Override // com.picspool.lib.sticker.core.DMSticker
    public Bitmap getBitmap() {
        Bitmap bitmap = this.textBitmap;
        return bitmap != null ? bitmap : super.getBitmap();
    }

    @Override // com.picspool.lib.sticker.core.DMSticker
    public void drawInCanvas(Canvas canvas) {
        super.drawInCanvas(canvas);
        if (this.textBitmap != null) {
            Matrix matrix = this.transform;
            Bitmap bitmap = super.getBitmap();
            if (bitmap != null) {
                float width = bitmap.getWidth() / this.textBitmap.getWidth();
                float height = bitmap.getHeight() / this.textBitmap.getHeight();
                if (width >= height) {
                    width = height;
                }
                matrix.postScale(width, width);
            }
            Paint paint = new Paint();
            paint.setAlpha(this.alpha);
            paint.setAntiAlias(true);
            canvas.drawBitmap(this.textBitmap, matrix, paint);
        }
    }

    public void releaseImage() {
        DMTextDrawer dMTextDrawer = this.BMTextDrawer;
        if (dMTextDrawer != null) {
            dMTextDrawer.cleanImagerDrawable();
            Bitmap bitmap = this.textBitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                this.textBitmap.recycle();
            }
            this.textBitmap = null;
        }
    }

    public DMTextDrawer getTextDrawer() {
        return this.BMTextDrawer;
    }

    public void setTextDrawer(DMTextDrawer dMTextDrawer) {
        this.BMTextDrawer = dMTextDrawer;
    }

    @Override // com.picspool.lib.sticker.core.DMSticker
    public Bitmap getStickerIcon(int i, int i2) {
        Bitmap bitmap = this.textBitmap;
        if (bitmap != null) {
            Bitmap cropCenterScaleBitmap = DMBitmapCrop.cropCenterScaleBitmap(bitmap, i, i2);
            Bitmap bitmap2 = this.textBitmap;
            return bitmap2 == cropCenterScaleBitmap ? bitmap2.copy(Bitmap.Config.ARGB_8888, true) : cropCenterScaleBitmap;
        }
        return super.getStickerIcon(i, i2);
    }
}
