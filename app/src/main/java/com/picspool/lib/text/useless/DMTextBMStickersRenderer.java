package com.picspool.lib.text.useless;

import android.graphics.Canvas;
import android.view.MotionEvent;
import androidx.core.view.ViewCompat;
import com.picspool.lib.sticker.view.DMStickersRenderer;

/* loaded from: classes3.dex */
public class DMTextBMStickersRenderer extends DMStickersRenderer {
    @Override // com.picspool.lib.sticker.view.DMStickersRenderer
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    @Override // com.picspool.lib.sticker.view.DMStickersRenderer, com.picspool.lib.sticker.util.DMRenderer
    public void drawFrame(Canvas canvas) {
        canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
        super.drawFrame(canvas);
    }
}
