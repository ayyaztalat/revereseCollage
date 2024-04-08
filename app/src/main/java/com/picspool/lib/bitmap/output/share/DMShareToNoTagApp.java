package com.picspool.lib.bitmap.output.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics;

/* loaded from: classes3.dex */
public class DMShareToNoTagApp {
    public static void shareImage(Activity activity, String str, Bitmap bitmap) {
        DMShareToApp.shareImage(activity, str, FirebaseAnalytics.Event.SHARE, "", bitmap);
    }

    public static void shareImageFromUri(Activity activity, String str, Uri uri) {
        DMShareToApp.shareImageFromUri(activity, str, FirebaseAnalytics.Event.SHARE, "", uri);
    }

    public static void shareImage(Activity activity, Bitmap bitmap) {
        DMShareToApp.shareImage(activity, null, FirebaseAnalytics.Event.SHARE, "", bitmap);
    }

    public static void shareImageFromUri(Activity activity, Uri uri) {
        DMShareToApp.shareImageFromUri(activity, null, FirebaseAnalytics.Event.SHARE, "", uri);
    }
}
