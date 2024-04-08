package com.picspool.libfuncview.background.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.res.CSGroupRes;
import java.util.List;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSGroupViewAdapter extends RecyclerView.Adapter<CSGroupViewAdapter.MyViewHolder> {
    private List<CSGroupRes> groupRes;
    private Context mContext;
    private onGroupItemClikListener mListener;
    private int selectedPos = 0;

    /* loaded from: classes.dex */
    public interface onGroupItemClikListener {
        void onClick(int i);
    }

    public CSGroupViewAdapter(Context context, List<CSGroupRes> list) {
        this.mContext = context;
        this.groupRes = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_bg_group_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.groupRes, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.groupRes.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_main;
        FrameLayout ly_root_container;
        int screenwidth;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.ly_root_container = (FrameLayout) view.findViewById(R.id.ly_root_container);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.background.adapter.CSGroupViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (CSGroupViewAdapter.this.mListener != null) {
                        CSGroupViewAdapter.this.mListener.onClick(MyViewHolder.this.getLayoutPosition());
                    }
                    CSGroupViewAdapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    CSGroupViewAdapter.this.notifyDataSetChanged();
                }
            });
            this.screenwidth = DMScreenInfoUtil.screenWidth(CSGroupViewAdapter.this.mContext);
            view.getLayoutParams().width = this.screenwidth / 6;
            view.getLayoutParams().height = this.screenwidth / 9;
            int i = this.screenwidth;
            ((FrameLayout.LayoutParams) this.imageView_main.getLayoutParams()).setMargins(i / 24, i / 72, i / 24, i / 72);
        }

        public void setData(List<CSGroupRes> list, int i) {
            Bitmap decodeFile;
            if (i >= CSGroupViewAdapter.this.groupRes.size()) {
                if (i == CSGroupViewAdapter.this.groupRes.size()) {
                    this.imageView_main.setImageBitmap(DMBitmapDbUtil.getImageFromResourceFile(CSGroupViewAdapter.this.mContext, R.drawable.img_stikcerbar_setting));
                    int i2 = this.screenwidth;
                    ((FrameLayout.LayoutParams) this.imageView_main.getLayoutParams()).setMargins(i2 / 18, i2 / 54, i2 / 18, i2 / 54);
                }
            } else {
                CSGroupRes cSGroupRes = list.get(i);
                int i3 = this.screenwidth;
                ((FrameLayout.LayoutParams) this.imageView_main.getLayoutParams()).setMargins(i3 / 24, i3 / 72, i3 / 24, i3 / 72);
                if (cSGroupRes != null && cSGroupRes.getList_res() != null && cSGroupRes.getList_res().size() > 0) {
                    DMWBRes dMWBRes = cSGroupRes.getList_res().get(0);
                    if (dMWBRes.getIconType() == DMWBRes.LocationType.ASSERT) {
                        this.imageView_main.setImageBitmap(cSGroupRes.getIconFileName() != null ? DMBitmapDbUtil.getImageFromAssetsFile(CSGroupViewAdapter.this.mContext, cSGroupRes.getIconFileName()) : DMBitmapDbUtil.getImageFromAssetsFile(CSGroupViewAdapter.this.mContext, dMWBRes.getIconFileName()));
                    } else if (dMWBRes.getIconType() == DMWBRes.LocationType.ONLINE) {
                        if (cSGroupRes.getIconFileName() != null) {
                            decodeFile = BitmapFactory.decodeFile(cSGroupRes.getIconFileName());
                        } else {
                            decodeFile = BitmapFactory.decodeFile(dMWBRes.getIconFileName());
                        }
                        this.imageView_main.setImageBitmap(decodeFile);
                    }
                }
            }
            if (i == CSGroupViewAdapter.this.selectedPos) {
                this.textView_main.setBackgroundColor(-16719996);
                this.ly_root_container.setBackgroundColor(-15066341);
                return;
            }
            this.textView_main.setBackgroundColor(0);
            this.ly_root_container.setBackgroundColor(0);
        }
    }

    public void setOnGroupItemClikListener(onGroupItemClikListener ongroupitemcliklistener) {
        this.mListener = ongroupitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }
}
