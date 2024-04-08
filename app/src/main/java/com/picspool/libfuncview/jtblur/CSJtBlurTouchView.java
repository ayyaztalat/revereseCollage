package com.picspool.libfuncview.jtblur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.filter.gpu.AsyncGPUFilter;
import com.picspool.lib.filter.gpu.newfilter.GPUImageGaussianBlurNewFilter;
import com.picspool.lib.filter.listener.OnPostFilteredListener;

/* loaded from: classes.dex */
public class CSJtBlurTouchView extends View {
    private static final String TAG = "CSJtBlurTouchView";
    private Bitmap blurBmp;
    private BlurMaskFilter blurMaskFilter;
    private float fcx;
    private float frRadius;
    private float fxy;
    private final GPUImageGaussianBlurNewFilter gpuImageGaussianBlurFilter;
    private boolean isTipmode;
    private Context mContext;
    private int mHeight;
    private int mWidth;
    private float moveDistance;
    private Paint paint;
    private int pointmode;
    private float preDistance;

    /* renamed from: px */
    private float f1663px;

    /* renamed from: py */
    private float f1664py;
    private RectF rectF;
    private boolean redrawXgCanvas;
    private int sgBwidth;
    private int sgPointGRound;
    private int sgPointGray;
    private float sgRatio;
    private List<CSJtsgPoint> sgrlist;
    private Bitmap srcBmp;
    private Bitmap xgBmp;
    private Canvas xgcanvas;
    private Bitmap yhBmp;
    private Canvas yhcanvas;

    private int getColorGray(int i) {
        return (((((16711680 & i) >> 16) * 38) + (((65280 & i) >> 8) * 75)) + ((i & 255) * 15)) >> 7;
    }

    public void setSgRatio(float f) {
        this.sgRatio = f;
        this.redrawXgCanvas = true;
        invalidate();
    }

    public void setSgBwidth(int i) {
        this.sgBwidth = i;
        refreshLPoints();
        invalidate();
    }

    public void setSgPointGray(int i) {
        this.sgPointGray = i;
        refreshLPoints();
        invalidate();
    }

    public void setSgPointGRound(int i) {
        this.sgPointGRound = i;
        refreshLPoints();
        invalidate();
    }

    public CSJtBlurTouchView(Context context, Bitmap bitmap, int i, int i2) {
        super(context);
        this.frRadius = 300.0f;
        this.sgRatio = 1.5f;
        this.sgBwidth = 100;
        this.sgPointGray = 120;
        this.sgPointGRound = 10;
        this.sgrlist = new ArrayList();
        this.redrawXgCanvas = true;
        setWillNotDraw(false);
        this.mContext = context;
        this.srcBmp = bitmap;
        GPUImageGaussianBlurNewFilter gPUImageGaussianBlurNewFilter = new GPUImageGaussianBlurNewFilter();
        this.gpuImageGaussianBlurFilter = gPUImageGaussianBlurNewFilter;
        gPUImageGaussianBlurNewFilter.setBlurSize(3.0f);
        AsyncGPUFilter.executeAsyncFilter(bitmap, this.gpuImageGaussianBlurFilter, new OnPostFilteredListener() { // from class: com.picspool.libfuncview.jtblur.CSJtBlurTouchView.1
            @Override // com.picspool.lib.filter.listener.OnPostFilteredListener
            public void postFiltered(Bitmap bitmap2) {
                CSJtBlurTouchView.this.blurBmp = bitmap2;
                CSJtBlurTouchView.this.refreshLPoints();
            }
        });
        this.mWidth = i;
        this.mHeight = i2;
        this.fcx = i / 2.0f;
        this.fxy = i2 / 2.0f;
        this.paint = new Paint(1);
        this.rectF = new RectF(0.0f, 0.0f, this.mWidth, this.mHeight);
        this.yhBmp = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
        this.yhcanvas = new Canvas(this.yhBmp);
        this.xgBmp = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
        this.xgcanvas = new Canvas(this.xgBmp);
        setSgRatio(1.0f);
    }

