package com.picspool.snappic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.picspool.snappic.utils.CSAppExecutor;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;

/* loaded from: classes.dex */
public class CSBmpIoCaChe {
    private static LinkedHashMap<String, Bitmap> cacheTmp = new LinkedHashMap<>();

    /* loaded from: classes.dex */
    public interface BmpCallback {
        void onResult(Bitmap bitmap);
    }

    public static String cachDir(Context context) {
        File file = new File(context.getExternalCacheDir().getAbsolutePath() + "/Pics Cut/.temp/");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    public static void putJPG(final Context context, final String str, final Bitmap bitmap) {
        cacheTmp.put(str, bitmap);
        CSAppExecutor.getExecutor().execute(new Runnable() { // from class: com.picspool.snappic.utils.CSBmpIoCaChe.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    synchronized (bitmap) {
                        FileOutputStream fileOutputStream = new FileOutputStream(CSBmpIoCaChe.cachDir(context) + "/" + str);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        CSBmpIoCaChe.cacheTmp.remove(str);
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public static void putPNG(final Context context, final String str, final Bitmap bitmap) {
        cacheTmp.put(str, bitmap);
        CSAppExecutor.getExecutor().execute(new Runnable() { // from class: com.picspool.snappic.utils.CSBmpIoCaChe.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    synchronized (bitmap) {
                        FileOutputStream fileOutputStream = new FileOutputStream(CSBmpIoCaChe.cachDir(context) + "/" + str);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                } catch (Exception unused) {
                }
            }
        });
    }

    public static void getBitmap(final Context context, final String str, final BmpCallback bmpCallback) {
        CSAppExecutor.getExecutor().execute(new Runnable() { // from class: com.picspool.snappic.utils.CSBmpIoCaChe.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (CSBmpIoCaChe.cacheTmp.containsKey(str)) {
                        final Bitmap bitmap = (Bitmap) CSBmpIoCaChe.cacheTmp.get(str);
                        if (bitmap == null || bitmap.isRecycled()) {
                            CSBmpIoCaChe.cacheTmp.remove(str);
                        } else {
                            CSAppExecutor.MainThreadExecutor.instance().execute(new Runnable() { // from class: com.picspool.snappic.utils.CSBmpIoCaChe.3.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    bmpCallback.onResult(bitmap);
                                }
                            });
                        }
                    }
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inScaled = false;
                    final Bitmap decodeFile = BitmapFactory.decodeFile(CSBmpIoCaChe.cachDir(context) + "/" + str, options);
                    CSAppExecutor.MainThreadExecutor.instance().execute(new Runnable() { // from class: com.picspool.snappic.utils.CSBmpIoCaChe.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            bmpCallback.onResult(decodeFile);
                        }
                    });
                } catch (Exception unused) {
                    CSAppExecutor.MainThreadExecutor.instance().execute(new Runnable() { // from class: com.picspool.snappic.utils.CSBmpIoCaChe.3.3
                        @Override // java.lang.Runnable
                        public void run() {
                            bmpCallback.onResult(null);
                        }
                    });
                }
            }
        });
    }

    public static void getBitmap(final Context context, final String str, final BitmapFactory.Options options, final BmpCallback bmpCallback) {
        CSAppExecutor.getExecutor().execute(new Runnable() { // from class: com.picspool.snappic.utils.CSBmpIoCaChe.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if (CSBmpIoCaChe.cacheTmp.containsKey(str)) {
                        final Bitmap bitmap = (Bitmap) CSBmpIoCaChe.cacheTmp.get(str);
                        if (bitmap == null || bitmap.isRecycled()) {
                            CSBmpIoCaChe.cacheTmp.remove(str);
                        } else {
                            CSAppExecutor.MainThreadExecutor.instance().execute(new Runnable() { // from class: com.picspool.snappic.utils.CSBmpIoCaChe.4.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    bmpCallback.onResult(bitmap);
                                }
                            });
                        }
                    }
                    final Bitmap decodeFile = BitmapFactory.decodeFile(CSBmpIoCaChe.cachDir(context) + "/" + str, options);
                    CSAppExecutor.MainThreadExecutor.instance().execute(new Runnable() { // from class: com.picspool.snappic.utils.CSBmpIoCaChe.4.2
                        @Override // java.lang.Runnable
                        public void run() {
                            bmpCallback.onResult(decodeFile);
                        }
                    });
                } catch (Exception unused) {
                    CSAppExecutor.MainThreadExecutor.instance().execute(new Runnable() { // from class: com.picspool.snappic.utils.CSBmpIoCaChe.4.3
                        @Override // java.lang.Runnable
                        public void run() {
                            bmpCallback.onResult(null);
                        }
                    });
                }
            }
        });
    }

    public static boolean hasBitmapExists(Context context, String str) {
        if (cacheTmp.containsKey(str)) {
            return true;
        }
        return new File(cachDir(context) + "/" + str).exists();
    }

    public static void remove(Context context, String str) {
        cacheTmp.remove(str);
        File file = new File(cachDir(context) + "/" + str);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void removeall(Context context) {
        cacheTmp.clear();
        delFolder(cachDir(context));
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
