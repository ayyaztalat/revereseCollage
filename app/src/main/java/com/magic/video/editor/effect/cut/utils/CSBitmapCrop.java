package com.magic.video.editor.effect.cut.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.InputStream;
import java.util.Objects;

/* loaded from: classes2.dex */
public class CSBitmapCrop {
    public static Bitmap CropItemImage(Context context, Uri uri, int i) {
        int i2 = 0;
        float exifOrientationToDegrees = 0;
        String scheme = uri.getScheme();
        try {
            if (scheme != null) {
                if (scheme.equalsIgnoreCase("file")) {
                    exifOrientationToDegrees = CSBitmapUtils.exifOrientationToDegrees(new ExifInterface(Objects.requireNonNull(uri.getPath())).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
                } else if (scheme.equalsIgnoreCase(FirebaseAnalytics.Param.CONTENT)) {
                    try {
                        i2 = CSBitmapUtils.getOrientation(context, uri);
                    } catch (Exception unused) {
                        exifOrientationToDegrees = CSBitmapUtils.exifOrientationToDegrees(new ExifInterface(CSBitmapUtils.imagelPathFromURI(context, uri)).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
                    }
                    InputStream openInputStream = context.getContentResolver().openInputStream(uri);
                    BitmapFactory.Options optionOfBitmapFromStream = CSBitmapUtils.optionOfBitmapFromStream(context.getContentResolver().openInputStream(uri));
                    openInputStream.close();
                    InputStream openInputStream2 = context.getContentResolver().openInputStream(uri);
                    Bitmap sampledBitmapFromStream = CSBitmapUtils.sampledBitmapFromStream(openInputStream2, optionOfBitmapFromStream, i, i);
                    openInputStream2.close();
                    return realCropFromBitmap(sampledBitmapFromStream, i2, i);
                }
                i2 = (int) exifOrientationToDegrees;
                InputStream openInputStream3 = context.getContentResolver().openInputStream(uri);
                BitmapFactory.Options optionOfBitmapFromStream2 = CSBitmapUtils.optionOfBitmapFromStream(context.getContentResolver().openInputStream(uri));
                openInputStream3.close();
                InputStream openInputStream22 = context.getContentResolver().openInputStream(uri);
                Bitmap sampledBitmapFromStream2 = CSBitmapUtils.sampledBitmapFromStream(openInputStream22, optionOfBitmapFromStream2, i, i);
                openInputStream22.close();
                return realCropFromBitmap(sampledBitmapFromStream2, i2, i);
            }
            InputStream openInputStream32 = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options optionOfBitmapFromStream22 = CSBitmapUtils.optionOfBitmapFromStream(context.getContentResolver().openInputStream(uri));
            openInputStream32.close();
            InputStream openInputStream222 = context.getContentResolver().openInputStream(uri);
            Bitmap sampledBitmapFromStream22 = CSBitmapUtils.sampledBitmapFromStream(openInputStream222, optionOfBitmapFromStream22, i, i);
            openInputStream222.close();
            return realCropFromBitmap(sampledBitmapFromStream22, i2, i);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap CropSquareImage(Context context, Uri uri, int i) {
        int exifOrientationToDegrees = 0;
        String scheme = uri.getScheme();
        if (scheme.equalsIgnoreCase("file")) {
            try {
                exifOrientationToDegrees = (int) CSBitmapUtils.exifOrientationToDegrees(new ExifInterface(uri.getPath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
            } catch (Exception ignored) {}
        } else {
            if (scheme.equalsIgnoreCase(FirebaseAnalytics.Param.CONTENT)) {
                exifOrientationToDegrees = CSBitmapUtils.getOrientation(context, uri);
            }
            exifOrientationToDegrees = -1;
        }
        Bitmap bitmap = null;
        try {
            InputStream openInputStream = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options optionOfBitmapFromStream = CSBitmapUtils.optionOfBitmapFromStream(context.getContentResolver().openInputStream(uri));
            openInputStream.close();
            int i2 = optionOfBitmapFromStream.outHeight;
            int i3 = optionOfBitmapFromStream.outWidth;
            int i4 = ((int) (i2 > i3 ? i2 / i3 : i3 / i2)) * i;
            InputStream openInputStream2 = context.getContentResolver().openInputStream(uri);
            Bitmap sampledBitmapFromStream = CSBitmapUtils.sampledBitmapFromStream(openInputStream2, optionOfBitmapFromStream, i4, i4);
            openInputStream2.close();
            Bitmap realCropFromBitmap = realCropFromBitmap(sampledBitmapFromStream, exifOrientationToDegrees, i4);
            int width = realCropFromBitmap.getWidth();
            int height = realCropFromBitmap.getHeight();
            int i5 = width > height ? height : width;
            bitmap = Bitmap.createBitmap(realCropFromBitmap, width > height ? (width - height) / 2 : 0, width > height ? 0 : (height - width) / 2, i5, i5, (Matrix) null, false);
            if (realCropFromBitmap != bitmap) {
                realCropFromBitmap.recycle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap cropDrawableImage(Context context, int i, int i2) {
        BitmapFactory.Options bitmapOptionFromResource = CSBitmapUtils.bitmapOptionFromResource(context.getResources(), i);
        try {
            InputStream openRawResource = context.getResources().openRawResource(i);
            Bitmap sampledBitmapFromStream = CSBitmapUtils.sampledBitmapFromStream(openRawResource, bitmapOptionFromResource, i2, i2);
            openRawResource.close();
            return realCropFromBitmap(sampledBitmapFromStream, 0, i2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static Bitmap realCropFromBitmap(Bitmap bitmap, int i, int i2) throws Exception {
        Bitmap createScaledBitmap;
        if (i != -1 && i != 0) {
            Matrix matrix = new Matrix();
            matrix.setRotate(i, bitmap.getWidth(), bitmap.getHeight());
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (createBitmap != bitmap && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = createBitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (bitmap != null && Math.max(bitmap.getWidth(), bitmap.getHeight()) >= i2) {
            float width2 = bitmap.getWidth() / bitmap.getHeight();
            if (width2 > 1.0f) {
                if (width <= i2) {
                    i2 = width;
                }
                height = (int) (i2 / width2);
                width = i2;
            } else {
                if (height <= i2) {
                    i2 = height;
                }
                width = (int) (i2 * width2);
                height = i2;
            }
        }
        try {
            createScaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        } catch (OutOfMemoryError unused) {
            float f = width;
            float f2 = height;
            try {
                createScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (f / 0.9f), (int) (f2 / 0.9f), true);
            } catch (OutOfMemoryError unused2) {
                try {
                    createScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (f / 0.8f), (int) (f2 / 0.8f), true);
                } catch (OutOfMemoryError unused3) {
                    createScaledBitmap = Bitmap.createScaledBitmap(bitmap, (int) (f / 0.6f), (int) (f2 / 0.6f), true);
                }
            }
        }
        int width3 = createScaledBitmap.getWidth();
        int height2 = createScaledBitmap.getHeight();
        Log.v("t", String.valueOf(width3) + String.valueOf(height2));
        if (bitmap != createScaledBitmap) {
            bitmap.recycle();
        }
        return createScaledBitmap;
    }

    public static Bitmap cropCenterScaleBitmap(Bitmap bitmap, int i, int i2) {
        if (bitmap == null || bitmap.isRecycled() || i <= 0 || i2 <= 0) {
            return bitmap;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f = i / i2;
        float f2 = width;
        float f3 = height;
        float f4 = f2 / f3;
        Rect rect = new Rect(0, 0, width, height);
        if (f > f4) {
            int i3 = (int) (f2 * (1.0f / f));
            rect.top = (height - i3) / 2;
            rect.bottom = rect.top + i3;
        } else if (f < f4) {
            int i4 = (int) (f3 * f);
            rect.left = (width - i4) / 2;
            rect.right = rect.left + i4;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, rect, new Rect(0, 0, i, i2), new Paint());
        }
        return createBitmap;
    }
}
