package com.baiwang.libuiinstalens.filter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.sky.testproject.R;
import com.google.firebase.messaging.ServiceStarter;


public class CSExpandableLayout extends RelativeLayout {
    private Animation animation;
    private FrameLayout contentLayout;
    private Integer duration;
    private FrameLayout headerLayout;
    private Boolean isAnimationRunning;
    private Boolean isOpened;
    private onExpandableLayoutListener mListener;
    private Context mcontext;

    /* loaded from: classes.dex */
    public interface onExpandableLayoutListener {
        void oncollapse();

        void onexpand();

        void onexpandall();

        void onexpanded();
    }

    public CSExpandableLayout(Context context) {
        super(context);
        isAnimationRunning = false;
        isOpened = false;
    }

    public CSExpandableLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        isAnimationRunning = false;
        isOpened = false;
        init(context, attributeSet);
    }

    public CSExpandableLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        isAnimationRunning = false;
        isOpened = false;
        init(context, attributeSet);
    }

    @SuppressLint("CustomViewStyleable") 
    private void init(Context context, AttributeSet attributeSet) {
        mcontext = context;
        View inflate = View.inflate(context,R.layout.view_expandable, this);
        headerLayout = (FrameLayout) inflate.findViewById(R.id.view_expandable_headerlayout);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet,R.styleable.ExpandableLayout);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.ExpandableLayout_expandable_el_headerLayout, -1);
        int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.ExpandableLayout_expandable_el_contentLayout, -1);
        contentLayout = (FrameLayout) inflate.findViewById(R.id.view_expandable_contentLayout);
        if (resourceId == -1 || resourceId2 == -1) {
            throw new IllegalArgumentException("HeaderLayout and ContentLayout cannot be null!");
        }
        if (isInEditMode()) {
            return;
        }
        duration = Integer.valueOf((int) ServiceStarter.ERROR_UNKNOWN);
        View inflate2 = View.inflate(context, resourceId, null);
        inflate2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        headerLayout.addView(inflate2);
        View inflate3 = View.inflate(context, resourceId2, null);
        inflate3.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        contentLayout.addView(inflate3);
        contentLayout.setVisibility(View.GONE);
        headerLayout.setOnClickListener(view -> {
            if (isAnimationRunning) {
                return;
            }
            if (contentLayout.getVisibility() == View.VISIBLE) {
                if (mListener != null) {
                    mListener.oncollapse();
                }
                collapse(contentLayout);
            } else {
                if (mListener != null) {
                    mListener.onexpand();
                }
                CSExpandableLayout cSExpandableLayout2 = CSExpandableLayout.this;
                cSExpandableLayout2.expand(cSExpandableLayout2.contentLayout);
            }
            isAnimationRunning = true;
            new Handler().postDelayed(() -> isAnimationRunning = false, duration.intValue());
        });
        obtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void expand(final View view) {
        view.measure(-2, -2);
        final int measuredWidth = view.getMeasuredWidth();
        view.getLayoutParams().width = 0;
        view.setVisibility(View.VISIBLE);
        new AnimationSet(true);
        animation = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return true;
            }

            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, Transformation transformation) {
                int i = (Float.compare(f, 1.0f));
                if (i == 0) {
                    if (mListener != null) {
                        mListener.onexpanded();
                    }
                    isOpened = true;
                }
                view.getLayoutParams().width = i == 0 ? -2 : (int) (measuredWidth * f);
                view.requestLayout();
                if (mListener != null) {
                    mListener.onexpandall();
                }
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collapse(final View view) {
        final int measuredWidth = view.getMeasuredWidth();
         animation = new Animation() {
            @Override
            public boolean willChangeBounds() {
                return true;
            }

            @Override
            protected void applyTransformation(float f, Transformation transformation) {
                if (f == 1.0f) {
                    view.setVisibility(View.GONE);
                    isOpened = false;
                    return;
                }
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.width = measuredWidth - ((int) (measuredWidth * f));
                view.requestLayout();
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    public Boolean isOpened() {
        return isOpened;
    }

    public void hideNow() {
        contentLayout.getLayoutParams().width = 0;
        contentLayout.invalidate();
        contentLayout.setVisibility(View.GONE);
        isOpened = false;
    }

    public void showNow() {
        if (isOpened().booleanValue()) {
            return;
        }
        contentLayout.setVisibility(View.VISIBLE);
        isOpened = true;
        contentLayout.getLayoutParams().width = -2;
        contentLayout.invalidate();
    }

    public void show() {
        if (isAnimationRunning.booleanValue()) {
            return;
        }
        expand(contentLayout);
        isAnimationRunning = true;
        new Handler().postDelayed(() -> isAnimationRunning = false, duration);
    }

    public FrameLayout getHeaderLayout() {
        return headerLayout;
    }

    public FrameLayout getContentLayout() {
        return contentLayout;
    }

    public void hide() {
        if (isAnimationRunning.booleanValue()) {
            return;
        }
        collapse(contentLayout);
        isAnimationRunning = true;
        new Handler().postDelayed(() -> isAnimationRunning = false, duration);
    }

    @Override // android.view.ViewGroup
    public void setLayoutAnimationListener(Animation.AnimationListener animationListener) {
        animation.setAnimationListener(animationListener);
    }

    public void setonExpandableLayoutListener(onExpandableLayoutListener onexpandablelayoutlistener) {
        mListener = onexpandablelayoutlistener;
    }
}
