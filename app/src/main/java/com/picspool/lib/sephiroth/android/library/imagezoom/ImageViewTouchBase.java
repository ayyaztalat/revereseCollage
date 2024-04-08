package com.picspool.lib.sephiroth.android.library.imagezoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.picspool.lib.sephiroth.android.library.imagezoom.easing.Cubic;
import com.picspool.lib.sephiroth.android.library.imagezoom.easing.Easing;
import com.picspool.lib.sephiroth.android.library.imagezoom.graphics.FastBitmapDrawable;
import com.picspool.lib.sephiroth.android.library.imagezoom.utils.IDisposable;

/* loaded from: classes3.dex */
public class ImageViewTouchBase extends androidx.appcompat.widget.AppCompatImageView implements IDisposable {
    public static final String LOG_TAG = "image";
    protected Matrix mBaseMatrix;
    protected RectF mBitmapRect;
    protected RectF mCenterRect;
    protected final Matrix mDisplayMatrix;
    protected Easing mEasing;
    protected boolean mFitToScreen;
    protected Handler mHandler;
    boolean mIsMirror;
    private OnBitmapChangedListener mListener;
    protected final float[] mMatrixValues;
    protected float mMaxZoom;
    protected float mMinZoom;
    protected Runnable mOnLayoutRunnable;
    protected float mRotation;
    protected RectF mScrollRect;
    protected Matrix mSuppMatrix;
    protected int mThisHeight;
    protected int mThisWidth;

    /* loaded from: classes3.dex */
    public interface OnBitmapChangedListener {
        void onBitmapChanged(Drawable drawable);
    }

    @Override // android.view.View
    public float getRotation() {
        return 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onZoom(float f) {
    }

    public ImageViewTouchBase(Context context) {
        super(context);
        this.mEasing = new Cubic();
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mHandler = new Handler();
        this.mOnLayoutRunnable = null;
        this.mMaxZoom = 8.0f;
        this.mMinZoom = 1.0f;
        this.mDisplayMatrix = new Matrix();
        this.mMatrixValues = new float[9];
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mFitToScreen = false;
        this.mBitmapRect = new RectF();
        this.mCenterRect = new RectF();
        this.mScrollRect = new RectF();
        this.mRotation = 0.0f;
        this.mIsMirror = false;
        init();
    }

    public ImageViewTouchBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mEasing = new Cubic();
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mHandler = new Handler();
        this.mOnLayoutRunnable = null;
        this.mMaxZoom = 8.0f;
        this.mMinZoom = 1.0f;
        this.mDisplayMatrix = new Matrix();
        this.mMatrixValues = new float[9];
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mFitToScreen = false;
        this.mBitmapRect = new RectF();
        this.mCenterRect = new RectF();
        this.mScrollRect = new RectF();
        this.mRotation = 0.0f;
        this.mIsMirror = false;
        init();
    }

