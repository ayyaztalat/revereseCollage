package com.picspool.libfuncview.masicview;

import android.view.MotionEvent;

/* loaded from: classes.dex */
public interface CSActionGesture {
    void actionRedo();

    void actionTouch(MotionEvent motionEvent, float f, float f2, float f3);

    void actionUndo();
}
