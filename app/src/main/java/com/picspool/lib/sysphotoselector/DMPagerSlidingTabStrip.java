package com.picspool.lib.sysphotoselector;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.util.Pair;
import androidx.viewpager.widget.ViewPager;

import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMPagerSlidingTabStrip extends HorizontalScrollView {
    private static final int[] ANDROID_ATTRS = {16842806, 16842965, 16842966, 16842968};
    public static final int DEF_VALUE_TAB_TEXT_ALPHA = 150;
    private static final int PADDING_INDEX = 1;
    private static final int PADDING_LEFT_INDEX = 2;
    private static final int PADDING_RIGHT_INDEX = 3;
    private static final int TEXT_COLOR_PRIMARY = 0;
    private ViewTreeObserver.OnGlobalLayoutListener firstTabGlobalLayoutListener;
    private boolean isCustomTabs;
    private boolean isExpandTabs;
    private boolean isPaddingMiddle;
    private boolean isTabTextAllCaps;
    private final PagerAdapterObserver mAdapterObserver;
    private int mCurrentPosition;
    private float mCurrentPositionOffset;
    public ViewPager.OnPageChangeListener mDelegatePageListener;
    private int mDividerColor;
    private int mDividerPadding;
    private Paint mDividerPaint;
    private int mDividerWidth;
    private int mIndicatorColor;
    private int mIndicatorHeight;
    private int mLastScrollX;
    private int mPaddingLeft;
    private int mPaddingRight;
    private final PageListener mPageListener;
    private ViewPager mPager;
    private Paint mRectPaint;
    private int mScrollOffset;
    private int mTabBackgroundResId;
    private int mTabCount;
    private LinearLayout.LayoutParams mTabLayoutParams;
    private int mTabPadding;
    private OnTabReselectedListener mTabReselectedListener;
    private ColorStateList mTabTextColor;
    private int mTabTextSize;
    private Typeface mTabTextTypeface;
    private int mTabTextTypefaceStyle;
    private LinearLayout mTabsContainer;
    private int mUnderlineColor;
    private int mUnderlineHeight;
    private int selectedPosition;
    private int selectedTabTextColor;
    private int unselectedTabTextColor;

    /* loaded from: classes3.dex */
    public interface CustomTabProvider {
        View getCustomTabView(ViewGroup viewGroup, int i);

        void tabSelected(View view);

        void tabUnselected(View view);
    }

    /* loaded from: classes3.dex */
    public interface OnTabReselectedListener {
        void onTabReselected(int i);
    }

    public DMPagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public DMPagerSlidingTabStrip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DMPagerSlidingTabStrip(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        String str;
        LinearLayout.LayoutParams layoutParams;
        this.mAdapterObserver = new PagerAdapterObserver();
        this.mPageListener = new PageListener();
        this.mTabReselectedListener = null;
        this.mCurrentPosition = 0;
        this.mCurrentPositionOffset = 0.0f;
        this.mIndicatorHeight = 2;
        this.mUnderlineHeight = 0;
        this.mDividerWidth = 0;
        this.mDividerPadding = 0;
        this.mTabPadding = 12;
        this.mTabTextSize = 14;
        this.mTabTextColor = null;
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.isExpandTabs = false;
        this.isPaddingMiddle = false;
        this.isTabTextAllCaps = true;
        this.mTabTextTypeface = null;
        this.mTabTextTypefaceStyle = 1;
        this.mLastScrollX = 0;
        this.mTabBackgroundResId = R.drawable.dm_psts_background_tab;
        this.selectedTabTextColor = -10066330;
        this.unselectedTabTextColor = -10066330;
        this.selectedPosition = 0;
        this.firstTabGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                View childAt = DMPagerSlidingTabStrip.this.mTabsContainer.getChildAt(0);
                if (Build.VERSION.SDK_INT < 16) {
                    removeGlobalLayoutListenerPreJB();
                } else {
                    removeGlobalLayoutListenerJB();
                }
                if (DMPagerSlidingTabStrip.this.isPaddingMiddle) {
                    DMPagerSlidingTabStrip dMPagerSlidingTabStrip = DMPagerSlidingTabStrip.this;
                    dMPagerSlidingTabStrip.mPaddingLeft = dMPagerSlidingTabStrip.mPaddingRight = (dMPagerSlidingTabStrip.getWidth() / 2) - (childAt.getWidth() / 2);
                }
                DMPagerSlidingTabStrip dMPagerSlidingTabStrip2 = DMPagerSlidingTabStrip.this;
                dMPagerSlidingTabStrip2.setPadding(dMPagerSlidingTabStrip2.mPaddingLeft, DMPagerSlidingTabStrip.this.getPaddingTop(), DMPagerSlidingTabStrip.this.mPaddingRight, DMPagerSlidingTabStrip.this.getPaddingBottom());
                if (DMPagerSlidingTabStrip.this.mScrollOffset == 0) {
                    DMPagerSlidingTabStrip dMPagerSlidingTabStrip3 = DMPagerSlidingTabStrip.this;
                    dMPagerSlidingTabStrip3.mScrollOffset = (dMPagerSlidingTabStrip3.getWidth() / 2) - DMPagerSlidingTabStrip.this.mPaddingLeft;
                }
                DMPagerSlidingTabStrip dMPagerSlidingTabStrip4 = DMPagerSlidingTabStrip.this;
                dMPagerSlidingTabStrip4.mCurrentPosition = dMPagerSlidingTabStrip4.mPager.getCurrentItem();
                DMPagerSlidingTabStrip.this.mCurrentPositionOffset = 0.0f;
                DMPagerSlidingTabStrip dMPagerSlidingTabStrip5 = DMPagerSlidingTabStrip.this;
                dMPagerSlidingTabStrip5.scrollToChild(dMPagerSlidingTabStrip5.mCurrentPosition, 0);
                DMPagerSlidingTabStrip dMPagerSlidingTabStrip6 = DMPagerSlidingTabStrip.this;
                dMPagerSlidingTabStrip6.updateSelection(dMPagerSlidingTabStrip6.mCurrentPosition);
            }

            private void removeGlobalLayoutListenerPreJB() {
                DMPagerSlidingTabStrip.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

            private void removeGlobalLayoutListenerJB() {
                DMPagerSlidingTabStrip.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        };
        setFillViewport(true);
        setWillNotDraw(false);
        LinearLayout linearLayout = new LinearLayout(context);
        this.mTabsContainer = linearLayout;
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(this.mTabsContainer);
        Paint paint = new Paint();
        this.mRectPaint = paint;
        paint.setAntiAlias(true);
        this.mRectPaint.setStyle(Paint.Style.FILL);
        this.selectedTabTextColor = getResources().getColor(R.color.photoselector_common_main_color);
        this.unselectedTabTextColor = getResources().getColor(R.color.photoselector_common_main_unselect_color);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.mScrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.mScrollOffset, displayMetrics);
        this.mIndicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.mIndicatorHeight, displayMetrics);
        this.mUnderlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.mUnderlineHeight, displayMetrics);
        this.mDividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.mDividerPadding, displayMetrics);
        this.mTabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.mTabPadding, displayMetrics);
        this.mDividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.mDividerWidth, displayMetrics);
        this.mTabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.mTabTextSize, displayMetrics);
        Paint paint2 = new Paint();
        this.mDividerPaint = paint2;
        paint2.setAntiAlias(true);
        this.mDividerPaint.setStrokeWidth(this.mDividerWidth);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DMPagerSliderTabStripPixel);
        int color = obtainStyledAttributes.getColor(0, getResources().getColor(R.color.color_default_crop_grid));
        this.mUnderlineColor = color;
        this.mDividerColor = color;
        this.mIndicatorColor = color;
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.DMPagerSliderTabStripPixel_pixel_dimen, 0);
        this.mPaddingLeft = dimensionPixelSize > 0 ? dimensionPixelSize : obtainStyledAttributes.getDimensionPixelSize(R.styleable.DMPagerSliderTabStripPixel_pixel_dimen, 0);
        this.mPaddingRight = dimensionPixelSize <= 0 ? obtainStyledAttributes.getDimensionPixelSize(R.styleable.DMPagerSliderTabStripPixel_pixel_dimen, 0) : dimensionPixelSize;
        obtainStyledAttributes.recycle();
        if (Build.VERSION.SDK_INT >= 21) {
            this.mTabTextTypefaceStyle = 0;
            str = "sans-serif-medium";
        } else {
            str = "sans-serif";
        }
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.DMPagerSlidingTabStrip);
        this.mIndicatorColor = obtainStyledAttributes2.getColor(R.styleable.DMPagerSlidingTabStrip_pstsIndicatorColor, this.mIndicatorColor);
        this.mIndicatorHeight = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.DMPagerSlidingTabStrip_pstsIndicatorHeight, this.mIndicatorHeight);
        this.mUnderlineColor = obtainStyledAttributes2.getColor(R.styleable.DMPagerSlidingTabStrip_pstsUnderlineColor, this.mUnderlineColor);
        this.mUnderlineHeight = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.DMPagerSlidingTabStrip_pstsUnderlineHeight, this.mUnderlineHeight);
        this.mDividerColor = obtainStyledAttributes2.getColor(R.styleable.DMPagerSlidingTabStrip_pstsDividerColor, this.mDividerColor);
        this.mDividerWidth = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.DMPagerSlidingTabStrip_pstsDividerWidth, this.mDividerWidth);
        this.mDividerPadding = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.DMPagerSlidingTabStrip_pstsDividerPadding, this.mDividerPadding);
        this.isExpandTabs = obtainStyledAttributes2.getBoolean(R.styleable.DMPagerSlidingTabStrip_pstsShouldExpand, this.isExpandTabs);
        this.mScrollOffset = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.DMPagerSlidingTabStrip_pstsScrollOffset, this.mScrollOffset);
        this.isPaddingMiddle = obtainStyledAttributes2.getBoolean(R.styleable.DMPagerSlidingTabStrip_pstsPaddingMiddle, this.isPaddingMiddle);
        this.mTabPadding = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.DMPagerSlidingTabStrip_pstsTabPaddingLeftRight, this.mTabPadding);
        this.mTabBackgroundResId = obtainStyledAttributes2.getResourceId(R.styleable.DMPagerSlidingTabStrip_pstsTabBackground, this.mTabBackgroundResId);
        this.mTabTextSize = obtainStyledAttributes2.getDimensionPixelSize(R.styleable.DMPagerSlidingTabStrip_pstsTabTextSize, this.mTabTextSize);
        this.mTabTextColor = obtainStyledAttributes2.hasValue(R.styleable.DMPagerSlidingTabStrip_pstsTabTextColor) ? obtainStyledAttributes2.getColorStateList(R.styleable.DMPagerSlidingTabStrip_pstsTabTextColor) : null;
        this.mTabTextTypefaceStyle = obtainStyledAttributes2.getInt(R.styleable.DMPagerSlidingTabStrip_pstsTabTextStyle, this.mTabTextTypefaceStyle);
        this.isTabTextAllCaps = obtainStyledAttributes2.getBoolean(R.styleable.DMPagerSlidingTabStrip_pstsTabTextAllCaps, this.isTabTextAllCaps);
        int i2 = obtainStyledAttributes2.getInt(R.styleable.DMPagerSlidingTabStrip_pstsTabTextAlpha, DEF_VALUE_TAB_TEXT_ALPHA);
        String string = obtainStyledAttributes2.getString(R.styleable.DMPagerSlidingTabStrip_pstsTabTextFontFamily);
        obtainStyledAttributes2.recycle();
        if (this.mTabTextColor == null) {
            this.mTabTextColor = createColorStateList(color, color, Color.argb(i2, Color.red(color), Color.green(color), Color.blue(color)));
        }
        this.mTabTextTypeface = Typeface.create(string != null ? string : str, this.mTabTextTypefaceStyle);
        setTabsContainerParentViewPaddings();
        if (this.isExpandTabs) {
            layoutParams = new LinearLayout.LayoutParams(0, -1, 1.0f);
        } else {
            layoutParams = new LinearLayout.LayoutParams(-2, -1);
        }
        this.mTabLayoutParams = layoutParams;
    }

    private void setTabsContainerParentViewPaddings() {
        int i = this.mIndicatorHeight;
        int i2 = this.mUnderlineHeight;
        if (i < i2) {
            i = i2;
        }
        setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), i);
    }

    public void setViewPager(ViewPager viewPager) {
        this.mPager = viewPager;
        if (viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        this.isCustomTabs = viewPager.getAdapter() instanceof CustomTabProvider;
        viewPager.setOnPageChangeListener(this.mPageListener);
        viewPager.getAdapter().registerDataSetObserver(this.mAdapterObserver);
        this.mAdapterObserver.setAttached(true);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        View inflate;
        this.mTabsContainer.removeAllViews();
        this.mTabCount = this.mPager.getAdapter().getCount();
        for (int i = 0; i < this.mTabCount; i++) {
            if (this.isCustomTabs) {
                inflate = ((CustomTabProvider) this.mPager.getAdapter()).getCustomTabView(this, i);
            } else {
                inflate = LayoutInflater.from(getContext()).inflate(R.layout.dm_psts_tab, (ViewGroup) this, false);
            }
            addTab(i, this.mPager.getAdapter().getPageTitle(i), inflate);
        }
        updateTabStyles();
    }

    private void addTab(final int i, CharSequence charSequence, View view) {
        TextView textView = (TextView) view.findViewById(R.id.psts_tab_title);
        if (textView != null && charSequence != null) {
            textView.setText(charSequence);
        }
        view.setFocusable(true);
        view.setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (DMPagerSlidingTabStrip.this.mPager.getCurrentItem() != i) {
                    DMPagerSlidingTabStrip.this.unSelect(DMPagerSlidingTabStrip.this.mTabsContainer.getChildAt(DMPagerSlidingTabStrip.this.mPager.getCurrentItem()));
                    DMPagerSlidingTabStrip.this.mPager.setCurrentItem(i);
                } else if (DMPagerSlidingTabStrip.this.mTabReselectedListener != null) {
                    DMPagerSlidingTabStrip.this.mTabReselectedListener.onTabReselected(i);
                }
            }
        });
        this.mTabsContainer.addView(view, i, this.mTabLayoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTabStyles() {
        for (int i = 0; i < this.mTabCount; i++) {
            View childAt = this.mTabsContainer.getChildAt(i);
            childAt.setBackgroundResource(this.mTabBackgroundResId);
            childAt.setPadding(this.mTabPadding, childAt.getPaddingTop(), this.mTabPadding, childAt.getPaddingBottom());
            TextView textView = (TextView) childAt.findViewById(R.id.psts_tab_title);
            if (textView != null) {
                textView.setTextColor(this.mTabTextColor);
                textView.setTypeface(this.mTabTextTypeface, this.mTabTextTypefaceStyle);
                textView.setTextSize(0, this.mTabTextSize);
                if (this.isTabTextAllCaps) {
                    if (Build.VERSION.SDK_INT >= 14) {
                        textView.setAllCaps(true);
                    } else {
                        textView.setText(textView.getText().toString().toUpperCase(getResources().getConfiguration().locale));
                    }
                }
            }
            if (i == this.selectedPosition) {
                textView.setTextColor(this.selectedTabTextColor);
            } else {
                textView.setTextColor(this.unselectedTabTextColor);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollToChild(int i, int i2) {
        if (this.mTabCount == 0) {
            return;
        }
        int left = this.mTabsContainer.getChildAt(i).getLeft() + i2;
        if (i > 0 || i2 > 0) {
            int i3 = left - this.mScrollOffset;
            Pair<Float, Float> indicatorCoordinates = getIndicatorCoordinates();
            left = (int) (i3 + ((indicatorCoordinates.second.floatValue() - indicatorCoordinates.first.floatValue()) / 2.0f));
        }
        if (left != this.mLastScrollX) {
            this.mLastScrollX = left;
            scrollTo(left, 0);
        }
    }

    private Pair<Float, Float> getIndicatorCoordinates() {
        int i;
        View childAt = this.mTabsContainer.getChildAt(this.mCurrentPosition);
        float left = childAt.getLeft();
        float right = childAt.getRight();
        if (this.mCurrentPositionOffset > 0.0f && (i = this.mCurrentPosition) < this.mTabCount - 1) {
            View childAt2 = this.mTabsContainer.getChildAt(i + 1);
            float f = this.mCurrentPositionOffset;
            left = (childAt2.getLeft() * f) + ((1.0f - f) * left);
            right = (childAt2.getRight() * f) + ((1.0f - f) * right);
        }
        return new Pair<>(Float.valueOf(left), Float.valueOf(right));
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int width;
        if (this.isPaddingMiddle || this.mPaddingLeft > 0 || this.mPaddingRight > 0) {
            if (this.isPaddingMiddle) {
                width = getWidth();
            } else {
                width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
            }
            this.mTabsContainer.setMinimumWidth(width);
            setClipToPadding(false);
        }
        if (this.mTabsContainer.getChildCount() > 0) {
            this.mTabsContainer.getChildAt(0).getViewTreeObserver().addOnGlobalLayoutListener(this.firstTabGlobalLayoutListener);
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isInEditMode() || this.mTabCount == 0) {
            return;
        }
        int height = getHeight();
        int i = this.mDividerWidth;
        if (i > 0) {
            this.mDividerPaint.setStrokeWidth(i);
            this.mDividerPaint.setColor(this.mDividerColor);
            for (int i2 = 0; i2 < this.mTabCount - 1; i2++) {
                View childAt = this.mTabsContainer.getChildAt(i2);
                canvas.drawLine(childAt.getRight(), this.mDividerPadding, childAt.getRight(), height - this.mDividerPadding, this.mDividerPaint);
            }
        }
        if (this.mUnderlineHeight > 0) {
            this.mRectPaint.setColor(this.mUnderlineColor);
            canvas.drawRect(this.mPaddingLeft, height - this.mUnderlineHeight, this.mTabsContainer.getWidth() + this.mPaddingRight, height, this.mRectPaint);
        }
        if (this.mIndicatorHeight > 0) {
            this.mRectPaint.setColor(this.mIndicatorColor);
            Pair<Float, Float> indicatorCoordinates = getIndicatorCoordinates();
            canvas.drawRect(indicatorCoordinates.first.floatValue() + this.mPaddingLeft, height - this.mIndicatorHeight, indicatorCoordinates.second.floatValue() + this.mPaddingLeft, height, this.mRectPaint);
        }
    }

    public void setOnTabReselectedListener(OnTabReselectedListener onTabReselectedListener) {
        this.mTabReselectedListener = onTabReselectedListener;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mDelegatePageListener = onPageChangeListener;
    }

    /* loaded from: classes3.dex */
    private class PageListener implements ViewPager.OnPageChangeListener {
        private PageListener() {
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
            DMPagerSlidingTabStrip.this.mCurrentPosition = i;
            DMPagerSlidingTabStrip.this.mCurrentPositionOffset = f;
            DMPagerSlidingTabStrip.this.scrollToChild(i, DMPagerSlidingTabStrip.this.mTabCount > 0 ? (int) (DMPagerSlidingTabStrip.this.mTabsContainer.getChildAt(i).getWidth() * f) : 0);
            DMPagerSlidingTabStrip.this.invalidate();
            if (DMPagerSlidingTabStrip.this.mDelegatePageListener != null) {
                DMPagerSlidingTabStrip.this.mDelegatePageListener.onPageScrolled(i, f, i2);
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
            if (i == 0) {
                DMPagerSlidingTabStrip dMPagerSlidingTabStrip = DMPagerSlidingTabStrip.this;
                dMPagerSlidingTabStrip.scrollToChild(dMPagerSlidingTabStrip.mPager.getCurrentItem(), 0);
            }
            DMPagerSlidingTabStrip.this.select(DMPagerSlidingTabStrip.this.mTabsContainer.getChildAt(DMPagerSlidingTabStrip.this.mPager.getCurrentItem()));
            if (DMPagerSlidingTabStrip.this.mPager.getCurrentItem() - 1 >= 0) {
                DMPagerSlidingTabStrip.this.unSelect(DMPagerSlidingTabStrip.this.mTabsContainer.getChildAt(DMPagerSlidingTabStrip.this.mPager.getCurrentItem() - 1));
            }
            if (DMPagerSlidingTabStrip.this.mPager.getCurrentItem() + 1 <= DMPagerSlidingTabStrip.this.mPager.getAdapter().getCount() - 1) {
                DMPagerSlidingTabStrip.this.unSelect(DMPagerSlidingTabStrip.this.mTabsContainer.getChildAt(DMPagerSlidingTabStrip.this.mPager.getCurrentItem() + 1));
            }
            if (DMPagerSlidingTabStrip.this.mDelegatePageListener != null) {
                DMPagerSlidingTabStrip.this.mDelegatePageListener.onPageScrollStateChanged(i);
            }
        }

        @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            DMPagerSlidingTabStrip.this.updateSelection(i);
            DMPagerSlidingTabStrip.this.selectedPosition = i;
            DMPagerSlidingTabStrip.this.updateTabStyles();
            if (DMPagerSlidingTabStrip.this.mDelegatePageListener != null) {
                DMPagerSlidingTabStrip.this.mDelegatePageListener.onPageSelected(i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelection(int i) {
        int i2 = 0;
        while (i2 < this.mTabCount) {
            View childAt = this.mTabsContainer.getChildAt(i2);
            if (i2 == i) {
                select(childAt);
            } else {
                unSelect(childAt);
            }
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unSelect(View view) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.psts_tab_title);
            if (textView != null) {
                textView.setSelected(false);
            }
            if (this.isCustomTabs) {
                ((CustomTabProvider) this.mPager.getAdapter()).tabUnselected(view);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void select(View view) {
        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.psts_tab_title);
            if (textView != null) {
                textView.setSelected(true);
            }
            if (this.isCustomTabs) {
                ((CustomTabProvider) this.mPager.getAdapter()).tabSelected(view);
            }
        }
    }

    /* loaded from: classes3.dex */
    private class PagerAdapterObserver extends DataSetObserver {
        private boolean attached;

        private PagerAdapterObserver() {
            this.attached = false;
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            DMPagerSlidingTabStrip.this.notifyDataSetChanged();
        }

        public void setAttached(boolean z) {
            this.attached = z;
        }

        public boolean isAttached() {
            return this.attached;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mPager == null || this.mAdapterObserver.isAttached()) {
            return;
        }
        this.mPager.getAdapter().registerDataSetObserver(this.mAdapterObserver);
        this.mAdapterObserver.setAttached(true);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mPager == null || !this.mAdapterObserver.isAttached()) {
            return;
        }
        this.mPager.getAdapter().unregisterDataSetObserver(this.mAdapterObserver);
        this.mAdapterObserver.setAttached(false);
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        int i = savedState.currentPosition;
        this.mCurrentPosition = i;
        if (i != 0 && this.mTabsContainer.getChildCount() > 0) {
            unSelect(this.mTabsContainer.getChildAt(0));
            select(this.mTabsContainer.getChildAt(this.mCurrentPosition));
        }
        requestLayout();
    }

    @Override // android.widget.HorizontalScrollView, android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPosition = this.mCurrentPosition;
        return savedState;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() { // from class: com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int currentPosition;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.currentPosition = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.currentPosition);
        }
    }

    public int getIndicatorColor() {
        return this.mIndicatorColor;
    }

    public int getIndicatorHeight() {
        return this.mIndicatorHeight;
    }

    public int getUnderlineColor() {
        return this.mUnderlineColor;
    }

    public int getDividerColor() {
        return this.mDividerColor;
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    public int getUnderlineHeight() {
        return this.mUnderlineHeight;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    public int getScrollOffset() {
        return this.mScrollOffset;
    }

    public boolean getShouldExpand() {
        return this.isExpandTabs;
    }

    public int getTextSize() {
        return this.mTabTextSize;
    }

    public boolean isTextAllCaps() {
        return this.isTabTextAllCaps;
    }

    public ColorStateList getTextColor() {
        return this.mTabTextColor;
    }

    public int getTabBackground() {
        return this.mTabBackgroundResId;
    }

    public int getTabPaddingLeftRight() {
        return this.mTabPadding;
    }

    public void setIndicatorColor(int i) {
        this.mIndicatorColor = i;
        invalidate();
    }

    public void setIndicatorColorResource(int i) {
        this.mIndicatorColor = getResources().getColor(i);
        invalidate();
    }

    public void setIndicatorHeight(int i) {
        this.mIndicatorHeight = i;
        invalidate();
    }

    public void setUnderlineColor(int i) {
        this.mUnderlineColor = i;
        invalidate();
    }

    public void setUnderlineColorResource(int i) {
        this.mUnderlineColor = getResources().getColor(i);
        invalidate();
    }

    public void setDividerColor(int i) {
        this.mDividerColor = i;
        invalidate();
    }

    public void setDividerColorResource(int i) {
        this.mDividerColor = getResources().getColor(i);
        invalidate();
    }

    public void setDividerWidth(int i) {
        this.mDividerWidth = i;
        invalidate();
    }

    public void setUnderlineHeight(int i) {
        this.mUnderlineHeight = i;
        invalidate();
    }

    public void setDividerPadding(int i) {
        this.mDividerPadding = i;
        invalidate();
    }

    public void setScrollOffset(int i) {
        this.mScrollOffset = i;
        invalidate();
    }

    public void setShouldExpand(boolean z) {
        this.isExpandTabs = z;
        if (this.mPager != null) {
            requestLayout();
        }
    }

    public void setAllCaps(boolean z) {
        this.isTabTextAllCaps = z;
    }

    public void setTextSize(int i) {
        this.mTabTextSize = i;
        updateTabStyles();
    }

    public void setTextColorResource(int i) {
        setTextColor(getResources().getColor(i));
    }

    public void setTextColor(int i) {
        setTextColor(createColorStateList(i));
    }

    public void setTextColorStateListResource(int i) {
        setTextColor(getResources().getColorStateList(i));
    }

    public void setTextColor(ColorStateList colorStateList) {
        this.mTabTextColor = colorStateList;
        updateTabStyles();
    }

    private ColorStateList createColorStateList(int i) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{i});
    }

    private ColorStateList createColorStateList(int i, int i2, int i3) {
        return new ColorStateList(new int[][]{new int[]{16842919}, new int[]{16842913}, new int[0]}, new int[]{i, i2, i3});
    }

    public void setTypeface(Typeface typeface, int i) {
        this.mTabTextTypeface = typeface;
        this.mTabTextTypefaceStyle = i;
        updateTabStyles();
    }

    public void setTabBackground(int i) {
        this.mTabBackgroundResId = i;
    }

    public void setTabPaddingLeftRight(int i) {
        this.mTabPadding = i;
        updateTabStyles();
    }
}
