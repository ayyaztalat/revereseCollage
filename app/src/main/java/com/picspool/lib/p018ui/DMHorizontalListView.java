package com.picspool.lib.p018ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import androidx.core.view.ViewCompat;
import androidx.core.widget.EdgeEffectCompat;

import com.sky.testproject.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/* renamed from: com.picspool.lib.ui.DMHorizontalListView */
/* loaded from: classes3.dex */
public class DMHorizontalListView extends AdapterView<ListAdapter> {
    private static final String BUNDLE_ID_CURRENT_X = "BUNDLE_ID_CURRENT_X";
    private static final String BUNDLE_ID_PARENT_STATE = "BUNDLE_ID_PARENT_STATE";
    private static final float FLING_DEFAULT_ABSORB_VELOCITY = 30.0f;
    private static final float FLING_FRICTION = 0.009f;
    private static final int INSERT_AT_END_OF_LIST = -1;
    private static final int INSERT_AT_START_OF_LIST = 0;
    protected ListAdapter mAdapter;
    private DataSetObserver mAdapterDataObserver;
    private boolean mBlockTouchAction;
    private OnScrollStateChangedListener.ScrollState mCurrentScrollState;
    protected int mCurrentX;
    private int mCurrentlySelectedAdapterIndex;
    private boolean mDataChanged;
    private Runnable mDelayedLayout;
    private int mDisplayOffset;
    private Drawable mDivider;
    private int mDividerWidth;
    private EdgeEffectCompat mEdgeGlowLeft;
    private EdgeEffectCompat mEdgeGlowRight;
    protected Scroller mFlingTracker;
    private GestureDetector mGestureDetector;
    private final GestureListener mGestureListener;
    private boolean mHasNotifiedRunningLowOnData;
    private int mHeightMeasureSpec;
    private boolean mIsParentVerticiallyScrollableViewDisallowingInterceptTouchEvent;
    private int mLeftViewAdapterIndex;
    private int mMaxX;
    protected int mNextX;
    private OnClickListener mOnClickListener;
    private OnScrollStateChangedListener mOnScrollStateChangedListener;
    private Rect mRect;
    private List<Queue<View>> mRemovedViewsCache;
    private Integer mRestoreX;
    private int mRightViewAdapterIndex;
    private RunningOutOfDataListener mRunningOutOfDataListener;
    private int mRunningOutOfDataThreshold;
    private View mViewBeingTouched;

    /* renamed from: com.picspool.lib.ui.DMHorizontalListView$OnScrollStateChangedListener */
    /* loaded from: classes3.dex */
    public interface OnScrollStateChangedListener {

        /* renamed from: com.picspool.lib.ui.DMHorizontalListView$OnScrollStateChangedListener$ScrollState */
        /* loaded from: classes3.dex */
        public enum ScrollState {
            SCROLL_STATE_IDLE,
            SCROLL_STATE_TOUCH_SCROLL,
            SCROLL_STATE_FLING
        }

        void onScrollStateChanged(ScrollState scrollState);
    }

    /* renamed from: com.picspool.lib.ui.DMHorizontalListView$RunningOutOfDataListener */
    /* loaded from: classes3.dex */
    public interface RunningOutOfDataListener {
        void onRunningOutOfData();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchSetPressed(boolean z) {
    }

