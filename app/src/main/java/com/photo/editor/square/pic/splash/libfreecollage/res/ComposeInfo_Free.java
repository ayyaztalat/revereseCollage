package com.photo.editor.square.pic.splash.libfreecollage.res;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;
import com.photo.editor.square.pic.splash.libfreecollage.resource.background.p009mg.Bg_Free_Manager;
import com.photo.editor.square.pic.splash.libfreecollage.resource.collage.ComposeManager_Free;
import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.resource.DMWBImageRes;

/* loaded from: classes2.dex */
public class ComposeInfo_Free extends DMWBImageRes {
    private int backgroundColor;
    private String backgroundName_1_1;
    private String backgroundName_5_4;
    private Bg_Free_Manager.BgType bgType;
    private String forgroundName_1_1;
    private String forgroundName_5_4;
    private boolean isTile = false;
    private boolean isWallpaper = false;
    private Context mContext;
    private RectF margin;
    private int photoBackgroundColor;

    public ComposeInfo_Free(Context context) {
        this.mContext = context;
    }

    public void setBgType(Bg_Free_Manager.BgType bgType) {
        this.bgType = bgType;
    }

    public Bg_Free_Manager.BgType getBgType() {
        return this.bgType;
    }

    public void setIsTile(boolean z) {
        if (z) {
            Log.v("t", "t");
        }
        this.isTile = z;
    }

    public boolean getIsTile() {
        return this.isTile;
    }

    public void setIsWallpaper(boolean z) {
        this.isWallpaper = z;
    }

    public boolean getIsWallpaper() {
        return this.isWallpaper;
    }

    public void setForegroundName_1_1(String str) {
        this.forgroundName_1_1 = str;
    }

    public String getForegroundName_1_1() {
        return this.forgroundName_1_1;
    }

    public void setBackgroundName_1_1(String str) {
        this.backgroundName_1_1 = str;
    }

    public String getBackgroundName_1_1() {
        return this.backgroundName_1_1;
    }

    public void setForegroundName_5_4(String str) {
        this.forgroundName_5_4 = str;
    }

    public String getForegroundName_5_4() {
        return this.forgroundName_5_4;
    }

    public void setBackgroundName_5_4(String str) {
        this.backgroundName_5_4 = str;
    }

    public String getBackgroundName_5_4() {
        return this.backgroundName_5_4;
    }

    public String getBackgroundName(ComposeManager_Free.FreeComposeType freeComposeType) {
        if (freeComposeType == ComposeManager_Free.FreeComposeType.COMPOSE_11) {
            return this.backgroundName_1_1;
        }
        return this.backgroundName_5_4;
    }

    public String getForegroundName(ComposeManager_Free.FreeComposeType freeComposeType) {
        if (freeComposeType == ComposeManager_Free.FreeComposeType.COMPOSE_11) {
            return this.forgroundName_1_1;
        }
        return this.forgroundName_5_4;
    }

    public void setBackgroundColor(int i) {
        this.backgroundColor = i;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setPhotogroundColor(int i) {
        this.photoBackgroundColor = i;
    }

    public int getPhotoBackgroundColor() {
        return this.photoBackgroundColor;
    }

    public void setMarginRectF(RectF rectF) {
        this.margin = rectF;
    }

    public RectF getMarginRectF() {
        return this.margin;
    }

    public Bitmap getBackgroundBitmap(ComposeManager_Free.FreeComposeType freeComposeType) {
        if (freeComposeType == ComposeManager_Free.FreeComposeType.COMPOSE_11) {
            String str = this.backgroundName_1_1;
            if (str == null || str.length() <= 0) {
                return null;
            }
            return DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), this.backgroundName_1_1);
        }
        String str2 = this.backgroundName_5_4;
        if (str2 == null || str2.length() <= 0) {
            return null;
        }
        return DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), this.backgroundName_5_4);
    }

    public Bitmap getForegroundBitmap(ComposeManager_Free.FreeComposeType freeComposeType) {
        if (freeComposeType == ComposeManager_Free.FreeComposeType.COMPOSE_11) {
            String str = this.forgroundName_1_1;
            if (str == null || str.length() <= 0) {
                return null;
            }
            return DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), this.forgroundName_1_1);
        }
        String str2 = this.forgroundName_5_4;
        if (str2 == null || str2.length() <= 0) {
            return null;
        }
        return DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), this.forgroundName_5_4);
    }
}
