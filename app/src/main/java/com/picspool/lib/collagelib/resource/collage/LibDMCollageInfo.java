package com.picspool.lib.collagelib.resource.collage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.util.ArrayList;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapUtil;

/* loaded from: classes3.dex */
public class LibDMCollageInfo {
    private boolean isCanCorner;
    private boolean isCanShadow;
    PointF mCenterPoint;
    private Context mContext;
    private float mInnerFrameWidth;
    private boolean mIsShowFrame;
    private List<Boolean> mLstIsOutLine;
    private String mMaskUri;
    private List<LibDMCollagePoint> mOldpoints;
    private int mOutFrameWidth;
    List<PointF> mVerticaPointFs;
    private final int minPointSpace;
    private int mradius;

    public LibDMCollageInfo() {
        this.mradius = 0;
        this.mOutFrameWidth = 0;
        this.mInnerFrameWidth = 0.0f;
        this.minPointSpace = 200;
        this.isCanShadow = true;
        this.isCanCorner = true;
        this.mLstIsOutLine = new ArrayList();
        this.mVerticaPointFs = null;
        this.mIsShowFrame = false;
    }

    public LibDMCollageInfo(List<LibDMCollagePoint> list, int i, int i2, int i3) {
        this.mradius = 0;
        this.mOutFrameWidth = 0;
        this.mInnerFrameWidth = 0.0f;
        this.minPointSpace = 200;
        this.isCanShadow = true;
        this.isCanCorner = true;
        this.mLstIsOutLine = new ArrayList();
        this.mVerticaPointFs = null;
        this.mIsShowFrame = false;
        this.mOldpoints = list;
        this.mradius = i;
        this.mOutFrameWidth = i2;
        this.mInnerFrameWidth = i3;
    }

    public LibDMCollageInfo(List<LibDMCollagePoint> list, int i, int i2, int i3, String str, Context context) {
        this.mradius = 0;
        this.mOutFrameWidth = 0;
        this.mInnerFrameWidth = 0.0f;
        this.minPointSpace = 200;
        this.isCanShadow = true;
        this.isCanCorner = true;
        this.mLstIsOutLine = new ArrayList();
        this.mVerticaPointFs = null;
        this.mIsShowFrame = false;
        this.mOldpoints = list;
        this.mradius = i;
        this.mOutFrameWidth = i2;
        this.mInnerFrameWidth = i3;
        this.mMaskUri = str;
        this.mContext = context;
    }

    public List<LibDMCollagePoint> getOldpoints() {
        return this.mOldpoints;
    }

    public void setOldpoints(List<LibDMCollagePoint> list) {
        this.mOldpoints = list;
        this.mCenterPoint = getCenterPoint();
    }

    public int getOutFrameWidth() {
        return this.mOutFrameWidth;
    }

    public void setOutFrameWidth(int i) {
        this.mOutFrameWidth = i;
    }

    public int getInnerFrameWidth() {
        return (int) (this.mInnerFrameWidth + 0.5f);
    }

    public void setInnerFrameWidth(int i) {
        this.mInnerFrameWidth = i;
    }

    public int getRadius() {
        return this.mradius;
    }

    public void setRadius(int i) {
        this.mradius = i;
    }

    public boolean getIsCanShadow() {
        return this.isCanShadow;
    }

    public void setIsCanShadow(boolean z) {
        this.isCanShadow = z;
    }

    public boolean getIsCanCorner() {
        return this.isCanCorner;
    }

    public void setIsCanCorner(boolean z) {
        this.isCanCorner = z;
    }

    public String getMaskUri() {
        return this.mMaskUri;
    }

    public void setMaskUri(String str) {
        this.mMaskUri = str;
    }

    public Bitmap getMaskBitmap(Context context) {
        if (this.mMaskUri != null) {
            Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(context.getResources(), this.mMaskUri);
            if (imageFromAssetsFile == null) {
                return DMBitmapUtil.getImageFromSDFile(context, "collagetemplate/" + this.mMaskUri);
            }
            return imageFromAssetsFile;
        }
        return null;
    }

