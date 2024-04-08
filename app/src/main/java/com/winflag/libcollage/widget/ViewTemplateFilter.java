package com.winflag.libcollage.widget;

import android.content.Context;
import android.view.LayoutInflater;
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
public class ViewTemplateFilter extends FrameLayout {
    private Context mContext;
    private FilterManager mFiterManager;
    private OnTemplateFilterViewListener mListener;
    private int mPos;
    private RecyclerView recyclerView;
    private CollageRecyclerViewAdapter recyclerViewAdapter;

    /* loaded from: classes.dex */
    public interface OnTemplateFilterViewListener {
        void onFilterClick(GPUFilterRes gPUFilterRes, int i, boolean z);
    }

    public void dispose() {
    }

    public ViewTemplateFilter(Context context, int i) {
        super(context);
        this.mPos = 0;
        this.mContext = context;
        this.mPos = i;
        init(context);
    }

    public void setOnTemplateFilterViewListener(OnTemplateFilterViewListener onTemplateFilterViewListener) {
        this.mListener = onTemplateFilterViewListener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_template_filter, (ViewGroup) this, true);
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
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, 0, false));
        this.recyclerViewAdapter.setSelectedPos(this.mPos);
        this.recyclerViewAdapter.setOnRecyclerViewItemClikListener(new CollageRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.winflag.libcollage.widget.ViewTemplateFilter.1
            @Override // com.winflag.libcollage.adapter.CollageRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                if (ViewTemplateFilter.this.mListener != null) {
                    ViewTemplateFilter.this.mListener.onFilterClick((GPUFilterRes) dMWBRes, i, z);
                }
            }
        });
    }

    public CollageRecyclerViewAdapter getRecyclerViewAdapter() {
        return this.recyclerViewAdapter;
    }
}
