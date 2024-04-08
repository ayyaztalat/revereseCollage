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

import com.picspool.lib.onlinestore.asyncload.DMAsyncDownloadFileLoad;
import com.picspool.lib.onlinestore.resource.WBMaterialRes;
import com.picspool.lib.onlinestore.resource.manager.DMMaterialResManager;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMBgListAdapter extends BaseAdapter {
    private Context context;
    private DMMaterialResManager resManager;
    private SelectedListener sListener;
    private BgAdapterType bgAdapterType = BgAdapterType.ONLINE;
    private List<Holder> holders = new ArrayList();

    /* loaded from: classes3.dex */
    public interface SelectedListener {
        void onSelected(WBMaterialRes wBMaterialRes);
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return null;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    /* loaded from: classes3.dex */
    public enum BgAdapterType {
        ONLINE(R.drawable.dm_img_bg_downlaod),
        LOCAL(R.drawable.dm_img_bg_del);
        
        int imgID;

        BgAdapterType(int i) {
            this.imgID = i;
        }
    }

    /* loaded from: classes3.dex */
    private static class Holder {
        ImageView bgImage;
        ImageView btnImage;
        TextView textName;

        private Holder() {
        }

        public void dispose() {
            recycleImageView(this.bgImage);
        }

        public void setBg(final WBMaterialRes wBMaterialRes, Context context) {
            Bitmap iconBitmap = wBMaterialRes.getIconBitmap();
            if (iconBitmap == null || (iconBitmap != null && iconBitmap.isRecycled())) {
                wBMaterialRes.downloadIconOnlineRes(context, new DMAsyncDownloadFileLoad.AsyncDownloadFileListener() { // from class: com.picspool.lib.onlinestore.widget.DMBgListAdapter.Holder.1
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

    public DMBgListAdapter(Context context) {
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
        final WBMaterialRes wBMaterialRes;
        if (view == null) {
            view = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dm_view_bg_list_item, (ViewGroup) null);
            view.setLayoutParams(new AbsListView.LayoutParams(DMScreenInfoUtil.screenWidth(this.context), (int) (DMScreenInfoUtil.screenWidth(this.context) / 2.87f)));
            holder = new Holder();
            view.setTag(holder);
            holder.bgImage = (ImageView) view.findViewById(R.id.bg_img);
            holder.btnImage = (ImageView) view.findViewById(R.id.btn_img);
            holder.textName = (TextView) view.findViewById(R.id.name_text);
            this.holders.add(holder);
        } else {
            holder = (Holder) view.getTag();
            holder.dispose();
        }
        DMMaterialResManager dMMaterialResManager = this.resManager;
        if (dMMaterialResManager != null && dMMaterialResManager.getCount() > i && (wBMaterialRes = (WBMaterialRes) this.resManager.getRes(i)) != null) {
            holder.setBg(wBMaterialRes, this.context);
            holder.btnImage.setImageResource(this.bgAdapterType.imgID);
            holder.btnImage.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.onlinestore.widget.DMBgListAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (DMBgListAdapter.this.sListener != null) {
                        DMBgListAdapter.this.sListener.onSelected(wBMaterialRes);
                    }
                }
            });
            holder.textName.setText(wBMaterialRes.getName());
        }
        return view;
    }

    public void dispose() {
        for (Holder holder : this.holders) {
            holder.dispose();
        }
        this.holders.clear();
        this.resManager = null;
    }

    public DMMaterialResManager getResManager() {
        return this.resManager;
    }

    public void setResManager(DMMaterialResManager dMMaterialResManager) {
        this.resManager = dMMaterialResManager;
    }

    public SelectedListener getsListener() {
        return this.sListener;
    }

    public void setsListener(SelectedListener selectedListener) {
        this.sListener = selectedListener;
    }

    public BgAdapterType getBgAdapterType() {
        return this.bgAdapterType;
    }

    public void setBgAdapterType(BgAdapterType bgAdapterType) {
        this.bgAdapterType = bgAdapterType;
    }
}
