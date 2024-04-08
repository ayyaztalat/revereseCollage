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
import android.widget.TextView;
import com.sky.testproject.R;
import com.photoart.libsticker.sticker3.DMLibStickerManager;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes2.dex */
public class DMStickerGroupAdapterNew extends BaseAdapter {
    private DMStickerGroupManager groupManager;
    private Context mContext;
    private int selectPos = 0;
    private List<Holder> holderList = new ArrayList();

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DMStickerGroupAdapterNew(Context context) {
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
            view = LayoutInflater.from(this.mContext).inflate(R.layout.dm_view_sticker_group_item_new, viewGroup, false);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.sticker_item_image);
            holder.textView = (TextView) view.findViewById(R.id.sticker_item_text);
            holder.imageViewNew = (ImageView) view.findViewById(R.id.img_sticker_new);
            holder.imageViewLock = (ImageView) view.findViewById(R.id.img_sticker_lock);
            holder.layout = view.findViewById(R.id.sticker_item_layout);
            view.setTag(holder);
            this.holderList.add(holder);
        } else {
            holder = (Holder) view.getTag();
            holder.dispose();
        }
        if (this.groupManager != null) {
            holder.imageView.setImageBitmap(this.groupManager.getRes(i).getIconBitmap(holder.imageView, holder.imageViewNew));
            holder.textView.setText(this.groupManager.getRes(i).getName());
        }
        if (this.groupManager.getRes(i).isShowNewTag()) {
            holder.imageViewNew.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewNew.setVisibility(View.GONE);
        }
        if (this.groupManager.getRes(i).getIslock().booleanValue() && DMPreferencesUtil.get(this.mContext, DMLibStickerManager.CONFIG, this.groupManager.getRes(i).getName()) == null) {
            holder.imageViewLock.setVisibility(View.VISIBLE);
        } else {
            holder.imageViewLock.setVisibility(View.GONE);
        }
        return view;
    }

    public void setImageViewLockGone(int i) {
        this.holderList.get(i).imageViewLock.setVisibility(View.GONE);
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
        public ImageView imageViewLock;
        public ImageView imageViewNew;
        public View layout;
        public TextView textView;

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
