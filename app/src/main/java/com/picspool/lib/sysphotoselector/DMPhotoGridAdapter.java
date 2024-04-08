package com.picspool.lib.sysphotoselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.picspool.lib.service.DMImageMediaItem;
import com.picspool.lib.view.DMPhotoItemView;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMPhotoGridAdapter extends BaseAdapter {
    private static final String TAG = "DMPhotoGridAdapter";
    private final Context mContext;
    private LayoutInflater mInflater;
    List<DMImageMediaItem> mItems;
    private GridView mGridView = null;
    private int mThumbBitmapWidth = 0;
    private int mMaxCacheNum = 0;
    HashMap<DMPhotoItemView, DMPhotoItemView> bitmapHash = new HashMap<>();
    private List<String> selectImgIdList = new ArrayList();
    private AbsListView.LayoutParams mImageViewLayoutParams = new AbsListView.LayoutParams(-1, -1);

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

    public void removeFromSelectImgList(String str) {
        if (this.selectImgIdList.contains(str)) {
            this.selectImgIdList.remove(str);
        }
    }

    public void addToSelectImgList(String str) {
        this.selectImgIdList.add(str);
    }

    public void resetSelectImgList(List<String> list) {
        this.selectImgIdList.clear();
        for (String str : list) {
            this.selectImgIdList.add(str);
        }
    }

    public DMPhotoGridAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
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
        for (DMPhotoItemView dMPhotoItemView : this.bitmapHash.keySet()) {
            dMPhotoItemView.clear();
            if (!z) {
                z = true;
                dMPhotoItemView.clearImageLoader();
            }
        }
    }

    public void dispose() {
        boolean z = false;
        for (DMPhotoItemView dMPhotoItemView : this.bitmapHash.keySet()) {
            dMPhotoItemView.clear();
            if (!z) {
                z = true;
                dMPhotoItemView.shutDwonImageLoader();
            }
        }
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        DMImageMediaItem dMImageMediaItem = this.mItems.get(i);
        if (view == null) {
            view = new DMPhotoItemView(this.mContext);
        } else {
            DMPhotoItemView dMPhotoItemView = (DMPhotoItemView) view;
        }
        DMPhotoItemView dMPhotoItemView2 = (DMPhotoItemView) view;
        if (this.bitmapHash.get(dMPhotoItemView2) == null) {
            this.bitmapHash.put(dMPhotoItemView2, dMPhotoItemView2);
        }
        if (this.mGridView != null) {
            View findViewById = dMPhotoItemView2.findViewById(R.id.imgView);
            findViewById.setTag("GridViewImageView" + dMImageMediaItem.getImgId());
            dMPhotoItemView2.setGridView(this.mGridView);
        }
        dMPhotoItemView2.setDataItem(dMImageMediaItem, this.mThumbBitmapWidth, this.mMaxCacheNum);
        if (this.selectImgIdList.contains(dMImageMediaItem.getImgId())) {
            dMPhotoItemView2.findViewById(R.id.imgSelectView).setVisibility(View.VISIBLE);
        } else {
            dMPhotoItemView2.findViewById(R.id.imgSelectView).setVisibility(View.INVISIBLE);
        }
        ImageView imageView = (ImageView) dMPhotoItemView2.findViewById(R.id.imgView);
        if (i == 0 && imageView != null && dMImageMediaItem.isCamera()) {
            imageView.setImageResource(R.drawable.dm_img_camera);
        }
        return view;
    }
}
