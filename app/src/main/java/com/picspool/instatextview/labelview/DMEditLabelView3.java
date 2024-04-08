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
import com.picspool.instatextview.edit.DM_TextFixedView3;
import com.picspool.instatextview.textview.DMInstaTextView3;
import com.picspool.instatextview.textview.DMShowTextStickerView3;
import com.picspool.lib.text.DMTextDrawer;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMEditLabelView3 extends FrameLayout {
    private boolean addFlag;
    private Context context;
    private boolean createFlag;
    private FrameLayout frameLayout;
    InputMethodManager imm;
    private DMInstaTextView3 instaTextView;
    private DMListLabelView3 listLabelView;
    private String orjText;
    private DMShowTextStickerView3 surfaceView;
    private DM_TextFixedView3 textFixedView;
    private int topViewHeight;

    public DMEditLabelView3(Context context) {
        super(context);
        this.addFlag = true;
        iniView(context);
    }

    public DMEditLabelView3(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.addFlag = true;
        iniView(context);
    }

    private void iniView(Context context) {
        this.context = context;
        View inflate = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_text_edit_label_view, (ViewGroup) null);
        this.frameLayout = (FrameLayout) inflate.findViewById(R.id.edit_label_layout);
        ((LinearLayout) inflate.findViewById(R.id.button_label_ok)).setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.labelview.DMEditLabelView3.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMEditLabelView3.this.finishEditText();
            }
        });
        ((LinearLayout) inflate.findViewById(R.id.button_label_back)).setOnClickListener(new OnClickListener() { // from class: com.picspool.instatextview.labelview.DMEditLabelView3.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMEditLabelView3.this.imm.hideSoftInputFromWindow(DMEditLabelView3.this.textFixedView.getWindowToken(), 0);
                DMEditLabelView3.this.setVisibility(View.INVISIBLE);
                if (!DMEditLabelView3.this.addFlag) {
                    if (DMEditLabelView3.this.textFixedView != null && DMEditLabelView3.this.textFixedView.getTextDrawer() != null) {
                        DMEditLabelView3.this.textFixedView.getTextDrawer().setText(DMEditLabelView3.this.orjText);
                    }
                    if (DMEditLabelView3.this.surfaceView != null) {
                        DMEditLabelView3.this.surfaceView.setSurfaceVisibility(0);
                    }
                    new Handler().post(new Runnable() { // from class: com.picspool.instatextview.labelview.DMEditLabelView3.2.2
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                if (DMEditLabelView3.this.instaTextView != null) {
                                    DMEditLabelView3.this.instaTextView.releaseLabelView();
                                }
                            } catch (Exception unused) {
                            }
                        }
                    });
                    return;
                }
                new Handler().postDelayed(new Runnable() { // from class: com.picspool.instatextview.labelview.DMEditLabelView3.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            DMEditLabelView3.this.listLabelView.setVisibility(View.VISIBLE);
                        } catch (Exception unused) {
                        }
                    }
                }, 200L);
            }
        });
        DM_TextFixedView3 dM_TextFixedView3 = (DM_TextFixedView3) inflate.findViewById(R.id.label_fixed_view);
        this.textFixedView = dM_TextFixedView3;
        this.imm = (InputMethodManager) dM_TextFixedView3.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.textFixedView.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.picspool.instatextview.labelview.DMEditLabelView3.3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 6) {
                    DMEditLabelView3.this.finishEditText();
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
        DM_TextFixedView3 dM_TextFixedView3;
        InputMethodManager inputMethodManager = this.imm;
        if (inputMethodManager != null && (dM_TextFixedView3 = this.textFixedView) != null) {
            inputMethodManager.hideSoftInputFromWindow(dM_TextFixedView3.getWindowToken(), 0);
        }
        if (this.surfaceView != null && this.textFixedView.getTextDrawer() != null) {
            if (this.addFlag) {
                setVisibility(View.INVISIBLE);
                this.textFixedView.getTextDrawer().setShowHelpFlag(false);
                this.surfaceView.addLabelView(this.textFixedView.getTextDrawer());
                DMInstaTextView3 dMInstaTextView3 = this.instaTextView;
                if (dMInstaTextView3 != null) {
                    dMInstaTextView3.releaseLabelView();
                }
            } else {
                this.surfaceView.changeTextView();
                DMInstaTextView3 dMInstaTextView32 = this.instaTextView;
                if (dMInstaTextView32 != null) {
                    dMInstaTextView32.releaseLabelView();
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
        DM_TextFixedView3 dM_TextFixedView3 = this.textFixedView;
        if (dM_TextFixedView3 != null) {
            dM_TextFixedView3.dispose();
        }
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
                new Handler().post(new Runnable() { // from class: com.picspool.instatextview.labelview.DMEditLabelView3.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            DMEditLabelView3.this.setVisibility(View.INVISIBLE);
                            if (DMEditLabelView3.this.instaTextView != null) {
                                DMEditLabelView3.this.instaTextView.callFinishEditLabel();
                                DMEditLabelView3.this.instaTextView.releaseLabelView();
                            }
                        } catch (Exception unused) {
                        }
                    }
                });
            }
        }
        this.createFlag = true;
    }

    public DMShowTextStickerView3 getSurfaceView() {
        return this.surfaceView;
    }

    public void setSurfaceView(DMShowTextStickerView3 dMShowTextStickerView3) {
        this.surfaceView = dMShowTextStickerView3;
    }

    public boolean isAddFlag() {
        return this.addFlag;
    }

    public void setAddFlag(boolean z) {
        this.addFlag = z;
    }

    public DMListLabelView3 getListLabelView() {
        return this.listLabelView;
    }

    public void setListLabelView(DMListLabelView3 dMListLabelView3) {
        this.listLabelView = dMListLabelView3;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setInstaTextView(DMInstaTextView3 dMInstaTextView3) {
        this.instaTextView = dMInstaTextView3;
    }

    public void setBottomBackgroundColor(int i) {
        findViewById(R.id.eidt_label_toor_layout).setBackgroundColor(i);
    }
}
