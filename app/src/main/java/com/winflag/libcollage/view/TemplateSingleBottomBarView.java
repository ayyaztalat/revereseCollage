package com.winflag.libcollage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sky.testproject.R;


/* loaded from: classes.dex */
public class TemplateSingleBottomBarView extends LinearLayout {
    private View ly_adjust;
    private View ly_filter;
    private View ly_fliph;
    private View ly_flipv;
    private View ly_glitch;
    private View ly_rotate;
    OnTemplateSingleBottomBarItemClickListener mListener;

    /* loaded from: classes.dex */
    public interface OnTemplateSingleBottomBarItemClickListener {
        void OnTemplateSingleBottomBarClose();

        void OnTemplateSingleBottomBarItemClick(TemplateSingleBottomItem templateSingleBottomItem);
    }

    /* loaded from: classes.dex */
    public enum TemplateSingleBottomItem {
        Single_Filter,
        Single_GLITCH,
        Single_ADJUST,
        Single_Rotate,
        Single_Horizontal,
        Single_Vertical
    }

    public TemplateSingleBottomBarView(Context context) {
        super(context);
        initView();
    }

    public TemplateSingleBottomBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public void setOnTemplateSingleBottomBarItemClickListener(OnTemplateSingleBottomBarItemClickListener onTemplateSingleBottomBarItemClickListener) {
        this.mListener = onTemplateSingleBottomBarItemClickListener;
    }

    protected void initView() {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_template_single_bottom_bar, (ViewGroup) this, true);
        findViewById(R.id.img_sub_close).setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateSingleBottomBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateSingleBottomBarView.this.mListener != null) {
                    TemplateSingleBottomBarView.this.mListener.OnTemplateSingleBottomBarClose();
                }
            }
        });
        View findViewById = findViewById(R.id.ly_filter);
        this.ly_filter = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateSingleBottomBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateSingleBottomBarView.this.mListener != null) {
                    TemplateSingleBottomBarView.this.mListener.OnTemplateSingleBottomBarItemClick(TemplateSingleBottomItem.Single_Filter);
                }
            }
        });
        View findViewById2 = findViewById(R.id.ly_glitch);
        this.ly_glitch = findViewById2;
        findViewById2.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateSingleBottomBarView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateSingleBottomBarView.this.mListener != null) {
                    TemplateSingleBottomBarView.this.mListener.OnTemplateSingleBottomBarItemClick(TemplateSingleBottomItem.Single_GLITCH);
                }
            }
        });
        View findViewById3 = findViewById(R.id.ly_adjust);
        this.ly_adjust = findViewById3;
        findViewById3.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateSingleBottomBarView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateSingleBottomBarView.this.mListener != null) {
                    TemplateSingleBottomBarView.this.mListener.OnTemplateSingleBottomBarItemClick(TemplateSingleBottomItem.Single_ADJUST);
                }
            }
        });
        View findViewById4 = findViewById(R.id.ly_rotate);
        this.ly_rotate = findViewById4;
        findViewById4.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateSingleBottomBarView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateSingleBottomBarView.this.mListener != null) {
                    TemplateSingleBottomBarView.this.mListener.OnTemplateSingleBottomBarItemClick(TemplateSingleBottomItem.Single_Rotate);
                }
            }
        });
        View findViewById5 = findViewById(R.id.ly_fliph);
        this.ly_fliph = findViewById5;
        findViewById5.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateSingleBottomBarView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateSingleBottomBarView.this.mListener != null) {
                    TemplateSingleBottomBarView.this.mListener.OnTemplateSingleBottomBarItemClick(TemplateSingleBottomItem.Single_Horizontal);
                }
            }
        });
        View findViewById6 = findViewById(R.id.ly_flipv);
        this.ly_flipv = findViewById6;
        findViewById6.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateSingleBottomBarView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateSingleBottomBarView.this.mListener != null) {
                    TemplateSingleBottomBarView.this.mListener.OnTemplateSingleBottomBarItemClick(TemplateSingleBottomItem.Single_Vertical);
                }
            }
        });
    }
}
