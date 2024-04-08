package com.magic.video.editor.effect.cut.utils.csBaseCodes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;
import com.magic.video.editor.effect.cut.utils.CSScreenInfoUtil;
import com.sky.testproject.R;

/* renamed from: com.magic.video.editor.effect.cut.utils.bg.CSColorGalleryView */
/* loaded from: classes2.dex */
public class CSColorGalleryView extends FrameLayout {
    private CSColorAdapter mBMColorAdapter;
    private Context mContext;
    private Gallery mGallery;
    private CSGalleryPointerView mGalleryPointerView;
    private CSOnMvColorChangedListener mOnColorChangedListener;
    private CSOnMvItemColorChangedListener mOnItemColorChangedListener;

    public CSColorGalleryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.cs_view_colorgallery, (ViewGroup) this, true);
        initView();
    }

    private void initView() {
        this.mBMColorAdapter = new CSColorAdapter(this.mContext);
        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        this.mGallery = gallery;
        gallery.setAdapter((SpinnerAdapter) this.mBMColorAdapter);
        this.mGallery.setUnselectedAlpha(1.1f);
        this.mGallery.setSelection(CSSysColors.length / 2);
        this.mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.magic.video.editor.effect.cut.utils.bg.CSColorGalleryView.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (CSColorGalleryView.this.mOnColorChangedListener != null) {
                    CSColorGalleryView.this.mOnColorChangedListener.onColorChanged(CSSysColors.getColor(i));
                }
                if (CSColorGalleryView.this.mOnItemColorChangedListener != null) {
                    CSColorGalleryView.this.mOnItemColorChangedListener.onColorChanged(CSSysColors.getColor(i), CSColorGalleryView.this);
                }
            }
        });
        this.mGalleryPointerView = (CSGalleryPointerView) findViewById(R.id.pointer);
    }

    public void setListener(CSOnMvColorChangedListener cSOnMvColorChangedListener) {
        this.mOnColorChangedListener = cSOnMvColorChangedListener;
    }

    public void setListener(CSOnMvItemColorChangedListener cSOnMvItemColorChangedListener) {
        this.mOnItemColorChangedListener = cSOnMvItemColorChangedListener;
    }

    public void setGalleryItemSize(int i, int i2, int i3, boolean z) {
        if (z) {
            this.mGallery.setLayoutParams(new LayoutParams(-1, CSScreenInfoUtil.dip2px(this.mContext, i2), 80));
        } else {
            this.mGallery.setLayoutParams(new LayoutParams(-1, CSScreenInfoUtil.dip2px(this.mContext, i2), 48));
        }
        this.mGallery.setSpacing(CSScreenInfoUtil.dip2px(this.mContext, i3));
        this.mBMColorAdapter.setItemSize(i, i2);
        this.mGalleryPointerView.setPointerItemSize(i, i2);
        if (z) {
            return;
        }
        this.mGalleryPointerView.setPointToBottom(false);
    }

    public void setPointTo(int i) {
        this.mGallery.setSelection(i);
    }

    public void setGalleryPointerViewVisibility(boolean z) {
        CSGalleryPointerView cSGalleryPointerView = this.mGalleryPointerView;
        if (cSGalleryPointerView != null) {
            if (z) {
                cSGalleryPointerView.setVisibility(View.VISIBLE);
            } else {
                cSGalleryPointerView.setVisibility(View.INVISIBLE);
            }
            invalidate();
        }
    }
}
