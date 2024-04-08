package com.winflag.libcollage.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.picspool.instatextview.labelview.DMListLabelView;
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.lib.text.DMTextDrawer;

/* loaded from: classes.dex */
public class ISInstaTextView extends DMInstaTextView {
    public ISInstaTextView(Context context) {
        super(context);
    }

    public ISInstaTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DMListLabelView createDMListLabelView() {
        return new ISListLabelView(getContext());
    }

    @Override // org.picspool.instatextview.textview.DMInstaTextView
    public void addText() {
        DMTextDrawer dMTextDrawer = new DMTextDrawer(getContext(), "");
        dMTextDrawer.setTypeface(DMInstaTextView.getTfList().get(1));
        dMTextDrawer.setTypefaceIndex(1);
        dMTextDrawer.setPaintColorIndex(33);
        this.showTextView.setVisibility(4);
        addText(dMTextDrawer);
    }

    @Override // org.picspool.instatextview.textview.DMInstaTextView
    public void addLabel() {
        if (this.listLabelView == null) {
            this.editLabelView = null;
            createLabelView();
            if (this.editLabelView != null) {
                this.editLabelView.setVisibility(4);
            }
        }
        this.handler.post(new Runnable() { // from class: com.winflag.libcollage.widget.ISInstaTextView.1
            @Override // java.lang.Runnable
            public void run() {
                ISInstaTextView.this.listLabelView.showLabelList();
                ISInstaTextView.this.editLabelView.setAddFlag(true);
            }
        });
    }

    @Override // org.picspool.instatextview.textview.DMInstaTextView
    public void editLabel(DMTextDrawer dMTextDrawer) {
        if (this.listLabelView == null || this.editLabelView == null) {
            createLabelView();
        }
        this.editLabelView.editText(dMTextDrawer);
        this.editLabelView.setAddFlag(false);
    }
}
