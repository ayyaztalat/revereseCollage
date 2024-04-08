package com.photoart.libsticker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.photoart.libsticker.sticker2.DMSticker2IconItemView;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class DMStickerLibChooseView2 extends FrameLayout {
    private LinearLayout ly_container;

    /* loaded from: classes2.dex */
    public interface OnStickerChooseListener {
        void onClose();

        void onStickerChoose(DMWBRes dMWBRes);
    }

    public DMStickerLibChooseView2(Context context) {
        super(context);
        init(context);
    }

    public DMStickerLibChooseView2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_selector_sticker, (ViewGroup) this, true);
        this.ly_container = (LinearLayout) findViewById(R.id.ly_container);
        this.ly_container.addView(new DMSticker2IconItemView(getContext()), new LinearLayout.LayoutParams(-1, -1, 1.0f));
        this.ly_container.addView(new DMSticker2IconItemView(getContext()), new LinearLayout.LayoutParams(-1, -1, 1.0f));
        this.ly_container.setMinimumWidth(DMScreenInfoUtil.dip2px(getContext(), 100.0f));
    }
}
