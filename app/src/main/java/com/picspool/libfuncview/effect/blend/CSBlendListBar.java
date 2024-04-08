package com.picspool.libfuncview.effect.blend;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.libfuncview.effect.CSEffectBarManager;
import com.picspool.libfuncview.effect.explistview.CSExpListAdapter;
import com.picspool.libfuncview.effect.explistview.CSExpListItemAnimator;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBlendListBar extends FrameLayout {
    private static final String TAG = "xlb";
    private boolean isexpande;
    private int lastleft;
    private FrameLayout ly_root;
    private CSExpListAdapter mAdapter;
    private Context mContext;
    private int mIndex;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;
    private boolean move;
    private onItemClickListener onItemClickListener;

    /* loaded from: classes.dex */
    public interface onItemClickListener {
        void onItemClicked(DMWBRes dMWBRes);
    }

    public CSBlendListBar(Context context) {
        super(context);
        this.move = false;
        this.mIndex = 0;
        this.lastleft = 0;
        this.isexpande = false;
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_effectlistbar, (ViewGroup) this, true);
        this.ly_root = (FrameLayout) findViewById(R.id.ly_filterbar_root);
        CSEffectBarManager cSEffectBarManager = new CSEffectBarManager(this.mContext);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext, RecyclerView.HORIZONTAL, false);
        this.mLinearLayoutManager = linearLayoutManager;
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        CSExpListAdapter cSExpListAdapter = new CSExpListAdapter(cSEffectBarManager.getGroupBMWBResList(), this.mContext);
        this.mAdapter = cSExpListAdapter;
        this.mRecyclerView.setAdapter(cSExpListAdapter);
        this.mAdapter.setOnRecyclerViewItemClikListener(new CSExpListAdapter.onRecyclerViewItemClikListener() { // from class: com.picspool.libfuncview.effect.blend.CSBlendListBar.1
            @Override // com.picspool.libfuncview.effect.explistview.CSExpListAdapter.onRecyclerViewItemClikListener
            public void onClick(int i, boolean z) {
                if (z) {
                    CSBlendListBar.this.lastleft = 0;
                    CSBlendListBar.this.isexpande = true;
                    CSBlendListBar.this.move(i);
                    return;
                }
                CSBlendListBar.this.isexpande = false;
                int i2 = 0;
                while (i < CSBlendListBar.this.mAdapter.getItemCount()) {
                    i2 += CSBlendListBar.this.mRecyclerView.getChildAt(0).getWidth();
                    i++;
                }
                int width = CSBlendListBar.this.mRecyclerView.getWidth() - i2;
                if (width < 0) {
                    width = 0;
                }
                CSBlendListBar.this.mRecyclerView.smoothScrollBy(-(CSBlendListBar.this.lastleft - width), 0);
            }

            @Override // com.picspool.libfuncview.effect.explistview.CSExpListAdapter.onRecyclerViewItemClikListener
            public void onContentClick(int i, int i2, DMWBRes dMWBRes) {
                if (CSBlendListBar.this.onItemClickListener != null) {
                    CSBlendListBar.this.onItemClickListener.onItemClicked(dMWBRes);
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
        new Handler().postDelayed(new Runnable() { // from class: com.picspool.libfuncview.effect.blend.CSBlendListBar.2
            @Override // java.lang.Runnable
            public void run() {
                int findFirstVisibleItemPosition = CSBlendListBar.this.mLinearLayoutManager.findFirstVisibleItemPosition();
                int findLastVisibleItemPosition = CSBlendListBar.this.mLinearLayoutManager.findLastVisibleItemPosition();
                int i2 = i;
                if (i2 <= findFirstVisibleItemPosition) {
                    CSBlendListBar.this.mRecyclerView.smoothScrollToPosition(i);
                } else if (i2 <= findLastVisibleItemPosition) {
                    CSBlendListBar.this.mRecyclerView.smoothScrollBy(CSBlendListBar.this.mRecyclerView.getChildAt(i - findFirstVisibleItemPosition).getLeft(), 0);
                } else {
                    CSBlendListBar.this.mRecyclerView.smoothScrollToPosition(i);
                    CSBlendListBar.this.move = true;
                }
            }
        }, 50L);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class RecyclerViewListener extends RecyclerView.OnScrollListener {
        RecyclerViewListener() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            super.onScrollStateChanged(recyclerView, i);
            if (CSBlendListBar.this.move && i == 0) {
                CSBlendListBar.this.move = false;
                int findFirstVisibleItemPosition = CSBlendListBar.this.mIndex - CSBlendListBar.this.mLinearLayoutManager.findFirstVisibleItemPosition();
                if (findFirstVisibleItemPosition < 0 || findFirstVisibleItemPosition >= recyclerView.getChildCount()) {
                    return;
                }
                recyclerView.smoothScrollBy(recyclerView.getChildAt(findFirstVisibleItemPosition).getLeft(), 0);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
            if (CSBlendListBar.this.isexpande) {
                CSBlendListBar.this.lastleft += i;
            }
        }
    }

    public void setOnItemClickListener(onItemClickListener onitemclicklistener) {
        this.onItemClickListener = onitemclicklistener;
    }
}
