package com.picspool.libfuncview.adjust;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import com.picspool.lib.bitmap.DMBitmapCrop;
import com.picspool.lib.bitmap.DMBitmapUtil;

/* loaded from: classes.dex */
public class CSAdjustCurveTouchView extends View {
    private static final String TAG = "CSAdjustCurveTouchView";
    private int bdpadding;
    private CSCurveLine curveCurrent;
    private List<CSCurveLine> curveLineList;
    private CurveTouchEventCallBack curveTouchEventCallBack;
    private boolean drawsjqx;
    private boolean drawsjzf;
    private int gridcount;
    private Context mContext;
    private int maxponit;
    private int padding;
    private final Paint paint;
    private Path path;
    private float pointCount;
    private int pointIndex;
    private boolean selectPointdown;
    private boolean selectPointright;
    private List<float[]> sjlist;
    private float touchpointRadius;
    private Bitmap ybBmp;

    /* loaded from: classes.dex */
    public enum CurveColor {
        Red,
        Green,
        Blue,
        RGB
    }

    /* loaded from: classes.dex */
    public interface CurveTouchEventCallBack {
        void getPoints(CSCurveLine cSCurveLine);
    }

    public CSAdjustCurveTouchView(Context context, int i) {
        super(context);
        this.gridcount = 4;
        this.padding = 120;
        this.bdpadding = 20;
        this.maxponit = 8;
        this.pointCount = 1000.0f;
        this.touchpointRadius = 0.15f;
        this.curveLineList = new ArrayList();
        this.pointIndex = -1;
        this.selectPointdown = false;
        this.selectPointright = false;
        this.sjlist = new ArrayList();
        this.drawsjqx = false;
        this.drawsjzf = false;
        this.mContext = context;
        setWillNotDraw(false);
        setPointCount(i - this.padding);
        resetCurve();
        Paint paint = new Paint();
        this.paint = paint;
        paint.setAntiAlias(true);
        this.paint.setDither(true);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(10.0f);
        this.path = new Path();
        Bitmap imageFromAssetsFile = DMBitmapUtil.getImageFromAssetsFile(this.mContext.getResources(), "curveadjust/img_yb_point.png");
        this.ybBmp = imageFromAssetsFile;
        int i2 = this.padding;
        this.ybBmp = DMBitmapCrop.cropCenterScaleBitmap(imageFromAssetsFile, ((i2 * 2) / 3) / 2, (i2 * 2) / 3);
    }

    public float getPointCount() {
        return this.pointCount;
    }

    public void setPointCount(float f) {
        this.pointCount = f;
    }

    public float getTouchpointRadius() {
        return this.touchpointRadius;
    }

    public void setTouchpointRadius(float f) {
        this.touchpointRadius = f;
    }

    public void resetCurve() {
        this.pointIndex = -1;
        this.curveLineList.clear();
        this.curveLineList.add(new CSCurveLine(CurveColor.RGB));
        this.curveLineList.add(new CSCurveLine(CurveColor.Red));
        this.curveLineList.add(new CSCurveLine(CurveColor.Green));
        this.curveLineList.add(new CSCurveLine(CurveColor.Blue));
        this.curveCurrent = this.curveLineList.get(0);
        invalidate();
    }

    public void resetCurve(CSCurveMode cSCurveMode) {
        this.pointIndex = -1;
        this.curveLineList.clear();
        this.curveLineList.add(new CSCurveLine(cSCurveMode.getCurveLineList().get(0)));
        this.curveLineList.add(new CSCurveLine(cSCurveMode.getCurveLineList().get(1)));
        this.curveLineList.add(new CSCurveLine(cSCurveMode.getCurveLineList().get(2)));
        this.curveLineList.add(new CSCurveLine(cSCurveMode.getCurveLineList().get(3)));
        this.curveCurrent = this.curveLineList.get(0);
        invalidate();
    }

