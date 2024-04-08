package com.picspool.snappic.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSEJAddBar extends FrameLayout {
    private View ly_add;
    private View ly_cancel;
    private View ly_filter_title;
    private View ly_ok;
    private Context mContext;
    private TopbarItemClickListener mListenrer;

    /* loaded from: classes.dex */
    public interface TopbarItemClickListener {
        void onAddClicked();

        void onCancelClicked();

        void onOkClicked();
    }

    public CSEJAddBar(Context context) {
        super(context);
        initViewFuncTop(context);
    }

    private void initView(Context context, AttributeSet attributeSet) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_topbar_style1, (ViewGroup) this, true);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TopBarStyle1);
        String string = obtainStyledAttributes.getString(R.styleable.TopBarStyle1_titlename);
        if (string != null) {
            ((TextView) findViewById(R.id.text_title)).setText(string);
        }
        obtainStyledAttributes.recycle();
    }

    private void initViewFuncTop(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_topbar_style1, (ViewGroup) this, true);
        findViewById(R.id.ly_topbar_style1).setVisibility(View.GONE);
        findViewById(R.id.ly_topbar_style2).setVisibility(View.VISIBLE);
        View findViewById = findViewById(R.id.ly_add);
        this.ly_add = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSEJAddBar.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSEJAddBar.this.mListenrer != null) {
                    CSEJAddBar.this.mListenrer.onAddClicked();
                }
            }
        });
        View findViewById2 = findViewById(R.id.ly_cancel);
        this.ly_cancel = findViewById2;
        findViewById2.setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSEJAddBar.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSEJAddBar.this.mListenrer != null) {
                    CSEJAddBar.this.mListenrer.onCancelClicked();
                }
            }
        });
        View findViewById3 = findViewById(R.id.ly_ok);
        this.ly_ok = findViewById3;
        findViewById3.setOnClickListener(new OnClickListener() { // from class: com.picspool.snappic.widget.CSEJAddBar.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSEJAddBar.this.mListenrer != null) {
                    CSEJAddBar.this.mListenrer.onOkClicked();
                }
            }
        });
        this.ly_filter_title = findViewById(R.id.ly_filter_title);
    }

    public void showAsFunTopBar(boolean z) {
        setVisibility(View.VISIBLE);
        this.ly_add.setVisibility(z ? View.VISIBLE : View.INVISIBLE);
        this.ly_filter_title.setVisibility(z ? View.INVISIBLE : View.VISIBLE);
    }

    public void hideAsFunTopBar() {
        setVisibility(View.INVISIBLE);
    }

    public void showAddButton() {
        this.ly_add.setVisibility(View.VISIBLE);
    }

    public void setTopbarItemClickListener(TopbarItemClickListener topbarItemClickListener) {
        this.mListenrer = topbarItemClickListener;
    }

    public View getLy_cancel() {
        return this.ly_cancel;
    }

    public View getLy_ok() {
        return this.ly_ok;
    }
}
