package com.picspool.libfuncview.effect.onlinestore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.picspool.libfuncview.CSBaseAdActivity;
import com.picspool.libfuncview.effect.onlinestore.CSOLSEffectD2View;
import com.picspool.libfuncview.effect.onlinestore.adapter.CSOLSEffectListStyle1Adapter;
import com.picspool.libfuncview.effect.onlinestore.manager.CSOLSWBEEffectManager;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialDStoreRes;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEMaterialRes;
import com.picspool.libfuncview.effect.onlinestore.resource.CSEffectDataWrapper;
import com.picspool.libfuncview.setting.CSSettingEffectActivity;
import com.sky.testproject.R;

import java.util.List;

/* loaded from: classes.dex */
public class CSOLSEffectActivity extends CSBaseAdActivity implements CSOLSEffectListStyle1Adapter.ItemClickListener, CSOLSEffectD2View.onOnlineStoreEffectD2ViewCallBack {
    public static final int EffectMoreResult = 113;
    public static final String EffectOlsMore = "effect_home_more";
    public static final String EffectSelect = "effect_home_select";
    private View btn_back;
    private View btn_setting;
    public FrameLayout ly_main;
    private FrameLayout ly_root;
    private CSOLSEffectListStyle1Adapter olsEffectListStyle1Adapter;
    private CSOLSEffectD2View onlineStoreEffectD2View;
    private RecyclerView recyclerView;
    private boolean showdefault = false;
    private List<CSEMaterialDStoreRes> wbeMaterialDStoreResList;

    @Override // com.picspool.libfuncview.CSBaseAdActivity
    protected String getAppOpenTag() {
        return "filter_act";
    }

    @Override // com.picspool.libfuncview.CSBaseAdActivity
    protected String getBackAdTag() {
        return "filter_act";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_lib_onlinestore_effect);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(1024, 1024);
        this.ly_root = (FrameLayout) findViewById(R.id.ly_effect_act_root);
        this.btn_back = findViewById(R.id.btn_back);
        this.btn_setting = findViewById(R.id.btn_setting);
        this.btn_back.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.effect.onlinestore.CSOLSEffectActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSOLSEffectActivity.this.finish();
            }
        });
        this.btn_setting.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.effect.onlinestore.CSOLSEffectActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSOLSEffectActivity.this.startActivity(new Intent(CSOLSEffectActivity.this, CSSettingEffectActivity.class));
            }
        });
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        List<CSEMaterialDStoreRes> wbeMaterialDStoreResList = new CSOLSWBEEffectManager(this, CSEffectDataWrapper.getInstance(this).getWbMaterialGroupResList()).getWbeMaterialDStoreResList();
        this.wbeMaterialDStoreResList = wbeMaterialDStoreResList;
        CSOLSEffectListStyle1Adapter cSOLSEffectListStyle1Adapter = new CSOLSEffectListStyle1Adapter(this, wbeMaterialDStoreResList);
        this.olsEffectListStyle1Adapter = cSOLSEffectListStyle1Adapter;
        this.recyclerView.setAdapter(cSOLSEffectListStyle1Adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: com.picspool.libfuncview.effect.onlinestore.CSOLSEffectActivity.3
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                CSEMaterialDStoreRes cSEMaterialDStoreRes = (CSEMaterialDStoreRes) CSOLSEffectActivity.this.wbeMaterialDStoreResList.get(i);
                return (cSEMaterialDStoreRes.getItemType() == CSEMaterialDStoreRes.ItemType.VPBANNER || cSEMaterialDStoreRes.getItemType() == CSEMaterialDStoreRes.ItemType.TITLE) ? 2 : 1;
            }
        });
        this.recyclerView.setLayoutManager(gridLayoutManager);
        this.olsEffectListStyle1Adapter.setItemClickListener(this);
        if (getIntent().getSerializableExtra(EffectSelect) != null) {
            this.showdefault = true;
            creatEffectD2view((CSEMaterialRes) getIntent().getSerializableExtra(EffectSelect));
        }
    }

    @Override // com.picspool.libfuncview.effect.onlinestore.adapter.CSOLSEffectListStyle1Adapter.ItemClickListener
    public void onItemClick(View view, int i, CSEMaterialRes cSEMaterialRes) {
        creatEffectD2view(cSEMaterialRes);
    }

    private void creatEffectD2view(CSEMaterialRes cSEMaterialRes) {
        CSOLSEffectD2View cSOLSEffectD2View = this.onlineStoreEffectD2View;
        if (cSOLSEffectD2View == null) {
            CSOLSEffectD2View cSOLSEffectD2View2 = new CSOLSEffectD2View(this, cSEMaterialRes, this.ly_root);
            this.onlineStoreEffectD2View = cSOLSEffectD2View2;
            cSOLSEffectD2View2.setOnOnlineStoreEffectD2ViewCallBack(this);
            this.ly_root.addView(this.onlineStoreEffectD2View, new ViewGroup.LayoutParams(-1, -1));
            return;
        }
        this.ly_root.removeView(cSOLSEffectD2View);
        this.onlineStoreEffectD2View = null;
    }

    @Override // com.picspool.libfuncview.effect.onlinestore.CSOLSEffectD2View.onOnlineStoreEffectD2ViewCallBack
    public void onBackClicked() {
        if (!this.showdefault) {
            this.ly_root.removeView(this.onlineStoreEffectD2View);
            this.onlineStoreEffectD2View = null;
            CSOLSEffectListStyle1Adapter cSOLSEffectListStyle1Adapter = this.olsEffectListStyle1Adapter;
            if (cSOLSEffectListStyle1Adapter != null) {
                cSOLSEffectListStyle1Adapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.libfuncview.CSBaseAdActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        CSOLSEffectListStyle1Adapter cSOLSEffectListStyle1Adapter = this.olsEffectListStyle1Adapter;
        if (cSOLSEffectListStyle1Adapter != null) {
            cSOLSEffectListStyle1Adapter.notifyDataSetChanged();
        }
    }
}
