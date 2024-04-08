package com.photo.editor.square.splash.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.net.MailTo;
import com.blankj.utilcode.util.ToastUtils;

/* loaded from: classes2.dex */
public class CSShareUtil {
    private static final String NO_BROWSER_APP = "No browser clients installed";
    private static final String NO_EMAIL_APP = "No email clients installed.";

    public static void composeEmail(Context context, String[] strArr, String str, Uri uri) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("*/*");
        intent.putExtra("android.intent.extra.EMAIL", strArr);
        intent.putExtra("android.intent.extra.SUBJECT", str);
        intent.putExtra("android.intent.extra.STREAM", uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            try {
                context.startActivity(intent);
                return;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                ToastUtils.showShort(NO_EMAIL_APP, 0);
                return;
            }
        }
        ToastUtils.showShort(NO_EMAIL_APP, 0);
    }

    public static void composeEmail(Context context, String[] strArr, String str, String str2, String str3) {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setData(Uri.parse(MailTo.MAILTO_SCHEME));
        intent.putExtra("android.intent.extra.EMAIL", strArr);
        intent.putExtra("android.intent.extra.SUBJECT", str);
        if (str3 != null) {
            intent.putExtra("android.intent.extra.TEXT", str3);
        }
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            ToastUtils.showShort(NO_EMAIL_APP, 0);
            return;
        }
        if (str2 == null) {
            str2 = "";
        }
        try {
            context.startActivity(Intent.createChooser(intent, str2));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            ToastUtils.showShort(NO_EMAIL_APP, 0);
        }
    }

    public static void openWebPage(Context context, String str, String str2) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            if (str2 == null) {
                str2 = "";
            }
            try {
                context.startActivity(Intent.createChooser(intent, str2));
                return;
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
                ToastUtils.showShort(NO_BROWSER_APP);
                return;
            }
        }
        ToastUtils.showShort(NO_BROWSER_APP);
    }

    public static void shareApp(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", "Hey check out my app at: https://play.google.com/store/apps/details?id=" + context.getPackageName());
        intent.setType("text/plain");
        if (str == null) {
            str = "";
        }
        try {
            context.startActivity(Intent.createChooser(intent, str));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
