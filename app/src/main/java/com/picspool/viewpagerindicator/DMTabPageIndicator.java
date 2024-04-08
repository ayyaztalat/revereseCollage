package com.picspool.viewpagerindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMTabPageIndicator extends HorizontalScrollView implements DMPageIndicator {
    private static final CharSequence EMPTY_TITLE = "";
    private ViewPager.OnPageChangeListener mListener;
    private int mMaxTabWidth;
    private int mSelectedTabIndex;
    private final OnClickListener mTabClickListener;
    private final DMIcsLinearLayout mTabLayout;
    private OnTabReselectedListener mTabReselectedListener;
    private Runnable mTabSelector;
    private ViewPager mViewPager;

    /* loaded from: classes3.dex */
    public interface OnTabReselectedListener {
        void onTabReselected(int i);
    }

    public DMTabPageIndicator(Context context) {
        this(context, null);
    }

    public DMTabPageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTabClickListener = new OnClickListener() { // from class: com.picspool.viewpagerindicator.DMTabPageIndicator.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int currentItem = DMTabPageIndicator.this.mViewPager.getCurrentItem();
                int index = ((TabView) view).getIndex();
                DMTabPageIndicator.this.mViewPager.setCurrentItem(index);
                if (currentItem != index || DMTabPageIndicator.this.mTabReselectedListener == null) {
                    return;
                }
                DMTabPageIndicator.this.mTabReselectedListener.onTabReselected(index);
            }
        };
        setHorizontalScrollBarEnabled(false);
        DMIcsLinearLayout dMIcsLinearLayout = new DMIcsLinearLayout(context, R.attr.vpiTabPageIndicatorStyle);
        this.mTabLayout = dMIcsLinearLayout;
        addView(dMIcsLinearLayout, new ViewGroup.LayoutParams(-2, -1));
    }

    public void setOnTabReselectedListener(OnTabReselectedListener onTabReselectedListener) {
        this.mTabReselectedListener = onTabReselectedListener;
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        boolean z = mode == 1073741824;
        setFillViewport(z);
        int childCount = this.mTabLayout.getChildCount();
        if (childCount <= 1 || !(mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            this.mMaxTabWidth = -1;
        } else if (childCount > 2) {
            this.mMaxTabWidth = (int) (MeasureSpec.getSize(i) * 0.4f);
        } else {
            this.mMaxTabWidth = MeasureSpec.getSize(i) / 2;
        }
        int measuredWidth = getMeasuredWidth();
        super.onMeasure(i, i2);
        int measuredWidth2 = getMeasuredWidth();
        if (!z || measuredWidth == measuredWidth2) {
            return;
        }
        setCurrentItem(this.mSelectedTabIndex);
    }

    private void animateToTab(int i) {
        final View childAt = this.mTabLayout.getChildAt(i);
        Runnable runnable = this.mTabSelector;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
        Runnable runnable2 = new Runnable() { // from class: com.picspool.viewpagerindicator.DMTabPageIndicator.2
            @Override // java.lang.Runnable
            public void run() {
                DMTabPageIndicator.this.smoothScrollTo(childAt.getLeft() - ((DMTabPageIndicator.this.getWidth() - childAt.getWidth()) / 2), 0);
                DMTabPageIndicator.this.mTabSelector = null;
            }
        };
        this.mTabSelector = runnable2;
        post(runnable2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Runnable runnable = this.mTabSelector;
        if (runnable != null) {
            post(runnable);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Runnable runnable = this.mTabSelector;
        if (runnable != null) {
            removeCallbacks(runnable);
        }
    }

    private void addTab(int i, CharSequence charSequence, int i2) {
        TabView tabView = new TabView(getContext());
        tabView.mIndex = i;
        tabView.setFocusable(true);
        tabView.setOnClickListener(this.mTabClickListener);
        tabView.setText(charSequence);
        if (i2 != 0) {
            tabView.setCompoundDrawablesWithIntrinsicBounds(i2, 0, 0, 0);
        }
        this.mTabLayout.addView(tabView, new LinearLayout.LayoutParams(0, -1, 1.0f));
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
        this.mTabLayout.removeAllViews();
        PagerAdapter adapter = this.mViewPager.getAdapter();
        DMIconPagerAdapter dMIconPagerAdapter = adapter instanceof DMIconPagerAdapter ? (DMIconPagerAdapter) adapter : null;
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence pageTitle = adapter.getPageTitle(i);
            if (pageTitle == null) {
                pageTitle = EMPTY_TITLE;
            }
            addTab(i, pageTitle, dMIconPagerAdapter != null ? dMIconPagerAdapter.getIconResId(i) : 0);
        }
        if (this.mSelectedTabIndex > count) {
            this.mSelectedTabIndex = count - 1;
        }
        setCurrentItem(this.mSelectedTabIndex);
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
        this.mSelectedTabIndex = i;
        viewPager.setCurrentItem(i);
        int childCount = this.mTabLayout.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = this.mTabLayout.getChildAt(i2);
            boolean z = i2 == i;
            childAt.setSelected(z);
            if (z) {
                animateToTab(i);
            }
            i2++;
        }
    }

    @Override // com.picspool.viewpagerindicator.DMPageIndicator
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class TabView extends androidx.appcompat.widget.AppCompatTextView {
        private int mIndex;

        public TabView(Context context) {
            super(context, null, R.attr.vpiTabPageIndicatorStyle);
        }

        @Override // android.widget.TextView, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (DMTabPageIndicator.this.mMaxTabWidth <= 0 || getMeasuredWidth() <= DMTabPageIndicator.this.mMaxTabWidth) {
                return;
            }
            super.onMeasure(MeasureSpec.makeMeasureSpec(DMTabPageIndicator.this.mMaxTabWidth, MeasureSpec.EXACTLY), i2);
        }

        public int getIndex() {
            return this.mIndex;
        }
    }
}
