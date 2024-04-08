package com.picspool.lib.sephiroth.android.library.imagezoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewConfiguration;
import com.picspool.lib.sephiroth.android.library.imagezoom.graphics.FastBitmapDrawable;

/* loaded from: classes3.dex */
public class ImageViewTouch extends ImageViewTouchBase {
    private OnImageViewTouchDoubleTapListener doubleTapListener;
    protected float mCurrentScaleFactor;
    protected int mDoubleTapDirection;
    protected boolean mDoubleTapToZoomEnabled;
    protected GestureDetector mGestureDetector;
    protected GestureDetector.OnGestureListener mGestureListener;
    public boolean mIsReturn;
    protected boolean mRotationEnabled;
    private float mScale;
    protected ScaleGestureDetector mScaleDetector;
    protected boolean mScaleEnabled;
    protected float mScaleFactor;
    protected ScaleGestureDetector.OnScaleGestureListener mScaleListener;
    protected boolean mScrollEnabled;
    protected int mTouchSlop;

    /* loaded from: classes3.dex */
    public interface OnImageViewTouchDoubleTapListener {
        void onDoubleTap();
    }

    public ImageViewTouch(Context context) {
        super(context);
        this.mDoubleTapToZoomEnabled = true;
        this.mScaleEnabled = true;
        this.mScrollEnabled = true;
        this.mRotationEnabled = true;
        this.mIsReturn = true;
        this.mScale = 1.0f;
    }

    public ImageViewTouch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDoubleTapToZoomEnabled = true;
        this.mScaleEnabled = true;
        this.mScrollEnabled = true;
        this.mRotationEnabled = true;
        this.mIsReturn = true;
        this.mScale = 1.0f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void init() {
        super.init();
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mGestureListener = getGestureListener();
        this.mScaleListener = getScaleListener();
        this.mScaleDetector = new ScaleGestureDetector(getContext(), this.mScaleListener);
        this.mGestureDetector = new GestureDetector(getContext(), this.mGestureListener, null, true);
        this.mCurrentScaleFactor = 1.0f;
        this.mDoubleTapDirection = 1;
    }

    public void setDoubleTapListener(OnImageViewTouchDoubleTapListener onImageViewTouchDoubleTapListener) {
        this.doubleTapListener = onImageViewTouchDoubleTapListener;
    }

    public void setDoubleTapToZoomEnabled(boolean z) {
        this.mDoubleTapToZoomEnabled = z;
    }

    public void setScaleEnabled(boolean z) {
        this.mScaleEnabled = z;
    }

    public void setScrollEnabled(boolean z) {
        this.mScrollEnabled = z;
    }

    public boolean getDoubleTapEnabled() {
        return this.mDoubleTapToZoomEnabled;
    }

