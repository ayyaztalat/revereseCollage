package com.picspool.lib.onlinestore.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.Iterator;
import java.util.List;
import com.picspool.lib.activity.DMFragmentActivityTemplate;

import com.picspool.lib.onlinestore.resource.WBMaterialFactory;
import com.picspool.lib.onlinestore.resource.WBMaterialRes;
import com.picspool.lib.onlinestore.resource.manager.DMMaterialResManager;
import com.picspool.lib.onlinestore.widget.DMBgListAdapter;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMOnlineBgManagerActivity extends DMFragmentActivityTemplate implements DMBgListAdapter.SelectedListener {
    private DMBgListAdapter adapter;
    List<WBMaterialRes> filesDirRess;
    private ListView listView;
    private DMMaterialResManager mManager;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.activity.DMFragmentActivityTemplate, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dm_bg_manager);
        this.listView = (ListView) findViewById(R.id.bg_list_view);
        this.adapter = new DMBgListAdapter(this);
        findViewById(R.id.activity_store_break).setOnClickListener(new View.OnClickListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineBgManagerActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DMOnlineBgManagerActivity.this.finish();
            }
        });
        List<WBMaterialRes> CreateMaterialFromFilesDir = WBMaterialFactory.CreateMaterialFromFilesDir(this, DMOnlineBgStoreActivity.BgMaterialType);
        this.filesDirRess = CreateMaterialFromFilesDir;
        Iterator<WBMaterialRes> it2 = CreateMaterialFromFilesDir.iterator();
        while (it2.hasNext()) {
            if (!it2.next().isContentExist()) {
                it2.remove();
            }
        }
        if (this.filesDirRess.size() == 0) {
            Toast.makeText(this, R.string.no_downloaded, 1).show();
        }
        DMMaterialResManager dMMaterialResManager = new DMMaterialResManager(this);
        this.mManager = dMMaterialResManager;
        dMMaterialResManager.setMaterialRess(this.filesDirRess);
        this.adapter.setsListener(this);
        this.adapter.setBgAdapterType(DMBgListAdapter.BgAdapterType.LOCAL);
        this.adapter.setResManager(this.mManager);
        this.listView.setAdapter((ListAdapter) this.adapter);
    }

    @Override // com.picspool.lib.onlinestore.widget.DMBgListAdapter.SelectedListener
    public void onSelected(WBMaterialRes wBMaterialRes) {
        dialog(wBMaterialRes);
    }

    protected void dialog(final WBMaterialRes wBMaterialRes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message);
        builder.setTitle(R.string.dialog_prompt);
        builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineBgManagerActivity.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                if (wBMaterialRes.isContentExist()) {
                    wBMaterialRes.delMaterialFromFile();
                    if (DMOnlineBgManagerActivity.this.adapter != null) {
                        DMOnlineBgManagerActivity.this.filesDirRess.remove(wBMaterialRes);
                    }
                    DMOnlineBgManagerActivity.this.adapter.notifyDataSetChanged();
                    dialogInterface.dismiss();
                }
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() { // from class: com.picspool.lib.onlinestore.activity.DMOnlineBgManagerActivity.3
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}
