package com.picspool.libfuncview;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes.dex */
public class CSBaseAdActivity extends AppCompatActivity {
    public static boolean isBecameForeground;
    private boolean isResumed;
    private boolean showAppOpenAdWhenResume;
    private boolean showBackAd;

    protected String getAppOpenTag() {
        return "";
    }

    protected String getBackAdTag() {
        return "";
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStart() {
        super.onStart();
        if (isBecameForeground) {
            this.showAppOpenAdWhenResume = true;
            isBecameForeground = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onStop() {
        super.onStop();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        try {
            super.onResume();
        } catch (Exception unused) {
        }
        this.isResumed = true;
        if (this.showAppOpenAdWhenResume) {
            this.showAppOpenAdWhenResume = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        try {
            super.onPause();
        } catch (Exception unused) {
        }
        this.isResumed = false;
    }

    @Override // android.app.Activity
    public void finish() {
        this.showBackAd = false;
        finishNOAd();
    }

    public void finishNOAd() {
        super.finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        isBecameForeground = false;
    }
}
