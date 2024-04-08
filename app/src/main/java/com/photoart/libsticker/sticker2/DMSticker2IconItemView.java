package com.photoart.libsticker.sticker2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class DMSticker2IconItemView extends FrameLayout {
    public DMSticker2IconItemView(Context context) {
        super(context);
        init(context);
    }

    public DMSticker2IconItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_sticker2_icon_item, (ViewGroup) this, true);
    }
}
