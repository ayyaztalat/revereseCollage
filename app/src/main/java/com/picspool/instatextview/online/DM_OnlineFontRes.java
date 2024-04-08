package com.picspool.instatextview.online;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import java.io.File;
import com.picspool.instatextview.online.DM_OnlineFontDownloadInterface;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes3.dex */
public class DM_OnlineFontRes extends DMWBRes {
    private String fontFileName;
    private Bitmap fontIcon;
    private String fontImgPath;
    private String httpFilePath;
    private boolean isDownLoad;
    private DMWBRes.LocationType locationType;
    private int mTextAddHeight;

    @Override // com.picspool.lib.resource.DMWBRes
    public String getType() {
        return "DMFontRes";
    }

    public String getFontFileName() {
        return this.fontFileName;
    }

    public void setFontFileName(String str) {
        this.fontFileName = str;
    }

    public DMWBRes.LocationType getLocationType() {
        return this.locationType;
    }

    public void setLocationType(DMWBRes.LocationType locationType) {
        this.locationType = locationType;
    }

    public void setIconBitmap(Bitmap bitmap) {
        this.fontIcon = bitmap;
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        Bitmap bitmap = this.fontIcon;
        return bitmap == null ? Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565) : bitmap;
    }

    public Typeface getFontTypeface(Context context) {
        DMWBRes.LocationType locationType = this.locationType;
        if (locationType == null) {
            return null;
        }
        if (locationType == DMWBRes.LocationType.ASSERT) {
            try {
                if (getName() == "Default") {
                    return Typeface.DEFAULT;
                }
                return Typeface.createFromAsset(context.getAssets(), this.fontFileName);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        if (this.locationType == DMWBRes.LocationType.ONLINE && this.isDownLoad) {
            try {
                return Typeface.createFromFile(this.fontFileName);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public int getTextAddHeight() {
        return this.mTextAddHeight;
    }

    public void setTextAddHeight(int i) {
        this.mTextAddHeight = i;
    }

    public String getHttpFilePath() {
        return this.httpFilePath;
    }

    public void setHttpFilePath(String str) {
        this.httpFilePath = str;
    }

    public boolean isDownLoad() {
        return this.isDownLoad;
    }

    public void setDownLoad(boolean z) {
        this.isDownLoad = z;
    }

    public String getFontImgPath() {
        return this.fontImgPath;
    }

    public void setFontImgPath(String str) {
        this.fontImgPath = str;
    }

    public void downloadFontOnlineRes(Context context, DM_OnlineFontDownloadInterface.OnFontResDownLoadListener onFontResDownLoadListener) {
        if (context == null) {
            onFontResDownLoadListener.onDownLoadFaile("Context is Null");
            return;
        }
        String absolutePath = context.getFilesDir().getAbsolutePath();
        File file = new File(absolutePath + "/nfonts");
        if (!file.exists()) {
            file.mkdir();
        }
        new DM_OnlineAsyncFontLoader().loadFontToFile(context, getHttpFilePath(), absolutePath + "/nfonts/" + getName() + ".zip", absolutePath + "/nfonts/", onFontResDownLoadListener);
    }
}