    public List<Point> GetPoints(float f) {
        ArrayList arrayList = new ArrayList();
        List<PointF> GetVerticaScalePoints = GetVerticaScalePoints(f);
        int i = this.mOutFrameWidth;
        if (i > -1) {
            float f2 = (3060 - (i * 2)) / 3060.0f;
            for (int i2 = 0; i2 < GetVerticaScalePoints.size(); i2++) {
                PointF pointF = GetVerticaScalePoints.get(i2);
                Point point = new Point();
                if (pointF.x < this.mCenterPoint.x) {
                    point.x = ((int) ((pointF.x * f2) - 0.5f)) + this.mOutFrameWidth;
                } else {
                    point.x = ((int) ((pointF.x * f2) + 1.91f)) + this.mOutFrameWidth;
                }
                if (pointF.y < this.mCenterPoint.y) {
                    point.y = (int) ((((pointF.y * f2) * f) - 0.5f) + (this.mOutFrameWidth * f));
                } else {
                    point.y = (int) ((pointF.y * f2 * f) + 1.91f + (this.mOutFrameWidth * f));
                }
                if (i2 == GetVerticaScalePoints.size() - 1) {
                    arrayList.add(0, point);
                } else {
                    arrayList.add(point);
                }
            }
        }
        return arrayList;
    }

    public List<PointF> GetVerticaPoints() {
        if (this.mCenterPoint == null) {
            this.mCenterPoint = getCenterPoint();
        }
        ArrayList arrayList = new ArrayList();
        this.mLstIsOutLine.clear();
        int i = 0;
        while (i < this.mOldpoints.size() - 1) {
            int i2 = i + 1;
            PointF verticalPoint = getVerticalPoint(changePointToFPointF(this.mOldpoints.get(i).moriPoint), changePointToFPointF(this.mOldpoints.get(i2).moriPoint), this.mCenterPoint);
            if (this.mOldpoints.get(i).getIsOutRectLinePoint() != 0 && this.mOldpoints.get(i2).getIsOutRectLinePoint() != 0 && this.mOldpoints.get(i).getIsOutRectLinePoint() == this.mOldpoints.get(i2).getIsOutRectLinePoint()) {
                this.mLstIsOutLine.add(true);
            } else {
                this.mLstIsOutLine.add(false);
            }
            arrayList.add(verticalPoint);
            i = i2;
        }
        PointF changePointToFPointF = changePointToFPointF(this.mOldpoints.get(0).moriPoint);
        List<LibDMCollagePoint> list = this.mOldpoints;
        PointF verticalPoint2 = getVerticalPoint(changePointToFPointF, changePointToFPointF(list.get(list.size() - 1).moriPoint), this.mCenterPoint);
        if (this.mOldpoints.get(0).getIsOutRectLinePoint() != 0) {
            List<LibDMCollagePoint> list2 = this.mOldpoints;
            if (list2.get(list2.size() - 1).getIsOutRectLinePoint() != 0) {
                int isOutRectLinePoint = this.mOldpoints.get(0).getIsOutRectLinePoint();
                List<LibDMCollagePoint> list3 = this.mOldpoints;
                if (isOutRectLinePoint == list3.get(list3.size() - 1).getIsOutRectLinePoint()) {
                    this.mLstIsOutLine.add(true);
                    arrayList.add(verticalPoint2);
                    return arrayList;
                }
            }
        }
        this.mLstIsOutLine.add(false);
        arrayList.add(verticalPoint2);
        return arrayList;
    }

    public List<Point> GetVerticaPoints(float f, float f2, int i, int i2) {
        if (this.mCenterPoint == null) {
            this.mCenterPoint = getCenterPoint();
        }
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (i3 < this.mOldpoints.size() - 1) {
            i3++;
            PointF verticalPoint = getVerticalPoint(changePointToFPointF(this.mOldpoints.get(i3).moriPoint), changePointToFPointF(this.mOldpoints.get(i3).moriPoint), this.mCenterPoint);
            arrayList.add(new Point((int) (((verticalPoint.x - i) * f) + 0.5f), (int) (((verticalPoint.y - i2) * f2) + 0.5f)));
        }
        PointF changePointToFPointF = changePointToFPointF(this.mOldpoints.get(0).moriPoint);
        List<LibDMCollagePoint> list = this.mOldpoints;
        PointF verticalPoint2 = getVerticalPoint(changePointToFPointF, changePointToFPointF(list.get(list.size() - 1).moriPoint), this.mCenterPoint);
        arrayList.add(new Point((int) (((verticalPoint2.x - i) * f) + 0.5f), (int) (((verticalPoint2.y - i2) * f2) + 0.5f)));
        return arrayList;
    }

