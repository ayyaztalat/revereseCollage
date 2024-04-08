package com.picspool.libfuncview.setting.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.res.CSGroupRes;
import com.picspool.libfuncview.res.CSResManagerInterface;
import com.picspool.libfuncview.setting.adapter.CSSettingListAdapter;
import java.util.Collections;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSettingListView extends FrameLayout {
    private boolean enbaleDard;
    private ItemTouchHelper helper;
    private List<CSGroupRes> list_groups;
    private CSSettingListAdapter listadapter;
    private Context mContext;
    private onListItemClickListener mListener;
    private RecyclerView recyclerView;
    private CSResManagerInterface resManagerInterface;

    /* loaded from: classes.dex */
    public interface onListItemClickListener {
        void onItemClicked(int i, DMWBRes dMWBRes);
    }

    public CSSettingListView(Context context, List<CSGroupRes> list, CSResManagerInterface cSResManagerInterface) {
        super(context);
        this.enbaleDard = false;
        this.mContext = context;
        this.list_groups = list;
        this.resManagerInterface = cSResManagerInterface;
        initView();
    }

    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_list_setting, (ViewGroup) this, true);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.listadapter = new CSSettingListAdapter(this.mContext, this.list_groups, this.resManagerInterface);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.VERTICAL, false));
        this.recyclerView.setAdapter(this.listadapter);
        this.listadapter.setOnStickerSettingItemClikListener(new CSSettingListAdapter.onStickerSettingItemClikListener() { // from class: com.picspool.libfuncview.setting.view.CSSettingListView.1
            @Override // com.picspool.libfuncview.setting.adapter.CSSettingListAdapter.onStickerSettingItemClikListener
            public void onClick(int i, DMWBRes dMWBRes) {
                if (CSSettingListView.this.mListener != null) {
                    CSSettingListView.this.mListener.onItemClicked(i, dMWBRes);
                }
            }

            @Override // com.picspool.libfuncview.setting.adapter.CSSettingListAdapter.onStickerSettingItemClikListener
            public void onDragTouched(boolean z) {
                CSSettingListView cSSettingListView = CSSettingListView.this;
                cSSettingListView.enbaleDard = !cSSettingListView.enbaleDard;
            }

            @Override // com.picspool.libfuncview.setting.adapter.CSSettingListAdapter.onStickerSettingItemClikListener
            public void onDragTouched(boolean z, CSSettingListAdapter.MyViewHolder myViewHolder) {
                if (z) {
                    CSSettingListView.this.helper.startDrag(myViewHolder);
                }
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() { // from class: com.picspool.libfuncview.setting.view.CSSettingListView.2
            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(3, 0);
            }

            @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                Collections.swap(CSSettingListView.this.list_groups, viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
                CSSettingListView.this.listadapter.notifyItemMoved(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
                return false;
            }
        });
        this.helper = itemTouchHelper;
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
    }

    public void onlistChangedDone() {
        for (int i = 0; i < this.list_groups.size(); i++) {
            String name = this.list_groups.get(i).getName();
            Context context = this.mContext;
            String orderKey = this.resManagerInterface.getOrderKey();
            DMPreferencesUtil.save(context, orderKey, name, i + "");
        }
    }

    public void setOnListItemClickListener(onListItemClickListener onlistitemclicklistener) {
        this.mListener = onlistitemclicklistener;
    }
}
