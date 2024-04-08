package com.picspool.lib.view.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/* loaded from: classes3.dex */
public class DMIgnoreRecycleImageView extends ImageView {
    public Bitmap image;

    public DMIgnoreRecycleImageView(Context context) {
        super(context);
    }

    public DMIgnoreRecycleImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DMIgnoreRecycleImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        this.image = bitmap;
        super.setImageBitmap(bitmap);
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
