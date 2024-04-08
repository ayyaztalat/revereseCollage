package com.picspool.instatextview.labelview;

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
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.instatextview.textview.DMShowTextStickerView;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMEditLabelView extends FrameLayout {
    private boolean addFlag;
    private Context context;
    private boolean createFlag;
    private FrameLayout frameLayout;
    InputMethodManager imm;
    private DMInstaTextView instaTextView;
    private DMListLabelView listLabelView;
    private String orjText;
    private DMShowTextStickerView surfaceView;
    private DM_TextFixedView textFixedView;
    private int topViewHeight;

    public DMEditLabelView(Context context) {
        super(context);
        this.addFlag = true;
        iniView(context);
    }

    public DMEditLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.addFlag = true;
        iniView(context);
    }

    private void iniView(Context context) {
        this.context = context;
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_edit_label_view, (ViewGroup) null);
        this.frameLayout = (FrameLayout) inflate.findViewById(R.id.edit_label_layout);
        ((LinearLayout) inflate.findViewById(R.id.button_label_ok)).setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.labelview.DMEditLabelView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMEditLabelView.this.finishEditText();
            }
        });
        ((LinearLayout) inflate.findViewById(R.id.button_label_back)).setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.labelview.DMEditLabelView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMEditLabelView.this.imm.hideSoftInputFromWindow(DMEditLabelView.this.textFixedView.getWindowToken(), 0);
                DMEditLabelView.this.setVisibility(View.INVISIBLE);
                if (!DMEditLabelView.this.addFlag) {
                    if (DMEditLabelView.this.textFixedView != null && DMEditLabelView.this.textFixedView.getTextDrawer() != null) {
                        DMEditLabelView.this.textFixedView.getTextDrawer().setText(DMEditLabelView.this.orjText);
                    }
                    if (DMEditLabelView.this.surfaceView != null) {
                        DMEditLabelView.this.surfaceView.setSurfaceVisibility(0);
                    }
                    new Handler().post(new Runnable() { // from class: com.picspool.instatextview.labelview.DMEditLabelView.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (DMEditLabelView.this.instaTextView != null) {
                                    DMEditLabelView.this.instaTextView.releaseLabelView();
                                }
                            } catch (Exception unused) {
                            }
                        }
                    });
                    return;
                }
                new Handler().postDelayed(new Runnable() { // from class: com.picspool.instatextview.labelview.DMEditLabelView.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            DMEditLabelView.this.listLabelView.setVisibility(View.VISIBLE);
                        } catch (Exception unused) {
                        }
                    }
                }, 200L);
            }
        });
        DM_TextFixedView dM_TextFixedView = (DM_TextFixedView) inflate.findViewById(R.id.label_fixed_view);
        this.textFixedView = dM_TextFixedView;
        this.imm = (InputMethodManager) dM_TextFixedView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.textFixedView.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.picspool.instatextview.labelview.DMEditLabelView.3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 6) {
                    DMEditLabelView.this.finishEditText();
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
        if (this.surfaceView != null && this.textFixedView.getTextDrawer() != null) {
            if (this.addFlag) {
                setVisibility(View.INVISIBLE);
                this.textFixedView.getTextDrawer().setShowHelpFlag(false);
                this.surfaceView.addLabelView(this.textFixedView.getTextDrawer());
                DMInstaTextView dMInstaTextView = this.instaTextView;
                if (dMInstaTextView != null) {
                    dMInstaTextView.releaseLabelView();
                }
            } else {
                this.surfaceView.changeTextView();
                DMInstaTextView dMInstaTextView2 = this.instaTextView;
                if (dMInstaTextView2 != null) {
                    dMInstaTextView2.releaseLabelView();
                }
            }
            this.textFixedView.setTextDrawer(null);
        }
        this.instaTextView.callFinishEditLabel();
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
                new Handler().post(new Runnable() { // from class: com.picspool.instatextview.labelview.DMEditLabelView.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            DMEditLabelView.this.setVisibility(View.INVISIBLE);
                            if (DMEditLabelView.this.instaTextView != null) {
                                DMEditLabelView.this.instaTextView.callFinishEditLabel();
                                DMEditLabelView.this.instaTextView.releaseLabelView();
                            }
                        } catch (Exception unused) {
                        }
                    }
                });
            }
        }
        this.createFlag = true;
    }

    public DMShowTextStickerView getSurfaceView() {
        return this.surfaceView;
    }

    public void setSurfaceView(DMShowTextStickerView dMShowTextStickerView) {
        this.surfaceView = dMShowTextStickerView;
    }

    public boolean isAddFlag() {
        return this.addFlag;
    }

    public void setAddFlag(boolean z) {
        this.addFlag = z;
    }

    public DMListLabelView getListLabelView() {
        return this.listLabelView;
    }

    public void setListLabelView(DMListLabelView dMListLabelView) {
        this.listLabelView = dMListLabelView;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setInstaTextView(DMInstaTextView dMInstaTextView) {
        this.instaTextView = dMInstaTextView;
    }

    public void setBottomBackgroundColor(int i) {
        findViewById(R.id.eidt_label_toor_layout).setBackgroundColor(i);
    }
}
