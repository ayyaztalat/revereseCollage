package com.picspool.libfuncview.effect.onlinestore.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSHomeTopViewPagerIndicator extends FrameLayout {
    private ViewGroup mIndicatorContainer;

    public CSHomeTopViewPagerIndicator(Context context) {
        super(context);
        init();
    }

    public CSHomeTopViewPagerIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.indicator_home_top_viewpager, (ViewGroup) this, true);
        this.mIndicatorContainer = (ViewGroup) findViewById(R.id.indicator_container);
    }

    public void setViewCount(int i) {
        this.mIndicatorContainer.removeAllViews();
        for (int i2 = 0; i2 < i; i2++) {
            View view = new View(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DMScreenInfoUtil.dip2px(getContext(), 6.0f), DMScreenInfoUtil.dip2px(getContext(), 2.0f));
            layoutParams.leftMargin = DMScreenInfoUtil.dip2px(getContext(), 5.0f);
            layoutParams.rightMargin = DMScreenInfoUtil.dip2px(getContext(), 5.0f);
            layoutParams.gravity = 1;
            view.setBackgroundResource(R.drawable.bg_indicator);
            this.mIndicatorContainer.addView(view, layoutParams);
            setSelected(0);
        }
    }

    public void setSelected(int i) {
        for (int i2 = 0; i2 < this.mIndicatorContainer.getChildCount(); i2++) {
            this.mIndicatorContainer.getChildAt(i2).setSelected(false);
        }
        this.mIndicatorContainer.getChildAt(i).setSelected(true);
    }
}
