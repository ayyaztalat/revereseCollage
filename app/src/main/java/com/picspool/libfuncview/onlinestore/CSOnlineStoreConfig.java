package com.picspool.libfuncview.onlinestore;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import com.picspool.lib.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes.dex */
public class CSOnlineStoreConfig {
    public static final String GROUP = "group_colorstyle";
    private static Class PIPActivity = null;
    private static Class PhotoSelectorActivity = null;
    public static final String SETTING_ORDER_MANAGER = "setting_res_order_manager";
    public static final String STICKER_AD_MANAGER = "ad_lock_manager";
    public static final String STICKER_COLORSTYLE = "sticker_colorstyle";
    public static final String STICKER_NAME_MANAGER = "group_names";
    private static Class ShareActivity = null;
    private static Class StickerOnlineStoreAcitvity = null;
    public static boolean isShowAD = false;
    static final String key_save_pix = "key_save_pix";
    static final String prefsName = "Config";
    private static Bitmap swapBitmap;

    public static String getAppid() {
        return "com.baiwang.stylephotomirror.activity";
    }

    public static int getImageQuality() {
        return 960;
    }

    public static int getImageQuality(String str) {
        return 960;
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

    public static Class getPhotoSelectorActivity() {
        return PhotoSelectorActivity;
    }

    public static void setPhotoSelectorActivity(Class cls) {
        PhotoSelectorActivity = cls;
    }

    public static Class getShareActivity() {
        return ShareActivity;
    }

    public static void setShareActivity(Class cls) {
        ShareActivity = cls;
    }

    public void setSavePix(Context context, int i) {
        DMPreferencesUtil.save(context, prefsName, key_save_pix, String.valueOf(i));
    }

    public int getSavePix(Context context) {
        String str = DMPreferencesUtil.get(context, prefsName, key_save_pix);
        if (str != null) {
            try {
                return Integer.parseInt(str);
            } catch (Exception unused) {
            }
        }
        return 612;
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

    public static String getMaterialUrlBase(String str, String str2) {
        String str3;
        String str4 = "http://s1.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str5 = "http://s2.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str6 = "http://s3.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str7 = "http://s4.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        String str8 = "http://s5.hcimc.com/aurona_material_src/public/material_all?app_name=" + str + "&&fun_name=" + str2 + "&&language=";
        ArrayList arrayList = new ArrayList();
        arrayList.add(str4);
        arrayList.add(str5);
        arrayList.add(str6);
        arrayList.add(str7);
        arrayList.add(str8);
        int nextInt = new Random().nextInt(arrayList.size());
        if (nextInt >= arrayList.size()) {
            nextInt = 0;
        }
        String str9 = (String) arrayList.get(nextInt);
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String lowerCase = locale.getCountry().toLowerCase();
        if ("zh".equals(language)) {
            if ("cn".equals(lowerCase)) {
                str9 = str9 + "1";
            } else if ("tw".equals(lowerCase)) {
                str9 = str9 + ExifInterface.GPS_MEASUREMENT_2D;
            }
        } else {
            str9 = str9 + "0";
        }
        String str10 = str9 + "&&statue=1";
        if (lowerCase.equals("cn")) {
            str3 = str10 + "&&country_code=1";
        } else if (lowerCase.equals("hk") || lowerCase.equals("mo") || lowerCase.equals("tw") || lowerCase.equals("th") || lowerCase.equals("my") || lowerCase.equals("sg") || lowerCase.equals("id") || lowerCase.equals("ph") || lowerCase.equals("jp") || lowerCase.equals("kp") || lowerCase.equals("in")) {
            str3 = str10 + "&&country_code=2";
        } else {
            str3 = str10 + "&&country_code=0";
        }
        String str11 = ((((str3 + "&&country_name=" + lowerCase) + "&&language_name=" + language) + "&&version_name=" + getVersion()) + "&&plat_type=android") + "&&phone_model=" + Build.MODEL.replaceAll(" ", "");
        if (Build.VERSION.SDK_INT >= 9) {
            str11 = str11 + "&&phone_sdk_version=" + Build.SERIAL;
        }
        return str11 + "&&phone_sys_version=" + Build.VERSION.RELEASE;
    }

    public static boolean checkNetwork(Context context) {
        ConnectivityManager connectivityManager;
        if (context == null || (connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)) == null) {
            return false;
        }
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
        NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
        return (networkInfo2 != null ? networkInfo2.isConnectedOrConnecting() : false) | (networkInfo != null ? networkInfo.isConnectedOrConnecting() : false);
    }

    public static Bitmap getSwapBitmap() {
        return swapBitmap;
    }

    public static void setSwapBitmap(Bitmap bitmap) {
        swapBitmap = bitmap;
    }

    public static String getImageAbsolutePath(Activity activity, Uri uri) {
        Uri uri2 = null;
        if (activity != null && uri != null) {
            if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(activity, uri)) {
                if (isExternalStorageDocument(uri)) {
                    String[] split = DocumentsContract.getDocumentId(uri).split(":");
                    if ("primary".equalsIgnoreCase(split[0])) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                } else if (isDownloadsDocument(uri)) {
                    return getDataColumn(activity, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
                } else {
                    if (isMediaDocument(uri)) {
                        String[] split2 = DocumentsContract.getDocumentId(uri).split(":");
                        String str = split2[0];
                        if (ImageViewTouchBase.LOG_TAG.equals(str)) {
                            uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(str)) {
                            uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(str)) {
                            uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        return getDataColumn(activity, uri2, "_id=?", new String[]{split2[1]});
                    }
                }
            } else if (FirebaseAnalytics.Param.CONTENT.equalsIgnoreCase(uri.getScheme())) {
                if (isGooglePhotosUri(uri)) {
                    return uri.getLastPathSegment();
                }
                return getDataColumn(activity, uri, null, null);
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String str, String[] strArr) {
        Cursor cursor = null;
        try {
            Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, str, strArr, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        String string = query.getString(query.getColumnIndexOrThrow("_data"));
                        if (query != null) {
                            query.close();
                        }
                        return string;
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        return str;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
}
