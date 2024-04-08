package com.magic.video.editor.effect.cut.utils.csBaseCodes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.magic.video.editor.effect.cut.utils.CSScreenInfoUtil;

/* renamed from: com.magic.video.editor.effect.cut.utils.bg.CSGalleryPointerView */
/* loaded from: classes2.dex */
public class CSGalleryPointerView extends View {
    private Rect centerItemRect;
    private int mBorderWidth;
    private Context mContext;
    private int mItemBorderColor;
    private int mItemHeight;
    private int mItemWidth;
    private Paint mPaint;
    private boolean mPointToBottom;
    private int mTriangleColor;
    private Path mTrianglePath;

    public CSGalleryPointerView(Context context) {
        super(context);
        this.mItemWidth = 0;
        this.mItemHeight = 0;
        this.mBorderWidth = 0;
        this.mPointToBottom = true;
        this.mPaint = new Paint();
        this.mItemBorderColor = -178155;
        this.mTriangleColor = -178155;
        this.mTrianglePath = new Path();
        this.centerItemRect = new Rect();
        this.mContext = context;
    }

    public CSGalleryPointerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mItemWidth = 0;
        this.mItemHeight = 0;
        this.mBorderWidth = 0;
        this.mPointToBottom = true;
        this.mPaint = new Paint();
        this.mItemBorderColor = -178155;
        this.mTriangleColor = -178155;
        this.mTrianglePath = new Path();
        this.centerItemRect = new Rect();
        this.mContext = context;
    }

    public CSGalleryPointerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mItemWidth = 0;
        this.mItemHeight = 0;
        this.mBorderWidth = 0;
        this.mPointToBottom = true;
        this.mPaint = new Paint();
        this.mItemBorderColor = -178155;
        this.mTriangleColor = -178155;
        this.mTrianglePath = new Path();
        this.centerItemRect = new Rect();
        this.mContext = context;
    }

    public void setPointerItemSize(int i, int i2) {
        this.mItemWidth = CSScreenInfoUtil.dip2px(this.mContext, i);
        int dip2px = CSScreenInfoUtil.dip2px(this.mContext, i2);
        this.mItemHeight = dip2px;
        int i3 = this.mItemWidth;
        if (i3 <= dip2px) {
            dip2px = i3;
        }
        int i4 = dip2px / 10;
        this.mBorderWidth = i4;
        if (i4 == 0) {
            this.mBorderWidth = 1;
        }
        this.mPaint.setStrokeWidth(this.mBorderWidth);
    }

    public void setPointToBottom(boolean z) {
        this.mPointToBottom = z;
    }

    public void setItemBorderColor(int i) {
        this.mItemBorderColor = i;
    }

    public void setTriangleColor(int i) {
        this.mTriangleColor = i;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.centerItemRect.left = (i - this.mItemWidth) / 2;
        Rect rect = this.centerItemRect;
        rect.right = rect.left + this.mItemWidth;
        if (this.mPointToBottom) {
            this.centerItemRect.top = i2 - this.mItemHeight;
            this.centerItemRect.bottom = i2;
        } else {
            this.centerItemRect.top = 0;
            this.centerItemRect.bottom = this.mItemHeight;
        }
        if (this.mBorderWidth == 1 && this.mPointToBottom) {
            this.centerItemRect.bottom--;
        } else {
            this.centerItemRect.left += this.mBorderWidth / 2;
            this.centerItemRect.top += this.mBorderWidth / 2;
            this.centerItemRect.right -= this.mBorderWidth / 2;
            this.centerItemRect.bottom -= this.mBorderWidth / 2;
        }
        this.mTrianglePath.reset();
        int i5 = (int) (((i2 - this.mItemHeight) / 1.732d) * 2.0d);
        if (this.mPointToBottom) {
            float f = (i - i5) / 2;
            this.mTrianglePath.moveTo(f, 0.0f);
            this.mTrianglePath.lineTo(i / 2, i2 - this.mItemHeight);
            this.mTrianglePath.lineTo((i + i5) / 2, 0.0f);
            this.mTrianglePath.lineTo(f, 0.0f);
        } else {
            float f2 = (i - i5) / 2;
            float f3 = i2;
            this.mTrianglePath.moveTo(f2, f3);
            this.mTrianglePath.lineTo(i / 2, this.mItemHeight);
            this.mTrianglePath.lineTo((i + i5) / 2, f3);
            this.mTrianglePath.lineTo(f2, f3);
        }
        this.mTrianglePath.close();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(this.mItemBorderColor);
        canvas.drawRect(this.centerItemRect, this.mPaint);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(this.mTriangleColor);
        canvas.drawPath(this.mTrianglePath, this.mPaint);
    }
}
