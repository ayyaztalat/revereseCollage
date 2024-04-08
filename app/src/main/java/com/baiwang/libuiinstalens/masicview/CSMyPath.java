package com.baiwang.libuiinstalens.masicview;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Rect;
import com.baiwang.libuiinstalens.masicview.CSDrawMosaic;
import java.util.List;

/* loaded from: classes.dex */
public class CSMyPath {
    private List<Float> angleLists;
    private List<Bitmap> bitmap;
    private CSDrawMosaic.Mode mode;
    private int paintWidth;
    private Path path;
    private List<Rect> rectList;

    public List<Rect> getRectList() {
        return this.rectList;
    }

    public void setRectList(List<Rect> list) {
        this.rectList = list;
    }

    public void setAngleLists(List<Float> list) {
        this.angleLists = list;
    }

    public List<Float> getAngleLists() {
        return this.angleLists;
    }

    public List<Bitmap> getBitmap() {
        return this.bitmap;
    }

    public int getPaintWidth() {
        return this.paintWidth;
    }

    public CSDrawMosaic.Mode getMode() {
        return this.mode;
    }

    public Path getPath() {
        return this.path;
    }

    public void setBitmap(List<Bitmap> list) {
        this.bitmap = list;
    }

    public void setMode(CSDrawMosaic.Mode mode) {
        this.mode = mode;
    }

    public void setPaintWidth(int i) {
        this.paintWidth = i;
    }

    public void setPath(Path path) {
        this.path = path;
    }
}
