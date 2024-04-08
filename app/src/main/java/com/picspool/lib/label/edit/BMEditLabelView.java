package com.picspool.lib.label.edit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.text.draw.DMTextDrawer;
import com.picspool.lib.text.edit.DMTextFixedView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class BMEditLabelView extends FrameLayout {
    private boolean createFlag;
    private InputMethodManager imm;
    private EditingChangedListener mChangedListener;
    private DMTextFixedView textFixedView;

    /* loaded from: classes3.dex */
    public interface EditingChangedListener {
        void cancelEditing();

        void editingBack();

        void finishEditing(DMTextDrawer dMTextDrawer);
    }

    public BMEditLabelView(Context context) {
        super(context);
        this.createFlag = false;
        iniView(context);
    }

    public BMEditLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.createFlag = false;
        iniView(context);
    }

    private void iniView(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_edit_label_view, (ViewGroup) this, true);
        findViewById(R.id.button_label_ok).setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.label.edit.BMEditLabelView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BMEditLabelView.this.finishEditText();
            }
        });
        findViewById(R.id.button_label_back).setOnClickListener(new OnClickListener() { // from class: com.picspool.lib.label.edit.BMEditLabelView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BMEditLabelView.this.imm.hideSoftInputFromWindow(BMEditLabelView.this.textFixedView.getWindowToken(), 0);
                BMEditLabelView.this.setVisibility(View.INVISIBLE);
                if (BMEditLabelView.this.mChangedListener != null) {
                    BMEditLabelView.this.mChangedListener.editingBack();
                }
            }
        });
        DMTextFixedView dMTextFixedView = (DMTextFixedView) findViewById(R.id.label_fixed_view);
        this.textFixedView = dMTextFixedView;
        this.imm = (InputMethodManager) dMTextFixedView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.textFixedView.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.picspool.lib.label.edit.BMEditLabelView.3
            @Override // android.widget.TextView.OnEditorActionListener
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == 6) {
                    BMEditLabelView.this.finishEditText();
                    return false;
                }
                return false;
            }
        });
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        EditingChangedListener editingChangedListener;
        if (this.createFlag && i2 == DMScreenInfoUtil.screenHeight(getContext()) && getVisibility() == View.VISIBLE && (editingChangedListener = this.mChangedListener) != null) {
            editingChangedListener.cancelEditing();
        }
        this.createFlag = true;
    }

    public void editText(DMTextDrawer dMTextDrawer) {
        if (dMTextDrawer == null) {
            try {
                dMTextDrawer = new DMTextDrawer(getContext(), "");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.textFixedView.setTextDrawer(dMTextDrawer);
        this.textFixedView.setFocusable(true);
        this.textFixedView.setFocusableInTouchMode(true);
        this.textFixedView.requestFocus();
        this.imm.showSoftInput(this.textFixedView, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void finishEditText() {
        DMTextFixedView dMTextFixedView;
        InputMethodManager inputMethodManager = this.imm;
        if (inputMethodManager != null && (dMTextFixedView = this.textFixedView) != null) {
            inputMethodManager.hideSoftInputFromWindow(dMTextFixedView.getWindowToken(), 0);
        }
        DMTextDrawer textDrawer = this.textFixedView.getTextDrawer();
        this.textFixedView.setTextDrawer(null);
        EditingChangedListener editingChangedListener = this.mChangedListener;
        if (editingChangedListener != null) {
            editingChangedListener.finishEditing(textDrawer);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setEditingChangedListener(EditingChangedListener editingChangedListener) {
        this.mChangedListener = editingChangedListener;
    }
}
