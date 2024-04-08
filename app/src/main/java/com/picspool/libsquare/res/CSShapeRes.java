package com.picspool.libsquare.res;

import com.picspool.lib.resource.DMWBImageRes;

/* loaded from: classes.dex */
public class CSShapeRes extends DMWBImageRes {
    private ShapeMode mode;

    /* loaded from: classes.dex */
    public enum ShapeMode {
        TRANSPARENT,
        OPAQUE
    }

    public ShapeMode getShapeMode() {
        return this.mode;
    }

    public void setShapeMode(ShapeMode shapeMode) {
        this.mode = shapeMode;
    }
}
