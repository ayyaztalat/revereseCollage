package com.winflag.libsquare.widget.label;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.photoart.libsticker.sticker.DMOnlineSticker;
import com.picspool.instatextview.textview.DMShowTextStickerView;


/* loaded from: classes3.dex */
public class CSShowTextStickerView extends DMShowTextStickerView {
    public CSShowTextStickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // org.picspool.instatextview.textview.DMShowTextStickerView
    public int getStickerCount() {
        if (this.surfaceView != null) {
            return this.surfaceView.getStickersCount();
        }
        return 0;
    }

    public void addSticker(Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        DMOnlineSticker dMOnlineSticker = new DMOnlineSticker(getWidth());
        dMOnlineSticker.setBitmap(bitmap);
        float width = (getWidth() / 3.0f) / dMOnlineSticker.getWidth();
        Matrix matrix = new Matrix();
        Matrix matrix2 = new Matrix();
        Matrix matrix3 = new Matrix();
        matrix3.postScale(width, width);
        matrix2.postTranslate(this.surfaceView.getWidth() / 6.0f, this.surfaceView.getHeight() / 6.0f);
        this.surfaceView.addSticker(dMOnlineSticker, matrix, matrix2, matrix3);
        if (this.surfaceView.getVisibility() != View.VISIBLE) {
            this.surfaceView.setVisibility(View.VISIBLE);
        }
        this.surfaceView.invalidate();
        this.surfaceView.onShow();
    }

    @Override // org.picspool.instatextview.textview.DMShowTextStickerView, org.picspool.lib.sticker.util.DMStickerStateCallback
    public void editButtonClicked() {
        super.editButtonClicked();
        if (this.seletedBMSticker != null && (this.seletedBMSticker instanceof DMOnlineSticker)) {
            Bitmap bitmap = this.seletedBMSticker.getBitmap();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            this.surfaceView.removeCurSelectedSticker();
            this.seletedBMSticker = null;
        }
        System.gc();
    }
}
