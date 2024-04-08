package com.picspool.lib.sticker.util;

import android.content.Context;
import android.view.MotionEvent;

/* loaded from: classes3.dex */
public abstract class DM_BaseGestureDetector {
    protected static final float PRESSURE_THRESHOLD = 0.67f;
    protected final Context mContext;
    protected MotionEvent mCurrEvent;
    protected float mCurrPressure;
    protected boolean mGestureInProgress;
    protected MotionEvent mPrevEvent;
    protected float mPrevPressure;
    protected long mTimeDelta;

    protected abstract void handleInProgressEvent(int i, MotionEvent motionEvent);

    protected abstract void handleStartProgressEvent(int i, MotionEvent motionEvent);

    public DM_BaseGestureDetector(Context context) {
        this.mContext = context;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (!this.mGestureInProgress) {
            handleStartProgressEvent(action, motionEvent);
            return true;
        }
        handleInProgressEvent(action, motionEvent);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateStateByEvent(MotionEvent motionEvent) {
        MotionEvent motionEvent2 = this.mPrevEvent;
        if (motionEvent == null || motionEvent2 == null) {
            return;
        }
        MotionEvent motionEvent3 = this.mCurrEvent;
        if (motionEvent3 != null) {
            motionEvent3.recycle();
            this.mCurrEvent = null;
        }
        this.mCurrEvent = MotionEvent.obtain(motionEvent);
        this.mTimeDelta = motionEvent.getEventTime() - motionEvent2.getEventTime();
        this.mCurrPressure = motionEvent.getPressure(motionEvent.getActionIndex());
        this.mPrevPressure = motionEvent2.getPressure(motionEvent2.getActionIndex());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void resetState() {
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
        this.mGestureInProgress = false;
    }

    public boolean isInProgress() {
        return this.mGestureInProgress;
    }

    public long getTimeDelta() {
        return this.mTimeDelta;
    }

    public long getEventTime() {
        return this.mCurrEvent.getEventTime();
    }
}
