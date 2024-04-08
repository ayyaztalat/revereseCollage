package com.photo.editor.square.splash.view;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/* loaded from: classes2.dex */
public class CSTouchView {
    public static final int STATUS_INIT = 1;
    public static final int STATUS_MOVE = 4;
    public static final int STATUS_ZOOM_IN = 3;
    public static final int STATUS_ZOOM_OUT = 2;
    private static final String TAG = "TouchViewT";
    private Bitmap bitmapItem;
    private Bitmap bitmapParent;
    private float centerPointX;
    private float centerPointY;
    private float initRatio;
    private boolean isInitDefaultPosition;
    private double lastFingerDis;
    protected MotionEvent mCurrEvent;
    protected float mCurrFingerDiffX;
    protected float mCurrFingerDiffY;
    protected MotionEvent mPrevEvent;
    protected float mPrevFingerDiffX;
    protected float mPrevFingerDiffY;
    private View mView;
    private float movedDistanceX;
    private float movedDistanceY;
    float rotationDegreesDelta;
    private float scaledRatio;
    private float totalTranslateX;
    private float totalTranslateY;
    private Matrix matrix = new Matrix();
    private float lastXMove = -1.0f;
    private float lastYMove = -1.0f;
    private float totalRatio = 1.0f;
    private float minScaleRatio = 0.2f;
    private float maxScaleRatio = 6.0f;
    private int currentStatus = 1;

    public CSTouchView(View view, Bitmap bitmap, Bitmap bitmap2) {
        this.mView = view;
        this.bitmapParent = bitmap;
        this.bitmapItem = bitmap2;
    }

