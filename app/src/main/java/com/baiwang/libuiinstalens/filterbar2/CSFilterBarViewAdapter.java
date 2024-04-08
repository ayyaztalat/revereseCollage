package com.baiwang.libuiinstalens.filterbar2;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;
import java.util.List;

public class CSFilterBarViewAdapter extends RecyclerView.Adapter<CSFilterBarViewAdapter.MyViewHolder> {
    private Context mContext;
    private onFilterBarViewItemClikListener mListener;
    private List<DMWBRes> resList;
    private int selectedPos = -1;
    private Bitmap srcbmp;

    /* loaded from: classes.dex */
    public interface onFilterBarViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes);
    }

    public CSFilterBarViewAdapter(Context context, List<DMWBRes> list, Bitmap bitmap) {
        this.mContext = context;
        this.resList = list;
        this.srcbmp = DMBitmapUtil.sampeZoomFromBitmap(bitmap, 120);
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_adapter_item_filterbarstyle2, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.resList, i);
    }

    @Override
    public int getItemCount() {
        return this.resList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_main;
        View ly_select;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            imageView_main = (ImageView) view.findViewById(R.id.img_main);
            textView_main = (TextView) view.findViewById(R.id.text_name);
            ly_select = view.findViewById(R.id.ly_select);
            view.setOnClickListener(view2 -> {
                int adapterPosition = MyViewHolder.this.getAdapterPosition();
                if (mListener != null) {
                    mListener.onClick(adapterPosition, (DMWBRes) resList.get(adapterPosition));
                }
                selectedPos = adapterPosition;
                notifyDataSetChanged();
            });
        }

        public void setData(List<DMWBRes> list, int i) {
            this.imageView_main.setVisibility(View.INVISIBLE);
            DMWBRes dMWBRes = list.get(i);
            this.textView_main.setText(dMWBRes.getShowText());
            dMWBRes.getAsyncIconBitmap(bitmap -> {
                if (bitmap != null) {
                    MyViewHolder.this.imageView_main.setImageBitmap(bitmap);
                    MyViewHolder.this.imageView_main.setVisibility(View.VISIBLE);
                }
            });
            if (i == selectedPos) {
                this.ly_select.setVisibility(View.VISIBLE);
            } else {
                this.ly_select.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setOnBarViewItemClikListener(onFilterBarViewItemClikListener onfilterbarviewitemcliklistener) {
        this.mListener = onfilterbarviewitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }
}
