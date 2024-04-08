package com.picspool.libfuncview.effect.onlinestore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.messaging.ServiceStarter;

import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialD2Res;
import java.util.List;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSOLSEffectListStyle2Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int TYPE_Style1 = 0;
    private int TYPE_Style2 = 1;
    private int TYPE_Style3 = 2;
    private Context context;
    private List<CSEMaterialD2Res> materialResList;

    public CSOLSEffectListStyle2Adapter(Context context) {
        this.context = context;
    }

    public CSOLSEffectListStyle2Adapter(Context context, List<CSEMaterialD2Res> list) {
        this.context = context;
        this.materialResList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == this.TYPE_Style1) {
            return new Style1ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.view_adapter_item_ols_style2_vh1, viewGroup, false));
        }
        if (i == this.TYPE_Style2) {
            return new Style2ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.view_adapter_item_ols_style2_vh2, viewGroup, false));
        }
        return new Style3ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.view_adapter_item_ols_style2_vh3, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof Style1ViewHolder) {
            ((Style1ViewHolder) viewHolder).setDataItem(this.materialResList, i);
        } else if (viewHolder instanceof Style2ViewHolder) {
            ((Style2ViewHolder) viewHolder).setData(this.materialResList, i);
        } else if (viewHolder instanceof Style3ViewHolder) {
            ((Style3ViewHolder) viewHolder).setData(this.materialResList, i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        CSEMaterialD2Res cSEMaterialD2Res = this.materialResList.get(i);
        if (cSEMaterialD2Res.getItemType() == CSEMaterialD2Res.ItemType.BIGIMAGE) {
            return this.TYPE_Style1;
        }
        if (cSEMaterialD2Res.getItemType() == CSEMaterialD2Res.ItemType.TITLE) {
            return this.TYPE_Style2;
        }
        return this.TYPE_Style3;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.materialResList.size();
    }

    /* loaded from: classes.dex */
    class Style1ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgmain;

        public Style1ViewHolder(View view) {
            super(view);
            this.imgmain = (ImageView) view.findViewById(R.id.img_main);
            view.getLayoutParams().width = DMScreenInfoUtil.screenWidth(CSOLSEffectListStyle2Adapter.this.context);
            view.getLayoutParams().height = (int) (DMScreenInfoUtil.screenWidth(CSOLSEffectListStyle2Adapter.this.context) * 0.6944444f);
        }

        public void setDataItem(List<CSEMaterialD2Res> list, int i) {
            Glide.with(CSOLSEffectListStyle2Adapter.this.context).load(list.get(i).getIconFileName()).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.drawable.material_glide_load_default_500).override(720, ServiceStarter.ERROR_UNKNOWN)).into(this.imgmain);
        }
    }

    /* loaded from: classes.dex */
    class Style2ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_main;

        public Style2ViewHolder(View view) {
            super(view);
            this.textView_main = (TextView) view.findViewById(R.id.txt_title);
            view.getLayoutParams().width = DMScreenInfoUtil.screenWidth(CSOLSEffectListStyle2Adapter.this.context);
            view.getLayoutParams().height = DMScreenInfoUtil.dip2px(CSOLSEffectListStyle2Adapter.this.context, 80.0f);
        }

        public void setData(List<CSEMaterialD2Res> list, int i) {
            this.textView_main.setText(list.get(i).getShowText());
        }
    }

    /* loaded from: classes.dex */
    class Style3ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgmain;

        public Style3ViewHolder(View view) {
            super(view);
            this.imgmain = (ImageView) view.findViewById(R.id.img_main);
            view.getLayoutParams().width = (int) (DMScreenInfoUtil.screenWidth(CSOLSEffectListStyle2Adapter.this.context) / 2.0f);
            view.getLayoutParams().height = (int) (DMScreenInfoUtil.screenWidth(CSOLSEffectListStyle2Adapter.this.context) / 2.0f);
        }

        public void setData(List<CSEMaterialD2Res> list, int i) {
            Glide.with(CSOLSEffectListStyle2Adapter.this.context).load(list.get(i).getIconFileName()).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.drawable.material_glide_load_default_500).override(300, 300)).into(this.imgmain);
        }
    }
}
