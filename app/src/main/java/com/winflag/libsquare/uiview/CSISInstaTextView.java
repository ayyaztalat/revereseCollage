package com.winflag.libsquare.uiview;

import android.content.Context;
import android.util.AttributeSet;

import com.picspool.instatextview.labelview.DMListLabelView;
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.lib.text.DMTextDrawer;

/* loaded from: classes3.dex */
public class CSISInstaTextView extends DMInstaTextView {
    public CSISInstaTextView(Context context) {
        super(context);
    }

    public CSISInstaTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // org.picspool.instatextview.textview.DMInstaTextView
    public DMListLabelView createListLabelView() {
        return new CSISListLabelView(getContext());
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
        this.handler.post(new Runnable() { // from class: com.winflag.libsquare.uiview.CSISInstaTextView.1
            @Override // java.lang.Runnable
            public void run() {
                CSISInstaTextView.this.listLabelView.showLabelList();
                CSISInstaTextView.this.editLabelView.setAddFlag(true);
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
