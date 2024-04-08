package com.picspool.snappic.adapter;

import android.content.Context;
import android.graphics.ColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSceneRecyclerViewAdapter extends RecyclerView.Adapter<CSSceneRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private List<DMWBRes> mResList;
    private int selectedPos = -1;
    private boolean isShowText = true;

    /* loaded from: classes.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes, boolean z);
    }

    public CSSceneRecyclerViewAdapter(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.mResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_scene_recycler_adapter_item, viewGroup, false));
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
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.snappic.adapter.CSSceneRecyclerViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSSceneRecyclerViewAdapter.this.mResList.size()) {
                        return;
                    }
                    DMWBRes dMWBRes = (DMWBRes) CSSceneRecyclerViewAdapter.this.mResList.get(adapterPosition);
                    if (CSSceneRecyclerViewAdapter.this.mListener != null) {
                        CSSceneRecyclerViewAdapter.this.mListener.onClick(adapterPosition, (DMWBRes) CSSceneRecyclerViewAdapter.this.mResList.get(adapterPosition), false);
                    }
                    CSSceneRecyclerViewAdapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    CSSceneRecyclerViewAdapter.this.notifyDataSetChanged();
                }
            });
            view.getLayoutParams().width = (int) (DMScreenInfoUtil.screenWidth(CSSceneRecyclerViewAdapter.this.mContext) / 5.5f);
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            this.imageView_main.setImageBitmap(DMBitmapUtil.getImageFromAssetsFile(CSSceneRecyclerViewAdapter.this.mContext.getResources(), dMWBRes.getIconFileName()));
            this.textView_main.setText(dMWBRes.getShowText());
            if (i != CSSceneRecyclerViewAdapter.this.selectedPos) {
                this.imageView_main.setColorFilter(CSSceneRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_barview_icon_grey));
                this.textView_main.setTextColor(CSSceneRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_barview_icon_grey));
                return;
            }
            this.imageView_main.setColorFilter((ColorFilter) null);
            this.textView_main.setTextColor(CSSceneRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_main_color_blue));
        }
    }

    public void setOnRecyclerViewItemClikListener(onRecyclerViewItemClikListener onrecyclerviewitemcliklistener) {
        this.mListener = onrecyclerviewitemcliklistener;
    }

    public boolean isShowText() {
        return this.isShowText;
    }

    public void setShowText(boolean z) {
        this.isShowText = z;
    }
}
