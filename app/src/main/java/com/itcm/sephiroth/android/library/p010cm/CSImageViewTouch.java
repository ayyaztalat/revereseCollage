package com.itcm.sephiroth.android.library.p010cm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.picspool.lib.sysutillib.DMScreenInfoUtil;


/* renamed from: itcm.sephiroth.android.library.cm.CSImageViewTouch */
/* loaded from: classes3.dex */
public class CSImageViewTouch extends CSImageViewTouchBase {
    private static final String TAG = "CSImageViewTouch";
    private RectF initRect;
    protected boolean lockTouch;
    public OnCustomeClickListener mClickListener;
    protected int mDoubleTapDirection;
    protected boolean mDoubleTapEnabled;
    private boolean mRotationEnabled;
    private float mScale;
    protected boolean mScaleEnabled;
    protected float mScaleFactor;
    protected int mTouchSlop;
    private int radius;

    /* renamed from: itcm.sephiroth.android.library.cm.CSImageViewTouch$OnCustomeClickListener */
    /* loaded from: classes3.dex */
    public interface OnCustomeClickListener {
        void CustomeClick(int i);
    }

    public CSImageViewTouch(Context context) {
        super(context);
        this.mDoubleTapEnabled = true;
        this.mScaleEnabled = true;
        this.mRotationEnabled = true;
        this.radius = 30;
        this.mScale = 1.0f;
        this.lockTouch = false;
    }

    public CSImageViewTouch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CSImageViewTouch(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDoubleTapEnabled = true;
        this.mScaleEnabled = true;
        this.mRotationEnabled = true;
        this.radius = 30;
        this.mScale = 1.0f;
        this.lockTouch = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase
    public void init(Context context, AttributeSet attributeSet, int i) {
        super.init(context, attributeSet, i);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mDoubleTapDirection = 1;
    }

    public void setScaleEnabled(boolean z) {
        this.mScaleEnabled = z;
    }

    public boolean GetScaleEnable() {
        return this.mScaleEnabled;
    }

    public int getRadius() {
        return this.radius;
    }

    public void changeRotation(float f) {
        postRotation(f);
        invalidate();
    }

    public void changeScale(float f) {
        postScale(f);
        invalidate();
    }

    public boolean getDoubleTapEnabled() {
        return this.mDoubleTapEnabled;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase
    public void _setImageDrawable(Drawable drawable, Matrix matrix, float f, float f2) {
        super._setImageDrawable(drawable, matrix, f, f2);
        this.mScaleFactor = getMaxScale() / 3.0f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase
    public void onLayoutChanged(int i, int i2, int i3, int i4) {
        super.onLayoutChanged(i, i2, i3, i4);
        RectF rectF = this.initRect;
        if (rectF == null || rectF.isEmpty()) {
            this.initRect = new RectF(getBitmapRect());
        }
    }

    @Override // itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
    }

    public void setLockTouch(boolean z) {
        this.lockTouch = z;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.lockTouch) {
            return false;
        }
        try {
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                this.mode = 1;
                this.mStart.set(motionEvent.getX(), motionEvent.getY());
            } else if (action != 1) {
                if (action == 2) {
                    if (this.mode == 1) {
                        float x = motionEvent.getX() - this.mStart.x;
                        float y = motionEvent.getY() - this.mStart.y;
                        RectF bitmapRect = getBitmapRect();
                        float screenWidth = DMScreenInfoUtil.screenWidth(getContext());
                        float screenHeight = DMScreenInfoUtil.screenHeight(getContext()) - DMScreenInfoUtil.dip2px(getContext(), 70.0f);
                        if (bitmapRect.left + x <= 0.0f && bitmapRect.right + x >= screenWidth && bitmapRect.top + y <= 0.0f && bitmapRect.bottom + y >= screenHeight) {
                            postTranslate(x, y);
                            this.mStart.set(motionEvent.getX(), motionEvent.getY());
                        }
                    }
                    if (this.mode == 2) {
                        this.mode = 1;
                        this.mStart.set(motionEvent.getX(), motionEvent.getY());
                    }
                    if (this.mode == 3) {
                        if (this.mScaleEnabled) {
                            float spacing = spacing(motionEvent);
                            if (this.oldDist != 0.0f) {
                                float f = spacing / this.oldDist;
                                if (f >= 0.5f) {
                                    if (this.initRect != null && (this.initRect.width() / (getBitmapRect().width() * f) > 1.0f || this.initRect.width() / (getBitmapRect().width() * f) < 0.25f)) {
                                        f = 1.0f;
                                    }
                                    postScale(f, (motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
                                }
                            }
                            this.oldDist = spacing;
                        }
                    }
                } else if (action != 5) {
                    if (action == 6) {
                        this.mode = 2;
                    }
                }
                if (motionEvent.getActionIndex() >= 1) {
                    this.oldDist = spacing(motionEvent);
                    this.oldDegree = rotation(motionEvent);
                    this.mode = 3;
                    midPoint(this.mMid, motionEvent);
                }
            } else {
                RectF bitmapRect2 = getBitmapRect();
                if (bitmapRect2.left > this.initRect.left || bitmapRect2.top > this.initRect.top || bitmapRect2.right < this.initRect.right || bitmapRect2.bottom < this.initRect.bottom) {
                    resetMatrix();
                }
            }
        } catch (Exception e) {
            System.out.println("error:" + e.getMessage());
        }
        return true;
    }

    public void setTop() {
        postTranslate(0.0f, -getBitmapRect().top);
    }

    public void setBottom() {
        postTranslate(0.0f, getHeight() - getBitmapRect().bottom);
    }

    public void setLeft() {
        postTranslate(-getBitmapRect().left, 0.0f);
    }

    public void setRight() {
        postTranslate(getWidth() - getBitmapRect().right, 0.0f);
    }

    public void setCenter() {
        RectF bitmapRect = getBitmapRect();
        postTranslate(((-bitmapRect.left) + (getWidth() - bitmapRect.right)) / 2.0f, ((-bitmapRect.top) + (getHeight() - bitmapRect.bottom)) / 2.0f);
    }

    private float spacing(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    private void midPoint(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    private float rotation(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2(motionEvent.getY(0) - motionEvent.getY(1), motionEvent.getX(0) - motionEvent.getX(1)));
    }

    public void SetRotationEnable(boolean z) {
        this.mRotationEnabled = z;
    }

    public boolean GetRotationEnable() {
        return this.mRotationEnabled;
    }

    public void Zoom(float f) {
        postScale(f);
    }
}