    public DMHorizontalListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFlingTracker = new Scroller(getContext());
        this.mGestureListener = new GestureListener();
        this.mRemovedViewsCache = new ArrayList();
        this.mDataChanged = false;
        this.mRect = new Rect();
        this.mViewBeingTouched = null;
        this.mDividerWidth = 0;
        this.mDivider = null;
        this.mRestoreX = null;
        this.mMaxX = Integer.MAX_VALUE;
        this.mRunningOutOfDataListener = null;
        this.mRunningOutOfDataThreshold = 0;
        this.mHasNotifiedRunningLowOnData = false;
        this.mOnScrollStateChangedListener = null;
        this.mCurrentScrollState = OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE;
        this.mBlockTouchAction = false;
        this.mIsParentVerticiallyScrollableViewDisallowingInterceptTouchEvent = false;
        this.mAdapterDataObserver = new DataSetObserver() { // from class: com.picspool.lib.ui.DMHorizontalListView.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                DMHorizontalListView.this.mDataChanged = true;
                DMHorizontalListView.this.mHasNotifiedRunningLowOnData = false;
                DMHorizontalListView.this.unpressTouchedChild();
                DMHorizontalListView.this.invalidate();
                DMHorizontalListView.this.requestLayout();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                DMHorizontalListView.this.mHasNotifiedRunningLowOnData = false;
                DMHorizontalListView.this.unpressTouchedChild();
                DMHorizontalListView.this.reset();
                DMHorizontalListView.this.invalidate();
                DMHorizontalListView.this.requestLayout();
            }
        };
        this.mDelayedLayout = new Runnable() { // from class: com.picspool.lib.ui.DMHorizontalListView.3
            @Override // java.lang.Runnable
            public void run() {
                DMHorizontalListView.this.requestLayout();
            }
        };
        this.mEdgeGlowLeft = new EdgeEffectCompat(context);
        this.mEdgeGlowRight = new EdgeEffectCompat(context);
        this.mGestureDetector = new GestureDetector(context, this.mGestureListener);
        bindGestureDetector();
        initView();
        retrieveXmlConfiguration(context, attributeSet);
        setWillNotDraw(false);
        if (Build.VERSION.SDK_INT >= 11) {
            HoneycombPlus.setFriction(this.mFlingTracker, FLING_FRICTION);
        }
    }

    private void bindGestureDetector() {
        setOnTouchListener(new OnTouchListener() { // from class: com.picspool.lib.ui.DMHorizontalListView.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return DMHorizontalListView.this.mGestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestParentListViewToNotInterceptTouchEvents(Boolean bool) {
        if (this.mIsParentVerticiallyScrollableViewDisallowingInterceptTouchEvent != bool.booleanValue()) {
            for (View view = this; view.getParent() instanceof View; view = (View) view.getParent()) {
                if ((view.getParent() instanceof ListView) || (view.getParent() instanceof ScrollView)) {
                    view.getParent().requestDisallowInterceptTouchEvent(bool.booleanValue());
                    this.mIsParentVerticiallyScrollableViewDisallowingInterceptTouchEvent = bool.booleanValue();
                    return;
                }
            }
        }
    }

    private void retrieveXmlConfiguration(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
//            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SYSUtil_HorizontalListView);
//            Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.SYSUtil_HorizontalListView_android_divider);
//            if (drawable != null) {
//                setDivider(drawable);
//            }
//            int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.SYSUtil_HorizontalListView_sysutil_dividerWidth, 0);
//            if (dimensionPixelSize != 0) {
//                setDividerWidth(dimensionPixelSize);
//            }
//            obtainStyledAttributes.recycle();
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_ID_PARENT_STATE, super.onSaveInstanceState());
        bundle.putInt(BUNDLE_ID_CURRENT_X, this.mCurrentX);
        return bundle;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.mRestoreX = Integer.valueOf(bundle.getInt(BUNDLE_ID_CURRENT_X));
            super.onRestoreInstanceState(bundle.getParcelable(BUNDLE_ID_PARENT_STATE));
        }
    }

    public void setDivider(Drawable drawable) {
        this.mDivider = drawable;
        if (drawable != null) {
            setDividerWidth(drawable.getIntrinsicWidth());
        } else {
            setDividerWidth(0);
        }
    }

    public void setDividerWidth(int i) {
        this.mDividerWidth = i;
        requestLayout();
        invalidate();
    }

    private void initView() {
        this.mLeftViewAdapterIndex = -1;
        this.mRightViewAdapterIndex = -1;
        this.mDisplayOffset = 0;
        this.mCurrentX = 0;
        this.mNextX = 0;
        this.mMaxX = Integer.MAX_VALUE;
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reset() {
        initView();
        removeAllViewsInLayout();
        requestLayout();
    }

    @Override // android.widget.AdapterView
    public void setSelection(int i) {
        this.mCurrentlySelectedAdapterIndex = i;
    }

    @Override // android.widget.AdapterView
    public View getSelectedView() {
        return getChild(this.mCurrentlySelectedAdapterIndex);
    }

    @Override // android.widget.AdapterView
    public void setAdapter(ListAdapter listAdapter) {
        ListAdapter listAdapter2 = this.mAdapter;
        if (listAdapter2 != null) {
            listAdapter2.unregisterDataSetObserver(this.mAdapterDataObserver);
        }
        if (listAdapter != null) {
            this.mHasNotifiedRunningLowOnData = false;
            this.mAdapter = listAdapter;
            listAdapter.registerDataSetObserver(this.mAdapterDataObserver);
        }
        initializeRecycledViewCache(this.mAdapter.getViewTypeCount());
        reset();
    }

    @Override // android.widget.AdapterView
    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    private void initializeRecycledViewCache(int i) {
        this.mRemovedViewsCache.clear();
        for (int i2 = 0; i2 < i; i2++) {
            this.mRemovedViewsCache.add(new LinkedList());
        }
    }

    private View getRecycledView(int i) {
        int itemViewType = this.mAdapter.getItemViewType(i);
        if (isItemViewTypeValid(itemViewType)) {
            return this.mRemovedViewsCache.get(itemViewType).poll();
        }
        return null;
    }

    private void recycleView(int i, View view) {
        int itemViewType = this.mAdapter.getItemViewType(i);
        if (isItemViewTypeValid(itemViewType)) {
            this.mRemovedViewsCache.get(itemViewType).offer(view);
        }
    }

    private boolean isItemViewTypeValid(int i) {
        return i < this.mRemovedViewsCache.size();
    }

    private void addAndMeasureChild(View view, int i) {
        addViewInLayout(view, i, getLayoutParams(view), true);
        measureChild(view);
    }

    private void measureChild(View view) {
        int makeMeasureSpec;
        LayoutParams layoutParams = getLayoutParams(view);
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, getPaddingTop() + getPaddingBottom(), layoutParams.height);
        if (layoutParams.width > 0) {
            makeMeasureSpec = MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824);
        } else {
            makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(makeMeasureSpec, childMeasureSpec);
    }

    private LayoutParams getLayoutParams(View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        return layoutParams == null ? new LayoutParams(-2, -1) : layoutParams;
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mAdapter == null) {
            return;
        }
        invalidate();
        if (this.mDataChanged) {
            int i5 = this.mCurrentX;
            initView();
            removeAllViewsInLayout();
            this.mNextX = i5;
            this.mDataChanged = false;
        }
        Integer num = this.mRestoreX;
        if (num != null) {
            this.mNextX = num.intValue();
            this.mRestoreX = null;
        }
        if (this.mFlingTracker.computeScrollOffset()) {
            this.mNextX = this.mFlingTracker.getCurrX();
        }
        int i6 = this.mNextX;
        if (i6 < 0) {
            this.mNextX = 0;
            if (this.mEdgeGlowLeft.isFinished()) {
                this.mEdgeGlowLeft.onAbsorb((int) determineFlingAbsorbVelocity());
            }
            this.mFlingTracker.forceFinished(true);
            setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
        } else {
            int i7 = this.mMaxX;
            if (i6 > i7) {
                this.mNextX = i7;
                if (this.mEdgeGlowRight.isFinished()) {
                    this.mEdgeGlowRight.onAbsorb((int) determineFlingAbsorbVelocity());
                }
                this.mFlingTracker.forceFinished(true);
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }
        }
        int i8 = this.mCurrentX - this.mNextX;
        removeNonVisibleChildren(i8);
        fillList(i8);
        positionChildren(i8);
        this.mCurrentX = this.mNextX;
        if (determineMaxX()) {
            onLayout(z, i, i2, i3, i4);
        } else if (this.mFlingTracker.isFinished()) {
            if (this.mCurrentScrollState == OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING) {
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }
        } else {
            ViewCompat.postOnAnimation(this, this.mDelayedLayout);
        }
    }

    @Override // android.view.View
    protected float getLeftFadingEdgeStrength() {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        int i = this.mCurrentX;
        if (i == 0) {
            return 0.0f;
        }
        if (i < horizontalFadingEdgeLength) {
            return i / horizontalFadingEdgeLength;
        }
        return 1.0f;
    }

    @Override // android.view.View
    protected float getRightFadingEdgeStrength() {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        int i = this.mCurrentX;
        int i2 = this.mMaxX;
        if (i == i2) {
            return 0.0f;
        }
        if (i2 - i < horizontalFadingEdgeLength) {
            return (i2 - i) / horizontalFadingEdgeLength;
        }
        return 1.0f;
    }

    private float determineFlingAbsorbVelocity() {
        return Build.VERSION.SDK_INT >= 14 ? IceCreamSandwichPlus.getCurrVelocity(this.mFlingTracker) : FLING_DEFAULT_ABSORB_VELOCITY;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mHeightMeasureSpec = i2;
    }

    private boolean determineMaxX() {
        View rightmostChild;
        if (isLastItemInAdapter(this.mRightViewAdapterIndex) && (rightmostChild = getRightmostChild()) != null) {
            int i = this.mMaxX;
            int right = (this.mCurrentX + (rightmostChild.getRight() - getPaddingLeft())) - getRenderWidth();
            this.mMaxX = right;
            if (right < 0) {
                this.mMaxX = 0;
            }
            if (this.mMaxX != i) {
                return true;
            }
        }
        return false;
    }

    private void fillList(int i) {
        View rightmostChild = getRightmostChild();
        fillListRight(rightmostChild != null ? rightmostChild.getRight() : 0, i);
        View leftmostChild = getLeftmostChild();
        fillListLeft(leftmostChild != null ? leftmostChild.getLeft() : 0, i);
    }

    private void removeNonVisibleChildren(int i) {
        View leftmostChild = getLeftmostChild();
        while (leftmostChild != null && leftmostChild.getRight() + i <= 0) {
            this.mDisplayOffset += isLastItemInAdapter(this.mLeftViewAdapterIndex) ? leftmostChild.getMeasuredWidth() : this.mDividerWidth + leftmostChild.getMeasuredWidth();
            recycleView(this.mLeftViewAdapterIndex, leftmostChild);
            removeViewInLayout(leftmostChild);
            this.mLeftViewAdapterIndex++;
            leftmostChild = getLeftmostChild();
        }
        View rightmostChild = getRightmostChild();
        while (rightmostChild != null && rightmostChild.getLeft() + i >= getWidth()) {
            recycleView(this.mRightViewAdapterIndex, rightmostChild);
            removeViewInLayout(rightmostChild);
            this.mRightViewAdapterIndex--;
            rightmostChild = getRightmostChild();
        }
    }

    private void fillListRight(int i, int i2) {
        while (i + i2 + this.mDividerWidth < getWidth() && this.mRightViewAdapterIndex + 1 < this.mAdapter.getCount()) {
            int i3 = this.mRightViewAdapterIndex + 1;
            this.mRightViewAdapterIndex = i3;
            if (this.mLeftViewAdapterIndex < 0) {
                this.mLeftViewAdapterIndex = i3;
            }
            ListAdapter listAdapter = this.mAdapter;
            int i4 = this.mRightViewAdapterIndex;
            View view = listAdapter.getView(i4, getRecycledView(i4), this);
            addAndMeasureChild(view, -1);
            i += (this.mRightViewAdapterIndex == 0 ? 0 : this.mDividerWidth) + view.getMeasuredWidth();
            determineIfLowOnData();
        }
    }

    private void fillListLeft(int i, int i2) {
        int i3;
        while ((i + i2) - this.mDividerWidth > 0 && (i3 = this.mLeftViewAdapterIndex) >= 1) {
            int i4 = i3 - 1;
            this.mLeftViewAdapterIndex = i4;
            View view = this.mAdapter.getView(i4, getRecycledView(i4), this);
            addAndMeasureChild(view, 0);
            i -= this.mLeftViewAdapterIndex == 0 ? view.getMeasuredWidth() : this.mDividerWidth + view.getMeasuredWidth();
            this.mDisplayOffset -= i + i2 == 0 ? view.getMeasuredWidth() : view.getMeasuredWidth() + this.mDividerWidth;
        }
    }

    private void positionChildren(int i) {
        int childCount = getChildCount();
        if (childCount > 0) {
            int i2 = this.mDisplayOffset + i;
            this.mDisplayOffset = i2;
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                int paddingLeft = getPaddingLeft() + i2;
                int paddingTop = getPaddingTop();
                childAt.layout(paddingLeft, paddingTop, childAt.getMeasuredWidth() + paddingLeft, childAt.getMeasuredHeight() + paddingTop);
                i2 += childAt.getMeasuredWidth() + this.mDividerWidth;
            }
        }
    }

    private View getLeftmostChild() {
        return getChildAt(0);
    }

    private View getRightmostChild() {
        return getChildAt(getChildCount() - 1);
    }

    private View getChild(int i) {
        int i2 = this.mLeftViewAdapterIndex;
        if (i < i2 || i > this.mRightViewAdapterIndex) {
            return null;
        }
        getChildAt(i - i2);
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getChildIndex(int i, int i2) {
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            getChildAt(i3).getHitRect(this.mRect);
            if (this.mRect.contains(i, i2)) {
                return i3;
            }
        }
        return -1;
    }

    private boolean isLastItemInAdapter(int i) {
        return i == this.mAdapter.getCount() - 1;
    }

    private int getRenderHeight() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    private int getRenderWidth() {
        return (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void scrollTo(int i) {
        Scroller scroller = this.mFlingTracker;
        int i2 = this.mNextX;
        scroller.startScroll(i2, 0, i - i2, 0);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING);
        requestLayout();
    }

    private void drawEdgeGlow(Canvas canvas) {
        EdgeEffectCompat edgeEffectCompat = this.mEdgeGlowLeft;
        if (edgeEffectCompat != null && !edgeEffectCompat.isFinished() && isEdgeGlowEnabled()) {
            int save = canvas.save();
            int height = getHeight();
            canvas.rotate(-90.0f, 0.0f, 0.0f);
            canvas.translate((-height) + getPaddingBottom(), 0.0f);
            this.mEdgeGlowLeft.setSize(getRenderHeight(), getRenderWidth());
            if (this.mEdgeGlowLeft.draw(canvas)) {
                invalidate();
            }
            canvas.restoreToCount(save);
            return;
        }
        EdgeEffectCompat edgeEffectCompat2 = this.mEdgeGlowRight;
        if (edgeEffectCompat2 == null || edgeEffectCompat2.isFinished() || !isEdgeGlowEnabled()) {
            return;
        }
        int save2 = canvas.save();
        int width = getWidth();
        canvas.rotate(90.0f, 0.0f, 0.0f);
        canvas.translate(getPaddingTop(), -width);
        this.mEdgeGlowRight.setSize(getRenderHeight(), getRenderWidth());
        if (this.mEdgeGlowRight.draw(canvas)) {
            invalidate();
        }
        canvas.restoreToCount(save2);
    }

    private void drawDividers(Canvas canvas) {
        int childCount = getChildCount();
        Rect rect = this.mRect;
        rect.top = getPaddingTop();
        Rect rect2 = this.mRect;
        rect2.bottom = rect2.top + getRenderHeight();
        for (int i = 0; i < childCount; i++) {
            if (i != childCount - 1 || !isLastItemInAdapter(this.mRightViewAdapterIndex)) {
                View childAt = getChildAt(i);
                rect.left = childAt.getRight();
                rect.right = childAt.getRight() + this.mDividerWidth;
                if (rect.left < getPaddingLeft()) {
                    rect.left = getPaddingLeft();
                }
                if (rect.right > getWidth() - getPaddingRight()) {
                    rect.right = getWidth() - getPaddingRight();
                }
                drawDivider(canvas, rect);
                if (i == 0 && childAt.getLeft() > getPaddingLeft()) {
                    rect.left = getPaddingLeft();
                    rect.right = childAt.getLeft();
                    drawDivider(canvas, rect);
                }
            }
        }
    }

    private void drawDivider(Canvas canvas, Rect rect) {
        Drawable drawable = this.mDivider;
        if (drawable != null) {
            drawable.setBounds(rect);
            this.mDivider.draw(canvas);
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDividers(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawEdgeGlow(canvas);
    }

    protected boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.mFlingTracker.fling(this.mNextX, 0, (int) (-f), 0, 0, this.mMaxX, 0, 0);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING);
        requestLayout();
        return true;
    }

    protected boolean onDown(MotionEvent motionEvent) {
        int childIndex;
        this.mBlockTouchAction = !this.mFlingTracker.isFinished();
        this.mFlingTracker.forceFinished(true);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
        unpressTouchedChild();
        if (!this.mBlockTouchAction && (childIndex = getChildIndex((int) motionEvent.getX(), (int) motionEvent.getY())) >= 0) {
            View childAt = getChildAt(childIndex);
            this.mViewBeingTouched = childAt;
            if (childAt != null) {
                childAt.setPressed(true);
                refreshDrawableState();
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unpressTouchedChild() {
        View view = this.mViewBeingTouched;
        if (view != null) {
            view.setPressed(false);
            refreshDrawableState();
            this.mViewBeingTouched = null;
        }
    }

    /* renamed from: com.picspool.lib.ui.DMHorizontalListView$GestureListener */
    /* loaded from: classes3.dex */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private GestureListener() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return DMHorizontalListView.this.onDown(motionEvent);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return DMHorizontalListView.this.onFling(motionEvent, motionEvent2, f, f2);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            DMHorizontalListView.this.requestParentListViewToNotInterceptTouchEvents(true);
            DMHorizontalListView.this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_TOUCH_SCROLL);
            DMHorizontalListView.this.unpressTouchedChild();
            DMHorizontalListView.this.mNextX += (int) f;
            DMHorizontalListView.this.updateOverscrollAnimation(Math.round(f));
            DMHorizontalListView.this.requestLayout();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            DMHorizontalListView.this.unpressTouchedChild();
            OnItemClickListener onItemClickListener = DMHorizontalListView.this.getOnItemClickListener();
            int childIndex = DMHorizontalListView.this.getChildIndex((int) motionEvent.getX(), (int) motionEvent.getY());
            if (childIndex >= 0 && !DMHorizontalListView.this.mBlockTouchAction) {
                View childAt = DMHorizontalListView.this.getChildAt(childIndex);
                int i = DMHorizontalListView.this.mLeftViewAdapterIndex + childIndex;
                if (onItemClickListener != null) {
                    DMHorizontalListView dMHorizontalListView = DMHorizontalListView.this;
                    onItemClickListener.onItemClick(dMHorizontalListView, childAt, i, dMHorizontalListView.mAdapter.getItemId(i));
                    return true;
                }
            }
            if (DMHorizontalListView.this.mOnClickListener == null || DMHorizontalListView.this.mBlockTouchAction) {
                return false;
            }
            DMHorizontalListView.this.mOnClickListener.onClick(DMHorizontalListView.this);
            return false;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            DMHorizontalListView.this.unpressTouchedChild();
            int childIndex = DMHorizontalListView.this.getChildIndex((int) motionEvent.getX(), (int) motionEvent.getY());
            if (childIndex < 0 || DMHorizontalListView.this.mBlockTouchAction) {
                return;
            }
            View childAt = DMHorizontalListView.this.getChildAt(childIndex);
            OnItemLongClickListener onItemLongClickListener = DMHorizontalListView.this.getOnItemLongClickListener();
            if (onItemLongClickListener != null) {
                int i = DMHorizontalListView.this.mLeftViewAdapterIndex + childIndex;
                DMHorizontalListView dMHorizontalListView = DMHorizontalListView.this;
                if (onItemLongClickListener.onItemLongClick(dMHorizontalListView, childAt, i, dMHorizontalListView.mAdapter.getItemId(i))) {
                    DMHorizontalListView.this.performHapticFeedback(0);
                }
            }
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            Scroller scroller = this.mFlingTracker;
            if (scroller == null || scroller.isFinished()) {
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }
            requestParentListViewToNotInterceptTouchEvents(false);
            releaseEdgeGlow();
        } else if (motionEvent.getAction() == 3) {
            unpressTouchedChild();
            releaseEdgeGlow();
            requestParentListViewToNotInterceptTouchEvents(false);
        }
        return super.onTouchEvent(motionEvent);
    }

    private void releaseEdgeGlow() {
        EdgeEffectCompat edgeEffectCompat = this.mEdgeGlowLeft;
        if (edgeEffectCompat != null) {
            edgeEffectCompat.onRelease();
        }
        EdgeEffectCompat edgeEffectCompat2 = this.mEdgeGlowRight;
        if (edgeEffectCompat2 != null) {
            edgeEffectCompat2.onRelease();
        }
    }

    public void setRunningOutOfDataListener(RunningOutOfDataListener runningOutOfDataListener, int i) {
        this.mRunningOutOfDataListener = runningOutOfDataListener;
        this.mRunningOutOfDataThreshold = i;
    }

    private void determineIfLowOnData() {
        ListAdapter listAdapter;
        if (this.mRunningOutOfDataListener == null || (listAdapter = this.mAdapter) == null || listAdapter.getCount() - (this.mRightViewAdapterIndex + 1) >= this.mRunningOutOfDataThreshold || this.mHasNotifiedRunningLowOnData) {
            return;
        }
        this.mHasNotifiedRunningLowOnData = true;
        this.mRunningOutOfDataListener.onRunningOutOfData();
    }

    @Override // android.widget.AdapterView, android.view.View
    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public void setOnScrollStateChangedListener(OnScrollStateChangedListener onScrollStateChangedListener) {
        this.mOnScrollStateChangedListener = onScrollStateChangedListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentScrollState(OnScrollStateChangedListener.ScrollState scrollState) {
        OnScrollStateChangedListener onScrollStateChangedListener;
        if (this.mCurrentScrollState != scrollState && (onScrollStateChangedListener = this.mOnScrollStateChangedListener) != null) {
            onScrollStateChangedListener.onScrollStateChanged(scrollState);
        }
        this.mCurrentScrollState = scrollState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOverscrollAnimation(int i) {
        if (this.mEdgeGlowLeft == null || this.mEdgeGlowRight == null) {
            return;
        }
        int i2 = this.mCurrentX + i;
        Scroller scroller = this.mFlingTracker;
        if (scroller == null || scroller.isFinished()) {
            if (i2 < 0) {
                this.mEdgeGlowLeft.onPull(Math.abs(i) / getRenderWidth());
                if (this.mEdgeGlowRight.isFinished()) {
                    return;
                }
                this.mEdgeGlowRight.onRelease();
            } else if (i2 > this.mMaxX) {
                this.mEdgeGlowRight.onPull(Math.abs(i) / getRenderWidth());
                if (this.mEdgeGlowLeft.isFinished()) {
                    return;
                }
                this.mEdgeGlowLeft.onRelease();
            }
        }
    }

    private boolean isEdgeGlowEnabled() {
        ListAdapter listAdapter = this.mAdapter;
        return (listAdapter == null || listAdapter.isEmpty() || this.mMaxX <= 0) ? false : true;
    }

    /* renamed from: com.picspool.lib.ui.DMHorizontalListView$HoneycombPlus */
    /* loaded from: classes3.dex */
    private static final class HoneycombPlus {
        private HoneycombPlus() {
        }

        static {
            if (Build.VERSION.SDK_INT < 11) {
                throw new RuntimeException("Should not get to HoneycombPlus class unless sdk is >= 11!");
            }
        }

        public static void setFriction(Scroller scroller, float f) {
            if (scroller != null) {
                scroller.setFriction(f);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.picspool.lib.ui.DMHorizontalListView$IceCreamSandwichPlus */
    /* loaded from: classes3.dex */
    public static final class IceCreamSandwichPlus {
        private IceCreamSandwichPlus() {
        }

        static {
            if (Build.VERSION.SDK_INT < 14) {
                throw new RuntimeException("Should not get to IceCreamSandwichPlus class unless sdk is >= 14!");
            }
        }

        public static float getCurrVelocity(Scroller scroller) {
            return scroller.getCurrVelocity();
        }
    }
}
