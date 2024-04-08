package com.winflag.libsquare.uiview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;
import com.winflag.libsquare.adapter.CSSquareRecyclerViewAdapter;
import com.winflag.libsquare.manager.CSSquareUiFilterManager;
import com.winflag.libsquare.res.CSListNaitveAdRes;

/* loaded from: classes3.dex */
public class CSSquareUiFilterToolBarView extends FrameLayout {
    private CSListNaitveAdRes listNaitveAdRes;
    private Context mContext;
    private CSSquareUiFilterManager mFiterManager;
    private onSquareUiFilterToolBarViewListener mListener;
    private int mPos;
    private RecyclerView recyclerView;
    private CSSquareRecyclerViewAdapter recyclerViewAdapter;

    /* loaded from: classes3.dex */
    public interface onSquareUiFilterToolBarViewListener {
        void onFilterClick(GPUFilterRes gPUFilterRes, int i, boolean z);
    }

    public void dispose() {
    }

    public CSSquareUiFilterToolBarView(Context context, int i) {
        super(context);
        this.mPos = 0;
        this.mContext = context;
        this.mPos = i;
        init(context);
    }

    public CSSquareUiFilterToolBarView(Context context, int i, CSListNaitveAdRes cSListNaitveAdRes) {
        super(context);
        this.mPos = 0;
        this.mContext = context;
        this.mPos = i;
        this.listNaitveAdRes = cSListNaitveAdRes;
        init(context);
    }

    public void setOnSquareUiFilterToolBarViewListener(onSquareUiFilterToolBarViewListener onsquareuifiltertoolbarviewlistener) {
        this.mListener = onsquareuifiltertoolbarviewlistener;
    }

    private void init(Context context) {
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.square_ui_filter_view, (ViewGroup) this, true);
        init();
    }

    private void init() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        CSListNaitveAdRes cSListNaitveAdRes = this.listNaitveAdRes;
        if (cSListNaitveAdRes != null && cSListNaitveAdRes.getListNativeAdInterface().getIsSuccess()) {
            this.mFiterManager = new CSSquareUiFilterManager(this.mContext, this.listNaitveAdRes);
        } else {
            this.mFiterManager = new CSSquareUiFilterManager(this.mContext);
        }
        CSSquareRecyclerViewAdapter cSSquareRecyclerViewAdapter = new CSSquareRecyclerViewAdapter(this.mContext, this.mFiterManager.getResList());
        this.recyclerViewAdapter = cSSquareRecyclerViewAdapter;
        this.recyclerView.setAdapter(cSSquareRecyclerViewAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false));
        this.recyclerViewAdapter.setSelectedPos(this.mPos);
        this.recyclerViewAdapter.setOnRecyclerViewItemClikListener(new CSSquareRecyclerViewAdapter.onRecyclerViewItemClikListener() { // from class: com.winflag.libsquare.uiview.CSSquareUiFilterToolBarView.1
            @Override // com.winflag.libsquare.adapter.CSSquareRecyclerViewAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, DMWBRes dMWBRes, boolean z) {
                if (CSSquareUiFilterToolBarView.this.mListener != null) {
                    CSSquareUiFilterToolBarView.this.mListener.onFilterClick((GPUFilterRes) dMWBRes, i, z);
                }
            }
        });
    }

    public CSSquareRecyclerViewAdapter getRecyclerViewAdapter() {
        return this.recyclerViewAdapter;
    }
}