    public List<PointF> GetVerticaScalePoints(float f) {
        return GetVerticaScalePoints(1.0f, 1.0f, 0, 0, f);
    }

    public List<PointF> GetVerticaScalePoints(float f, float f2, int i, int i2, float f3) {
        float lineB = 0;
        float lineA = 0;
        float lineB2;
        float lineB3;
        Point point;
        double d;
        float f4;
        double d2;
        double d3;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        LibDMCollageInfo libDMCollageInfo = this;
        int i3 = i;
        libDMCollageInfo.mCenterPoint = getCenterPoint();
        List<PointF> GetVerticaPoints = GetVerticaPoints();
        libDMCollageInfo.mVerticaPointFs = GetVerticaPoints;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int i4 = 0;
        while (i4 < GetVerticaPoints.size()) {
            double cosRatio = libDMCollageInfo.getCosRatio(libDMCollageInfo.mCenterPoint, GetVerticaPoints.get(i4));
            double lineSpace = libDMCollageInfo.lineSpace(libDMCollageInfo.mCenterPoint, GetVerticaPoints.get(i4));
            float f11 = libDMCollageInfo.mCenterPoint.x;
            int i5 = (cosRatio > FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 1 : (cosRatio == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? 0 : -1));
            if (i5 != 0) {
                if (!libDMCollageInfo.mLstIsOutLine.get(i4).booleanValue()) {
                    f9 = (float) ((lineSpace - libDMCollageInfo.mInnerFrameWidth) * cosRatio);
                    f10 = libDMCollageInfo.mCenterPoint.x;
                } else {
                    f9 = (float) ((lineSpace + libDMCollageInfo.mInnerFrameWidth) * cosRatio);
                    f10 = libDMCollageInfo.mCenterPoint.x;
                }
                f11 = f9 + f10;
            }
            float pointYByLine = libDMCollageInfo.getPointYByLine(f11, libDMCollageInfo.mCenterPoint, GetVerticaPoints.get(i4));
            if (i5 == 0) {
                if (pointYByLine > 0.0f) {
                    if (!libDMCollageInfo.mLstIsOutLine.get(i4).booleanValue()) {
                        f5 = GetVerticaPoints.get(i4).y;
                        f6 = libDMCollageInfo.mInnerFrameWidth;
                        pointYByLine = f5 - (f6 / f3);
                    } else {
                        f7 = GetVerticaPoints.get(i4).y;
                        f8 = libDMCollageInfo.mInnerFrameWidth;
                        pointYByLine = f7 + (f8 / f3);
                    }
                } else if (!libDMCollageInfo.mLstIsOutLine.get(i4).booleanValue()) {
                    f7 = GetVerticaPoints.get(i4).y;
                    f8 = libDMCollageInfo.mInnerFrameWidth;
                    pointYByLine = f7 + (f8 / f3);
                } else {
                    f5 = GetVerticaPoints.get(i4).y;
                    f6 = libDMCollageInfo.mInnerFrameWidth;
                    pointYByLine = f5 - (f6 / f3);
                }
            }
            arrayList.add(new PointF((f11 - i3) * f, (pointYByLine - i2) * f));
            Point point2 = libDMCollageInfo.mOldpoints.get(i4).moriPoint;
            PointF pointF = GetVerticaPoints.get(i4);
            if (point2.x - pointF.x != 0.0f) {
                d = ((point2.x * pointF.y) - (pointF.x * point2.y)) / (point2.x - pointF.x);
                point = point2;
            } else {
                point = point2;
                d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
            }
            if (point.x != 0) {
                f4 = pointYByLine;
                d2 = point.y - d;
                d3 = point.x;
            } else {
                f4 = pointYByLine;
                d2 = pointF.y - d;
                d3 = pointF.x;
            }
            double d4 = d2 / d3;
            List<PointF> list = GetVerticaPoints;
            ArrayList arrayList4 = arrayList;
            float f12 = f4;
            LibDMCollageLineInfo libDMCollageLineInfo = new LibDMCollageLineInfo();
            libDMCollageLineInfo.setLineA(d4);
            libDMCollageLineInfo.setLineB(f12 - (f11 * d4));
            libDMCollageLineInfo.setVerPointF(new PointF(f11, f12));
            if (point.x - pointF.x == 0.0f) {
                libDMCollageLineInfo.setIsXFunction(true);
            }
            arrayList3.add(libDMCollageLineInfo);
            i4++;
            libDMCollageInfo = this;
            i3 = i;
            GetVerticaPoints = list;
            arrayList = arrayList4;
        }
        if (arrayList3.size() > 0) {
            arrayList3.add(arrayList3.get(0));
        }
        int i6 = 0;
        while (i6 < arrayList3.size() - 1) {
            LibDMCollageLineInfo libDMCollageLineInfo2 = (LibDMCollageLineInfo) arrayList3.get(i6);
            i6++;
            LibDMCollageLineInfo libDMCollageLineInfo3 = (LibDMCollageLineInfo) arrayList3.get(i6);
            if (libDMCollageLineInfo2.getIsXFunction()) {
                lineB3 = libDMCollageLineInfo2.getVerPointF().x;
                lineB2 = (float) ((libDMCollageLineInfo3.getLineA() * lineB3) + libDMCollageLineInfo3.getLineB());
            } else {
                if (libDMCollageLineInfo3.getIsXFunction()) {
                    lineB = libDMCollageLineInfo3.getVerPointF().x;
                    lineA = (float) ((lineB * libDMCollageLineInfo2.getLineA()) + libDMCollageLineInfo2.getLineB());
                } else if (libDMCollageLineInfo3.getLineA() == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    lineB2 = (float) libDMCollageLineInfo3.getLineB();
                    lineB3 = (float) ((lineB2 - libDMCollageLineInfo2.getLineB()) / libDMCollageLineInfo2.getLineA());
                } else if (libDMCollageLineInfo2.getLineA() == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    lineA = (float) libDMCollageLineInfo2.getLineB();
                    lineB = (float) ((lineA - libDMCollageLineInfo3.getLineB()) / libDMCollageLineInfo3.getLineA());
                } else {
                    lineB = (float) ((libDMCollageLineInfo3.getLineB() - libDMCollageLineInfo2.getLineB()) / (libDMCollageLineInfo2.getLineA() - libDMCollageLineInfo3.getLineA()));
                    lineA = (float) ((lineB * libDMCollageLineInfo2.getLineA()) + libDMCollageLineInfo2.getLineB());
                }
                float f13 = lineB;
                lineB2 = lineA;
                lineB3 = f13;
                arrayList2.add(new PointF((lineB3 - i) * f, (lineB2 - i2) * f));
            }
            arrayList2.add(new PointF((lineB3 - i) * f, (lineB2 - i2) * f));
        }
        return arrayList2;
    }

