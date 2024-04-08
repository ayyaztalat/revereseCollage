package com.picspool.viewpagerindicator;

import androidx.viewpager.widget.ViewPager;

/* loaded from: classes3.dex */
public interface DMPageIndicator extends ViewPager.OnPageChangeListener {
    void notifyDataSetChanged();

    void setCurrentItem(int i);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setViewPager(ViewPager viewPager);

    void setViewPager(ViewPager viewPager, int i);
}
