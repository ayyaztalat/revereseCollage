package com.magic.video.editor.effect.cut.utils.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader;
import com.magic.video.editor.effect.cut.utils.CSBitmapUtils;
import com.magic.video.editor.effect.cut.utils.CSPreferencesUtil;
import com.magic.video.editor.effect.cut.utils.base.CSStickerRes;
import java.io.File;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class CSImageRes extends CSStickerRes {
    private FitType fitType;
    protected String imageFileName;
    private int imageID;
    protected LocationType imageType;
    private Boolean isShowLike = false;

    /* loaded from: classes2.dex */
    public enum FitType {
        TITLE,
        SCALE
    }

    /* loaded from: classes2.dex */
    public interface OnResImageDownLoadListener {
        void onImageDownLoadFaile();

        void onImageDownLoadFinish(String str);
    }

    /* loaded from: classes2.dex */
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

    public LocationType getImageType() {
        return this.imageType;
    }

    public void setImageType(LocationType locationType) {
        this.imageType = locationType;
    }

    public boolean isImageResInLocal(Context context) {
        LocationType locationType;
        if (this.imageType == LocationType.RES || this.imageType == LocationType.ASSERT || (locationType = this.imageType) == null || locationType == LocationType.CACHE) {
            return true;
        }
        return this.imageType == LocationType.ONLINE && onlineImageResLocalFile(context) != null;
    }

    public void getImageBitmap(Context context, OnResImageLoadListener onResImageLoadListener) {
        String str;
        if (this.imageType == null && onResImageLoadListener != null) {
            onResImageLoadListener.onImageLoadFaile();
        }
        if (this.imageType == LocationType.RES) {
            if (onResImageLoadListener != null) {
                onResImageLoadListener.onImageLoadFinish(CSBitmapUtils.getImageFromAssetsFile(getResources(), this.imageFileName));
            }
        } else if (this.imageType == LocationType.ASSERT) {
            if (onResImageLoadListener != null) {
                onResImageLoadListener.onImageLoadFinish(CSBitmapUtils.getImageFromAssetsFile(getResources(), this.imageFileName));
            }
        } else if (this.imageType == LocationType.ONLINE) {
            String imageFileName = getImageFileName();
            if (imageFileName != null && !imageFileName.equals("") && (str = CSPreferencesUtil.get(context, "config", "stickerconfig")) != null) {
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
        Bitmap loadImageBitamp = new CSAsyncImageLoader().loadImageBitamp(this.context, getIconFileName(), new CSAsyncImageLoader.ImageCallback() { // from class: com.magic.video.editor.effect.cut.utils.base.CSImageRes.1
            @Override // com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader.ImageCallback
            public void imageLoaded(Bitmap bitmap) {
            }

            @Override // com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader.ImageCallback
            public void imageLoadedError(Exception exc) {
            }
        });
        if (loadImageBitamp != null) {
            return loadImageBitamp;
        }
        return null;
    }

    public Bitmap getLocalImageBitmap() {
        LocationType locationType = this.imageType;
        if (locationType == null) {
            return null;
        }
        if (locationType == LocationType.RES) {
            return CSBitmapUtils.getImageFromResourceFile(getResources(), this.imageID);
        }
        if (this.imageType == LocationType.ASSERT) {
            return CSBitmapUtils.getImageFromAssetsFile(getResources(), this.imageFileName);
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
        new CSAsyncImageLoader().loadImageToFile(context, getImageFileName(), absolutePath + "/material/" + getName() + "/" + getName(), new CSAsyncImageLoader.OnLineImageToFileCallback() { // from class: com.magic.video.editor.effect.cut.utils.base.CSImageRes.2
            @Override // com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader.OnLineImageToFileCallback
            public void imageDownload(String str) {
                OnResImageDownLoadListener onResImageDownLoadListener2 = onResImageDownLoadListener;
                if (onResImageDownLoadListener2 != null) {
                    onResImageDownLoadListener2.onImageDownLoadFinish(str);
                }
            }

            @Override // com.magic.video.editor.effect.cut.utils.CSAsyncImageLoader.OnLineImageToFileCallback
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
