package com.picspool.snappic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBColorRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

import io.reactivex.rxjava3.annotations.SchedulerSupport;

/* loaded from: classes.dex */
public class CSBgEffectRecyclerViewAdapter extends RecyclerView.Adapter<CSBgEffectRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private List<DMWBRes> mResList;
    private int selectedPos = 0;
    private boolean isShowText = true;

    /* loaded from: classes.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes, boolean z);
    }

    public CSBgEffectRecyclerViewAdapter(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.mResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_square_bgeffect_recycler_adapter_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.mResList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mResList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_color_none;
        ImageView imageView_main;
        ImageView imageView_select;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.imageView_select = (ImageView) view.findViewById(R.id.img_select);
            this.imageView_color_none = (ImageView) view.findViewById(R.id.img_main_color_none);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.snappic.adapter.CSBgEffectRecyclerViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSBgEffectRecyclerViewAdapter.this.mResList.size()) {
                        return;
                    }
                    CSBgEffectRecyclerViewAdapter.this.selectedPos = adapterPosition;
                    if (CSBgEffectRecyclerViewAdapter.this.mListener != null) {
                        CSBgEffectRecyclerViewAdapter.this.mListener.onClick(adapterPosition, (DMWBRes) CSBgEffectRecyclerViewAdapter.this.mResList.get(adapterPosition), false);
                    }
                    CSBgEffectRecyclerViewAdapter.this.notifyDataSetChanged();
                }
            });
            int screenWidth = (int) (DMScreenInfoUtil.screenWidth(CSBgEffectRecyclerViewAdapter.this.mContext) / 7.5f);
            view.getLayoutParams().width = screenWidth;
            view.getLayoutParams().height = screenWidth;
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBColorRes dMWBColorRes = (DMWBColorRes) list.get(i);
            if (dMWBColorRes.getName().equals(SchedulerSupport.NONE)) {
                String iconFileName = dMWBColorRes.getIconFileName();
                if (iconFileName != null && !iconFileName.equals("")) {
                    Bitmap imageFromAssetsFile = DMBitmapDbUtil.getImageFromAssetsFile(CSBgEffectRecyclerViewAdapter.this.mContext, dMWBColorRes.getIconFileName());
                    this.imageView_color_none.setVisibility(View.VISIBLE);
                    this.imageView_main.setVisibility(View.GONE);
                    this.imageView_color_none.setImageBitmap(imageFromAssetsFile);
                }
            } else {
                this.imageView_color_none.setVisibility(View.GONE);
                this.imageView_main.setVisibility(View.VISIBLE);
                this.imageView_main.setColorFilter(dMWBColorRes.getColorValue());
            }
            if (i == CSBgEffectRecyclerViewAdapter.this.selectedPos) {
                this.imageView_select.setVisibility(View.VISIBLE);
            } else {
                this.imageView_select.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setOnRecyclerViewItemClikListener(onRecyclerViewItemClikListener onrecyclerviewitemcliklistener) {
        this.mListener = onrecyclerviewitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }

    public boolean isShowText() {
        return this.isShowText;
    }

    public void setShowText(boolean z) {
        this.isShowText = z;
    }
}
