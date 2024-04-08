package com.picspool.lib.p017io;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import java.io.File;

/* renamed from: com.picspool.lib.io.DMCameraTakenUri */
/* loaded from: classes3.dex */
public class DMCameraTakenUri {
    public static Uri uriFromCamera(Intent intent) {
        Bitmap bitmap = (Bitmap) intent.getExtras().get("data");
        String putJPG = DMBitmapIoCache.putJPG("ResultPic", bitmap);
        if (putJPG == null || putJPG == "") {
            return null;
        }
        Uri fromFile = Uri.fromFile(new File(putJPG));
        if (fromFile != null && bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return fromFile;
    }

    public static Bitmap bitmapFromCamera(Intent intent) {
        return (Bitmap) intent.getExtras().get("data");
    }
}
