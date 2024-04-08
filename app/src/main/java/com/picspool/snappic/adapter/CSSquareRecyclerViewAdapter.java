package com.picspool.snappic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.snappic.res.CSSquareBarRes;
import java.util.List;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSquareRecyclerViewAdapter extends RecyclerView.Adapter<CSSquareRecyclerViewAdapter.MyViewHolder> {
    private static String UNLOCKRES = "";
    private Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private List<DMWBRes> mResList;
    private int selectedPos = 0;
    private String[] lock_items = new String[0];

    /* loaded from: classes.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes, boolean z);
    }

    public CSSquareRecyclerViewAdapter(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.mResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_squarebar_recycler_adapter_item, viewGroup, false));
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
            TextView textView = (TextView) view.findViewById(R.id.text_name);
            this.textView_main = textView;
            textView.setTextColor(CSSquareRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_barview_icon_grey));
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.snappic.adapter.CSSquareRecyclerViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSSquareRecyclerViewAdapter.this.mResList.size()) {
                        return;
                    }
                    DMWBRes dMWBRes = (DMWBRes) CSSquareRecyclerViewAdapter.this.mResList.get(adapterPosition);
                    if (dMWBRes instanceof CSSquareBarRes) {
                        ((CSSquareBarRes) dMWBRes).movetonextRes();
                        if (CSSquareRecyclerViewAdapter.this.mListener != null) {
                            CSSquareRecyclerViewAdapter.this.mListener.onClick(adapterPosition, (DMWBRes) CSSquareRecyclerViewAdapter.this.mResList.get(adapterPosition), false);
                        }
                        CSSquareRecyclerViewAdapter.this.notifyDataSetChanged();
                    }
                }
            });
            view.getLayoutParams().width = (int) (DMScreenInfoUtil.screenWidth(CSSquareRecyclerViewAdapter.this.mContext) / 5.5f);
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            if (dMWBRes instanceof CSSquareBarRes) {
                DMWBImageRes currentRes = ((CSSquareBarRes) dMWBRes).getCurrentRes();
                this.imageView_main.setImageBitmap(DMBitmapDbUtil.getImageFromAssetsFile(CSSquareRecyclerViewAdapter.this.mContext, currentRes.getIconFileName()));
                this.textView_main.setText(currentRes.getShowText());
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

    public boolean getlockres(String str) {
        boolean z = false;
        for (String str2 : this.lock_items) {
            z = z || str2.equals(str);
        }
        return z;
    }
}
