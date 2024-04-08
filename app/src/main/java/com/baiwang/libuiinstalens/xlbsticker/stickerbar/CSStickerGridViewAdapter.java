package com.baiwang.libuiinstalens.xlbsticker.stickerbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysphotoselector.DMPagerSlidingTabStrip;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

import java.util.List;

/* loaded from: classes.dex */
public class CSStickerGridViewAdapter extends RecyclerView.Adapter<CSStickerGridViewAdapter.MyViewHolder> {
    private Context mContext;
    private onStickerGridViewItemClikListener mListener;
    private int mpagerorder;
    private CSStickerGroup stickerGroup;

    /* loaded from: classes.dex */
    public interface onStickerGridViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes, boolean z);
    }

    public CSStickerGridViewAdapter(Context context, CSStickerGroup cSStickerGroup, int i) {
        this.mpagerorder = 0;
        this.mContext = context;
        this.stickerGroup = cSStickerGroup;
        this.mpagerorder = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_sticker_grid_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.stickerGroup, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return CSXlbStickerBarView.grid_item_count;
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
            int screenWidth = DMScreenInfoUtil.screenWidth(CSStickerGridViewAdapter.this.mContext);
            view.getLayoutParams().height = (screenWidth * 2) / 9;
            view.getLayoutParams().width = screenWidth / 4;
            int i = screenWidth / 16;
            ((FrameLayout.LayoutParams) this.imageView_main.getLayoutParams()).setMargins(i, i, i, i);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerGridViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (CSStickerGridViewAdapter.this.mListener == null || MyViewHolder.this.getAdapterPosition() + (CSStickerGridViewAdapter.this.mpagerorder * CSXlbStickerBarView.grid_item_count) >= CSStickerGridViewAdapter.this.stickerGroup.getList_sticker().size()) {
                        return;
                    }
                    CSStickerGridViewAdapter.this.mListener.onClick(MyViewHolder.this.getAdapterPosition(), CSStickerGridViewAdapter.this.stickerGroup.getList_sticker().get(MyViewHolder.this.getAdapterPosition() + (CSStickerGridViewAdapter.this.mpagerorder * CSXlbStickerBarView.grid_item_count)), false);
                }
            });
            view.setBackgroundColor(CSStickerGridViewAdapter.this.stickerGroup.getStylecolor1());
        }

        public void setData(CSStickerGroup cSStickerGroup, int i) {
            List<CSStickerRes> list_sticker = cSStickerGroup.getList_sticker();
            if ((CSStickerGridViewAdapter.this.mpagerorder * CSXlbStickerBarView.grid_item_count) + i < list_sticker.size()) {
                CSStickerRes cSStickerRes = list_sticker.get(i + (CSStickerGridViewAdapter.this.mpagerorder * CSXlbStickerBarView.grid_item_count));
                if (cSStickerRes.getImageType() == DMWBRes.LocationType.ASSERT) {
                    this.imageView_main.setImageBitmap(DMBitmapCrop.cropCenterScaleBitmap(DMBitmapDbUtil.getImageFromAssetsFile(CSStickerGridViewAdapter.this.mContext, cSStickerRes.getImageFileName()), DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA, DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA));
                } else if (cSStickerRes.getImageType() == DMWBRes.LocationType.ONLINE) {
                    this.imageView_main.setImageBitmap(DMBitmapCrop.cropCenterScaleBitmap(DMBitmapUtil.getImageFromSDFile(CSStickerGridViewAdapter.this.mContext, cSStickerRes.getImageFileName()), DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA, DMPagerSlidingTabStrip.DEF_VALUE_TAB_TEXT_ALPHA));
                }
            }
        }
    }

    public void setOnStickerGridViewItemClikListener(onStickerGridViewItemClikListener onstickergridviewitemcliklistener) {
        this.mListener = onstickergridviewitemcliklistener;
    }
}
