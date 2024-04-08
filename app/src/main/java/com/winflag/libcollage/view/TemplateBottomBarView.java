package com.winflag.libcollage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;
import com.winflag.Utils.CacheUtils;

/* loaded from: classes.dex */
public class TemplateBottomBarView extends LinearLayout {
    private View ly_add;
    private View ly_adjust;
    private View ly_background;
    private View ly_blur;
    private View ly_filter;
    private View ly_glitch;
    private View ly_layout;
    private View ly_margin;
    private View ly_masic;
    private View ly_ratio;
    private View ly_space;
    private View ly_sticker;
    private View ly_text;
    OnTemplateBottomBarItemClickListener mListener;
    private TextView txt_space;

    /* loaded from: classes.dex */
    public interface OnTemplateBottomBarItemClickListener {
        void OnTemplateBottomBarItemClick(TemplateBottomItem templateBottomItem);
    }

    /* loaded from: classes.dex */
    public enum TemplateBottomItem {
        ADD,
        Layout,
        Background,
        Margin,
        Space,
        Ratio,
        Text,
        DMSticker,
        Filter,
        GLITCH,
        ADJUST,
        Masic,
        Blur
    }

    public TemplateBottomBarView(Context context) {
        super(context);
        initView(context);
    }

    public TemplateBottomBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    public void setOnTemplateBottomBarItemClickListener(OnTemplateBottomBarItemClickListener onTemplateBottomBarItemClickListener) {
        this.mListener = onTemplateBottomBarItemClickListener;
    }

    public void resetSelectorStat() {
        this.ly_add.setSelected(false);
        this.ly_layout.setSelected(false);
        this.ly_background.setSelected(false);
        this.ly_space.setSelected(false);
        this.ly_margin.setSelected(false);
        this.ly_ratio.setSelected(false);
        this.ly_text.setSelected(false);
        this.ly_sticker.setSelected(false);
        this.ly_filter.setSelected(false);
        this.ly_glitch.setSelected(false);
        this.ly_adjust.setSelected(false);
        this.ly_masic.setSelected(false);
        this.ly_blur.setSelected(false);
    }

    public void setBottomItemVisible(TemplateBottomItem templateBottomItem, boolean z) {
        if (templateBottomItem == TemplateBottomItem.ADD) {
            this.ly_add.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.Layout) {
            this.ly_layout.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.Background) {
            this.ly_background.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.Space) {
            this.ly_space.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.Margin) {
            this.ly_margin.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.Ratio) {
            this.ly_ratio.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.Text) {
            this.ly_text.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.DMSticker) {
            this.ly_sticker.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.Filter) {
            this.ly_filter.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.GLITCH) {
            this.ly_glitch.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.ADJUST) {
            this.ly_adjust.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.Masic) {
            this.ly_masic.setVisibility(z ? View.VISIBLE : View.GONE);
        } else if (templateBottomItem == TemplateBottomItem.Blur) {
            this.ly_blur.setVisibility(z ? View.VISIBLE : View.GONE);
        }
    }

