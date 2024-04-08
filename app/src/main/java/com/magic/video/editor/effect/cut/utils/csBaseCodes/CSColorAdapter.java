package com.magic.video.editor.effect.cut.utils.csBaseCodes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import com.magic.video.editor.effect.cut.utils.CSScreenInfoUtil;

/* renamed from: com.magic.video.editor.effect.cut.utils.bg.CSColorAdapter */
/* loaded from: classes2.dex */
public class CSColorAdapter extends BaseAdapter {
    private Context mContext;
    private int mItemWidth = 0;
    private int mItemHeight = 0;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public CSColorAdapter(Context context) {
        this.mContext = context;
    }

    public void setItemSize(int i, int i2) {
        this.mItemWidth = CSScreenInfoUtil.dip2px(this.mContext, i);
        this.mItemHeight = CSScreenInfoUtil.dip2px(this.mContext, i2);
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return CSSysColors.length;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        CSBorderImageView cSBorderImageView;
        if (view == null) {
            cSBorderImageView = new CSBorderImageView(this.mContext);
            cSBorderImageView.setLayoutParams(new Gallery.LayoutParams(this.mItemWidth, this.mItemHeight));
        } else {
            cSBorderImageView = (CSBorderImageView) view;
        }
        cSBorderImageView.setBackgroundColor(CSSysColors.getColor(i));
        return cSBorderImageView;
    }
}
