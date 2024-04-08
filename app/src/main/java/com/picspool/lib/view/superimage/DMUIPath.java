package com.picspool.lib.view.superimage;

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

/* loaded from: classes3.dex */
public class DMUIPath {
    private int designViewHeight;
    private int designViewInnerHeight;
    private int designViewInnerWidth;
    private int designViewWidth;
    private float[] floatInnerValues;
    private float[] floatValues;
    private float innerPercent;
    private String innerPoints;
    private String[] innerValues;
    private boolean innered;
    private float mScaleX;
    private float mScaleY;
    private String points;
    private pathType type;
    private String[] values;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum pathType {
        NONE,
        ARC,
        CIRCLE,
        OVAL,
        ROUNDRECT,
        LINE
    }

    public DMUIPath(String str, int i, int i2) {
        this.points = "";
        this.innerPoints = "";
        this.designViewWidth = -1;
        this.designViewHeight = -1;
        this.designViewInnerWidth = -1;
        this.designViewInnerHeight = -1;
        this.innered = false;
        this.innerPercent = 0.0f;
        this.type = pathType.NONE;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.points = str;
        this.designViewWidth = i;
        this.designViewHeight = i2;
        init();
    }

    public DMUIPath(String str, String str2, int i, int i2, int i3, int i4) {
        this.points = "";
        this.innerPoints = "";
        this.designViewWidth = -1;
        this.designViewHeight = -1;
        this.designViewInnerWidth = -1;
        this.designViewInnerHeight = -1;
        this.innered = false;
        this.innerPercent = 0.0f;
        this.type = pathType.NONE;
        this.mScaleX = 1.0f;
        this.mScaleY = 1.0f;
        this.points = str;
        this.designViewWidth = i;
        this.designViewHeight = i2;
        this.designViewInnerWidth = i3;
        this.designViewInnerHeight = i4;
        this.innerPoints = str2;
        init();
        initInner();
    }

    public void init() {
        String str = this.points;
        if (str == null || str.equals("")) {
            return;
        }
        if (this.points.startsWith("A:")) {
            this.type = pathType.ARC;
            this.points = this.points.replace("A:", "");
        } else if (this.points.startsWith("C:")) {
            this.type = pathType.CIRCLE;
            this.points = this.points.replace("C:", "");
        } else if (this.points.startsWith("O:")) {
            this.type = pathType.OVAL;
            this.points = this.points.replace("O:", "");
        } else if (this.points.startsWith("R:")) {
            this.type = pathType.ROUNDRECT;
            this.points = this.points.replace("R:", "");
        } else if (this.points.startsWith("L:")) {
            this.type = pathType.LINE;
            this.points = this.points.replace("L:", "");
        }
        String replace = this.points.replace("{", "");
        this.points = replace;
        String replace2 = replace.replace("}", "");
        this.points = replace2;
        String[] split = replace2.split(",");
        this.values = split;
        this.floatValues = new float[split.length];
        int i = 0;
        while (true) {
            String[] strArr = this.values;
            if (i >= strArr.length) {
                return;
            }
            this.floatValues[i] = Float.parseFloat(strArr[i]);
            i++;
        }
    }

    public void initInner() {
        String str = this.innerPoints;
        if (str == null || str.equals("")) {
            return;
        }
        if (this.innerPoints.startsWith("A:")) {
            this.innerPoints = this.innerPoints.replace("A:", "");
        } else if (this.innerPoints.startsWith("C:")) {
            this.innerPoints = this.innerPoints.replace("C:", "");
        } else if (this.innerPoints.startsWith("O:")) {
            this.innerPoints = this.innerPoints.replace("O:", "");
        } else if (this.innerPoints.startsWith("R:")) {
            this.innerPoints = this.innerPoints.replace("R:", "");
        } else if (this.innerPoints.startsWith("L:")) {
            this.innerPoints = this.innerPoints.replace("L:", "");
        }
        String replace = this.innerPoints.replace("{", "");
        this.innerPoints = replace;
        String replace2 = replace.replace("}", "");
        this.innerPoints = replace2;
        String[] split = replace2.split(",");
        this.innerValues = split;
        this.floatInnerValues = new float[split.length];
        int i = 0;
        while (true) {
            String[] strArr = this.innerValues;
            if (i >= strArr.length) {
                return;
            }
            this.floatInnerValues[i] = Float.parseFloat(strArr[i]);
            i++;
        }
    }

    public void setScale(float f) {
        this.mScaleX = f;
        this.mScaleY = f;
    }

    public void setScale(float f, float f2) {
        this.mScaleX = f;
        this.mScaleY = f2;
    }

    public void setInnerPercent(float f) {
        this.innerPercent = f;
    }

    public Path getTestPath(Rect rect) {
        Path path = new Path();
        path.moveTo(rect.exactCenterX(), rect.top);
        path.lineTo(rect.left, rect.bottom);
        path.lineTo(rect.right, rect.bottom);
        path.lineTo(rect.exactCenterX(), rect.top);
        path.close();
        return path;
    }

