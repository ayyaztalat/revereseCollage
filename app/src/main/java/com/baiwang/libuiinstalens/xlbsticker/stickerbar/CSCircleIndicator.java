package com.baiwang.libuiinstalens.xlbsticker.stickerbar;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import androidx.viewpager.widget.ViewPager;
import com.sky.testproject.R;
import java.util.List;

/* loaded from: classes.dex */
public class CSCircleIndicator extends LinearLayout {
    private static final int DEFAULT_INDICATOR_WIDTH = 5;
    private int[] group_pagerscount;
    private Animator mAnimatorIn;
    private Animator mAnimatorOut;
    private int mAnimatorResId;
    private int mAnimatorReverseResId;
    private Animator mImmediateAnimatorIn;
    private Animator mImmediateAnimatorOut;
    private int mIndicatorBackgroundResId;
    private int mIndicatorHeight;
    private int mIndicatorMargin;
    private int mIndicatorUnselectedBackgroundResId;
    private int mIndicatorWidth;
    private DataSetObserver mInternalDataSetObserver;
    private final ViewPager.OnPageChangeListener mInternalPageChangeListener;
    private int mLastPagerPosition;
    private int mLastPosition;
    private int mPagerItemCount;
    private ViewPager mViewpager;
    private List<CSStickerGroup> stickerGroups;

