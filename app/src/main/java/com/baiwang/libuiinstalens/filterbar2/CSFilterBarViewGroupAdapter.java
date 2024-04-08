package com.baiwang.libuiinstalens.filterbar2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;
import java.util.List;

public class CSFilterBarViewGroupAdapter extends RecyclerView.Adapter<CSFilterBarViewGroupAdapter.MyViewHolder> {
    private Context mContext;
    private onGroupBarViewItemClikListener mListener;
    private List<DMWBRes> resList;
    private int selectedPos = 0;

    public interface onGroupBarViewItemClikListener {
        void onClick(int i);
    }

    public CSFilterBarViewGroupAdapter(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.resList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_adapter_item_filterbarstyle2_group, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.resList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.resList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_main;
        View img_select;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            imageView_main = (ImageView) view.findViewById(R.id.img_main);
            textView_main = (TextView) view.findViewById(R.id.text_name);
            img_select = view.findViewById(R.id.img_select);
            view.setOnClickListener(view2 -> {
                if (mListener != null) {
                    mListener.onClick(getLayoutPosition());
                }
                selectedPos = getLayoutPosition();
                notifyDataSetChanged();
            });
        }

        public void setData(List<DMWBRes> list, int i) {
            this.imageView_main.setImageBitmap(DMBitmapUtil.createCircleImage(DMBitmapUtil.getImageFromAssetsFile(CSFilterBarViewGroupAdapter.this.mContext.getResources(), list.get(i).getIconFileName()), 80));
            if (i == CSFilterBarViewGroupAdapter.this.selectedPos) {
                this.img_select.setVisibility(View.VISIBLE);
            } else {
                this.img_select.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setOnBarViewItemClikListener(onGroupBarViewItemClikListener ongroupbarviewitemcliklistener) {
        this.mListener = ongroupbarviewitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }

    public int getSelectedPos() {
        return this.selectedPos;
    }
}
