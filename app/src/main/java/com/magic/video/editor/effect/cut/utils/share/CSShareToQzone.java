package com.magic.video.editor.effect.cut.utils.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import com.sky.testproject.R;
import com.magic.video.editor.effect.cut.utils.CSOtherAppPackages2;

/* loaded from: classes2.dex */
public class CSShareToQzone {
    public static void shareImage(Activity activity, Bitmap bitmap) {
        CSShareToApp.shareImage(activity, CSOtherAppPackages2.qzonePackage, "shareQzone", activity.getResources().getString(R.string.app_name), bitmap);
    }

    public static void shareImageFromUri(Activity activity, Uri uri) {
        CSShareToApp.shareImageFromUri(activity, CSOtherAppPackages2.qzonePackage, "shareQzone", activity.getResources().getString(R.string.app_name), uri);
    }
}
