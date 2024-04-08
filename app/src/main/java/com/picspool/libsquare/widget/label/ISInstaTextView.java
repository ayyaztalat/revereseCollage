package com.picspool.libsquare.widget.label;

import android.content.Context;
import android.util.AttributeSet;
import com.picspool.instatextview.labelview.DMListLabelView;
import com.picspool.instatextview.textview.DMInstaTextView;

/* loaded from: classes.dex */
public class ISInstaTextView extends DMInstaTextView {
    public ISInstaTextView(Context context) {
        super(context);
    }

    public ISInstaTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DMListLabelView createBMListLabelView() {
        return new ISListLabelView(getContext());
    }
}
