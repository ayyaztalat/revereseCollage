package com.picspool.snappic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSCropRatioRecyclerViewAdapter extends RecyclerView.Adapter<CSCropRatioRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private List<DMWBRes> mResList;
    private int selectedPos = 0;

    /* loaded from: classes.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes, boolean z);
    }

    public CSCropRatioRecyclerViewAdapter(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.mResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_crop_ratio_recycler_adapter_item, viewGroup, false));
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
        ImageView imageView_main;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.snappic.adapter.CSCropRatioRecyclerViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSCropRatioRecyclerViewAdapter.this.mResList.size()) {
                        return;
                    }
                    CSCropRatioRecyclerViewAdapter.this.selectedPos = adapterPosition;
                    if (CSCropRatioRecyclerViewAdapter.this.mListener != null) {
                        CSCropRatioRecyclerViewAdapter.this.mListener.onClick(adapterPosition, (DMWBRes) CSCropRatioRecyclerViewAdapter.this.mResList.get(adapterPosition), false);
                    }
                    CSCropRatioRecyclerViewAdapter.this.notifyDataSetChanged();
                }
            });
            view.getLayoutParams().width = (int) (DMScreenInfoUtil.screenWidth(CSCropRatioRecyclerViewAdapter.this.mContext) / 7.0f);
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            this.imageView_main.setImageBitmap(DMBitmapDbUtil.getImageFromAssetsFile(CSCropRatioRecyclerViewAdapter.this.mContext, dMWBRes.getIconFileName()));
            this.textView_main.setText(dMWBRes.getShowText());
            if (i == CSCropRatioRecyclerViewAdapter.this.selectedPos) {
                this.textView_main.setTextColor(CSCropRatioRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_main_color_red));
                this.imageView_main.setColorFilter(CSCropRatioRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_main_color_red));
                return;
            }
            this.imageView_main.setColorFilter(CSCropRatioRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_barview_icon_grey));
            this.textView_main.setTextColor(CSCropRatioRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_barview_icon_grey));
        }
    }

    public void setOnRecyclerViewItemClikListener(onRecyclerViewItemClikListener onrecyclerviewitemcliklistener) {
        this.mListener = onrecyclerviewitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }
}
