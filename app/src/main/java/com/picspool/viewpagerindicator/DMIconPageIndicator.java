package com.picspool.viewpagerindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import androidx.viewpager.widget.ViewPager;

import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMIconPageIndicator extends HorizontalScrollView implements DMPageIndicator {
    private Runnable mIconSelector;
    private final DMIcsLinearLayout mIconsLayout;
    private ViewPager.OnPageChangeListener mListener;
    private int mSelectedIndex;
    private ViewPager mViewPager;

    public DMIconPageIndicator(Context context) {
        this(context, null);
    }

    public DMIconPageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setHorizontalScrollBarEnabled(false);
        DMIcsLinearLayout dMIcsLinearLayout = new DMIcsLinearLayout(context, R.attr.vpiIconPageIndicatorStyle);
        this.mIconsLayout = dMIcsLinearLayout;
        addView(dMIcsLinearLayout, new LayoutParams(-2, -1, 17));
    }

    private void animateToIcon(int i) {
        final View childAt = this.mIconsLayout.getChildAt(i);
        Runnable runnable = this.mIconSelector;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: com.picspool.viewpagerindicator.DMIconPageIndicator.1
            @Override // java.lang.Runnable
            public void run() {
                DMIconPageIndicator.this.smoothScrollTo(childAt.getLeft() - ((DMIconPageIndicator.this.getWidth() - childAt.getWidth()) / 2), 0);
                DMIconPageIndicator.this.mIconSelector = null;
            }
        };
        this.mIconSelector = runnable2;
        post(runnable2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Runnable runnable = this.mIconSelector;
        if (runnable != null) {
            post(runnable);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable = this.mIconSelector;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
        ViewPager.OnPageChangeListener onPageChangeListener = this.mListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(i);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
        ViewPager.OnPageChangeListener onPageChangeListener = this.mListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(i, f, i2);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        setCurrentItem(i);
        ViewPager.OnPageChangeListener onPageChangeListener = this.mListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(i);
        }
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void setViewPager(ViewPager viewPager) {
        ViewPager viewPager2 = this.mViewPager;
        if (viewPager2 == viewPager) {
            return;
        }
        if (viewPager2 != null) {
            viewPager2.setOnPageChangeListener(null);
        }
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.mViewPager = viewPager;
        viewPager.setOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void notifyDataSetChanged() {
        this.mIconsLayout.removeAllViews();
        DMIconPagerAdapter dMIconPagerAdapter = (DMIconPagerAdapter) this.mViewPager.getAdapter();
        int count = dMIconPagerAdapter.getCount();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext(), null, R.attr.vpiIconPageIndicatorStyle);
            imageView.setImageResource(dMIconPagerAdapter.getIconResId(i));
            this.mIconsLayout.addView(imageView);
        }
        if (this.mSelectedIndex > count) {
            this.mSelectedIndex = count - 1;
        }
        setCurrentItem(this.mSelectedIndex);
        requestLayout();
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void setViewPager(ViewPager viewPager, int i) {
        setViewPager(viewPager);
        setCurrentItem(i);
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void setCurrentItem(int i) {
        ViewPager viewPager = this.mViewPager;
        if (viewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.mSelectedIndex = i;
        viewPager.setCurrentItem(i);
        int childCount = this.mIconsLayout.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = this.mIconsLayout.getChildAt(i2);
            boolean z = i2 == i;
            childAt.setSelected(z);
            if (z) {
                animateToIcon(i);
            }
            i2++;
        }
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }
}
