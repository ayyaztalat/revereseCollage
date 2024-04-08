package com.baiwang.libuiinstalens.xlbsticker.stickersetting;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSStickerBarConfig;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerGroup;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerRes;
import java.util.List;

/* loaded from: classes.dex */
public class CSStickerSettingListAdapter extends RecyclerView.Adapter<CSStickerSettingListAdapter.MyViewHolder> {
    private Context mContext;
    private onStickerSettingItemClikListener mListener;
    private int selectedPos = -1;
    private List<CSStickerGroup> stickerGroups;

    /* loaded from: classes.dex */
    public interface onStickerSettingItemClikListener {
        void onClick(int i);

        void onDragTouched(boolean z);

        void onDragTouched(boolean z, MyViewHolder myViewHolder);
    }

    public CSStickerSettingListAdapter(Context context, List<CSStickerGroup> list) {
        this.mContext = context;
        this.stickerGroups = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_sticker_setting_item, viewGroup, false));
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
        View btn_delete;
        ImageView imageView_main;
        View ly_drag;
        View ly_line;
        TextView textView_main;

        public MyViewHolder(View view) {
            super(view);
            this.imageView_main = (ImageView) view.findViewById(R.id.img_main);
            this.textView_main = (TextView) view.findViewById(R.id.text_name);
            this.ly_line = view.findViewById(R.id.bottom_line);
            this.ly_drag = view.findViewById(R.id.btn_drag);
            this.btn_delete = view.findViewById(R.id.btn_delete);
            this.ly_drag.setOnTouchListener(new View.OnTouchListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingListAdapter.MyViewHolder.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view2, MotionEvent motionEvent) {
                    CSStickerSettingListAdapter.this.mListener.onDragTouched(true, MyViewHolder.this);
                    return false;
                }
            });
            this.btn_delete.setOnClickListener(new View.OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingListAdapter.MyViewHolder.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSStickerSettingListAdapter.this.stickerGroups.size()) {
                        return;
                    }
                    String group_name = ((CSStickerGroup) CSStickerSettingListAdapter.this.stickerGroups.get(adapterPosition)).getGroup_name();
                    CSStickerBarConfig.deleteOnlineStickerRes(group_name, CSStickerSettingListAdapter.this.mContext);
                    Context context = CSStickerSettingListAdapter.this.mContext;
                    if (DMPreferencesUtil.get(context, "xlbsticker", "sticker_group" + group_name) != null) {
                        Context context2 = CSStickerSettingListAdapter.this.mContext;
                        DMPreferencesUtil.save(context2, "xlbsticker", "sticker_group" + group_name, "0");
                    }
                    CSStickerSettingListAdapter.this.stickerGroups.remove(adapterPosition);
                    CSStickerSettingListAdapter.this.notifyItemRemoved(adapterPosition);
                }
            });
            view.setOnClickListener(new View.OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingListAdapter.MyViewHolder.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (CSStickerSettingListAdapter.this.mListener != null) {
                        CSStickerSettingListAdapter.this.mListener.onClick(MyViewHolder.this.getAdapterPosition());
                    }
                    CSStickerSettingListAdapter.this.selectedPos = MyViewHolder.this.getLayoutPosition();
                    CSStickerSettingListAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public void setData(List<CSStickerGroup> list, int i) {
            CSStickerGroup cSStickerGroup = list.get(i);
            if (cSStickerGroup == null || cSStickerGroup.getList_sticker() == null || cSStickerGroup.getList_sticker().size() <= 0) {
                return;
            }
            CSStickerRes cSStickerRes = cSStickerGroup.getList_sticker().get(0);
            if (cSStickerRes.getImageType() == DMWBRes.LocationType.ASSERT) {
                this.imageView_main.setImageBitmap(DMBitmapDbUtil.getImageFromAssetsFile(CSStickerSettingListAdapter.this.mContext, cSStickerRes.getImageFileName()));
            } else if (cSStickerRes.getImageType() == DMWBRes.LocationType.ONLINE) {
                this.imageView_main.setImageBitmap(BitmapFactory.decodeFile(cSStickerRes.getImageFileName()));
            }
            if (cSStickerGroup.getStickerGroupType() == CSStickerGroup.StickerGroupType.ONLINE) {
                String str = DMPreferencesUtil.get(CSStickerSettingListAdapter.this.mContext, "group_names", cSStickerGroup.getGroup_name());
                if (str != null) {
                    this.textView_main.setText(str);
                } else {
                    this.textView_main.setText("");
                }
            } else if (cSStickerGroup.getGroup_name() != null) {
                TextView textView = this.textView_main;
                textView.setText(cSStickerGroup.getGroup_name() + "");
            } else {
                this.textView_main.setText("");
            }
            if (cSStickerGroup.getStickerGroupType() == CSStickerGroup.StickerGroupType.ONLINE) {
                this.btn_delete.setVisibility(View.VISIBLE);
            } else if (cSStickerGroup.getStickerGroupType() == CSStickerGroup.StickerGroupType.ASSERT) {
                this.btn_delete.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void setOnStickerSettingItemClikListener(onStickerSettingItemClikListener onstickersettingitemcliklistener) {
        this.mListener = onstickersettingitemcliklistener;
    }

    public void setSelectedPos(int i) {
        this.selectedPos = i;
        notifyDataSetChanged();
    }
}
