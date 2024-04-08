package com.picspool.lib.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import java.util.Date;

/* loaded from: classes3.dex */
public class DMAsyncCameraRollSave23 {
    private Bitmap bmp;
    private Context context;
    private String folder;
    private final Handler handler = new Handler();
    private OnDMCameraRollSaveEventListener listener;

    public DMAsyncCameraRollSave23(Context context, String str, Bitmap bitmap) {
        this.context = context;
        this.folder = str;
        this.bmp = bitmap;
    }

    public void setOnCameraRollSaveEventListener(OnDMCameraRollSaveEventListener onDMCameraRollSaveEventListener) {
        this.listener = onDMCameraRollSaveEventListener;
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.picspool.lib.share.DMAsyncCameraRollSave23.1
            @Override // java.lang.Runnable
            public void run() {
                final boolean z;
                if (DMAsyncCameraRollSave23.this.bmp == null || DMAsyncCameraRollSave23.this.bmp.isRecycled()) {
                    z = false;
                } else {
                    synchronized (DMAsyncCameraRollSave23.this.bmp) {
                        long time = new Date().getTime();
                        z = DMShareOtherApp.saveToCameraRoll(DMAsyncCameraRollSave23.this.context, DMAsyncCameraRollSave23.this.folder, "img" + String.valueOf(time) + ".png", DMAsyncCameraRollSave23.this.bmp, Bitmap.CompressFormat.PNG);
                    }
                }
                DMAsyncCameraRollSave23.this.handler.post(new Runnable() { // from class: com.picspool.lib.share.DMAsyncCameraRollSave23.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DMAsyncCameraRollSave23.this.listener != null) {
                            if (z) {
                                DMAsyncCameraRollSave23.this.listener.onSaveFinish();
                            } else {
                                DMAsyncCameraRollSave23.this.listener.onSaveFail();
                            }
                        }
                    }
                });
            }
        }).start();
    }
}
