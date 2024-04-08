package com.photo.editor.square.splash.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import com.photo.editor.square.splash.utils.CSScreenInfoUtil;

/* loaded from: classes2.dex */
class ImageFramesView<T> extends CustomFrameLayoutImags<T> {
    protected BackgroundGroupRecycleView recyclerView;

    @Override // com.photo.editor.square.splash.view.view.IL29
    protected void onSelectedPosition(int i) {
        Log.e(CustomFrameLayoutImags.TAG, "onSelectedPosition: " + i);
        BackgroundGroupRecycleView backgroundGroupRecycleView = this.recyclerView;
        if (backgroundGroupRecycleView != null) {
            backgroundGroupRecycleView.selectedPosition(i);
        }
    }

    public ImageFramesView(Context context) {
        super(context);
    }

    public ImageFramesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ImageFramesView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.photo.editor.square.splash.view.view.IL29
    public int f11() {
        return CSScreenInfoUtil.dip2px(getContext(), 90.0f) + super.f11();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.photo.editor.square.splash.view.view.IL29
    public void f10(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        super.f10(context, frameLayout, frameLayout2);
        BackgroundGroupRecycleView backgroundGroupRecycleView = new BackgroundGroupRecycleView(context);
        this.recyclerView = backgroundGroupRecycleView;
        backgroundGroupRecycleView.setSize(-1, CSScreenInfoUtil.dip2px(context, 90.0f));
        this.recyclerView.setOrientation(0);
        frameLayout2.addView(this.recyclerView);
    }
}
