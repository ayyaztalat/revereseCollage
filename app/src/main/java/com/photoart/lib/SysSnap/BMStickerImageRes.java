package com.photoart.lib.SysSnap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.widget.ImageView;
import java.io.InputStream;
import com.picspool.lib.onlineImage.DMAsyncImageLoader;
import com.picspool.lib.resource.DMWBImageRes;

/* loaded from: classes3.dex */
public class BMStickerImageRes extends DMWBImageRes {
    protected String iconPressedFileName;

    public String getIconPressedFileName() {
        return this.iconPressedFileName;
    }

    public void setIconPressedFileName(String str) {
        this.iconPressedFileName = str;
    }

    public static Bitmap getImageFromAssetsFile(Context context, String str, int i) {
        Bitmap bitmap = null;
        if (str == null) {
            return null;
        }
        try {
            InputStream open = context.getResources().getAssets().open(str);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inSampleSize = i;
            options.inPurgeable = true;
            options.inInputShareable = true;
            bitmap = BitmapFactory.decodeStream(open, null, options);
            open.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public Bitmap getIconPressedBitmap() {
        return getImageFromAssetsFile(this.context, getIconPressedFileName(), 2);
    }

    @Override
    public Bitmap getIconBitmap() {
        return getImageFromAssetsFile(this.context, getIconFileName(), 2);
    }

    public Bitmap getIconBitmap(ImageView imageView) {
        if (imageView != null) {
            imageView.setTag(getIconFileName());
        }
        if (getIconType() == LocationType.ONLINE) {
            return getImageBitmapFromUrl(imageView, 4);
        }
        return getImageFromAssetsFile(this.context, getIconFileName(), 2);
    }

    public Bitmap getImageBitmapFromUrl(final ImageView imageView, int i) {
        if (this.context == null) {
            return null;
        }
        DMAsyncImageLoader dMAsyncImageLoader = DMAsyncImageLoader.getInstance();
        final String iconFileName = getIconFileName();
        return dMAsyncImageLoader.loadImageBitamp(this.context, iconFileName, new DMAsyncImageLoader.ImageCallback() { // from class: org.photoart.lib.SysSnap.BMStickerImageRes.1
            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoadedError(Exception exc) {
            }

            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoaded(Bitmap bitmap) {
                ImageView imageView2 = imageView;
                if (imageView2 == null || !imageView2.getTag().equals(iconFileName)) {
                    return;
                }
                imageView.setImageBitmap(bitmap);
            }
        }, i);
    }

    public Bitmap getImageBitmap() {
        return getImageFromAssetsFile(this.context, getImageFileName(), 1);
    }

    public Bitmap getIconBitmap(int i, int i2) {
        Bitmap imageFromAssetsFile;
        float f;
        int height;
        if (getIconType() == LocationType.ONLINE) {
            imageFromAssetsFile = getIconBitmap(null);
        } else {
            imageFromAssetsFile = getImageFromAssetsFile(this.context, getIconFileName(), 1);
        }
        if (imageFromAssetsFile == null || imageFromAssetsFile.isRecycled()) {
            return null;
        }
        if (imageFromAssetsFile.getWidth() > imageFromAssetsFile.getHeight()) {
            f = i;
            height = imageFromAssetsFile.getWidth();
        } else {
            f = i;
            height = imageFromAssetsFile.getHeight();
        }
        float f2 = f / height;
        Matrix matrix = new Matrix();
        matrix.postScale(f2, f2);
        Bitmap createBitmap = Bitmap.createBitmap(imageFromAssetsFile, 0, 0, imageFromAssetsFile.getWidth(), imageFromAssetsFile.getHeight(), matrix, true);
        if (imageFromAssetsFile != null && imageFromAssetsFile != createBitmap && !imageFromAssetsFile.isRecycled()) {
            imageFromAssetsFile.recycle();
        }
        return createBitmap;
    }
}
