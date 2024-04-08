package com.picspool.lib.share;

import android.content.Context;
import android.graphics.Bitmap;

/* loaded from: classes3.dex */
public class DMAsyncCameraRollSaveExecute {
    public static void executeAsyncTask(Context context, String str, Bitmap bitmap, OnDMCameraRollSaveEventListener onDMCameraRollSaveEventListener) {
        DMAsyncCameraRollSave23 dMAsyncCameraRollSave23 = new DMAsyncCameraRollSave23(context, str, bitmap);
        dMAsyncCameraRollSave23.setOnCameraRollSaveEventListener(onDMCameraRollSaveEventListener);
        dMAsyncCameraRollSave23.execute();
    }
}
