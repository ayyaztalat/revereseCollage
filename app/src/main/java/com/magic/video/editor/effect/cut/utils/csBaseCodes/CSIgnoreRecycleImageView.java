package com.magic.video.editor.effect.cut.utils.csBaseCodes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;

/* renamed from: com.magic.video.editor.effect.cut.utils.bg.CSIgnoreRecycleImageView */
/* loaded from: classes2.dex */
public class CSIgnoreRecycleImageView extends androidx.appcompat.widget.AppCompatImageView {
    public Bitmap image;

    public CSIgnoreRecycleImageView(Context context) {
        super(context);
    }

    public CSIgnoreRecycleImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CSIgnoreRecycleImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        this.image = bitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            super.setImageBitmap(bitmap);
        } else {
            super.setImageBitmap(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Throwable unused) {
        }
    }
}
