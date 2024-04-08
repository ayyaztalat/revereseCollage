package com.picspool.libfuncview.xlbsticker.stickerbar;

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

import java.util.List;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSStickerGroupViewAdapter extends RecyclerView.Adapter<CSStickerGroupViewAdapter.MyViewHolder> {
    private Context mContext;
    private onStickerGroupItemClikListener mListener;
    private int selectedPos = 0;
    private List<CSStickerGroup> stickerGroups;

    /* loaded from: classes.dex */
    public interface onStickerGroupItemClikListener {
        void onClick(int i);
    }

    public CSStickerGroupViewAdapter(Context context, List<CSStickerGroup> list) {
        this.mContext = context;
        this.stickerGroups = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_sticker_groupview_item, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(this.stickerGroups, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.stickerGroups.size();
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
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main_center);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.ly_root_container = (FrameLayout) view.findViewById(R.id.ly_root_container);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.xlbsticker.stickerbar.CSStickerGroupViewAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (CSStickerGroupViewAdapter.this.mListener != null) {
                        CSStickerGroupViewAdapter.this.mListener.onClick(MyViewHolder.this.getLayoutPosition());
                    }
                    CSStickerGroupViewAdapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    CSStickerGroupViewAdapter.this.notifyDataSetChanged();
                }
            });
            this.screenwidth = DMScreenInfoUtil.screenWidth(CSStickerGroupViewAdapter.this.mContext);
            view.getLayoutParams().width = this.screenwidth / 6;
            view.getLayoutParams().height = this.screenwidth / 9;
            int i = this.screenwidth;
            ((FrameLayout.LayoutParams) this.imageView_main.getLayoutParams()).setMargins(i / 24, i / 72, i / 24, i / 72);
        }

        public void setData(List<CSStickerGroup> list, int i) {
            Bitmap decodeFile;
            if (i < list.size()) {
                CSStickerGroup cSStickerGroup = list.get(i);
                int i2 = this.screenwidth;
                ((FrameLayout.LayoutParams) this.imageView_main.getLayoutParams()).setMargins(i2 / 24, i2 / 72, i2 / 24, i2 / 72);
                if (cSStickerGroup != null && cSStickerGroup.getList_res() != null && cSStickerGroup.getList_res().size() > 0) {
                    CSStickerRes cSStickerRes = (CSStickerRes) cSStickerGroup.getList_res().get(0);
                    if (cSStickerRes.getImageType() == DMWBRes.LocationType.ASSERT) {
                        this.imageView_main.setImageBitmap(cSStickerGroup.getIconFileName() != null ? DMBitmapDbUtil.getImageFromAssetsFile(CSStickerGroupViewAdapter.this.mContext, cSStickerGroup.getIconFileName()) : DMBitmapDbUtil.getImageFromAssetsFile(CSStickerGroupViewAdapter.this.mContext, cSStickerRes.getImageFileName()));
                    } else if (cSStickerRes.getImageType() == DMWBRes.LocationType.ONLINE) {
                        if (cSStickerGroup.getIconFileName() != null) {
                            decodeFile = BitmapFactory.decodeFile(cSStickerGroup.getIconFileName());
                        } else {
                            decodeFile = BitmapFactory.decodeFile(cSStickerRes.getImageFileName());
                        }
                        this.imageView_main.setImageBitmap(decodeFile);
                    }
                }
            } else if (i == list.size()) {
                this.imageView_main.setImageBitmap(DMBitmapDbUtil.getImageFromResourceFile(CSStickerGroupViewAdapter.this.mContext, R.drawable.img_stikcerbar_setting));
                int i3 = this.screenwidth;
                ((FrameLayout.LayoutParams) this.imageView_main.getLayoutParams()).setMargins(i3 / 18, i3 / 54, i3 / 18, i3 / 54);
            }
            if (i == CSStickerGroupViewAdapter.this.selectedPos) {
                this.textView_main.setBackgroundColor(-16719996);
                this.ly_root_container.setBackgroundColor(-15066341);
                return;
            }
            this.textView_main.setBackgroundColor(0);
            this.ly_root_container.setBackgroundColor(0);
        }
    }

    public void setOnStickerGroupItemClikListener(onStickerGroupItemClikListener onstickergroupitemcliklistener) {
        this.mListener = onstickergroupitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }
}
