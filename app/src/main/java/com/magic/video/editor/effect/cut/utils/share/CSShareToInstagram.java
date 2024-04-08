package com.magic.video.editor.effect.cut.utils.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import com.sky.testproject.R;
import com.magic.video.editor.effect.cut.utils.CSBitmapIoCache;
import com.magic.video.editor.effect.cut.utils.CSBitmapUtils;
import com.magic.video.editor.effect.cut.utils.CSOtherAppPackages2;
import java.io.File;

/* loaded from: classes2.dex */
public class CSShareToInstagram {
    private static void shareImage(Activity activity, Bitmap bitmap) {
        CSShareToApp.shareImage(activity, CSOtherAppPackages2.instagramPackage, "shareig", activity.getResources().getString(R.string.app_name), bitmap);
    }

    private static void shareImageFromUri(Activity activity, Uri uri) {
        CSShareToApp.shareImageFromUri(activity, CSOtherAppPackages2.instagramPackage, "shareig", activity.getResources().getString(R.string.app_name), uri);
    }

    public static void shareImageFromUri(Activity activity, Uri uri, boolean z) {
        if (uri == null) {
            Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_image), Toast.LENGTH_SHORT).show();
        } else if (!z) {
            shareImageFromUri(activity, uri);
        } else {
            BitmapFactory.Options bitmapOptionFromUri = CSBitmapUtils.bitmapOptionFromUri(activity, uri);
            if (bitmapOptionFromUri == null || bitmapOptionFromUri.outWidth == bitmapOptionFromUri.outHeight) {
                shareImageFromUri(activity, uri);
                return;
            }
            Bitmap imageFromSDFile = CSBitmapUtils.getImageFromSDFile(activity, uri.getPath());
            shareImage(activity, imageFromSDFile, true);
            if (imageFromSDFile == null || imageFromSDFile.isRecycled()) {
                return;
            }
            imageFromSDFile.recycle();
        }
    }

    public static void shareImage(Activity activity, Bitmap bitmap, boolean z) {
        Bitmap createBitmap;
        Uri uriForFile;
        if (bitmap == null || bitmap.isRecycled()) {
            Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_image), Toast.LENGTH_SHORT).show();
        } else if (!z) {
            shareImage(activity, bitmap);
        } else {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (width != height) {
                int i = Math.max(width, height);
                Bitmap bitmap2 = null;
                try {
                    createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
                } catch (Exception unused) {
                    createBitmap = bitmap;
                }
                try {
                    Canvas canvas = new Canvas(createBitmap);
                    canvas.drawColor(-1);
                    if (width > height) {
                        canvas.drawBitmap(bitmap, 0.0f, (float) (width - height) / 2, new Paint());
                    } else {
                        canvas.drawBitmap(bitmap, (float) (height - width) / 2, 0.0f, new Paint());
                    }
                    String putJPG = CSBitmapIoCache.putJPG(activity.getPackageName(), createBitmap);
                    createBitmap.isRecycled();
                    if (putJPG == null || putJPG.isEmpty()) {
                        return;
                    }
                    File file = new File(putJPG);
                    uriForFile = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
                    if (uriForFile == null) {
                        return;
                    }
                    CSShareToApp.shareImageFromUri(activity, CSOtherAppPackages2.instagramPackage, "shareig", activity.getResources().getString(R.string.app_name), uriForFile);
                    return;
                } catch (Exception unused2) {
                    bitmap2 = createBitmap;
                    bitmap2.recycle();
                    return;
                }
            }
            CSShareToApp.shareImage(activity, CSOtherAppPackages2.instagramPackage, "shareig", activity.getResources().getString(R.string.app_name), bitmap);
        }
    }

    public static void shareImage2(Activity activity, Bitmap bitmap, boolean z) {
        if (bitmap == null || bitmap.isRecycled()) {
            Toast.makeText(activity, activity.getResources().getString(R.string.warning_no_image), Toast.LENGTH_SHORT).show();
        } else {
            shareImage(activity, bitmap);
        }
    }
}
