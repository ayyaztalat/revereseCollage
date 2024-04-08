package com.picspool.lib.onlinestore.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes3.dex */
public class DMDownloadView extends View {
    private int padding;
    private Paint paint;
    private int progress;

    public DMDownloadView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.progress = 0;
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(-1);
        this.paint.setAntiAlias(true);
        this.padding = DMScreenInfoUtil.dip2px(getContext(), 10.0f);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.progress > 98) {
            return;
        }
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setAlpha(255);
        this.paint.setStrokeWidth(DMScreenInfoUtil.dip2px(getContext(), 5.0f));
        int i = this.padding;
        RectF rectF = new RectF(i, i, getWidth() - this.padding, getHeight() - this.padding);
        canvas.drawOval(rectF, this.paint);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setAlpha(DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA);
        canvas.drawArc(rectF, -90.0f, (this.progress * 360) / 100, true, this.paint);
    }

    public void iniProgress() {
        this.progress = 0;
    }

    public void updateProgress(int i) {
        this.progress = i;
        invalidate();
    }
}
