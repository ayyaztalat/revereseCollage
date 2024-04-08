package com.winflag.libcollage.widget.radioview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class RatioImageView extends FrameLayout {
    private int height;
    private FrameLayout ly_layer1;
    private FrameLayout ly_layer2;
    private Context mContext;
    private float mRadio;
    private String name;
    private TextView textView;

    public float getmRadio() {
        return this.mRadio;
    }

    public void setmRadio(float f, int i) {
        this.mRadio = f;
        LayoutParams layoutParams = (LayoutParams) this.ly_layer1.getLayoutParams();
        LayoutParams layoutParams2 = (LayoutParams) this.ly_layer2.getLayoutParams();
        int dip2px = DMScreenInfoUtil.dip2px(this.mContext, 2.0f);
        if (f <= 1.0f) {
            int i2 = (int) ((i * (1.0f - f)) / 2.0f);
            layoutParams.setMargins(i2, 0, i2, 0);
            layoutParams2.setMargins(dip2px, dip2px, dip2px, dip2px);
            return;
        }
        int i3 = (int) ((i * (1.0f - (1.0f / f))) / 2.0f);
        layoutParams.setMargins(0, i3, 0, i3);
        layoutParams2.setMargins(dip2px, dip2px, dip2px, dip2px);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
        this.textView.setText(str);
    }

    public RatioImageView(Context context) {
        super(context);
        this.mRadio = 0.8f;
        initView(context);
    }

    public RatioImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRadio = 0.8f;
        initView(context);
    }

    public RatioImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRadio = 0.8f;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_radtio_style2_item_imageview, (ViewGroup) this, true);
        this.ly_layer1 = (FrameLayout) findViewById(R.id.ly_layer1);
        this.ly_layer2 = (FrameLayout) findViewById(R.id.ly_layer2);
        this.textView = (TextView) findViewById(R.id.txt_name);
    }
}
