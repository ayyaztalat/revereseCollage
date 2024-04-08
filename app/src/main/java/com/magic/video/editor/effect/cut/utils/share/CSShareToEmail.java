package com.magic.video.editor.effect.cut.utils.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import com.sky.testproject.R;
/* loaded from: classes2.dex */
public class CSShareToEmail {
    public static void shareImage(Activity activity, Bitmap bitmap) {
        CSShareToApp.shareImage(activity, "com.android.email", "ShareE-mail", activity.getResources().getString(R.string.app_name), bitmap);
    }

    public static void shareImageFromUri(Activity activity, Uri uri) {
        CSShareToApp.shareImageFromUri(activity, "com.android.email", "ShareE-mail", activity.getResources().getString(R.string.app_name), uri);
    }
}