    public void setupDefualtPosition(float f, float f2, float f3) {
        this.isInitDefaultPosition = true;
        this.totalTranslateX = f;
        this.totalTranslateY = f2;
        this.scaledRatio = f3;
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1) {
            this.lastXMove = -1.0f;
            this.lastYMove = -1.0f;
            resetState();
        } else if (actionMasked != 2 && actionMasked != 3) {
            if (actionMasked != 5) {
                if (actionMasked != 6) {
                    return;
                }
                if (motionEvent.getPointerCount() == 2) {
                    this.lastXMove = -1.0f;
                    this.lastYMove = -1.0f;
                }
                resetState();
                return;
            }
            resetState();
            if (motionEvent.getPointerCount() == 2) {
                this.lastFingerDis = distanceBetweenFingers(motionEvent);
                this.mPrevEvent = MotionEvent.obtain(motionEvent);
                updateStateByEvent(motionEvent);
            }
        } else if (motionEvent.getPointerCount() == 1) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (this.lastXMove == -1.0f && this.lastYMove == -1.0f) {
                this.lastXMove = x;
                this.lastYMove = y;
            }
            this.currentStatus = 4;
            this.movedDistanceX = x - this.lastXMove;
            this.movedDistanceY = y - this.lastYMove;
            this.mView.invalidate();
            this.lastXMove = x;
            this.lastYMove = y;
        } else if (motionEvent.getPointerCount() == 2) {
            centerPointBetweenFingers(motionEvent);
            double distanceBetweenFingers = distanceBetweenFingers(motionEvent);
            if (distanceBetweenFingers > this.lastFingerDis) {
                this.currentStatus = 2;
            } else {
                this.currentStatus = 3;
            }
            updateStateByEvent(motionEvent);
            MotionEvent motionEvent2 = this.mPrevEvent;
            if (motionEvent2 != null) {
                motionEvent2.recycle();
            }
            this.mPrevEvent = MotionEvent.obtain(motionEvent);
            if ((this.currentStatus != 2 || this.totalRatio >= this.maxScaleRatio * this.initRatio) && (this.currentStatus != 3 || this.totalRatio <= this.initRatio * this.minScaleRatio)) {
                return;
            }
            Log.d(TAG, "onTouchEvent: ");
            float f = (float) (distanceBetweenFingers / this.lastFingerDis);
            this.scaledRatio = f;
            float f2 = this.totalRatio * f;
            this.totalRatio = f2;
            float f3 = this.maxScaleRatio;
            float f4 = this.initRatio;
            if (f2 > f3 * f4) {
                this.totalRatio = f3 * f4;
            } else {
                float f5 = this.minScaleRatio;
                if (f2 < f4 * f5) {
                    this.totalRatio = f4 * f5;
                }
            }
            this.mView.invalidate();
            this.lastFingerDis = distanceBetweenFingers;
        }
    }

    public void postMatrix() {
        int i = this.currentStatus;
        if (i == 1) {
            initBitmap();
        } else if (i == 2 || i == 3) {
            zoom();
        } else if (i != 4) {
        } else {
            move();
        }
    }

    private void zoom() {
        this.matrix.reset();
        float rotationDegreesDelta = this.rotationDegreesDelta - getRotationDegreesDelta();
        this.rotationDegreesDelta = rotationDegreesDelta;
        this.matrix.postRotate(rotationDegreesDelta, this.bitmapItem.getWidth() * 0.5f, this.bitmapItem.getHeight() * 0.5f);
        Matrix matrix = this.matrix;
        float f = this.totalRatio;
        matrix.postScale(f, f);
        float f2 = this.totalTranslateX;
        float f3 = this.scaledRatio;
        float f4 = (f2 * f3) + (this.centerPointX * (1.0f - f3));
        float f5 = (this.totalTranslateY * f3) + (this.centerPointY * (1.0f - f3));
        this.matrix.postTranslate(f4, f5);
        this.totalTranslateX = f4;
        this.totalTranslateY = f5;
    }

    private void move() {
        this.matrix.reset();
        float rotationDegreesDelta = this.rotationDegreesDelta - getRotationDegreesDelta();
        this.rotationDegreesDelta = rotationDegreesDelta;
        this.matrix.postRotate(rotationDegreesDelta, this.bitmapItem.getWidth() * 0.5f, this.bitmapItem.getHeight() * 0.5f);
        float f = this.totalTranslateX + this.movedDistanceX;
        float f2 = this.totalTranslateY + this.movedDistanceY;
        Matrix matrix = this.matrix;
        float f3 = this.totalRatio;
        matrix.postScale(f3, f3);
        this.matrix.postTranslate(f, f2);
        this.totalTranslateX = f;
        this.totalTranslateY = f2;
    }

    private void initBitmap() {
        if (this.isInitDefaultPosition) {
            initBitmapCenter();
            return;
        }
        this.matrix.reset();
        Matrix matrix = this.matrix;
        float f = this.totalRatio;
        matrix.postScale(f, f);
        this.matrix.postTranslate(this.totalTranslateX, this.totalTranslateY);
    }

    private void initBitmapCenter() {
        if (this.bitmapItem != null) {
            this.matrix.reset();
            int width = this.bitmapItem.getWidth();
            int height = this.bitmapItem.getHeight();
            if (width > this.bitmapParent.getWidth() || height > this.bitmapParent.getHeight()) {
                if (width - this.bitmapParent.getWidth() > height - this.bitmapParent.getHeight()) {
                    float width2 = this.bitmapParent.getWidth() / (width * 1.0f);
                    this.matrix.postScale(width2, width2);
                    float height2 = (this.bitmapParent.getHeight() - (height * width2)) / 2.0f;
                    this.matrix.postTranslate(0.0f, height2);
                    this.totalTranslateY = height2;
                    this.initRatio = width2;
                    this.totalRatio = width2;
                    return;
                }
                float height3 = this.bitmapParent.getHeight() / (height * 1.0f);
                this.matrix.postScale(height3, height3);
                float width3 = (this.bitmapParent.getWidth() - (width * height3)) / 2.0f;
                this.matrix.postTranslate(width3, 0.0f);
                this.totalTranslateX = width3;
                this.initRatio = height3;
                this.totalRatio = height3;
                return;
            }
            float width4 = (this.bitmapParent.getWidth() - this.bitmapItem.getWidth()) / 2.0f;
            float height4 = (this.bitmapParent.getHeight() - this.bitmapItem.getHeight()) / 2.0f;
            this.matrix.postTranslate(width4, height4);
            this.totalTranslateX = width4;
            this.totalTranslateY = height4;
            this.initRatio = 1.0f;
            this.totalRatio = 1.0f;
        }
    }

    protected void updateStateByEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = this.mPrevEvent;
        if (motionEvent2 != null) {
            float x = motionEvent2.getX(0);
            float y = motionEvent2.getY(0);
            this.mPrevFingerDiffX = motionEvent2.getX(1) - x;
            this.mPrevFingerDiffY = motionEvent2.getY(1) - y;
        }
        float x2 = motionEvent.getX(0);
        float y2 = motionEvent.getY(0);
        this.mCurrFingerDiffX = motionEvent.getX(1) - x2;
        this.mCurrFingerDiffY = motionEvent.getY(1) - y2;
    }

    private double distanceBetweenFingers(MotionEvent motionEvent) {
        float abs = Math.abs(motionEvent.getX(0) - motionEvent.getX(1));
        float abs2 = Math.abs(motionEvent.getY(0) - motionEvent.getY(1));
        return Math.sqrt((abs * abs) + (abs2 * abs2));
    }

    private void centerPointBetweenFingers(MotionEvent motionEvent) {
        float x = motionEvent.getX(0);
        float y = motionEvent.getY(0);
        float x2 = motionEvent.getX(1);
        float y2 = motionEvent.getY(1);
        this.centerPointX = (x + x2) / 2.0f;
        this.centerPointY = (y + y2) / 2.0f;
    }

    protected void resetState() {
        MotionEvent motionEvent = this.mPrevEvent;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mPrevEvent = null;
        }
        MotionEvent motionEvent2 = this.mCurrEvent;
        if (motionEvent2 != null) {
            motionEvent2.recycle();
            this.mCurrEvent = null;
        }
        this.mPrevFingerDiffX = 0.0f;
        this.mPrevFingerDiffY = 0.0f;
        this.mCurrFingerDiffX = 0.0f;
        this.mCurrFingerDiffY = 0.0f;
    }

    public float getRotationDegreesDelta() {
        float atan2 = (float) (((Math.atan2(this.mPrevFingerDiffY, this.mPrevFingerDiffX) - Math.atan2(this.mCurrFingerDiffY, this.mCurrFingerDiffX)) * 180.0d) / 3.141592653589793d);
        Log.d(TAG, "getRotationDegreesDelta: " + atan2);
        return atan2;
    }

    public Matrix getMatrix() {
        return this.matrix;
    }

    public float getTotalTranslateX() {
        return this.totalTranslateX;
    }

    public float getTotalTranslateY() {
        return this.totalTranslateY;
    }

    public void setMinScaleRatio(float f) {
        this.minScaleRatio = f;
    }

    public void setMaxScaleRatio(float f) {
        this.maxScaleRatio = f;
    }
}
