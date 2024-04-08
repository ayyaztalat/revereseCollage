package com.photoart.libsticker.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import java.io.FileInputStream;
import java.io.IOException;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes2.dex */
public class DMStickerRes extends DMWBImageRes {
    private BitmapFactory.Options iconConfig;

    @Override // com.picspool.lib.resource.DMWBImageRes
    public Bitmap getLocalImageBitmap() {
        if (this.imageType == null) {
            return null;
        }
        if (this.imageType == DMWBRes.LocationType.CACHE) {
            return getCacheBitmap(this.context, getImageFileName(), 1);
        }
        return super.getLocalImageBitmap();
    }

    @Override // com.picspool.lib.resource.DMWBImageRes
    public void getImageBitmap(Context context, DMWBImageRes.OnResImageLoadListener onResImageLoadListener) {
        if (this.imageType == null && onResImageLoadListener != null) {
            onResImageLoadListener.onImageLoadFaile();
        }
        if (this.imageType == DMWBRes.LocationType.RES) {
            if (onResImageLoadListener != null) {
                onResImageLoadListener.onImageLoadFinish(DMBitmapUtil.getImageFromAssetsFile(getResources(), this.imageFileName));
            }
        } else if (this.imageType == DMWBRes.LocationType.ASSERT) {
            if (onResImageLoadListener != null) {
                onResImageLoadListener.onImageLoadFinish(DMBitmapUtil.getImageFromAssetsFile(getResources(), this.imageFileName));
            }
        } else if (this.imageType == DMWBRes.LocationType.CACHE) {
            Bitmap cacheBitmap = getCacheBitmap(context, getImageFileName(), 1);
            if (onResImageLoadListener != null) {
                onResImageLoadListener.onImageLoadFinish(cacheBitmap);
            }
        }
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        if (getIconFileName() == null) {
            return null;
        }
        if (getIconType() == DMWBRes.LocationType.CACHE) {
            return getCacheBitmap(this.context, getIconFileName(), 1);
        }
        if (getIconFileName() == null) {
            return null;
        }
        if (getIconType() == DMWBRes.LocationType.RES) {
            return DMBitmapUtil.getImageFromResourceFile(getResources(), getIconID());
        }
        if (getIconType() == DMWBRes.LocationType.ASSERT) {
            if (this.iconConfig != null) {
                return DMBitmapUtil.getImageFromAssetsFile(getResources(), getIconFileName(), this.iconConfig);
            }
            return DMBitmapUtil.getImageFromAssetsFile(getResources(), getIconFileName(), 2);
        }
        return null;
    }

    public Bitmap getIconBitmap(int i, int i2) {
        float f;
        int height;
        Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(this.context.getResources(), getIconFileName(), 2);
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

    private Bitmap getCacheBitmap(Context context, String str, int i) {
        Bitmap bitmap = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = i;
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeStream(fileInputStream, null, options);
            fileInputStream.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public BitmapFactory.Options getIconConfig() {
        return this.iconConfig;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setIconConfig(BitmapFactory.Options options) {
        this.iconConfig = options;
    }
}
