package com.photo.editor.square.splash.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSTopBarStyle1 extends FrameLayout {
    public CSTopBarStyle1(Context context) {
        super(context);
        initView(context, null);
    }

    public CSTopBarStyle1(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context, attributeSet);
    }

    public CSTopBarStyle1(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView(context, attributeSet);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_topbar_style2, (ViewGroup) this, true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TopBarStyle1);
        String string = obtainStyledAttributes.getString(0);
        if (string != null) {
            ((TextView) findViewById(R.id.text_title)).setText(string);
        }
        obtainStyledAttributes.recycle();
        ((ImageView) findViewById(R.id.img_save_light)).startAnimation(AnimationUtils.loadAnimation(context, R.anim.light_anim));
    }
}
