package com.photo.editor.square.splash.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes2.dex */
public class CSSpiralView extends View {
    private static final String TAG = "SpiralViewT";
    private Bitmap bitmapBck;
    private Bitmap bitmapBckDisplay;
    private Bitmap bitmapFnt;
    private Bitmap bitmapMsk;
    private Bitmap bitmapSrc;
    private CSEffectRes effectRes;
    private Context mContext;
    private int mHeight;
    private int mWidth;
    private CSTouchView touchView;

    public CSSpiralView(Context context, int i, int i2, Bitmap bitmap) {
        super(context);
        this.mHeight = i2;
        this.mWidth = i;
        this.bitmapSrc = bitmap;
        this.mContext = context;
        setupRes(null);
    }

    public void setupMaskBitmap(Bitmap bitmap) {
        this.bitmapMsk = bitmap;
        invalidate();
    }

    public void setupRes(CSEffectRes cSEffectRes) {
        this.effectRes = cSEffectRes;
        this.bitmapFnt = cSEffectRes == null ? null : cSEffectRes.getFgBitmap(this.mContext);
        Bitmap bgBitmap = cSEffectRes != null ? cSEffectRes.getBgBitmap(this.mContext) : null;
        this.bitmapBck = bgBitmap;
        if (cSEffectRes == null || bgBitmap == null) {
            this.bitmapBckDisplay = Bitmap.createBitmap(this.bitmapSrc.getWidth(), this.bitmapSrc.getHeight(), Bitmap.Config.ARGB_8888);
            invalidate();
            return;
        }
        Bitmap createBitmap = Bitmap.createBitmap(this.bitmapSrc.getWidth(), this.bitmapSrc.getHeight(), Bitmap.Config.ARGB_8888);
        this.bitmapBckDisplay = createBitmap;
        CSTouchView cSTouchView = new CSTouchView(this, createBitmap, this.bitmapBck);
        this.touchView = cSTouchView;
        cSTouchView.setupDefualtPosition(0.0f, 0.0f, 1.0f);
        this.touchView.setMinScaleRatio(0.4f);
        invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        CSTouchView cSTouchView = this.touchView;
        if (cSTouchView != null) {
            cSTouchView.onTouchEvent(motionEvent);
            return true;
        }
        return true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            drawMain(canvas, this.mWidth, this.mHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawMain(Canvas canvas, int i, int i2) {
        CSTouchView cSTouchView;
        CSTouchView cSTouchView2;
        Bitmap bitmap = this.bitmapSrc;
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(this.bitmapSrc, new Rect(0, 0, this.bitmapSrc.getWidth(), this.bitmapSrc.getHeight()), new Rect(0, 0, i, i2), (Paint) null);
        }
        try {
            this.touchView.postMatrix();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Canvas canvas2 = new Canvas(this.bitmapBckDisplay);
        canvas2.drawColor(-16711936, PorterDuff.Mode.CLEAR);
        Bitmap bitmap2 = this.bitmapBck;
        if (bitmap2 != null && (cSTouchView2 = this.touchView) != null) {
            canvas2.drawBitmap(bitmap2, cSTouchView2.getMatrix(), null);
        }
        if (this.bitmapMsk != null && this.bitmapBck != null) {
            Paint paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            canvas2.drawBitmap(this.bitmapMsk, new Rect(0, 0, this.bitmapMsk.getWidth(), this.bitmapMsk.getHeight()), new Rect(0, 0, this.bitmapBckDisplay.getWidth(), this.bitmapBckDisplay.getHeight()), paint);
        }
        Bitmap bitmap3 = this.bitmapFnt;
        if (bitmap3 != null && (cSTouchView = this.touchView) != null) {
            canvas2.drawBitmap(bitmap3, cSTouchView.getMatrix(), null);
        }
        Rect rect = new Rect(0, 0, this.bitmapBckDisplay.getWidth(), this.bitmapBckDisplay.getHeight());
        Rect rect2 = new Rect(0, 0, i, i2);
        Bitmap bitmap4 = this.bitmapBckDisplay;
        if (bitmap4 != null) {
            canvas.drawBitmap(bitmap4, rect, rect2, (Paint) null);
        }
    }

    public Bitmap getBitmapBckDisplay() {
        Bitmap createBitmap = Bitmap.createBitmap(this.bitmapSrc.getWidth(), this.bitmapSrc.getHeight(), Bitmap.Config.ARGB_8888);
        drawMain(new Canvas(createBitmap), createBitmap.getWidth(), createBitmap.getHeight());
        return createBitmap;
    }

    public CSEffectRes getEffectRes() {
        return this.effectRes;
    }

    public Bitmap getBitmapMsk() {
        return this.bitmapMsk;
    }
}
