package com.picspool.lib.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes3.dex */
public class DMBitmapIO {
    public static InputStream getSDFileInputStream(Context context, String str) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            String path = Environment.getExternalStorageDirectory().getPath();
            try {
                return new FileInputStream(new File(path + "//" + str));
            } catch (Throwable unused) {
                return null;
            }
        }
        return null;
    }

    public static Bitmap getImageFromSDFile(Context context, String str) {
        Bitmap bitmap = null;
        try {
            InputStream sDFileInputStream = getSDFileInputStream(context, str);
            if (sDFileInputStream != null) {
                bitmap = BitmapFactory.decodeStream(sDFileInputStream);
                sDFileInputStream.close();
                return bitmap;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap getImageFromSDFile(Context context, String str, BitmapFactory.Options options) {
        Bitmap bitmap = null;
        try {
            InputStream sDFileInputStream = getSDFileInputStream(context, str);
            if (sDFileInputStream != null) {
                bitmap = BitmapFactory.decodeStream(sDFileInputStream);
                sDFileInputStream.close();
                return bitmap;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }
}
