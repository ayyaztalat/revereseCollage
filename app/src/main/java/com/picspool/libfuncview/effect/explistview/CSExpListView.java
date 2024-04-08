package com.picspool.libfuncview.effect.explistview;

import android.content.Context;
import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.effect.explistview.CSExpListAdapter;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSExpListView {
    private static final String TAG = "CSExpListView";
    private CSExpListAdapter mAdapter;
    private Context mContext;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private onItemClickListener onItemClickListener;
    private boolean move = false;
    private int mIndex = 0;
    private int lastleft = 0;
    private boolean isexpande = false;

    /* loaded from: classes.dex */
    public interface onItemClickListener {
        void onItemClicked(int i, int i2, DMWBRes dMWBRes);
    }

    public void initView(Context context, RecyclerView recyclerView, List<DMWBRes> list) {
        this.mContext = context;
        this.mRecyclerView = recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, 0, false);
        this.mLinearLayoutManager = linearLayoutManager;
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        CSExpListAdapter cSExpListAdapter = new CSExpListAdapter(list, this.mContext);
        this.mAdapter = cSExpListAdapter;
        this.mRecyclerView.setAdapter(cSExpListAdapter);
        this.mAdapter.setOnRecyclerViewItemClikListener(new CSExpListAdapter.onRecyclerViewItemClikListener() { // from class: com.picspool.libfuncview.effect.explistview.CSExpListView.1
            @Override // com.picspool.libfuncview.effect.explistview.CSExpListAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, boolean z) {
                if (z) {
                    CSExpListView.this.lastleft = 0;
                    CSExpListView.this.isexpande = true;
                    CSExpListView.this.move(i);
                    return;
                }
                CSExpListView.this.isexpande = false;
                int i2 = 0;
                while (i < CSExpListView.this.mAdapter.getItemCount()) {
                    i2 += CSExpListView.this.mRecyclerView.getChildAt(0).getWidth();
                    i++;
                }
                int width = CSExpListView.this.mRecyclerView.getWidth() - i2;
                if (width < 0) {
                    width = 0;
                }
                CSExpListView.this.mRecyclerView.smoothScrollBy(-(CSExpListView.this.lastleft - width), 0);
            }

            @Override // com.picspool.libfuncview.effect.explistview.CSExpListAdapter.onRecyclerViewItemClikListener
            public void onContentClick(int i, int i2, DMWBRes dMWBRes) {
                if (CSExpListView.this.onItemClickListener != null) {
                    CSExpListView.this.onItemClickListener.onItemClicked(i, i2, dMWBRes);
                }
            }
        });
        this.mRecyclerView.addOnScrollListener(new RecyclerViewListener());
        this.mRecyclerView.setItemAnimator(new CSExpListItemAnimator());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void move(int i) {
        if (i < 0 || i >= this.mAdapter.getItemCount()) {
            return;
        }
        this.mIndex = i;
        this.mRecyclerView.stopScroll();
        smoothMoveToPosition(i);
    }

    private void smoothMoveToPosition(final int i) {
        new Handler().postDelayed(new Runnable() { // from class: com.picspool.libfuncview.effect.explistview.CSExpListView.2
            @Override // java.lang.Runnable
            public void run() {
                int findFirstVisibleItemPosition = CSExpListView.this.mLinearLayoutManager.findFirstVisibleItemPosition();
                int findLastVisibleItemPosition = CSExpListView.this.mLinearLayoutManager.findLastVisibleItemPosition();
                int i2 = i;
                if (i2 <= findFirstVisibleItemPosition) {
                    CSExpListView.this.mRecyclerView.smoothScrollToPosition(i);
                } else if (i2 <= findLastVisibleItemPosition) {
                    CSExpListView.this.mRecyclerView.smoothScrollBy(CSExpListView.this.mRecyclerView.getChildAt(i - findFirstVisibleItemPosition).getLeft(), 0);
                } else {
                    CSExpListView.this.mRecyclerView.smoothScrollToPosition(i);
                    CSExpListView.this.move = true;
                }
            }
        }, 50L);
    }

    /* loaded from: classes.dex */
    class RecyclerViewListener extends RecyclerView.OnScrollListener {
        RecyclerViewListener() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            super.onScrollStateChanged(recyclerView, i);
            if (CSExpListView.this.move && i == 0) {
                CSExpListView.this.move = false;
                int findFirstVisibleItemPosition = CSExpListView.this.mIndex - CSExpListView.this.mLinearLayoutManager.findFirstVisibleItemPosition();
                if (findFirstVisibleItemPosition < 0 || findFirstVisibleItemPosition >= recyclerView.getChildCount()) {
                    return;
                }
                recyclerView.smoothScrollBy(recyclerView.getChildAt(findFirstVisibleItemPosition).getLeft(), 0);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
            if (CSExpListView.this.isexpande) {
                CSExpListView.this.lastleft += i;
            }
        }
    }

    public void setOnItemClickListener(onItemClickListener onitemclicklistener) {
        this.onItemClickListener = onitemclicklistener;
    }

    public void notifyDataSetChanged() {
        this.mAdapter.notifyDataSetChanged();
    }

    public CSExpListAdapter getmAdapter() {
        return this.mAdapter;
    }

    public void setmAdapter(CSExpListAdapter cSExpListAdapter) {
        this.mAdapter = cSExpListAdapter;
    }
}
