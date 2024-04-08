package com.winflag.libsquare.res;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.picspool.lib.resource.DMWBRes;


/* loaded from: classes3.dex */
public class CSGradientRes extends DMWBRes {
    int mGraType = 0;
    GradientDrawable.Orientation mOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
    int[] mColors = new int[2];
    int mOrientate = 0;
    int mOriginalOrientation = 0;

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

    public int getOriention() {
        return this.mOrientate;
    }

    public void setOrientation(int i) {
        this.mOrientate = i;
    }

    public int getOriginalOriention() {
        return this.mOriginalOrientation;
    }

    public void setOriginalOrientation(int i) {
        this.mOriginalOrientation = i;
    }

    public int[] getColors() {
        return this.mColors;
    }

    public void setColors(int[] iArr) {
        this.mColors = iArr;
    }

    public GradientDrawable getGradientDrawable() {
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.TR_BL;
        switch (this.mOrientate) {
            case 0:
                orientation = GradientDrawable.Orientation.TR_BL;
                break;
            case 1:
                orientation = GradientDrawable.Orientation.TOP_BOTTOM;
                break;
            case 2:
                orientation = GradientDrawable.Orientation.TL_BR;
                break;
            case 3:
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
                break;
            case 4:
                orientation = GradientDrawable.Orientation.BL_TR;
                break;
            case 5:
                orientation = GradientDrawable.Orientation.BOTTOM_TOP;
                break;
            case 6:
                orientation = GradientDrawable.Orientation.BR_TL;
                break;
            case 7:
                orientation = GradientDrawable.Orientation.RIGHT_LEFT;
                break;
        }
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, this.mColors);
        gradientDrawable.setGradientType(this.mGraType);
        return gradientDrawable;
    }

    @Override // org.picspool.lib.resource.DMWBRes
    public Bitmap getIconBitmap() {
        return drawableToBitmap(getGradientDrawable());
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap createBitmap = Bitmap.createBitmap(20, 20, drawable.getOpacity() != PixelFormat.UNKNOWN ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, 20, 20);
        drawable.draw(canvas);
        return createBitmap;
    }
}
