package com.itcm.sephiroth.android.library.p010cm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.itcm.sephiroth.android.library.p010cm.easing.Cubic;
import com.itcm.sephiroth.android.library.p010cm.easing.Easing;
import com.itcm.sephiroth.android.library.p010cm.graphics.CSFastBitmapDrawable;
import com.itcm.sephiroth.android.library.p010cm.utils.CSIDisposable;

/* renamed from: itcm.sephiroth.android.library.cm.CSImageViewTouchBase */
/* loaded from: classes3.dex */
public abstract class CSImageViewTouchBase extends androidx.appcompat.widget.AppCompatImageView implements CSIDisposable {
    protected static final int DRAG = 1;
    protected static final int JUMP = 2;
    protected static final boolean LOG_ENABLED = false;
    public static final String LOG_TAG = "CSImageViewTouchBase";
    protected static final int NONE = 0;
    protected static final int ZOOM = 3;
    public static final float ZOOM_INVALID = -1.0f;
    protected final int DEFAULT_ANIMATION_DURATION;
    protected Matrix mBaseMatrix;
    private boolean mBitmapChanged;
    protected RectF mBitmapRect;
    private PointF mCenter;
    protected RectF mCenterRect;
    protected final Matrix mDisplayMatrix;
    private OnDrawableChangeListener mDrawableChangeListener;
    protected Easing mEasing;
    private float mEldScale;
    protected Handler mHandler;
    protected Runnable mLayoutRunnable;
    protected final float[] mMatrixValues;
    private float mMaxZoom;
    private boolean mMaxZoomDefined;
    protected PointF mMid;
    private float mMinZoom;
    private boolean mMinZoomDefined;
    protected Matrix mNextMatrix;
    private OnLayoutChangeListener mOnLayoutChangeListener;
    protected DisplayType mScaleType;
    private boolean mScaleTypeChanged;
    protected RectF mScrollRect;
    protected PointF mStart;
    protected Matrix mSuppMatrix;
    protected int mThisHeight;
    protected int mThisWidth;
    protected boolean mUserScaled;
    protected int mode;
    protected float oldDegree;
    protected float oldDist;
    PaintFlagsDrawFilter paintFlagsDrawFilter;

    /* renamed from: itcm.sephiroth.android.library.cm.CSImageViewTouchBase$DisplayType */
    /* loaded from: classes3.dex */
    public enum DisplayType {
        NONE,
        FIT_TO_SCREEN,
        FIT_IF_BIGGER,
        FILL_TO_SCREEN
    }

    /* renamed from: itcm.sephiroth.android.library.cm.CSImageViewTouchBase$OnDrawableChangeListener */
    /* loaded from: classes3.dex */
    public interface OnDrawableChangeListener {
        void onDrawableChanged(Drawable drawable);
    }

    /* renamed from: itcm.sephiroth.android.library.cm.CSImageViewTouchBase$OnLayoutChangeListener */
    /* loaded from: classes3.dex */
    public interface OnLayoutChangeListener {
        void onLayoutChanged(boolean z, int i, int i2, int i3, int i4);
    }

    @Override // android.view.View
    public float getRotation() {
        return 0.0f;
    }

    protected void onImageMatrixChanged() {
    }

    protected void onZoom(float f) {
    }

    protected void onZoomAnimationCompleted(float f) {
    }

    public CSImageViewTouchBase(Context context) {
        this(context, null);
    }

    public CSImageViewTouchBase(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CSImageViewTouchBase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mStart = new PointF();
        this.mMid = new PointF();
        this.mode = 0;
        this.mEasing = new Cubic();
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mHandler = new Handler();
        this.mLayoutRunnable = null;
        this.mUserScaled = false;
        this.mMaxZoom = -1.0f;
        this.mMinZoom = -1.0f;
        this.mDisplayMatrix = new Matrix();
        this.mMatrixValues = new float[9];
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mCenter = new PointF();
        this.mScaleType = DisplayType.NONE;
        this.DEFAULT_ANIMATION_DURATION = 200;
        this.mBitmapRect = new RectF();
        this.mCenterRect = new RectF();
        this.mScrollRect = new RectF();
        this.paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        init(context, attributeSet, i);
    }

    public void setOnDrawableChangedListener(OnDrawableChangeListener onDrawableChangeListener) {
        this.mDrawableChangeListener = onDrawableChangeListener;
    }

