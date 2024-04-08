package com.picspool.lib.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.onlineImage.DMAsyncImageLoader;
import com.picspool.lib.resource.DMWBRes;
import com.picspool.lib.sysutillib.DMPreferencesUtil;

/* loaded from: classes3.dex */
public class DMWBImageRes extends DMWBRes {
    private FitType fitType;
    protected String imageFileName;
    private int imageID;
    protected DMWBRes.LocationType imageType;
    private Boolean isShowLike = false;

    /* loaded from: classes3.dex */
    public enum FitType {
        TITLE,
        SCALE
    }

    /* loaded from: classes3.dex */
    public interface OnResImageDownLoadListener {
        void onImageDownLoadFaile();

        void onImageDownLoadFinish(String str);
    }

    /* loaded from: classes3.dex */
    public interface OnResImageLoadListener {
        void onImageLoadFaile();

        void onImageLoadFinish(Bitmap bitmap);
    }

    public void setIsShowLikeIcon(boolean z) {
        this.isShowLike = Boolean.valueOf(z);
    }

    public Boolean getIsShowLikeIcon() {
        return this.isShowLike;
    }

    public String getImageFileName() {
        return this.imageFileName;
    }

    public void setImageFileName(String str) {
        this.imageFileName = str;
    }

    public DMWBRes.LocationType getImageType() {
        return this.imageType;
    }

    public void setImageType(DMWBRes.LocationType locationType) {
        this.imageType = locationType;
    }

    public boolean isImageResInLocal(Context context) {
        DMWBRes.LocationType locationType;
        if (this.imageType == DMWBRes.LocationType.RES || this.imageType == DMWBRes.LocationType.ASSERT || (locationType = this.imageType) == null || locationType == DMWBRes.LocationType.CACHE) {
            return true;
        }
        return this.imageType == DMWBRes.LocationType.ONLINE && onlineImageResLocalFile(context) != null;
    }

    public void getImageBitmap(Context context, OnResImageLoadListener onResImageLoadListener) {
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
                    JSONArray jSONArray = new JSONObject(str).getJSONArray("data");
                    for (int i = 0; i < jSONArray.length(); i++) {
                        if (jSONArray.getJSONObject(i).getString("content_name").equals(imageFileName)) {
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

    public Bitmap getImageBitmapFromUrl() {
        Bitmap loadImageBitamp = new DMAsyncImageLoader().loadImageBitamp(this.context, getIconFileName(), new DMAsyncImageLoader.ImageCallback() { // from class: com.picspool.lib.resource.DMWBImageRes.1
            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoaded(Bitmap bitmap) {
            }

            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.ImageCallback
            public void imageLoadedError(Exception exc) {
            }
        });
        if (loadImageBitamp != null) {
            return loadImageBitamp;
        }
        return null;
    }

    public Bitmap getLocalImageBitmap() {
        DMWBRes.LocationType locationType = this.imageType;
        if (locationType == null) {
            return null;
        }
        if (locationType == DMWBRes.LocationType.RES) {
            return DMBitmapUtil.getImageFromResourceFile(getResources(), this.imageID);
        }
        if (this.imageType == DMWBRes.LocationType.ASSERT) {
            return DMBitmapUtil.getImageFromAssetsFile(getResources(), this.imageFileName);
        }
        return null;
    }

    public void downloadImageOnlineRes(Context context, final OnResImageDownLoadListener onResImageDownLoadListener) {
        if (context == null) {
            onResImageDownLoadListener.onImageDownLoadFaile();
            return;
        }
        String absolutePath = context.getFilesDir().getAbsolutePath();
        File file = new File(absolutePath + "/material");
        if (!file.exists()) {
            file.mkdir();
        }
        File file2 = new File(absolutePath + "/material/" + getName());
        if (!file2.exists()) {
            file2.mkdir();
        }
        new DMAsyncImageLoader().loadImageToFile(context, getImageFileName(), absolutePath + "/material/" + getName() + "/" + getName(), new DMAsyncImageLoader.OnLineImageToFileCallback() { // from class: com.picspool.lib.resource.DMWBImageRes.2
            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.OnLineImageToFileCallback
            public void imageDownload(String str) {
                OnResImageDownLoadListener onResImageDownLoadListener2 = onResImageDownLoadListener;
                if (onResImageDownLoadListener2 != null) {
                    onResImageDownLoadListener2.onImageDownLoadFinish(str);
                }
            }

            @Override // com.picspool.lib.onlineImage.DMAsyncImageLoader.OnLineImageToFileCallback
            public void imageDownloadFaile(Exception exc) {
                onResImageDownLoadListener.onImageDownLoadFaile();
            }
        });
    }

    public FitType getFitType() {
        return this.fitType;
    }

    public void setScaleType(FitType fitType) {
        this.fitType = fitType;
    }

    public FitType getScaleType() {
        return this.fitType;
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
}