    public void setInSelectorStat(TemplateBottomItem templateBottomItem, boolean z) {
        if (templateBottomItem == TemplateBottomItem.ADD) {
            if (z) {
                this.ly_add.setSelected(true);
            } else {
                this.ly_add.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.Layout) {
            if (z) {
                this.ly_layout.setSelected(true);
            } else {
                this.ly_layout.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.Background) {
            if (z) {
                this.ly_background.setSelected(true);
            } else {
                this.ly_background.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.Space) {
            if (z) {
                this.ly_space.setSelected(true);
            } else {
                this.ly_space.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.Margin) {
            if (z) {
                this.ly_margin.setSelected(true);
            } else {
                this.ly_margin.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.Ratio) {
            if (z) {
                this.ly_ratio.setSelected(true);
            } else {
                this.ly_ratio.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.Text) {
            if (z) {
                this.ly_text.setSelected(true);
            } else {
                this.ly_text.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.DMSticker) {
            if (z) {
                this.ly_sticker.setSelected(true);
            } else {
                this.ly_sticker.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.Filter) {
            if (z) {
                this.ly_filter.setSelected(true);
            } else {
                this.ly_filter.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.GLITCH) {
            if (z) {
                this.ly_glitch.setSelected(true);
            } else {
                this.ly_glitch.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.ADJUST) {
            if (z) {
                this.ly_adjust.setSelected(true);
            } else {
                this.ly_adjust.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.Masic) {
            if (z) {
                this.ly_masic.setSelected(true);
            } else {
                this.ly_masic.setSelected(false);
            }
        } else if (templateBottomItem == TemplateBottomItem.Blur) {
            if (z) {
                this.ly_blur.setSelected(true);
            } else {
                this.ly_blur.setSelected(false);
            }
        }
    }

    public void initView(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_template_bottom_bar, (ViewGroup) this, true);
        this.ly_margin = findViewById(R.id.ly_margin);
        View findViewById = findViewById(R.id.ly_add);
        this.ly_add = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.ADD);
                }
            }
        });
        View findViewById2 = findViewById(R.id.ly_layout);
        this.ly_layout = findViewById2;
        findViewById2.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.Layout);
                }
            }
        });
        View findViewById3 = findViewById(R.id.ly_background);
        this.ly_background = findViewById3;
        findViewById3.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.Background);
                }
            }
        });
        View findViewById4 = findViewById(R.id.ly_space);
        this.ly_space = findViewById4;
        findViewById4.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.Space);
                }
            }
        });
        this.ly_margin = findViewById(R.id.ly_margin);
        this.txt_space = (TextView) findViewById(R.id.txt_space);
        if (CacheUtils.getString(context, "collage_or_free", "free").equals("collage")) {
            this.txt_space.setText(R.string.square_borde);
        } else {
            this.txt_space.setText(R.string.bottom_Stroke);
        }
        this.ly_margin.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.Margin);
                }
            }
        });
        View findViewById5 = findViewById(R.id.ly_ratio);
        this.ly_ratio = findViewById5;
        findViewById5.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.Ratio);
                }
            }
        });
        View findViewById6 = findViewById(R.id.ly_text);
        this.ly_text = findViewById6;
        findViewById6.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.Text);
                }
            }
        });
        View findViewById7 = findViewById(R.id.ly_sticker);
        this.ly_sticker = findViewById7;
        findViewById7.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.DMSticker);
                }
            }
        });
        View findViewById8 = findViewById(R.id.ly_filter);
        this.ly_filter = findViewById8;
        findViewById8.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.Filter);
                }
            }
        });
        View findViewById9 = findViewById(R.id.ly_glitch);
        this.ly_glitch = findViewById9;
        findViewById9.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.GLITCH);
                }
            }
        });
        View findViewById10 = findViewById(R.id.ly_adjust);
        this.ly_adjust = findViewById10;
        findViewById10.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.ADJUST);
                }
            }
        });
        View findViewById11 = findViewById(R.id.ly_masic);
        this.ly_masic = findViewById11;
        findViewById11.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.Masic);
                }
            }
        });
        View findViewById12 = findViewById(R.id.ly_blur);
        this.ly_blur = findViewById12;
        findViewById12.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.view.TemplateBottomBarView.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (TemplateBottomBarView.this.mListener != null) {
                    TemplateBottomBarView.this.mListener.OnTemplateBottomBarItemClick(TemplateBottomItem.Blur);
                }
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.bottom_button_fl);
        double screenWidthDp = DMScreenInfoUtil.screenWidthDp(getContext());
        double d = (screenWidthDp / 5.5d) * 9.0d;
        if (screenWidthDp > d) {
            linearLayout.setMinimumWidth(DMScreenInfoUtil.screenWidth(getContext()));
        } else {
            linearLayout.setMinimumWidth(DMScreenInfoUtil.dip2px(getContext(), (float) d));
        }
    }
}
