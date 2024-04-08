package com.picspool.libfuncview.masicview;

import com.picspool.libfuncview.masicview.CSDrawMosaic;
import com.picspool.lib.resource.DMWBImageRes;

/* loaded from: classes.dex */
public class CSMosaicRes extends DMWBImageRes {
    private int bmp_height;
    private int bmp_width;
    private int bmps_distance;
    private String[] bmps_filename;
    private CSDrawMosaic.Mode mMosaicMode;
    private boolean rotatemode;

    public CSDrawMosaic.Mode getmMosaicMode() {
        return this.mMosaicMode;
    }

    public void setmMosaicMode(CSDrawMosaic.Mode mode) {
        this.mMosaicMode = mode;
    }

    public int getBmp_width() {
        return this.bmp_width;
    }

    public void setBmp_width(int i) {
        this.bmp_width = i;
    }

    public int getBmp_height() {
        return this.bmp_height;
    }

    public void setBmp_height(int i) {
        this.bmp_height = i;
    }

    public int getBmps_distance() {
        return this.bmps_distance;
    }

    public void setBmps_distance(int i) {
        this.bmps_distance = i;
    }

    public boolean isRotatemode() {
        return this.rotatemode;
    }

    public void setRotatemode(boolean z) {
        this.rotatemode = z;
    }

    public String[] getBmps_filename() {
        return this.bmps_filename;
    }

    public void setBmps_filename(String[] strArr) {
        this.bmps_filename = strArr;
    }
}
