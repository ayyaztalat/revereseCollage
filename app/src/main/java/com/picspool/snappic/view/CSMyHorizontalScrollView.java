package com.picspool.snappic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/* loaded from: classes.dex */
public class CSMyHorizontalScrollView extends HorizontalScrollView {
    private OnScrollListener onScrollListener;

    /* loaded from: classes.dex */
    public interface OnScrollListener {
        void onScrollChanged(int i, int i2, int i3, int i4);
    }

    public CSMyHorizontalScrollView(Context context) {
        this(context, null);
    }

    public CSMyHorizontalScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CSMyHorizontalScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.onScrollListener = null;
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        OnScrollListener onScrollListener = this.onScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollChanged(i, i2, i3, i4);
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }
}