    public void setCurveCurrent(CurveColor curveColor) {
        this.pointIndex = -1;
        if (curveColor == CurveColor.RGB) {
            this.curveCurrent = this.curveLineList.get(0);
        } else if (curveColor == CurveColor.Red) {
            this.curveCurrent = this.curveLineList.get(1);
        } else if (curveColor == CurveColor.Green) {
            this.curveCurrent = this.curveLineList.get(2);
        } else if (curveColor == CurveColor.Blue) {
            this.curveCurrent = this.curveLineList.get(3);
        }
        invalidate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i = 0;
        if (this.pointCount < 0.0f) {
            return false;
        }
        int width = getWidth() - (this.padding * 2);
        int height = getHeight() - (this.padding * 2);
        float x = (motionEvent.getX() - this.padding) / width;
        float y = 1.0f - ((motionEvent.getY() - this.padding) / height);
        if (motionEvent.getAction() == 0) {
            int i2 = (x > 0.0f ? 1 : (x == 0.0f ? 0 : -1));
            if (i2 >= 0 && x <= 1.0f && y >= 0.0f && y <= 1.0f) {
                this.pointIndex = -1;
                while (true) {
                    if (i >= this.curveCurrent.getPointFList().size()) {
                        break;
                    }
                    PointF pointF = this.curveCurrent.getPointFList().get(i);
                    if (Math.abs(x - pointF.x) + Math.abs(y - pointF.y) < getTouchpointRadius()) {
                        this.pointIndex = i;
                        break;
                    }
                    i++;
                }
                if (this.pointIndex == -1 && this.curveCurrent.getPointFList().size() <= this.maxponit) {
                    this.curveCurrent.getPointFList().add(new PointF(x, y));
                    this.pointIndex = this.curveCurrent.getPointFList().size() - 1;
                }
            } else if (y < 0.0f && i2 >= 0 && x <= 1.0f && this.pointIndex >= 0) {
                this.selectPointdown = false;
                if (Math.abs(x - this.curveCurrent.getPointFList().get(this.pointIndex).x) < getTouchpointRadius()) {
                    this.selectPointdown = true;
                }
            } else if (x > 1.0f && y >= 0.0f && y <= 1.0f && this.pointIndex >= 0) {
                this.selectPointright = false;
                if (Math.abs(y - this.curveCurrent.getPointFList().get(this.pointIndex).y) < getTouchpointRadius()) {
                    this.selectPointright = true;
                }
            }
        } else if (this.pointIndex >= 0) {
            int i3 = (x > 0.0f ? 1 : (x == 0.0f ? 0 : -1));
            if (i3 >= 0 && x <= 1.0f && y >= 0.0f && y <= 1.0f) {
                moveCurrentPoint(x, y);
            } else if (y > ((this.padding / (getWidth() - this.padding)) / 3.0f) + 1.0f && i3 >= 0 && x <= 1.0f && this.curveCurrent.getPointFList().size() > 2) {
                this.curveCurrent.getPointFList().remove(this.pointIndex);
                this.pointIndex = -1;
            } else if (y < 0.0f && i3 >= 0 && x <= 1.0f && this.selectPointdown) {
                moveCurrentPointX(x);
            } else if (x > 1.0f && y >= 0.0f && y <= 1.0f && this.selectPointright) {
                this.curveCurrent.getPointFList().get(this.pointIndex).y = y;
            }
        }
        CurveTouchEventCallBack curveTouchEventCallBack = this.curveTouchEventCallBack;
        if (curveTouchEventCallBack != null) {
            curveTouchEventCallBack.getPoints(this.curveCurrent);
        }
        invalidate();
        return true;
    }

    private void moveCurrentPointX(float f) {
        moveCurrentPoint(f, -1.0f);
    }