    public ImageViewTouchBase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEasing = new Cubic();
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mHandler = new Handler();
        this.mOnLayoutRunnable = null;
        this.mMaxZoom = 8.0f;
        this.mMinZoom = 1.0f;
        this.mDisplayMatrix = new Matrix();
        this.mMatrixValues = new float[9];
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mFitToScreen = false;
        this.mBitmapRect = new RectF();
        this.mCenterRect = new RectF();
        this.mScrollRect = new RectF();
        this.mRotation = 0.0f;
        this.mIsMirror = false;
        init(context, attributeSet, i);
    }

    public void setOnBitmapChangedListener(OnBitmapChangedListener onBitmapChangedListener) {
        this.mListener = onBitmapChangedListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void init() {
        try {
            if (Build.VERSION.SDK_INT > 10) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        } catch (Exception unused) {
        }
        setScaleType(ScaleType.MATRIX);
    }

    protected void init(Context context, AttributeSet attributeSet, int i) {
        setScaleType(ScaleType.MATRIX);
    }

    public void clear() {
        setImageBitmap(null, true);
    }

    public void setFitToScreen(boolean z) {
        if (z != this.mFitToScreen) {
            this.mFitToScreen = z;
            requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mThisWidth = i3 - i;
        this.mThisHeight = i4 - i2;
        Runnable runnable = this.mOnLayoutRunnable;
        if (runnable != null) {
            this.mOnLayoutRunnable = null;
            runnable.run();
        }
        if (getDrawable() != null) {
            if (this.mFitToScreen) {
                getProperBaseMatrix2(getDrawable(), this.mBaseMatrix);
            } else {
                getProperBaseMatrix(getDrawable(), this.mBaseMatrix);
            }
            setImageMatrix(getImageViewMatrix());
        }
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap, true);
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) {
        setImageDrawable(getContext().getResources().getDrawable(i));
    }

    public void setImageBitmap(Bitmap bitmap, boolean z) {
        setImageBitmap(bitmap, z, null);
    }

    public void setImageBitmap(Bitmap bitmap, boolean z, Matrix matrix) {
        setImageBitmap(bitmap, z, matrix, -1.0f);
    }

    public void setImageBitmap(Bitmap bitmap, boolean z, Matrix matrix, float f) {
        Log.i(LOG_TAG, "setImageBitmap: " + bitmap);
        if (bitmap != null) {
            setImageDrawable(new FastBitmapDrawable(bitmap), z, matrix, f);
        } else {
            setImageDrawable(null, z, matrix, f);
        }
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        setImageDrawable(drawable, true, null, -1.0f);
    }

    public void setImageDrawable(final Drawable drawable, final boolean z, final Matrix matrix, final float f) {
        if (getWidth() <= 0) {
            this.mOnLayoutRunnable = new Runnable() { // from class: com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase.1
                @Override // java.lang.Runnable
                public void run() {
                    ImageViewTouchBase.this.setImageDrawable(drawable, z, matrix, f);
                }
            };
        } else {
            _setImageDrawable(drawable, z, matrix, f);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void _setImageDrawable(Drawable drawable, boolean z, Matrix matrix, float f) {
        if (drawable != null) {
            if (z) {
                if (this.mFitToScreen) {
                    getProperBaseMatrix2(drawable, this.mBaseMatrix);
                } else {
                    getProperBaseMatrix(drawable, this.mBaseMatrix);
                }
            }
            super.setImageDrawable(drawable);
        } else {
            if (z) {
                this.mBaseMatrix.reset();
            }
            super.setImageDrawable(null);
        }
        if (z) {
            this.mSuppMatrix.reset();
            if (matrix != null) {
                this.mSuppMatrix = new Matrix(matrix);
            }
        }
        setImageMatrix(getImageViewMatrix());
        if (f < 1.0f) {
            this.mMaxZoom = maxZoom();
        } else {
            this.mMaxZoom = f;
        }
        onBitmapChanged(drawable);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onBitmapChanged(Drawable drawable) {
        OnBitmapChangedListener onBitmapChangedListener = this.mListener;
        if (onBitmapChangedListener != null) {
            onBitmapChangedListener.onBitmapChanged(drawable);
        }
    }

    protected float maxZoom() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return 1.0f;
        }
        return Math.max(drawable.getIntrinsicWidth() / this.mThisWidth, drawable.getIntrinsicHeight() / this.mThisHeight) * 4.0f;
    }

    public void setMaxZoom(float f) {
        this.mMaxZoom = f;
    }

    public float getMaxZoom() {
        return this.mMaxZoom;
    }

    public Matrix getImageViewMatrix() {
        this.mDisplayMatrix.set(this.mBaseMatrix);
        this.mDisplayMatrix.postConcat(this.mSuppMatrix);
        return this.mDisplayMatrix;
    }

    public Matrix getDisplayMatrix() {
        return new Matrix(this.mSuppMatrix);
    }

    public void resetDisplayMatrix() {
        this.mSuppMatrix.reset();
    }

    protected void getProperBaseMatrix(Drawable drawable, Matrix matrix) {
        float width = getWidth();
        float height = getHeight();
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        matrix.reset();
        if (intrinsicWidth > width || intrinsicHeight > height) {
            float min = Math.min(Math.min(width / intrinsicWidth, 2.0f), Math.min(height / intrinsicHeight, 2.0f));
            matrix.postScale(min, min);
            matrix.postTranslate((width - (intrinsicWidth * min)) / 2.0f, (height - (intrinsicHeight * min)) / 2.0f);
            return;
        }
        matrix.postTranslate((width - intrinsicWidth) / 2.0f, (height - intrinsicHeight) / 2.0f);
    }

    protected void getProperBaseMatrix2(Drawable drawable, Matrix matrix) {
        float width = getWidth();
        float height = getHeight();
        float intrinsicWidth = drawable.getIntrinsicWidth();
        float intrinsicHeight = drawable.getIntrinsicHeight();
        matrix.reset();
        if (this.mRotation == 0.0f) {
            float max = Math.max(Math.min(width / intrinsicWidth, this.mMaxZoom), Math.min(height / intrinsicHeight, this.mMaxZoom));
            matrix.postScale(max, max);
            matrix.postTranslate((width - (intrinsicWidth * max)) / 2.0f, (height - (intrinsicHeight * max)) / 2.0f);
            return;
        }
        Math.max(Math.min(width / intrinsicWidth, this.mMaxZoom), Math.min(height / intrinsicHeight, this.mMaxZoom));
        float f = this.mMinZoom;
        matrix.postScale(f, f);
        matrix.postTranslate((width - (intrinsicWidth * f)) / 2.0f, (height - (intrinsicHeight * f)) / 2.0f);
    }

    protected float getValue(Matrix matrix, int i) {
        matrix.getValues(this.mMatrixValues);
        return this.mMatrixValues[i];
    }

    protected RectF getBitmapRect() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return null;
        }
        Matrix imageViewMatrix = getImageViewMatrix();
        this.mBitmapRect.set(0.0f, 0.0f, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        imageViewMatrix.mapRect(this.mBitmapRect);
        return this.mBitmapRect;
    }

    protected float getScale(Matrix matrix) {
        return getValue(matrix, 0);
    }

    public float getScale() {
        return getScale(this.mSuppMatrix);
    }

    protected void center(boolean z, boolean z2) {
        if (getDrawable() == null) {
            return;
        }
        RectF center = getCenter(z, z2);
        if (center.left == 0.0f && center.top == 0.0f) {
            return;
        }
        postTranslate(center.left, center.top);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected RectF getCenter(boolean r7, boolean r8) {
        /*
            r6 = this;
            android.graphics.drawable.Drawable r0 = r6.getDrawable()
            r1 = 0
            if (r0 != 0) goto Ld
            android.graphics.RectF r7 = new android.graphics.RectF
            r7.<init>(r1, r1, r1, r1)
            return r7
        Ld:
            android.graphics.RectF r0 = r6.getBitmapRect()
            float r2 = r0.height()
            float r3 = r0.width()
            r4 = 1073741824(0x40000000, float:2.0)
            if (r8 == 0) goto L44
            int r8 = r6.getHeight()
            float r8 = (float) r8
            int r5 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r5 >= 0) goto L2c
            float r8 = r8 - r2
            float r8 = r8 / r4
            float r2 = r0.top
        L2a:
            float r8 = r8 - r2
            goto L45
        L2c:
            float r2 = r0.top
            int r2 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r2 <= 0) goto L36
            float r8 = r0.top
            float r8 = -r8
            goto L45
        L36:
            float r2 = r0.bottom
            int r8 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r8 >= 0) goto L44
            int r8 = r6.getHeight()
            float r8 = (float) r8
            float r2 = r0.bottom
            goto L2a
        L44:
            r8 = 0
        L45:
            if (r7 == 0) goto L6e
            int r7 = r6.getWidth()
            float r7 = (float) r7
            int r2 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r2 >= 0) goto L56
            float r7 = r7 - r3
            float r7 = r7 / r4
            float r0 = r0.left
        L54:
            float r7 = r7 - r0
            goto L6f
        L56:
            boolean r2 = r6.mIsMirror
            if (r2 == 0) goto L5b
            goto L6e
        L5b:
            float r2 = r0.left
            int r2 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r2 <= 0) goto L65
            float r7 = r0.left
            float r7 = -r7
            goto L6f
        L65:
            float r2 = r0.right
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 >= 0) goto L6e
            float r0 = r0.right
            goto L54
        L6e:
            r7 = 0
        L6f:
            android.graphics.RectF r0 = r6.mCenterRect
            r0.set(r7, r8, r1, r1)
            android.graphics.RectF r7 = r6.mCenterRect
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase.getCenter(boolean, boolean):android.graphics.RectF");
    }

    public void setIsMirror(boolean z) {
        this.mIsMirror = z;
    }

    public boolean getIsMirror() {
        return this.mIsMirror;
    }

    protected void postTranslate(float f, float f2) {
        if (this.mIsMirror) {
            this.mSuppMatrix.postTranslate(f, f2);
        } else {
            this.mSuppMatrix.postTranslate(f, f2);
        }
        setImageMatrix(getImageViewMatrix());
    }

    protected void postScale(float f, float f2, float f3) {
        this.mSuppMatrix.postScale(f, f, f2, f3);
        setImageMatrix(getImageViewMatrix());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void zoomTo(float f) {
        zoomTo(f, getWidth() / 2.0f, getHeight() / 2.0f);
    }

    public void zoomTo(float f, float f2) {
        zoomTo(f, getWidth() / 2.0f, getHeight() / 2.0f, f2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void zoomTo(float f, float f2, float f3) {
        float f4 = this.mMaxZoom;
        if (f > f4) {
            f = f4;
        }
        float f5 = this.mMinZoom;
        if (f < f5) {
            f = f5;
        }
        postScale(f / getScale(), f2, f3);
        onZoom(getScale());
        center(true, true);
    }

    public void scrollBy(float f, float f2) {
        panBy(f, f2);
    }

    protected void panBy(double d, double d2) {
        RectF bitmapRect = getBitmapRect();
        float f = (float) d2;
        this.mScrollRect.set((float) d, f, 0.0f, 0.0f);
        if (this.mIsMirror) {
            this.mScrollRect.set((float) (-d), f, 0.0f, 0.0f);
        }
        updateRect(bitmapRect, this.mScrollRect);
        postTranslate(this.mScrollRect.left, this.mScrollRect.top);
        center(true, true);
    }

    protected void updateRect(RectF rectF, RectF rectF2) {
        float f=0;
        float f2=0;
        float f3=0;
        float f4=0;
        float f5=0;
        float f6=0;
        float width = getWidth();
        float height = getHeight();
        if (rectF == null || rectF2 == null) {
            return;
        }
        if (this.mRotation == 0.0f) {
            if (this.mIsMirror) {
                if (rectF.top >= 0.0f && rectF.bottom <= height) {
                    rectF2.top = 0.0f;
                }
                if (rectF.left >= 0.0f && rectF.right <= width) {
                    rectF2.left = 0.0f;
                }
                if (rectF.top + rectF2.top >= 0.0f && rectF.bottom > height) {
                    rectF2.top = (int) (0.0f - rectF.top);
                }
                if (rectF.bottom + rectF2.top <= height - 0.0f && rectF.top < 0.0f) {
                    rectF2.top = (int) (f5 - rectF.bottom);
                }
                if (rectF.left + rectF2.left >= 0.0f) {
                    rectF2.left = (int) (0.0f - rectF.left);
                }
                if (rectF.right + rectF2.left <= width - 0.0f) {
                    rectF2.left = (int) (f6 - rectF.right);
                    return;
                }
                return;
            }
            if (rectF.top >= 0.0f && rectF.bottom <= height) {
                rectF2.top = 0.0f;
            }
            if (rectF.left >= 0.0f && rectF.right <= width) {
                rectF2.left = 0.0f;
            }
            if (rectF.top + rectF2.top >= 0.0f && rectF.bottom > height) {
                rectF2.top = (int) (0.0f - rectF.top);
            }
            if (rectF.bottom + rectF2.top <= height - 0.0f && rectF.top < 0.0f) {
                rectF2.top = (int) (f3 - rectF.bottom);
            }
            if (rectF.left + rectF2.left >= 0.0f) {
                rectF2.left = (int) (0.0f - rectF.left);
            }
            if (rectF.right + rectF2.left <= width - 0.0f) {
                rectF2.left = (int) (f4 - rectF.right);
                return;
            }
            return;
        }
        if (rectF.top >= 0.0f && rectF.bottom <= height) {
            rectF2.top = 0.0f;
        }
        if (rectF.left >= 0.0f && rectF.right <= width) {
            rectF2.left = 0.0f;
        }
        if (rectF.top + rectF2.top >= 0.0f && rectF.bottom > height) {
            rectF2.top = (int) (0.0f - rectF.top);
        }
        if (rectF.bottom + rectF2.top <= height - 0.0f && rectF.top < 0.0f) {
            rectF2.top = (int) (f - rectF.bottom);
        }
        if (rectF.left + rectF2.left >= 0.0f) {
            rectF2.left = (int) (0.0f - rectF.left);
        }
        if (rectF.right + rectF2.left <= width - 0.0f) {
            rectF2.left = (int) (f2 - rectF.right);
        }
    }

    private int getComputeValue(double d, float f) {
        double abs = Math.abs(d);
        if (f > 0.0f) {
            return (int) ((Math.cos(abs) * f) + 0.5d);
        }
        return (int) ((f / Math.cos(abs)) - 0.5d);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void scrollBy(float f, float f2, final double d) {
        final double d2 = f;
        final double d3 = f2;
        final long currentTimeMillis = System.currentTimeMillis();
        this.mHandler.post(new Runnable() { // from class: com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase.2
            double old_x = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            double old_y = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;

            @Override // java.lang.Runnable
            public void run() {
                double min = Math.min(d, System.currentTimeMillis() - currentTimeMillis);
                double easeOut = ImageViewTouchBase.this.mEasing.easeOut(min, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, d2, d);
                double easeOut2 = ImageViewTouchBase.this.mEasing.easeOut(min, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE, d3, d);
                ImageViewTouchBase.this.panBy(easeOut - this.old_x, easeOut2 - this.old_y);
                this.old_x = easeOut;
                this.old_y = easeOut2;
                if (min < d) {
                    ImageViewTouchBase.this.mHandler.post(this);
                    return;
                }
                RectF center = ImageViewTouchBase.this.getCenter(true, true);
                if (center.left == 0.0f && center.top == 0.0f) {
                    return;
                }
                ImageViewTouchBase.this.scrollBy(center.left, center.top);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void zoomTo(float f, final float f2, final float f3, final float f4) {
        final long currentTimeMillis = System.currentTimeMillis();
        final float scale = (f - getScale()) / f4;
        final float scale2 = getScale();
        this.mHandler.post(new Runnable() { // from class: com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase.3
            @Override // java.lang.Runnable
            public void run() {
                float min = Math.min(f4, (float) (System.currentTimeMillis() - currentTimeMillis));
                ImageViewTouchBase.this.zoomTo(scale2 + (scale * min), f2, f3);
                if (min < f4) {
                    ImageViewTouchBase.this.mHandler.post(this);
                }
            }
        });
    }

    public Bitmap getImageBitmap() {
        FastBitmapDrawable fastBitmapDrawable = (FastBitmapDrawable) getDrawable();
        if (fastBitmapDrawable == null) {
            return null;
        }
        return fastBitmapDrawable.getBitmap();
    }

    public void setImageBitmapWithStatKeep(Bitmap bitmap) {
        if (bitmap == null) {
            super.setImageDrawable(null);
            return;
        }
        super.setImageDrawable(new FastBitmapDrawable(bitmap));
        super.setImageMatrix(getImageMatrix());
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.utils.IDisposable
    public void dispose() {
        clear();
    }

    public void ResetImageView() {
        this.mEasing = new Cubic();
        this.mBaseMatrix = new Matrix();
        this.mSuppMatrix = new Matrix();
        this.mHandler = new Handler();
        this.mOnLayoutRunnable = null;
        this.mMaxZoom = 0.0f;
        this.mThisWidth = -1;
        this.mThisHeight = -1;
        this.mFitToScreen = false;
        this.mBitmapRect = new RectF();
        this.mCenterRect = new RectF();
        this.mScrollRect = new RectF();
        this.mListener = null;
    }

    public void postRotation(float f) {
        this.mSuppMatrix.postRotate(f, getWidth() / 2, getHeight() / 2);
        setImageMatrix(getImageViewMatrix());
    }

    public void setMinZoom(float f) {
        this.mMinZoom = f;
    }

    public float getMinZoom() {
        return this.mMinZoom;
    }

    public void Reversal(float f) {
        this.mSuppMatrix.postScale(1.0f, -1.0f, getWidth() / 2, getHeight() / 2);
        this.mSuppMatrix.postRotate(f, getWidth() / 2, getHeight() / 2);
        setImageMatrix(getImageViewMatrix());
    }
}
