package com.winflag.libcollage.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;
import com.winflag.libcollage.adapter.CollageRecyclerViewAdapter;
import com.winflag.libcollage.manager.TemplateManager;
import com.winflag.libcollage.resource.TemplateRes;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ViewTemplateHorizonList extends FrameLayout implements AdapterView.OnItemClickListener {
    String TAG;
    View bg_function_area;
    View layout_mask;
    View layout_pager;
    View layout_resize_container;
    private Context mContext;
    public OnTemplateChangedListener mListener;
    private TemplateManager mManager;
    private RecyclerView recyclerView;
    private CollageRecyclerViewAdapter recyclerViewAdapter;
    View vSel_Template;
    View vTemplate;

    /* loaded from: classes.dex */
    public interface OnTemplateChangedListener {
        void onTemplateChanged(DMWBRes dMWBRes, int i, boolean z);
    }

    public void dispose() {
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    public CollageRecyclerViewAdapter getRecyclerViewAdapter() {
        return this.recyclerViewAdapter;
    }

    public ViewTemplateHorizonList(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = "CollageTemplateBarView";
        this.mContext = context;
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_collage_template, (ViewGroup) this, true);
    }

    public void setManager(TemplateManager templateManager) {
        if (templateManager == null) {
            return;
        }
        this.mManager = templateManager;
        setAdapter();
    }

    private void setAdapter() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        ArrayList arrayList = new ArrayList();
        for (TemplateRes templateRes : this.mManager.getResList()) {
            arrayList.add(templateRes);
        }
        CollageRecyclerViewAdapter collageRecyclerViewAdapter = new CollageRecyclerViewAdapter(this.mContext, arrayList, this.recyclerView);
        this.recyclerViewAdapter = collageRecyclerViewAdapter;
        collageRecyclerViewAdapter.setShowText(false);
        this.recyclerView.setAdapter(this.recyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.recyclerViewAdapter.setOnRecyclerViewItemClikListener(new CollageRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.winflag.libcollage.widget.ViewTemplateHorizonList.1
            @Override // com.winflag.libcollage.adapter.CollageRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                if (ViewTemplateHorizonList.this.mListener != null) {
                    ViewTemplateHorizonList.this.mListener.onTemplateChanged(dMWBRes, i, z);
                }
            }
        });
    }

    public void setOnTemplateChangedListener(OnTemplateChangedListener onTemplateChangedListener) {
        this.mListener = onTemplateChangedListener;
    }
}
