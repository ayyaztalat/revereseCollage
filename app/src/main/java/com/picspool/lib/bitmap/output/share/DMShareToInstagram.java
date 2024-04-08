package com.picspool.lib.bitmap.output.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.widget.Toast;
import java.io.File;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.p017io.DMBitmapIoCache;
import com.picspool.lib.packages.DMAppPackages;
import com.picspool.lib.packages.DMOtherAppPackages;
import com.sky.testproject.R;


/* loaded from: classes3.dex */
public class DMShareToInstagram {
    private static void shareImage(Activity activity, Bitmap bitmap) {
        DMShareToApp.shareImage(activity, DMOtherAppPackages.instagramPackage, "shareig", DMShareTag.getDefaultTag(activity), bitmap);
    }

    private static void shareImageFromUri(Activity activity, Uri uri) {
        DMShareToApp.shareImageFromUri(activity, DMOtherAppPackages.instagramPackage, "shareig", DMShareTag.getDefaultTag(activity), uri);
    }

    public static void shareImageFromUri(Activity activity, Uri uri, boolean z) {
        if (uri == null) {
            Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_image), Toast.LENGTH_LONG).show();
        } else if (!z) {
            shareImageFromUri(activity, uri);
        } else {
            BitmapFactory.Options bitmapOptionFromUri = DMBitmapUtil.bitmapOptionFromUri(activity, uri);
            if (bitmapOptionFromUri == null || bitmapOptionFromUri.outWidth == bitmapOptionFromUri.outHeight) {
                shareImageFromUri(activity, uri);
                return;
            }
            Bitmap imageFromSDFile = DMBitmapUtil.getImageFromSDFile(activity, uri.getPath());
            shareImage(activity, imageFromSDFile, true);
            if (imageFromSDFile == null || imageFromSDFile.isRecycled()) {
                return;
            }
            imageFromSDFile.recycle();
        }
    }

    public static void shareImage(Activity activity, Bitmap bitmap, boolean z) {
        Uri fromFile;
        if (bitmap == null || bitmap.isRecycled()) {
            Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_image), Toast.LENGTH_LONG).show();
        } else if (!z) {
            shareImage(activity, bitmap);
        } else {
            new DMAppPackages();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width != height) {
                int i = width > height ? width : height;
                Bitmap bitmap2 = null;
                try {
                    Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
                    try {
                        Canvas canvas = new Canvas(createBitmap);
                        canvas.drawColor(-1);
                        if (width > height) {
                            canvas.drawBitmap(bitmap, 0.0f, (width - height) / 2, new Paint());
                        } else {
                            canvas.drawBitmap(bitmap, (height - width) / 2, 0.0f, new Paint());
                        }
                        String putJPG = DMBitmapIoCache.putJPG(DMAppPackages.getAppName(activity.getPackageName()), createBitmap);
                        createBitmap.isRecycled();
                        if (putJPG == null || putJPG.equals("") || (fromFile = Uri.fromFile(new File(putJPG))) == null) {
                            return;
                        }
                        DMShareToApp.shareImageFromUri(activity, DMOtherAppPackages.instagramPackage, "shareig", DMShareTag.getDefaultTag(activity), fromFile);
                    } catch (Exception unused) {
                        bitmap2 = createBitmap;
                        if (bitmap2 != null) {
                            bitmap2.recycle();
                        }
                    }
                } catch (Exception unused2) {
                }
            } else {
                DMShareToApp.shareImage(activity, DMOtherAppPackages.instagramPackage, "shareig", DMShareTag.getDefaultTag(activity), bitmap);
            }
        }
    }

    public static void shareImage2(Activity activity, Bitmap bitmap, boolean z) {
        if (bitmap == null || bitmap.isRecycled()) {
            Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_image), Toast.LENGTH_LONG).show();
        } else {
            shareImage(activity, bitmap);
        }
    }
}
