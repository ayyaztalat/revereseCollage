package com.photo.editor.square.splash.rate;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.sky.testproject.R;

import java.util.Random;

/* loaded from: classes2.dex */
public class BpFeedback {
    private String appName;
    private Context context;
    private String emailAddress = "mailto:tianyanjun0616@gmail.com";

    public BpFeedback(Activity activity) {
        this.context = activity;
        this.appName = activity.getResources().getString(R.string.app_name);
    }

    public void startFeedback() {
        emailUs();
    }

    public void setAppName(String str) {
        this.appName = str;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setEmailAddress(String str) {
        this.emailAddress = str;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    private void emailUs() {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setData(Uri.parse(this.emailAddress));
        intent.putExtra("android.intent.extra.SUBJECT", "for " + this.appName);
        intent.putExtra("android.intent.extra.TEXT", "App Name: " + this.appName + " android\nApp Version:" + getVersion(this.context) + "\nSystem Version:" + Build.VERSION.RELEASE + "\nPhone:" + Build.MODEL + "\n\nYour Question:\n");
        try {
            this.context.startActivity(Intent.createChooser(intent, "share with"));
            if (showRateToast()) {
                Toast.makeText(this.context, "Thank you for your rate, Best wishes to you.", Toast.LENGTH_SHORT).show();
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Context context = this.context;
            if (context != null) {
                Toast.makeText(context, "plz install any Email app!", 0).show();
            }
        }
    }

    public static boolean showRateToast() {
        try {
            return new Random().nextInt(100) < Integer.parseInt(FirebaseRemoteConfig.getInstance().getString("show_rate_toast"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
