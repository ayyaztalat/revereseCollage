package com.picspool.snappic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libsquare.res.CSShapeRes;
import java.util.List;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSShapeRecyclerViewAdapter extends RecyclerView.Adapter<CSShapeRecyclerViewAdapter.MyViewHolder> {
    private static String UNLOCKRES = "";
    private Context mContext;
    private onShapeBarViewItemClikListener mListener;
    private List<CSShapeRes> shapeResList;
    private int selectedPos = 0;
    private String[] lock_items = {""};

    /* loaded from: classes.dex */
    public interface onShapeBarViewItemClikListener {
        void onClick(int i, CSShapeRes cSShapeRes, boolean z);
    }

    public CSShapeRecyclerViewAdapter(Context context, List<CSShapeRes> list) {
        this.mContext = context;
        this.shapeResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_shape_adapter_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.shapeResList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.shapeResList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_lock;
        ImageView imageView_main;
        FrameLayout ly_container;
        FrameLayout ly_select;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.imageView_lock = (ImageView) view.findViewById(R.id.img_lock);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.ly_container = (FrameLayout) view.findViewById(R.id.ly_container);
            this.ly_select = (FrameLayout) view.findViewById(R.id.ly_select);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.snappic.adapter.CSShapeRecyclerViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSShapeRecyclerViewAdapter.this.shapeResList.size()) {
                        return;
                    }
                    CSShapeRes cSShapeRes = (CSShapeRes) CSShapeRecyclerViewAdapter.this.shapeResList.get(adapterPosition);
                    if (CSShapeRecyclerViewAdapter.this.mListener != null) {
                        CSShapeRecyclerViewAdapter.this.mListener.onClick(adapterPosition, cSShapeRes, false);
                    }
                    CSShapeRecyclerViewAdapter.this.selectedPos = adapterPosition;
                    CSShapeRecyclerViewAdapter.this.notifyDataSetChanged();
                }
            });
            view.getLayoutParams().width = (int) (DMScreenInfoUtil.screenWidth(CSShapeRecyclerViewAdapter.this.mContext) / 6.5f);
            view.getLayoutParams().height = DMScreenInfoUtil.dip2px(CSShapeRecyclerViewAdapter.this.mContext, 60.0f);
        }

        public void setData(List<CSShapeRes> list, int i) {
            CSShapeRes cSShapeRes = list.get(i);
            this.imageView_main.setImageBitmap(DMBitmapDbUtil.getImageFromAssetsFile(CSShapeRecyclerViewAdapter.this.mContext, cSShapeRes.getIconFileName()));
            this.imageView_main.setColorFilter(-1);
            if (CSShapeRecyclerViewAdapter.this.getlockres(cSShapeRes.getName())) {
                Context context = CSShapeRecyclerViewAdapter.this.mContext;
                String str = CSShapeRecyclerViewAdapter.UNLOCKRES;
                if (DMPreferencesUtil.get(context, str, cSShapeRes.getName() + "") == null) {
                    this.imageView_lock.setVisibility(View.VISIBLE);
                } else {
                    this.imageView_lock.setVisibility(View.INVISIBLE);
                }
            } else {
                this.imageView_lock.setVisibility(View.INVISIBLE);
            }
            if (i == CSShapeRecyclerViewAdapter.this.selectedPos) {
                this.ly_select.setBackgroundColor(CSShapeRecyclerViewAdapter.this.mContext.getResources().getColor(R.color.snap_main_color_blue_1));
            } else {
                this.ly_select.setBackgroundColor(ViewCompat.MEASURED_SIZE_MASK);
            }
        }
    }

    public void setShapeBarItemClikListener(onShapeBarViewItemClikListener onshapebarviewitemcliklistener) {
        this.mListener = onshapebarviewitemcliklistener;
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