    public void setOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
        this.mOnLayoutChangeListener = onLayoutChangeListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void init(Context context, AttributeSet attributeSet, int i) {
        setScaleType(ScaleType.MATRIX);
    }

    @Override // android.widget.ImageView
    public void setScaleType(ScaleType scaleType) {
        if (scaleType == ScaleType.MATRIX) {
            super.setScaleType(scaleType);
        } else {
            Log.w(LOG_TAG, "Unsupported scaletype. Only MATRIX can be used");
        }
    }

    public void resetDisplayMatrix() {
        this.mSuppMatrix.reset();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        canvas.setDrawFilter(this.paintFlagsDrawFilter);
        super.onDraw(canvas);
    }

    public void clear() {
        setImageBitmap(null);
    }

    public void setDisplayType(DisplayType displayType) {
        if (displayType != this.mScaleType) {
            this.mUserScaled = false;
            this.mScaleType = displayType;
            this.mScaleTypeChanged = true;
            requestLayout();
        }
    }

    public DisplayType getDisplayType() {
        return this.mScaleType;
    }

    protected void setMinScale(float f) {
        this.mMinZoom = f;
    }

    protected void setMaxScale(float f) {
        this.mMaxZoom = f;
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        float defaultScale;
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            int i7 = this.mThisWidth;
            int i8 = this.mThisHeight;
            int i9 = i3 - i;
            this.mThisWidth = i9;
            int i10 = i4 - i2;
            this.mThisHeight = i10;
            i5 = i9 - i7;
            i6 = i10 - i8;
            this.mCenter.x = i9 / 2.0f;
            this.mCenter.y = this.mThisHeight / 2.0f;
        } else {
            i5 = 0;
            i6 = 0;
        }
        Runnable runnable = this.mLayoutRunnable;
        if (runnable != null) {
            this.mLayoutRunnable = null;
            runnable.run();
        }
        Drawable drawable = getDrawable();
        if (drawable != null) {
            if (z || this.mScaleTypeChanged || this.mBitmapChanged) {
                getDefaultScale(this.mScaleType);
                float scale = getScale(this.mBaseMatrix);
                float scale2 = getScale();
                float min = Math.min(1.0f, 1.0f / scale);
                getProperBaseMatrix(drawable, this.mBaseMatrix);
                float scale3 = getScale(this.mBaseMatrix);
                float r12 = 0;
                if (this.mBitmapChanged || this.mScaleTypeChanged) {
                    Matrix matrix = this.mNextMatrix;
                    if (matrix != null) {
                        this.mSuppMatrix.set(matrix);
                        this.mNextMatrix = null;
                        defaultScale = getScale();
                    } else {
                        this.mSuppMatrix.reset();
                        defaultScale = getDefaultScale(this.mScaleType);
                    }
                    r12 = defaultScale;
                    setImageMatrix(getImageViewMatrix());
                    if (r12 != getScale()) {
                        zoomTo(r12);
                    }
                } else if (z) {
                    if (!this.mMinZoomDefined) {
                        this.mMinZoom = -1.0f;
                    }
                    if (!this.mMaxZoomDefined) {
                        this.mMaxZoom = -1.0f;
                    }
                    setImageMatrix(getImageViewMatrix());
                    postTranslate(-i5, -i6);
                    if (!this.mUserScaled) {
                        r12 = getDefaultScale(this.mScaleType);
                        zoomTo(r12);
                    } else {
                        r12 = ((double) Math.abs(scale2 - min)) > 0.001d ? (scale / scale3) * scale2 : 1.0f;
                        zoomTo(r12);
                    }
                }
                this.mUserScaled = false;
                if (r12 > getMaxScale() || r12 < getMinScale()) {
                    zoomTo(r12);
                }
                center(true, true);
                if (this.mBitmapChanged) {
                    onDrawableChanged(drawable);
                }
                if (z || this.mBitmapChanged || this.mScaleTypeChanged) {
                    onLayoutChanged(i, i2, i3, i4);
                }
                if (this.mScaleTypeChanged) {
                    this.mScaleTypeChanged = false;
                }
                if (this.mBitmapChanged) {
                    this.mBitmapChanged = false;
                    return;
                }
                return;
            }
            return;
        }
        if (this.mBitmapChanged) {
            onDrawableChanged(drawable);
        }
        if (z || this.mBitmapChanged || this.mScaleTypeChanged) {
            onLayoutChanged(i, i2, i3, i4);
        }
        if (this.mBitmapChanged) {
            this.mBitmapChanged = false;
        }
        if (this.mScaleTypeChanged) {
            this.mScaleTypeChanged = false;
        }
    }

    public void resetDisplay() {
        this.mBitmapChanged = true;
        requestLayout();
    }

    public void resetMatrix() {
        this.mSuppMatrix = new Matrix();
        float defaultScale = getDefaultScale(this.mScaleType);
        setImageMatrix(getImageViewMatrix());
        if (defaultScale != getScale()) {
            zoomTo(defaultScale);
        }
        postInvalidate();
    }

    protected float getDefaultScale(DisplayType displayType) {
        if (displayType == DisplayType.FIT_TO_SCREEN) {
            return 1.0f;
        }
        if (displayType == DisplayType.FIT_IF_BIGGER) {
            return Math.min(1.0f, 1.0f / getScale(this.mBaseMatrix));
        }
        if (displayType == DisplayType.FILL_TO_SCREEN) {
            float intrinsicWidth = getDrawable().getIntrinsicWidth() / getDrawable().getIntrinsicHeight();
            return intrinsicWidth < 1.0f ? 1.0f / intrinsicWidth : intrinsicWidth;
        }
        return 1.0f / getScale(this.mBaseMatrix);
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) {
        setImageDrawable(getContext().getResources().getDrawable(i));
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap, null, -1.0f, -1.0f);
    }

    public void setImageBitmapWithStatKeep(Bitmap bitmap) {
        if (bitmap == null) {
            super.setImageDrawable(null);
        } else {
            super.setImageDrawable(new CSFastBitmapDrawable(bitmap));
        }
    }

    public void setImageBitmap(Bitmap bitmap, Matrix matrix, float f, float f2) {
        if (bitmap != null) {
            setImageDrawable(new CSFastBitmapDrawable(bitmap), matrix, f, f2);
        } else {
            setImageDrawable(null, matrix, f, f2);
        }
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        setImageDrawable(drawable, null, -1.0f, -1.0f);
    }

    public void setImageDrawable(final Drawable drawable, final Matrix matrix, final float f, final float f2) {
        if (getWidth() <= 0) {
            this.mLayoutRunnable = new Runnable() { // from class: itcm.sephiroth.android.library.cm.CSImageViewTouchBase.1
                @Override // java.lang.Runnable
                public void run() {
                    CSImageViewTouchBase.this.setImageDrawable(drawable, matrix, f, f2);
                }
            };
        } else {
            _setImageDrawable(drawable, matrix, f, f2);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void _setImageDrawable(Drawable drawable, Matrix matrix, float f, float f2) {
        if (drawable != null) {
            super.setImageDrawable(drawable);
        } else {
            this.mBaseMatrix.reset();
            super.setImageDrawable(null);
        }
        if (matrix != null) {
            this.mNextMatrix = new Matrix(matrix);
        }
        this.mBitmapChanged = true;
        requestLayout();
    }

    protected void onDrawableChanged(Drawable drawable) {
        fireOnDrawableChangeListener(drawable);
    }

    protected void fireOnLayoutChangeListener(int i, int i2, int i3, int i4) {
        OnLayoutChangeListener onLayoutChangeListener = this.mOnLayoutChangeListener;
        if (onLayoutChangeListener != null) {
            onLayoutChangeListener.onLayoutChanged(true, i, i2, i3, i4);
        }
    }

    protected void fireOnDrawableChangeListener(Drawable drawable) {
        OnDrawableChangeListener onDrawableChangeListener = this.mDrawableChangeListener;
        if (onDrawableChangeListener != null) {
            onDrawableChangeListener.onDrawableChanged(drawable);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLayoutChanged(int i, int i2, int i3, int i4) {
        fireOnLayoutChangeListener(i, i2, i3, i4);
    }

    protected float computeMaxZoom() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return 1.0f;
        }
        return Math.max(drawable.getIntrinsicWidth() / this.mThisWidth, drawable.getIntrinsicHeight() / this.mThisHeight) * 8.0f;
    }

    protected float computeMinZoom() {
        if (getDrawable() == null) {
            return 1.0f;
        }
        return Math.min(1.0f, 1.0f / getScale(this.mBaseMatrix));
    }

    public float getMaxScale() {
        float computeMaxZoom = computeMaxZoom();
        this.mMaxZoom = computeMaxZoom;
        return computeMaxZoom;
    }

    public float getMinScale() {
        if (this.mMinZoom == -1.0f) {
            this.mMinZoom = computeMinZoom();
        }
        return this.mMinZoom;
    }

    public Matrix getImageViewMatrix() {
        return getImageViewMatrix(this.mSuppMatrix);
    }

    public Matrix getImageViewMatrix(Matrix matrix) {
        this.mDisplayMatrix.set(this.mBaseMatrix);
        this.mDisplayMatrix.postConcat(matrix);
        return this.mDisplayMatrix;
    }

    @Override // android.widget.ImageView
    public void setImageMatrix(Matrix matrix) {
        Matrix imageMatrix = getImageMatrix();
        boolean z = (matrix == null && !imageMatrix.isIdentity()) || !(matrix == null || imageMatrix.equals(matrix));
        super.setImageMatrix(matrix);
        if (z) {
            onImageMatrixChanged();
        }
    }

    public Matrix getDisplayMatrix() {
        return new Matrix(this.mSuppMatrix);
    }

    protected void getProperBaseMatrix(Drawable drawable, Matrix matrix) {
        float f = this.mThisWidth;
        float f2 = this.mThisHeight;
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        matrix.reset();
        if (intrinsicWidth > f || intrinsicHeight > f2) {
            float min = Math.min(f / intrinsicWidth, f2 / intrinsicHeight);
            matrix.postScale(min, min);
            matrix.postTranslate((f - (intrinsicWidth * min)) / 2.0f, (f2 - (intrinsicHeight * min)) / 2.0f);
            return;
        }
        float min2 = Math.min(f / intrinsicWidth, f2 / intrinsicHeight);
        matrix.postScale(min2, min2);
        matrix.postTranslate((f - (intrinsicWidth * min2)) / 2.0f, (f2 - (intrinsicHeight * min2)) / 2.0f);
    }

    protected void getProperBaseMatrix2(Drawable drawable, Matrix matrix) {
        float f = this.mThisWidth;
        float f2 = this.mThisHeight;
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        matrix.reset();
        float min = Math.min(f / intrinsicWidth, f2 / intrinsicHeight);
        matrix.postScale(min, min);
        matrix.postTranslate((f - (intrinsicWidth * min)) / 2.0f, (f2 - (intrinsicHeight * min)) / 2.0f);
    }

    protected float getValue(Matrix matrix, int i) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[i];
    }

    public void printMatrix(Matrix matrix) {
        float value = getValue(matrix, 0);
        float value2 = getValue(matrix, 4);
        float value3 = getValue(matrix, 2);
        float value4 = getValue(matrix, 5);
        Log.d(LOG_TAG, "matrix: { x: " + value3 + ", y: " + value4 + ", scalex: " + value + ", scaley: " + value2 + " }");
    }

    public RectF getBitmapRect() {
        return getBitmapRect(this.mSuppMatrix);
    }

    protected RectF getBitmapRect(Matrix matrix) {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }
        Matrix imageViewMatrix = getImageViewMatrix(matrix);
        this.mBitmapRect.set(0.0f, 0.0f, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        imageViewMatrix.mapRect(this.mBitmapRect);
        return this.mBitmapRect;
    }

    protected float getScale(Matrix matrix) {
        return getValue(matrix, 0);
    }

    public float getScale() {
        float scale = getScale(this.mSuppMatrix);
        if (scale != 0.0f) {
            this.mEldScale = scale;
        }
        return this.mEldScale;
    }

    public float getBaseScale() {
        return getScale(this.mBaseMatrix);
    }

    protected void center(boolean z, boolean z2) {
        if (getDrawable() == null) {
            return;
        }
        RectF center = getCenter(this.mSuppMatrix, z, z2);
        if (center.left == 0.0f && center.top == 0.0f) {
            return;
        }
        postTranslate(center.left, center.top);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected RectF getCenter(Matrix r6, boolean r7, boolean r8) {
        /*
            r5 = this;
            android.graphics.drawable.Drawable r0 = r5.getDrawable()
            r1 = 0
            if (r0 != 0) goto Ld
            android.graphics.RectF r6 = new android.graphics.RectF
            r6.<init>(r1, r1, r1, r1)
            return r6
        Ld:
            android.graphics.RectF r0 = r5.mCenterRect
            r0.set(r1, r1, r1, r1)
            android.graphics.RectF r6 = r5.getBitmapRect(r6)
            float r0 = r6.height()
            float r2 = r6.width()
            r3 = 1073741824(0x40000000, float:2.0)
            if (r8 == 0) goto L45
            int r8 = r5.mThisHeight
            float r8 = (float) r8
            int r4 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r4 >= 0) goto L2f
            float r8 = r8 - r0
            float r8 = r8 / r3
            float r0 = r6.top
        L2d:
            float r8 = r8 - r0
            goto L46
        L2f:
            float r0 = r6.top
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L39
            float r8 = r6.top
            float r8 = -r8
            goto L46
        L39:
            float r0 = r6.bottom
            int r8 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r8 >= 0) goto L45
            int r8 = r5.mThisHeight
            float r8 = (float) r8
            float r0 = r6.bottom
            goto L2d
        L45:
            r8 = 0
        L46:
            if (r7 == 0) goto L68
            int r7 = r5.mThisWidth
            float r7 = (float) r7
            int r0 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r0 >= 0) goto L55
            float r7 = r7 - r2
            float r7 = r7 / r3
            float r6 = r6.left
        L53:
            float r7 = r7 - r6
            goto L69
        L55:
            float r0 = r6.left
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L5f
            float r6 = r6.left
            float r7 = -r6
            goto L69
        L5f:
            float r0 = r6.right
            int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r0 >= 0) goto L68
            float r6 = r6.right
            goto L53
        L68:
            r7 = 0
        L69:
            android.graphics.RectF r6 = r5.mCenterRect
            r6.set(r7, r8, r1, r1)
            android.graphics.RectF r6 = r5.mCenterRect
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: itcm.sephiroth.android.library.p010cm.CSImageViewTouchBase.getCenter(android.graphics.Matrix, boolean, boolean):android.graphics.RectF");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void postTranslate(float f, float f2) {
        if (f == 0.0f && f2 == 0.0f) {
            return;
        }
        this.mSuppMatrix.postTranslate(f, f2);
        setImageMatrix(getImageViewMatrix());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void postScale(float f, float f2, float f3) {
        this.mSuppMatrix.postScale(f, f, f2, f3);
        setImageMatrix(getImageViewMatrix());
    }

    public void postScale(float f) {
        this.mSuppMatrix.postScale(f, f, getWidth() / 2, getHeight() / 2);
        setImageMatrix(getImageViewMatrix());
    }

    protected void postRotation(float f, float f2, float f3) {
        this.mSuppMatrix.postRotate(f, f2, f3);
        setImageMatrix(getImageViewMatrix());
    }

    public void postRotation(float f) {
        this.mEldScale = getScale();
        this.mSuppMatrix.postRotate(f, getWidth() / 2, getHeight() / 2);
        setImageMatrix(getImageViewMatrix());
    }

    public void postRoundRadius(float f, float f2) {
        ((CSFastBitmapDrawable) getDrawable()).setRoundRadius(f, f2);
        invalidate();
    }

    public void Reversal(float f) {
        this.mEldScale = getScale();
        this.mSuppMatrix.postScale(1.0f, -1.0f, getWidth() / 2, getHeight() / 2);
        this.mSuppMatrix.postRotate(f, getWidth() / 2, getHeight() / 2);
        setImageMatrix(getImageViewMatrix());
    }

    public Bitmap getImageBitmap() {
        CSFastBitmapDrawable cSFastBitmapDrawable = (CSFastBitmapDrawable) getDrawable();
        if (cSFastBitmapDrawable == null) {
            return null;
        }
        return cSFastBitmapDrawable.getBitmap();
    }

    protected PointF getCenter() {
        return this.mCenter;
    }

    public void zoomTo(float f) {
        PointF center = getCenter();
        zoomTo(f, center.x, center.y);
    }

    public void zoomTo(float f, float f2) {
        PointF center = getCenter();
        zoomTo(f, center.x, center.y, f2);
    }

    protected void zoomTo(float f, float f2, float f3) {
        postScale(f / getScale(), f2, f3);
        onZoom(getScale());
        center(true, true);
    }

    public void scrollBy(float f, float f2) {
        panBy(f, f2);
    }

    protected void panBy(double d, double d2) {
        RectF bitmapRect = getBitmapRect();
        this.mScrollRect.set((float) d, (float) d2, 0.0f, 0.0f);
        updateRect(bitmapRect, this.mScrollRect);
        postTranslate(this.mScrollRect.left, this.mScrollRect.top);
        center(true, true);
    }

    protected void updateRect(RectF rectF, RectF rectF2) {
        if (rectF == null) {
            return;
        }
        if (rectF.top >= 0.0f && rectF.bottom <= this.mThisHeight) {
            rectF2.top = 0.0f;
        }
        if (rectF.left >= 0.0f && rectF.right <= this.mThisWidth) {
            rectF2.left = 0.0f;
        }
        if (rectF.top + rectF2.top >= 0.0f && rectF.bottom > this.mThisHeight) {
            rectF2.top = (int) (0.0f - rectF.top);
        }
        if (rectF.bottom + rectF2.top <= this.mThisHeight + 0 && rectF.top < 0.0f) {
            rectF2.top = (int) ((this.mThisHeight + 0) - rectF.bottom);
        }
        if (rectF.left + rectF2.left >= 0.0f) {
            rectF2.left = (int) (0.0f - rectF.left);
        }
        float f = rectF.right + rectF2.left;
        int i = this.mThisWidth;
        if (f <= i + 0) {
            rectF2.left = (int) ((i + 0) - rectF.right);
        }
    }

    protected void scrollBy(float f, float f2, final double d) {
        final double d2 = f;
        final double d3 = f2;
        final long currentTimeMillis = System.currentTimeMillis();
        this.mHandler.post(new Runnable() { // from class: itcm.sephiroth.android.library.cm.CSImageViewTouchBase.2
            double old_x = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            double old_y = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;

            @Override // java.lang.Runnable
            public void run() {
                double min = Math.min(d, System.currentTimeMillis() - currentTimeMillis);
                double easeOut = CSImageViewTouchBase.this.mEasing.easeOut(min, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, d2, d);
                double easeOut2 = CSImageViewTouchBase.this.mEasing.easeOut(min, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, d3, d);
                CSImageViewTouchBase.this.panBy(easeOut - this.old_x, easeOut2 - this.old_y);
                this.old_x = easeOut;
                this.old_y = easeOut2;
                if (min < d) {
                    CSImageViewTouchBase.this.mHandler.post(this);
                    return;
                }
                CSImageViewTouchBase cSImageViewTouchBase = CSImageViewTouchBase.this;
                RectF center = cSImageViewTouchBase.getCenter(cSImageViewTouchBase.mSuppMatrix, true, true);
                if (center.left == 0.0f && center.top == 0.0f) {
                    return;
                }
                CSImageViewTouchBase.this.scrollBy(center.left, center.top);
            }
        });
    }

    protected void zoomTo(float f, float f2, float f3, final float f4) {
        if (f > getMaxScale()) {
            f = getMaxScale();
        }
        final long currentTimeMillis = System.currentTimeMillis();
        final float scale = getScale();
        final float f5 = f - scale;
        Matrix matrix = new Matrix(this.mSuppMatrix);
        matrix.postScale(f, f, f2, f3);
        RectF center = getCenter(matrix, true, true);
        final float f6 = f2 + (center.left * f);
        final float f7 = f3 + (center.top * f);
        this.mHandler.post(new Runnable() { // from class: itcm.sephiroth.android.library.cm.CSImageViewTouchBase.3
            @Override // java.lang.Runnable
            public void run() {
                float min = Math.min(f4, (float) (System.currentTimeMillis() - currentTimeMillis));
                CSImageViewTouchBase.this.zoomTo(scale + ((float) CSImageViewTouchBase.this.mEasing.easeInOut(min, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, f5, f4)), f6, f7);
                if (min < f4) {
                    CSImageViewTouchBase.this.mHandler.post(this);
                    return;
                }
                CSImageViewTouchBase cSImageViewTouchBase = CSImageViewTouchBase.this;
                cSImageViewTouchBase.onZoomAnimationCompleted(cSImageViewTouchBase.getScale());
                CSImageViewTouchBase.this.center(true, true);
            }
        });
    }

    @Override // itcm.sephiroth.android.library.p010cm.utils.CSIDisposable
    public void dispose() {
        clear();
    }
}
