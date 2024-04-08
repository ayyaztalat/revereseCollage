package com.magic.video.editor.effect.cut.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/* loaded from: classes2.dex */
public class CSOtherApp {
    public static Intent playStoreIntent(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        Intent data = intent.setData(Uri.parse("market://details?id=" + str));
        data.setPackage(CSOtherAppPackages2.googleplayPackage);
        return data;
    }

    public static Intent marketIntent(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        return intent.setData(Uri.parse("market://details?id=" + str));
    }

    public static Intent browserIntent(String str) {
        return new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + str));
    }

    public static Intent appIntent(String str, String str2) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268435456);
        intent.setComponent(new ComponentName(str, str2));
        return intent;
    }

    public static Boolean isInstalled(Context context, String str) {
        new CSOtherApp();
        if (str == null) {
            return false;
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (Exception unused) {
        }
        return Boolean.valueOf(packageInfo != null);
    }

    public static void openIntentOrInMarket(Context context, String str, String str2) {
        if (isInstalled(context, str).booleanValue()) {
            context.startActivity(appIntent(str, str2));
            return;
        }
        try {
            try {
                context.startActivity(marketIntent(str));
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            context.startActivity(browserIntent(str));
        }
    }

    public static void openInMarket(Context context, String str) {
        try {
            try {
                if (isInstalled(context, CSOtherAppPackages2.googleplayPackage).booleanValue()) {
                    context.startActivity(playStoreIntent(str));
                } else {
                    context.startActivity(marketIntent(str));
                }
            } catch (Throwable unused) {
            }
        } catch (Exception unused2) {
            context.startActivity(browserIntent(str));
        }
    }
}
