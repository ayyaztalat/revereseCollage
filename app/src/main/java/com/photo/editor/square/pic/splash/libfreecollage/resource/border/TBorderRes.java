package com.photo.editor.square.pic.splash.libfreecollage.resource.border;

import android.content.Context;
import android.graphics.Bitmap;
import com.picspool.lib.border.res.DMWBBorderRes;

/* loaded from: classes2.dex */
public class TBorderRes extends TResInfo {
    private DMWBBorderRes.BorderType borderType;
    private String bottomUri;
    private Context context;
    private int innerPx;
    private int innerPx2;
    private int innerPy;
    private int innerPy2;
    private String leftBottomCornorUri;
    private String leftTopCornorUri;
    private String leftUri;
    private LineDrawMode lineDrawMode;
    private int mapDestX;
    private int mapDestY;
    private int mapSize;
    private String rightBottomCornorUri;
    private String rightTopCornorUri;
    private String rightUri;
    private String topUri;

    /* loaded from: classes2.dex */
    public enum LineDrawMode {
        SCALE,
        TILE
    }

    public TBorderRes(Context context) {
        this.context = context;
    }

    public Bitmap getBottomBitmap() {
        String str = this.bottomUri;
        if (str != null) {
            return readFromAssert(this.context, str);
        }
        return null;
    }

    public Bitmap getTopBitmap() {
        String str = this.topUri;
        if (str != null) {
            return readFromAssert(this.context, str);
        }
        return null;
    }

    public Bitmap getLeftBitmap() {
        String str = this.leftUri;
        if (str != null) {
            return readFromAssert(this.context, str);
        }
        return null;
    }

    public Bitmap getRightBitmap() {
        String str = this.rightUri;
        if (str != null) {
            return readFromAssert(this.context, str);
        }
        return null;
    }

    public Bitmap getLeftTopCornorBitmap() {
        String str = this.leftTopCornorUri;
        if (str != null) {
            return readFromAssert(this.context, str);
        }
        return null;
    }

    public Bitmap getLeftBottomCornorBitmap() {
        String str = this.leftBottomCornorUri;
        if (str != null) {
            return readFromAssert(this.context, str);
        }
        return null;
    }

    public Bitmap getRightTopCornorBitmap() {
        String str = this.rightTopCornorUri;
        if (str != null) {
            return readFromAssert(this.context, str);
        }
        return null;
    }

    public Bitmap getRightBottomCornorBitmap() {
        String str = this.rightBottomCornorUri;
        if (str != null) {
            return readFromAssert(this.context, str);
        }
        return null;
    }

    public Bitmap getBitmapFromAsset(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        return readFromAssert(this.context, str);
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

    public String getBottomUri() {
        return this.bottomUri;
    }

    public void setBottomUri(String str) {
        this.bottomUri = str;
    }

    public int getInnerPx() {
        return this.innerPx;
    }

    public void setInnerPx(int i) {
        this.innerPx = i;
    }

    public int getInnerPy() {
        return this.innerPy;
    }

    public void setInnerPy(int i) {
        this.innerPy = i;
    }

    public LineDrawMode getLineDrawMode() {
        if (this.lineDrawMode == null) {
            this.lineDrawMode = LineDrawMode.SCALE;
        }
        return this.lineDrawMode;
    }

    public void setLineDrawMode(LineDrawMode lineDrawMode) {
        this.lineDrawMode = lineDrawMode;
    }

    public int getMapDestX() {
        return this.mapDestX;
    }

    public void setMapDestX(int i) {
        this.mapDestX = i;
    }

    public int getMapDestY() {
        return this.mapDestY;
    }

    public void setMapDestY(int i) {
        this.mapDestY = i;
    }

    public int getInnerPx2() {
        return this.innerPx2;
    }

    public void setInnerPx2(int i) {
        this.innerPx2 = i;
    }

    public int getInnerPy2() {
        return this.innerPy2;
    }

    public void setInnerPy2(int i) {
        this.innerPy2 = i;
    }

    public int getMapSize() {
        return this.mapSize;
    }

    public void setMapSize(int i) {
        this.mapSize = i;
    }

    public DMWBBorderRes.BorderType getBorderType() {
        return this.borderType;
    }

    public void setBorderType(DMWBBorderRes.BorderType borderType) {
        this.borderType = borderType;
    }
}
