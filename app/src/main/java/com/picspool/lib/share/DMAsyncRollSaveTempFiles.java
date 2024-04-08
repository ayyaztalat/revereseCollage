package com.picspool.lib.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

/* loaded from: classes3.dex */
public class DMAsyncRollSaveTempFiles extends AsyncTask<String, Void, Boolean> {
    private Bitmap[] bmp;
    private Context context;
    private String[] filename;
    private String folder;
    private Bitmap.CompressFormat format;
    private OnDMCameraRollSaveEventListener listener;

    public static void executeAsyncTask(Context context, String str, Bitmap[] bitmapArr, String[] strArr, OnDMCameraRollSaveEventListener onDMCameraRollSaveEventListener, Bitmap.CompressFormat compressFormat) {
        DMAsyncRollSaveTempFiles dMAsyncRollSaveTempFiles = new DMAsyncRollSaveTempFiles(context, str, strArr, bitmapArr, compressFormat);
        dMAsyncRollSaveTempFiles.setOnRollSaveTempFileEventListener(onDMCameraRollSaveEventListener);
        dMAsyncRollSaveTempFiles.execute(new String[0]);
    }

    public DMAsyncRollSaveTempFiles(Context context, String str, String[] strArr, Bitmap[] bitmapArr, Bitmap.CompressFormat compressFormat) {
        this.context = context;
        this.folder = str;
        this.bmp = bitmapArr;
        this.filename = strArr;
        this.format = compressFormat;
    }

    public void setOnRollSaveTempFileEventListener(OnDMCameraRollSaveEventListener onDMCameraRollSaveEventListener) {
        this.listener = onDMCameraRollSaveEventListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(String... strArr) {
        boolean z = true;
        int i = 0;
        while (true) {
            String[] strArr2 = this.filename;
            if (i < strArr2.length) {
                if (!DMShareOtherApp.saveToCameraRoll(this.context, this.folder, strArr2[i], this.bmp[i], this.format)) {
                    z = false;
                }
                i++;
            } else {
                return Boolean.valueOf(z);
            }
        }
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
