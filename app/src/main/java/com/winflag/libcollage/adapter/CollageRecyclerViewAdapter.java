package com.winflag.libcollage.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import java.util.List;

/* loaded from: classes.dex */
public class CollageRecyclerViewAdapter extends RecyclerView.Adapter<CollageRecyclerViewAdapter.MyViewHolder> implements View.OnTouchListener {
    private static String UNLOCKRES = "instalens_lock";
    private Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private List<DMWBRes> mResList;
    private RecyclerView recyclerView;
    private int selectedPos = 0;
    private boolean isonclick = true;
    private boolean isShowText = true;
    private String[] lock_items = new String[0];

    /* loaded from: classes.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(int i, DMWBRes dMWBRes, boolean z);
    }

    public CollageRecyclerViewAdapter(Context context, List<DMWBRes> list, RecyclerView recyclerView) {
        this.mContext = context;
        this.mResList = list;
        this.recyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_collage_recycler_adapter_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.mResList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mResList.size();
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        motionEvent.getX();
        motionEvent.getY();
        int action = motionEvent.getAction();
        if (action == 0) {
            this.isonclick = true;
        } else if (action == 1) {
            this.isonclick = false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_lock;
        ImageView imageView_main;
        FrameLayout ly_imgcontainer;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.imageView_lock = (ImageView) view.findViewById(R.id.img_lock);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.ly_imgcontainer = (FrameLayout) view.findViewById(R.id.ly_img_container);
            if (CollageRecyclerViewAdapter.this.isShowText) {
                return;
            }
            this.textView_main.setVisibility(View.INVISIBLE);
            ((FrameLayout.LayoutParams) this.ly_imgcontainer.getLayoutParams()).gravity = 17;
        }

        public void setData(List<DMWBRes> list, final int i) {
            DMWBRes dMWBRes = list.get(i);
            if (dMWBRes instanceof GPUFilterRes) {
                ((GPUFilterRes) dMWBRes).getAsyncIconBitmap(new DMWBAsyncPostIconListener() { // from class: com.winflag.libcollage.adapter.CollageRecyclerViewAdapter.MyViewHolder.1
                    @Override // org.picspool.lib.resource.DMWBAsyncPostIconListener
                    public void postIcon(Bitmap bitmap) {
                        MyViewHolder.this.imageView_main.setImageBitmap(bitmap);
                    }
                });
            } else {
                this.imageView_main.setImageBitmap(dMWBRes.getIconBitmap());
            }
            this.textView_main.setText(dMWBRes.getShowText());
            if (CollageRecyclerViewAdapter.this.getlockres(dMWBRes.getName())) {
                Context context = CollageRecyclerViewAdapter.this.mContext;
                String str = CollageRecyclerViewAdapter.UNLOCKRES;
                if (DMPreferencesUtil.get(context, str, dMWBRes.getName() + "") == null) {
                    this.imageView_lock.setVisibility(View.VISIBLE);
                } else {
                    this.imageView_lock.setVisibility(View.INVISIBLE);
                }
            } else {
                this.imageView_lock.setVisibility(View.INVISIBLE);
            }
            if (i == CollageRecyclerViewAdapter.this.selectedPos) {
                this.ly_imgcontainer.setBackgroundResource(R.drawable.image_biankuang);
            } else {
                this.ly_imgcontainer.setBackgroundResource(R.drawable.image_biankuang_bai);
            }
            this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libcollage.adapter.CollageRecyclerViewAdapter.MyViewHolder.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    int i2 = i;
                    if (i2 < 0 || i2 >= CollageRecyclerViewAdapter.this.mResList.size()) {
                        return;
                    }
                    DMWBRes dMWBRes2 = (DMWBRes) CollageRecyclerViewAdapter.this.mResList.get(i);
                    boolean z = false;
                    if (CollageRecyclerViewAdapter.this.getlockres(dMWBRes2.getName())) {
                        Context context2 = CollageRecyclerViewAdapter.this.mContext;
                        String str2 = CollageRecyclerViewAdapter.UNLOCKRES;
                        if (DMPreferencesUtil.get(context2, str2, dMWBRes2.getName() + "") == null) {
                            z = true;
                        }
                    }
                    if (CollageRecyclerViewAdapter.this.mListener != null) {
                        CollageRecyclerViewAdapter.this.mListener.onClick(i, (DMWBRes) CollageRecyclerViewAdapter.this.mResList.get(i), z);
                    }
                    CollageRecyclerViewAdapter.this.selectedPos = i;
                    CollageRecyclerViewAdapter.this.notifyDataSetChanged();
                }
            });
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

    public boolean isShowText() {
        return this.isShowText;
    }

    public void setShowText(boolean z) {
        this.isShowText = z;
    }
}
