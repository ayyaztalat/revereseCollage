package com.photo.editor.square.pic.splash.libfreecollage.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/* loaded from: classes2.dex */
public class AndroidBug5497Workaround {
    private Activity activity;
    private int contentHeight;
    private FrameLayout.LayoutParams frameLayoutParams;
    private boolean isfirst = true;
    private View mChildOfContent;
    private int statusBarHeight;
    private int usableHeightPrevious;

    public static void assistActivity(Activity activity) {
        new AndroidBug5497Workaround(activity);
    }

    private AndroidBug5497Workaround(Activity activity) {
        this.statusBarHeight = activity.getResources().getDimensionPixelSize(activity.getResources().getIdentifier("status_bar_height", "dimen", "android"));
        this.activity = activity;
        View childAt = ((FrameLayout) activity.findViewById(16908290)).getChildAt(0);
        this.mChildOfContent = childAt;
        childAt.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.photo.editor.square.pic.splash.libfreecollage.utils.AndroidBug5497Workaround.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (AndroidBug5497Workaround.this.isfirst) {
                    AndroidBug5497Workaround androidBug5497Workaround = AndroidBug5497Workaround.this;
                    androidBug5497Workaround.contentHeight = androidBug5497Workaround.mChildOfContent.getHeight();
                    AndroidBug5497Workaround.this.isfirst = false;
                }
                AndroidBug5497Workaround.this.possiblyResizeChildOfContent();
            }
        });
        this.frameLayoutParams = (FrameLayout.LayoutParams) this.mChildOfContent.getLayoutParams();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void possiblyResizeChildOfContent() {
        int computeUsableHeight = computeUsableHeight();
        if (computeUsableHeight != this.usableHeightPrevious) {
            int height = this.mChildOfContent.getRootView().getHeight();
            int i = height - computeUsableHeight;
            if (i > height / 4) {
                if (Build.VERSION.SDK_INT >= 19) {
                    this.frameLayoutParams.height = (height - i) + this.statusBarHeight;
                } else {
                    this.frameLayoutParams.height = height - i;
                }
            } else {
                this.frameLayoutParams.height = this.contentHeight;
            }
            this.mChildOfContent.requestLayout();
            this.usableHeightPrevious = computeUsableHeight;
        }
    }

    private int computeUsableHeight() {
        Rect rect = new Rect();
        this.mChildOfContent.getWindowVisibleDisplayFrame(rect);
        return rect.bottom - rect.top;
    }
}
