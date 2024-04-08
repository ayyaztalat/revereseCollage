package com.winflag.libcollage.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.picspool.lib.bitmap.DMBitmapUtil;
import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;
import com.picspool.lib.resource.DMWBImageRes;
import com.winflag.libcollage.activity.BaseSdk;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class TemplateRes extends DMWBImageRes {
    private String backgroundPath;
    private DMWBImageRes.FitType fitType;
    private Bitmap icon;
    private String image;
    private boolean isSingleMode;
    int mAspectRatio;
    int mFrameWidth;
    private int mHeight;
    private String mIconPath;
    public boolean mInnerChange;
    private int mInnerFrameWidth;
    List<LibDMCollageInfo> mLibCollageInfo;
    private int mOutFrameWidth;
    private boolean mParseFailed;
    int mRadius;
    private String mResId;
    private int mVersion;
    private int mWidth;
    int mindex;
    private int photoAmount;
    private String puzzlePath;
    private String rootPath;

    public TemplateRes() {
        this.mRadius = 0;
        this.mAspectRatio = 1;
        this.mindex = 0;
        this.mLibCollageInfo = null;
        this.mFrameWidth = 12;
        this.mOutFrameWidth = 0;
        this.mInnerFrameWidth = 0;
        this.isSingleMode = false;
        this.mResId = "";
        this.mVersion = 1;
        this.mIconPath = "";
        this.mWidth = 0;
        this.mHeight = 0;
        this.mParseFailed = false;
        this.mInnerChange = true;
    }

    public TemplateRes(int i, int i2, int i3, List<LibDMCollageInfo> list) {
        this.mRadius = 0;
        this.mAspectRatio = 1;
        this.mindex = 0;
        this.mLibCollageInfo = null;
        this.mFrameWidth = 12;
        this.mOutFrameWidth = 0;
        this.mInnerFrameWidth = 0;
        this.isSingleMode = false;
        this.mResId = "";
        this.mVersion = 1;
        this.mIconPath = "";
        this.mWidth = 0;
        this.mHeight = 0;
        this.mParseFailed = false;
        this.mindex = i;
        this.mRadius = i2;
        this.mAspectRatio = i3;
        this.mLibCollageInfo = list;
        this.mInnerChange = true;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public int getFrameWidth() {
        return this.mFrameWidth;
    }

    public void setFrameWidth(int i) {
        this.mFrameWidth = i;
    }

    public int getAspectRatio() {
        return this.mAspectRatio;
    }

    public List<LibDMCollageInfo> getCollageInfo() {
        if (this.mLibCollageInfo == null) {
            this.mLibCollageInfo = new ArrayList();
        }
        if (this.mLibCollageInfo.size() == 0 && !getPuzzlePath().equals("")) {
            try {
                PuzzleParser.parse(BaseSdk.getContext().getAssets().open(getPuzzlePath()), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.mLibCollageInfo;
    }

    public int getRoundRadius() {
        return this.mRadius;
    }

    public void setAspectRatio(int i) {
        this.mAspectRatio = i;
    }

    public void setIndex(int i) {
        this.mindex = i;
    }

    public void setCollageInfo(List<LibDMCollageInfo> list) {
        this.mLibCollageInfo = list;
    }

    public void setRoundRadius(int i) {
        this.mRadius = i;
    }

    public int getOutFrameWidth() {
        return this.mOutFrameWidth;
    }

    public void setOutFrameWidth(int i) {
        if (this.mLibCollageInfo != null) {
            for (int i2 = 0; i2 < this.mLibCollageInfo.size(); i2++) {
                this.mLibCollageInfo.get(i2).setOutFrameWidth(i);
            }
        }
        this.mOutFrameWidth = i;
    }

    public int getInnerFrameWidth() {
        return this.mInnerFrameWidth;
    }

    public void setInnerFrameWidth(int i) {
        if (this.mLibCollageInfo != null) {
            for (int i2 = 0; i2 < this.mLibCollageInfo.size(); i2++) {
                this.mLibCollageInfo.get(i2).setInnerFrameWidth(i);
            }
        }
        this.mInnerFrameWidth = i;
    }

    public void setSingleMode(boolean z) {
        this.isSingleMode = z;
    }

    public boolean isSingleModel() {
        return this.isSingleMode;
    }

    public boolean getInnerChange() {
        return this.mInnerChange;
    }

    public void setInnerChange(boolean z) {
        this.mInnerChange = z;
    }

    public Bitmap getIcon() {
        return this.icon;
    }

    public void setIcon(Bitmap bitmap) {
        this.icon = bitmap;
    }

    @Override // org.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        return this.icon;
    }

    public String getRootPath() {
        return this.rootPath;
    }

    public void setRootPath(String str) {
        this.rootPath = str;
    }

    public int getPhotoAmount() {
        return this.photoAmount;
    }

    public void setPhotoAmount(int i) {
        this.photoAmount = i;
    }

    public String getPuzzlePath() {
        return this.puzzlePath;
    }

    public void setPuzzlePath(String str) {
        this.puzzlePath = str;
    }

    public String getBackgroundPath() {
        return this.backgroundPath;
    }

    public void setBackgroundPath(String str) {
        this.backgroundPath = str;
    }

    @Override // org.picspool.lib.resource.DMWBImageRes
    public DMWBImageRes.FitType getFitType() {
        return this.fitType;
    }

    public void setFitType(DMWBImageRes.FitType fitType) {
        this.fitType = fitType;
    }

    public String getResId() {
        return this.mResId;
    }

    public void setResId(String str) {
        this.mResId = str;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public void setVersion(int i) {
        this.mVersion = i;
    }

    public String getIconPath() {
        return this.mIconPath;
    }

    public void setIconPath(String str) {
        this.mIconPath = str;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void setWidth(int i) {
        this.mWidth = i;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public void setHeight(int i) {
        this.mHeight = i;
    }

    public boolean getParseFailed() {
        return this.mParseFailed;
    }

    public void setParseFailed(boolean z) {
        this.mParseFailed = z;
    }

    public Bitmap getBackgroundBitmap(Context context) {
        if (TextUtils.isEmpty(this.backgroundPath)) {
            return null;
        }
        return DMBitmapUtil.getImageFromAssetsFile(context.getResources(), this.backgroundPath, 2);
    }
}
