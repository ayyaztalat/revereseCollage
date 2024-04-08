package com.picspool.libfuncview.masicview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/* loaded from: classes.dex */
public class CSSgImageView extends View {
    private static final int DEFAULT_ANIM_DURATION = 600;
    private static final float DEFAULT_MAX_SCALE = 2.5f;
    private static final String TAG = "CSSgImageView";
    private static final int TYPE_NO_ANIME = -1;
    private static final int TYPE_SCALE_ANIM = 1;
    private static final int TYPE_TRANSLATE_ANIM = 0;
    private boolean doubleClick;
    private boolean hasRecordIllegalScaleMatrix;
    private int mAnimDuration;
    private int mCurAnimType;
    private GestureDetector mGestureDetector;
    private Matrix mIllegalScaleMatrix;
    private ImageLocation mImageLocation;
    private Matrix mImageMatrix;
    private Matrix mInitMatrix;
    private float mMaxHeight;
    private float mMaxScale;
    private float mMaxWidth;
    private float mPrePointsDis;
    private float mPreX;
    private float mPreY;
    private MatrixFactory.SgScaleType mScaleType;
    private ScrollerProxy<PointF> mScroller;
    private Matrix mTempMatrix;
    private boolean minorPointerDown;
    private boolean minorPointerUp;
    private Bitmap src;
    private boolean srcBmpSizeChanged;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class OnDoubleClickListener extends GestureDetector.SimpleOnGestureListener {
        private OnDoubleClickListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onDoubleTap(MotionEvent motionEvent) {
            float f;
            float f2;
            CSSgImageView.this.mImageLocation.convert(CSSgImageView.this.mImageMatrix);
            float curWidth = CSSgImageView.this.mImageLocation.getCurWidth();
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (curWidth > CSSgImageView.this.mMaxWidth * 0.5f) {
                f2 = CSSgImageView.this.mImageLocation.getInitWidth() / curWidth;
                float[] computeReboundScaleCenter = CSSgImageView.this.computeReboundScaleCenter(CSSgImageView.this.mImageLocation.getCurLeftTop(), CSSgImageView.this.mImageLocation.getInitLeftTop(), f2);
                x = computeReboundScaleCenter[0];
                f = computeReboundScaleCenter[1];
            } else {
                float f3 = CSSgImageView.this.mMaxWidth / curWidth;
                f = y;
                f2 = f3;
            }
            CSSgImageView.this.startScaleAnim(f2, f2, x, f);
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ScrollerProxy<T> {
        private static final int DELTA = 1000;
        private float mDeltaX;
        private float mDeltaY;
        private T mHolderValue;
        private Scroller mScroller;
        private float mStartX;
        private float mStartY;

        private ScrollerProxy(Scroller scroller) {
            this.mScroller = scroller;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void startScroll(float f, float f2, float f3, float f4, int i) {
            this.mStartX = f;
            this.mStartY = f2;
            this.mDeltaX = f3;
            this.mDeltaY = f4;
            this.mScroller.startScroll(0, 0, 1000, 1000, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getCurrX() {
            return (((this.mScroller.getCurrX() * 1.0f) / 1000.0f) * this.mDeltaX) + this.mStartX;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getCurrY() {
            return (((this.mScroller.getCurrY() * 1.0f) / 1000.0f) * this.mDeltaY) + this.mStartY;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean computeScrollOffset() {
            return this.mScroller.computeScrollOffset();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isFinished() {
            return this.mScroller.isFinished();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void abortAnimation() {
            this.mScroller.abortAnimation();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setHolderValue(T t) {
            this.mHolderValue = t;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public T getHolderValue() {
            return this.mHolderValue;
        }
    }

    /* loaded from: classes.dex */
    public static class ImageLocation {
        private float[] mCurLeftTop;
        private float[] mCurRightBottom;
        private float mInitHeight;
        private float[] mInitLeftTop;
        private float[] mInitRightBottom;
        private float mInitWidth;
        private float[] mSrcLeftTop;
        private float[] mSrcRightBottom;

        private ImageLocation() {
            this.mInitLeftTop = new float[2];
            this.mInitRightBottom = new float[2];
            this.mCurLeftTop = new float[2];
            this.mCurRightBottom = new float[2];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSrcLeftTop(float[] fArr) {
            this.mSrcLeftTop = fArr;
        }

        public float[] getSrcLeftTop() {
            return this.mSrcLeftTop;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSrcRightBottom(float[] fArr) {
            this.mSrcRightBottom = fArr;
        }

        public float[] getSrcRightBottom() {
            return this.mSrcRightBottom;
        }

        public float getSrcWidth() {
            return this.mSrcRightBottom[0] - this.mSrcLeftTop[0];
        }

        public float getSrcHeight() {
            return this.mSrcRightBottom[1] - this.mSrcLeftTop[1];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void init(Matrix matrix) {
            System.arraycopy(this.mSrcLeftTop, 0, this.mInitLeftTop, 0, 2);
            matrix.mapPoints(this.mInitLeftTop);
            round(this.mInitLeftTop);
            System.arraycopy(this.mSrcRightBottom, 0, this.mInitRightBottom, 0, 2);
            matrix.mapPoints(this.mInitRightBottom);
            round(this.mInitRightBottom);
            float[] fArr = this.mInitRightBottom;
            float f = fArr[0];
            float[] fArr2 = this.mInitLeftTop;
            this.mInitWidth = f - fArr2[0];
            this.mInitHeight = fArr[1] - fArr2[1];
        }

        public float[] getInitLeftTop() {
            return this.mInitLeftTop;
        }

        public float[] getInitRightBottom() {
            return this.mInitRightBottom;
        }

        public float getInitWidth() {
            return this.mInitWidth;
        }

        public float getInitHeight() {
            return this.mInitHeight;
        }

        public void convert(Matrix matrix) {
            System.arraycopy(this.mSrcLeftTop, 0, this.mCurLeftTop, 0, 2);
            matrix.mapPoints(this.mCurLeftTop);
            round(this.mCurLeftTop);
            System.arraycopy(this.mSrcRightBottom, 0, this.mCurRightBottom, 0, 2);
            matrix.mapPoints(this.mCurRightBottom);
            round(this.mCurRightBottom);
        }

        public float[] getCurLeftTop() {
            return this.mCurLeftTop;
        }

        public float[] getCurRightBottom() {
            return this.mCurRightBottom;
        }

        public float getCurWidth() {
            return this.mCurRightBottom[0] - this.mCurLeftTop[0];
        }

        public float getCurHeight() {
            return this.mCurRightBottom[1] - this.mCurLeftTop[1];
        }

        public float[] round(float[] fArr) {
            fArr[0] = Math.round(fArr[0]);
            fArr[1] = Math.round(fArr[1]);
            return fArr;
        }
    }

    /* loaded from: classes.dex */
    static class MatrixFactory {

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes.dex */
        public enum SgScaleType {
            CENTER,
            CENTER_INSIDE,
            CENTER_CROP
        }

        MatrixFactory() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static Matrix create(SgScaleType sgScaleType, float f, float f2, float f3, float f4) {
            Matrix matrix = new Matrix();
            if (sgScaleType == SgScaleType.CENTER) {
                matrix.setTranslate(Math.round((f - f3) * 0.5f), Math.round((f2 - f4) * 0.5f));
            } else if (sgScaleType == SgScaleType.CENTER_INSIDE) {
                float min = Math.min(f / f3, f2 / f4);
                matrix.setScale(min, min);
                matrix.postTranslate(Math.round((f - (f3 * min)) * 0.5f), Math.round((f2 - (f4 * min)) * 0.5f));
            } else if (sgScaleType == SgScaleType.CENTER_CROP) {
                float max = Math.max(f / f3, f2 / f4);
                matrix.setScale(max, max);
                matrix.postTranslate(Math.round((f - (f3 * max)) * 0.5f), Math.round((f2 - (f4 * max)) * 0.5f));
            }
            return matrix;
        }
    }

    public CSSgImageView(Context context) {
        super(context);
        this.mScaleType = MatrixFactory.SgScaleType.CENTER_INSIDE;
        this.mMaxScale = DEFAULT_MAX_SCALE;
        this.mCurAnimType = -1;
        this.mAnimDuration = DEFAULT_ANIM_DURATION;
        this.minorPointerUp = false;
        this.minorPointerDown = false;
        this.doubleClick = false;
        this.hasRecordIllegalScaleMatrix = false;
        this.srcBmpSizeChanged = false;
        init();
    }

    public CSSgImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mScaleType = MatrixFactory.SgScaleType.CENTER_INSIDE;
        this.mMaxScale = DEFAULT_MAX_SCALE;
        this.mCurAnimType = -1;
        this.mAnimDuration = DEFAULT_ANIM_DURATION;
        this.minorPointerUp = false;
        this.minorPointerDown = false;
        this.doubleClick = false;
        this.hasRecordIllegalScaleMatrix = false;
        this.srcBmpSizeChanged = false;
        init();
    }

    public void setBitmap(Bitmap bitmap) {
        Bitmap bitmap2 = this.src;
        if (bitmap2 == null) {
            this.srcBmpSizeChanged = true;
            this.src = bitmap;
            return;
        }
        int width = bitmap2.getWidth();
        int height = this.src.getHeight();
        int width2 = bitmap.getWidth();
        int height2 = bitmap.getHeight();
        this.src = bitmap;
        if (width != width2 || height != height2) {
            this.srcBmpSizeChanged = true;
            requestLayout();
        }
        invalidate();
    }

    public Bitmap getBitmap() {
        return this.src;
    }

    public void setScaleType(MatrixFactory.SgScaleType sgScaleType) {
        this.mScaleType = sgScaleType;
    }

    public MatrixFactory.SgScaleType getScaleType() {
        return this.mScaleType;
    }

    public void setMaxScale(float f) {
        this.mMaxScale = f;
    }

    public float getMaxScale() {
        return this.mMaxScale;
    }

    public Matrix getImageMatrix() {
        return this.mImageMatrix;
    }

    public ImageLocation getImageLocation() {
        return this.mImageLocation;
    }

    public void setAnimDuration(int i) {
        this.mAnimDuration = i;
    }

    private void init() {
        this.mImageLocation = new ImageLocation();
        this.mScroller = new ScrollerProxy<>(new Scroller(getContext()));
        this.mInitMatrix = new Matrix();
        this.mImageMatrix = new Matrix();
        this.mTempMatrix = new Matrix();
        this.mIllegalScaleMatrix = new Matrix();
        this.mGestureDetector = new GestureDetector(getContext(), new OnDoubleClickListener());
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        Bitmap bitmap = this.src;
        if (bitmap != null && !bitmap.isRecycled()) {
            setMeasuredDimension(getSize(i, this.src.getWidth()), getSize(i2, this.src.getHeight()));
        } else {
            super.onMeasure(i, i2);
        }
    }

    private int getSize(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode != Integer.MIN_VALUE) {
            if (mode != 1073741824) {
                return 0;
            }
            return size;
        }
        return i2;
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z || this.srcBmpSizeChanged) {
            Matrix create = MatrixFactory.create(this.mScaleType, getWidth(), getHeight(), this.src.getWidth(), this.src.getHeight());
            this.mInitMatrix.set(create);
            this.mImageMatrix.set(create);
            confirmBound();
            this.srcBmpSizeChanged = false;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mGestureDetector.onTouchEvent(motionEvent)) {
            this.doubleClick = true;
            return true;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mPreX = motionEvent.getX();
            this.mPreY = motionEvent.getY();
        } else {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (!this.mScroller.isFinished() && !this.doubleClick) {
                        this.mScroller.abortAnimation();
                    }
                    if (motionEvent.getPointerCount() > 1) {
                        float x = motionEvent.getX(0);
                        float y = motionEvent.getY(0);
                        float x2 = motionEvent.getX(1);
                        float y2 = motionEvent.getY(1);
                        float computePointsDis = computePointsDis(x, y, x2, y2);
                        float f = computePointsDis / this.mPrePointsDis;
                        float f2 = (x + x2) * 0.5f;
                        float f3 = (x2 + y2) * 0.5f;
                        float f4 = x - this.mPreX;
                        float f5 = y - this.mPreY;
                        if (this.minorPointerUp || this.minorPointerDown) {
                            this.minorPointerUp = false;
                            this.minorPointerDown = false;
                        } else {
                            float f6 = f4 * 0.65f;
                            float f7 = f5 * 0.65f;
                            if (isIllegalScaleRatio(f, f, f2, f3)) {
                                recordIllegalScaleMatrix();
                                f = ((f - 1.0f) * 0.2f) + 1.0f;
                                f6 *= 0.5f;
                                f7 *= 0.5f;
                            }
                            scaleInternal(f, f, f2, f3);
                            translateInternal(f6, f7);
                        }
                        this.mPrePointsDis = computePointsDis;
                        this.mPreX = x;
                        this.mPreY = y;
                    } else {
                        float x3 = motionEvent.getX();
                        float y3 = motionEvent.getY();
                        if (this.minorPointerUp) {
                            this.minorPointerUp = false;
                        } else {
                            translateInternal((x3 - this.mPreX) * 0.65f, (y3 - this.mPreY) * 0.65f);
                        }
                        this.mPreX = x3;
                        this.mPreY = y3;
                    }
                } else if (actionMasked != 3) {
                    if (actionMasked == 5) {
                        this.minorPointerDown = true;
                    } else if (actionMasked == 6) {
                        this.minorPointerUp = true;
                    }
                }
            }
            if (this.doubleClick) {
                this.doubleClick = false;
            } else {
                performLastPointerUp();
            }
        }
        return true;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Matrix matrix;
        super.onDraw(canvas);
        Bitmap bitmap = this.src;
        if (bitmap == null || bitmap.isRecycled() || (matrix = this.mImageMatrix) == null) {
            return;
        }
        canvas.drawBitmap(this.src, matrix, null);
    }

    private void confirmBound() {
        this.mImageLocation.setSrcLeftTop(new float[]{0.0f, 0.0f});
        this.mImageLocation.setSrcRightBottom(new float[]{this.src.getWidth(), this.src.getHeight()});
        this.mImageLocation.init(this.mInitMatrix);
        this.mMaxWidth = Math.round(this.mImageLocation.getInitWidth() * this.mMaxScale);
        this.mMaxHeight = Math.round(this.mImageLocation.getInitHeight() * this.mMaxScale);
    }

    private void translateInternal(float f, float f2) {
        this.mImageMatrix.postTranslate(Math.round(f), Math.round(f2));
        invalidate();
    }

    public void translate(float f, float f2, boolean z) {
        if (z && isIllegalTranslate(f, f2)) {
            return;
        }
        translateInternal(f, f2);
    }

    public void setTranslate(float f, float f2, boolean z) {
        if (z && isIllegalTranslate(f, f2)) {
            return;
        }
        this.mImageMatrix.set(this.mInitMatrix);
        translateInternal(f, f2);
    }

    private void scaleInternal(float f, float f2, float f3, float f4) {
        this.mImageMatrix.postScale(f, f2, Math.round(f3), Math.round(f4));
        invalidate();
    }

    public void scale(float f, float f2, float f3, float f4, boolean z) {
        if (z && isIllegalScaleRatio(f, f2, f3, f4)) {
            return;
        }
        scaleInternal(f, f2, f3, f4);
    }

    public void setScale(float f, float f2, float f3, float f4, boolean z) {
        if (z && isIllegalScaleRatio(f, f2, f3, f4)) {
            return;
        }
        this.mImageMatrix.set(this.mInitMatrix);
        scaleInternal(f, f2, f3, f4);
    }

    private void recordIllegalScaleMatrix() {
        if (this.hasRecordIllegalScaleMatrix) {
            return;
        }
        this.hasRecordIllegalScaleMatrix = true;
        this.mIllegalScaleMatrix.set(this.mImageMatrix);
    }

    private boolean isIllegalScaleRatio(float f, float f2, float f3, float f4) {
        this.mTempMatrix.set(this.mImageMatrix);
        this.mTempMatrix.postScale(f, f2, Math.round(f3), Math.round(f4));
        this.mImageLocation.convert(this.mTempMatrix);
        float curWidth = this.mImageLocation.getCurWidth();
        float curHeight = this.mImageLocation.getCurHeight();
        return curWidth > this.mMaxWidth || curWidth < this.mImageLocation.getInitWidth() || curHeight > this.mMaxHeight || curHeight < this.mImageLocation.getInitHeight();
    }

    private boolean isIllegalTranslate(float f, float f2) {
        this.mTempMatrix.set(this.mImageMatrix);
        this.mTempMatrix.postTranslate(Math.round(f), Math.round(f2));
        this.mImageLocation.convert(this.mTempMatrix);
        float[] curLeftTop = this.mImageLocation.getCurLeftTop();
        float[] curRightBottom = this.mImageLocation.getCurRightBottom();
        float[] fArr = {0.0f, 0.0f};
        float[] fArr2 = {getWidth(), getHeight()};
        return curLeftTop[0] > fArr[0] || curRightBottom[0] < fArr2[0] || curLeftTop[1] > fArr[1] || curRightBottom[1] < fArr2[1];
    }

    private float computePointsDis(float f, float f2, float f3, float f4) {
        float f5 = f - f3;
        float f6 = f2 - f4;
        return (float) Math.sqrt((f5 * f5) + (f6 * f6));
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void performLastPointerUp() {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.libfuncview.masicview.CSSgImageView.performLastPointerUp():void");
    }

    @Override // android.view.View
    public void computeScroll() {
        ScrollerProxy<PointF> scrollerProxy;
        PointF pointF;
        if (this.mCurAnimType == -1 || (scrollerProxy = this.mScroller) == null || !scrollerProxy.computeScrollOffset() || this.mScroller.isFinished()) {
            return;
        }
        this.mImageMatrix.set(this.mTempMatrix);
        int i = this.mCurAnimType;
        if (i == 0) {
            translateInternal(this.mScroller.getCurrX(), this.mScroller.getCurrY());
        } else if (i != 1 || (pointF = (PointF) this.mScroller.getHolderValue()) == null) {
        } else {
            scaleInternal(this.mScroller.getCurrX(), this.mScroller.getCurrY(), pointF.x, pointF.y);
        }
    }

    private void startTranslateAnim(float f, float f2) {
        if (!this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        float round = Math.round(f);
        float round2 = Math.round(f2);
        if (round == 0.0f && round2 == 0.0f) {
            return;
        }
        this.mCurAnimType = 0;
        this.mTempMatrix.set(this.mImageMatrix);
        this.mScroller.startScroll(0.0f, 0.0f, round, round2, this.mAnimDuration);
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startScaleAnim(float f, float f2, float f3, float f4) {
        if (!this.mScroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        this.mCurAnimType = 1;
        this.mTempMatrix.set(this.mImageMatrix);
        this.mScroller.setHolderValue(new PointF(f3, f4));
        this.mScroller.startScroll(1.0f, 1.0f, f - 1.0f, f2 - 1.0f, this.mAnimDuration);
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float[] computeReboundScaleCenter(float[] fArr, float[] fArr2, float f) {
        float[] fArr3 = new float[2];
        if (f != 1.0f) {
            float f2 = 1.0f - f;
            fArr3[0] = (fArr2[0] - (fArr[0] * f)) / f2;
            fArr3[1] = (fArr2[1] - (fArr[1] * f)) / f2;
        }
        return fArr3;
    }
}
