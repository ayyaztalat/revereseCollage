package com.picspool.lib.collagelib.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.picspool.lib.collagelib.resource.LibDMTemplateWithImageRes;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;


/* loaded from: classes3.dex */
public class LibDMCollageScrollAdapter extends ArrayAdapter<Object> {
    private Context mContext;
    private LayoutInflater mInflater;

    public LibDMCollageScrollAdapter(Context context, DMWBRes[] dMWBResArr) {
        super(context, R.layout.dm_view_scroll_item, dMWBResArr);
        this.mInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        this.mContext = context;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.mInflater.inflate(R.layout.dm_view_scroll_item, viewGroup, false);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.item_icon);
        try {
            LibDMTemplateWithImageRes libDMTemplateWithImageRes = (LibDMTemplateWithImageRes) getItem(i);
            libDMTemplateWithImageRes.setContext(this.mContext);
            if (libDMTemplateWithImageRes instanceof DMWBImageRes) {
                imageView.setImageBitmap(libDMTemplateWithImageRes.getCustomBitmap());
            }
        } catch (Exception unused) {
        }
        return view;
    }
}
