package com.picspool.lib.bitmap.output.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;

/* loaded from: classes3.dex */
public class DMShareToEmail {
    public static void shareImage(Activity activity, Bitmap bitmap) {
        DMShareToApp.shareImage(activity, "com.android.email", "ShareE-mail", DMShareTag.getDefaultTag(activity), bitmap);
    }

    public static void shareImageFromUri(Activity activity, Uri uri) {
        DMShareToApp.shareImageFromUri(activity, "com.android.email", "ShareE-mail", DMShareTag.getDefaultTag(activity), uri);
    }
}
