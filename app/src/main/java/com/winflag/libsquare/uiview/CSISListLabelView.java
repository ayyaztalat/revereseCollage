package com.winflag.libsquare.uiview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.picspool.instatextview.labelview.DMListLabelView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class CSISListLabelView extends DMListLabelView {
    public CSISListLabelView(Context context) {
        super(context);
        iniISView();
    }

    public CSISListLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniISView();
    }

    private void iniISView() {
        // from class: com.winflag.libsquare.uiview.CSISListLabelView.1
// android.view.View.OnClickListener
        this.rootLayout.findViewById(R.id.btn_label_text).setOnClickListener(view -> {
            View findViewById;
            if (CSISListLabelView.this.rootLayout == null || (findViewById = CSISListLabelView.this.rootLayout.findViewById(R.id.button_back)) == null) {
                return;
            }
            findViewById.performClick();
            if (CSISListLabelView.this.instaTextView != null) {
                CSISListLabelView.this.instaTextView.addText();
            }
        });
    }
}