    public Path getPath(Rect rect) {
        int i;
        int i2;
        int i3;
        int i4;
        Path path = new Path();
        if (rect == null) {
            return path;
        }
        if (this.points.equals("test")) {
            return getTestPath(rect);
        }
        Path path2 = new Path();
        path2.addRect(new RectF(rect), Path.Direction.CW);
        path2.close();
        if (this.values.length == 0) {
            return path2;
        }
        int i5 = rect.right - rect.left;
        int i6 = rect.bottom - rect.top;
        float[] fArr = (float[]) this.floatValues.clone();
        if (this.innered) {
            if (this.floatValues.length != this.floatInnerValues.length) {
                return path2;
            }
            for (int i7 = 0; i7 < this.values.length; i7++) {
                float f = this.floatValues[i7];
                fArr[i7] = f - ((f - this.floatInnerValues[i7]) * this.innerPercent);
            }
        }
        try {
            pathType pathtype = pathType.ARC;
            if (this.type == pathType.CIRCLE) {
                if (this.values.length < 3) {
                    return path2;
                }
                path.addCircle(fArr[0] * this.mScaleX, fArr[1] * this.mScaleY, fArr[2] * this.mScaleX, Path.Direction.CW);
            }
            if (this.type == pathType.OVAL) {
                if (this.values.length < 4) {
                    return path2;
                }
                RectF rectF = new RectF();
                rectF.left = fArr[0];
                rectF.top = fArr[1];
                rectF.right = rectF.left + fArr[2];
                rectF.bottom = rectF.top + fArr[3];
                path.addOval(rectF, Path.Direction.CW);
            }
            if (this.type == pathType.ROUNDRECT) {
                if (this.values.length < 8) {
                    return path2;
                }
                path.addRoundRect(new RectF(rect), new float[]{fArr[0] * this.mScaleX, fArr[1] * this.mScaleY, fArr[2] * this.mScaleX, fArr[3] * this.mScaleY, fArr[4] * this.mScaleX, fArr[5] * this.mScaleY, fArr[6] * this.mScaleX, fArr[7] * this.mScaleY}, Path.Direction.CW);
            }
            if (this.type == pathType.LINE) {
                if (this.values.length < 4) {
                    return path2;
                }
                int i8 = ((int) ((fArr[0] * this.mScaleX) + 0.9f)) + rect.left;
                int i9 = ((int) ((fArr[1] * this.mScaleY) + 0.9f)) + rect.top;
                int i10 = -1;
                if (this.designViewWidth != -1 && this.designViewHeight != -1) {
                    if (this.designViewInnerWidth != -1 && this.designViewInnerHeight != -1) {
                        float f2 = this.floatValues[0] / this.designViewWidth;
                        i8 = ((int) (((f2 - ((f2 - (this.floatInnerValues[0] / this.designViewInnerWidth)) * this.innerPercent)) * i5) + 0.4d)) + rect.left;
                        float f3 = this.floatValues[1] / this.designViewHeight;
                        i3 = (int) (((f3 - ((f3 - (this.floatInnerValues[1] / this.designViewInnerHeight)) * this.innerPercent)) * i6) + 0.4d);
                        i4 = rect.top;
                    } else {
                        i8 = ((int) (((this.floatValues[0] / this.designViewWidth) * i5) + 0.4d)) + rect.left;
                        i3 = (int) (((this.floatValues[1] / this.designViewHeight) * i6) + 0.4d);
                        i4 = rect.top;
                    }
                    i9 = i3 + i4;
                }
                path.moveTo(i8, i9);
                int i11 = 2;
                for (int i12 = 1; i11 < fArr.length - i12; i12 = 1) {
                    int i13 = ((int) ((fArr[i11] * this.mScaleX) + 0.9f)) + rect.left;
                    int i14 = i11 + 1;
                    int i15 = ((int) ((fArr[i14] * this.mScaleY) + 0.9f)) + rect.top;
                    if (this.designViewWidth != i10 && this.designViewHeight != i10) {
                        if (this.designViewInnerWidth != i10 && this.designViewInnerHeight != i10) {
                            float f4 = this.floatValues[i11] / this.designViewWidth;
                            i13 = ((int) (((f4 - ((f4 - (this.floatInnerValues[i11] / this.designViewInnerWidth)) * this.innerPercent)) * i5) + 0.4d)) + rect.left;
                            float f5 = this.floatValues[i14] / this.designViewHeight;
                            i = (int) (((f5 - ((f5 - (this.floatInnerValues[i14] / this.designViewInnerHeight)) * this.innerPercent)) * i6) + 0.4d);
                            i2 = rect.top;
                        } else {
                            i13 = ((int) (((this.floatValues[i11] / this.designViewWidth) * i5) + 0.4d)) + rect.left;
                            i = (int) (((this.floatValues[i14] / this.designViewHeight) * i6) + 0.4d);
                            i2 = rect.top;
                        }
                        i15 = i2 + i;
                    }
                    path.lineTo(i13, i15);
                    i11 += 2;
                    i10 = -1;
                }
            }
            path.close();
            return path;
        } catch (Exception unused) {
            return path2;
        }
    }
}