    public CSCircleIndicator(Context context) {
        super(context);
        this.mIndicatorMargin = -1;
        this.mIndicatorWidth = -1;
        this.mIndicatorHeight = -1;
        this.mAnimatorResId =R.animator.scale_with_alpha;
        this.mAnimatorReverseResId = 0;
        this.mIndicatorBackgroundResId =R.drawable.white_radius;
        this.mIndicatorUnselectedBackgroundResId =R.drawable.white_radius;
        this.mLastPosition = -1;
        this.mPagerItemCount = 1;
        this.mLastPagerPosition = 0;
        this.mInternalPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSCircleIndicator.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                CSCircleIndicator cSCircleIndicator;
                View childAt;
                int i2 = 0;
                int i3 = 0;
                while (i2 < CSCircleIndicator.this.group_pagerscount.length) {
                    i3 += CSCircleIndicator.this.group_pagerscount[i2];
                    if (i - i3 < 0) {
                        break;
                    }
                    i2++;
                }
                Log.d("xlb", "real position" + i);
                if (i2 > 0) {
                    i -= i3 - CSCircleIndicator.this.group_pagerscount[i2];
                }
                if (CSCircleIndicator.this.mLastPagerPosition != i2) {
                    CSCircleIndicator.this.mLastPagerPosition = i2;
                    CSCircleIndicator.this.createIndicators(i);
                }
                Log.d("xlb", "position:" + i + "///mLastPagerPosition:" + CSCircleIndicator.this.mLastPagerPosition + "///currentpagerindex:" + i2 + "///pagerindex:" + i3 + "///group_pagerscount:" + CSCircleIndicator.this.group_pagerscount[i2]);
                if (CSCircleIndicator.this.mViewpager.getAdapter() == null || CSCircleIndicator.this.mViewpager.getAdapter().getCount() <= 0) {
                    return;
                }
                if (CSCircleIndicator.this.mAnimatorIn.isRunning()) {
                    CSCircleIndicator.this.mAnimatorIn.end();
                    CSCircleIndicator.this.mAnimatorIn.cancel();
                }
                if (CSCircleIndicator.this.mAnimatorOut.isRunning()) {
                    CSCircleIndicator.this.mAnimatorOut.end();
                    CSCircleIndicator.this.mAnimatorOut.cancel();
                }
                if (CSCircleIndicator.this.mLastPosition >= 0 && (childAt = (cSCircleIndicator = CSCircleIndicator.this).getChildAt(cSCircleIndicator.mLastPosition)) != null) {
                    childAt.setBackgroundResource(CSCircleIndicator.this.mIndicatorUnselectedBackgroundResId);
                    CSCircleIndicator.this.mAnimatorIn.setTarget(childAt);
                    CSCircleIndicator.this.mAnimatorIn.start();
                }
                View childAt2 = CSCircleIndicator.this.getChildAt(i);
                if (childAt2 != null) {
                    childAt2.setBackgroundResource(CSCircleIndicator.this.mIndicatorBackgroundResId);
                    CSCircleIndicator.this.mAnimatorOut.setTarget(childAt2);
                    CSCircleIndicator.this.mAnimatorOut.start();
                }
                CSCircleIndicator.this.mLastPosition = i;
            }
        };
        this.mInternalDataSetObserver = new DataSetObserver() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSCircleIndicator.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                int count;
                super.onChanged();
                if (CSCircleIndicator.this.mViewpager == null || (count = CSCircleIndicator.this.mViewpager.getAdapter().getCount()) == CSCircleIndicator.this.getChildCount()) {
                    return;
                }
                if (CSCircleIndicator.this.mLastPosition >= count) {
                    CSCircleIndicator.this.mLastPosition = -1;
                } else {
                    CSCircleIndicator cSCircleIndicator = CSCircleIndicator.this;
                    cSCircleIndicator.mLastPosition = cSCircleIndicator.mViewpager.getCurrentItem();
                }
                CSCircleIndicator.this.createIndicators(0);
            }
        };
        init(context, null);
    }

    public CSCircleIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIndicatorMargin = -1;
        this.mIndicatorWidth = -1;
        this.mIndicatorHeight = -1;
        this.mAnimatorResId =R.animator.scale_with_alpha;
        this.mAnimatorReverseResId = 0;
        this.mIndicatorBackgroundResId =R.drawable.white_radius;
        this.mIndicatorUnselectedBackgroundResId =R.drawable.white_radius;
        this.mLastPosition = -1;
        this.mPagerItemCount = 1;
        this.mLastPagerPosition = 0;
        this.mInternalPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSCircleIndicator.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                CSCircleIndicator cSCircleIndicator;
                View childAt;
                int i2 = 0;
                int i3 = 0;
                while (i2 < CSCircleIndicator.this.group_pagerscount.length) {
                    i3 += CSCircleIndicator.this.group_pagerscount[i2];
                    if (i - i3 < 0) {
                        break;
                    }
                    i2++;
                }
                Log.d("xlb", "real position" + i);
                if (i2 > 0) {
                    i -= i3 - CSCircleIndicator.this.group_pagerscount[i2];
                }
                if (CSCircleIndicator.this.mLastPagerPosition != i2) {
                    CSCircleIndicator.this.mLastPagerPosition = i2;
                    CSCircleIndicator.this.createIndicators(i);
                }
                Log.d("xlb", "position:" + i + "///mLastPagerPosition:" + CSCircleIndicator.this.mLastPagerPosition + "///currentpagerindex:" + i2 + "///pagerindex:" + i3 + "///group_pagerscount:" + CSCircleIndicator.this.group_pagerscount[i2]);
                if (CSCircleIndicator.this.mViewpager.getAdapter() == null || CSCircleIndicator.this.mViewpager.getAdapter().getCount() <= 0) {
                    return;
                }
                if (CSCircleIndicator.this.mAnimatorIn.isRunning()) {
                    CSCircleIndicator.this.mAnimatorIn.end();
                    CSCircleIndicator.this.mAnimatorIn.cancel();
                }
                if (CSCircleIndicator.this.mAnimatorOut.isRunning()) {
                    CSCircleIndicator.this.mAnimatorOut.end();
                    CSCircleIndicator.this.mAnimatorOut.cancel();
                }
                if (CSCircleIndicator.this.mLastPosition >= 0 && (childAt = (cSCircleIndicator = CSCircleIndicator.this).getChildAt(cSCircleIndicator.mLastPosition)) != null) {
                    childAt.setBackgroundResource(CSCircleIndicator.this.mIndicatorUnselectedBackgroundResId);
                    CSCircleIndicator.this.mAnimatorIn.setTarget(childAt);
                    CSCircleIndicator.this.mAnimatorIn.start();
                }
                View childAt2 = CSCircleIndicator.this.getChildAt(i);
                if (childAt2 != null) {
                    childAt2.setBackgroundResource(CSCircleIndicator.this.mIndicatorBackgroundResId);
                    CSCircleIndicator.this.mAnimatorOut.setTarget(childAt2);
                    CSCircleIndicator.this.mAnimatorOut.start();
                }
                CSCircleIndicator.this.mLastPosition = i;
            }
        };
        this.mInternalDataSetObserver = new DataSetObserver() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSCircleIndicator.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                int count;
                super.onChanged();
                if (CSCircleIndicator.this.mViewpager == null || (count = CSCircleIndicator.this.mViewpager.getAdapter().getCount()) == CSCircleIndicator.this.getChildCount()) {
                    return;
                }
                if (CSCircleIndicator.this.mLastPosition >= count) {
                    CSCircleIndicator.this.mLastPosition = -1;
                } else {
                    CSCircleIndicator cSCircleIndicator = CSCircleIndicator.this;
                    cSCircleIndicator.mLastPosition = cSCircleIndicator.mViewpager.getCurrentItem();
                }
                CSCircleIndicator.this.createIndicators(0);
            }
        };
        init(context, attributeSet);
    }

    public CSCircleIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIndicatorMargin = -1;
        this.mIndicatorWidth = -1;
        this.mIndicatorHeight = -1;
        this.mAnimatorResId =R.animator.scale_with_alpha;
        this.mAnimatorReverseResId = 0;
        this.mIndicatorBackgroundResId =R.drawable.white_radius;
        this.mIndicatorUnselectedBackgroundResId =R.drawable.white_radius;
        this.mLastPosition = -1;
        this.mPagerItemCount = 1;
        this.mLastPagerPosition = 0;
        this.mInternalPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSCircleIndicator.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i2, float f, int i22) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i2) {
                CSCircleIndicator cSCircleIndicator;
                View childAt;
                int i22 = 0;
                int i3 = 0;
                while (i22 < CSCircleIndicator.this.group_pagerscount.length) {
                    i3 += CSCircleIndicator.this.group_pagerscount[i22];
                    if (i2 - i3 < 0) {
                        break;
                    }
                    i22++;
                }
                Log.d("xlb", "real position" + i2);
                if (i22 > 0) {
                    i2 -= i3 - CSCircleIndicator.this.group_pagerscount[i22];
                }
                if (CSCircleIndicator.this.mLastPagerPosition != i22) {
                    CSCircleIndicator.this.mLastPagerPosition = i22;
                    CSCircleIndicator.this.createIndicators(i2);
                }
                Log.d("xlb", "position:" + i2 + "///mLastPagerPosition:" + CSCircleIndicator.this.mLastPagerPosition + "///currentpagerindex:" + i22 + "///pagerindex:" + i3 + "///group_pagerscount:" + CSCircleIndicator.this.group_pagerscount[i22]);
                if (CSCircleIndicator.this.mViewpager.getAdapter() == null || CSCircleIndicator.this.mViewpager.getAdapter().getCount() <= 0) {
                    return;
                }
                if (CSCircleIndicator.this.mAnimatorIn.isRunning()) {
                    CSCircleIndicator.this.mAnimatorIn.end();
                    CSCircleIndicator.this.mAnimatorIn.cancel();
                }
                if (CSCircleIndicator.this.mAnimatorOut.isRunning()) {
                    CSCircleIndicator.this.mAnimatorOut.end();
                    CSCircleIndicator.this.mAnimatorOut.cancel();
                }
                if (CSCircleIndicator.this.mLastPosition >= 0 && (childAt = (cSCircleIndicator = CSCircleIndicator.this).getChildAt(cSCircleIndicator.mLastPosition)) != null) {
                    childAt.setBackgroundResource(CSCircleIndicator.this.mIndicatorUnselectedBackgroundResId);
                    CSCircleIndicator.this.mAnimatorIn.setTarget(childAt);
                    CSCircleIndicator.this.mAnimatorIn.start();
                }
                View childAt2 = CSCircleIndicator.this.getChildAt(i2);
                if (childAt2 != null) {
                    childAt2.setBackgroundResource(CSCircleIndicator.this.mIndicatorBackgroundResId);
                    CSCircleIndicator.this.mAnimatorOut.setTarget(childAt2);
                    CSCircleIndicator.this.mAnimatorOut.start();
                }
                CSCircleIndicator.this.mLastPosition = i2;
            }
        };
        this.mInternalDataSetObserver = new DataSetObserver() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSCircleIndicator.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                int count;
                super.onChanged();
                if (CSCircleIndicator.this.mViewpager == null || (count = CSCircleIndicator.this.mViewpager.getAdapter().getCount()) == CSCircleIndicator.this.getChildCount()) {
                    return;
                }
                if (CSCircleIndicator.this.mLastPosition >= count) {
                    CSCircleIndicator.this.mLastPosition = -1;
                } else {
                    CSCircleIndicator cSCircleIndicator = CSCircleIndicator.this;
                    cSCircleIndicator.mLastPosition = cSCircleIndicator.mViewpager.getCurrentItem();
                }
                CSCircleIndicator.this.createIndicators(0);
            }
        };
        init(context, attributeSet);
    }

    public CSCircleIndicator(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIndicatorMargin = -1;
        this.mIndicatorWidth = -1;
        this.mIndicatorHeight = -1;
        this.mAnimatorResId =R.animator.scale_with_alpha;
        this.mAnimatorReverseResId = 0;
        this.mIndicatorBackgroundResId =R.drawable.white_radius;
        this.mIndicatorUnselectedBackgroundResId =R.drawable.white_radius;
        this.mLastPosition = -1;
        this.mPagerItemCount = 1;
        this.mLastPagerPosition = 0;
        this.mInternalPageChangeListener = new ViewPager.OnPageChangeListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSCircleIndicator.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i22) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i22, float f, int i222) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i22) {
                CSCircleIndicator cSCircleIndicator;
                View childAt;
                int i222 = 0;
                int i3 = 0;
                while (i222 < CSCircleIndicator.this.group_pagerscount.length) {
                    i3 += CSCircleIndicator.this.group_pagerscount[i222];
                    if (i22 - i3 < 0) {
                        break;
                    }
                    i222++;
                }
                Log.d("xlb", "real position" + i22);
                if (i222 > 0) {
                    i22 -= i3 - CSCircleIndicator.this.group_pagerscount[i222];
                }
                if (CSCircleIndicator.this.mLastPagerPosition != i222) {
                    CSCircleIndicator.this.mLastPagerPosition = i222;
                    CSCircleIndicator.this.createIndicators(i22);
                }
                Log.d("xlb", "position:" + i22 + "///mLastPagerPosition:" + CSCircleIndicator.this.mLastPagerPosition + "///currentpagerindex:" + i222 + "///pagerindex:" + i3 + "///group_pagerscount:" + CSCircleIndicator.this.group_pagerscount[i222]);
                if (CSCircleIndicator.this.mViewpager.getAdapter() == null || CSCircleIndicator.this.mViewpager.getAdapter().getCount() <= 0) {
                    return;
                }
                if (CSCircleIndicator.this.mAnimatorIn.isRunning()) {
                    CSCircleIndicator.this.mAnimatorIn.end();
                    CSCircleIndicator.this.mAnimatorIn.cancel();
                }
                if (CSCircleIndicator.this.mAnimatorOut.isRunning()) {
                    CSCircleIndicator.this.mAnimatorOut.end();
                    CSCircleIndicator.this.mAnimatorOut.cancel();
                }
                if (CSCircleIndicator.this.mLastPosition >= 0 && (childAt = (cSCircleIndicator = CSCircleIndicator.this).getChildAt(cSCircleIndicator.mLastPosition)) != null) {
                    childAt.setBackgroundResource(CSCircleIndicator.this.mIndicatorUnselectedBackgroundResId);
                    CSCircleIndicator.this.mAnimatorIn.setTarget(childAt);
                    CSCircleIndicator.this.mAnimatorIn.start();
                }
                View childAt2 = CSCircleIndicator.this.getChildAt(i22);
                if (childAt2 != null) {
                    childAt2.setBackgroundResource(CSCircleIndicator.this.mIndicatorBackgroundResId);
                    CSCircleIndicator.this.mAnimatorOut.setTarget(childAt2);
                    CSCircleIndicator.this.mAnimatorOut.start();
                }
                CSCircleIndicator.this.mLastPosition = i22;
            }
        };
        this.mInternalDataSetObserver = new DataSetObserver() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSCircleIndicator.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                int count;
                super.onChanged();
                if (CSCircleIndicator.this.mViewpager == null || (count = CSCircleIndicator.this.mViewpager.getAdapter().getCount()) == CSCircleIndicator.this.getChildCount()) {
                    return;
                }
                if (CSCircleIndicator.this.mLastPosition >= count) {
                    CSCircleIndicator.this.mLastPosition = -1;
                } else {
                    CSCircleIndicator cSCircleIndicator = CSCircleIndicator.this;
                    cSCircleIndicator.mLastPosition = cSCircleIndicator.mViewpager.getCurrentItem();
                }
                CSCircleIndicator.this.createIndicators(0);
            }
        };
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        handleTypedArray(context, attributeSet);
        checkIndicatorConfig(context);
    }

    private void handleTypedArray(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet,R.styleable.CircleIndicator);
        this.mIndicatorWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CircleIndicator_ci_width, -1);
        this.mIndicatorHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CircleIndicator_ci_height, -1);
        this.mIndicatorMargin = obtainStyledAttributes.getDimensionPixelSize(R.styleable.CircleIndicator_ci_margin, -1);
        this.mAnimatorResId = obtainStyledAttributes.getResourceId(R.styleable.CircleIndicator_ci_animator,R.animator.scale_with_alpha);
        this.mAnimatorReverseResId = obtainStyledAttributes.getResourceId(R.styleable.CircleIndicator_ci_animator_reverse, 0);
        this.mIndicatorBackgroundResId = obtainStyledAttributes.getResourceId(R.styleable.CircleIndicator_ci_drawable,R.drawable.white_radius);
        this.mIndicatorUnselectedBackgroundResId = obtainStyledAttributes.getResourceId(R.styleable.CircleIndicator_ci_drawable_unselected, this.mIndicatorBackgroundResId);
        setOrientation(obtainStyledAttributes.getInt(R.styleable.CircleIndicator_ci_orientation, -1) == 1 ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL);
        int i = obtainStyledAttributes.getInt(R.styleable.CircleIndicator_ci_gravity, -1);
        if (i < 0) {
            i = 17;
        }
        setGravity(i);
        obtainStyledAttributes.recycle();
    }

    public void configureIndicator(int i, int i2, int i3) {
        configureIndicator(i, i2, i3,R.animator.scale_with_alpha, 0,R.drawable.white_radius,R.drawable.white_radius);
    }

    public void configureIndicator(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        this.mIndicatorWidth = i;
        this.mIndicatorHeight = i2;
        this.mIndicatorMargin = i3;
        this.mAnimatorResId = i4;
        this.mAnimatorReverseResId = i5;
        this.mIndicatorBackgroundResId = i6;
        this.mIndicatorUnselectedBackgroundResId = i7;
        checkIndicatorConfig(getContext());
    }

    private void checkIndicatorConfig(Context context) {
        int i = this.mIndicatorWidth;
        if (i < 0) {
            i = dip2px(5.0f);
        }
        this.mIndicatorWidth = i;
        int i2 = this.mIndicatorHeight;
        if (i2 < 0) {
            i2 = dip2px(5.0f);
        }
        this.mIndicatorHeight = i2;
        int i3 = this.mIndicatorMargin;
        if (i3 < 0) {
            i3 = dip2px(5.0f);
        }
        this.mIndicatorMargin = i3;
        int i4 = this.mAnimatorResId;
        if (i4 == 0) {
            i4 =R.animator.scale_with_alpha;
        }
        this.mAnimatorResId = i4;
        this.mAnimatorOut = createAnimatorOut(context);
        Animator createAnimatorOut = createAnimatorOut(context);
        this.mImmediateAnimatorOut = createAnimatorOut;
        createAnimatorOut.setDuration(0L);
        this.mAnimatorIn = createAnimatorIn(context);
        Animator createAnimatorIn = createAnimatorIn(context);
        this.mImmediateAnimatorIn = createAnimatorIn;
        createAnimatorIn.setDuration(0L);
        int i5 = this.mIndicatorBackgroundResId;
        if (i5 == 0) {
            i5 =R.drawable.white_radius;
        }
        this.mIndicatorBackgroundResId = i5;
        int i6 = this.mIndicatorUnselectedBackgroundResId;
        if (i6 != 0) {
            i5 = i6;
        }
        this.mIndicatorUnselectedBackgroundResId = i5;
    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator(context, this.mAnimatorResId);
    }

    private Animator createAnimatorIn(Context context) {
        int i = this.mAnimatorReverseResId;
        if (i == 0) {
            Animator loadAnimator = AnimatorInflater.loadAnimator(context, this.mAnimatorResId);
            loadAnimator.setInterpolator(new ReverseInterpolator());
            return loadAnimator;
        }
        return AnimatorInflater.loadAnimator(context, i);
    }

    public void setViewPager(ViewPager viewPager) {
        this.mViewpager = viewPager;
        if (viewPager == null || viewPager.getAdapter() == null) {
            return;
        }
        this.mLastPosition = -1;
        createIndicators(0);
        this.mViewpager.removeOnPageChangeListener(this.mInternalPageChangeListener);
        this.mViewpager.addOnPageChangeListener(this.mInternalPageChangeListener);
        this.mInternalPageChangeListener.onPageSelected(this.mViewpager.getCurrentItem());
    }

    public DataSetObserver getDataSetObserver() {
        return this.mInternalDataSetObserver;
    }

    @Deprecated
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        ViewPager viewPager = this.mViewpager;
        if (viewPager == null) {
            throw new NullPointerException("can not find Viewpager , setViewPager first");
        }
        viewPager.removeOnPageChangeListener(onPageChangeListener);
        this.mViewpager.addOnPageChangeListener(onPageChangeListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createIndicators(int i) {
        removeAllViews();
        int i2 = this.group_pagerscount[this.mLastPagerPosition];
        if (i2 <= 0) {
            return;
        }
        int orientation = getOrientation();
        for (int i3 = 0; i3 < i2; i3++) {
            if (i == i3) {
                addIndicator(orientation, this.mIndicatorBackgroundResId, this.mImmediateAnimatorOut);
            } else {
                addIndicator(orientation, this.mIndicatorUnselectedBackgroundResId, this.mImmediateAnimatorIn);
            }
        }
    }

    private void addIndicator(int i, int i2, Animator animator) {
        if (animator.isRunning()) {
            animator.end();
            animator.cancel();
        }
        View view = new View(getContext());
        view.setBackgroundResource(i2);
        addView(view, this.mIndicatorWidth, this.mIndicatorHeight);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (i == 0) {
            layoutParams.leftMargin = this.mIndicatorMargin;
            layoutParams.rightMargin = this.mIndicatorMargin;
        } else {
            layoutParams.topMargin = this.mIndicatorMargin;
            layoutParams.bottomMargin = this.mIndicatorMargin;
        }
        view.setLayoutParams(layoutParams);
        animator.setTarget(view);
        animator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ReverseInterpolator implements Interpolator {
        private ReverseInterpolator() {
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            return Math.abs(1.0f - f);
        }
    }

    public int dip2px(float f) {
        return (int) ((f * getResources().getDisplayMetrics().density) + 0.5f);
    }

    public void setStickerGroups(List<CSStickerGroup> list) {
        this.stickerGroups = list;
    }

    public void setmPagerItemCount(int i) {
        this.mPagerItemCount = i;
    }

    public void setGroup_pagerscount(int[] iArr) {
        this.group_pagerscount = iArr;
    }
}
