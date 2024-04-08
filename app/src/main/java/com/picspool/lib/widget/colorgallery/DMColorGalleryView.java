package com.picspool.lib.widget.colorgallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;
import com.picspool.lib.color.DMColorAdapter;
import com.picspool.lib.color.DMSysColors;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.widget.listener.OnDMColorChangedListener;
import com.picspool.lib.widget.listener.OnDMItemColorChangedListener;
import com.picspool.lib.widget.pointer.DMGalleryPointerView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMColorGalleryView extends FrameLayout {
    private DMColorAdapter mBMColorAdapter;
    private Context mContext;
    private Gallery mGallery;
    private DMGalleryPointerView mGalleryPointerView;
    private OnDMColorChangedListener mOnColorChangedListener;
    private OnDMItemColorChangedListener mOnItemColorChangedListener;

    public DMColorGalleryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_colorgallery, (ViewGroup) this, true);
        initView();
    }

    private void initView() {
        this.mBMColorAdapter = new DMColorAdapter(this.mContext);
        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        this.mGallery = gallery;
        gallery.setAdapter((SpinnerAdapter) this.mBMColorAdapter);
        this.mGallery.setUnselectedAlpha(1.1f);
        this.mGallery.setSelection(DMSysColors.length / 2);
        this.mGallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.picspool.lib.widget.colorgallery.DMColorGalleryView.1
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (DMColorGalleryView.this.mOnColorChangedListener != null) {
                    DMColorGalleryView.this.mOnColorChangedListener.onColorChanged(DMSysColors.getColor(i));
                }
                if (DMColorGalleryView.this.mOnItemColorChangedListener != null) {
                    DMColorGalleryView.this.mOnItemColorChangedListener.onColorChanged(DMSysColors.getColor(i), DMColorGalleryView.this);
                }
            }
        });
        this.mGalleryPointerView = (DMGalleryPointerView) findViewById(R.id.pointer);
    }

    public void setListener(OnDMColorChangedListener onDMColorChangedListener) {
        this.mOnColorChangedListener = onDMColorChangedListener;
    }

    public void setListener(OnDMItemColorChangedListener onDMItemColorChangedListener) {
        this.mOnItemColorChangedListener = onDMItemColorChangedListener;
    }

    public void setGalleryItemSize(int i, int i2, int i3, boolean z) {
        if (z) {
            this.mGallery.setLayoutParams(new LayoutParams(-1, DMScreenInfoUtil.dip2px(this.mContext, i2), 80));
        } else {
            this.mGallery.setLayoutParams(new LayoutParams(-1, DMScreenInfoUtil.dip2px(this.mContext, i2), 48));
        }
        this.mGallery.setSpacing(DMScreenInfoUtil.dip2px(this.mContext, i3));
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
        DMGalleryPointerView dMGalleryPointerView = this.mGalleryPointerView;
        if (dMGalleryPointerView != null) {
            if (z) {
                dMGalleryPointerView.setVisibility(View.VISIBLE);
            } else {
                dMGalleryPointerView.setVisibility(View.INVISIBLE);
            }
            invalidate();
        }
    }
}
