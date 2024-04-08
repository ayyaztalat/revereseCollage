package com.picspool.lib.onlinestore.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.onlinestore.activity.DMOnlineStickerStoreActivity;
import com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad;
import com.picspool.lib.onlinestore.resource.WBMaterialRes;
import com.picspool.lib.onlinestore.resource.manager.DMMaterialResManager;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMStoreDownListAdapter extends BaseAdapter {
    private Context context;
    private List<Holder> holders = new ArrayList();
    private DMOnlineStickerStoreActivity.ShowListMode listMode;
    private DMMaterialResManager resManager;
    private SelectedListener sListener;

    /* loaded from: classes3.dex */
    public interface SelectedListener {
        void onSelected(WBMaterialRes wBMaterialRes);
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return false;
    }

    public DMStoreDownListAdapter(Context context) {
        this.context = context;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        DMMaterialResManager dMMaterialResManager = this.resManager;
        if (dMMaterialResManager != null) {
            return dMMaterialResManager.getCount();
        }
        return 0;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_list_downl_item, (ViewGroup) null);
            view.setLayoutParams(new AbsListView.LayoutParams(DMScreenInfoUtil.screenWidth(this.context) - DMScreenInfoUtil.dip2px(this.context, 40.0f), (int) ((DMScreenInfoUtil.screenWidth(this.context) - DMScreenInfoUtil.dip2px(this.context, 10.0f)) / 1.6f)));
            holder = new Holder();
            holder.bgImage = (ImageView) view.findViewById(R.id.down_bg_img);
            holder.nameText = (TextView) view.findViewById(R.id.down_text);
            holder.downButton = view.findViewById(R.id.down_download_btn);
            holder.downBtnImage = (ImageView) view.findViewById(R.id.down_download_btn_img);
            view.setTag(holder);
            this.holders.add(holder);
        } else {
            holder = (Holder) view.getTag();
            holder.dispose();
        }
        DMMaterialResManager dMMaterialResManager = this.resManager;
        if (dMMaterialResManager != null) {
            final WBMaterialRes wBMaterialRes = (WBMaterialRes) dMMaterialResManager.getRes(i);
            holder.nameText.setText(wBMaterialRes.getName());
            holder.downButton.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.onlinestore.widget.DMStoreDownListAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (DMStoreDownListAdapter.this.sListener != null) {
                        DMStoreDownListAdapter.this.sListener.onSelected(wBMaterialRes);
                    }
                }
            });
            if (this.listMode == DMOnlineStickerStoreActivity.ShowListMode.noDownload) {
                holder.downBtnImage.setBackgroundResource(R.drawable.dm_img_sticker_download);
            } else {
                holder.downBtnImage.setBackgroundResource(R.drawable.dm_img_sticker_delete);
            }
            holder.setBg(wBMaterialRes, this.context);
        }
        return view;
    }

    public void dispose() {
        for (Holder holder : this.holders) {
            holder.dispose();
        }
        this.holders.clear();
    }

    /* loaded from: classes3.dex */
    private static class Holder {
        public ImageView bgImage;
        public ImageView downBtnImage;
        public View downButton;
        public TextView nameText;

        private Holder() {
        }

        public void setBg(final WBMaterialRes wBMaterialRes, Context context) {
            Bitmap iconBitmap = wBMaterialRes.getIconBitmap();
            if (iconBitmap == null || (iconBitmap != null && iconBitmap.isRecycled())) {
                wBMaterialRes.downloadIconOnlineRes(context, new DMAsyncDownloadFileLoad.AsyncDownloadFileListener() { // from class: com.picspool.lib.onlinestore.widget.DMStoreDownListAdapter.Holder.1
                    @Override // com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad.AsyncDownloadFileListener
                    public void onImageDownLoadFaile() {
                    }

                    @Override // com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad.AsyncDownloadFileListener
                    public void onProgressUpdate(Integer... numArr) {
                    }

                    @Override // com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad.AsyncDownloadFileListener
                    public void onPostExecute(Object obj) {
                        Bitmap iconBitmap2;
                        if (Holder.this.bgImage == null || (iconBitmap2 = wBMaterialRes.getIconBitmap()) == null || iconBitmap2.isRecycled()) {
                            return;
                        }
                        Holder.this.bgImage.setImageBitmap(iconBitmap2);
                    }
                });
            } else if (this.bgImage != null) {
                dispose();
                this.bgImage.setImageBitmap(iconBitmap);
            }
        }

        public void dispose() {
            recycleImageView(this.bgImage);
            recycleImageView(this.downBtnImage);
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
    }

    public SelectedListener getsListener() {
        return this.sListener;
    }

    public void setsListener(SelectedListener selectedListener) {
        this.sListener = selectedListener;
    }

    public DMMaterialResManager getResManager() {
        return this.resManager;
    }

    public void setResManager(DMMaterialResManager dMMaterialResManager, DMOnlineStickerStoreActivity.ShowListMode showListMode) {
        this.resManager = dMMaterialResManager;
        this.listMode = showListMode;
    }
}
