package com.picspool.lib.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

/* loaded from: classes3.dex */
public class DMAsyncCameraRollSave extends AsyncTask<String, Void, Boolean> {
    private Bitmap bmp;
    private Context context;
    private String folder;
    private OnDMCameraRollSaveEventListener listener;

    public DMAsyncCameraRollSave(Context context, String str, Bitmap bitmap) {
        this.context = context;
        this.folder = str;
        this.bmp = bitmap;
    }

    public void setOnCameraRollSaveEventListener(OnDMCameraRollSaveEventListener onDMCameraRollSaveEventListener) {
        this.listener = onDMCameraRollSaveEventListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(String... strArr) {
        Boolean valueOf;
        Bitmap bitmap = this.bmp;
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        synchronized (this.bmp) {
            valueOf = Boolean.valueOf(DMShareOtherApp.saveToCameraRoll(this.context, this.folder, this.bmp));
        }
        return valueOf;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Boolean bool) {
        if (this.listener != null) {
            if (bool.booleanValue()) {
                this.listener.onSaveFinish();
            } else {
                this.listener.onSaveFail();
            }
        }
    }
}