    public void refreshLPoints() {
        Bitmap bitmap = null;
        this.redrawXgCanvas = true;
        Bitmap sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(this.srcBmp, this.sgBwidth);
        int width = sampeZoomFromBitmap.getWidth();
        int height = sampeZoomFromBitmap.getHeight();
        int i = width * height;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        sampeZoomFromBitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i2 = 0; i2 < i; i2++) {
            iArr2[i2] = getColorGray(iArr[i2]);
        }
        this.sgrlist.clear();
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr2[i3];
            int i5 = i3 % width;
            int i6 = i3 / width;
            int i7 = i5 - 1;
            if (i7 < 0) {
                i7 = 0;
            }
            int i8 = i5 + 1;
            int i9 = width - 1;
            if (i8 > i9) {
                i8 = i9;
            }
            int i10 = i6 - 1;
            if (i10 < 0) {
                i10 = 0;
            }
            int i11 = i6 + 1;
            int i12 = height - 1;
            if (i11 > i12) {
                i11 = i12;
            }
            int i13 = 0;
            for (int i14 = i7; i14 <= i8; i14++) {
                for (int i15 = i10; i15 <= i11; i15++) {
                    i13 += iArr2[(i15 * width) + i14];
                }
            }
            int i16 = (i13 / ((i8 - i7) + 1)) / ((i11 - i10) + 1);
            if (i4 > this.sgPointGray && i4 - i16 > this.sgPointGRound) {
                float f = (i5 + 0.5f) / width;
                float f2 = (i6 + 0.5f) / height;
                int pixel = this.srcBmp.getPixel((int) (bitmap.getWidth() * f), (int) (this.srcBmp.getHeight() * f2));
                int colorGray = getColorGray(pixel);
                CSJtsgPoint cSJtsgPoint = new CSJtsgPoint();
                cSJtsgPoint.setPointF(new PointF(f, f2));
                cSJtsgPoint.setColor(pixel);
                cSJtsgPoint.setGray(colorGray);
                this.sgrlist.add(cSJtsgPoint);
            }
        }
        Collections.sort(this.sgrlist, new Comparator<CSJtsgPoint>() { // from class: com.picspool.libfuncview.jtblur.CSJtBlurTouchView.2
            @Override // java.util.Comparator
            public int compare(CSJtsgPoint cSJtsgPoint2, CSJtsgPoint cSJtsgPoint3) {
                if (cSJtsgPoint2.getGray() == cSJtsgPoint3.getGray()) {
                    return 0;
                }
                return cSJtsgPoint2.getGray() > cSJtsgPoint3.getGray() ? 1 : -1;
            }
        });
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.f1663px = motionEvent.getX();
            this.f1664py = motionEvent.getY();
            this.pointmode = 1;
            this.isTipmode = true;
        } else if (actionMasked == 5) {
            this.preDistance = getDistance(motionEvent);
            this.pointmode = 2;
            this.isTipmode = true;
        } else if (actionMasked == 2) {
            if (this.pointmode == 2 && motionEvent.getPointerCount() > 1) {
                this.moveDistance = getDistance(motionEvent) - this.preDistance;
            } else if (this.pointmode == 1) {
                this.moveDistance = 0.0f;
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                float f2 = (this.fcx + x) - this.f1663px;
                this.fcx = f2;
                this.fxy = (this.fxy + y) - this.f1664py;
                if (f2 < 0.0f) {
                    f = 0.0f;
                } else {
                    f = f2 > ((float) getWidth()) ? getWidth() : this.fcx;
                }
                this.fcx = f;
                float f3 = this.fxy;
                this.fxy = f3 >= 0.0f ? f3 > ((float) getHeight()) ? getHeight() : this.fxy : 0.0f;
                this.f1663px = x;
                this.f1664py = y;
            }
        } else if (actionMasked == 1 || actionMasked == 1 || actionMasked == 3) {
            this.isTipmode = false;
        }
        invalidate();
        return true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.blurBmp;
        if (bitmap == null) {
            return;
        }
        canvas.drawBitmap(bitmap, (Rect) null, this.rectF, (Paint) null);
        drawsgBmp(canvas);
        drawyhBmp(canvas);
    }

    private void drawsgBmp(Canvas canvas) {
        if (this.redrawXgCanvas) {
            this.redrawXgCanvas = false;
            this.paint.reset();
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            this.xgcanvas.drawRect(0.0f, 0.0f, this.mWidth, this.mHeight, this.paint);
            this.paint.reset();
            this.paint.setStyle(Paint.Style.FILL);
            for (CSJtsgPoint cSJtsgPoint : this.sgrlist) {
                if (cSJtsgPoint.getGrayAlhpa() < 180) {
                    this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
                } else {
                    this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
                }
                this.paint.setColor(cSJtsgPoint.getColor());
                this.paint.setAlpha(cSJtsgPoint.getGray());
                float height = this.sgRatio * (((getHeight() > getWidth() ? getHeight() : getWidth()) / this.sgBwidth) / 2.0f);
                if (height != 0.0f) {
                    BlurMaskFilter blurMaskFilter = new BlurMaskFilter(height * 0.2f, BlurMaskFilter.Blur.NORMAL);
                    this.blurMaskFilter = blurMaskFilter;
                    this.paint.setMaskFilter(blurMaskFilter);
                }
            }
        }
        canvas.drawBitmap(this.xgBmp, (Rect) null, this.rectF, (Paint) null);
    }

    private void drawyhBmp(Canvas canvas) {
        RectF rectF = new RectF(0.0f, 0.0f, getWidth(), getHeight());
        this.paint.reset();
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.yhcanvas.drawRect(0.0f, 0.0f, this.mWidth, this.mHeight, this.paint);
        float f = this.frRadius + (this.moveDistance / 10.0f);
        this.frRadius = f;
        float f2 = 150.0f;
        if (f >= 150.0f) {
            f2 = f > ((float) (getWidth() / 3)) ? getWidth() / 3 : this.frRadius;
        }
        this.frRadius = f2;
        this.paint.reset();
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(this.frRadius - 50.0f, BlurMaskFilter.Blur.NORMAL);
        this.blurMaskFilter = blurMaskFilter;
        this.paint.setMaskFilter(blurMaskFilter);
        this.paint.setColor(-16711936);
        this.yhcanvas.drawCircle(this.fcx, this.fxy, this.frRadius, this.paint);
        this.paint.reset();
        if (this.isTipmode) {
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            this.paint.setColor(-285212673);
            this.yhcanvas.drawRect(rectF, this.paint);
            canvas.drawBitmap(this.srcBmp, (Rect) null, rectF, (Paint) null);
        } else {
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            this.paint.setColor(-1);
            this.yhcanvas.drawBitmap(this.srcBmp, (Rect) null, rectF, this.paint);
        }
        canvas.drawBitmap(this.yhBmp, (Rect) null, rectF, (Paint) null);
    }

    private float getDistance(MotionEvent motionEvent) {
        float x = motionEvent.getX(1) - motionEvent.getX(0);
        float y = motionEvent.getY(1) - motionEvent.getY(0);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    public static float[] fivePoints(float f, float f2, int i) {
        double d = f;
        double d2 = i;
        float sin = (float) (d - (Math.sin(Math.toRadians(18.0d)) * d2));
        float sin2 = (float) (d + (Math.sin(Math.toRadians(18.0d)) * d2));
        double d3 = f2;
        float cos = (float) ((Math.cos(Math.toRadians(18.0d)) * d2) + d3);
        int i2 = i / 2;
        float sqrt = (float) (d3 + Math.sqrt(Math.pow(sin2 - sin, 2.0d) - Math.pow(i2, 2.0d)));
        float f3 = i2;
        return new float[]{f, f2, sin, cos, f + f3, sqrt, f - f3, sqrt, sin2, cos, f, f2};
    }
}
