package com.winflag.libsquare.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.instafilter.resource.GPUFilterRes;
import com.picspool.lib.resource.DMWBAsyncPostIconListener;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;
import com.winflag.libsquare.res.CSListNaitveAdRes;
import com.winflag.libsquare.res.CSListNativeAdInterface;

import java.util.List;

/* loaded from: classes3.dex */
public class CSSquareRecyclerViewAdapter extends RecyclerView.Adapter<CSSquareRecyclerViewAdapter.MyViewHolder> {
    private static String UNLOCKRES = "instalens_lock";
    private Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private List<DMWBRes> mResList;
    String TAG = "filterlist";
    private int selectedPos = 0;
    private String[] lock_items = new String[0];

    /* loaded from: classes3.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes, boolean z);
    }

    public CSSquareRecyclerViewAdapter(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.mResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_recycler_adapter_item, viewGroup, false));
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
    /* loaded from: classes3.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout ad_btnbg;
        TextView ad_content;
        ImageView ad_icon;
        FrameLayout adchoicesLayout;
        ImageView imageView_lock;
        ImageView imageView_main;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.imageView_lock = (ImageView) view.findViewById(R.id.img_lock);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.ad_icon = (ImageView) view.findViewById(R.id.img_adicon);
            this.adchoicesLayout = (FrameLayout) view.findViewById(R.id.adchoicesLayout);
            this.ad_content = (TextView) view.findViewById(R.id.btn_view);
            this.ad_btnbg = (FrameLayout) view.findViewById(R.id.ad_btn_bg);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libsquare.adapter.CSSquareRecyclerViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSSquareRecyclerViewAdapter.this.mResList.size()) {
                        return;
                    }
                    DMWBRes dMWBRes = (DMWBRes) CSSquareRecyclerViewAdapter.this.mResList.get(adapterPosition);
                    if (!(dMWBRes instanceof CSListNaitveAdRes)) {
                        boolean z = false;
                        if (CSSquareRecyclerViewAdapter.this.getlockres(dMWBRes.getName())) {
                            Context context = CSSquareRecyclerViewAdapter.this.mContext;
                            String str = CSSquareRecyclerViewAdapter.UNLOCKRES;
                            if (DMPreferencesUtil.get(context, str, dMWBRes.getName() + "") == null) {
                                z = true;
                            }
                        }
                        if (CSSquareRecyclerViewAdapter.this.mListener != null) {
                            CSSquareRecyclerViewAdapter.this.mListener.onClick(adapterPosition, (DMWBRes) CSSquareRecyclerViewAdapter.this.mResList.get(adapterPosition), z);
                        }
                    }
                    CSSquareRecyclerViewAdapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    CSSquareRecyclerViewAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            this.ad_icon.setVisibility(View.GONE);
            this.ad_content.setVisibility(View.GONE);
            this.adchoicesLayout.setVisibility(View.GONE);
            this.ad_btnbg.setVisibility(View.GONE);
            this.imageView_main.setImageBitmap(null);
            if (dMWBRes instanceof GPUFilterRes) {
                ((GPUFilterRes) dMWBRes).getAsyncIconBitmap(new DMWBAsyncPostIconListener() { // from class: com.winflag.libsquare.adapter.CSSquareRecyclerViewAdapter.MyViewHolder.2
                    @Override // org.picspool.lib.resource.DMWBAsyncPostIconListener
                    public void postIcon(Bitmap bitmap) {
                        MyViewHolder.this.imageView_main.setImageBitmap(bitmap);
                    }
                });
                this.textView_main.setText(dMWBRes.getShowText());
            } else if (dMWBRes instanceof CSListNaitveAdRes) {
                this.ad_content.setVisibility(View.VISIBLE);
                this.ad_icon.setVisibility(View.VISIBLE);
                CSListNativeAdInterface listNativeAdInterface = ((CSListNaitveAdRes) dMWBRes).getListNativeAdInterface();
                Context context = CSSquareRecyclerViewAdapter.this.mContext;
                ImageView imageView = this.imageView_main;
                TextView textView = this.ad_content;
                listNativeAdInterface.showAd(context, imageView, textView, this.textView_main, this.ad_icon, textView, this.ad_btnbg, this.adchoicesLayout);
            } else {
                this.imageView_main.setImageBitmap(dMWBRes.getIconBitmap());
                this.textView_main.setText(dMWBRes.getShowText());
            }
            if (CSSquareRecyclerViewAdapter.this.getlockres(dMWBRes.getName())) {
                Context context2 = CSSquareRecyclerViewAdapter.this.mContext;
                String str = CSSquareRecyclerViewAdapter.UNLOCKRES;
                if (DMPreferencesUtil.get(context2, str, dMWBRes.getName() + "") == null) {
                    this.imageView_lock.setVisibility(View.VISIBLE);
                } else {
                    this.imageView_lock.setVisibility(View.INVISIBLE);
                }
            } else {
                this.imageView_lock.setVisibility(View.INVISIBLE);
            }
            if (i == CSSquareRecyclerViewAdapter.this.selectedPos) {
                this.textView_main.setBackgroundColor(-16717173);
            } else {
                this.textView_main.setBackgroundColor(-7829368);
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
