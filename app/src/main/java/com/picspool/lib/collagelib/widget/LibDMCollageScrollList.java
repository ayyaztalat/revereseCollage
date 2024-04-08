package com.picspool.lib.collagelib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import java.util.HashMap;
import java.util.Map;
import com.picspool.lib.collagelib.resource.LibDMTemplateWithImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.resource.widget.DMWBHorizontalListView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class LibDMCollageScrollList extends FrameLayout implements AdapterView.OnItemClickListener {
    String TAG;
    View bg_function_area;
    View layout_mask;
    View layout_pager;
    View layout_resize_container;
    public OnTemplateChangedListener mListener;
    private DMTemplateManager mManager;
    private HashMap<String, Bitmap> mSrcBitmaps;
    private DMWBHorizontalListView mTemplateList;
    LibDMCollageScrollAdapter scrollBarAdapter;
    View vSel_Template;
    View vTemplate;

    /* loaded from: classes3.dex */
    public interface OnTemplateChangedListener {
        void onTemplateChanged(Bitmap bitmap, DMWBRes dMWBRes);
    }

    public LibDMCollageScrollList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "CollageTemplateBarView";
        this.mSrcBitmaps = new HashMap<>();
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.dm_view_collage_template, (ViewGroup) this, true);
        this.mTemplateList = (DMWBHorizontalListView) findViewById(R.id.templateList);
    }

    public void setManager(DMTemplateManager dMTemplateManager) {
        if (dMTemplateManager == null) {
            this.mManager = dMTemplateManager;
            return;
        }
        this.mManager = dMTemplateManager;
        setAdapter();
    }

    public void setResBits(HashMap<String, Bitmap> hashMap) {
        for (Map.Entry<String, Bitmap> entry : this.mSrcBitmaps.entrySet()) {
            Bitmap value = entry.getValue();
            if (!hashMap.containsKey(entry.getKey()) && value != null && !value.isRecycled()) {
                value.recycle();
            }
        }
        this.mSrcBitmaps.clear();
        for (Map.Entry<String, Bitmap> entry2 : hashMap.entrySet()) {
            this.mSrcBitmaps.put(entry2.getKey(), entry2.getValue());
        }
    }

    private void setAdapter() {
        int count = this.mManager.getCount();
        LibDMTemplateWithImageRes[] libDMTemplateWithImageResArr = new LibDMTemplateWithImageRes[count];
        for (int i = 0; i < count; i++) {
            libDMTemplateWithImageResArr[i] = (LibDMTemplateWithImageRes) this.mManager.getRes(i);
            libDMTemplateWithImageResArr[i].setBitmaps(this.mSrcBitmaps);
            libDMTemplateWithImageResArr[i].setData();
        }
        this.scrollBarAdapter = null;
        LibDMCollageScrollAdapter libDMCollageScrollAdapter = new LibDMCollageScrollAdapter(getContext(), libDMTemplateWithImageResArr);
        this.scrollBarAdapter = libDMCollageScrollAdapter;
        this.mTemplateList.setAdapter((ListAdapter) libDMCollageScrollAdapter);
        this.mTemplateList.setOnItemClickListener(this);
    }

    public void setOnTemplateChangedListener(OnTemplateChangedListener onTemplateChangedListener) {
        this.mListener = onTemplateChangedListener;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        LibDMTemplateWithImageRes res = (LibDMTemplateWithImageRes) this.mManager.getRes(i);
        OnTemplateChangedListener onTemplateChangedListener = this.mListener;
        if (onTemplateChangedListener != null) {
            onTemplateChangedListener.onTemplateChanged(res.getIconBitmap(), res);
        }
    }

    public void dispose() {
        if (this.mManager != null) {
            this.mManager = null;
        }
        try {
            if (this.mTemplateList != null) {
                this.mTemplateList.setAdapter((ListAdapter) null);
                this.mTemplateList = null;
            }
            this.scrollBarAdapter = null;
        } catch (Throwable unused) {
        }
    }
}
