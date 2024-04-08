package com.picspool.libfuncview.setting;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.picspool.libfuncview.effect.CSEffectBarManager2;
import com.picspool.libfuncview.setting.view.CSSettingListView;
import com.sky.testproject.R;

/* loaded from: classes.dex */
public class CSSettingEffectActivity extends AppCompatActivity {
    private View btn_back;
    private View btn_done;
    private FrameLayout lyRoot;
    private CSSettingListView settingListView;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_lib_setting_effect);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(1024, 1024);
        initView();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4) {
            onlistChangedDone();
            finish();
            return false;
        }
        return false;
    }

    private void initView() {
        this.lyRoot = (FrameLayout) findViewById(R.id.ly_act_setting_root);
        this.btn_back = findViewById(R.id.btn_back);
        this.btn_done = findViewById(R.id.btn_done);
        this.btn_back.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.setting.CSSettingEffectActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSettingEffectActivity.this.onlistChangedDone();
                CSSettingEffectActivity.this.finish();
            }
        });
        this.btn_done.setOnClickListener(new View.OnClickListener() { // from class: com.picspool.libfuncview.setting.CSSettingEffectActivity.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CSSettingEffectActivity.this.onlistChangedDone();
                CSSettingEffectActivity.this.finish();
            }
        });
        CSEffectBarManager2 cSEffectBarManager2 = new CSEffectBarManager2(this, false);
        CSSettingListView cSSettingListView = new CSSettingListView(this, cSEffectBarManager2.getResGroupList(), cSEffectBarManager2);
        this.settingListView = cSSettingListView;
        this.lyRoot.addView(cSSettingListView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onlistChangedDone() {
        this.settingListView.onlistChangedDone();
    }
}
