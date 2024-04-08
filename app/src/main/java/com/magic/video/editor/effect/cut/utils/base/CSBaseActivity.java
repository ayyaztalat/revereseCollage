package com.magic.video.editor.effect.cut.utils.base;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import com.magic.video.editor.effect.cut.utils.CSBarUtilsCompat;

/* loaded from: classes2.dex */
public class CSBaseActivity extends FragmentActivity {
    public void dismissProcessDialog() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CSBarUtilsCompat.setStatusBarColor(this, 0);
        CSBarUtilsCompat.setStatusBarLightMode((Activity) this, true);
    }
}
