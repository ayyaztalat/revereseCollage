package com.picspool.libfuncview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSRoundImageView extends androidx.appcompat.widget.AppCompatImageView {
    float cornerw;
    float height;
    float width;

    public CSRoundImageView(Context context) {
        this(context, null);
    }

    public CSRoundImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CSRoundImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        this.cornerw = context.getResources().getDimension(R.dimen.libui_imgview_corner);
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.width = getWidth();
        this.height = getHeight();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        float f = this.width;
        float f2 = this.cornerw;
        if (f > f2 && this.height > f2) {
            Path path = new Path();
            path.moveTo(this.cornerw, 0.0f);
            path.lineTo(this.width - this.cornerw, 0.0f);
            float f3 = this.width;
            path.quadTo(f3, 0.0f, f3, this.cornerw);
            path.lineTo(this.width, this.height - this.cornerw);
            float f4 = this.width;
            float f5 = this.height;
            path.quadTo(f4, f5, f4 - this.cornerw, f5);
            path.lineTo(this.cornerw, this.height);
            float f6 = this.height;
            path.quadTo(0.0f, f6, 0.0f, f6 - this.cornerw);
            path.lineTo(0.0f, this.cornerw);
            path.quadTo(0.0f, 0.0f, this.cornerw, 0.0f);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}
