package com.picspool.libfuncview.effect.adjustview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSPorterDuffModeBarAdapter extends RecyclerView.Adapter<CSPorterDuffModeBarAdapter.MyViewHolder> {
    private Context mContext;
    private onABarViewItemClikListener mListener;
    private List<DMWBRes> resList;
    private int selectedPos = -1;

    /* loaded from: classes.dex */
    public interface onABarViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes);
    }

    public CSPorterDuffModeBarAdapter(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.resList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_adapter_item_effectadjust, viewGroup, false));
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
        FrameLayout lyBottomLine;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.lyBottomLine = (FrameLayout) view.findViewById(R.id.ly_selectline);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.effect.adjustview.CSPorterDuffModeBarAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    CSPorterDuffModeBarAdapter.this.selectedPos = MyViewHolder.this.getAdapterPosition();
                    if (CSPorterDuffModeBarAdapter.this.mListener != null) {
                        CSPorterDuffModeBarAdapter.this.mListener.onClick(MyViewHolder.this.getAdapterPosition(), (DMWBRes) CSPorterDuffModeBarAdapter.this.resList.get(MyViewHolder.this.getLayoutPosition()));
                    }
                    CSPorterDuffModeBarAdapter.this.notifyDataSetChanged();
                }
            });
            view.getLayoutParams().width = (int) (DMScreenInfoUtil.screenWidth(CSPorterDuffModeBarAdapter.this.mContext) / 5.5f);
            view.getLayoutParams().height = DMScreenInfoUtil.dip2px(CSPorterDuffModeBarAdapter.this.mContext, 40.0f);
        }

        public void setData(List<DMWBRes> list, int i) {
            this.textView_main.setText(list.get(i).getShowText());
            if (i == CSPorterDuffModeBarAdapter.this.selectedPos) {
                this.textView_main.setTextColor(CSPorterDuffModeBarAdapter.this.mContext.getResources().getColor(R.color.libui_main_color_text));
                this.lyBottomLine.setLayoutParams(new LinearLayout.LayoutParams(-1, DMScreenInfoUtil.dip2px(CSPorterDuffModeBarAdapter.this.mContext, 12.0f)));
                this.lyBottomLine.setBackgroundColor(CSPorterDuffModeBarAdapter.this.mContext.getResources().getColor(R.color.libui_main_color_blue));
                return;
            }
            this.textView_main.setTextColor(CSPorterDuffModeBarAdapter.this.mContext.getResources().getColor(R.color.libui_main_color_texthint));
            this.lyBottomLine.setLayoutParams(new LinearLayout.LayoutParams(-1, DMScreenInfoUtil.dip2px(CSPorterDuffModeBarAdapter.this.mContext, 6.0f)));
            this.lyBottomLine.setBackgroundColor(CSPorterDuffModeBarAdapter.this.mContext.getResources().getColor(R.color.libui_main_color_grey));
        }
    }

    public void setOnBarViewItemClikListener(onABarViewItemClikListener onabarviewitemcliklistener) {
        this.mListener = onabarviewitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }
}
