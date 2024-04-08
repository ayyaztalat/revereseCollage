package com.magic.video.editor.effect.cut.utils.base;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import com.magic.video.editor.effect.cut.utils.CSBitmapUtils;

/* loaded from: classes2.dex */
public abstract class CSStickerRes {
    protected Context context;
    private Bitmap iconBitmap;
    private String iconFileName;
    private int iconID;
    private LocationType iconType;
    private String managerName;
    private String name;
    private String showText;
    protected Boolean asyncIcon = false;
    private boolean isNew = false;
    private boolean isShowText = false;
    private int textColor = 0;
    private int textBgColor = 0;
    private boolean isSetTextBgColor = false;
    private boolean isCircle = false;

    /* loaded from: classes2.dex */
    public enum LocationType {
        RES,
        ASSERT,
        FILTERED,
        ONLINE,
        CACHE
    }

    public void getAsyncIconBitmap(CSAsyncPostIconListener cSAsyncPostIconListener) {
    }

    public String getType() {
        return "TRes";
    }

    public void setIsShowText(boolean z) {
        this.isShowText = z;
    }

    public Boolean getIsShowText() {
        return Boolean.valueOf(this.isShowText);
    }

    public void setShowText(String str) {
        this.showText = str;
    }

    public String getShowText() {
        return this.showText;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextBgColor(int i) {
        setTextBgColor(i, true);
    }

    public void setTextBgColor(int i, boolean z) {
        this.textBgColor = i;
        this.isSetTextBgColor = z;
    }

    public boolean isSetTextBgColor() {
        return this.isSetTextBgColor;
    }

    public int getTextBgColor() {
        return this.textBgColor;
    }

    public void setTextColor(int i) {
        this.textColor = i;
    }

    public boolean isCircle() {
        return this.isCircle;
    }

    public void setCircle(boolean z) {
        this.isCircle = z;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Resources getResources() {
        Context context = this.context;
        if (context != null) {
            return context.getResources();
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getIconFileName() {
        return this.iconFileName;
    }

    public void setIconFileName(String str) {
        this.iconFileName = str;
    }

    public int getIconID() {
        return this.iconID;
    }

    public void setIconID(int i) {
        this.iconID = i;
    }

    public LocationType getIconType() {
        return this.iconType;
    }

    public void setIconType(LocationType locationType) {
        this.iconType = locationType;
    }

    public Boolean getAsyncIcon() {
        return this.asyncIcon;
    }

    public Bitmap getIconBitmap() {
        if (this.iconFileName == null) {
            return null;
        }
        if (this.iconType == LocationType.RES) {
            return CSBitmapUtils.getImageFromResourceFile(getResources(), this.iconID);
        }
        if (this.iconType == LocationType.ASSERT) {
            return CSBitmapUtils.getImageFromAssetsFile(getResources(), this.iconFileName);
        }
        return this.iconBitmap;
    }

    public boolean getIsNewValue() {
        return this.isNew;
    }

    public void setIsNewValue(boolean z) {
        this.isNew = z;
    }

    public String getManagerName() {
        return this.managerName;
    }

    public void setManagerName(String str) {
        this.managerName = str;
    }
}
