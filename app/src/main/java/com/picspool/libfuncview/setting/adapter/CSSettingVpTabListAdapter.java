package com.picspool.libfuncview.setting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMScreenInfoUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSettingVpTabListAdapter extends RecyclerView.Adapter<CSSettingVpTabListAdapter.MyViewHolder> {
    private List<DMWBRes> BMWBResList;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private int selectPos = 0;

    /* loaded from: classes.dex */
    public interface ItemClickListener {
        void onItemClick(View view, int i);
    }

    public CSSettingVpTabListAdapter(Context context, List<DMWBRes> list) {
        this.mContext = context;
        this.BMWBResList = list;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.view_adapter_item_setting_vptablist, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.setDataItem(this.BMWBResList, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.BMWBResList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(final View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.textView);
            view.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.setting.adapter.CSSettingVpTabListAdapter.MyViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    int adapterPosition = MyViewHolder.this.getAdapterPosition();
                    if (CSSettingVpTabListAdapter.this.itemClickListener != null) {
                        CSSettingVpTabListAdapter.this.itemClickListener.onItemClick(view, adapterPosition);
                    }
                    CSSettingVpTabListAdapter.this.selectPos = adapterPosition;
                    CSSettingVpTabListAdapter.this.notifyDataSetChanged();
                }
            });
            if (CSSettingVpTabListAdapter.this.BMWBResList.size() > 4) {
                view.getLayoutParams().width = (int) (DMScreenInfoUtil.screenWidth(CSSettingVpTabListAdapter.this.mContext) / 4.5f);
                return;
            }
            view.getLayoutParams().width = DMScreenInfoUtil.screenWidth(CSSettingVpTabListAdapter.this.mContext) / CSSettingVpTabListAdapter.this.BMWBResList.size();
        }

        public void setDataItem(List<DMWBRes> list, int i) {
            this.textView.setText(list.get(i).getShowText());
            if (CSSettingVpTabListAdapter.this.selectPos == i) {
                this.textView.setBackgroundDrawable(CSSettingVpTabListAdapter.this.mContext.getResources().getDrawable(R.drawable.xml_vptab_item_bg_select));
                this.textView.setTextColor(CSSettingVpTabListAdapter.this.mContext.getResources().getColor(R.color.libui_main_color_text));
                return;
            }
            this.textView.setBackgroundDrawable(CSSettingVpTabListAdapter.this.mContext.getResources().getDrawable(R.drawable.xml_vptab_item_bg));
            this.textView.setTextColor(CSSettingVpTabListAdapter.this.mContext.getResources().getColor(R.color.libui_main_color_texthint));
        }
    }

    public void setSelectPos(int i) {
        this.selectPos = i;
        notifyDataSetChanged();
    }
}
