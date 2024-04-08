package com.photo.editor.square.splash.view.filterbar;

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
import com.google.firebase.messaging.ServiceStarter;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSExpandableLayout extends RelativeLayout {
    private Animation animation;
    private FrameLayout contentLayout;
    private Integer duration;
    private FrameLayout headerLayout;
    private Boolean isAnimationRunning;
    private Boolean isOpened;
    private onExpandableLayoutListener mListener;
    private Context mcontext;

    /* loaded from: classes2.dex */
    public interface onExpandableLayoutListener {
        void oncollapse();

        void onexpand();

        void onexpandall();

        void onexpanded();
    }

    public CSExpandableLayout(Context context) {
        super(context);
        this.isAnimationRunning = false;
        this.isOpened = false;
    }

    public CSExpandableLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isAnimationRunning = false;
        this.isOpened = false;
        init(context, attributeSet);
    }

    public CSExpandableLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isAnimationRunning = false;
        this.isOpened = false;
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mcontext = context;
        View inflate = View.inflate(context, R.layout.view_expandable, this);
        this.headerLayout = (FrameLayout) inflate.findViewById(R.id.view_expandable_headerlayout);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ExpandableLayout);
        @SuppressLint("ResourceType") int resourceId = obtainStyledAttributes.getResourceId(2, -1);
        int resourceId2 = obtainStyledAttributes.getResourceId(0, -1);
        this.contentLayout = (FrameLayout) inflate.findViewById(R.id.view_expandable_contentLayout);
        if (resourceId == -1 || resourceId2 == -1) {
            throw new IllegalArgumentException("HeaderLayout and ContentLayout cannot be null!");
        }
        if (isInEditMode()) {
            return;
        }
        this.duration = Integer.valueOf((int) ServiceStarter.ERROR_UNKNOWN);
        View inflate2 = View.inflate(context, resourceId, null);
        inflate2.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.headerLayout.addView(inflate2);
        View inflate3 = View.inflate(context, resourceId2, null);
        inflate3.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.contentLayout.addView(inflate3);
        this.contentLayout.setVisibility(View.GONE);
        this.headerLayout.setOnClickListener(new OnClickListener() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSExpandableLayout.this.isAnimationRunning.booleanValue()) {
                    return;
                }
                if (CSExpandableLayout.this.contentLayout.getVisibility() == View.VISIBLE) {
                    if (CSExpandableLayout.this.mListener != null) {
                        CSExpandableLayout.this.mListener.oncollapse();
                    }
                    CSExpandableLayout cSExpandableLayout = CSExpandableLayout.this;
                    cSExpandableLayout.collapse(cSExpandableLayout.contentLayout);
                } else {
                    if (CSExpandableLayout.this.mListener != null) {
                        CSExpandableLayout.this.mListener.onexpand();
                    }
                    CSExpandableLayout cSExpandableLayout2 = CSExpandableLayout.this;
                    cSExpandableLayout2.expand(cSExpandableLayout2.contentLayout);
                }
                CSExpandableLayout.this.isAnimationRunning = true;
                new Handler().postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        CSExpandableLayout.this.isAnimationRunning = false;
                    }
                }, CSExpandableLayout.this.duration.intValue());
            }
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
        Animation animation = new Animation() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.2
            @Override // android.view.animation.Animation
            public boolean willChangeBounds() {
                return true;
            }

            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, Transformation transformation) {
                int i = (f > 1.0f ? 1 : (f == 1.0f ? 0 : -1));
                if (i == 0) {
                    if (CSExpandableLayout.this.mListener != null) {
                        CSExpandableLayout.this.mListener.onexpanded();
                    }
                    CSExpandableLayout.this.isOpened = true;
                }
                view.getLayoutParams().width = i == 0 ? -2 : (int) (measuredWidth * f);
                view.requestLayout();
                if (CSExpandableLayout.this.mListener != null) {
                    CSExpandableLayout.this.mListener.onexpandall();
                }
            }
        };
        this.animation = animation;
        animation.setDuration(this.duration.intValue());
        view.startAnimation(this.animation);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void collapse(final View view) {
        final int measuredWidth = view.getMeasuredWidth();
        Animation animation = new Animation() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.3
            @Override // android.view.animation.Animation
            public boolean willChangeBounds() {
                return true;
            }

            @Override // android.view.animation.Animation
            protected void applyTransformation(float f, Transformation transformation) {
                if (f == 1.0f) {
                    view.setVisibility(View.GONE);
                    CSExpandableLayout.this.isOpened = false;
                    return;
                }
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                int i = measuredWidth;
                layoutParams.width = i - ((int) (i * f));
                view.requestLayout();
            }
        };
        this.animation = animation;
        animation.setDuration(this.duration.intValue());
        view.startAnimation(this.animation);
    }

    public Boolean isOpened() {
        return this.isOpened;
    }

    public void hideNow() {
        this.contentLayout.getLayoutParams().width = 0;
        this.contentLayout.invalidate();
        this.contentLayout.setVisibility(View.GONE);
        this.isOpened = false;
    }

    public void showNow() {
        if (isOpened().booleanValue()) {
            return;
        }
        this.contentLayout.setVisibility(View.VISIBLE);
        this.isOpened = true;
        this.contentLayout.getLayoutParams().width = -2;
        this.contentLayout.invalidate();
    }

    public void show() {
        if (this.isAnimationRunning.booleanValue()) {
            return;
        }
        expand(this.contentLayout);
        this.isAnimationRunning = true;
        new Handler().postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.4
            @Override // java.lang.Runnable
            public void run() {
                CSExpandableLayout.this.isAnimationRunning = false;
            }
        }, this.duration.intValue());
    }

    public FrameLayout getHeaderLayout() {
        return this.headerLayout;
    }

    public FrameLayout getContentLayout() {
        return this.contentLayout;
    }

    public void hide() {
        if (this.isAnimationRunning.booleanValue()) {
            return;
        }
        collapse(this.contentLayout);
        this.isAnimationRunning = true;
        new Handler().postDelayed(new Runnable() { // from class: com.photo.editor.square.splash.view.filterbar.CSExpandableLayout.5
            @Override // java.lang.Runnable
            public void run() {
                CSExpandableLayout.this.isAnimationRunning = false;
            }
        }, this.duration.intValue());
    }

    @Override // android.view.ViewGroup
    public void setLayoutAnimationListener(Animation.AnimationListener animationListener) {
        this.animation.setAnimationListener(animationListener);
    }

    public void setonExpandableLayoutListener(onExpandableLayoutListener onexpandablelayoutlistener) {
        this.mListener = onexpandablelayoutlistener;
    }
}
