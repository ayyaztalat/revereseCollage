package com.picspool.lib.text.sticker.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import com.sky.testproject.R;
import com.picspool.lib.sticker.core.DMSticker;
import com.picspool.lib.text.DMTextDrawer;

/* loaded from: classes3.dex */
public class DMSmallTextSticker extends DMSticker {
    private DMTextDrawer BMTextDrawer;
    private Bitmap textBitmap;
    private int textPadding;

    public DMSmallTextSticker(DMTextDrawer dMTextDrawer, int i) {
        super(i);
        this.textPadding = 50;
        this.BMTextDrawer = dMTextDrawer;
        this.textPadding = (int) dMTextDrawer.getContext().getResources().getDimension(R.dimen.show_text_padding);
    }

    public void updateBitmap() {
        int width = this.BMTextDrawer.getContentRect().width();
        int height = this.BMTextDrawer.getContentRect().height();
        int width2 = this.BMTextDrawer.getTextContentRect().width();
        int height2 = this.BMTextDrawer.getTextContentRect().height();
        int i = this.textPadding;
        int i2 = width + (i * 2);
        int i3 = height + (i * 2);
        int i4 = (i2 - width2) / 2;
        int i5 = (i3 - height2) / 2;
        if (i2 <= 0 || i3 <= 0) {
            return;
        }
        this.textBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(this.textBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
        this.BMTextDrawer.drawInCanvas(canvas, i4, i5);
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
            Bitmap bitmap2 = this.textBitmap;
            if (bitmap2 != null) {
                canvas.drawBitmap(bitmap2, matrix, paint);
            }
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
}
