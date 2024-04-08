package com.winflag.libcollage.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.picspool.instafilter.activity.part.FilterViewScrollSelectorBase;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.manager.DMWBManager;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.picspool.lib.resource.widget.DMWBScrollBarArrayAdapter;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class LibCollageViewSelectorFilter extends FilterViewScrollSelectorBase {
    LibCollageFilterArtManager mArtManager;
    private Bitmap mSrc;

    public LibCollageViewSelectorFilter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mArtManager = new LibCollageFilterArtManager(context);
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.collage_lib_collage_sel_filter, (ViewGroup) this, true);
        this.scrollView = (DMWBHorizontalListView) findViewById(R.id.horizontalListView2);
        setDataAdapter(this.mArtManager);
    }

    public void setSrcBitmap(Bitmap bitmap) {
        this.mSrc = centerSquareScaleBitmap(bitmap, 130);
    }

    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int i) {
        if (bitmap == null || i <= 0) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= i || height <= i) {
            return bitmap;
        }
        int max = (Math.max(width, height) * i) / Math.min(width, height);
        int i2 = width > height ? max : i;
        if (width > height) {
            max = i;
        }
        try {
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, i2, max, true);
            Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap, (i2 - i) / 2, (max - i) / 2, i, i);
            createScaledBitmap.recycle();
            return createBitmap;
        } catch (Exception unused) {
            return null;
        }
    }

    public void initData() {
        if (this.mArtManager == null) {
            this.mArtManager = new LibCollageFilterArtManager(getContext());
        }
        setDataAdapter(this.mArtManager);
    }

    @Override // org.picspool.instafilter.activity.part.FilterViewScrollSelectorBase
    public void setDataAdapter(DMWBManager dMWBManager) {
        int count = dMWBManager.getCount();
        GPUFilterRes[] gPUFilterResArr = new GPUFilterRes[count];
        for (int i = 0; i < count; i++) {
            gPUFilterResArr[i] = (GPUFilterRes) dMWBManager.getRes(i);
            gPUFilterResArr[i].setSRC(this.mSrc);
        }
        if (this.scrollDataAdapter != null) {
            this.scrollDataAdapter.dispose();
            this.scrollDataAdapter = null;
        }
        this.scrollDataAdapter = new DMWBScrollBarArrayAdapter(getContext(), gPUFilterResArr);
        this.scrollDataAdapter.setImageBorderViewLayout(62, 45, 60);
        this.scrollDataAdapter.setImageViewScaleType(ImageView.ScaleType.CENTER_CROP);
        this.scrollDataAdapter.setTextViewBackColor(Color.argb(128, 255, 255, 255));
        this.scrollDataAdapter.setTextViewHeightDp(18);
        this.scrollDataAdapter.setTextViewWidthDp(45);
        this.scrollDataAdapter.setTextViewMarginBottom(0);
        this.scrollView.setAdapter((ListAdapter) this.scrollDataAdapter);
        this.scrollView.setOnItemClickListener(this);
    }

    @Override // org.picspool.instafilter.activity.part.FilterViewScrollSelectorBase, android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        DMWBRes item = this.scrollDataAdapter.getItem(i);
        if (this.mResListener != null) {
            this.mResListener.resourceChanged(item, "", this.scrollDataAdapter.getCount(), i);
        }
    }

    public void dispose() {
        if (this.mArtManager != null) {
            this.mArtManager = null;
        }
        if (this.scrollView != null) {
            this.scrollView.setAdapter((ListAdapter) null);
            this.scrollView = null;
        }
        if (this.scrollDataAdapter != null) {
            this.scrollDataAdapter.dispose();
        }
        this.scrollDataAdapter = null;
    }
}
