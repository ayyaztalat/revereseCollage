package com.photo.editor.square.splash.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.photo.editor.square.splash.utils.CSFeedBackUtil;
import com.photo.editor.square.splash.utils.CSShareUtil;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSSettingActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_setting_cs);
        findViewById(R.id.setting_back_icon).setOnClickListener(view -> finish());
        findViewById(R.id.share).setOnClickListener(view -> CSShareUtil.shareApp(CSSettingActivity.this, "Share with"));
        findViewById(R.id.yhxy).setOnClickListener(view -> {
            Intent intent2 = new Intent(CSSettingActivity.this, CSWebViewActivity.class);
            intent2.setAction(CSWebViewActivity.ACTION_AGREEMENT);
            startActivity(intent2);
        });
        findViewById(R.id.about).setOnClickListener(view -> {
            Intent intent = new Intent(CSSettingActivity.this, CSWebViewActivity.class);
            intent.setAction(CSWebViewActivity.ACTION_POLICY);
            startActivity(intent);
        });
        findViewById(R.id.contact).setOnClickListener(view -> CSShareUtil.composeEmail(CSSettingActivity.this, CSFeedBackUtil.createEmailAddress(), "", "Contact us!", ""));
    }
}
