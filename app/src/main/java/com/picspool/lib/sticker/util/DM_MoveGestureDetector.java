package com.picspool.lib.sticker.util;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

/* loaded from: classes3.dex */
public class DM_MoveGestureDetector extends DM_BaseGestureDetector {
    private static final PointF FOCUS_DELTA_ZERO = new PointF();
    private PointF mCurrFocusInternal;
    private PointF mFocusDeltaExternal;
    private PointF mFocusExternal;
    private final OnMoveGestureListener mListener;
    private PointF mPrevFocusInternal;

    /* loaded from: classes3.dex */
    public interface OnMoveGestureListener {
        boolean onMove(DM_MoveGestureDetector dM_MoveGestureDetector);

        boolean onMoveBegin(DM_MoveGestureDetector dM_MoveGestureDetector);

        void onMoveEnd(DM_MoveGestureDetector dM_MoveGestureDetector);
    }

    /* loaded from: classes3.dex */
    public static class SimpleOnMoveGestureListener implements OnMoveGestureListener {
        @Override // com.picspool.lib.sticker.util.DM_MoveGestureDetector.OnMoveGestureListener
        public boolean onMove(DM_MoveGestureDetector dM_MoveGestureDetector) {
            return false;
        }

        @Override // com.picspool.lib.sticker.util.DM_MoveGestureDetector.OnMoveGestureListener
        public boolean onMoveBegin(DM_MoveGestureDetector dM_MoveGestureDetector) {
            return true;
        }

        @Override // com.picspool.lib.sticker.util.DM_MoveGestureDetector.OnMoveGestureListener
        public void onMoveEnd(DM_MoveGestureDetector dM_MoveGestureDetector) {
        }
    }

    public DM_MoveGestureDetector(Context context, OnMoveGestureListener onMoveGestureListener) {
        super(context);
        this.mFocusExternal = new PointF();
        this.mFocusDeltaExternal = new PointF();
        this.mListener = onMoveGestureListener;
    }

    @Override // com.picspool.lib.sticker.util.DM_BaseGestureDetector
    protected void handleStartProgressEvent(int i, MotionEvent motionEvent) {
        if (i != 0) {
            if (i != 2) {
                return;
            }
            this.mGestureInProgress = this.mListener.onMoveBegin(this);
            return;
        }
        resetState();
        this.mPrevEvent = MotionEvent.obtain(motionEvent);
        this.mTimeDelta = 0L;
        updateStateByEvent(motionEvent);
    }

    @Override // com.picspool.lib.sticker.util.DM_BaseGestureDetector
    protected void handleInProgressEvent(int i, MotionEvent motionEvent) {
        if (i != 1) {
            if (i == 2) {
                updateStateByEvent(motionEvent);
                if (this.mCurrPressure / this.mPrevPressure <= 0.67f || !this.mListener.onMove(this)) {
                    return;
                }
                if (this.mPrevEvent != null) {
                    this.mPrevEvent.recycle();
                }
                this.mPrevEvent = MotionEvent.obtain(motionEvent);
                return;
            } else if (i != 3) {
                return;
            }
        }
        this.mListener.onMoveEnd(this);
        resetState();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.sticker.util.DM_BaseGestureDetector
    public void updateStateByEvent(MotionEvent motionEvent) {
        if (motionEvent == null || this.mPrevEvent == null) {
            return;
        }
        super.updateStateByEvent(motionEvent);
        MotionEvent motionEvent2 = this.mPrevEvent;
        this.mCurrFocusInternal = determineFocalPoint(motionEvent);
        this.mPrevFocusInternal = determineFocalPoint(motionEvent2);
        this.mFocusDeltaExternal = motionEvent2.getPointerCount() != motionEvent.getPointerCount() ? FOCUS_DELTA_ZERO : new PointF(this.mCurrFocusInternal.x - this.mPrevFocusInternal.x, this.mCurrFocusInternal.y - this.mPrevFocusInternal.y);
        this.mFocusExternal.x += this.mFocusDeltaExternal.x;
        this.mFocusExternal.y += this.mFocusDeltaExternal.y;
    }

    private PointF determineFocalPoint(MotionEvent motionEvent) {
        float f = 0.0f;
        if (motionEvent == null) {
            return new PointF(0.0f, 0.0f);
        }
        int pointerCount = motionEvent.getPointerCount();
        float f2 = 0.0f;
        for (int i = 0; i < pointerCount; i++) {
            f += motionEvent.getX(i);
            f2 += motionEvent.getY(i);
        }
        float f3 = pointerCount;
        return new PointF(f / f3, f2 / f3);
    }

    public float getFocusX() {
        return this.mFocusExternal.x;
    }

    public float getFocusY() {
        return this.mFocusExternal.y;
    }

    public PointF getFocusDelta() {
        return this.mFocusDeltaExternal;
    }
}
