package com.picspool.libfuncview.filter;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class CSCenterLayoutManager extends LinearLayoutManager {
    public CSCenterLayoutManager(Context context) {
        super(context);
    }

    public CSCenterLayoutManager(Context context, int i, boolean z) {
        super(context, i, z);
    }

    public CSCenterLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        CenterSmoothScroller centerSmoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        centerSmoothScroller.setTargetPosition(i);
        startSmoothScroll(centerSmoothScroller);
    }

    /* loaded from: classes.dex */
    private static class CenterSmoothScroller extends LinearSmoothScroller {
        CenterSmoothScroller(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.LinearSmoothScroller
        public int calculateDtToFit(int i, int i2, int i3, int i4, int i5) {
            return (i3 + ((i4 - i3) / 2)) - (i + ((i2 - i) / 2));
        }
    }
}
