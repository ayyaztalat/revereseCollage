package com.photoart.libsticker.sticker2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.sky.testproject.R;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DMStickerPagerAdapter extends PagerAdapter {
    private List<GridView> gridViewList = new ArrayList();
    private Context mContext;

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public DMStickerPagerAdapter(Context context) {
        DMStickerModeManager.StickerMode[] values;
        this.mContext = context;
        for (DMStickerModeManager.StickerMode stickerMode : DMStickerModeManager.StickerMode.values()) {
            if (stickerMode != DMStickerModeManager.StickerMode.STICKERALL) {
                GridView gridView = (GridView) LayoutInflater.from(this.mContext).inflate(R.layout.dm_sticker_gridview, (ViewGroup) null, false);
                gridView.setTag(stickerMode);
                DMStikcerListAdapter dMStikcerListAdapter = new DMStikcerListAdapter(this.mContext);
                dMStikcerListAdapter.setStickerMode(stickerMode);
                gridView.setAdapter((ListAdapter) dMStikcerListAdapter);
                this.gridViewList.add(gridView);
            }
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        DMStikcerListAdapter dMStikcerListAdapter;
        GridView gridView = this.gridViewList.get(i);
        if (gridView != null && (dMStikcerListAdapter = (DMStikcerListAdapter) gridView.getAdapter()) != null) {
            dMStikcerListAdapter.dispose();
        }
        viewGroup.removeView(gridView);
    }

    public void dispose() {
        DMStikcerListAdapter dMStikcerListAdapter;
        for (GridView gridView : this.gridViewList) {
            if (gridView != null && (dMStikcerListAdapter = (DMStikcerListAdapter) gridView.getAdapter()) != null) {
                dMStikcerListAdapter.dispose();
            }
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        List<GridView> list = this.gridViewList;
        if (list == null || list.size() < 0) {
            return 0;
        }
        return this.gridViewList.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        return super.getItemPosition(obj);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        viewGroup.addView(this.gridViewList.get(i));
        return this.gridViewList.get(i);
    }

    public void setStickerOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        for (GridView gridView : this.gridViewList) {
            gridView.setOnItemClickListener(onItemClickListener);
        }
    }
}
