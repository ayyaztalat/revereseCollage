package com.picspool.snappic.view;

import android.content.Context;
import android.util.AttributeSet;
import com.picspool.instatextview.textview.DMShowTextStickerView3;

/* loaded from: classes.dex */
public class CSMyShowTextStickerView3 extends DMShowTextStickerView3 {
    public CSMyShowTextStickerView3(Context context) {
        super(context);
    }

    public CSMyShowTextStickerView3(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void clenaTextView() {
        this.surfaceView.clearStickers();
        this.surfaceView.requestLayout();
        this.surfaceView.cancelSelected();
    }
}
