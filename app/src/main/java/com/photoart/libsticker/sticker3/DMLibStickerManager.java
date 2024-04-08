package com.photoart.libsticker.sticker3;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import com.photoart.libsticker.sticker2.DMStickerGroupManager;
import com.photoart.libsticker.sticker3.DMSgOkHttpClient;
import java.io.IOException;
import java.util.Date;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes2.dex */
public class DMLibStickerManager {
    public static String CONFIG = "config";
    public static String LOCK_AD_PKG_NAME = "lock_ad_pkg_name";
    public static String LOCK_ITEM_NAME = "lockitemname";
    public static String STICKER_CONFIG = "stickerconfig";
    static boolean isTest = false;

    public static void setTestStatus(boolean z) {
        isTest = z;
    }

    public static boolean getTestStatus() {
        return isTest;
    }

    public static void initOnLineSticker(final Context context, String str, final DMViewLockOnlineStickerInterface dMViewLockOnlineStickerInterface) {
        if (str == null) {
            return;
        }
        if (!isFitTimeConditon(context)) {
            DMStickerGroupManager.getSingletManager(context, dMViewLockOnlineStickerInterface);
            return;
        }
        recordHaveRequestRec(context);
        DMSgOkHttpClient.getInstance(context).asyncStickersPost(new DMSgOkHttpClient.ResponseListener() { // from class: com.photoart.libsticker.sticker3.DMLibStickerManager.1
            @Override // com.photoart.libsticker.sticker3.DMSgOkHttpClient.ResponseListener
            public void onResponseFail(IOException iOException) {
            }

            @Override // com.photoart.libsticker.sticker3.DMSgOkHttpClient.ResponseListener
            public void onResponseSuccess(String str2) {
                DMPreferencesUtil.save(context, DMLibStickerManager.CONFIG, DMLibStickerManager.STICKER_CONFIG, str2);
                DMStickerGroupManager.getSingletManager(context, dMViewLockOnlineStickerInterface);
            }
        }, str);
        new Handler().postDelayed(new Runnable() { // from class: com.photoart.libsticker.sticker3.DMLibStickerManager.2
            @Override // java.lang.Runnable
            public void run() {
                DMStickerGroupManager.getSingletManager(context, dMViewLockOnlineStickerInterface);
            }
        }, 5001L);
    }

    private static boolean isWifi(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == 1;
    }

    private static boolean isFitTimeConditon(Context context) {
        String str = DMPreferencesUtil.get(context, "rec_apps_onsticker", "last_time_dy");
        if (str != null) {
            return new Date().getTime() - Long.parseLong(str) >= ((long) 10800000);
        }
        recordHaveRequestRec(context);
        return true;
    }

    private static void recordHaveRequestRec(Context context) {
        DMPreferencesUtil.save(context, "rec_apps_onsticker", "last_time_dy", String.valueOf(new Date().getTime()));
    }
}
