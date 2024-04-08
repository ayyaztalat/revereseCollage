package com.picspool.instatextview.labelview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes3.dex */
public class DMLockViewPager extends ViewPager {
    private boolean lockFlag;

    public DMLockViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.lockFlag = true;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.lockFlag) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.lockFlag) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public boolean isLockFlag() {
        return this.lockFlag;
    }

    public void setLockFlag(boolean z) {
        this.lockFlag = z;
    }
}
