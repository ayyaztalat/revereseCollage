package com.picspool.libsquare.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.picspool.instatextview.labelview.DMListLabelView;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBMListLabelView extends DMListLabelView {
    public CSBMListLabelView(Context context) {
        super(context);
        iniISView();
    }

    public CSBMListLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniISView();
    }

    private void iniISView() {
        this.rootLayout.findViewById(R.id.btn_label_label).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libsquare.uiview.CSBMListLabelView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                View findViewById;
                if (CSBMListLabelView.this.rootLayout == null || (findViewById = CSBMListLabelView.this.rootLayout.findViewById(R.id.button_back)) == null) {
                    return;
                }
                findViewById.performClick();
                if (CSBMListLabelView.this.instaTextView != null) {
                    CSBMListLabelView.this.instaTextView.addText();
                }
            }
        });
    }
}
