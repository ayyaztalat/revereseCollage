package com.picspool.snappic.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.photo.editor.square.splash.view.view.bean.BackgroundGroupsItems;
import com.picspool.snappic.manager.CSBottomBarManager;
import java.util.List;
import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSBottomRecyclerViewAdapter extends RecyclerView.Adapter<CSBottomRecyclerViewAdapter.MyViewHolder> {
    private Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private List<DMWBRes> mResList;
    private int selectedPos = -1;
    private boolean isShowText = true;

    /* loaded from: classes.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes, boolean z, boolean z2);
    }

    public CSBottomRecyclerViewAdapter(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.mResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_bottom_recycler_adapter_item, viewGroup, false));
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
        View adIcon;
        ImageView imageView_main;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.adIcon = view.findViewById(R.id.adIcon);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.snappic.adapter.CSBottomRecyclerViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSBottomRecyclerViewAdapter.this.mResList.size()) {
                        return;
                    }
                    CSBottomRecyclerViewAdapter.this.selectedPos = adapterPosition;
                    CSBottomRecyclerViewAdapter.this.notifyDataSetChanged();
                    if (CSBottomRecyclerViewAdapter.this.mListener != null) {
                        CSBottomRecyclerViewAdapter.this.mListener.onClick(adapterPosition, (DMWBRes) CSBottomRecyclerViewAdapter.this.mResList.get(adapterPosition), false, CSBottomRecyclerViewAdapter.this.selectedPos == adapterPosition);
                    }
                }
            });
            view.getLayoutParams().width = (int) (DMScreenInfoUtil.screenWidth(CSBottomRecyclerViewAdapter.this.mContext) / 5.5f);
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            this.adIcon.setVisibility(View.GONE);
            if (dMWBRes instanceof GPUFilterRes) {
                ((GPUFilterRes) dMWBRes).getAsyncIconBitmap(new DMWBAsyncPostIconListener() { // from class: com.picspool.snappic.adapter.CSBottomRecyclerViewAdapter.MyViewHolder.2
                    @Override // com.picspool.lib.resource.DMWBAsyncPostIconListener
                    public void postIcon(Bitmap bitmap) {
                        MyViewHolder.this.imageView_main.setImageBitmap(bitmap);
                    }
                });
            } else if (!(dMWBRes instanceof CSBottomBarManager.BottomBarEntity) || ((CSBottomBarManager.BottomBarEntity) dMWBRes).getAd() == null) {
                RequestManager with = Glide.with(CSBottomRecyclerViewAdapter.this.mContext);
                with.load(BackgroundGroupsItems.ANDROID_ASSETS_PATH + dMWBRes.getIconFileName()).into(this.imageView_main);
            } else {
                Glide.with(CSBottomRecyclerViewAdapter.this.mContext).load(dMWBRes.getIconFileName()).into(this.imageView_main);
            }
            this.textView_main.setText(dMWBRes.getShowText());
            if (i == CSBottomRecyclerViewAdapter.this.selectedPos) {
                if (!(dMWBRes instanceof CSBottomBarManager.BottomBarEntity) || ((CSBottomBarManager.BottomBarEntity) dMWBRes).getAd() == null) {
                    this.textView_main.setTextColor(CSBottomRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_main_color_blue));
                    this.imageView_main.setColorFilter((ColorFilter) null);
                    return;
                }
                this.imageView_main.setColorFilter((ColorFilter) null);
            } else if (!(dMWBRes instanceof CSBottomBarManager.BottomBarEntity) || ((CSBottomBarManager.BottomBarEntity) dMWBRes).getAd() == null) {
                this.imageView_main.setColorFilter(CSBottomRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_barview_icon_grey));
                this.textView_main.setTextColor(CSBottomRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.libui_barview_icon_grey));
            } else {
                this.imageView_main.setColorFilter((ColorFilter) null);
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
