package com.picspool.lib.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import com.picspool.lib.p017io.DMBitmapIoCache;

/* loaded from: classes3.dex */
public class DMShareOtherApp {
    static String shareText = "";

    /* loaded from: classes3.dex */
    public interface ShareCallBack {
        void onResult(boolean z, boolean z2);
    }

    public static void SetShareText(String str) {
        shareText = str;
    }

    public static boolean saveToCameraRoll(Context context, String str, String str2, Bitmap bitmap) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            if (str != null) {
                file = file + "/" + str;
            }
            File file2 = new File(file);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            new Date();
            String str3 = file + "/" + str2;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                try {
                    int length = byteArray.length;
                    FileOutputStream fileOutputStream = new FileOutputStream(str3);
                    fileOutputStream.write(byteArray, 0, length);
                    fileOutputStream.close();
                    MediaScannerConnection.scanFile(context, new String[]{str3}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.picspool.lib.share.DMShareOtherApp.1
                        @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                        public void onScanCompleted(String str4, Uri uri) {
                        }
                    });
                } catch (Exception unused) {
                }
                return true;
            }
        }
        return false;
    }

    public static boolean saveToCameraRoll(Context context, String str, String str2, Bitmap bitmap, Bitmap.CompressFormat compressFormat) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            if (str != null) {
                file = file + "/" + str;
            }
            File file2 = new File(file);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            new Date();
            String str3 = file + "/" + str2;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (compressFormat == null) {
                compressFormat = Bitmap.CompressFormat.JPEG;
            }
            if (bitmap != null) {
                try {
                    if (!bitmap.isRecycled()) {
                        bitmap.compress(compressFormat, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        int length = byteArray.length;
                        FileOutputStream fileOutputStream = new FileOutputStream(str3);
                        fileOutputStream.write(byteArray, 0, length);
                        fileOutputStream.close();
                        MediaScannerConnection.scanFile(context, new String[]{str3}, null, new MediaScannerConnection.OnScanCompletedListener() { // from class: com.picspool.lib.share.DMShareOtherApp.2
                            @Override // android.media.MediaScannerConnection.OnScanCompletedListener
                            public void onScanCompleted(String str4, Uri uri) {
                            }
                        });
                        return true;
                    }
                } catch (Exception unused) {
                }
            }
        }
        return false;
    }

    public static boolean saveToCameraRoll(Context context, String str, Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return false;
        }
        long time = new Date().getTime();
        return saveToCameraRoll(context, str, "img" + String.valueOf(time) + ".jpg", bitmap);
    }

    public static boolean isAppInstall(Context context, String str) {
        PackageInfo packageInfo;
        if (str == null) {
            return false;
        }
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            packageInfo = null;
        }
        return packageInfo != null;
    }

    public static boolean toOtherApp(Activity activity, String str, String str2, String str3, Bitmap bitmap) {
        if (str3 == "") {
            try {
                str3 = shareText;
            } catch (Exception unused) {
            }
        }
        if (bitmap != null && !bitmap.isRecycled()) {
            Uri fromFile = Uri.fromFile(new File(DMBitmapIoCache.putJPG(str2 + ".jpg", bitmap)));
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.STREAM", fromFile);
            intent.putExtra("android.intent.extra.TITLE", str2);
            intent.putExtra("android.intent.extra.TEXT", str3);
            if (str != null) {
                intent.setPackage(str);
            }
            activity.startActivity(intent);
            return true;
        }
        return false;
    }

    public static boolean toOtherAppFromUri(Activity activity, String str, String str2, String str3, Uri uri) {
        if (str3 == "") {
            try {
                str3 = shareText;
            } catch (Exception unused) {
                return false;
            }
        }
        if (uri == null) {
            return false;
        }
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

    public static boolean toOtherApp(Activity activity, String str, String str2, String str3) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.SUBJECT", str2);
            intent.putExtra("android.intent.extra.TEXT", str3);
            if (str != null) {
                intent.setPackage(str);
            }
            activity.startActivity(intent);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static void thirdPartAppIterator(Context context) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("*");
        for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentActivities(intent, 0)) {
            String str = resolveInfo.activityInfo.name;
            if (resolveInfo.activityInfo.name.contains("sina")) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                new ComponentName(activityInfo.applicationInfo.packageName, activityInfo.name);
            }
        }
    }

    public static void toInstagram(Activity activity, Bitmap bitmap, String str, int i) {
        toOtherApp(activity, "com.instagram.android", String.valueOf(i), str, bitmap);
    }

    public static void toInstagram(Activity activity, Bitmap bitmap, int i) {
        toOtherApp(activity, "com.instagram.android", String.valueOf(i), shareText, bitmap);
    }

    public static boolean isInstagramInstall(Context context) {
        return isAppInstall(context, "com.instagram.android");
    }

    public static void shareFacebook(Activity activity, Bitmap bitmap, int i) {
        toOtherApp(activity, "com.facebook.katana", String.valueOf(i), shareText, bitmap);
    }

    public static boolean isFacebookInstall(Context context) {
        return isAppInstall(context, "com.facebook.katana");
    }

    public static void shareTwitter(Activity activity, Bitmap bitmap, int i) {
        toOtherApp(activity, "com.twitter.android", String.valueOf(i), shareText, bitmap);
    }

    public static boolean isTwitterInstall(Context context) {
        return isAppInstall(context, "com.twitter.android");
    }

    public static void shareLine(Activity activity, Bitmap bitmap, int i) {
        toOtherApp(activity, "jp.naver.line.android", String.valueOf(i), shareText, bitmap);
    }

    public static boolean isLineInstall(Context context) {
        return isAppInstall(context, "jp.naver.line.android");
    }

    public static void shareTumblr(Activity activity, Bitmap bitmap, int i) {
        toOtherApp(activity, "com.tumblr", String.valueOf(i), shareText, bitmap);
    }

    public static boolean isTumblrInstall(Context context) {
        return isAppInstall(context, "com.tumblr");
    }

    public static void shareWhatsApp(Activity activity, Bitmap bitmap, int i) {
        toOtherApp(activity, "com.whatsapp", String.valueOf(i), shareText, bitmap);
    }

    public static boolean isWhatsAppInstall(Context context) {
        return isAppInstall(context, "com.whatsapp");
    }

    public static void shareSina(Activity activity, Bitmap bitmap, int i) {
        String valueOf = String.valueOf(i);
        toOtherApp(activity, "com.sina.weibo", valueOf, shareText + "#", bitmap);
    }

    public static boolean isSinaInstall(Context context) {
        return isAppInstall(context, "com.sina.weibo");
    }

    public static void shareQQZone(Activity activity, Bitmap bitmap, int i) {
        toOtherApp(activity, "com.qzone", String.valueOf(i), shareText, bitmap);
    }

    public static boolean isQQZoneInstall(Context context) {
        return isAppInstall(context, "com.qzone");
    }

    public static void shareQQ(Activity activity, Bitmap bitmap, int i) {
        toOtherApp(activity, "com.tencent.mobileqq", String.valueOf(i), shareText, bitmap);
    }

    public static boolean isQQInstall(Context context) {
        return isAppInstall(context, "com.tencent.mobileqq");
    }

    protected static void shareNormalItem(final Activity activity, final Bitmap bitmap, final String str, final String str2, final String str3, String str4, String str5, String str6, final ShareCallBack shareCallBack) {
        if (!isAppInstall(activity, str)) {
            Toast.makeText(activity, str6, 1).show();
            if (shareCallBack != null) {
                shareCallBack.onResult(false, false);
                return;
            }
            return;
        }
        new Handler().postDelayed(new Runnable() { // from class: com.picspool.lib.share.DMShareOtherApp.3
            @Override // java.lang.Runnable
            public void run() {
                if (!DMShareOtherApp.toOtherApp(activity, str, str2, str3, bitmap)) {
                    Toast.makeText(activity, str3, 1).show();
                    ShareCallBack shareCallBack2 = shareCallBack;
                    if (shareCallBack2 != null) {
                        shareCallBack2.onResult(true, false);
                        return;
                    }
                    return;
                }
                ShareCallBack shareCallBack3 = shareCallBack;
                if (shareCallBack3 != null) {
                    shareCallBack3.onResult(true, true);
                }
            }
        }, 200L);
    }

    public static String getSaveFolder(String str) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            if (str != null) {
                file = file + "/" + str;
            }
            File file2 = new File(file);
            if (file2.exists()) {
                return file;
            }
            file2.mkdirs();
            return file;
        }
        return "";
    }
}
