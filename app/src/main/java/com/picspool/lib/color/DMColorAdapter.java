package com.picspool.lib.color;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.picspool.lib.view.image.DMBorderImageView;

/* loaded from: classes3.dex */
public class DMColorAdapter extends BaseAdapter {
    private Context mContext;
    private int mItemWidth = 0;
    private int mItemHeight = 0;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DMColorAdapter(Context context) {
        this.mContext = context;
    }

    public void setItemSize(int i, int i2) {
        this.mItemWidth = DMScreenInfoUtil.dip2px(this.mContext, i);
        this.mItemHeight = DMScreenInfoUtil.dip2px(this.mContext, i2);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return DMSysColors.length;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        DMBorderImageView dMBorderImageView;
        if (view == null) {
            dMBorderImageView = new DMBorderImageView(this.mContext);
            dMBorderImageView.setLayoutParams(new Gallery.LayoutParams(this.mItemWidth, this.mItemHeight));
        } else {
            dMBorderImageView = (DMBorderImageView) view;
        }
        dMBorderImageView.setBackgroundColor(DMSysColors.getColor(i));
        return dMBorderImageView;
    }
}
