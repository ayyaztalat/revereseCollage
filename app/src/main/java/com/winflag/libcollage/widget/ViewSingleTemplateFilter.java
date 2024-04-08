package com.winflag.libcollage.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.lib.resource.DMWBRes;

import com.picspool.instafilter.resource.GPUFilterRes;
import com.sky.testproject.R;
import com.winflag.libcollage.adapter.CollageRecyclerViewAdapter;
import com.winflag.libcollage.manager.FilterManager;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ViewSingleTemplateFilter extends FrameLayout {
    private Context mContext;
    private FilterManager mFiterManager;
    private OnSingleTemplateFilterViewListener mListener;
    private RecyclerView recyclerView;
    private CollageRecyclerViewAdapter recyclerViewAdapter;

    /* loaded from: classes.dex */
    public interface OnSingleTemplateFilterViewListener {
        void onSingleClose();

        void onSingleFilterClick(GPUFilterRes gPUFilterRes, int i, boolean z);
    }

    public void dispose() {
    }

    public CollageRecyclerViewAdapter getRecyclerViewAdapter() {
        return this.recyclerViewAdapter;
    }

    public ViewSingleTemplateFilter(Context context) {
        super(context);
        this.mContext = context;
        init(context);
    }

    public ViewSingleTemplateFilter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init(context);
    }

    public void setOnSingleTemplateFilterViewListener(OnSingleTemplateFilterViewListener onSingleTemplateFilterViewListener) {
        this.mListener = onSingleTemplateFilterViewListener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_template_single_filter, (ViewGroup) this, true);
        init();
    }

    private void init() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mFiterManager = new FilterManager(this.mContext);
        ArrayList arrayList = new ArrayList();
        for (GPUFilterRes gPUFilterRes : this.mFiterManager.getResList()) {
            arrayList.add(gPUFilterRes);
        }
        CollageRecyclerViewAdapter collageRecyclerViewAdapter = new CollageRecyclerViewAdapter(this.mContext, arrayList, this.recyclerView);
        this.recyclerViewAdapter = collageRecyclerViewAdapter;
        this.recyclerView.setAdapter(collageRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.recyclerViewAdapter.setOnRecyclerViewItemClikListener(new CollageRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.winflag.libcollage.widget.ViewSingleTemplateFilter.1
            @Override // com.winflag.libcollage.adapter.CollageRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                if (ViewSingleTemplateFilter.this.mListener != null) {
                    ViewSingleTemplateFilter.this.mListener.onSingleFilterClick((GPUFilterRes) dMWBRes, i, z);
                }
            }
        });
        findViewById(R.id.ly_single_filter_back).setOnClickListener(new OnClickListener() { // from class: com.winflag.libcollage.widget.ViewSingleTemplateFilter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ViewSingleTemplateFilter.this.mListener != null) {
                    ViewSingleTemplateFilter.this.mListener.onSingleClose();
                }
            }
        });
    }
}
