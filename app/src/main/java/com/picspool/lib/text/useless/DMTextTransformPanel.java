package com.picspool.lib.text.useless;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import com.picspool.lib.sticker.util.DM_ImageTransformPanel;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMTextTransformPanel extends DM_ImageTransformPanel {
    private Drawable editButton;

    public DMTextTransformPanel(Context context) {
        super(context);
        this.editButton = context.getResources().getDrawable(R.drawable.dmbtn_editphoto);
    }

    @Override // com.picspool.lib.sticker.util.DM_ImageTransformPanel
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (getSprite() == null || !this.isVisible) {
            return;
        }
        DMScreenInfoUtil.dip2px(getmContext(), 15.0f);
        DMScreenInfoUtil.dip2px(getmContext(), 15.0f);
    }
}
