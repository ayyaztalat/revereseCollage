package com.picspool.lib.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

/* loaded from: classes3.dex */
public class DMAsyncRollSaveTempFile23 {
    private Bitmap bmp;
    private Context context;
    private String filename;
    private String folder;
    private Bitmap.CompressFormat format;
    private final Handler handler = new Handler();
    private OnDMCameraRollSaveEventListener listener;

    public DMAsyncRollSaveTempFile23(Context context, String str, String str2, Bitmap bitmap, Bitmap.CompressFormat compressFormat) {
        this.context = context;
        this.folder = str;
        this.bmp = bitmap;
        this.filename = str2;
        this.format = compressFormat;
    }

    public void setOnRollSaveTempFileEventListener(OnDMCameraRollSaveEventListener onDMCameraRollSaveEventListener) {
        this.listener = onDMCameraRollSaveEventListener;
    }

    public void execute() {
        new Thread(new Runnable() { // from class: com.picspool.lib.share.DMAsyncRollSaveTempFile23.1
            @Override // java.lang.Runnable
            public void run() {
                final boolean z;
                if (DMAsyncRollSaveTempFile23.this.bmp == null || DMAsyncRollSaveTempFile23.this.bmp.isRecycled()) {
                    z = false;
                } else {
                    synchronized (DMAsyncRollSaveTempFile23.this.bmp) {
                        z = DMShareOtherApp.saveToCameraRoll(DMAsyncRollSaveTempFile23.this.context, DMAsyncRollSaveTempFile23.this.folder, DMAsyncRollSaveTempFile23.this.filename, DMAsyncRollSaveTempFile23.this.bmp, DMAsyncRollSaveTempFile23.this.format);
                    }
                }
                DMAsyncRollSaveTempFile23.this.handler.post(new Runnable() { // from class: com.picspool.lib.share.DMAsyncRollSaveTempFile23.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (DMAsyncRollSaveTempFile23.this.listener != null) {
                            if (z) {
                                DMAsyncRollSaveTempFile23.this.listener.onSaveFinish();
                            } else {
                                DMAsyncRollSaveTempFile23.this.listener.onSaveFail();
                            }
                        }
                    }
                });
            }
        }).start();
    }
}
