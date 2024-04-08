package com.picspool.libfuncview.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBRes;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSFilterBarViewGroupAdapter extends RecyclerView.Adapter<CSFilterBarViewGroupAdapter.MyViewHolder> {
    private Context mContext;
    private onGroupBarViewItemClikListener mListener;
    private List<DMWBRes> resList;
    private int selectedPos = 0;

    /* loaded from: classes.dex */
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

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_main;
        View img_select;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.img_select = view.findViewById(R.id.img_select);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.filter.CSFilterBarViewGroupAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (CSFilterBarViewGroupAdapter.this.mListener != null) {
                        CSFilterBarViewGroupAdapter.this.mListener.onClick(MyViewHolder.this.getLayoutPosition());
                    }
                    CSFilterBarViewGroupAdapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    CSFilterBarViewGroupAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            this.imageView_main.setImageBitmap(DMBitmapUtil.createCircleImage(DMBitmapUtil.getImageFromAssetsFile(CSFilterBarViewGroupAdapter.this.mContext.getResources(), dMWBRes.getIconFileName()), 80));
            this.textView_main.setText(dMWBRes.getShowText());
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
