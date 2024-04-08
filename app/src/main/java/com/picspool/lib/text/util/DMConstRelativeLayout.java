package com.picspool.lib.text.util;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/* loaded from: classes3.dex */
public class DMConstRelativeLayout extends RelativeLayout {
    private Handler handler;
    private int screenHeight;

    public DMConstRelativeLayout(Context context) {
        super(context);
        this.handler = new Handler();
        this.screenHeight = 0;
    }

    public DMConstRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.handler = new Handler();
        this.screenHeight = 0;
    }

    public DMConstRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.handler = new Handler();
        this.screenHeight = 0;
    }

    @Override // android.view.View
    protected void onSizeChanged(final int i, int i2, int i3, int i4) {
        if (this.screenHeight == 0) {
            this.screenHeight = i2;
        }
        this.handler.post(new Runnable() { // from class: com.picspool.lib.text.util.DMConstRelativeLayout.1
            @Override // java.lang.Runnable
            public void run() {
                DMConstRelativeLayout.this.setLayoutParams(new FrameLayout.LayoutParams(i, DMConstRelativeLayout.this.screenHeight));
            }
        });
    }
}
