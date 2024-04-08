package com.picspool.lib.sticker.util;

import android.content.Context;
import android.view.MotionEvent;

/* loaded from: classes3.dex */
public class DMRotateGestureDetectorBMTwoFingerGestureDetector extends DM_TwoFingerGestureDetector {
    private final OnRotateGestureListener mListener;
    private boolean mSloppyGesture;

    /* loaded from: classes3.dex */
    public interface OnRotateGestureListener {
        boolean onRotate(DMRotateGestureDetectorBMTwoFingerGestureDetector dMRotateGestureDetectorBMTwoFingerGestureDetector);

        boolean onRotateBegin(DMRotateGestureDetectorBMTwoFingerGestureDetector dMRotateGestureDetectorBMTwoFingerGestureDetector);

        void onRotateEnd(DMRotateGestureDetectorBMTwoFingerGestureDetector dMRotateGestureDetectorBMTwoFingerGestureDetector);
    }

    /* loaded from: classes3.dex */
    public static class SimpleOnRotateGestureListener implements OnRotateGestureListener {
        @Override // com.picspool.lib.sticker.util.DMRotateGestureDetectorBMTwoFingerGestureDetector.OnRotateGestureListener
        public boolean onRotate(DMRotateGestureDetectorBMTwoFingerGestureDetector dMRotateGestureDetectorBMTwoFingerGestureDetector) {
            return false;
        }

        @Override // com.picspool.lib.sticker.util.DMRotateGestureDetectorBMTwoFingerGestureDetector.OnRotateGestureListener
        public boolean onRotateBegin(DMRotateGestureDetectorBMTwoFingerGestureDetector dMRotateGestureDetectorBMTwoFingerGestureDetector) {
            return true;
        }

        @Override // com.picspool.lib.sticker.util.DMRotateGestureDetectorBMTwoFingerGestureDetector.OnRotateGestureListener
        public void onRotateEnd(DMRotateGestureDetectorBMTwoFingerGestureDetector dMRotateGestureDetectorBMTwoFingerGestureDetector) {
        }
    }

    public DMRotateGestureDetectorBMTwoFingerGestureDetector(Context context, OnRotateGestureListener onRotateGestureListener) {
        super(context);
        this.mListener = onRotateGestureListener;
    }

    @Override // com.picspool.lib.sticker.util.DM_TwoFingerGestureDetector, com.picspool.lib.sticker.util.DM_BaseGestureDetector
    protected void handleStartProgressEvent(int i, MotionEvent motionEvent) {
        if (i == 2) {
            if (this.mSloppyGesture) {
                boolean isSloppyGesture = isSloppyGesture(motionEvent);
                this.mSloppyGesture = isSloppyGesture;
                if (isSloppyGesture) {
                    return;
                }
                this.mGestureInProgress = this.mListener.onRotateBegin(this);
            }
        } else if (i != 5) {
        } else {
            resetState();
            this.mPrevEvent = MotionEvent.obtain(motionEvent);
            this.mTimeDelta = 0L;
            updateStateByEvent(motionEvent);
            boolean isSloppyGesture2 = isSloppyGesture(motionEvent);
            this.mSloppyGesture = isSloppyGesture2;
            if (isSloppyGesture2) {
                return;
            }
            this.mGestureInProgress = this.mListener.onRotateBegin(this);
        }
    }

    @Override // com.picspool.lib.sticker.util.DM_TwoFingerGestureDetector, com.picspool.lib.sticker.util.DM_BaseGestureDetector
    protected void handleInProgressEvent(int i, MotionEvent motionEvent) {
        if (i == 2) {
            updateStateByEvent(motionEvent);
            if (this.mCurrPressure / this.mPrevPressure <= 0.67f || !this.mListener.onRotate(this)) {
                return;
            }
            this.mPrevEvent.recycle();
            this.mPrevEvent = MotionEvent.obtain(motionEvent);
        } else if (i == 3) {
            if (!this.mSloppyGesture) {
                this.mListener.onRotateEnd(this);
            }
            resetState();
        } else if (i != 6) {
        } else {
            updateStateByEvent(motionEvent);
            if (!this.mSloppyGesture) {
                this.mListener.onRotateEnd(this);
            }
            resetState();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.sticker.util.DM_BaseGestureDetector
    public void resetState() {
        super.resetState();
        this.mSloppyGesture = false;
    }

    public float getRotationDegreesDelta() {
        return (float) (((Math.atan2(this.mPrevFingerDiffY, this.mPrevFingerDiffX) - Math.atan2(this.mCurrFingerDiffY, this.mCurrFingerDiffX)) * 180.0d) / 3.141592653589793d);
    }
}
