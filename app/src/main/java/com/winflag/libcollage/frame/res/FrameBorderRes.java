package com.winflag.libcollage.frame.res;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.picspool.lib.resource.DMWBImageRes;

import java.util.List;


/* loaded from: classes.dex */
public class FrameBorderRes extends DMWBImageRes {
    private String bgImageResName;
    private BorderType borderType;
    private String bottomUri;
    private int defaultColor;
    private List<Integer> gradientColorArray;
    private String leftBottomCornorUri;
    private String leftTopCornorUri;
    private String leftUri;
    private String rightBottomCornorUri;
    private String rightTopCornorUri;
    private String rightUri;
    private String topUri;
    private GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
    private BackgroundType backgroundType = BackgroundType.NORMAL;

    /* loaded from: classes.dex */
    public enum BackgroundType {
        NORMAL,
        GRADIENT,
        BGIMAGE
    }

    /* loaded from: classes.dex */
    public enum BorderType {
        IMAGE,
        NINE
    }

    public GradientDrawable.Orientation getGradientOrientation() {
        return this.orientation;
    }

    public void setOrientation(GradientDrawable.Orientation orientation) {
        this.orientation = orientation;
    }

    public int getDefaultColor() {
        return this.defaultColor;
    }

    public void setDefaultColor(int i) {
        this.defaultColor = i;
    }

    public String getLeftUri() {
        return this.leftUri;
    }

    public void setLeftUri(String str) {
        this.leftUri = str;
    }

    public String getRightUri() {
        return this.rightUri;
    }

    public void setRightUri(String str) {
        this.rightUri = str;
    }

    public String getTopUri() {
        return this.topUri;
    }

    public void setTopUri(String str) {
        this.topUri = str;
    }

    public String getBottomUri() {
        return this.bottomUri;
    }

    public void setBottomUri(String str) {
        this.bottomUri = str;
    }

    public String getLeftTopCornorUri() {
        return this.leftTopCornorUri;
    }

    public void setLeftTopCornorUri(String str) {
        this.leftTopCornorUri = str;
    }

    public String getLeftBottomCornorUri() {
        return this.leftBottomCornorUri;
    }

    public void setLeftBottomCornorUri(String str) {
        this.leftBottomCornorUri = str;
    }

    public String getRightTopCornorUri() {
        return this.rightTopCornorUri;
    }

    public void setRightTopCornorUri(String str) {
        this.rightTopCornorUri = str;
    }

    public String getRightBottomCornorUri() {
        return this.rightBottomCornorUri;
    }

    public void setRightBottomCornorUri(String str) {
        this.rightBottomCornorUri = str;
    }

    public BorderType getBorderType() {
        return this.borderType;
    }

    public void setBorderType(BorderType borderType) {
        this.borderType = borderType;
    }

    public BackgroundType getBackgroundType() {
        return this.backgroundType;
    }

    public void setBackgroundType(BackgroundType backgroundType) {
        this.backgroundType = backgroundType;
    }

    public List<Integer> getGradientColorArray() {
        return this.gradientColorArray;
    }

    public void setGradientColorArray(List<Integer> list) {
        this.gradientColorArray = list;
    }

    public Drawable getGradientDrawable() {
        if (this.backgroundType == BackgroundType.NORMAL) {
            int i = this.defaultColor;
            GradientDrawable gradientDrawable = new GradientDrawable(this.orientation, new int[]{i, i});
            gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            return gradientDrawable;
        } else if (this.backgroundType == BackgroundType.GRADIENT) {
            GradientDrawable gradientDrawable2 = new GradientDrawable(this.orientation, new int[]{this.gradientColorArray.get(0).intValue(), this.gradientColorArray.get(1).intValue()});
            gradientDrawable2.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            return gradientDrawable2;
        } else {
            return null;
        }
    }

    public String getBgImageResName() {
        return this.bgImageResName;
    }

    public void setBgAssetPath(String str) {
        this.bgImageResName = str;
    }

    @Override // org.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        return super.getIconBitmap();
    }
}
