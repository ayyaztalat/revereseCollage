package com.winflag.libcollage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.sky.testproject.R;


/* loaded from: classes.dex */
public class TemplateTopBarView extends LinearLayout {
    private OnTemplateTopBarListener listener;
    private FrameLayout ly_instagram;
    private FrameLayout ly_save;
    private FrameLayout ly_share;
    private View vTopBack;

    /* loaded from: classes.dex */
    public interface OnTemplateTopBarListener {
        void onTopItemClick(TemplateTopBarType templateTopBarType);
    }

    /* loaded from: classes.dex */
    public enum TemplateTopBarType {
        TOP_BACK,
        TOP_SAVE,
        TOP_INSTAGRAM,
        TOP_SHARE
    }

    public TemplateTopBarView(Context context) {
        super(context);
        init(context);
    }

    public TemplateTopBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_template_top_bar, (ViewGroup) this, true);
        View findViewById = findViewById(R.id.ly_back);
        this.vTopBack = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateTopBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateTopBarView.this.listener != null) {
                    TemplateTopBarView.this.listener.onTopItemClick(TemplateTopBarType.TOP_BACK);
                }
            }
        });
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.ly_done);
        this.ly_share = frameLayout;
        frameLayout.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateTopBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateTopBarView.this.listener != null) {
                    TemplateTopBarView.this.listener.onTopItemClick(TemplateTopBarType.TOP_SHARE);
                }
            }
        });
    }

    public void setOnTemplateTopBarListener(OnTemplateTopBarListener onTemplateTopBarListener) {
        this.listener = onTemplateTopBarListener;
    }
}
