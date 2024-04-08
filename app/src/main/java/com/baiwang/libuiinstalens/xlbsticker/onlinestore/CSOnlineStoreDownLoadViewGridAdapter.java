package com.baiwang.libuiinstalens.xlbsticker.onlinestore;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialRes;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.signature.ObjectKey;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;

/* loaded from: classes.dex */
public class CSOnlineStoreDownLoadViewGridAdapter extends RecyclerView.Adapter<CSOnlineStoreDownLoadViewGridAdapter.MyViewHolder> {
    private int icon_count;
    private Context mContext;
    private onABarViewItemClikListener mListener;
    private CSWBMaterialRes mRes;
    private int selectedPos = 0;
    private SimpleTarget<Bitmap> simpleTarget;

    /* loaded from: classes.dex */
    public interface onABarViewItemClikListener {
        void onClick(int i);
    }

    public CSOnlineStoreDownLoadViewGridAdapter(Context context, CSWBMaterialRes cSWBMaterialRes) {
        this.icon_count = 0;
        this.mContext = context;
        this.mRes = cSWBMaterialRes;
        this.icon_count = Integer.valueOf(cSWBMaterialRes.getContent_backup_1()).intValue();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_onlinestore_download_adapter_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.mRes, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.icon_count;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_main;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreDownLoadViewGridAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (CSOnlineStoreDownLoadViewGridAdapter.this.mListener != null) {
                        CSOnlineStoreDownLoadViewGridAdapter.this.mListener.onClick(MyViewHolder.this.getLayoutPosition());
                    }
                    CSOnlineStoreDownLoadViewGridAdapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    CSOnlineStoreDownLoadViewGridAdapter.this.notifyDataSetChanged();
                }
            });
            int screenWidth = DMScreenInfoUtil.screenWidth(CSOnlineStoreDownLoadViewGridAdapter.this.mContext);
            int i = screenWidth / 4;
            view.getLayoutParams().height = i;
            view.getLayoutParams().width = i;
            int i2 = screenWidth / 16;
            ((FrameLayout.LayoutParams) this.imageView_main.getLayoutParams()).setMargins(i2, i2, i2, i2);
        }

        public void setData(CSWBMaterialRes cSWBMaterialRes, int i) {
            String[] split = new String[0];
            String iconUriPath = cSWBMaterialRes.getIconUriPath();
            String str = "";
            if (iconUriPath.split("/").length > 0) {
                for (int i2 = 0; i2 < split.length - 1; i2++) {
                    str = str + split[i2] + "/";
                }
            }
            new DecimalFormat("000");
            String str2 = str + "icon/" + cSWBMaterialRes.getName() + "/" + (i + 1) + ".data";
            String[] split2 = iconUriPath.split("/");
            if (split2 != null && split2.length > 0) {
                iconUriPath = split2[split2.length - 1];
            }
            Glide.with(CSOnlineStoreDownLoadViewGridAdapter.this.mContext).load(str2).apply((BaseRequestOptions<?>) new RequestOptions().signature(new ObjectKey(iconUriPath)).override(100, 100)).into((ImageView) new WeakReference(this.imageView_main).get());
            if (i == CSOnlineStoreDownLoadViewGridAdapter.this.selectedPos) {
                this.textView_main.setBackgroundColor(-16719996);
            } else {
                this.textView_main.setBackgroundColor(ViewCompat.MEASURED_SIZE_MASK);
            }
        }
    }

    public void setOnBarViewItemClikListener(onABarViewItemClikListener onabarviewitemcliklistener) {
        this.mListener = onabarviewitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }
}
