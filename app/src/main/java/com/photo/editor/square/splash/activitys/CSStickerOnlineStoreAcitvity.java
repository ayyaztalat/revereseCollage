package com.photo.editor.square.splash.activitys;

import android.os.Bundle;
import android.util.Log;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialRes;
import com.photo.editor.square.splash.view.CSViewLockAd;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes2.dex */
public class CSStickerOnlineStoreAcitvity extends CSOnlineStoreActivity {
    private static final String TAG = "xlb";
    private String mResname;
    private CSViewLockAd viewLockAd;

    @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity
    public void lockeResClicked(String str, DMWBRes dMWBRes) {
    }

    @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.isVungleAd = false;
        initPopProgress();
    }

    @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity
    public void stickerApplyClicked(String str, CSWBMaterialRes cSWBMaterialRes) {
        super.stickerApplyClicked(str, cSWBMaterialRes);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onPause() {
        super.onPause();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initPopProgress() {
        CSViewLockAd cSViewLockAd = new CSViewLockAd(this, this.ly_main);
        this.viewLockAd = cSViewLockAd;
        cSViewLockAd.loadAd();
        this.viewLockAd.setOnLockAdNativeItemListener(new CSViewLockAd.onLockAdLoadedListener() { // from class: com.photo.editor.square.splash.activitys.CSStickerOnlineStoreAcitvity.1
            @Override // com.photo.editor.square.splash.view.CSViewLockAd.onLockAdLoadedListener
            public void onCloseClick() {
            }

            @Override // com.photo.editor.square.splash.view.CSViewLockAd.onLockAdLoadedListener
            public void onPositiveClicked() {
            }

            @Override // com.photo.editor.square.splash.view.CSViewLockAd.onLockAdLoadedListener
            public void onAdLoaded() {
                CSStickerOnlineStoreAcitvity.this.isAdLoaded = true;
            }

            @Override // com.photo.editor.square.splash.view.CSViewLockAd.onLockAdLoadedListener
            public void onProgressDone() {
                Log.d(CSStickerOnlineStoreAcitvity.TAG, "onProgress Done");
                CSStickerOnlineStoreAcitvity.this.locklistNotifyChanged();
            }
        });
    }

    @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity
    public void stickerdownloadClicked(String str, DMWBRes dMWBRes) {
        CSViewLockAd cSViewLockAd = this.viewLockAd;
        if (cSViewLockAd != null) {
            cSViewLockAd.showAdNative(dMWBRes, null, false);
        }
        this.mResname = str;
    }

    @Override // com.baiwang.libuiinstalens.xlbsticker.onlinestore.CSOnlineStoreActivity
    public void stickerdownloadPrgress(int i) {
        CSViewLockAd cSViewLockAd = this.viewLockAd;
        if (cSViewLockAd != null) {
            cSViewLockAd.setProgress(i);
        }
    }
}
