package com.photo.editor.square.splash.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import com.photo.editor.square.splash.app.CSMyApp;
import com.sky.testproject.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


/* loaded from: classes2.dex */
public class CSBitmapUtils {
    public static Bitmap scaleBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        int width = bitmap.getWidth();
        int i3 = (width * i2) / i;
        if (i3 > bitmap.getHeight()) {
            i3 = bitmap.getHeight();
            width = (i3 * i) / i2;
        }
        int width2 = (bitmap.getWidth() - width) / 2;
        int height = (bitmap.getHeight() - i3) / 2;
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawBitmap(bitmap, new Rect(width2, height, width + width2, i3 + height), new Rect(0, 0, i, i2), (Paint) null);
        return createBitmap;
    }

    public static String saveImageToGallery(Context context, Bitmap bitmap) {
        String string = context.getString(R.string.app_name);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + string);
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(file, string + System.currentTimeMillis() + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            boolean compress = bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
            return compress ? file2.getAbsolutePath() : "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Bitmap loadBitmapFromView(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int width = view.getWidth();
        int height = view.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(-1);
        view.layout(0, 0, width, height);
        view.draw(canvas);
        view.setLayoutParams(layoutParams);
        return createBitmap;
    }

    public static Bitmap getImageFromResourceFile(Resources resources, int i) {
        Bitmap bitmap = null;
        try {
            InputStream openRawResource = resources.openRawResource(i);
            bitmap = BitmapFactory.decodeStream(openRawResource);
            openRawResource.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap getImageFromAssetsFile(Resources resources, String str) {
        Bitmap bitmap = null;
        try {
            InputStream open = resources.getAssets().open(str);
            bitmap = BitmapFactory.decodeStream(open);
            open.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap getImageFromAssetsFile(Resources resources, String str, BitmapFactory.Options options) {
        Bitmap bitmap = null;
        try {
            InputStream open = resources.getAssets().open(str);
            bitmap = BitmapFactory.decodeStream(open, null, options);
            open.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap getImageFromAssetsFile(Resources resources, String str, int i) {
        Bitmap bitmap = null;
        try {
            InputStream open = resources.getAssets().open(str);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = i;
            options.inPurgeable = true;
            options.inInputShareable = true;
            bitmap = BitmapFactory.decodeStream(open, null, options);
            open.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static Bitmap getImageFromSDFile(Context context, String str, int i) {
        Bitmap bitmap = null;
        try {
            InputStream sDFileInputStream = getSDFileInputStream(context, str);
            if (sDFileInputStream != null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = i;
                options.inPurgeable = true;
                options.inInputShareable = true;
                bitmap = BitmapFactory.decodeStream(sDFileInputStream, null, options);
                sDFileInputStream.close();
                return bitmap;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
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

    public static InputStream getSDFileInputStream(Context context, String str) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            String path = CSMyApp.getContext().getExternalFilesDir(null).getPath();
            if (str.contains(path)) {
                str = str.replace(path, "");
            }
            try {
                return new FileInputStream(new File(path + "//" + str));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Bitmap getImageFromFile(Context context, String str) {
        Bitmap bitmap = null;
        try {
            InputStream fileInputStream = getFileInputStream(context, str);
            if (fileInputStream != null) {
                bitmap = BitmapFactory.decodeStream(fileInputStream);
                fileInputStream.close();
                return bitmap;
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static InputStream getFileInputStream(Context context, String str) {
        try {
            return new FileInputStream(new File(str));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void delPNG(String str) {
        try {
            File file = new File(cachDir() + "/" + str);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception unused) {
        }
    }

    public static String putPNG(String str, Bitmap bitmap) {
        try {
            String str2 = cachDir() + "/" + str;
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return str2;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Bitmap getBitmap(String str) {
        String str2 = cachDir() + "/" + str;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        try {
            return BitmapFactory.decodeFile(str2, options);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String cachDir() {
        File file = new File(CSMyApp.getContext().getExternalFilesDir(null).getPath() + "/.tmpb/");
        if (!file.exists()) {
            file.mkdir();
        }
        return file.getAbsolutePath();
    }
}
