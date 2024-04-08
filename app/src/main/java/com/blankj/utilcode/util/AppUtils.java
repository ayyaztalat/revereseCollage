package com.blankj.utilcode.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.util.Log;
import com.blankj.utilcode.util.C0879Utils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class AppUtils {
    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void registerAppStatusChangedListener(C0879Utils.OnAppStatusChangedListener onAppStatusChangedListener) {
        if (onAppStatusChangedListener == null) {
            throw new NullPointerException("Argument 'listener' of type Utils.OnAppStatusChangedListener (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
        UtilsBridge.addOnAppStatusChangedListener(onAppStatusChangedListener);
    }

    public static void unregisterAppStatusChangedListener(C0879Utils.OnAppStatusChangedListener onAppStatusChangedListener) {
        if (onAppStatusChangedListener == null) {
            throw new NullPointerException("Argument 'listener' of type Utils.OnAppStatusChangedListener (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
        }
        UtilsBridge.removeOnAppStatusChangedListener(onAppStatusChangedListener);
    }

    public static void installApp(String str) {
        installApp(UtilsBridge.getFileByPath(str));
    }

    public static void installApp(File file) {
        Intent installAppIntent = UtilsBridge.getInstallAppIntent(file);
        if (installAppIntent == null) {
            return;
        }
        C0879Utils.getApp().startActivity(installAppIntent);
    }

    public static void installApp(Uri uri) {
        Intent installAppIntent = UtilsBridge.getInstallAppIntent(uri);
        if (installAppIntent == null) {
            return;
        }
        C0879Utils.getApp().startActivity(installAppIntent);
    }

    public static void uninstallApp(String str) {
        if (UtilsBridge.isSpace(str)) {
            return;
        }
        C0879Utils.getApp().startActivity(UtilsBridge.getUninstallAppIntent(str));
    }

    public static boolean isAppInstalled(String str) {
        if (UtilsBridge.isSpace(str)) {
            return false;
        }
        try {
            return C0879Utils.getApp().getPackageManager().getApplicationInfo(str, 0).enabled;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isAppRoot() {
        return UtilsBridge.execCmd("echo root", true).result == 0;
    }

    public static boolean isAppDebug() {
        return isAppDebug(C0879Utils.getApp().getPackageName());
    }

    public static boolean isAppDebug(String str) {
        if (UtilsBridge.isSpace(str)) {
            return false;
        }
        try {
            return (C0879Utils.getApp().getPackageManager().getApplicationInfo(str, 0).flags & 2) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isAppSystem() {
        return isAppSystem(C0879Utils.getApp().getPackageName());
    }

    public static boolean isAppSystem(String str) {
        if (UtilsBridge.isSpace(str)) {
            return false;
        }
        try {
            return (C0879Utils.getApp().getPackageManager().getApplicationInfo(str, 0).flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isAppForeground() {
        return UtilsBridge.isAppForeground();
    }

    public static boolean isAppForeground(String str) {
        if (str != null) {
            return !UtilsBridge.isSpace(str) && str.equals(UtilsBridge.getForegroundProcessName());
        }
        throw new NullPointerException("Argument 'pkgName' of type String (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    public static boolean isAppRunning(String str) {
        ActivityManager activityManager;
        if (!UtilsBridge.isSpace(str) && (activityManager = (ActivityManager) C0879Utils.getApp().getSystemService("activity")) != null) {
            List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
            if (runningTasks != null && runningTasks.size() > 0) {
                for (ActivityManager.RunningTaskInfo runningTaskInfo : runningTasks) {
                    if (runningTaskInfo.baseActivity != null && str.equals(runningTaskInfo.baseActivity.getPackageName())) {
                        return true;
                    }
                }
            }
            List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(Integer.MAX_VALUE);
            if (runningServices != null && runningServices.size() > 0) {
                for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
                    if (str.equals(runningServiceInfo.service.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void launchApp(String str) {
        if (UtilsBridge.isSpace(str)) {
            return;
        }
        Intent launchAppIntent = UtilsBridge.getLaunchAppIntent(str);
        if (launchAppIntent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
        } else {
            C0879Utils.getApp().startActivity(launchAppIntent);
        }
    }

    public static void relaunchApp() {
        relaunchApp(false);
    }

    public static void relaunchApp(boolean z) {
        Intent launchAppIntent = UtilsBridge.getLaunchAppIntent(C0879Utils.getApp().getPackageName());
        if (launchAppIntent == null) {
            Log.e("AppUtils", "Didn't exist launcher activity.");
            return;
        }
        launchAppIntent.addFlags(335577088);
        C0879Utils.getApp().startActivity(launchAppIntent);
        if (z) {
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }

    public static void launchAppDetailsSettings() {
        launchAppDetailsSettings(C0879Utils.getApp().getPackageName());
    }

    public static void launchAppDetailsSettings(String str) {
        if (UtilsBridge.isSpace(str)) {
            return;
        }
        Intent launchAppDetailsSettingsIntent = UtilsBridge.getLaunchAppDetailsSettingsIntent(str, true);
        if (UtilsBridge.isIntentAvailable(launchAppDetailsSettingsIntent)) {
            C0879Utils.getApp().startActivity(launchAppDetailsSettingsIntent);
        }
    }

    public static void launchAppDetailsSettings(Activity activity, int i) {
        launchAppDetailsSettings(activity, i, C0879Utils.getApp().getPackageName());
    }

    public static void launchAppDetailsSettings(Activity activity, int i, String str) {
        if (activity == null || UtilsBridge.isSpace(str)) {
            return;
        }
        Intent launchAppDetailsSettingsIntent = UtilsBridge.getLaunchAppDetailsSettingsIntent(str, false);
        if (UtilsBridge.isIntentAvailable(launchAppDetailsSettingsIntent)) {
            activity.startActivityForResult(launchAppDetailsSettingsIntent, i);
        }
    }

    public static void exitApp() {
        UtilsBridge.finishAllActivities();
        System.exit(0);
    }

    public static Drawable getAppIcon() {
        return getAppIcon(C0879Utils.getApp().getPackageName());
    }

    public static Drawable getAppIcon(String str) {
        if (UtilsBridge.isSpace(str)) {
            return null;
        }
        try {
            PackageManager packageManager = C0879Utils.getApp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            if (packageInfo == null) {
                return null;
            }
            return packageInfo.applicationInfo.loadIcon(packageManager);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getAppIconId() {
        return getAppIconId(C0879Utils.getApp().getPackageName());
    }

    public static int getAppIconId(String str) {
        if (UtilsBridge.isSpace(str)) {
            return 0;
        }
        try {
            PackageInfo packageInfo = C0879Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return 0;
            }
            return packageInfo.applicationInfo.icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getAppPackageName() {
        String packageName = C0879Utils.getApp().getPackageName();
        if (packageName != null) {
            return packageName;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppPackageName() marked by @androidx.annotation.NonNull");
    }

    public static String getAppName() {
        String appName = getAppName(C0879Utils.getApp().getPackageName());
        if (appName != null) {
            return appName;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppName() marked by @androidx.annotation.NonNull");
    }

    public static String getAppName(String str) {
        if (UtilsBridge.isSpace(str)) {
            return "";
        }
        try {
            PackageManager packageManager = C0879Utils.getApp().getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(str, 0);
            String charSequence = packageInfo == null ? "" : packageInfo.applicationInfo.loadLabel(packageManager).toString();
            if (charSequence != null) {
                return charSequence;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppName() marked by @androidx.annotation.NonNull");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getAppPath() {
        String appPath = getAppPath(C0879Utils.getApp().getPackageName());
        if (appPath != null) {
            return appPath;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppPath() marked by @androidx.annotation.NonNull");
    }

    public static String getAppPath(String str) {
        if (UtilsBridge.isSpace(str)) {
            return "";
        }
        try {
            PackageInfo packageInfo = C0879Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            String str2 = packageInfo == null ? "" : packageInfo.applicationInfo.sourceDir;
            if (str2 != null) {
                return str2;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppPath() marked by @androidx.annotation.NonNull");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getAppVersionName() {
        String appVersionName = getAppVersionName(C0879Utils.getApp().getPackageName());
        if (appVersionName != null) {
            return appVersionName;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppVersionName() marked by @androidx.annotation.NonNull");
    }

    public static String getAppVersionName(String str) {
        if (UtilsBridge.isSpace(str)) {
            return "";
        }
        try {
            PackageInfo packageInfo = C0879Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            String str2 = packageInfo == null ? "" : packageInfo.versionName;
            if (str2 != null) {
                return str2;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppVersionName() marked by @androidx.annotation.NonNull");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getAppVersionCode() {
        return getAppVersionCode(C0879Utils.getApp().getPackageName());
    }

    public static int getAppVersionCode(String str) {
        if (UtilsBridge.isSpace(str)) {
            return -1;
        }
        try {
            PackageInfo packageInfo = C0879Utils.getApp().getPackageManager().getPackageInfo(str, 0);
            if (packageInfo == null) {
                return -1;
            }
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Signature[] getAppSignatures() {
        return getAppSignatures(C0879Utils.getApp().getPackageName());
    }

    public static Signature[] getAppSignatures(String str) {
        if (UtilsBridge.isSpace(str)) {
            return null;
        }
        try {
            PackageManager packageManager = C0879Utils.getApp().getPackageManager();
            if (Build.VERSION.SDK_INT >= 28) {
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 134217728);
                if (packageInfo == null) {
                    return null;
                }
                SigningInfo signingInfo = packageInfo.signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    return signingInfo.getApkContentsSigners();
                }
                return signingInfo.getSigningCertificateHistory();
            }
            PackageInfo packageInfo2 = packageManager.getPackageInfo(str, 64);
            if (packageInfo2 == null) {
                return null;
            }
            return packageInfo2.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Signature[] getAppSignatures(File file) {
        if (file == null) {
            return null;
        }
        PackageManager packageManager = C0879Utils.getApp().getPackageManager();
        if (Build.VERSION.SDK_INT >= 28) {
            PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(file.getAbsolutePath(), 134217728);
            if (packageArchiveInfo == null) {
                return null;
            }
            SigningInfo signingInfo = packageArchiveInfo.signingInfo;
            if (signingInfo.hasMultipleSigners()) {
                return signingInfo.getApkContentsSigners();
            }
            return signingInfo.getSigningCertificateHistory();
        }
        PackageInfo packageArchiveInfo2 = packageManager.getPackageArchiveInfo(file.getAbsolutePath(), 64);
        if (packageArchiveInfo2 == null) {
            return null;
        }
        return packageArchiveInfo2.signatures;
    }

    public static List<String> getAppSignaturesSHA1() {
        List<String> appSignaturesSHA1 = getAppSignaturesSHA1(C0879Utils.getApp().getPackageName());
        if (appSignaturesSHA1 != null) {
            return appSignaturesSHA1;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesSHA1() marked by @androidx.annotation.NonNull");
    }

    public static List<String> getAppSignaturesSHA1(String str) {
        List<String> appSignaturesHash = getAppSignaturesHash(str, "SHA1");
        if (appSignaturesHash != null) {
            return appSignaturesHash;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesSHA1() marked by @androidx.annotation.NonNull");
    }

    public static List<String> getAppSignaturesSHA256() {
        List<String> appSignaturesSHA256 = getAppSignaturesSHA256(C0879Utils.getApp().getPackageName());
        if (appSignaturesSHA256 != null) {
            return appSignaturesSHA256;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesSHA256() marked by @androidx.annotation.NonNull");
    }

    public static List<String> getAppSignaturesSHA256(String str) {
        List<String> appSignaturesHash = getAppSignaturesHash(str, "SHA256");
        if (appSignaturesHash != null) {
            return appSignaturesHash;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesSHA256() marked by @androidx.annotation.NonNull");
    }

    public static List<String> getAppSignaturesMD5() {
        List<String> appSignaturesMD5 = getAppSignaturesMD5(C0879Utils.getApp().getPackageName());
        if (appSignaturesMD5 != null) {
            return appSignaturesMD5;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesMD5() marked by @androidx.annotation.NonNull");
    }

    public static List<String> getAppSignaturesMD5(String str) {
        List<String> appSignaturesHash = getAppSignaturesHash(str, "MD5");
        if (appSignaturesHash != null) {
            return appSignaturesHash;
        }
        throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.getAppSignaturesMD5() marked by @androidx.annotation.NonNull");
    }

    public static int getAppUid() {
        return getAppUid(C0879Utils.getApp().getPackageName());
    }

    public static int getAppUid(String str) {
        try {
            return C0879Utils.getApp().getPackageManager().getApplicationInfo(str, 0).uid;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static List<String> getAppSignaturesHash(String str, String str2) {
        Signature[] appSignatures;
        ArrayList arrayList = new ArrayList();
        if (!UtilsBridge.isSpace(str) && (appSignatures = getAppSignatures(str)) != null && appSignatures.length > 0) {
            for (Signature signature : appSignatures) {
                arrayList.add(UtilsBridge.bytes2HexString(UtilsBridge.hashTemplate(signature.toByteArray(), str2)).replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0"));
            }
        }
        return arrayList;
    }

    public static AppInfo getAppInfo() {
        return getAppInfo(C0879Utils.getApp().getPackageName());
    }

    public static AppInfo getAppInfo(String str) {
        try {
            PackageManager packageManager = C0879Utils.getApp().getPackageManager();
            if (packageManager == null) {
                return null;
            }
            return getBean(packageManager, packageManager.getPackageInfo(str, 0));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<AppInfo> getAppsInfo() {
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = C0879Utils.getApp().getPackageManager();
        if (packageManager == null) {
            return arrayList;
        }
        for (PackageInfo packageInfo : packageManager.getInstalledPackages(0)) {
            AppInfo bean = getBean(packageManager, packageInfo);
            if (bean != null) {
                arrayList.add(bean);
            }
        }
        return arrayList;
    }

    public static AppInfo getApkInfo(File file) {
        if (file != null && file.isFile() && file.exists()) {
            return getApkInfo(file.getAbsolutePath());
        }
        return null;
    }

    public static AppInfo getApkInfo(String str) {
        PackageManager packageManager;
        PackageInfo packageArchiveInfo;
        if (UtilsBridge.isSpace(str) || (packageManager = C0879Utils.getApp().getPackageManager()) == null || (packageArchiveInfo = packageManager.getPackageArchiveInfo(str, 0)) == null) {
            return null;
        }
        ApplicationInfo applicationInfo = packageArchiveInfo.applicationInfo;
        applicationInfo.sourceDir = str;
        applicationInfo.publicSourceDir = str;
        return getBean(packageManager, packageArchiveInfo);
    }

    private static AppInfo getBean(PackageManager packageManager, PackageInfo packageInfo) {
        if (packageInfo == null) {
            return null;
        }
        String str = packageInfo.versionName;
        int i = packageInfo.versionCode;
        String str2 = packageInfo.packageName;
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        if (applicationInfo == null) {
            return new AppInfo(str2, "", null, "", str, i, false);
        }
        return new AppInfo(str2, applicationInfo.loadLabel(packageManager).toString(), applicationInfo.loadIcon(packageManager), applicationInfo.sourceDir, str, i, (applicationInfo.flags & 1) != 0);
    }

    /* loaded from: classes.dex */
    public static class AppInfo {
        private Drawable icon;
        private boolean isSystem;
        private String name;
        private String packageName;
        private String packagePath;
        private int versionCode;
        private String versionName;

        public Drawable getIcon() {
            return this.icon;
        }

        public void setIcon(Drawable drawable) {
            this.icon = drawable;
        }

        public boolean isSystem() {
            return this.isSystem;
        }

        public void setSystem(boolean z) {
            this.isSystem = z;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public void setPackageName(String str) {
            this.packageName = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }

        public String getPackagePath() {
            return this.packagePath;
        }

        public void setPackagePath(String str) {
            this.packagePath = str;
        }

        public int getVersionCode() {
            return this.versionCode;
        }

        public void setVersionCode(int i) {
            this.versionCode = i;
        }

        public String getVersionName() {
            return this.versionName;
        }

        public void setVersionName(String str) {
            this.versionName = str;
        }

        public AppInfo(String str, String str2, Drawable drawable, String str3, String str4, int i, boolean z) {
            setName(str2);
            setIcon(drawable);
            setPackageName(str);
            setPackagePath(str3);
            setVersionName(str4);
            setVersionCode(i);
            setSystem(z);
        }

        public String toString() {
            String str = "{\n    pkg name: " + getPackageName() + "\n    app icon: " + getIcon() + "\n    app name: " + getName() + "\n    app path: " + getPackagePath() + "\n    app v name: " + getVersionName() + "\n    app v code: " + getVersionCode() + "\n    is system: " + isSystem() + "\n}";
            if (str != null) {
                return str;
            }
            throw new NullPointerException("Detected an attempt to return null from a method com.blankj.utilcode.util.AppUtils.AppInfo.toString() marked by @androidx.annotation.NonNull");
        }
    }
}
