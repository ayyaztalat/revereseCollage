package com.picspool.lib.share;

import android.content.Context;
import android.graphics.Bitmap;

/* loaded from: classes3.dex */
public class DMAsyncRollSaveTempFileExecute {
    public static void executeAsyncTask(Context context, String str, Bitmap bitmap, String str2, OnDMCameraRollSaveEventListener onDMCameraRollSaveEventListener, Bitmap.CompressFormat compressFormat) {
        DMAsyncRollSaveTempFile23 dMAsyncRollSaveTempFile23 = new DMAsyncRollSaveTempFile23(context, str, str2, bitmap, compressFormat);
        dMAsyncRollSaveTempFile23.setOnRollSaveTempFileEventListener(onDMCameraRollSaveEventListener);
        dMAsyncRollSaveTempFile23.execute();
    }
}
