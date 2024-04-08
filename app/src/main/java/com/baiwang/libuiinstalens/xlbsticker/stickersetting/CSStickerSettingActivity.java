package com.baiwang.libuiinstalens.xlbsticker.stickersetting;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sky.testproject.R;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerGroup;
import com.baiwang.libuiinstalens.xlbsticker.stickerbar.CSStickerManager;
import com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingListAdapter;
import java.util.Collections;
import java.util.List;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes.dex */
public class CSStickerSettingActivity extends AppCompatActivity {
    private View btn_back;
    private View btn_done;
    private boolean enbaleDard = false;
    private ItemTouchHelper helper;
    private List<CSStickerGroup> list_groups;
    private CSStickerSettingListAdapter listadapter;
    private RecyclerView recyclerView;
    private CSStickerManager stickerManager;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_sticker_setting_cs);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(1024, 1024);
        initView();
    }

    private void initView() {
        this.btn_back = findViewById(R.id.btn_back);
        this.btn_done = findViewById(R.id.btn_done);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview_1);
        this.btn_back.setOnClickListener(new View.OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSStickerSettingActivity.this.onlistChangedDone();
                CSStickerSettingActivity.this.finish();
            }
        });
        this.btn_done.setOnClickListener(new View.OnClickListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSStickerSettingActivity.this.onlistChangedDone();
                CSStickerSettingActivity.this.finish();
            }
        });
        CSStickerManager cSStickerManager = new CSStickerManager(this);
        this.stickerManager = cSStickerManager;
        List<CSStickerGroup> list_groups = cSStickerManager.getList_groups();
        this.list_groups = list_groups;
        this.listadapter = new CSStickerSettingListAdapter(this, list_groups);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.recyclerView.setAdapter(this.listadapter);
        this.listadapter.setOnStickerSettingItemClikListener(new CSStickerSettingListAdapter.onStickerSettingItemClikListener() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingActivity.3
            @Override // com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingListAdapter.onStickerSettingItemClikListener
            public void onClick(int i) {
            }

            @Override // com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingListAdapter.onStickerSettingItemClikListener
            public void onDragTouched(boolean z) {
                CSStickerSettingActivity cSStickerSettingActivity = CSStickerSettingActivity.this;
                cSStickerSettingActivity.enbaleDard = !cSStickerSettingActivity.enbaleDard;
            }

            @Override // com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingListAdapter.onStickerSettingItemClikListener
            public void onDragTouched(boolean z, CSStickerSettingListAdapter.MyViewHolder myViewHolder) {
                if (z) {
                    CSStickerSettingActivity.this.helper.startDrag(myViewHolder);
                }
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() { // from class: com.baiwang.libuiinstalens.xlbsticker.stickersetting.CSStickerSettingActivity.4
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
                Collections.swap(CSStickerSettingActivity.this.list_groups, viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
                CSStickerSettingActivity.this.listadapter.notifyItemMoved(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
                return false;
            }
        });
        this.helper = itemTouchHelper;
        itemTouchHelper.attachToRecyclerView(this.recyclerView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onlistChangedDone() {
        for (int i = 0; i < this.list_groups.size(); i++) {
            DMPreferencesUtil.save(this, "xlbsticker", "sticker_group" + this.list_groups.get(i).getGroup_name(), i + "");
        }
    }
}
