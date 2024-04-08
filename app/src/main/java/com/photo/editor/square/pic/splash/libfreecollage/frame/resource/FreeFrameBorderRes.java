package com.photo.editor.square.pic.splash.libfreecollage.frame.resource;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import java.util.List;
import com.picspool.lib.border.res.DMWBBorderRes;

/* loaded from: classes2.dex */
public class FreeFrameBorderRes extends DMWBBorderRes {
    private String bgImageResName;
    private DMWBBorderRes.BorderType borderType;
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
    private DMWBBorderRes.BackgroundType backgroundType = DMWBBorderRes.BackgroundType.NORMAL;

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public GradientDrawable.Orientation getGradientOrientation() {
        return this.orientation;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setOrientation(GradientDrawable.Orientation orientation) {
        this.orientation = orientation;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public int getDefaultColor() {
        return this.defaultColor;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setDefaultColor(int i) {
        this.defaultColor = i;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public String getLeftUri() {
        return this.leftUri;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setLeftUri(String str) {
        this.leftUri = str;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public String getRightUri() {
        return this.rightUri;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setRightUri(String str) {
        this.rightUri = str;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public String getTopUri() {
        return this.topUri;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setTopUri(String str) {
        this.topUri = str;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public String getBottomUri() {
        return this.bottomUri;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setBottomUri(String str) {
        this.bottomUri = str;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public String getLeftTopCornorUri() {
        return this.leftTopCornorUri;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setLeftTopCornorUri(String str) {
        this.leftTopCornorUri = str;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public String getLeftBottomCornorUri() {
        return this.leftBottomCornorUri;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setLeftBottomCornorUri(String str) {
        this.leftBottomCornorUri = str;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public String getRightTopCornorUri() {
        return this.rightTopCornorUri;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setRightTopCornorUri(String str) {
        this.rightTopCornorUri = str;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public String getRightBottomCornorUri() {
        return this.rightBottomCornorUri;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setRightBottomCornorUri(String str) {
        this.rightBottomCornorUri = str;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public DMWBBorderRes.BorderType getBorderType() {
        return this.borderType;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setBorderType(DMWBBorderRes.BorderType borderType) {
        this.borderType = borderType;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public DMWBBorderRes.BackgroundType getBackgroundType() {
        return this.backgroundType;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setBackgroundType(DMWBBorderRes.BackgroundType backgroundType) {
        this.backgroundType = backgroundType;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public List<Integer> getGradientColorArray() {
        return this.gradientColorArray;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setGradientColorArray(List<Integer> list) {
        this.gradientColorArray = list;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public Drawable getGradientDrawable() {
        if (this.backgroundType == DMWBBorderRes.BackgroundType.NORMAL) {
            int i = this.defaultColor;
            GradientDrawable gradientDrawable = new GradientDrawable(this.orientation, new int[]{i, i});
            gradientDrawable.setGradientType(0);
            return gradientDrawable;
        } else if (this.backgroundType == DMWBBorderRes.BackgroundType.GRADIENT) {
            GradientDrawable gradientDrawable2 = new GradientDrawable(this.orientation, new int[]{this.gradientColorArray.get(0).intValue(), this.gradientColorArray.get(1).intValue()});
            gradientDrawable2.setGradientType(0);
            return gradientDrawable2;
        } else {
            return null;
        }
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public String getBgImageResName() {
        return this.bgImageResName;
    }

    @Override // com.picspool.lib.border.res.DMWBBorderRes
    public void setBgAssetPath(String str) {
        this.bgImageResName = str;
    }
}
