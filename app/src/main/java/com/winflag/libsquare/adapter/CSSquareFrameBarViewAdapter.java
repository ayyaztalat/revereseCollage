package com.winflag.libsquare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.lib.border.CSTBorderRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;

import java.util.List;


/* loaded from: classes3.dex */
public class CSSquareFrameBarViewAdapter extends RecyclerView.Adapter<CSSquareFrameBarViewAdapter.MyViewHolder> {
    private static String UNLOCKRES = "instalens_lock";
    private Context mContext;
    private onFrameBarViewItemClikListener mListener;
    private List<CSTBorderRes> tBorderResList;
    private int selectedPos = 0;
    private String[] lock_items = new String[0];

    /* loaded from: classes3.dex */
    public interface onFrameBarViewItemClikListener {
        void onClick(int i, CSTBorderRes cSTBorderRes, boolean z);
    }

    public CSSquareFrameBarViewAdapter(Context context, List<CSTBorderRes> list) {
        this.mContext = context;
        this.tBorderResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_frame_adapter_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.tBorderResList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.tBorderResList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_lock;
        ImageView imageView_main;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.imageView_lock = (ImageView) view.findViewById(R.id.img_lock);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libsquare.adapter.CSSquareFrameBarViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSSquareFrameBarViewAdapter.this.tBorderResList.size()) {
                        return;
                    }
                    CSTBorderRes cSTBorderRes = (CSTBorderRes) CSSquareFrameBarViewAdapter.this.tBorderResList.get(adapterPosition);
                    boolean z = false;
                    if (CSSquareFrameBarViewAdapter.this.getlockres(cSTBorderRes.getName())) {
                        Context context = CSSquareFrameBarViewAdapter.this.mContext;
                        String str = CSSquareFrameBarViewAdapter.UNLOCKRES;
                        if (DMPreferencesUtil.get(context, str, cSTBorderRes.getName() + "") == null) {
                            z = true;
                        }
                    }
                    if (CSSquareFrameBarViewAdapter.this.mListener != null) {
                        CSSquareFrameBarViewAdapter.this.mListener.onClick(adapterPosition, (CSTBorderRes) CSSquareFrameBarViewAdapter.this.tBorderResList.get(adapterPosition), z);
                    }
                    CSSquareFrameBarViewAdapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    CSSquareFrameBarViewAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void setData(List<CSTBorderRes> list, int i) {
            CSTBorderRes cSTBorderRes = list.get(i);
            this.imageView_main.setImageBitmap(cSTBorderRes.getIconBitmap());
            this.textView_main.setText(cSTBorderRes.getShowText());
            if (CSSquareFrameBarViewAdapter.this.getlockres(cSTBorderRes.getName())) {
                Context context = CSSquareFrameBarViewAdapter.this.mContext;
                String str = CSSquareFrameBarViewAdapter.UNLOCKRES;
                if (DMPreferencesUtil.get(context, str, cSTBorderRes.getName() + "") == null) {
                    this.imageView_lock.setVisibility(View.VISIBLE);
                } else {
                    this.imageView_lock.setVisibility(View.INVISIBLE);
                }
            } else {
                this.imageView_lock.setVisibility(View.INVISIBLE);
            }
            if (i == CSSquareFrameBarViewAdapter.this.selectedPos) {
                this.textView_main.setBackgroundColor(-15299076);
            } else {
                this.textView_main.setBackgroundColor(-7829368);
            }
        }
    }

    public void setOnFrameBarViewItemClikListener(onFrameBarViewItemClikListener onframebarviewitemcliklistener) {
        this.mListener = onframebarviewitemcliklistener;
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
