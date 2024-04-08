package com.picspool.lib.view.redraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes3.dex */
public abstract class DMReDrawView extends View {
    protected int canvasHeight;
    protected Rect canvasRect;
    protected int canvasWidth;
    protected PorterDuffXfermode clearXfermode;
    protected PorterDuffXfermode dstInXfermode;
    protected PorterDuffXfermode dstOutXfermode;
    protected Paint mPaint;
    protected PorterDuffXfermode srcInXfermode;
    protected PorterDuffXfermode srcOutXfermode;
    protected PorterDuffXfermode xorXfermode;

    public abstract void drawView(Canvas canvas);

    public DMReDrawView(Context context) {
        super(context);
        this.mPaint = new Paint();
        this.clearXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        this.dstInXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.xorXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        this.srcInXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.dstOutXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.srcOutXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        this.canvasWidth = 0;
        this.canvasHeight = 0;
        this.canvasRect = new Rect();
        init();
    }

    public DMReDrawView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint = new Paint();
        this.clearXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        this.dstInXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.xorXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        this.srcInXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.dstOutXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.srcOutXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        this.canvasWidth = 0;
        this.canvasHeight = 0;
        this.canvasRect = new Rect();
        init();
    }

    public DMReDrawView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint = new Paint();
        this.clearXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        this.dstInXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.xorXfermode = new PorterDuffXfermode(PorterDuff.Mode.XOR);
        this.srcInXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        this.dstOutXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.srcOutXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT);
        this.canvasWidth = 0;
        this.canvasHeight = 0;
        this.canvasRect = new Rect();
        init();
    }

    private void init() {
        this.mPaint.setDither(true);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
        setWillNotDraw(false);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.canvasWidth = getWidth();
        int height = getHeight();
        this.canvasHeight = height;
        this.canvasRect.set(0, 0, this.canvasWidth, height);
        drawView(canvas);
    }
}
