package com.picspool.snappic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import java.text.DecimalFormat;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSRuleView extends View {
    private static final String TAG = "CSRuleView";
    private Context context;

    /* renamed from: df */
    DecimalFormat f1694df;
    private DecimalFormat format;
    private float gap;
    private CSMyHorizontalScrollView horizontalScrollView;
    boolean isDraw;
    private float largeHeight;
    public onChangedListener listener;
    private int mCurrentX;
    private float mFontSize;
    private Handler mScrollHandler;
    private Runnable mScrollRunnable;
    private int maxValue;
    private DisplayMetrics metrics;
    Paint paint;
    int scrollWidth;
    private float smallHeight;
    private float startX;
    private float startY;
    private float textGap;
    private float unit;
    private float yLenght;

    /* loaded from: classes.dex */
    public interface onChangedListener {
        void onSlide(float f);
    }

    public void setMaxScaleValue(Float f) {
    }

    public void setMinScaleValue(Float f) {
    }

    public CSRuleView(Context context) {
        super(context);
        this.maxValue = 90;
        this.gap = 2.0f;
        this.textGap = 5.0f;
        this.smallHeight = 6.0f;
        this.largeHeight = 10.0f;
        this.metrics = null;
        this.mScrollHandler = null;
        this.mCurrentX = -999999999;
        this.unit = 5.0f;
        this.isDraw = true;
        this.f1694df = new DecimalFormat("0.0");
        this.scrollWidth = 0;
        this.mScrollRunnable = new Runnable() { // from class: com.picspool.snappic.view.CSRuleView.2
            @Override // java.lang.Runnable
            public void run() {
                Log.i(CSRuleView.TAG, "" + CSRuleView.this.horizontalScrollView.getScrollX());
                if (CSRuleView.this.mCurrentX == CSRuleView.this.horizontalScrollView.getScrollX()) {
                    try {
                        float scrollX = CSRuleView.this.horizontalScrollView.getScrollX() / (CSRuleView.this.gap * CSRuleView.this.unit);
                        DMScreenInfoUtil.screenWidth(CSRuleView.this.getContext());
                        CSRuleView.this.horizontalScrollView.smoothScrollTo((int) (Double.parseDouble(CSRuleView.this.f1694df.format(scrollX)) * CSRuleView.this.gap * CSRuleView.this.unit), 0);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    CSRuleView.this.mScrollHandler.removeCallbacks(this);
                    return;
                }
                CSRuleView cSRuleView = CSRuleView.this;
                cSRuleView.mCurrentX = cSRuleView.horizontalScrollView.getScrollX();
                CSRuleView.this.mScrollHandler.postDelayed(this, 50L);
            }
        };
        this.context = context;
        init();
    }

    public void setHorizontalScrollView(CSMyHorizontalScrollView cSMyHorizontalScrollView) {
        this.horizontalScrollView = cSMyHorizontalScrollView;
        cSMyHorizontalScrollView.setOnTouchListener(new OnTouchListener() { // from class: com.picspool.snappic.view.CSRuleView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 1) {
                    CSRuleView.this.mScrollHandler.post(CSRuleView.this.mScrollRunnable);
                    return false;
                } else if (action != 2) {
                    return false;
                } else {
                    CSRuleView.this.mScrollHandler.removeCallbacks(CSRuleView.this.mScrollRunnable);
                    return false;
                }
            }
        });
    }

    public CSRuleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.maxValue = 90;
        this.gap = 2.0f;
        this.textGap = 5.0f;
        this.smallHeight = 6.0f;
        this.largeHeight = 10.0f;
        this.metrics = null;
        this.mScrollHandler = null;
        this.mCurrentX = -999999999;
        this.unit = 5.0f;
        this.isDraw = true;
        this.f1694df = new DecimalFormat("0.0");
        this.scrollWidth = 0;
        this.mScrollRunnable = new Runnable() { // from class: com.picspool.snappic.view.CSRuleView.2
            @Override // java.lang.Runnable
            public void run() {
                Log.i(CSRuleView.TAG, "" + CSRuleView.this.horizontalScrollView.getScrollX());
                if (CSRuleView.this.mCurrentX == CSRuleView.this.horizontalScrollView.getScrollX()) {
                    try {
                        float scrollX = CSRuleView.this.horizontalScrollView.getScrollX() / (CSRuleView.this.gap * CSRuleView.this.unit);
                        DMScreenInfoUtil.screenWidth(CSRuleView.this.getContext());
                        CSRuleView.this.horizontalScrollView.smoothScrollTo((int) (Double.parseDouble(CSRuleView.this.f1694df.format(scrollX)) * CSRuleView.this.gap * CSRuleView.this.unit), 0);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    CSRuleView.this.mScrollHandler.removeCallbacks(this);
                    return;
                }
                CSRuleView cSRuleView = CSRuleView.this;
                cSRuleView.mCurrentX = cSRuleView.horizontalScrollView.getScrollX();
                CSRuleView.this.mScrollHandler.postDelayed(this, 50L);
            }
        };
        this.context = context;
        init();
    }

    public void init() {
        this.format = new DecimalFormat("0.0");
        this.metrics = new DisplayMetrics();
        ((WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(this.metrics);
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.paint.setStrokeWidth(getResources().getDimension(R.dimen.text_h2));
        this.paint.setColor(Color.parseColor("#333333"));
        this.mFontSize = DMScreenInfoUtil.dip2px(this.context, 10.0f);
        this.startY = DMScreenInfoUtil.dip2px(this.context, 5.0f);
        this.yLenght = DMScreenInfoUtil.dip2px(this.context, 10.0f);
        this.gap = DMScreenInfoUtil.dip2px(this.context, 10.0f);
        this.startX = (DMScreenInfoUtil.screenWidth(this.context) / 2.0f) - getResources().getDimension(R.dimen.activity_horizontal_margin);
        this.mScrollHandler = new Handler(this.context.getMainLooper());
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension((int) (((this.maxValue * this.gap) + DMScreenInfoUtil.screenWidth(this.context)) - (getResources().getDimension(R.dimen.activity_horizontal_margin) * 2.0f)), i2);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        String str = "";
        super.onDraw(canvas);
        this.paint.setColor(getResources().getColor(R.color.ruleColor));
        int i = 0;
        for (int i2 = 0; i2 <= this.maxValue; i2++) {
            if (i2 % 5 == 0) {
                this.yLenght = DMScreenInfoUtil.dip2px(this.context, this.largeHeight);
            } else {
                this.yLenght = DMScreenInfoUtil.dip2px(this.context, this.smallHeight);
            }
            float f = i2;
            float f2 = this.gap;
            float f3 = this.startX;
            float f4 = this.startY;
            canvas.drawLine((f * f2) + f3, f4, (f * f2) + f3, this.yLenght + f4, this.paint);
        }
        this.paint.setTextSize(this.mFontSize);
        this.textGap = this.gap * this.unit;
        this.paint.setColor(getResources().getColor(R.color.ruleColor));
        int i3 = -(this.maxValue / 2);
        while (true) {
            float f5 = i;
            if (f5 > this.maxValue / this.unit) {
                return;
            }
            canvas.drawText(Integer.toString(i3) + "°", (this.startX - (DMScreenInfoUtil.px2dip(this.context, calculateTextWidth(str)) / 2.0f)) + (f5 * this.textGap), this.startY + DMScreenInfoUtil.dip2px(this.context, this.largeHeight) + DMScreenInfoUtil.dip2px(this.context, 14.0f), this.paint);
            i++;
            i3 = (int) (i3 + this.unit);
        }
    }

    private float calculateTextWidth(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0.0f;
        }
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(this.mFontSize * this.metrics.scaledDensity);
        return textPaint.measureText(str);
    }

    public void setScrollerChanged(int i, int i2, int i3, int i4) {
        this.scrollWidth = i;
        this.listener.onSlide((i / this.gap) / this.unit);
    }

    public void onChangedListener(onChangedListener onchangedlistener) {
        this.listener = onchangedlistener;
    }

    public void setDefaultScaleValue(float f) {
        final int i = (int) (f * this.gap * this.unit);
        new Handler().postDelayed(new Runnable() { // from class: com.picspool.snappic.view.CSRuleView.3
            @Override // java.lang.Runnable
            public void run() {
                CSRuleView.this.horizontalScrollView.smoothScrollTo(i, 0);
            }
        }, 100L);
    }

    public Float getMaxScaleValue() {
        return Float.valueOf(90.0f);
    }

    public void setScaleScroll(float f) {
        this.horizontalScrollView.smoothScrollTo((int) ((f - 1.0f) * this.gap * this.unit), 0);
    }
}
