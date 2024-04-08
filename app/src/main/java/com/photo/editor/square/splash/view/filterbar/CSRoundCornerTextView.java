package com.photo.editor.square.splash.view.filterbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;

/* loaded from: classes2.dex */
public class CSRoundCornerTextView extends AppCompatTextView {
    private int color;
    private ShapeDrawable mShapeDrawable;

    public CSRoundCornerTextView(Context context) {
        super(context);
        this.color = 0;
    }

    public CSRoundCornerTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.color = 0;
        float dip2px = CSScreenInfoUtil.dip2px(getContext(), 8.0f);
        this.mShapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{dip2px, dip2px, dip2px, dip2px, dip2px, dip2px, dip2px, dip2px}, null, new float[]{dip2px, dip2px, dip2px, dip2px, dip2px, dip2px, dip2px, dip2px}));
    }

    public CSRoundCornerTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.color = 0;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        this.mShapeDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        this.mShapeDrawable.getPaint().setColor(this.color);
        this.mShapeDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.color = i;
        invalidate();
    }
}
