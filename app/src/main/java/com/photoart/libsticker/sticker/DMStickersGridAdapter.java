package com.photoart.libsticker.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.sticker.enumoperations.DMStickerTypeOperation;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class DMStickersGridAdapter extends BaseAdapter {
    int actualHeight;
    private Context mContext;
    DMStickerTypeOperation sto;
    List<DMStickerRes> list = new ArrayList();
    private List<StickerViewHolder> holderList = new ArrayList();

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public void setContext(Context context) {
        this.mContext = context;
        this.actualHeight = (DMScreenInfoUtil.screenWidth(context) - 3) / 4;
    }

    public void initData(DMStickerManager dMStickerManager) {
        int count = dMStickerManager.getCount();
        for (int i = 0; i < count; i++) {
            this.list.add(dMStickerManager.getRes(i));
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<DMStickerRes> list = this.list;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.list.get(i);
    }

    public void clearAll() {
        List<DMStickerRes> list = this.list;
        if (list != null) {
            list.clear();
        }
        for (int i = 0; i < this.holderList.size(); i++) {
            StickerViewHolder stickerViewHolder = this.holderList.get(i);
            stickerViewHolder.img_icon.setImageBitmap(null);
            if (stickerViewHolder.iconBitmap != null && !stickerViewHolder.iconBitmap.isRecycled()) {
                stickerViewHolder.iconBitmap.recycle();
            }
            stickerViewHolder.iconBitmap = null;
        }
        this.holderList.clear();
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        StickerViewHolder stickerViewHolder;
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.dm_sticker_lib_icon_grid_item, viewGroup, false);
            stickerViewHolder = new StickerViewHolder();
            stickerViewHolder.img_icon = (ImageView) view.findViewById(R.id.img_icon);
            stickerViewHolder.frameLayout = (FrameLayout) view.findViewById(R.id.FrameLayout1);
            view.setTag(stickerViewHolder);
            this.holderList.add(stickerViewHolder);
        } else {
            stickerViewHolder = (StickerViewHolder) view.getTag();
            stickerViewHolder.img_icon.setImageBitmap(null);
            if (stickerViewHolder.iconBitmap != null && !stickerViewHolder.iconBitmap.isRecycled()) {
                stickerViewHolder.iconBitmap.recycle();
            }
            stickerViewHolder.iconBitmap = null;
        }
        stickerViewHolder.frameLayout.getLayoutParams().height = this.actualHeight;
        Bitmap iconBitmap = this.list.get(i).getIconBitmap();
        stickerViewHolder.iconBitmap = iconBitmap;
        stickerViewHolder.img_icon.setImageBitmap(iconBitmap);
        stickerViewHolder.img_icon.invalidate();
        return view;
    }

    /* loaded from: classes2.dex */
    public final class StickerViewHolder {
        public FrameLayout frameLayout;
        public Bitmap iconBitmap;
        public ImageView img_icon;

        public StickerViewHolder() {
        }
    }
}
