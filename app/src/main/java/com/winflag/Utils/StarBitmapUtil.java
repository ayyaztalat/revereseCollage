package com.winflag.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class StarBitmapUtil {
    public static int degreesToExifOrientation(float f) {
        if (f == 0.0f) {
            return 1;
        }
        if (f == 90.0f) {
            return 6;
        }
        if (f == 180.0f) {
            return 3;
        }
        return f == 270.0f ? 8 : 1;
    }

    public static float exifOrientationToDegrees(int i) {
        if (i == 6) {
            return 90.0f;
        }
        if (i == 3) {
            return 180.0f;
        }
        return i == 8 ? 270.0f : 0.0f;
    }

    public static Bitmap readDrawableWithStream(Context context, int i) {
        InputStream openRawResource = context.getResources().openRawResource(i);
        Bitmap decodeStream = BitmapFactory.decodeStream(openRawResource, null, null);
        try {
            openRawResource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return decodeStream;
    }

    public static Bitmap sampeMinZoomFromFile(String str, int i) {
        Bitmap sampledBitmapFromFile = sampledBitmapFromFile(str, i, i);
        if (sampledBitmapFromFile == null) {
            return null;
        }
        Bitmap sampeMinZoomFromBitmap = sampeMinZoomFromBitmap(sampledBitmapFromFile, i);
        if (sampledBitmapFromFile != sampeMinZoomFromBitmap) {
            ourBitmapRecycle(sampledBitmapFromFile, true);
        }
        return sampeMinZoomFromBitmap;
    }

    public static Bitmap sampeMinZoomFromBitmap(Bitmap bitmap, int i) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = i;
        float f2 = f / width;
        float f3 = f / height;
        if (f2 < f3) {
            f2 = f3;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(f2, f2);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap sampeZoomFromFile(String str, int i) {
        Bitmap sampledBitmapFromFile = sampledBitmapFromFile(str, i, i);
        if (sampledBitmapFromFile == null) {
            return null;
        }
        Bitmap sampeZoomFromBitmap = sampeZoomFromBitmap(sampledBitmapFromFile, i);
        if (sampledBitmapFromFile != sampeZoomFromBitmap) {
            ourBitmapRecycle(sampledBitmapFromFile, false);
        }
        return sampeZoomFromBitmap;
    }

    public static Bitmap sampeZoomFromResource(Resources resources, int i, int i2) {
        Bitmap sampledBitmapFromResource = sampledBitmapFromResource(resources, i, i2);
        Bitmap sampeZoomFromBitmap = sampeZoomFromBitmap(sampledBitmapFromResource, i2);
        if (sampledBitmapFromResource != sampeZoomFromBitmap) {
            ourBitmapRecycle(sampledBitmapFromResource, false);
        }
        return sampeZoomFromBitmap;
    }

    public static Bitmap sampeZoomFromBitmap(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = i;
        float f2 = f / width;
        float f3 = f / height;
        if (f2 >= f3) {
            f2 = f3;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(f2, f2);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static Bitmap zoomImg(Bitmap bitmap, int i, int i2, boolean z, int i3) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = i / width;
        float f2 = i2 / height;
        Matrix matrix = new Matrix();
        if (z) {
            matrix.postScale(-f, f2);
        } else {
            matrix.postScale(f, f2);
        }
        matrix.postRotate(i3);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        if (i3 > i2 || i4 > i) {
            float f = i4 / i;
            int round = Math.round(i3 / i2);
            int round2 = Math.round(f);
            return round > round2 ? round : round2;
        }
        return 1;
    }

    public static BitmapFactory.Options bitmapOptionFromResource(Resources resources, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap decodeResource = BitmapFactory.decodeResource(resources, i, options);
        if (decodeResource != null && !decodeResource.isRecycled()) {
            decodeResource.recycle();
        }
        return options;
    }

    public static BitmapFactory.Options bitmapOptionFromFilename(String str) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options);
        if (decodeFile != null && !decodeFile.isRecycled()) {
            decodeFile.recycle();
        }
        return options;
    }

    public static BitmapFactory.Options bitmapOptionFromStream(InputStream inputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
        if (decodeStream != null && !decodeStream.isRecycled()) {
            decodeStream.recycle();
        }
        return options;
    }

    public static BitmapFactory.Options bitmapOptionFromUri(Context context, Uri uri) {
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            if (openInputStream == null) {
                return null;
            }
            BitmapFactory.Options optionOfBitmapFromStream = optionOfBitmapFromStream(openInputStream);
            openInputStream.close();
            return optionOfBitmapFromStream;
        } catch (Exception unused) {
            return null;
        }
    }

    public static Bitmap sampledBitmapFromResource(Resources resources, int i, int i2) {
        BitmapFactory.Options bitmapOptionFromResource = bitmapOptionFromResource(resources, i);
        bitmapOptionFromResource.inSampleSize = calculateInSampleSize(bitmapOptionFromResource, i2, i2);
        bitmapOptionFromResource.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(resources, i, bitmapOptionFromResource);
    }

    public static Bitmap sampledBitmapFromFile(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeFile(str, options);
    }

    public static BitmapFactory.Options optionOfBitmapFromStream(InputStream inputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        return options;
    }

    public static Bitmap sampledBitmapFromStream(InputStream inputStream, BitmapFactory.Options options, int i, int i2) {
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    public static Bitmap sampledBitmapFromStream(InputStream inputStream, InputStream inputStream2, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        options.inSampleSize = calculateInSampleSize(options, i, i2);
        options.inJustDecodeBounds = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeStream(inputStream2, null, options);
    }

    public static void ourBitmapRecycle(Bitmap bitmap, boolean z) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    public static int getOrientation(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            try {
                Cursor query = context.getContentResolver().query(uri, new String[]{"orientation"}, null, null, null);
                if (query == null) {
                    if (query != null) {
                        query.close();
                    }
                    return -1;
                } else if (!query.moveToFirst()) {
                    if (query != null) {
                        query.close();
                    }
                    return -1;
                } else {
                    int i = query.getInt(0);
                    if (query != null) {
                        query.close();
                    }
                    return i;
                }
            } catch (Throwable th) {
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException unused) {
            return -1;
        }
    }

    public static Bitmap readBitMap(Context context, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        return BitmapFactory.decodeStream(context.getResources().openRawResource(i), null, options);
    }

    public static Bitmap getImageThumbnail(String str, int i, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        options.inJustDecodeBounds = false;
        int i3 = options.outHeight;
        int i4 = options.outWidth / i;
        int i5 = i3 / i2;
        if (i4 >= i5) {
            i4 = i5;
        }
        options.inSampleSize = i4 > 0 ? i4 : 1;
        return ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(str, options), i, i2, 2);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float f) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
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

    public static InputStream getSDFileInputStream(Context context, String str) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            String path = Environment.getExternalStorageDirectory().getPath();
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

    public static String imagelPathFromURI(Context context, Uri uri) {
        Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = (query == null || !query.moveToFirst()) ? "" : query.getString(query.getColumnIndexOrThrow("_data"));
        if (query != null) {
            query.close();
        }
        return string;
    }

    public static Bitmap createCircleImage(Bitmap bitmap, int i) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        float f = i / 2;
        canvas.drawCircle(f, f, f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        return createBitmap;
    }
}
