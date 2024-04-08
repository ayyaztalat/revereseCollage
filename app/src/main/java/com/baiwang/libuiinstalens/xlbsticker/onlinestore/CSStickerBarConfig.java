package com.baiwang.libuiinstalens.xlbsticker.onlinestore;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.baiwang.libuiinstalens.xlbsticker.onlinestore.resource.CSWBMaterialFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/* loaded from: classes.dex */
public class CSStickerBarConfig {
    public static final String GROUP = "group_colorstyle";
    private static Class PhotoSelectorActivity = null;
    public static final String STICKER_AD_MANAGER = "ad_lock_manager";
    public static final String STICKER_COLORSTYLE = "sticker_colorstyle";
    public static final String STICKER_NAME_MANAGER = "group_names";
    private static Class ShareActivity = null;
    private static Class StickerOnlineStoreAcitvity = null;
    public static boolean isShowAD = false;
    static final String key_save_pix = "key_save_pix";
    static final String prefsName = "Config";
    private static Bitmap swapBitmap;

    public static String getMaterialUrlBase(String str, String str2) {
        return "http://m1.picsrun.com/V2/Instalens/getGroupStickers";
    }

    private static String getVersion() {
        return "";
    }

    public static Class getStickerOnlineStoreAcitvity() {
        return StickerOnlineStoreAcitvity;
    }

    public static void setStickerOnlineStoreAcitvity(Class cls) {
        StickerOnlineStoreAcitvity = cls;
    }

    public static int getAndroidSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    public static String getMaterialUrlBase() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("http://baiwang.hcimc.com/InstaShape/index.php?m=Material&a=materialOfName");
        arrayList.add("http://baiwang1.hcimc.com/InstaShape/index.php?m=Material&a=materialOfName");
        arrayList.add("http://baiwang2.hcimc.com/InstaShape/index.php?m=Material&a=materialOfName");
        arrayList.add("http://baiwang3.hcimc.com/InstaShape/index.php?m=Material&a=materialOfName");
        arrayList.add("http://baiwang4.hcimc.com/InstaShape/index.php?m=Material&a=materialOfName");
        int nextInt = new Random().nextInt(arrayList.size());
        if (nextInt >= arrayList.size()) {
            nextInt = 0;
        }
        return (String) arrayList.get(nextInt);
    }

    public static boolean checkNetwork(Context context) {
        ConnectivityManager connectivityManager;
        if (context == null || (connectivityManager = (ConnectivityManager) context.getSystemService("connectivity")) == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        return (networkInfo2 != null ? networkInfo2.isConnectedOrConnecting() : false) | (networkInfo != null ? networkInfo.isConnectedOrConnecting() : false);
    }

    public static void deleteOnlineStickerRes(String str, Context context) {
        String str2;
        String packageName = context.getApplicationContext().getPackageName();
        if (CSWBMaterialFactory.isSDAvailable()) {
            str2 = context.getExternalFilesDir(null).getPath() + "/" + packageName + CSWBMaterialFactory.SDRootDirName + "/.material/sticker/" + str + "/" + str;
        } else {
            File filesDir = context.getFilesDir();
            str2 = filesDir.getAbsolutePath() + "/" + packageName + CSWBMaterialFactory.SDRootDirName + "/.material/sticker/" + str + "/" + str;
        }
        delFolder(str2);
    }

    private static void delFolder(String str) {
        if (str == null) {
            return;
        }
        try {
            delAllFile(str);
            new File(str.toString()).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean delAllFile(String str) {
        File file;
        File file2 = new File(str);
        if (file2.exists() && file2.isDirectory()) {
            String[] list = file2.list();
            boolean z = false;
            for (int i = 0; i < list.length; i++) {
                if (str.endsWith(File.separator)) {
                    file = new File(str + list[i]);
                } else {
                    file = new File(str + File.separator + list[i]);
                }
                if (file.isFile()) {
                    file.delete();
                }
                if (file.isDirectory()) {
                    delAllFile(str + "/" + list[i]);
                    delFolder(str + "/" + list[i]);
                    z = true;
                }
            }
            return z;
        }
        return false;
    }
}
