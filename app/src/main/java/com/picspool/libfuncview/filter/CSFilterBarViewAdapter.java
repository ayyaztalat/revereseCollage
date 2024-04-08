package com.picspool.libfuncview.filter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
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
        this.srcbmp = DMBitmapUtil.sampeZoomFromBitmap(bitmap, 200);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_adapter_item_filterbarstyle2, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.resList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.resList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_main;
        View ly_select;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.ly_select = view.findViewById(R.id.ly_select);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.filter.CSFilterBarViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (CSFilterBarViewAdapter.this.mListener != null) {
                        CSFilterBarViewAdapter.this.mListener.onClick(adapterPosition, (DMWBRes) CSFilterBarViewAdapter.this.resList.get(adapterPosition));
                    }
                    CSFilterBarViewAdapter.this.selectedPos = adapterPosition;
                    CSFilterBarViewAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            this.imageView_main.setVisibility(View.INVISIBLE);
            dMWBRes.getAsyncIconBitmap(new DMWBAsyncPostIconListener() { // from class: com.picspool.libfuncview.filter.CSFilterBarViewAdapter.MyViewHolder.2
                @Override // com.picspool.lib.resource.DMWBAsyncPostIconListener
                public void postIcon(Bitmap bitmap) {
                    if (bitmap != null) {
                        MyViewHolder.this.imageView_main.setImageBitmap(bitmap);
                        MyViewHolder.this.imageView_main.setVisibility(View.VISIBLE);
                    }
                }
            });
            this.textView_main.setText(dMWBRes.getName());
            if (i == CSFilterBarViewAdapter.this.selectedPos) {
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