    public PointF changePointToFPointF(Point point) {
        return new PointF(point);
    }

    private double getCosRatio(PointF pointF, PointF pointF2) {
        return Math.round(pointF2.x - pointF.x) / Math.sqrt(((pointF2.x - pointF.x) * (pointF2.x - pointF.x)) + ((pointF2.y - pointF.y) * (pointF2.y - pointF.y)));
    }

    private float getPointYByLine(float f, PointF pointF, PointF pointF2) {
        float f2 = pointF.x;
        float f3 = pointF.y;
        float f4 = pointF2.x;
        float f5 = pointF2.y;
        float f6 = f4 - f2;
        float f7 = f3 - f5;
        return ((double) Math.abs(f6)) > 0.01d ? (-(((f2 * f5) - (f4 * f3)) / f6)) - ((f7 / f6) * f) : -f7;
    }

    public List<Point> GetOriPoints() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mOldpoints.size(); i++) {
            arrayList.add(this.mOldpoints.get(i).GetPoint());
        }
        return arrayList;
    }

    public Rect GetRect(float f) {
        if (this.mCenterPoint == null) {
            this.mCenterPoint = getCenterPoint();
        }
        List<Point> GetPoints = GetPoints(f);
        int i = GetPoints.get(0).x;
        int i2 = GetPoints.get(0).y;
        int i3 = i2;
        int i4 = i;
        for (int i5 = 0; i5 < GetPoints.size(); i5++) {
            int i6 = GetPoints.get(i5).x;
            int i7 = GetPoints.get(i5).y;
            if (i4 < i6) {
                i4 = i6;
            }
            if (i3 < i7) {
                i3 = i7;
            }
            if (i > i6) {
                i = i6;
            }
            if (i2 > i7) {
                i2 = i7;
            }
        }
        return new Rect(i, i2, i4, i3);
    }

    public List<LibCollageTouchPoint> getTouchCollagePoints() {
        ArrayList arrayList = new ArrayList();
        List<Point> GetPoints = GetPoints(1.0f);
        for (int i = 0; i < this.mOldpoints.size(); i++) {
            if (i == this.mOldpoints.size() - 1) {
                if (this.mOldpoints.get(i).gethLineMode() > 0 && this.mOldpoints.get(i).gethLineMode() == this.mOldpoints.get(0).gethLineMode()) {
                    LibCollageTouchPoint libCollageTouchPoint = new LibCollageTouchPoint();
                    libCollageTouchPoint.getTouchPoint().x = (GetPoints.get(i).x + GetPoints.get(0).x) / 2;
                    libCollageTouchPoint.getTouchPoint().y = (GetPoints.get(i).y + GetPoints.get(0).y) / 2;
                    libCollageTouchPoint.sethLineMode(this.mOldpoints.get(i).gethLineMode());
                    libCollageTouchPoint.setTouchState(0);
                    arrayList.add(libCollageTouchPoint);
                }
                if (this.mOldpoints.get(i).getvLineMode() > 0 && this.mOldpoints.get(i).getvLineMode() == this.mOldpoints.get(0).getvLineMode()) {
                    LibCollageTouchPoint libCollageTouchPoint2 = new LibCollageTouchPoint();
                    libCollageTouchPoint2.getTouchPoint().x = (GetPoints.get(i).x + GetPoints.get(0).x) / 2;
                    libCollageTouchPoint2.getTouchPoint().y = (GetPoints.get(i).y + GetPoints.get(0).y) / 2;
                    libCollageTouchPoint2.setvLineMode(this.mOldpoints.get(i).getvLineMode());
                    libCollageTouchPoint2.setTouchState(1);
                    arrayList.add(libCollageTouchPoint2);
                }
            } else {
                if (this.mOldpoints.get(i).gethLineMode() > 0) {
                    int i2 = i + 1;
                    if (this.mOldpoints.get(i).gethLineMode() == this.mOldpoints.get(i2).gethLineMode()) {
                        LibCollageTouchPoint libCollageTouchPoint3 = new LibCollageTouchPoint();
                        libCollageTouchPoint3.getTouchPoint().x = (GetPoints.get(i).x + GetPoints.get(i2).x) / 2;
                        libCollageTouchPoint3.getTouchPoint().y = (GetPoints.get(i).y + GetPoints.get(i2).y) / 2;
                        libCollageTouchPoint3.sethLineMode(this.mOldpoints.get(i).gethLineMode());
                        libCollageTouchPoint3.setTouchState(0);
                        arrayList.add(libCollageTouchPoint3);
                    }
                }
                if (this.mOldpoints.get(i).getvLineMode() > 0) {
                    int i3 = i + 1;
                    if (this.mOldpoints.get(i).getvLineMode() == this.mOldpoints.get(i3).getvLineMode()) {
                        LibCollageTouchPoint libCollageTouchPoint4 = new LibCollageTouchPoint();
                        libCollageTouchPoint4.getTouchPoint().x = (GetPoints.get(i).x + GetPoints.get(i3).x) / 2;
                        libCollageTouchPoint4.getTouchPoint().y = (GetPoints.get(i).y + GetPoints.get(i3).y) / 2;
                        libCollageTouchPoint4.setvLineMode(this.mOldpoints.get(i).getvLineMode());
                        libCollageTouchPoint4.setTouchState(1);
                        arrayList.add(libCollageTouchPoint4);
                    }
                }
            }
        }
        return arrayList;
    }

    public Rect GetOriRect() {
        List<Point> GetOriPoints = GetOriPoints();
        int i = GetOriPoints.get(0).x;
        int i2 = GetOriPoints.get(0).y;
        int i3 = i2;
        int i4 = i;
        for (int i5 = 0; i5 < GetOriPoints.size(); i5++) {
            int i6 = GetOriPoints.get(i5).x;
            int i7 = GetOriPoints.get(i5).y;
            if (i4 < i6) {
                i4 = i6;
            }
            if (i3 < i7) {
                i3 = i7;
            }
            if (i > i6) {
                i = i6;
            }
            if (i2 > i7) {
                i2 = i7;
            }
        }
        return new Rect(i, i2, i4, i3);
    }

    public Path getPath(float f, float f2) {
        Path path = new Path();
        List<Point> GetPoints = GetPoints(1.0f);
        if (GetPoints.size() < 3) {
            return null;
        }
        for (int i = 0; i < GetPoints.size(); i++) {
            int i2 = (int) ((GetPoints.get(i).x * f) + 0.5f);
            int i3 = (int) ((GetPoints.get(i).y * f2) + 0.5f);
            if (i == 0) {
                path.moveTo(i2, i3);
            } else {
                path.lineTo(i2, i3);
            }
        }
        path.close();
        return path;
    }

    public Path getPath(float f, float f2, int i, int i2, float f3) {
        Path path = new Path();
        List<Point> GetPoints = GetPoints(f3);
        if (GetPoints.size() < 3) {
            return null;
        }
        int i3 = 0;
        while (i3 < GetPoints.size()) {
            int i4 = (int) (((GetPoints.get(i3).x - i) * f) + 0.5f);
            int i5 = (int) (((GetPoints.get(i3).y - i2) * f2) + 0.5f);
            if (i3 == 0) {
                path.moveTo(i4, i5);
            } else if (this.mOldpoints.get(i3).getIsArcPoint()) {
                i3++;
                path.quadTo(i4, i5, (int) (((GetPoints.get(i3).x - i) * f) + 0.5f), (int) (((GetPoints.get(i3).y - i2) * f2) + 0.5f));
            } else {
                path.lineTo(i4, i5);
            }
            i3++;
        }
        path.close();
        return path;
    }

    public PointF getScaleCenterPoint(float f, float f2, int i, int i2) {
        if (this.mCenterPoint == null) {
            this.mCenterPoint = getCenterPoint();
        }
        PointF pointF = this.mCenterPoint;
        PointF pointF2 = new PointF((pointF.x - i) * f, (pointF.y - i2) * f2);
        Log.i("Collage_CenterPoint", pointF.toString());
        Log.i("Collage_CenterPoint_modify", pointF.toString());
        return pointF2;
    }

    public PointF getCenterPoint() {
        int size = this.mOldpoints.size();
        PointF[] pointFArr = new PointF[size + 1];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            pointFArr[i2] = new PointF(this.mOldpoints.get(i2).moriPoint.x, this.mOldpoints.get(i2).moriPoint.y);
        }
        pointFArr[size] = new PointF();
        pointFArr[size].x = pointFArr[0].x;
        pointFArr[size].y = pointFArr[0].y;
        float f = 0.0f;
        int i3 = 0;
        float f2 = 0.0f;
        while (i3 < size) {
            int i4 = i3 + 1;
            f2 = (float) (f2 + cal(i3, i4, pointFArr));
            i3 = i4;
        }
        float f3 = f2 / 2.0f;
        float f4 = 0.0f;
        while (i < size) {
            int i5 = i + 1;
            f = (float) (f + ((pointFArr[i].x + pointFArr[i5].x) * cal(i, i5, pointFArr)));
            f4 = (float) (f4 + ((pointFArr[i].y + pointFArr[i5].y) * cal(i, i5, pointFArr)));
            i = i5;
        }
        float f5 = f3 * 6.0f;
        return new PointF(Math.round(f / f5), Math.round(f4 / f5));
    }

    public double cal(int i, int i2, PointF[] pointFArr) {
        return (pointFArr[i].x * pointFArr[i2].y) - (pointFArr[i2].x * pointFArr[i].y);
    }

    private double lineSpace(PointF pointF, PointF pointF2) {
        return Math.sqrt(((pointF.x - pointF2.x) * (pointF.x - pointF2.x)) + ((pointF.y - pointF2.y) * (pointF.y - pointF2.y)));
    }

    public PointF getVerticalPoint(PointF pointF, PointF pointF2, PointF pointF3) {
        return getVerticalPoint(pointF3.x, pointF3.y, pointF.x, pointF.y, pointF2.x, pointF2.y);
    }

    private PointF getVerticalPoint(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f3 - f5;
        float f8 = f4 - f6;
        float f9 = f5 - f3;
        float f10 = f6 - f4;
        float f11 = (((f - f3) * f9) + ((f2 - f4) * f10)) / ((f7 * f7) + (f8 * f8));
        return new PointF(f3 + (f9 * f11), f4 + (f11 * f10));
    }

    public void createNewPoint(int i, int i2, int i3) {
        for (LibDMCollagePoint libDMCollagePoint : this.mOldpoints) {
            if (i2 == 0 && libDMCollagePoint.gethLineMode() == i && i > 0) {
                libDMCollagePoint.GetPoint().x += i3;
            }
            if (i2 == 1 && libDMCollagePoint.getvLineMode() == i && i > 0) {
                libDMCollagePoint.GetPoint().y += i3;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x007a, code lost:
        if (r8 > r1) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x008c, code lost:
        if (r8 < r2) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00ac, code lost:
        if (r8 > r1) goto L19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00ae, code lost:
        r1 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00bf, code lost:
        if (r8 < r2) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00c1, code lost:
        r2 = r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo.LibCollageTouchPoint getPointArea(int r11, int r12, com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo.LibCollageTouchPoint r13) {
        /*
            Method dump skipped, instructions count: 336
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo.getPointArea(int, int, com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo$LibCollageTouchPoint):com.picspool.lib.collagelib.resource.collage.LibDMCollageInfo$LibCollageTouchPoint");
    }

    public void setIsShowFrame(boolean z) {
        this.mIsShowFrame = z;
    }

    public boolean getIsShowFrame() {
        return this.mIsShowFrame;
    }

    /* loaded from: classes3.dex */
    public class LibCollageTouchPoint {
        private int vLineMode = 0;
        private int hLineMode = 0;
        private int mTouchState = 0;
        int minAreaValue = -3060;
        int maxAreaValue = 3060;
        private int mIndex = -1;
        private Point mPoint = new Point();
        int maxPointValue = 3060;
        int minPointValue = -3060;

        public LibCollageTouchPoint() {
        }

        public int getvLineMode() {
            return this.vLineMode;
        }

        public void setvLineMode(int i) {
            this.vLineMode = i;
        }

        public int gethLineMode() {
            return this.hLineMode;
        }

        public void sethLineMode(int i) {
            this.hLineMode = i;
        }

        public void setTouchPoint(Point point) {
            this.mPoint = point;
        }

        public Point getTouchPoint() {
            return this.mPoint;
        }

        public int getTouchState() {
            return this.mTouchState;
        }

        public void setTouchState(int i) {
            this.mTouchState = i;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public void setIndex(int i) {
            this.mIndex = i;
        }

        public int getAreaMinValue() {
            return this.minAreaValue;
        }

        public void setAreaMinValue(int i) {
            this.minAreaValue = i;
        }

        public int getAreaMaxValue() {
            return this.maxAreaValue;
        }

        public void setAreaMaxValue(int i) {
            this.maxAreaValue = i;
        }

        public int getPointMaxValue() {
            return this.maxPointValue;
        }

        public void setPointMaxValue(int i) {
            this.maxPointValue = i;
        }

        public int getPointMinValue() {
            return this.minPointValue;
        }

        public void setPointMinValue(int i) {
            this.minPointValue = i;
        }
    }
}
