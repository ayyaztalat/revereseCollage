package com.picspool.lib.sysphotoselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import java.util.HashMap;
import java.util.List;
import com.picspool.lib.service.DMImageMediaItem;
import com.picspool.lib.view.DMCommonPhotoItemView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMCommonPhotoGridAdapter extends BaseAdapter {
    public static final int MULTI = 1;
    public static final int SINGLE = 2;
    private static final String TAG = "DMCommonPhotoGridAdapter";
    HashMap<DMCommonPhotoItemView, DMCommonPhotoItemView> bitmapHash;
    private int isMultiselector;
    private final Context mContext;
    private GridView mGridView;
    private AbsListView.LayoutParams mImageViewLayoutParams;
    private LayoutInflater mInflater;
    List<DMImageMediaItem> mItems;
    private int mMaxCacheNum;
    private int mThumbBitmapWidth;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        return 0;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return 2;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    public DMCommonPhotoGridAdapter(Context context) {
        this.mGridView = null;
        this.mThumbBitmapWidth = 0;
        this.mMaxCacheNum = 0;
        this.isMultiselector = 0;
        this.bitmapHash = new HashMap<>();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mImageViewLayoutParams = new AbsListView.LayoutParams(-1, -1);
    }

    public DMCommonPhotoGridAdapter(Context context, int i) {
        this.mGridView = null;
        this.mThumbBitmapWidth = 0;
        this.mMaxCacheNum = 0;
        this.isMultiselector = 0;
        this.bitmapHash = new HashMap<>();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mImageViewLayoutParams = new AbsListView.LayoutParams(-1, -1);
        this.isMultiselector = i;
    }

    public void setThumbBitmapInfo(int i, int i2) {
        this.mThumbBitmapWidth = i;
        this.mMaxCacheNum = i2;
    }

    public void setGridView(GridView gridView) {
        this.mGridView = gridView;
    }

    public void setPhotoItems(List<DMImageMediaItem> list) {
        this.mItems = list;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<DMImageMediaItem> list = this.mItems;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.mItems.get(i);
    }

    public void clearAll() {
        boolean z = false;
        for (DMCommonPhotoItemView dMCommonPhotoItemView : this.bitmapHash.keySet()) {
            dMCommonPhotoItemView.clear();
            if (!z) {
                z = true;
                dMCommonPhotoItemView.clearImageLoader();
            }
        }
    }

    public void dispose() {
        boolean z = false;
        for (DMCommonPhotoItemView dMCommonPhotoItemView : this.bitmapHash.keySet()) {
            dMCommonPhotoItemView.clear();
            if (!z) {
                z = true;
                dMCommonPhotoItemView.shutDwonImageLoader();
            }
        }
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        DMImageMediaItem dMImageMediaItem = this.mItems.get(i);
        if (view == null) {
            view = new DMCommonPhotoItemView(this.mContext);
        } else {
            DMCommonPhotoItemView dMCommonPhotoItemView = (DMCommonPhotoItemView) view;
        }
        DMCommonPhotoItemView dMCommonPhotoItemView2 = (DMCommonPhotoItemView) view;
        if (this.bitmapHash.get(dMCommonPhotoItemView2) == null) {
            this.bitmapHash.put(dMCommonPhotoItemView2, dMCommonPhotoItemView2);
        }
        if (this.mGridView != null) {
            View findViewById = dMCommonPhotoItemView2.findViewById(R.id.imgView);
            findViewById.setTag("GridViewImageView" + dMImageMediaItem.getImgId());
            dMCommonPhotoItemView2.setGridView(this.mGridView);
        }
        dMCommonPhotoItemView2.setDataItem(dMImageMediaItem, this.mThumbBitmapWidth, this.mMaxCacheNum);
        if (this.isMultiselector == 1) {
            if (DMCommonSelectedManager.getInstance().isContainsKey(dMImageMediaItem.getImgId())) {
                dMCommonPhotoItemView2.setMultiSelectViewVisable(true, DMCommonSelectedManager.getInstance().getKeyValue(dMImageMediaItem.getImgId()));
            } else {
                dMCommonPhotoItemView2.setMultiSelectViewVisable(false, 0);
            }
        } else if (DMCommonSelectedManager.getInstance().isContainsKey(dMImageMediaItem.getImgId())) {
            dMCommonPhotoItemView2.setSingleSelectViewVisable(true, DMCommonSelectedManager.getInstance().getKeyValue(dMImageMediaItem.getImgId()));
        } else {
            dMCommonPhotoItemView2.setSingleSelectViewVisable(false, 0);
        }
        return view;
    }
}
