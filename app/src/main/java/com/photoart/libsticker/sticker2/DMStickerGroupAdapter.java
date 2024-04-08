package com.photoart.libsticker.sticker2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sky.testproject.R;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DMStickerGroupAdapter extends BaseAdapter {
    private DMStickerGroupManager groupManager;
    private Context mContext;
    private int selectPos = 0;
    private List<Holder> holderList = new ArrayList();

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DMStickerGroupAdapter(Context context) {
        this.groupManager = DMStickerGroupManager.getSingletManager(context);
        this.mContext = context;
    }

    public void dispose() {
        for (Holder holder : this.holderList) {
            holder.dispose();
        }
        this.holderList.clear();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        DMStickerGroupManager dMStickerGroupManager = this.groupManager;
        if (dMStickerGroupManager != null) {
            return dMStickerGroupManager.getCount();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        DMStickerGroupManager dMStickerGroupManager = this.groupManager;
        if (dMStickerGroupManager != null) {
            return dMStickerGroupManager.getRes(i);
        }
        return null;
    }

    public int getSelectpos() {
        return this.selectPos;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.dm_view_sticker_group_item, viewGroup, false);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.sticker_item_image);
            holder.layout = view.findViewById(R.id.sticker_item_layout);
            view.setTag(holder);
            this.holderList.add(holder);
        } else {
            holder = (Holder) view.getTag();
            holder.dispose();
        }
        if (this.groupManager != null) {
            holder.imageView.setImageBitmap(this.groupManager.getRes(i).getIconBitmap());
        }
        if (i == this.selectPos) {
            holder.layout.setBackgroundResource(R.drawable.dm_sticker_group_icon_shape_press);
        } else {
            holder.layout.setBackgroundColor(0);
        }
        return view;
    }

    public void setSelectpos(int i) {
        this.selectPos = i;
        dispose();
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        public ImageView imageView;
        public View layout;

        private Holder() {
        }

        private void recycleImageView(ImageView imageView) {
            if (imageView != null) {
                Drawable drawable = imageView.getDrawable();
                imageView.setImageBitmap(null);
                if (drawable != null) {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bitmapDrawable.setCallback(null);
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    if (bitmap == null || bitmap.isRecycled()) {
                        return;
                    }
                    bitmap.recycle();
                }
            }
        }

        public void dispose() {
            recycleImageView(this.imageView);
        }
    }
}
