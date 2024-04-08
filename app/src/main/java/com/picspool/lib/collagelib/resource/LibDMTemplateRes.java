package com.picspool.lib.collagelib.resource;

import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo;
import com.picspool.lib.p017io.DMFileLocation;
import com.picspool.lib.resource.DMWBImageRes;

/* loaded from: classes3.dex */
public class LibDMTemplateRes extends DMWBImageRes {
    private String image;
    private boolean isWithBlurBackground;
    int mAspectRatio;
    List<LibDMCollageInfo> mCollageinfo;
    int mFrameWidth;
    private int mHeight;
    private String mIconPath;
    public boolean mInnerChange;
    private int mInnerFrameWidth;
    private DMFileLocation mLocation;
    private String mName;
    private int mOutFrameWidth;
    private boolean mParseFailed;
    private int mPhotoAmount;
    private String mPuzzlePath;
    int mRadius;
    private String mResId;
    private String mRootPath;
    private int mVersion;
    private int mWidth;
    int mindex;

    public LibDMTemplateRes() {
        this.mRadius = 0;
        this.mAspectRatio = 1;
        this.mindex = 0;
        this.mCollageinfo = null;
        this.mFrameWidth = 12;
        this.mOutFrameWidth = 0;
        this.mInnerFrameWidth = 0;
        this.isWithBlurBackground = false;
        this.mLocation = DMFileLocation.ASSERT;
        this.mRootPath = "";
        this.mParseFailed = false;
        this.mResId = "";
        this.mVersion = 1;
        this.mName = "";
        this.mIconPath = "";
        this.mWidth = 0;
        this.mHeight = 0;
        this.mPhotoAmount = 1;
        this.mPuzzlePath = "";
        this.mInnerChange = true;
    }

    public LibDMTemplateRes(int i, int i2, int i3, List<LibDMCollageInfo> list) {
        this.mRadius = 0;
        this.mAspectRatio = 1;
        this.mindex = 0;
        this.mCollageinfo = null;
        this.mFrameWidth = 12;
        this.mOutFrameWidth = 0;
        this.mInnerFrameWidth = 0;
        this.isWithBlurBackground = false;
        this.mLocation = DMFileLocation.ASSERT;
        this.mRootPath = "";
        this.mParseFailed = false;
        this.mResId = "";
        this.mVersion = 1;
        this.mName = "";
        this.mIconPath = "";
        this.mWidth = 0;
        this.mHeight = 0;
        this.mPhotoAmount = 1;
        this.mPuzzlePath = "";
        this.mInnerChange = true;
        this.mindex = i;
        this.mRadius = i2;
        this.mAspectRatio = i3;
        this.mCollageinfo = list;
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
        if (this.mCollageinfo == null) {
            this.mCollageinfo = new ArrayList();
        }
        if (this.mCollageinfo.size() == 0 && !getPuzzlePath().equals("")) {
            try {
                DMPuzzleParser.parse(getResources().getAssets().open(getPuzzlePath()), this);
            } catch (Exception unused) {
            }
        }
        return this.mCollageinfo;
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
        this.mCollageinfo = list;
    }

    public void setRoundRadius(int i) {
        this.mRadius = i;
    }

    public int getOutFrameWidth() {
        return this.mOutFrameWidth;
    }

    public void setOutFrameWidth(int i) {
        if (this.mCollageinfo != null) {
            for (int i2 = 0; i2 < this.mCollageinfo.size(); i2++) {
                this.mCollageinfo.get(i2).setOutFrameWidth(i);
            }
        }
        this.mOutFrameWidth = i;
    }

    public int getInnerFrameWidth() {
        return this.mInnerFrameWidth;
    }

    public void setInnerFrameWidth(int i) {
        if (this.mCollageinfo != null) {
            for (int i2 = 0; i2 < this.mCollageinfo.size(); i2++) {
                this.mCollageinfo.get(i2).setInnerFrameWidth(i);
            }
        }
        this.mInnerFrameWidth = i;
    }

    public boolean isWithBlurBackground() {
        return this.isWithBlurBackground;
    }

    public void setWithBlurBackground(boolean z) {
        this.isWithBlurBackground = z;
    }

    public DMFileLocation getLocation() {
        return this.mLocation;
    }

    public void setLocation(DMFileLocation dMFileLocation) {
        this.mLocation = dMFileLocation;
    }

    public String getRootPath() {
        return this.mRootPath;
    }

    public void setRootPath(String str) {
        this.mRootPath = str;
    }

    public boolean getParseFailed() {
        return this.mParseFailed;
    }

    public void setParseFailed(boolean z) {
        this.mParseFailed = z;
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

    @Override // com.picspool.lib.resource.DMWBRes
    public String getName() {
        return this.mName;
    }

    @Override // com.picspool.lib.resource.DMWBRes
    public void setName(String str) {
        this.mName = str;
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

    public int getPhotoAmount() {
        return this.mPhotoAmount;
    }

    public void setPhotoAmount(int i) {
        this.mPhotoAmount = i;
    }

    public String getPuzzlePath() {
        return this.mPuzzlePath;
    }

    public void setPuzzlePath(String str) {
        this.mPuzzlePath = str;
    }

    public boolean getInnerChange() {
        return this.mInnerChange;
    }

    public void setInnerChange(boolean z) {
        this.mInnerChange = z;
    }
}
