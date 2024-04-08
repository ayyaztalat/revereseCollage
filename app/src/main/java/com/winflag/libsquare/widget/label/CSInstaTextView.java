package com.winflag.libsquare.widget.label;

import android.content.Context;
import android.util.AttributeSet;

import com.picspool.instatextview.labelview.DMListLabelView;
import com.picspool.instatextview.textview.DMInstaTextView;

/* loaded from: classes3.dex */
public class CSInstaTextView extends DMInstaTextView {
    public CSInstaTextView(Context context) {
        super(context);
    }

    public CSInstaTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // org.picspool.instatextview.textview.DMInstaTextView
    public DMListLabelView createListLabelView() {
        return new CSListLabelView(getContext());
    }
}
