package com.photoart.libsticker.sticker2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.onlineImage.DMAsyncImageLoader;
import com.picspool.lib.resource.DMWBImageRes;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;
import com.sky.testproject.R;

/* loaded from: classes2.dex */
public class DMStickerImageRes extends DMWBImageRes {
    protected String iconPressedFileName;
    private boolean isNativeJsonSticker;
    private boolean isShowNewTag;
    private Boolean islock = false;
    private int positionInleftMenu;
    private int stickerNumber;
    private String stickersUrlBase;

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

    @Override // com.picspool.lib.resource.DMWBImageRes
    public void getImageBitmap(Context context, DMWBImageRes.OnResImageLoadListener onResImageLoadListener) {
        String str;
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
        } else if (this.imageType == DMWBRes.LocationType.ONLINE) {
            String imageFileName = getImageFileName();
            if (imageFileName != null && !imageFileName.equals("") && (str = DMPreferencesUtil.get(context, "config", "stickerconfig")) != null) {
                try {
                    JSONArray jSONArray = new JSONObject(str).getJSONArray("stickers_data");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        if (jSONArray.getJSONObject(i).getString("name").equals(imageFileName)) {
                            if (onResImageLoadListener != null) {
                                onResImageLoadListener.onImageLoadFinish(getImageBitmapFromUrl());
                                return;
                            }
                            return;
                        }
                    }
                } catch (Exception unused) {
                }
            }
            Bitmap decodeFile = BitmapFactory.decodeFile(onlineImageResLocalFile(context));
            if (onResImageLoadListener != null) {
                onResImageLoadListener.onImageLoadFinish(decodeFile);
            }
        }
    }

    private String onlineImageResLocalFile(Context context) {
        String absolutePath = context.getFilesDir().getAbsolutePath();
        File file = new File(absolutePath + "/material");
        if (!file.exists()) {
            file.mkdir();
        }
        if (new File(absolutePath + "/material/" + getName()).exists()) {
            String str = absolutePath + "/material/" + getName() + "/" + getName();
            if (new File(str).exists()) {
                return str;
            }
            return null;
        }
        return null;
    }

    public Bitmap getImageBitmapFromUrl(final ImageView imageView, ImageView imageView2, int i) {
        if (this.context == null) {
            Toast.makeText(this.context, this.context.getString(R.string.warning_failed_download), Toast.LENGTH_SHORT).show();
            return null;
        }
        Bitmap loadImageBitamp = DMAsyncImageLoader.getInstance().loadImageBitamp(this.context, getIconFileName(), new DMAsyncImageLoader.ImageCallback() { // from class: com.photoart.libsticker.sticker2.DMStickerImageRes.1
            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoaded(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoadedError(Exception exc) {
                Toast.makeText(DMStickerImageRes.this.context, DMStickerImageRes.this.context.getString(R.string.warning_failed_download), Toast.LENGTH_SHORT).show();
            }
        }, i);
        if (loadImageBitamp != null) {
            if (imageView2 != null) {
                imageView2.setVisibility(View.GONE);
            }
            return loadImageBitamp;
        }
        return null;
    }

    public Bitmap getIconPressedBitmap() {
        return getImageFromAssetsFile(this.context, getIconPressedFileName(), 2);
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        return getImageFromAssetsFile(this.context, getIconFileName(), 2);
    }

    public Bitmap getIconBitmap(ImageView imageView) {
        if (getIconType() == DMWBRes.LocationType.ONLINE && !isNativeJsonSticker()) {
            return getImageBitmapFromUrl(imageView, null, 3);
        }
        return getImageFromAssetsFile(this.context, getIconFileName(), 2);
    }

    public Bitmap getIconBitmap(ImageView imageView, ImageView imageView2) {
        if (getIconType() == DMWBRes.LocationType.ONLINE && !isNativeJsonSticker()) {
            return getImageBitmapFromUrl(imageView, imageView2, 1);
        }
        if (imageView2 != null) {
            imageView2.setVisibility(View.GONE);
        }
        return getImageFromAssetsFile(this.context, getIconFileName(), 2);
    }

    public Bitmap getImageBitmap() {
        return getImageFromAssetsFile(this.context, getImageFileName(), 1);
    }

    public Bitmap getIconBitmap(int i, int i2) {
        float f;
        int height;
        Bitmap imageFromAssetsFile = getImageFromAssetsFile(this.context, getIconFileName(), 1);
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

    public void setIslock(boolean z) {
        this.islock = Boolean.valueOf(z);
    }

    public Boolean getIslock() {
        return this.islock;
    }

    public void setStickersUrlBase(String str) {
        this.stickersUrlBase = str;
    }

    public String getStickersUrlBase() {
        return this.stickersUrlBase;
    }

    public int getPositionInleftMenu() {
        return this.positionInleftMenu;
    }

    public void setPositionInleftMenu(int i) {
        this.positionInleftMenu = i;
    }

    public int getStickerNumber() {
        return this.stickerNumber;
    }

    public void setStickerNumber(int i) {
        this.stickerNumber = i;
    }

    public boolean isShowNewTag() {
        return this.isShowNewTag;
    }

    public void setShowNewTag(boolean z) {
        this.isShowNewTag = z;
    }

    public boolean isNativeJsonSticker() {
        return this.isNativeJsonSticker;
    }

    public void setNativeJsonSticker(boolean z) {
        this.isNativeJsonSticker = z;
    }
}
