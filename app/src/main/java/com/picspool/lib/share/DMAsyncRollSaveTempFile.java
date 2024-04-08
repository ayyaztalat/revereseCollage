package com.picspool.lib.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

/* loaded from: classes3.dex */
public class DMAsyncRollSaveTempFile extends AsyncTask<String, Void, Boolean> {
    private Bitmap bmp;
    private Context context;
    private String filename;
    private String folder;
    private Bitmap.CompressFormat format;
    private OnDMCameraRollSaveEventListener listener;

    public DMAsyncRollSaveTempFile(Context context, String str, String str2, Bitmap bitmap, Bitmap.CompressFormat compressFormat) {
        this.context = context;
        this.folder = str;
        this.bmp = bitmap;
        this.filename = str2;
        this.format = compressFormat;
    }

    public void setOnRollSaveTempFileEventListener(OnDMCameraRollSaveEventListener onDMCameraRollSaveEventListener) {
        this.listener = onDMCameraRollSaveEventListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(String... strArr) {
        return Boolean.valueOf(DMShareOtherApp.saveToCameraRoll(this.context, this.folder, this.filename, this.bmp, this.format));
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
