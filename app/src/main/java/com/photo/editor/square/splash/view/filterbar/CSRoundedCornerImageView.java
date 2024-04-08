package com.photo.editor.square.splash.view.filterbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSRoundedCornerImageView extends AppCompatImageView {
    private float mCornerRadius;

    public CSRoundedCornerImageView(Context context) {
        this(context, null);
    }

    public CSRoundedCornerImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CSRoundedCornerImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RoundedCornerImageView, i, 0);
        this.mCornerRadius = obtainStyledAttributes.getDimension(0, 0.0f);
        obtainStyledAttributes.recycle();
    }

    public void setCornerRadius(float f) {
        this.mCornerRadius = f;
        postInvalidate();
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        RoundedBitmapDrawable create = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        create.setCornerRadius(this.mCornerRadius);
        super.setImageDrawable(create);
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            if (drawable.getIntrinsicWidth() > 0 && drawable.getIntrinsicHeight() > 0) {
                Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.UNKNOWN ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                drawable.draw(canvas);
                setImageBitmap(createBitmap);
                return;
            }
            super.setImageDrawable(drawable);
            return;
        }
        super.setImageDrawable(drawable);
    }
}
