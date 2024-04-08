package com.winflag.libcollage.widget.radioview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sky.testproject.R;
import com.winflag.libcollage.resource.RatioRes;
import java.util.List;

/* loaded from: classes.dex */
public class TemplateRatioAdapter extends RecyclerView.Adapter<TemplateRatioAdapter.MyViewHolder> {
    private boolean isVerticalRatio = true;
    private Context mContext;
    private onRadioItemClikListener mListener;
    private List<RatioRes> radioResList;
    private int selectedPos;

    /* loaded from: classes.dex */
    public interface onRadioItemClikListener {
        void onClick(int i, RatioRes ratioRes, boolean z);
    }

    public TemplateRatioAdapter(Context context, List<RatioRes> list, int i) {
        this.selectedPos = 0;
        this.mContext = context;
        this.radioResList = list;
        this.selectedPos = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_radtio_style2_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.radioResList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.radioResList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_main;
        FrameLayout ly_container;
        RatioImageView ratioImageView;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.ratioImageView = (RatioImageView) view.findViewById(R.id.radio_imgview);
            this.ly_container = (FrameLayout) view.findViewById(R.id.ly_container);
        }

        public void setData(final List<RatioRes> list, final int i) {
            if (i == TemplateRatioAdapter.this.selectedPos) {
                this.imageView_main.setSelected(true);
            } else {
                this.imageView_main.setSelected(false);
                this.ly_container.setBackgroundColor(ViewCompat.MEASURED_SIZE_MASK);
            }
            RatioRes ratioRes = list.get(i);
            if (ratioRes != null) {
                this.imageView_main.setBackgroundResource(ratioRes.getInteger().intValue());
            }
            this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libcollage.widget.radioview.TemplateRatioAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    TemplateRatioAdapter.this.selectedPos = i;
                    if (TemplateRatioAdapter.this.mListener != null) {
                        TemplateRatioAdapter.this.mListener.onClick(MyViewHolder.this.getLayoutPosition(), (RatioRes) list.get(TemplateRatioAdapter.this.selectedPos), TemplateRatioAdapter.this.isVerticalRatio);
                    }
                    TemplateRatioAdapter.this.selectedPos = i;
                    TemplateRatioAdapter.this.notifyDataSetChanged();
                }
            });
        }
    }

    public void setOnRatioItemClikListener(onRadioItemClikListener onradioitemcliklistener) {
        this.mListener = onradioitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
    }
}
