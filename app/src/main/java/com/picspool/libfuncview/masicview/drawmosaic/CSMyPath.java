package com.picspool.libfuncview.masicview.drawmosaic;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Rect;
import com.picspool.libfuncview.masicview.CSDrawMosaic;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CSMyPath {
    private CSDrawMosaic.Mode mode;
    private int paintWidth;
    private Path path;
    private List<Bitmap> bitmapList = new ArrayList();
    private List<Float> angleList = new ArrayList();
    private List<Rect> rectList = new ArrayList();

    public CSMyPath() {
        addAngle(Float.valueOf(0.0f));
    }

    public void addBitmaps(Bitmap[] bitmapArr) {
        if (bitmapArr == null || bitmapArr.length <= 0) {
            return;
        }
        for (Bitmap bitmap : bitmapArr) {
            this.bitmapList.add(bitmap);
        }
    }

    public void addBitmap(Bitmap bitmap) {
        this.bitmapList.add(bitmap);
    }

    public void addRect(Rect rect) {
        if (rect == null) {
            return;
        }
        this.rectList.add(rect);
    }

    public void addAngle(Float f) {
        if (f == null) {
            return;
        }
        this.angleList.add(f);
    }

    public List<Float> getAngleList() {
        return this.angleList;
    }

    public List<Bitmap> getBitmapList() {
        return this.bitmapList;
    }

    public List<Rect> getRectList() {
        return this.rectList;
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

    public void setMode(CSDrawMosaic.Mode mode) {
        this.mode = mode;
    }

    public void setPaintWidth(int i) {
        this.paintWidth = i;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public Float getAngle() {
        return getAngleList().get(getAngleList().size() - 1);
    }

    public Rect getRect() {
        return getRectList().get(getRectList().size() - 1);
    }
}
