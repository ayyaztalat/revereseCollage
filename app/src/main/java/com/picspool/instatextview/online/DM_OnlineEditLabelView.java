package com.picspool.instatextview.online;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.picspool.instatextview.edit.DM_TextFixedView;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DM_OnlineEditLabelView extends FrameLayout {
    private boolean addFlag;
    private Context context;
    private boolean createFlag;
    private FrameLayout frameLayout;
    InputMethodManager imm;
    private DM_OnlineInstaTextView instaTextView;
    private DM_OnlineListLabelView listLabelView;
    private String orjText;
    private DM_OnlineShowTextBMStickerView surfaceView;
    private DM_TextFixedView textFixedView;
    private int topViewHeight;

    public DM_OnlineEditLabelView(Context context) {
        super(context);
        this.addFlag = true;
        iniView(context);
    }

    public DM_OnlineEditLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.addFlag = true;
        iniView(context);
    }

    private void iniView(Context context) {
        this.context = context;
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_edit_label_view, (ViewGroup) null);
        this.frameLayout = (FrameLayout) inflate.findViewById(R.id.edit_label_layout);
        ((LinearLayout) inflate.findViewById(R.id.button_label_ok)).setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditLabelView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineEditLabelView.this.finishEditText();
            }
        });
        ((LinearLayout) inflate.findViewById(R.id.button_label_back)).setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditLabelView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DM_OnlineEditLabelView.this.imm.hideSoftInputFromWindow(DM_OnlineEditLabelView.this.textFixedView.getWindowToken(), 0);
                DM_OnlineEditLabelView.this.setVisibility(View.INVISIBLE);
                if (!DM_OnlineEditLabelView.this.addFlag) {
                    if (DM_OnlineEditLabelView.this.textFixedView != null && DM_OnlineEditLabelView.this.textFixedView.getTextDrawer() != null) {
                        DM_OnlineEditLabelView.this.textFixedView.getTextDrawer().setText(DM_OnlineEditLabelView.this.orjText);
                    }
                    if (DM_OnlineEditLabelView.this.surfaceView != null) {
                        DM_OnlineEditLabelView.this.surfaceView.setSurfaceVisibility(0);
                    }
                    new Handler().post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineEditLabelView.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (DM_OnlineEditLabelView.this.instaTextView != null) {
                                    DM_OnlineEditLabelView.this.instaTextView.releaseLabelView();
                                }
                            } catch (Exception unused) {
                            }
                        }
                    });
                    return;
                }
                new Handler().postDelayed(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineEditLabelView.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            DM_OnlineEditLabelView.this.listLabelView.setVisibility(View.VISIBLE);
                        } catch (Exception unused) {
                        }
                    }
                }, 200L);
            }
        });
        DM_TextFixedView dM_TextFixedView = (DM_TextFixedView) inflate.findViewById(R.id.label_fixed_view);
        this.textFixedView = dM_TextFixedView;
        this.imm = (InputMethodManager) dM_TextFixedView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.textFixedView.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.picspool.instatextview.online.DM_OnlineEditLabelView.3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 6) {
                    DM_OnlineEditLabelView.this.finishEditText();
                    return false;
                }
                return false;
            }
        });
        addView(inflate);
    }

    public void editText(DMTextDrawer dMTextDrawer) {
        try {
            setVisibility(View.VISIBLE);
            if (dMTextDrawer == null) {
                dMTextDrawer = new DMTextDrawer(getContext(), "");
            } else {
                this.orjText = dMTextDrawer.getTextString();
            }
            this.textFixedView.setTextDrawer(dMTextDrawer);
            this.textFixedView.setFocusable(true);
            this.textFixedView.setFocusableInTouchMode(true);
            this.textFixedView.requestFocus();
            this.imm.showSoftInput(this.textFixedView, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishEditText() {
        DM_TextFixedView dM_TextFixedView;
        InputMethodManager inputMethodManager = this.imm;
        if (inputMethodManager != null && (dM_TextFixedView = this.textFixedView) != null) {
            inputMethodManager.hideSoftInputFromWindow(dM_TextFixedView.getWindowToken(), 0);
        }
        if (this.surfaceView == null || this.textFixedView.getTextDrawer() == null) {
            return;
        }
        if (this.addFlag) {
            setVisibility(View.INVISIBLE);
            this.textFixedView.getTextDrawer().setShowHelpFlag(false);
            this.surfaceView.addLabelView(this.textFixedView.getTextDrawer());
            DM_OnlineInstaTextView dM_OnlineInstaTextView = this.instaTextView;
            if (dM_OnlineInstaTextView != null) {
                dM_OnlineInstaTextView.releaseLabelView();
            }
        } else {
            this.surfaceView.changeTextView();
            DM_OnlineInstaTextView dM_OnlineInstaTextView2 = this.instaTextView;
            if (dM_OnlineInstaTextView2 != null) {
                dM_OnlineInstaTextView2.releaseLabelView();
            }
        }
        this.textFixedView.setTextDrawer(null);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (i == 0) {
            this.textFixedView.loadImage();
            return;
        }
        if (!this.addFlag) {
            removeAllViews();
        }
        this.textFixedView.dispose();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        if (this.topViewHeight == 0) {
            this.topViewHeight = i2;
        }
        int i5 = this.topViewHeight - i2;
        if (this.createFlag && getVisibility() != View.INVISIBLE && i5 == 0) {
            setVisibility(View.INVISIBLE);
            this.surfaceView.setSurfaceVisibility(0);
            if (this.listLabelView.getVisibility() == View.INVISIBLE && this.instaTextView != null) {
                new Handler().post(new Runnable() { // from class: com.picspool.instatextview.online.DM_OnlineEditLabelView.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            DM_OnlineEditLabelView.this.setVisibility(View.INVISIBLE);
                            if (DM_OnlineEditLabelView.this.instaTextView != null) {
                                DM_OnlineEditLabelView.this.instaTextView.releaseLabelView();
                            }
                        } catch (Exception unused) {
                        }
                    }
                });
            }
        }
        this.createFlag = true;
    }

    public DM_OnlineShowTextBMStickerView getSurfaceView() {
        return this.surfaceView;
    }

    public void setSurfaceView(DM_OnlineShowTextBMStickerView dM_OnlineShowTextBMStickerView) {
        this.surfaceView = dM_OnlineShowTextBMStickerView;
    }

    public boolean isAddFlag() {
        return this.addFlag;
    }

    public void setAddFlag(boolean z) {
        this.addFlag = z;
    }

    public DM_OnlineListLabelView getListLabelView() {
        return this.listLabelView;
    }

    public void setListLabelView(DM_OnlineListLabelView dM_OnlineListLabelView) {
        this.listLabelView = dM_OnlineListLabelView;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setInstaTextView(DM_OnlineInstaTextView dM_OnlineInstaTextView) {
        this.instaTextView = dM_OnlineInstaTextView;
    }
}
