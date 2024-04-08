package com.baiwang.libuiinstalens.masicview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;
import java.util.List;

/* loaded from: classes.dex */
public class CSMosaicRecyclerViewAdapter extends RecyclerView.Adapter<CSMosaicRecyclerViewAdapter.MyViewHolder> {
    private static final String UNLOCKRES = "instalens_lock";
    private final Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private final List<DMWBRes> mResList;
    private int selectedPos = 0;
    private boolean isShowText = true;
    private final String[] lock_items = new String[0];

    /* loaded from: classes.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes, boolean z);
    }

    public CSMosaicRecyclerViewAdapter(Context context, List<DMWBRes> list) {
        mContext = context;
        mResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_mosaic_recycler_adapter_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(mResList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return mResList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_lock;
        ImageView imageView_main;
        FrameLayout ly_container;
        FrameLayout ly_imgcontainer;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            imageView_main = (ImageView) view.findViewById(R.id.img_main);
            imageView_lock = (ImageView) view.findViewById(R.id.img_lock);
            textView_main = (TextView) view.findViewById(R.id.text_name);
            ly_imgcontainer = (FrameLayout) view.findViewById(R.id.ly_img_container);
            ly_container = (FrameLayout) view.findViewById(R.id.ly_container);
            view.setOnClickListener(view2 -> {
                DMWBRes dMWBRes = (DMWBRes) mResList.get(getAdapterPosition());
                boolean z = false;
                if (getlockres(dMWBRes.getName())) {
                    if (DMPreferencesUtil.get(mContext, UNLOCKRES, dMWBRes.getName()) == null) {
                        z = true;
                    }
                }
                if (mListener != null) {
                    mListener.onClick(getAdapterPosition(), (DMWBRes) mResList.get(getAdapterPosition()), z);
                }
                selectedPos = getLayoutPosition();
                notifyDataSetChanged();
            });
            if (isShowText) {
                return;
            }
            textView_main.setVisibility(View.INVISIBLE);
            ((FrameLayout.LayoutParams) ly_imgcontainer.getLayoutParams()).gravity = 17;
        }

        public void setData(List<DMWBRes> list, int i) {
            DMWBRes dMWBRes = list.get(i);
            imageView_main.setImageBitmap(DMBitmapDbUtil.getImageFromAssetsFile(mContext, dMWBRes.getIconFileName()));
            textView_main.setText(dMWBRes.getShowText());
            if (getlockres(dMWBRes.getName())) {
                if (DMPreferencesUtil.get(mContext, UNLOCKRES, dMWBRes.getName()) == null) {
                    imageView_lock.setVisibility(View.VISIBLE);
                } else {
                    imageView_lock.setVisibility(View.INVISIBLE);
                }
            } else {
                imageView_lock.setVisibility(View.INVISIBLE);
            }
            if (i == selectedPos) {
                ly_container.setBackgroundColor(-15299076);
            } else {
                ly_container.setBackgroundColor(ViewCompat.MEASURED_SIZE_MASK);
            }
        }
    }

    public void setOnRecyclerViewItemClikListener(onRecyclerViewItemClikListener onrecyclerviewitemcliklistener) {
        mListener = onrecyclerviewitemcliklistener;
    }

    public void setSelectedPos(int i) {
        selectedPos = i;
        notifyDataSetChanged();
    }

    public boolean getlockres(String str) {
        boolean z = false;
        for (String str2 : lock_items) {
            z = z || str2.equals(str);
        }
        return z;
    }

    public boolean isShowText() {
        return isShowText;
    }

    public void setShowText(boolean z) {
        isShowText = z;
    }
}
