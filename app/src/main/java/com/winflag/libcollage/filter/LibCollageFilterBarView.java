package com.winflag.libcollage.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.picspool.lib.p017io.DMBitmapIoCache;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.view.DMWBOnResourceChangedListener;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class LibCollageFilterBarView extends RelativeLayout {
    private LibCollageViewSelectorFilter bar_effect;

    /* renamed from: bg */
    private View f1766bg;
    private OnFilterBarViewListener mListener;
    protected Bitmap mSrcBitmap;

    /* loaded from: classes.dex */
    public interface OnFilterBarViewListener {
        void onFilterBarDisappear();

        void resourceFilterChanged(DMWBRes dMWBRes, String str, int i, int i2);
    }

    public LibCollageFilterBarView(Context context, Bitmap bitmap) {
        super(context);
        this.mSrcBitmap = bitmap;
        init(context);
    }

    public LibCollageFilterBarView(Context context, AttributeSet attributeSet, Bitmap bitmap) {
        super(context, attributeSet);
        this.mSrcBitmap = bitmap;
        init(context);
    }

    public void setOnFilterBarViewListener(OnFilterBarViewListener onFilterBarViewListener) {
        this.mListener = onFilterBarViewListener;
    }

    protected void loadImage(String str) {
        Bitmap bitmap = DMBitmapIoCache.getBitmap(str);
        this.mSrcBitmap = bitmap;
        if (bitmap == null) {
            Toast.makeText(getContext(), "No Enough Storage !", Toast.LENGTH_LONG).show();
        }
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.collage_lib_collage_view_filter, (ViewGroup) this, true);
        LibCollageViewSelectorFilter libCollageViewSelectorFilter = this.bar_effect;
        if (libCollageViewSelectorFilter != null) {
            libCollageViewSelectorFilter.dispose();
        }
        this.bar_effect = null;
        LibCollageViewSelectorFilter libCollageViewSelectorFilter2 =  findViewById(R.id.viewSelectorFilter);
        this.bar_effect = libCollageViewSelectorFilter2;
        libCollageViewSelectorFilter2.setSrcBitmap(this.mSrcBitmap);
        this.bar_effect.initData();
        this.bar_effect.setWBOnResourceChangedListener(new DMWBOnResourceChangedListener() { // from class: com.winflag.libcollage.filter.LibCollageFilterBarView.1
            @Override // org.picspool.lib.resource.view.DMWBOnResourceChangedListener
            public void resourceChanged(DMWBRes dMWBRes, String str, int i, int i2) {
                if (LibCollageFilterBarView.this.mListener != null) {
                    LibCollageFilterBarView.this.mListener.resourceFilterChanged(dMWBRes, str, i, i2);
                }
            }
        });
        View findViewById = findViewById(R.id.bg);
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.filter.LibCollageFilterBarView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
            }
        });
    }

    public void dispose() {
        LibCollageViewSelectorFilter libCollageViewSelectorFilter = this.bar_effect;
        if (libCollageViewSelectorFilter != null) {
            libCollageViewSelectorFilter.dispose();
        }
        this.bar_effect = null;
    }
}
