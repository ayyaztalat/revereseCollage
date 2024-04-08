package com.photo.editor.square.splash.view.glitch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class ILCorner123Image456View extends AppCompatImageView {
    private int ilConnerColor;
    private int ilCorner;
    private final Paint ilPaint;

    public ILCorner123Image456View(Context context) {
        super(context);
        this.ilPaint = new Paint();
        init(context, null);
    }

    public ILCorner123Image456View(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ilPaint = new Paint();
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CornerImageView);
        this.ilConnerColor = obtainStyledAttributes.getColor(R.styleable.CornerImageView_corner_color, 0);
        this.ilCorner = (int) obtainStyledAttributes.getDimension(R.styleable.CornerImageView_corner_area, 0.0f);
        obtainStyledAttributes.recycle();
        this.ilPaint.setColor(this.ilConnerColor);
        this.ilPaint.setDither(true);
        this.ilPaint.setAntiAlias(true);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap);
            canvas2.drawRect(new Rect(0, 0, getWidth(), getHeight()), this.ilPaint);
            this.ilPaint.setColor(0);
            this.ilPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas2.drawRoundRect(new RectF(0.0f, 0.0f, getWidth(), getHeight()), this.ilCorner, this.ilCorner, this.ilPaint);
            canvas2.setBitmap(null);
            this.ilPaint.setColor(this.ilConnerColor);
            this.ilPaint.setXfermode(null);
            if (createBitmap == null || createBitmap.isRecycled()) {
                return;
            }
            canvas.drawBitmap(createBitmap, 0.0f, 0.0f, (Paint) null);
            createBitmap.recycle();
        } catch (Exception unused) {
        }
    }
}
