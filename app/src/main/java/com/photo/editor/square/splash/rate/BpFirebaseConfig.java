package com.photo.editor.square.splash.rate;

import android.content.Context;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.sky.testproject.R;
//import photo.editor.photo.collage.maker.R;

/* loaded from: classes2.dex */
public class BpFirebaseConfig {
    private static BpFirebaseConfig instance;
    private Context mContext;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    /* JADX INFO: Access modifiers changed from: private */
    public void getFirebaseConfig() {
    }

    private BpFirebaseConfig(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public static BpFirebaseConfig getInstance(Context context) {
        if (instance == null) {
            synchronized (BpFirebaseConfig.class) {
                if (instance == null) {
                    instance = new BpFirebaseConfig(context);
                }
            }
        }
        return instance;
    }

    private void fetchConfig() {
        try {
            // from class: com.photo.editor.square.splash.rate.BpFirebaseConfig.1
// com.google.android.gms.tasks.OnCompleteListener
            this.mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(task -> BpFirebaseConfig.this.getFirebaseConfig());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFirebaseRomote() {
        FirebaseApp.initializeApp(this.mContext);
        this.mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        this.mFirebaseRemoteConfig.setConfigSettingsAsync(new FirebaseRemoteConfigSettings.Builder().build());
        this.mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
    }

    public void init() {
        initFirebaseRomote();
        fetchConfig();
    }
}