    private void moveCurrentPoint(float f, float f2) {
        PointF pointForIndex = this.curveCurrent.getPointForIndex(this.pointIndex);
        PointF previousPointF = this.curveCurrent.getPreviousPointF(pointForIndex);
        PointF nextPointF = this.curveCurrent.getNextPointF(pointForIndex);
        float f3 = previousPointF != null ? previousPointF.x + 0.011764706f : 0.0f;
        float f4 = nextPointF != null ? nextPointF.x - 0.011764706f : 1.0f;
        if (f <= f3 || f >= f4) {
            return;
        }
        pointForIndex.x = f;
        if (f2 > 0.0f) {
            pointForIndex.y = f2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:64:0x0485  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x059e  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected void onDraw(android.graphics.Canvas r24) {
        /*
            Method dump skipped, instructions count: 1559
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.picspool.libfuncview.adjust.CSAdjustCurveTouchView.onDraw(android.graphics.Canvas):void");
    }

    public void setCurveTouchEventCallBack(CurveTouchEventCallBack curveTouchEventCallBack) {
        this.curveTouchEventCallBack = curveTouchEventCallBack;
    }

    private ArrayList<Point> createSplineCurve3(PointF[] pointFArr) {
        if (pointFArr == null || pointFArr.length <= 0) {
            return null;
        }
        PointF[] pointFArr2 = (PointF[]) pointFArr.clone();
        Arrays.sort(pointFArr2, new Comparator() { // from class: com.picspool.libfuncview.adjust.CSAdjustCurveTouchView.1
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                PointF pointF = (PointF) obj;
                PointF pointF2 = (PointF) obj2;
                if (pointF.x < pointF2.x) {
                    return -1;
                }
                return pointF.x > pointF2.x ? 1 : 0;
            }
        });
        Point[] pointArr = new Point[pointFArr2.length];
        for (int i = 0; i < pointFArr.length; i++) {
            PointF pointF = pointFArr2[i];
            pointArr[i] = new Point((int) (pointF.x * this.pointCount), (int) (pointF.y * this.pointCount));
        }
        ArrayList<Point> createSplineCurve2 = createSplineCurve2(pointArr, this.pointCount);
        Point point = createSplineCurve2.get(0);
        if (point.x > 0) {
            for (int i2 = point.x; i2 >= 0; i2--) {
                createSplineCurve2.add(0, new Point(i2, point.y));
            }
        }
        Point point2 = createSplineCurve2.get(createSplineCurve2.size() - 1);
        if (point2.x < ((int) this.pointCount)) {
            int i3 = point2.x;
            while (true) {
                i3++;
                if (i3 > ((int) this.pointCount)) {
                    break;
                }
                createSplineCurve2.add(new Point(i3, point2.y));
            }
        }
        return createSplineCurve2;
    }

    private ArrayList<Float> createSplineCurve255(PointF[] pointFArr) {
        if (pointFArr == null || pointFArr.length <= 0) {
            return null;
        }
        PointF[] pointFArr2 = (PointF[]) pointFArr.clone();
        Arrays.sort(pointFArr2, new Comparator() { // from class: com.picspool.libfuncview.adjust.CSAdjustCurveTouchView.2
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                PointF pointF = (PointF) obj;
                PointF pointF2 = (PointF) obj2;
                if (pointF.x < pointF2.x) {
                    return -1;
                }
                return pointF.x > pointF2.x ? 1 : 0;
            }
        });
        Point[] pointArr = new Point[pointFArr2.length];
        for (int i = 0; i < pointFArr.length; i++) {
            PointF pointF = pointFArr2[i];
            pointArr[i] = new Point((int) (pointF.x * 255.0f), (int) (pointF.y * 255.0f));
        }
        ArrayList<Point> createSplineCurve2 = createSplineCurve2(pointArr, 255.0f);
        Point point = createSplineCurve2.get(0);
        if (point.x > 0) {
            for (int i2 = point.x; i2 >= 0; i2--) {
                createSplineCurve2.add(0, new Point(i2, point.y));
            }
        }
        Point point2 = createSplineCurve2.get(createSplineCurve2.size() - 1);
        if (point2.x < 255) {
            int i3 = point2.x;
            while (true) {
                i3++;
                if (i3 > 255) {
                    break;
                }
                createSplineCurve2.add(new Point(i3, point2.y));
            }
        }
        ArrayList<Float> arrayList = new ArrayList<>(createSplineCurve2.size());
        Iterator<Point> it2 = createSplineCurve2.iterator();
        while (it2.hasNext()) {
            Point next = it2.next();
            Point point3 = new Point(next.x, next.x);
            float sqrt = (float) Math.sqrt(Math.pow(point3.x - next.x, 2.0d) + Math.pow(point3.y - next.y, 2.0d));
            if (point3.y > next.y) {
                sqrt = -sqrt;
            }
            arrayList.add(Float.valueOf(sqrt));
        }
        return arrayList;
    }

    private ArrayList<Point> createSplineCurve2(Point[] pointArr, float f) {
        Point[] pointArr2 = pointArr;
        float f2 = f;
        ArrayList<Double> createSecondDerivative = createSecondDerivative(pointArr);
        int size = createSecondDerivative.size();
        if (size < 1) {
            return null;
        }
        double[] dArr = new double[size];
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            dArr[i2] = createSecondDerivative.get(i2).doubleValue();
        }
        ArrayList<Point> arrayList = new ArrayList<>(size + 1);
        while (i < size - 1) {
            Point point = pointArr2[i];
            int i3 = i + 1;
            Point point2 = pointArr2[i3];
            int i4 = point.x;
            while (i4 < point2.x) {
                double d = (i4 - point.x) / (point2.x - point.x);
                double d2 = 1.0d - d;
                int i5 = size;
                double d3 = point2.x - point.x;
                Point point3 = point2;
                int i6 = i4;
                double d4 = (point.y * d2) + (point2.y * d) + (((d3 * d3) / 6.0d) * (((((d2 * d2) * d2) - d2) * dArr[i]) + ((((d * d) * d) - d) * dArr[i3])));
                double d5 = f;
                if (d4 > d5) {
                    d4 = d5;
                } else if (d4 < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    d4 = 0.0d;
                }
                arrayList.add(new Point(i6, (int) Math.round(d4)));
                i4 = i6 + 1;
                f2 = f;
                size = i5;
                point2 = point3;
            }
            pointArr2 = pointArr;
            i = i3;
        }
        if (arrayList.size() == ((int) f2)) {
            arrayList.add(pointArr[pointArr.length - 1]);
        }
        return arrayList;
    }

    private ArrayList<Double> createSecondDerivative(Point[] pointArr) {
        int i;
        int length = pointArr.length;
        if (length <= 1) {
            return null;
        }
        char c = 0;
        double[][] dArr = (double[][]) Array.newInstance(double.class, length, 3);
        double[] dArr2 = new double[length];
        dArr[0][1] = 1.0d;
        double[] dArr3 = dArr[0];
        double d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        dArr3[0] = 0.0d;
        dArr[0][2] = 0.0d;
        int i2 = 1;
        while (true) {
            i = length - 1;
            if (i2 >= i) {
                break;
            }
            Point point = pointArr[i2 - 1];
            Point point2 = pointArr[i2];
            int i3 = i2 + 1;
            Point point3 = pointArr[i3];
            dArr[i2][c] = (point2.x - point.x) / 6.0d;
            dArr[i2][1] = (point3.x - point.x) / 3.0d;
            dArr[i2][2] = (point3.x - point2.x) / 6.0d;
            dArr2[i2] = ((point3.y - point2.y) / (point3.x - point2.x)) - ((point2.y - point.y) / (point2.x - point.x));
            i2 = i3;
            c = 0;
            d = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        }
        double d2 = d;
        dArr2[c] = d2;
        dArr2[i] = d2;
        dArr[i][1] = 1.0d;
        dArr[i][c] = d2;
        dArr[i][2] = d2;
        int i4 = 1;
        while (i4 < length) {
            double d3 = dArr[i4][c];
            int i5 = i4 - 1;
            double d4 = d3 / dArr[i5][1];
            double[] dArr4 = dArr[i4];
            dArr4[1] = dArr4[1] - (dArr[i5][2] * d4);
            dArr[i4][0] = 0.0d;
            dArr2[i4] = dArr2[i4] - (d4 * dArr2[i5]);
            i4++;
            c = 0;
        }
        for (int i6 = length - 2; i6 >= 0; i6--) {
            int i7 = i6 + 1;
            double d5 = dArr[i6][2] / dArr[i7][1];
            double[] dArr5 = dArr[i6];
            dArr5[1] = dArr5[1] - (dArr[i7][0] * d5);
            dArr[i6][2] = 0.0d;
            dArr2[i6] = dArr2[i6] - (d5 * dArr2[i7]);
        }
        ArrayList<Double> arrayList = new ArrayList<>(length);
        for (int i8 = 0; i8 < length; i8++) {
            arrayList.add(Double.valueOf(dArr2[i8] / dArr[i8][1]));
        }
        return arrayList;
    }

    public void initsejiebmp(Bitmap bitmap) {
        Bitmap sampeZoomFromBitmap = DMBitmapUtil.sampeZoomFromBitmap(bitmap, 200);
        this.sjlist.add(new float[256]);
        this.sjlist.add(new float[256]);
        this.sjlist.add(new float[256]);
        this.sjlist.add(new float[256]);
        int width = sampeZoomFromBitmap.getWidth();
        int height = sampeZoomFromBitmap.getHeight();
        int i = width * height;
        int[] iArr = new int[i];
        sampeZoomFromBitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = iArr[i2];
            int i4 = (16711680 & i3) >> 16;
            int i5 = (65280 & i3) >> 8;
            int i6 = i3 & 255;
            float[] fArr = this.sjlist.get(0);
            fArr[i4] = fArr[i4] + 1.0f;
            float[] fArr2 = this.sjlist.get(0);
            fArr2[i5] = fArr2[i5] + 1.0f;
            float[] fArr3 = this.sjlist.get(0);
            fArr3[i6] = fArr3[i6] + 1.0f;
            float[] fArr4 = this.sjlist.get(1);
            fArr4[i4] = fArr4[i4] + 1.0f;
            float[] fArr5 = this.sjlist.get(2);
            fArr5[i5] = fArr5[i5] + 1.0f;
            float[] fArr6 = this.sjlist.get(3);
            fArr6[i6] = fArr6[i6] + 1.0f;
        }
        for (float[] fArr7 : this.sjlist) {
            float average = getAverage(fArr7);
            for (int i7 = 0; i7 < fArr7.length; i7++) {
                fArr7[i7] = (fArr7[i7] / average) / 6.0f;
            }
        }
        this.drawsjqx = true;
        invalidate();
    }

    private List<float[]> getSjzflist(List<float[]> list, List<CSCurveLine> list2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new float[256]);
        arrayList.add(new float[256]);
        arrayList.add(new float[256]);
        arrayList.add(new float[256]);
        ArrayList<Float> createSplineCurve255 = createSplineCurve255(list2.get(0).getPontFArray());
        ArrayList<Float> createSplineCurve2552 = createSplineCurve255(list2.get(1).getPontFArray());
        ArrayList<Float> createSplineCurve2553 = createSplineCurve255(list2.get(2).getPontFArray());
        ArrayList<Float> createSplineCurve2554 = createSplineCurve255(list2.get(3).getPontFArray());
        int i = 0;
        for (int i2 = 256; i < i2; i2 = 256) {
            float f = i;
            int min = (int) Math.min(Math.max(createSplineCurve255.get(i).floatValue() + f, 0.0f), 255.0f);
            int min2 = (int) Math.min(Math.max(f + createSplineCurve2552.get(i).floatValue() + createSplineCurve255.get(i).floatValue(), 0.0f), 255.0f);
            int min3 = (int) Math.min(Math.max(f + createSplineCurve2553.get(i).floatValue() + createSplineCurve255.get(i).floatValue(), 0.0f), 255.0f);
            int min4 = (int) Math.min(Math.max(f + createSplineCurve2554.get(i).floatValue() + createSplineCurve255.get(i).floatValue(), 0.0f), 255.0f);
            ((float[]) arrayList.get(0))[min] = list.get(0)[i] + ((float[]) arrayList.get(0))[min];
            ((float[]) arrayList.get(1))[min2] = list.get(1)[i] + ((float[]) arrayList.get(1))[min2];
            ((float[]) arrayList.get(2))[min3] = list.get(2)[i] + ((float[]) arrayList.get(2))[min3];
            ((float[]) arrayList.get(3))[min4] = list.get(3)[i] + ((float[]) arrayList.get(3))[min4];
            i++;
        }
        return arrayList;
    }

    private List<float[]> getSjqxlist(List<float[]> list, List<CSCurveLine> list2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new float[256]);
        arrayList.add(new float[256]);
        arrayList.add(new float[256]);
        arrayList.add(new float[256]);
        ArrayList<Float> createSplineCurve255 = createSplineCurve255(list2.get(0).getPontFArray());
        ArrayList<Float> createSplineCurve2552 = createSplineCurve255(list2.get(1).getPontFArray());
        ArrayList<Float> createSplineCurve2553 = createSplineCurve255(list2.get(2).getPontFArray());
        ArrayList<Float> createSplineCurve2554 = createSplineCurve255(list2.get(3).getPontFArray());
        int i = 0;
        for (int i2 = 256; i < i2; i2 = 256) {
            float f = i;
            int min = (int) Math.min(Math.max(createSplineCurve255.get(i).floatValue() + f, 0.0f), 255.0f);
            int min2 = (int) Math.min(Math.max(f + createSplineCurve2552.get(i).floatValue() + createSplineCurve255.get(i).floatValue(), 0.0f), 255.0f);
            int min3 = (int) Math.min(Math.max(f + createSplineCurve2553.get(i).floatValue() + createSplineCurve255.get(i).floatValue(), 0.0f), 255.0f);
            int min4 = (int) Math.min(Math.max(f + createSplineCurve2554.get(i).floatValue() + createSplineCurve255.get(i).floatValue(), 0.0f), 255.0f);
            ((float[]) arrayList.get(0))[min] = list.get(0)[i] + ((float[]) arrayList.get(0))[min];
            ((float[]) arrayList.get(1))[min2] = list.get(1)[i] + ((float[]) arrayList.get(1))[min2];
            ((float[]) arrayList.get(2))[min3] = list.get(2)[i] + ((float[]) arrayList.get(2))[min3];
            ((float[]) arrayList.get(3))[min4] = list.get(3)[i] + ((float[]) arrayList.get(3))[min4];
            i++;
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new float[256]);
        arrayList2.add(new float[256]);
        arrayList2.add(new float[256]);
        arrayList2.add(new float[256]);
        int i3 = 7;
        int i4 = 0;
        while (i4 < arrayList2.size()) {
            int i5 = 1;
            while (i5 < ((float[]) arrayList2.get(i4)).length - 1) {
                int i6 = i5 - 7;
                if (i6 < 1) {
                    i6 = 1;
                }
                int i7 = i5 + 7;
                if (i7 > 254) {
                    i7 = 254;
                }
                int i8 = i6;
                float f2 = 0.0f;
                while (i8 < i7) {
                    float f3 = i3;
                    f2 += ((float[]) arrayList.get(i4))[i8] * (((((f3 / 2.0f) - Math.abs(i5 - i8)) * 0.8f) / f3) + 1.0f);
                    i8++;
                    i3 = 7;
                }
                ((float[]) arrayList2.get(i4))[i5] = f2 / (i7 - i6);
                i5++;
                i3 = 7;
            }
            ((float[]) arrayList2.get(i4))[0] = ((float[]) arrayList.get(i4))[0];
            ((float[]) arrayList2.get(i4))[255] = ((float[]) arrayList.get(i4))[255];
            i4++;
            i3 = 7;
        }
        for (int i9 = 0; i9 < arrayList.size(); i9++) {
            for (int i10 = 1; i10 < ((float[]) arrayList.get(i9)).length - 1; i10++) {
                int i11 = i10 - 7;
                if (i11 < 1) {
                    i11 = 1;
                }
                int i12 = i10 + 7;
                if (i12 > 254) {
                    i12 = 254;
                }
                float f4 = 0.0f;
                for (int i13 = i11; i13 < i12; i13++) {
                    f4 += ((float[]) arrayList2.get(i9))[i13];
                }
                ((float[]) arrayList.get(i9))[i10] = f4 / (i12 - i11);
            }
            ((float[]) arrayList.get(i9))[0] = ((float[]) arrayList2.get(i9))[0];
            ((float[]) arrayList.get(i9))[255] = ((float[]) arrayList2.get(i9))[255];
        }
        return arrayList;
    }

    private static float getMax(float[] fArr) {
        float f = fArr[0];
        for (int i = 1; i < fArr.length; i++) {
            if (fArr[i] > f) {
                f = fArr[i];
            }
        }
        return f;
    }

    private static float getAverage(float[] fArr) {
        float f = fArr[0];
        for (float f2 : fArr) {
            f += f2;
        }
        return f / fArr.length;
    }
}
