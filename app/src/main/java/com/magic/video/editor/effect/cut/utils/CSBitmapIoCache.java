package com.magic.video.editor.effect.cut.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.magic.video.editor.effect.cut.utils.base.CSUtilsAppHolder;
import java.io.File;
import java.io.FileOutputStream;

/* loaded from: classes2.dex */
public class CSBitmapIoCache {
    public static String cachDir() {
        File file = new File(CSUtilsAppHolder.appContext.getCacheDir().getPath() + "/.tmpb/");
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }

    public static String putJPG(String str, Bitmap bitmap) {
        String str2;
        if (bitmap != null && !bitmap.isRecycled()) {
            try {
                synchronized (bitmap) {
                    str2 = cachDir() + "/" + str;
                    FileOutputStream fileOutputStream = new FileOutputStream(str2);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                return str2;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String putPNG(String str, Bitmap bitmap) {
        String str2;
        try {
            synchronized (bitmap) {
                str2 = cachDir() + "/" + str;
                FileOutputStream fileOutputStream = new FileOutputStream(str2);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
            }
            return str2;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Bitmap getBitmap(String str) {
        String str2 = cachDir() + "/" + str;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        return BitmapFactory.decodeFile(str2, options);
    }

    public static Bitmap getBitmap(String str, BitmapFactory.Options options) {
        return BitmapFactory.decodeFile(cachDir() + "/" + str, options);
    }

    public static boolean hasBitmapExists(String str) {
        return new File(cachDir() + "/" + str).exists();
    }

    public static void remove(String str) {
        File file = new File(cachDir() + "/" + str);
        if (file.exists()) {
            file.delete();
        }
    }
}
