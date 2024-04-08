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
import com.bumptech.glide.Glide;
import com.photo.editor.square.splash.utils.AssBitManage;
import com.sky.testproject.R;

import java.util.List;

/* loaded from: classes.dex */
public class BordRecyclerViewAdapter extends RecyclerView.Adapter<BordRecyclerViewAdapter.MyViewHolder> implements View.OnTouchListener {
    private static String UNLOCKRES = "instalens_lock";
    private Bitmap icon;
    private Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private List<AssBitManage.bitBean> mResList;
    private RecyclerView recyclerView;
    private int selectedPos = 0;
    private boolean isonclick = true;
    private boolean isShowText = true;
    private String[] lock_items = new String[0];

    /* loaded from: classes.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(List<AssBitManage.bitBean> list, int i);
    }

    public BordRecyclerViewAdapter(Context context, List<AssBitManage.bitBean> list, RecyclerView recyclerView, Bitmap bitmap) {
        this.mContext = context;
        this.mResList = list;
        this.icon = bitmap;
        this.recyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_collage_bord_adapter_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.mResList, this.icon, i);
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
        ImageView icon_image;
        ImageView imageView_lock;
        ImageView imageView_main;
        FrameLayout ly_imgcontainer;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.imageView_lock = (ImageView) view.findViewById(R.id.img_lock);
            this.icon_image = (ImageView) view.findViewById(R.id.icon);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.ly_imgcontainer = (FrameLayout) view.findViewById(R.id.ly_img_container);
            if (BordRecyclerViewAdapter.this.isShowText) {
                return;
            }
            this.textView_main.setVisibility(View.INVISIBLE);
            ((FrameLayout.LayoutParams) this.ly_imgcontainer.getLayoutParams()).gravity = 17;
        }

        public void setData(final List<AssBitManage.bitBean> list, Bitmap bitmap, final int i) {
            if (BordRecyclerViewAdapter.this.selectedPos == i) {
                this.icon_image.setBackgroundResource(R.drawable.image_icon_one);
                this.icon_image.setColorFilter(R.color.white);
            } else {
                this.icon_image.setColorFilter(R.color.pop_color);
                this.icon_image.setBackgroundResource(R.drawable.image_icon);
            }
            Glide.with(BordRecyclerViewAdapter.this.mContext).load(list.get(i).getIcon()).into(this.imageView_main);
            this.imageView_main.setOnClickListener(new View.OnClickListener() { // from class: com.winflag.libcollage.adapter.BordRecyclerViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (BordRecyclerViewAdapter.this.mListener != null) {
                        BordRecyclerViewAdapter.this.mListener.onClick(list, i);
                    }
                    BordRecyclerViewAdapter.this.selectedPos = i;
                    BordRecyclerViewAdapter.this.notifyDataSetChanged();
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
