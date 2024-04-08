package com.picspool.lib.bitmap.output.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.Toast;
import java.io.File;
import com.picspool.lib.otherapp.DMOtherApp;
import com.picspool.lib.p017io.DMBitmapIoCache;
import com.picspool.lib.packages.DMAppPackages;
import com.sky.testproject.R;

/* loaded from: classes3.dex */
public class DMShareToApp {
    public static boolean shareImage(Activity activity, String str, String str2, String str3, Bitmap bitmap) {
        Uri fromFile;
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    if (str != null && !DMOtherApp.isInstalled(activity, str).booleanValue()) {
                        Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_installed), Toast.LENGTH_LONG).show();
                        return false;
                    }
                    String putJPG = DMBitmapIoCache.putJPG(DMAppPackages.getAppName(activity.getPackageName()) + ".jpg", bitmap);
                    if (putJPG == null || (fromFile = Uri.fromFile(new File(putJPG))) == null) {
                        return false;
                    }
                    return shareImageFromUri(activity, str, str2, str3, fromFile);
                }
            } catch (Exception unused) {
            }
        }
        Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_image), Toast.LENGTH_LONG).show();
        return false;
    }

    public static boolean shareImageFromUri(Activity activity, String str, String str2, String str3, Uri uri) {
        try {
            if (uri == null) {
                Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_image), Toast.LENGTH_LONG).show();
                return false;
            } else if (str != null && !DMOtherApp.isInstalled(activity, str).booleanValue()) {
                Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_installed), Toast.LENGTH_LONG).show();
                return false;
            } else {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.STREAM", uri);
                intent.putExtra("android.intent.extra.TITLE", str2);
                intent.putExtra("android.intent.extra.TEXT", str3);
                if (str != null) {
                    intent.setPackage(str);
                }
                activity.startActivity(intent);
                return true;
            }
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean shareText(Activity activity, String str, String str2, String str3) {
        try {
            if (DMOtherApp.isInstalled(activity, str).booleanValue()) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", str2);
                intent.putExtra("android.intent.extra.TEXT", str3);
                if (str != null) {
                    intent.setPackage(str);
                }
                activity.startActivity(intent);
                return true;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
