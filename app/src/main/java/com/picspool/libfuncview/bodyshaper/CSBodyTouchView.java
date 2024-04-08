package com.picspool.libfuncview.bodyshaper;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/* loaded from: classes.dex */
public class CSBodyTouchView extends FrameLayout {
    private Context mContext;
    private onTouchEventCallBack touchEventCallBack;

    /* loaded from: classes.dex */
    public interface onTouchEventCallBack {
        void getPoint(float f, float f2);
    }

    public CSBodyTouchView(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        onTouchEventCallBack ontoucheventcallback = this.touchEventCallBack;
        if (ontoucheventcallback != null) {
            ontoucheventcallback.getPoint(motionEvent.getX() / getWidth(), motionEvent.getY() / getHeight());
            return true;
        }
        return true;
    }

    public void setTouchEventCallBack(onTouchEventCallBack ontoucheventcallback) {
        this.touchEventCallBack = ontoucheventcallback;
    }
}
