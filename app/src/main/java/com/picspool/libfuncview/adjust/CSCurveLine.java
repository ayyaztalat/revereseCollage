package com.picspool.libfuncview.adjust;

import android.graphics.Point;
import android.graphics.PointF;
import com.picspool.libfuncview.adjust.CSAdjustCurveTouchView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CSCurveLine {
    private int color;
    private CSAdjustCurveTouchView.CurveColor curveColor;
    private List<PointF> pointFList;

    public CSCurveLine(CSAdjustCurveTouchView.CurveColor curveColor) {
        ArrayList arrayList = new ArrayList();
        this.pointFList = arrayList;
        arrayList.add(new PointF(0.0f, 0.0f));
        this.pointFList.add(new PointF(1.0f, 1.0f));
        if (curveColor.equals(CSAdjustCurveTouchView.CurveColor.Red)) {
            setColor(-3407872);
        } else if (curveColor.equals(CSAdjustCurveTouchView.CurveColor.Green)) {
            setColor(-16724992);
        } else if (curveColor.equals(CSAdjustCurveTouchView.CurveColor.Blue)) {
            setColor(-16777012);
        } else if (curveColor.equals(CSAdjustCurveTouchView.CurveColor.RGB)) {
            setColor(-1);
        }
        setCurveColor(curveColor);
    }

    public CSCurveLine(CSCurveLine cSCurveLine) {
        this.pointFList = new ArrayList();
        setColor(cSCurveLine.getColor());
        setCurveColor(cSCurveLine.getCurveColor());
        for (PointF pointF : cSCurveLine.getPointFList()) {
            this.pointFList.add(new PointF(pointF.x, pointF.y));
        }
    }

    public List<PointF> getPointFList() {
        return this.pointFList;
    }

    public PointF getPointForIndex(int i) {
        return this.pointFList.get(i);
    }

    public PointF[] getPontFArray() {
        List<PointF> list = this.pointFList;
        return (PointF[]) list.toArray(new PointF[list.size()]);
    }

    public Point[] getPontArray() {
        Point[] pointArr = new Point[this.pointFList.size()];
        for (int i = 0; i < this.pointFList.size(); i++) {
            pointArr[i] = new Point((int) this.pointFList.get(i).x, (int) this.pointFList.get(i).y);
        }
        return pointArr;
    }

    public void setPointFList(List<PointF> list) {
        this.pointFList = list;
    }

    public CSAdjustCurveTouchView.CurveColor getCurveColor() {
        return this.curveColor;
    }

    public void setCurveColor(CSAdjustCurveTouchView.CurveColor curveColor) {
        this.curveColor = curveColor;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int i) {
        this.color = i;
    }

    public boolean haveCurveLineChanged() {
        PointF pointF = new PointF(0.0f, 0.0f);
        PointF pointF2 = new PointF(1.0f, 1.0f);
        if (this.pointFList.size() > 2 || !this.pointFList.get(0).equals(pointF)) {
            return true;
        }
        List<PointF> list = this.pointFList;
        return !list.get(list.size() - 1).equals(pointF2);
    }

    public PointF getNextPointF(PointF pointF) {
        PointF pointF2 = null;
        float f = 2.0f;
        for (PointF pointF3 : this.pointFList) {
            if (pointF.x < pointF3.x) {
                float f2 = pointF3.x - pointF.x;
                if (f2 < f) {
                    pointF2 = pointF3;
                    f = f2;
                }
            }
        }
        return pointF2;
    }

    public PointF getPreviousPointF(PointF pointF) {
        PointF pointF2 = null;
        float f = 2.0f;
        for (PointF pointF3 : this.pointFList) {
            if (pointF.x > pointF3.x) {
                float f2 = pointF.x - pointF3.x;
                if (f2 < f) {
                    pointF2 = pointF3;
                    f = f2;
                }
            }
        }
        return pointF2;
    }
}
