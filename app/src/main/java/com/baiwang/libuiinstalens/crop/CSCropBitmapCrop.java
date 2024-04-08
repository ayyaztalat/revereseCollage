package com.baiwang.libuiinstalens.crop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.picspool.lib.bitmap.DMBitmapUtil;

import java.io.InputStream;


/* loaded from: classes.dex */
public class CSCropBitmapCrop {
    public static Bitmap CropItemImage(Context context, Uri uri, int i) {
        int i2 = 0;
        float exifOrientationToDegrees;
        String scheme = uri.getScheme();
        try {
            if (scheme != null) {
                if (scheme.equalsIgnoreCase("file")) {
                    try {
                        exifOrientationToDegrees = DMBitmapUtil.exifOrientationToDegrees(new ExifInterface(uri.getPath()).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
                        i2 = (int) exifOrientationToDegrees;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    InputStream openInputStream = context.getContentResolver().openInputStream(uri);
                    BitmapFactory.Options optionOfBitmapFromStream = DMBitmapUtil.optionOfBitmapFromStream(openInputStream);
                    openInputStream.close();
                    InputStream openInputStream2 = context.getContentResolver().openInputStream(uri);
                    Bitmap sampledBitmapFromStream = DMBitmapUtil.sampledBitmapFromStream(openInputStream2, optionOfBitmapFromStream, i, i);
                    openInputStream2.close();
                    return realCropFromBitmap(sampledBitmapFromStream, i2, i);
                } else if (scheme.equalsIgnoreCase(FirebaseAnalytics.Param.CONTENT)) {
                    try {
                        try {
                            i2 = DMBitmapUtil.getOrientation(context, uri);
                        } catch (Exception unused) {
                            exifOrientationToDegrees = DMBitmapUtil.exifOrientationToDegrees(new ExifInterface(DMBitmapUtil.imagelPathFromURI(context, uri)).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1));
                        }
                    } catch (Exception unused2) {
                    }
                    InputStream openInputStream3 = context.getContentResolver().openInputStream(uri);
                    BitmapFactory.Options optionOfBitmapFromStream2 = DMBitmapUtil.optionOfBitmapFromStream(openInputStream3);
                    openInputStream3.close();
                    InputStream openInputStream22 = context.getContentResolver().openInputStream(uri);
                    Bitmap sampledBitmapFromStream2 = DMBitmapUtil.sampledBitmapFromStream(openInputStream22, optionOfBitmapFromStream2, i, i);
                    openInputStream22.close();
                    return realCropFromBitmap(sampledBitmapFromStream2, i2, i);
                }
            }
            InputStream openInputStream32 = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options optionOfBitmapFromStream22 = DMBitmapUtil.optionOfBitmapFromStream(openInputStream32);
            openInputStream32.close();
            InputStream openInputStream222 = context.getContentResolver().openInputStream(uri);
            Bitmap sampledBitmapFromStream22 = DMBitmapUtil.sampledBitmapFromStream(openInputStream222, optionOfBitmapFromStream22, i, i);
            openInputStream222.close();
            return realCropFromBitmap(sampledBitmapFromStream22, i2, i);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static Bitmap cropDrawableImage(Context context, int i, int i2) {
        BitmapFactory.Options bitmapOptionFromResource = DMBitmapUtil.bitmapOptionFromResource(context.getResources(), i);
        try {
            InputStream openRawResource = context.getResources().openRawResource(i);
            Bitmap sampledBitmapFromStream = DMBitmapUtil.sampledBitmapFromStream(openRawResource, bitmapOptionFromResource, i2, i2);
            openRawResource.close();
            return realCropFromBitmap(sampledBitmapFromStream, 0, i2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected static Bitmap realCropFromBitmap(Bitmap bitmap, int i, int i2) {
        int i3;
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
        if (bitmap != null) {
            float width2 = bitmap.getWidth() / bitmap.getHeight();
            if (width2 < 1.0f) {
                i3 = (int) (i2 / width2);
            } else {
                i2 = (int) (i2 * width2);
                i3 = i2;
            }
        } else {
            i2 = width;
            i3 = height;
        }
        try {
            createScaledBitmap = Bitmap.createScaledBitmap(bitmap, i2, i3, true);
        } catch (OutOfMemoryError unused) {
            float f = i2;
            float f2 = i3;
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
        new Canvas(createBitmap).drawBitmap(bitmap, rect, new Rect(0, 0, i, i2), new Paint());
        return createBitmap;
    }
}
