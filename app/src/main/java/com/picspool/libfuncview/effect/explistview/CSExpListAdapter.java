package com.picspool.libfuncview.effect.explistview;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.picspool.libfuncview.effect.CSEffectBarManager2;
import com.picspool.libfuncview.res.CSGroupRes;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.bitmap.multi.DMBitmapDbUtil;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSExpListAdapter extends RecyclerView.Adapter<CSExpListAdapter.MyViewHolder> {
    private Context mContext;
    private onRecyclerViewItemClikListener mListener;
    private MyViewHolder myViewHolder;
    private List<DMWBRes> resList;
    private List<Integer> animationlist = new ArrayList();
    private int currentExpGroupPos = -1;
    private int selectPos = -1;
    private int itemcount = 0;

    /* loaded from: classes.dex */
    public interface onRecyclerViewItemClikListener {
        void onClick(int i, boolean z);

        void onContentClick(int i, int i2, DMWBRes dMWBRes);
    }

    public CSExpListAdapter(List<DMWBRes> list, Context context) {
        initData(context, list);
    }

    private void initData(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.resList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder myViewHolder = new MyViewHolder(((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_style3_adapter_item, viewGroup, false));
        this.myViewHolder = myViewHolder;
        return myViewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setData(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.resList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        View ViewItemSelect;
        ImageView imageView;
        ImageView imageView_group;
        ImageView imageView_group_add;
        View ly_content_container;
        View ly_group_add;
        View ly_group_container;
        View mItemview;
        TextView textView;
        TextView textView_group;
        TextView textView_group_add;

        public MyViewHolder(View view) {
            super(view);
            this.mItemview = view;
            this.ly_group_container = view.findViewById(R.id.ly_group_container);
            this.ly_content_container = view.findViewById(R.id.ly_content_container);
            this.ly_group_add = view.findViewById(R.id.ly_add);
            this.imageView_group = (ImageView) view.findViewById(R.id.img_main_group);
            this.textView_group = (TextView) view.findViewById(R.id.textname_group);
            this.imageView_group_add = (ImageView) view.findViewById(R.id.img_main_add);
            this.textView_group_add = (TextView) view.findViewById(R.id.textname_add);
            this.imageView = (ImageView) view.findViewById(R.id.img_main);
            this.textView = (TextView) view.findViewById(R.id.textname);
            this.ViewItemSelect = view.findViewById(R.id.textselect);
            CSExpListAdapter.this.itemcount = (int) Math.ceil(DMScreenInfoUtil.screenWidth(CSExpListAdapter.this.mContext) / DMScreenInfoUtil.dip2px(CSExpListAdapter.this.mContext, 70.0f));
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.effect.explistview.CSExpListAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= CSExpListAdapter.this.resList.size()) {
                        return;
                    }
                    DMWBRes dMWBRes = (DMWBRes) CSExpListAdapter.this.resList.get(adapterPosition);
                    if (!(dMWBRes instanceof CSGroupRes)) {
                        if (CSExpListAdapter.this.mListener != null) {
                            CSExpListAdapter.this.mListener.onContentClick(adapterPosition, CSExpListAdapter.this.currentExpGroupPos, dMWBRes);
                        }
                        CSExpListAdapter.this.selectPos = adapterPosition;
                        CSExpListAdapter.this.notifyDataSetChanged();
                    } else if (TextUtils.equals(dMWBRes.getName(), CSEffectBarManager2.EffectStoreResName)) {
                        if (CSExpListAdapter.this.mListener != null) {
                            CSExpListAdapter.this.mListener.onContentClick(adapterPosition, CSExpListAdapter.this.currentExpGroupPos, dMWBRes);
                        }
                        CSExpListAdapter.this.selectPos = adapterPosition;
                        CSExpListAdapter.this.notifyDataSetChanged();
                    } else {
                        CSExpListAdapter.this.onCollapse(adapterPosition);
                        int adapterPosition2 = MyViewHolder.this.getAdapterPosition();
                        if (adapterPosition2 < 0 || adapterPosition2 >= CSExpListAdapter.this.resList.size()) {
                            return;
                        }
                        CSExpListAdapter.this.selectPos = -1;
                        CSGroupRes cSGroupRes = (CSGroupRes) ((DMWBRes) CSExpListAdapter.this.resList.get(adapterPosition2));
                        boolean isExpanded = cSGroupRes.isExpanded();
                        int size = cSGroupRes.getList_res().size();
                        if (!isExpanded) {
                            CSExpListAdapter.this.currentExpGroupPos = adapterPosition2;
                            cSGroupRes.setExpanded(!isExpanded);
                            for (int i = 0; i < size; i++) {
                                int i2 = adapterPosition2 + i + 1;
                                CSExpListAdapter.this.resList.add(i2, cSGroupRes.getList_res().get(i));
                                if (CSExpListAdapter.this.animationlist.size() < CSExpListAdapter.this.itemcount - 1) {
                                    CSExpListAdapter.this.animationlist.add(Integer.valueOf(i2));
                                }
                            }
                            CSExpListAdapter.this.notifyItemRangeInserted(adapterPosition2 + 1, size);
                            if (CSExpListAdapter.this.mListener != null) {
                                CSExpListAdapter.this.mListener.onClick(adapterPosition2, true);
                                return;
                            }
                            return;
                        }
                        CSExpListAdapter.this.currentExpGroupPos = -1;
                        cSGroupRes.setExpanded(!isExpanded);
                        for (int i3 = 0; i3 < size; i3++) {
                            CSExpListAdapter.this.resList.remove(adapterPosition2 + 1);
                            CSExpListAdapter.this.animationlist.clear();
                        }
                        CSExpListAdapter.this.notifyItemRangeRemoved(adapterPosition2 + 1, size);
                        if (CSExpListAdapter.this.mListener != null) {
                            CSExpListAdapter.this.mListener.onClick(adapterPosition2, false);
                        }
                    }
                }
            });
        }

        public void setData(int i) {
            ImageView imageView;
            TextView textView;
            int i2 = -1;
            boolean z = false;
            for (int i3 = 0; i3 < CSExpListAdapter.this.animationlist.size(); i3++) {
                if (((Integer) CSExpListAdapter.this.animationlist.get(i3)).intValue() == i) {
                    z = true;
                    i2 = i3;
                }
            }
            if (z) {
                this.mItemview.startAnimation(AnimationUtils.loadAnimation(CSExpListAdapter.this.mContext, R.anim.explist_addin));
                CSExpListAdapter.this.animationlist.remove(i2);
            }
            DMWBRes dMWBRes = (DMWBRes) CSExpListAdapter.this.resList.get(i);
            if (dMWBRes instanceof CSGroupRes) {
                this.ly_content_container.setVisibility(View.GONE);
                if (!dMWBRes.getName().endsWith("_add")) {
                    this.ly_group_container.setVisibility(View.VISIBLE);
                    this.ly_group_add.setVisibility(View.GONE);
                    imageView = this.imageView_group;
                    textView = this.textView_group;
                } else {
                    this.ly_group_container.setVisibility(View.GONE);
                    this.ly_group_add.setVisibility(View.VISIBLE);
                    imageView = this.imageView_group_add;
                    textView = this.textView_group_add;
                }
            } else {
                this.ly_group_container.setVisibility(View.GONE);
                this.ly_group_add.setVisibility(View.GONE);
                this.ly_content_container.setVisibility(View.VISIBLE);
                imageView = this.imageView;
                textView = this.textView;
            }
            if (dMWBRes.getIconFileName() != null) {
                if (dMWBRes.getIconType() == DMWBRes.LocationType.ASSERT) {
                    Bitmap imageFromAssetsFile = DMBitmapDbUtil.getImageFromAssetsFile(CSExpListAdapter.this.mContext, dMWBRes.getIconFileName());
                    if (imageFromAssetsFile != null && imageFromAssetsFile.getHeight() > 0 && imageFromAssetsFile.getWidth() > 0) {
                        imageView.setImageBitmap(imageFromAssetsFile);
                    }
                } else {
                    Bitmap imageFromSDFile = DMBitmapUtil.getImageFromSDFile(CSExpListAdapter.this.mContext, dMWBRes.getIconFileName());
                    if (imageFromSDFile != null && imageFromSDFile.getHeight() > 0 && imageFromSDFile.getWidth() > 0) {
                        imageView.setImageBitmap(imageFromSDFile);
                    }
                }
            } else {
                imageView.setImageBitmap(null);
            }
            textView.setText(dMWBRes.getShowText());
            if (CSExpListAdapter.this.selectPos == i && !dMWBRes.getName().endsWith("_add")) {
                this.ViewItemSelect.setVisibility(View.VISIBLE);
            } else {
                this.ViewItemSelect.setVisibility(View.GONE);
            }
        }
    }

    public void setOnRecyclerViewItemClikListener(onRecyclerViewItemClikListener onrecyclerviewitemcliklistener) {
        this.mListener = onrecyclerviewitemcliklistener;
    }

    public void onCollapse(int i) {
        int i2 = -1;
        for (int i3 = 0; i3 < this.resList.size(); i3++) {
            DMWBRes dMWBRes = this.resList.get(i3);
            if ((dMWBRes instanceof CSGroupRes) && ((CSGroupRes) dMWBRes).isExpanded()) {
                i2 = i3;
            }
        }
        if (i2 < 0 || i == i2) {
            return;
        }
        CSGroupRes cSGroupRes = (CSGroupRes) this.resList.get(i2);
        cSGroupRes.setExpanded(false);
        for (int i4 = 0; i4 < cSGroupRes.getList_res().size(); i4++) {
            this.resList.remove(i2 + 1);
        }
        notifyItemRangeRemoved(i2 + 1, cSGroupRes.getList_res().size());
    }

    public int getSelectPos() {
        return this.selectPos;
    }

    public void setSelectPos(int i) {
        this.selectPos = i;
        notifyDataSetChanged();
    }
}
