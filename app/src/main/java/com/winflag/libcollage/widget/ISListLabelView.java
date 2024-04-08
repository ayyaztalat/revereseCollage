package com.winflag.libcollage.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.picspool.instatextview.labelview.DMListLabelView;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class ISListLabelView extends DMListLabelView {
    public ISListLabelView(Context context) {
        super(context);
        iniISView();
    }

    public ISListLabelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        iniISView();
    }

    private void iniISView() {
        this.rootLayout.findViewById(R.id.btn_label_text).setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libcollage.widget.ISListLabelView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                View findViewById;
                if (ISListLabelView.this.rootLayout == null || (findViewById = ISListLabelView.this.rootLayout.findViewById(R.id.button_back)) == null) {
                    return;
                }
                findViewById.performClick();
                if (ISListLabelView.this.instaTextView != null) {
                    ISListLabelView.this.instaTextView.addText();
                }
            }
        });
    }
}
