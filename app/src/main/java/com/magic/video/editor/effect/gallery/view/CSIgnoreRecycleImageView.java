package com.magic.video.editor.effect.gallery.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes2.dex */
public class CSIgnoreRecycleImageView extends AppCompatImageView {
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

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
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
