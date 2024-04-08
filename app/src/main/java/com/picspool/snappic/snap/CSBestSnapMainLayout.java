package com.picspool.snappic.snap;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/* loaded from: classes.dex */
public class CSBestSnapMainLayout extends RelativeLayout {
    public ImageView mImageView;
    public boolean mIsHasFrame;
    public CSBestFrameRes mRes;
    public TextView mTextView;
    public int mTextViewTopMargin;
    public int mTxtBackgroundColor;
    public int snapHeight;

    public CSBestSnapMainLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.snapHeight = 0;
    }

    public CSBestSnapMainLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.snapHeight = 0;
    }

    public CSBestSnapMainLayout(Context context) {
        super(context);
        this.snapHeight = 0;
    }
}
