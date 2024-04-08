package com.magic.video.editor.effect.cut.utils.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.magic.video.editor.effect.cut.utils.CSOtherApp;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class CSShareToApp {
    /* JADX WARN: Removed duplicated region for block: B:23:0x007c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007d A[Catch: Exception -> 0x0094, TryCatch #0 {Exception -> 0x0094, blocks: (B:4:0x0004, B:8:0x000e, B:10:0x0018, B:12:0x002a, B:15:0x0046, B:20:0x005c, B:24:0x007d, B:21:0x0076, B:26:0x0082), top: B:30:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean shareImage(Activity r4, String r5, String r6, String r7, android.graphics.Bitmap r8) {
        /*
            r0 = 1
            r1 = 0
            if (r8 == 0) goto L82
            boolean r2 = r8.isRecycled()     // Catch: java.lang.Exception -> L94
            if (r2 == 0) goto Lc
            goto L82
        Lc:
            if (r5 == 0) goto L2a
            java.lang.Boolean r2 = com.magic.video.editor.effect.cut.utils.CSOtherApp.isInstalled(r4, r5)     // Catch: java.lang.Exception -> L94
            boolean r2 = r2.booleanValue()     // Catch: java.lang.Exception -> L94
            if (r2 != 0) goto L2a
            android.content.res.Resources r5 = r4.getResources()     // Catch: java.lang.Exception -> L94
            int r6 = com.magic.video.editor.effect.cut.utils.C2000R.string.warning_no_installed     // Catch: java.lang.Exception -> L94
            java.lang.String r5 = r5.getString(r6)     // Catch: java.lang.Exception -> L94
            android.widget.Toast r4 = android.widget.Toast.makeText(r4, r5, r0)     // Catch: java.lang.Exception -> L94
            r4.show()     // Catch: java.lang.Exception -> L94
            return r1
        L2a:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L94
            r0.<init>()     // Catch: java.lang.Exception -> L94
            java.lang.String r2 = r4.getPackageName()     // Catch: java.lang.Exception -> L94
            r0.append(r2)     // Catch: java.lang.Exception -> L94
            java.lang.String r2 = ".jpg"
            r0.append(r2)     // Catch: java.lang.Exception -> L94
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Exception -> L94
            java.lang.String r8 = com.magic.video.editor.effect.cut.utils.CSBitmapIoCache.putJPG(r0, r8)     // Catch: java.lang.Exception -> L94
            if (r8 != 0) goto L46
            return r1
        L46:
            java.io.File r0 = new java.io.File     // Catch: java.lang.Exception -> L94
            r0.<init>(r8)     // Catch: java.lang.Exception -> L94
            int r8 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Exception -> L94
            android.content.pm.ApplicationInfo r2 = r4.getApplicationInfo()     // Catch: java.lang.Exception -> L94
            int r2 = r2.targetSdkVersion     // Catch: java.lang.Exception -> L94
            r3 = 24
            if (r8 < r3) goto L76
            r8 = 23
            if (r2 >= r8) goto L5c
            goto L76
        L5c:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L94
            r8.<init>()     // Catch: java.lang.Exception -> L94
            java.lang.String r2 = r4.getPackageName()     // Catch: java.lang.Exception -> L94
            r8.append(r2)     // Catch: java.lang.Exception -> L94
            java.lang.String r2 = ".fileprovider"
            r8.append(r2)     // Catch: java.lang.Exception -> L94
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Exception -> L94
            android.net.Uri r8 = androidx.core.content.FileProvider.getUriForFile(r4, r8, r0)     // Catch: java.lang.Exception -> L94
            goto L7a
        L76:
            android.net.Uri r8 = android.net.Uri.fromFile(r0)     // Catch: java.lang.Exception -> L94
        L7a:
            if (r8 != 0) goto L7d
            return r1
        L7d:
            boolean r4 = shareImageFromUri(r4, r5, r6, r7, r8)     // Catch: java.lang.Exception -> L94
            return r4
        L82:
            android.content.res.Resources r5 = r4.getResources()     // Catch: java.lang.Exception -> L94
            int r6 = com.magic.video.editor.effect.cut.utils.C2000R.string.warning_no_image     // Catch: java.lang.Exception -> L94
            java.lang.String r5 = r5.getString(r6)     // Catch: java.lang.Exception -> L94
            android.widget.Toast r4 = android.widget.Toast.makeText(r4, r5, r0)     // Catch: java.lang.Exception -> L94
            r4.show()     // Catch: java.lang.Exception -> L94
            return r1
        L94:
            r4 = move-exception
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magic.video.editor.effect.cut.utils.share.CSShareToApp.shareImage(android.app.Activity, java.lang.String, java.lang.String, java.lang.String, android.graphics.Bitmap):boolean");
    }

    public static boolean shareImageFromUri(Activity activity, String str, String str2, String str3, Uri uri) {
        try {
            if (uri == null) {
                Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_image), Toast.LENGTH_SHORT).show();
                return false;
            } else if (str != null && !CSOtherApp.isInstalled(activity, str).booleanValue()) {
                Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_installed), Toast.LENGTH_SHORT).show();
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
            if (CSOtherApp.isInstalled(activity, str).booleanValue()) {
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
