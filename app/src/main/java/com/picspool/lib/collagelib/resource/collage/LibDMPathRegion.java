package com.picspool.lib.collagelib.resource.collage;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Region;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class LibDMPathRegion {
    private List<Path> clipPath = new ArrayList();
    private List<Region.Op> regionOpList = new ArrayList();

    public void addClipPath(Path path, Region.Op op) {
        this.clipPath.add(path);
        this.regionOpList.add(op);
    }

    public List<Path> getClipPath() {
        return this.clipPath;
    }

    public List<Region.Op> getClipRegionOp() {
        return this.regionOpList;
    }

    public static LibDMPathRegion clonePathRegion(LibDMPathRegion libDMPathRegion, float f) {
        LibDMPathRegion libDMPathRegion2 = new LibDMPathRegion();
        if (libDMPathRegion == null) {
            return null;
        }
        List<Path> clipPath = libDMPathRegion.getClipPath();
        List<Region.Op> clipRegionOp = libDMPathRegion.getClipRegionOp();
        Matrix matrix = new Matrix();
        matrix.setScale(f, f);
        for (int i = 0; i < clipPath.size(); i++) {
            Path path = new Path();
            path.addPath(clipPath.get(i), matrix);
            libDMPathRegion2.addClipPath(path, clipRegionOp.get(i));
        }
        return libDMPathRegion2;
    }
}
