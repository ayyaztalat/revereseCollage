package com.blankj.utilcode.util;

import android.app.ActivityManager;
import android.os.Environment;
import java.io.File;

/* loaded from: classes.dex */
public final class CleanUtils {
    private CleanUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean cleanInternalCache() {
        return UtilsBridge.deleteAllInDir(C0879Utils.getApp().getCacheDir());
    }

    public static boolean cleanInternalFiles() {
        return UtilsBridge.deleteAllInDir(C0879Utils.getApp().getFilesDir());
    }

    public static boolean cleanInternalDbs() {
        return UtilsBridge.deleteAllInDir(new File(C0879Utils.getApp().getFilesDir().getParent(), "databases"));
    }

    public static boolean cleanInternalDbByName(String str) {
        return C0879Utils.getApp().deleteDatabase(str);
    }

    public static boolean cleanInternalSp() {
        return UtilsBridge.deleteAllInDir(new File(C0879Utils.getApp().getFilesDir().getParent(), "shared_prefs"));
    }

    public static boolean cleanExternalCache() {
        return "mounted".equals(Environment.getExternalStorageState()) && UtilsBridge.deleteAllInDir(C0879Utils.getApp().getExternalCacheDir());
    }

    public static boolean cleanCustomDir(String str) {
        return UtilsBridge.deleteAllInDir(UtilsBridge.getFileByPath(str));
    }

    public static void cleanAppUserData() {
        ((ActivityManager) C0879Utils.getApp().getSystemService("activity")).clearApplicationUserData();
    }
}
