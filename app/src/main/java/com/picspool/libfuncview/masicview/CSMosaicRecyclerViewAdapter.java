package com.picspool.libfuncview.masicview;

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
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSMosaicRecyclerViewAdapter extends RecyclerView.Adapter<CSMosaicRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private List<DMWBRes> mResList;
    private int selectedPos = 0;
    private boolean isShowText = true;

    /* loaded from: classes.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes, boolean z);
    }

    public CSMosaicRecyclerViewAdapter(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.mResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_adapter_item_mosaicbar, viewGroup, false));
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
        View view_select;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.view_select = view.findViewById(R.id.ly_select);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.masicview.CSMosaicRecyclerViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSMosaicRecyclerViewAdapter.this.mResList.size()) {
                        return;
                    }
                    if (CSMosaicRecyclerViewAdapter.this.mListener != null) {
                        CSMosaicRecyclerViewAdapter.this.mListener.onClick(adapterPosition, (DMWBRes) CSMosaicRecyclerViewAdapter.this.mResList.get(adapterPosition), false);
                    }
                    CSMosaicRecyclerViewAdapter.this.selectedPos = adapterPosition;
                    CSMosaicRecyclerViewAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            this.imageView_main.setImageBitmap(DMBitmapDbUtil.getImageFromAssetsFile(CSMosaicRecyclerViewAdapter.this.mContext, dMWBRes.getIconFileName()));
            this.textView_main.setText(dMWBRes.getShowText());
            if (i == CSMosaicRecyclerViewAdapter.this.selectedPos) {
                this.view_select.setVisibility(View.VISIBLE);
            } else {
                this.view_select.setVisibility(View.GONE);
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
