package com.picspool.libsquare.uiview;

import android.content.Context;
import android.util.AttributeSet;
import com.picspool.instatextview.labelview.DMListLabelView;
import com.picspool.instatextview.textview.DMInstaTextView;
import com.picspool.lib.text.DMTextDrawer;

/* loaded from: classes.dex */
public class CSInstaTextView extends DMInstaTextView {
    public CSInstaTextView(Context context) {
        super(context);
    }

    public CSInstaTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DMListLabelView createBMListLabelView() {
        return new CSBMListLabelView(getContext());
    }

    @Override // com.picspool.instatextview.textview.DMInstaTextView
    public void addText() {
        DMTextDrawer dMTextDrawer = new DMTextDrawer(getContext(), "");
        dMTextDrawer.setTypeface(DMInstaTextView.getTfList().get(1));
        dMTextDrawer.setTypefaceIndex(1);
        dMTextDrawer.setPaintColorIndex(33);
        this.showTextView.setVisibility(4);
        addText(dMTextDrawer);
    }

    @Override // com.picspool.instatextview.textview.DMInstaTextView
    public void addLabel() {
        if (this.listLabelView == null) {
            this.editLabelView = null;
            createLabelView();
            if (this.editLabelView != null) {
                this.editLabelView.setVisibility(4);
            }
        }
        this.handler.post(new Runnable() { // from class: com.picspool.libsquare.uiview.CSInstaTextView.1
            @Override // java.lang.Runnable
            public void run() {
                CSInstaTextView.this.listLabelView.showLabelList();
                CSInstaTextView.this.editLabelView.setAddFlag(true);
            }
        });
    }

    @Override // com.picspool.instatextview.textview.DMInstaTextView
    public void editLabel(DMTextDrawer dMTextDrawer) {
        if (this.listLabelView == null || this.editLabelView == null) {
            createLabelView();
        }
        this.editLabelView.editText(dMTextDrawer);
        this.editLabelView.setAddFlag(false);
    }
}
