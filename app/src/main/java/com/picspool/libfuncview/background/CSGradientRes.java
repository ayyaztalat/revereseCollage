package com.picspool.libfuncview.background;

import android.graphics.drawable.GradientDrawable;
import com.picspool.lib.resource.DMWBRes;

/* loaded from: classes.dex */
public class CSGradientRes extends DMWBRes {
    int mGraType = 0;
    GradientDrawable.Orientation mOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
    int[] mColors = new int[2];

    public int getGraType() {
        return this.mGraType;
    }

    public void setGraType(int i) {
        this.mGraType = i;
    }

    public GradientDrawable.Orientation getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(GradientDrawable.Orientation orientation) {
        this.mOrientation = orientation;
    }

    public int[] getColors() {
        return this.mColors;
    }

    public void setColors(int[] iArr) {
        this.mColors = iArr;
    }

    public GradientDrawable getGradientDrawable() {
        GradientDrawable gradientDrawable = new GradientDrawable(this.mOrientation, this.mColors);
        if (this.mGraType == 2) {
            int[] iArr = this.mColors;
            gradientDrawable = new GradientDrawable(this.mOrientation, new int[]{iArr[0], iArr[1], iArr[0]});
        }
        if (this.mGraType == 1) {
            int[] iArr2 = this.mColors;
            GradientDrawable gradientDrawable2 = new GradientDrawable(this.mOrientation, new int[]{iArr2[1], iArr2[0]});
            gradientDrawable2.setGradientRadius(360.0f);
            gradientDrawable = gradientDrawable2;
        }
        gradientDrawable.setGradientType(this.mGraType);
        return gradientDrawable;
    }
}
