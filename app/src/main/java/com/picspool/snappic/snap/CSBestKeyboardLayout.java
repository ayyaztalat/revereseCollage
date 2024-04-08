package com.picspool.snappic.snap;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;

/* loaded from: classes.dex */
public class CSBestKeyboardLayout extends RelativeLayout {
    private static final String TAG = "KeyboardLayoutTAG";
    private onSizeChangedListener mChangedListener;
    private boolean mShowKeyboard;
    private int screenHeight;

    /* loaded from: classes.dex */
    public interface onSizeChangedListener {
        void onBraydenChanged(boolean z, int i, int i2, int i3);
    }

    public CSBestKeyboardLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowKeyboard = false;
        this.screenHeight = 0;
    }

    public CSBestKeyboardLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShowKeyboard = false;
        this.screenHeight = 0;
    }

    public CSBestKeyboardLayout(Context context) {
        super(context);
        this.mShowKeyboard = false;
        this.screenHeight = 0;
    }

    @Override // android.widget.RelativeLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        Log.d(TAG, "onMeasure-----------");
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        Log.d(TAG, "onLayout-------------------");
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (this.screenHeight == 0) {
            this.screenHeight = i2;
        }
        Log.d(TAG, "--------------------------------------------------------------");
        Log.d(TAG, "w----" + i + "\nh-----" + i2 + "\noldW-----" + i3 + "\noldh----" + i4);
        if (this.mChangedListener == null || i3 == 0 || i4 == 0) {
            return;
        }
        if (i2 < i4) {
            this.mShowKeyboard = true;
            this.mChangedListener.onBraydenChanged(this.mShowKeyboard, i, i2, DMScreenInfoUtil.screenHeight(getContext()) - i2);
        } else {
            this.mShowKeyboard = false;
        }
        Log.d(TAG, "mShowKeyboard-----      " + this.mShowKeyboard);
    }

    public void setOnSizeChangedListener(onSizeChangedListener onsizechangedlistener) {
        this.mChangedListener = onsizechangedlistener;
    }
}