    public Bitmap getDispalyImage(int i, int i2) {
        float width = i / getWidth();
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Matrix imageMatrix = getImageMatrix();
        imageMatrix.postScale(width, width);
        Bitmap bitmap = ((FastBitmapDrawable) getDrawable()).getBitmap();
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, imageMatrix, paint);
        return createBitmap;
    }

    protected GestureDetector.OnGestureListener getGestureListener() {
        return new GestureListener();
    }

    protected ScaleGestureDetector.OnScaleGestureListener getScaleListener() {
        return new ScaleListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void onBitmapChanged(Drawable drawable) {
        super.onBitmapChanged(drawable);
        float[] fArr = new float[9];
        this.mSuppMatrix.getValues(fArr);
        this.mCurrentScaleFactor = fArr[0];
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void _setImageDrawable(Drawable drawable, boolean z, Matrix matrix, float f) {
        super._setImageDrawable(drawable, z, matrix, f);
        this.mScaleFactor = getMaxZoom() / 3.0f;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = this.mIsReturn;
        if (z) {
            this.mScaleDetector.onTouchEvent(motionEvent);
            if (!this.mScaleDetector.isInProgress()) {
                this.mGestureDetector.onTouchEvent(motionEvent);
            }
            if ((motionEvent.getAction() & 255) == 1) {
                getScale();
                float f = this.mMinZoom;
            }
            return true;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void onZoom(float f) {
        super.onZoom(f);
        if (this.mScaleDetector.isInProgress()) {
            return;
        }
        this.mCurrentScaleFactor = f;
    }

    protected float onDoubleTapPost(float f, float f2) {
        if (this.mDoubleTapDirection == 1) {
            float f3 = this.mScaleFactor;
            if ((2.0f * f3) + f <= f2) {
                return f + f3;
            }
            this.mDoubleTapDirection = -1;
            return f2;
        }
        this.mDoubleTapDirection = 1;
        return 1.0f;
    }

    public void changeScale(float f) {
        postScale(f);
        invalidate();
    }

    public void postScale(float f) {
        this.mSuppMatrix.postScale(f, f, getWidth() / 2, getHeight() / 2);
        setImageMatrix(getImageViewMatrix());
    }

    /* loaded from: classes3.dex */
    public class GestureListener extends GestureDetector.SimpleOnGestureListener {
        public GestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            Log.i(ImageViewTouchBase.LOG_TAG, "onDoubleTap. double tap enabled? " + ImageViewTouch.this.mDoubleTapToZoomEnabled);
            if (ImageViewTouch.this.mDoubleTapToZoomEnabled) {
                float scale = ImageViewTouch.this.getScale();
                ImageViewTouch imageViewTouch = ImageViewTouch.this;
                float min = Math.min(ImageViewTouch.this.getMaxZoom(), Math.max(imageViewTouch.onDoubleTapPost(scale, imageViewTouch.getMaxZoom()), ImageViewTouch.this.mMinZoom));
                ImageViewTouch.this.mCurrentScaleFactor = min;
                ImageViewTouch.this.zoomTo(min, motionEvent.getX(), motionEvent.getY(), 200.0f);
                ImageViewTouch.this.invalidate();
            }
            if (ImageViewTouch.this.doubleTapListener != null) {
                ImageViewTouch.this.doubleTapListener.onDoubleTap();
            }
            return super.onDoubleTap(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            if (!ImageViewTouch.this.isLongClickable() || ImageViewTouch.this.mScaleDetector.isInProgress()) {
                return;
            }
            ImageViewTouch.this.setPressed(true);
            ImageViewTouch.this.performLongClick();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (ImageViewTouch.this.mScrollEnabled && motionEvent != null && motionEvent2 != null && motionEvent.getPointerCount() <= 1 && motionEvent2.getPointerCount() <= 1 && !ImageViewTouch.this.mScaleDetector.isInProgress()) {
                ImageViewTouch.this.scrollBy(-f, -f2);
                ImageViewTouch.this.invalidate();
                return super.onScroll(motionEvent, motionEvent2, f, f2);
            }
            return false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (ImageViewTouch.this.mScrollEnabled && motionEvent != null && motionEvent2 != null) {
                try {
                    if (motionEvent.getPointerCount() <= 1 && motionEvent2.getPointerCount() <= 1) {
                        if (ImageViewTouch.this.mScaleDetector.isInProgress() || motionEvent == null || motionEvent2 == null || motionEvent.getPointerCount() > 1 || motionEvent2.getPointerCount() > 1 || ImageViewTouch.this.mScaleDetector.isInProgress()) {
                            return false;
                        }
                        float x = motionEvent2.getX() - motionEvent.getX();
                        float y = motionEvent2.getY() - motionEvent.getY();
                        if (Math.abs(f) > 800.0f || Math.abs(f2) > 800.0f) {
                            ImageViewTouch.this.scrollBy(x / 2.0f, y / 2.0f, 300.0d);
                            ImageViewTouch.this.invalidate();
                        }
                        return super.onFling(motionEvent, motionEvent2, f, f2);
                    }
                } catch (Exception unused) {
                }
            }
            return false;
        }
    }

    /* loaded from: classes3.dex */
    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        public ScaleListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            scaleGestureDetector.getCurrentSpan();
            scaleGestureDetector.getPreviousSpan();
            float scaleFactor = ImageViewTouch.this.mCurrentScaleFactor * scaleGestureDetector.getScaleFactor();
            if (ImageViewTouch.this.mScaleEnabled) {
                float min = Math.min(ImageViewTouch.this.getMaxZoom(), Math.max(scaleFactor, ImageViewTouch.this.mMinZoom));
                ImageViewTouch.this.zoomTo(min, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                ImageViewTouch imageViewTouch = ImageViewTouch.this;
                imageViewTouch.mCurrentScaleFactor = Math.min(imageViewTouch.getMaxZoom(), Math.max(min, ImageViewTouch.this.mMinZoom));
                ImageViewTouch.this.mDoubleTapDirection = 1;
                ImageViewTouch.this.invalidate();
                return true;
            }
            return false;
        }
    }

    @Override // com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase
    public void ResetImageView() {
        super.ResetImageView();
        this.mScaleDetector = null;
        this.mGestureDetector = null;
        this.mTouchSlop = 0;
        this.mCurrentScaleFactor = 0.0f;
        this.mScaleFactor = 0.0f;
        this.mDoubleTapDirection = 0;
        this.mGestureListener = null;
        this.mScaleListener = null;
        this.mScaleEnabled = true;
        this.mScrollEnabled = true;
    }

    public void changeRotation(float f) {
        invalidate();
    }

    public void SetRotationEnable(boolean z) {
        this.mRotationEnabled = z;
    }

    public void Zoom(float f) {
        float f2 = this.mScale * f;
        this.mScale = f2;
        if (f2 > this.mMaxZoom) {
            this.mScale = this.mMaxZoom;
        }
        if (this.mScale < 1.0f) {
            this.mScale = 1.0f;
        }
        zoomTo(this.mScale);
    }

    public void SetScale(float f) {
        this.mScale = f;
    }
}
