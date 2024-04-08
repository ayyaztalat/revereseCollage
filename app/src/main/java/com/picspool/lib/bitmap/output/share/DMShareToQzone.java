package com.picspool.lib.bitmap.output.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import com.picspool.lib.packages.DMOtherAppPackages;

/* loaded from: classes3.dex */
public class DMShareToQzone {
    public static void shareImage(Activity activity, Bitmap bitmap) {
        DMShareToApp.shareImage(activity, DMOtherAppPackages.qzonePackage, "shareQzone", DMShareTag.getDefaultTag(activity), bitmap);
    }

    public static void shareImageFromUri(Activity activity, Uri uri) {
        DMShareToApp.shareImageFromUri(activity, DMOtherAppPackages.qzonePackage, "shareQzone", DMShareTag.getDefaultTag(activity), uri);
    }
}
