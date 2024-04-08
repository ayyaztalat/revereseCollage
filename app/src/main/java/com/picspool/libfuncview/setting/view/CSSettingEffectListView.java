package com.picspool.libfuncview.setting.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.effect.res.CSEffectRes;
import com.picspool.libfuncview.res.CSResManagerInterface;
import com.picspool.libfuncview.setting.adapter.CSSettingEffectListAdapter;
import java.util.Collections;
import java.util.List;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSettingEffectListView extends FrameLayout {
    private boolean enbaleDard;
    private ItemTouchHelper helper;
    private List<DMWBRes> list_groups;
    private CSSettingEffectListAdapter listadapter;
    private View lyClose;
    private Context mContext;
    private onItemClickLinstener mLinstener;
    private RecyclerView recyclerView;
    private CSResManagerInterface resManagerInterface;

    /* loaded from: classes.dex */
    public interface onItemClickLinstener {
        void onClick();
    }

    public CSSettingEffectListView(Context context, List<DMWBRes> list, CSResManagerInterface cSResManagerInterface) {
        super(context);
        this.enbaleDard = false;
        this.mContext = context;
        this.list_groups = list;
        this.resManagerInterface = cSResManagerInterface;
        initView();
    }

    private void initView() {
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_list_effect_setting, (ViewGroup) this, true);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        View findViewById = findViewById(R.id.ly_close);
        this.lyClose = findViewById;
        findViewById.setOnClickListener(new OnClickListener() { // from class: com.picspool.libfuncview.setting.view.CSSettingEffectListView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CSSettingEffectListView.this.mLinstener != null) {
                    CSSettingEffectListView.this.onlistChangedDone();
                    CSSettingEffectListView.this.mLinstener.onClick();
                }
            }
        });
        this.listadapter = new CSSettingEffectListAdapter(this.mContext, this.list_groups, this.resManagerInterface);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this.mContext, RecyclerView.VERTICAL, false));
        this.recyclerView.setAdapter(this.listadapter);
        this.listadapter.setOnStickerSettingItemClikListener(new CSSettingEffectListAdapter.onStickerSettingItemClikListener() { // from class: com.picspool.libfuncview.setting.view.CSSettingEffectListView.2
            @Override // com.picspool.libfuncview.setting.adapter.CSSettingEffectListAdapter.onStickerSettingItemClikListener
            public void onClick(int i) {
            }

            @Override // com.picspool.libfuncview.setting.adapter.CSSettingEffectListAdapter.onStickerSettingItemClikListener
            public void onDragTouched(boolean z) {
                CSSettingEffectListView cSSettingEffectListView = CSSettingEffectListView.this;
                cSSettingEffectListView.enbaleDard = !cSSettingEffectListView.enbaleDard;
            }

            @Override // com.picspool.libfuncview.setting.adapter.CSSettingEffectListAdapter.onStickerSettingItemClikListener
            public void onDragTouched(boolean z, CSSettingEffectListAdapter.MyViewHolder myViewHolder) {
                if (z) {
                    CSSettingEffectListView.this.helper.startDrag(myViewHolder);
                }
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() { // from class: com.picspool.libfuncview.setting.view.CSSettingEffectListView.3
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
                Collections.swap(CSSettingEffectListView.this.list_groups, viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
                CSSettingEffectListView.this.listadapter.notifyItemMoved(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
                return false;
            }
        });
        this.helper = itemTouchHelper;
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
    }

    public void onlistChangedDone() {
        DMWBRes dMWBRes = null;
        for (int i = 0; i < this.list_groups.size(); i++) {
            if (this.list_groups.get(i) instanceof CSEffectRes) {
                Context context = this.mContext;
                String orderKey = this.resManagerInterface.getOrderKey();
                DMPreferencesUtil.save(context, orderKey, ((CSEffectRes) dMWBRes).getGroupName() + dMWBRes.getName(), i + "");
            }
        }
    }

    public void setOnItemClickLinstener(onItemClickLinstener onitemclicklinstener) {
        this.mLinstener = onitemclicklinstener;
    }
}
