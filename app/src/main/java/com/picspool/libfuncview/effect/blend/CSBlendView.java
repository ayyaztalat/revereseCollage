package com.picspool.libfuncview.effect.blend;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBlendView extends RelativeLayout {
    private CSBlendImageView blendImageView;
    private ProgressBar blendLoadHint;
    private Context mContext;

    public CSBlendView(Context context) {
        super(context);
        init(context);
    }

    public CSBlendView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public CSBlendView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.blend_container_view, (ViewGroup) this, true);
        this.blendImageView = (CSBlendImageView) findViewById(R.id.blend_image);
        this.blendImageView.setMagnifierImageView((ImageView) findViewById(R.id.magnifier_view));
        this.blendLoadHint = (ProgressBar) findViewById(R.id.blend_load_hint);
    }

    public CSBlendImageView getBlendImageView() {
        return this.blendImageView;
    }

    public void setBlendImageViewWAH(int i, int i2) {
        CSBlendImageView cSBlendImageView = this.blendImageView;
        if (cSBlendImageView != null) {
            cSBlendImageView.setWidthAndHeight(i, i2);
        }
    }

    public void setHintVisibility(int i) {
        ProgressBar progressBar = this.blendLoadHint;
        if (progressBar != null) {
            progressBar.setVisibility(i);
        }
    }

    public int getHintVisibility() {
        ProgressBar progressBar = this.blendLoadHint;
        if (progressBar != null) {
            return progressBar.getVisibility();
        }
        return -1;
    }

    public void setFirstMatrix(Matrix matrix) {
        CSBlendImageView cSBlendImageView = this.blendImageView;
        if (cSBlendImageView != null) {
            cSBlendImageView.setFirstMatrix(matrix);
        }
    }
}
