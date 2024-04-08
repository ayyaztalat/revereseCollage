package com.picspool.libfuncview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.picspool.libfuncview.utils.CSLibUiConfig;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSTipsView extends FrameLayout {
    private Context mContext;
    private String title_name;

    public CSTipsView(Context context) {
        super(context);
        init(context, null);
    }

    public CSTipsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public CSTipsView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        this.mContext = context;
        View inflate = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_tips_window, (ViewGroup) this, true);
        if (attributeSet == null) {
            return;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TipsView);
        String string = obtainStyledAttributes.getString(R.styleable.TipsView_tips_message);
        this.title_name = string;
        String str = DMPreferencesUtil.get(this.mContext, CSLibUiConfig.TipsWindowManager, string);
        if (str != null && !str.equals("")) {
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
            if (this.title_name != null) {
                ((TextView) findViewById(R.id.text_message)).setText(this.title_name);
            }
            Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.TipsView_tips_drawable);
            if (drawable != null) {
                ImageView imageView = (ImageView) findViewById(R.id.img_main);
                int screenWidth = DMScreenInfoUtil.screenWidth(this.mContext) - DMScreenInfoUtil.dip2px(this.mContext, 60.0f);
                imageView.getLayoutParams().height = screenWidth;
                imageView.getLayoutParams().width = screenWidth;
                imageView.setImageDrawable(drawable);
            }
            findViewById(R.id.txt_done).setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.view.CSTipsView.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CSTipsView.this.setVisibility(View.GONE);
                    DMPreferencesUtil.save(CSTipsView.this.mContext, CSLibUiConfig.TipsWindowManager, CSTipsView.this.title_name, "tips_clicked");
                }
            });
            inflate.setFocusableInTouchMode(true);
            inflate.requestFocusFromTouch();
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            setVisibility(View.GONE);
            return true;
        }
        return true;
    }
}
