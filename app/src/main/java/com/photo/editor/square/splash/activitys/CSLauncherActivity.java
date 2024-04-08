package com.photo.editor.square.splash.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSLauncherActivity extends AppCompatActivity {
    private Runnable runnable = new Runnable() { // from class: com.photo.editor.square.splash.activitys.CSLauncherActivity.1
        @Override // java.lang.Runnable
        public void run() {
            CSLauncherActivity.this.startActivity(new Intent(CSLauncherActivity.this, CSMainActivity.class));
            CSLauncherActivity.this.finish();
        }
    };
    private Handler handler = new Handler();

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_laucnher_cs);
        this.handler.postDelayed(this.runnable, 2500L);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Handler handler = this.handler;
        if (handler != null) {
            handler.removeCallbacks(this.runnable);
        }
    }
}
