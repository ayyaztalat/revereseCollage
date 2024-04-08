package com.picspool.lib.sticker.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/* loaded from: classes3.dex */
public abstract class DM_TwoFingerGestureDetector extends DM_BaseGestureDetector {
    private float mBottomSlopEdge;
    protected float mCurrFingerDiffX;
    protected float mCurrFingerDiffY;
    private float mCurrLen;
    private final float mEdgeSlop;
    protected float mPrevFingerDiffX;
    protected float mPrevFingerDiffY;
    private float mPrevLen;
    private float mRightSlopEdge;

    @Override // com.picspool.lib.sticker.util.DM_BaseGestureDetector
    protected abstract void handleInProgressEvent(int i, MotionEvent motionEvent);

    @Override // com.picspool.lib.sticker.util.DM_BaseGestureDetector
    protected abstract void handleStartProgressEvent(int i, MotionEvent motionEvent);

    public DM_TwoFingerGestureDetector(Context context) {
        super(context);
        this.mEdgeSlop = ViewConfiguration.get(context).getScaledEdgeSlop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.sticker.util.DM_BaseGestureDetector
    public void updateStateByEvent(MotionEvent motionEvent) {
        super.updateStateByEvent(motionEvent);
        MotionEvent motionEvent2 = this.mPrevEvent;
        this.mCurrLen = -1.0f;
        this.mPrevLen = -1.0f;
        float x = motionEvent2.getX(0);
        float y = motionEvent2.getY(0);
        this.mPrevFingerDiffX = motionEvent2.getX(1) - x;
        this.mPrevFingerDiffY = motionEvent2.getY(1) - y;
        float x2 = motionEvent.getX(0);
        float y2 = motionEvent.getY(0);
        this.mCurrFingerDiffX = motionEvent.getX(1) - x2;
        this.mCurrFingerDiffY = motionEvent.getY(1) - y2;
    }

    public float getCurrentSpan() {
        if (this.mCurrLen == -1.0f) {
            float f = this.mCurrFingerDiffX;
            float f2 = this.mCurrFingerDiffY;
            this.mCurrLen = (float) Math.sqrt((f * f) + (f2 * f2));
        }
        return this.mCurrLen;
    }

    public float getPreviousSpan() {
        if (this.mPrevLen == -1.0f) {
            float f = this.mPrevFingerDiffX;
            float f2 = this.mPrevFingerDiffY;
            this.mPrevLen = (float) Math.sqrt((f * f) + (f2 * f2));
        }
        return this.mPrevLen;
    }

    protected static float getRawX(MotionEvent motionEvent, int i) {
        float x = motionEvent.getX() - motionEvent.getRawX();
        if (i < motionEvent.getPointerCount()) {
            return motionEvent.getX(i) + x;
        }
        return 0.0f;
    }

    protected static float getRawY(MotionEvent motionEvent, int i) {
        float y = motionEvent.getY() - motionEvent.getRawY();
        if (i < motionEvent.getPointerCount()) {
            return motionEvent.getY(i) + y;
        }
        return 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSloppyGesture(MotionEvent motionEvent) {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        this.mRightSlopEdge = displayMetrics.widthPixels - this.mEdgeSlop;
        float f = this.mEdgeSlop;
        float f2 = displayMetrics.heightPixels - f;
        this.mBottomSlopEdge = f2;
        float f3 = this.mRightSlopEdge;
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        float rawX2 = getRawX(motionEvent, 1);
        float rawY2 = getRawY(motionEvent, 1);
        boolean z = rawX < f || rawY < f || rawX > f3 || rawY > f2;
        boolean z2 = rawX2 < f || rawY2 < f || rawX2 > f3 || rawY2 > f2;
        return (z && z2) || z || z2;
    }
}
