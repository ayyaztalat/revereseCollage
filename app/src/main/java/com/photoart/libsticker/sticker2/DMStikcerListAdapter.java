package com.photoart.libsticker.sticker2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.sky.testproject.R;
import com.photoart.libsticker.sticker.DMSelectStickerRes;
import com.photoart.libsticker.sticker2.DMStickerModeManager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DMStikcerListAdapter extends BaseAdapter {
    private String curgroupItemName;
    private Context mContext;
    private DMStickerImageManager mManager;
    private Handler handler = new Handler();
    private DMStickerModeManager.StickerMode mStickerMode = DMStickerModeManager.StickerMode.STICKER1;
    private List<Holder> holderList = new ArrayList();
    private List<DMSelectStickerRes> selectResList = new ArrayList();

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public DMStikcerListAdapter(Context context) {
        this.mContext = context;
        this.mManager = DMStickerModeManager.getStickerImageManager(this.mContext, this.mStickerMode);
    }

    public DMStikcerListAdapter(Context context, String str, DMStickerImageRes dMStickerImageRes) {
        this.mContext = context;
        this.mManager = DMStickerModeManager.getStickerImageManager(this.mContext, DMStickerModeManager.getStickerModeByName(str), dMStickerImageRes);
    }

    public void setStickerMode(DMStickerModeManager.StickerMode stickerMode) {
        this.mStickerMode = stickerMode;
        this.mManager = DMStickerModeManager.getStickerImageManager(this.mContext, stickerMode);
    }

    public void setStickerMode(DMStickerModeManager.StickerMode stickerMode, DMStickerImageRes dMStickerImageRes) {
        String name = dMStickerImageRes.getName();
        this.mStickerMode = stickerMode;
        this.curgroupItemName = name;
        this.mManager = DMStickerModeManager.getStickerImageManager(this.mContext, stickerMode, dMStickerImageRes);
    }

    public void dispose() {
        for (Holder holder : this.holderList) {
            holder.dispose();
        }
        this.holderList.clear();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        DMStickerImageManager dMStickerImageManager = this.mManager;
        if (dMStickerImageManager != null) {
            return dMStickerImageManager.getCount();
        }
        return 0;
    }

    public DMStickerModeManager.StickerMode getStickerMode() {
        return this.mStickerMode;
    }

    public void resetSelectListOnLine(List<DMSelectStickerRes> list, DMStickerModeManager.StickerMode stickerMode, String str) {
        dispose();
        this.selectResList.clear();
        for (DMSelectStickerRes dMSelectStickerRes : list) {
            if (dMSelectStickerRes.resName == str) {
                this.selectResList.add(dMSelectStickerRes);
            }
        }
        notifyDataSetChanged();
    }

    public void resetSelectList(List<DMSelectStickerRes> list, DMStickerModeManager.StickerMode stickerMode) {
        dispose();
        this.selectResList.clear();
        for (DMSelectStickerRes dMSelectStickerRes : list) {
            if (dMSelectStickerRes.mode == stickerMode) {
                this.selectResList.add(dMSelectStickerRes);
            }
        }
        notifyDataSetChanged();
    }

    public void resetSelectList(List<DMSelectStickerRes> list, String str) {
        dispose();
        this.selectResList.clear();
        for (DMSelectStickerRes dMSelectStickerRes : list) {
            if (dMSelectStickerRes.resName == str) {
                this.selectResList.add(dMSelectStickerRes);
            }
        }
        notifyDataSetChanged();
    }

    private boolean shouldSelectImageShow(int i) {
        for (DMSelectStickerRes dMSelectStickerRes : this.selectResList) {
            if (this.mStickerMode == DMStickerModeManager.StickerMode.ONLINE) {
                if (dMSelectStickerRes.posInGridView == i && dMSelectStickerRes.resName == this.curgroupItemName) {
                    return true;
                }
            } else if (dMSelectStickerRes.posInGridView == i) {
                return true;
            }
        }
        return false;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.dm_sticker_list_item, viewGroup, false);
            holder = new Holder();
            holder.imageView = (ImageView) view.findViewById(R.id.list_item_image);
            holder.selectImageView = (ImageView) view.findViewById(R.id.list_item_select_img);
            view.setTag(holder);
            this.holderList.add(holder);
        } else {
            holder = (Holder) view.getTag();
            holder.dispose();
        }
        if (this.mManager != null) {
            holder.imageView.setImageBitmap(this.mManager.getRes(i).getIconBitmap(holder.imageView));
        }
        if (shouldSelectImageShow(i)) {
            holder.selectImageView.setVisibility(0);
        } else {
            holder.selectImageView.setVisibility(4);
        }
        return view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        public ImageView imageView;
        public ImageView selectImageView;

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
