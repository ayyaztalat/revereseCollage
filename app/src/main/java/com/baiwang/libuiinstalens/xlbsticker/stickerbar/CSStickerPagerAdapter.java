package com.baiwang.libuiinstalens.xlbsticker.stickerbar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CSStickerPagerAdapter extends PagerAdapter {
    private CSStickerGridViewAdapter currentAdapter;
    private Context mContext;
    private List<RecyclerView> mItemViewList = new ArrayList();
    private List<CSStickerGridViewAdapter> mAdapters = new ArrayList();

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public void notifyAllData() {
    }

    public CSStickerPagerAdapter(Context context, List<CSStickerGroup> list) {
        this.mContext = context;
        for (int i = 0; i < list.size(); i++) {
            int ceil = (int) Math.ceil(list.get(i).getList_sticker().size() / CSXlbStickerBarView.grid_item_count);
            for (int i2 = 0; i2 < ceil; i2++) {
                RecyclerView recyclerView = new RecyclerView(this.mContext);
                recyclerView.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
                CSStickerGridViewAdapter cSStickerGridViewAdapter = new CSStickerGridViewAdapter(this.mContext, list.get(i), i2);
                recyclerView.setAdapter(cSStickerGridViewAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, CSXlbStickerBarView.grid_item_count / CSXlbStickerBarView.grid_line_count));
                this.mAdapters.add(cSStickerGridViewAdapter);
                this.mItemViewList.add(recyclerView);
            }
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mItemViewList.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        viewGroup.addView(this.mItemViewList.get(i));
        return this.mItemViewList.get(i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView(this.mItemViewList.get(i));
    }

    public CSStickerGridViewAdapter getCurrentAdapter() {
        return this.currentAdapter;
    }

    public List<CSStickerGridViewAdapter> getmAdapters() {
        return this.mAdapters;
    }
}
