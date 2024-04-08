package com.picspool.lib.filter.gpu.vignette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.opengl.GLES20;
import androidx.work.Data;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/* loaded from: classes3.dex */
public class GPUImageVignetteToneCurveFilter extends GPUImageVignetteFilter {
    public static final String VIGNETTING_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n \n uniform lowp vec2 vignetteCenter;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n uniform lowp float vignetteInvert;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     lowp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     lowp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     mediump vec4 base =  texture2D(inputImageTexture, textureCoordinate);\n     lowp float redCurveValue = texture2D(toneCurveTexture, vec2(base.r, 0.0)).r;\n     lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(base.g, 0.5)).g;\n     lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(base.b, 1.0)).b;\n     lowp vec4 textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue, base.a);\n     lowp vec4 textureColor3 = vec4(mix(base.rgb, textureColor2.rgb, textureColor2.a*(1.0-percent)), base.a);\n     if(vignetteInvert == 0.0) textureColor3 = vec4(mix(base.rgb, textureColor2.rgb, textureColor2.a*percent), base.a);\n     gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), base.a);\n }";
    private String fileType;
    private PointF[] mBlueControlPoints;
    private ArrayList<Float> mBlueCurve;
    private PointF[] mGreenControlPoints;
    private ArrayList<Float> mGreenCurve;
    private PointF[] mRedControlPoints;
    private ArrayList<Float> mRedCurve;
    private PointF[] mRgbCompositeControlPoints;
    private ArrayList<Float> mRgbCompositeCurve;
    private int[] mToneCurveTexture;
    private int mToneCurveTextureUniformLocation;

    public GPUImageVignetteToneCurveFilter() {
        this(new PointF(), 0.3f, 0.75f);
    }

    public GPUImageVignetteToneCurveFilter(PointF pointF, float f, float f2) {
        super(VIGNETTING_FRAGMENT_SHADER, pointF, f, f2);
        this.mToneCurveTexture = new int[]{-1};
        this.fileType = "acv";
        PointF[] pointFArr = {new PointF(0.0f, 0.0f), new PointF(0.5f, 0.5f), new PointF(1.0f, 1.0f)};
        this.mRgbCompositeControlPoints = pointFArr;
        this.mRedControlPoints = pointFArr;
        this.mGreenControlPoints = pointFArr;
        this.mBlueControlPoints = pointFArr;
    }

    public void setFileType(String str) {
        this.fileType = str;
    }

    @Override // com.picspool.lib.filter.gpu.vignette.GPUImageVignetteFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mToneCurveTextureUniformLocation = GLES20.glGetUniformLocation(getProgram(), "toneCurveTexture");
        GLES20.glActiveTexture(33987);
        GLES20.glGenTextures(1, this.mToneCurveTexture, 0);
        GLES20.glBindTexture(3553, this.mToneCurveTexture[0]);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexParameteri(3553, Data.MAX_DATA_BYTES, 9729);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        if (this.fileType == "acv") {
            this.mRgbCompositeCurve = createSplineCurve(this.mRgbCompositeControlPoints);
            this.mRedCurve = createSplineCurve(this.mRedControlPoints);
            this.mGreenCurve = createSplineCurve(this.mGreenControlPoints);
            this.mBlueCurve = createSplineCurve(this.mBlueControlPoints);
        }
        updateToneCurveTexture();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onDrawArraysPre() {
        if (this.mToneCurveTexture[0] != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.mToneCurveTexture[0]);
            GLES20.glUniform1i(this.mToneCurveTextureUniformLocation, 3);
        }
    }

    public void setFromAcvCurveFileInputStream(InputStream inputStream) {
        try {
            readShort(inputStream);
            short readShort = readShort(inputStream);
            ArrayList arrayList = new ArrayList(readShort);
            for (int i = 0; i < readShort; i++) {
                int readShort2 = readShort(inputStream);
                PointF[] pointFArr = new PointF[readShort2];
                for (int i2 = 0; i2 < readShort2; i2++) {
                    pointFArr[i2] = new PointF(readShort(inputStream) * 0.003921569f, readShort(inputStream) * 0.003921569f);
                }
                arrayList.add(pointFArr);
            }
            inputStream.close();
            this.mRgbCompositeControlPoints = (PointF[]) arrayList.get(0);
            this.mRedControlPoints = (PointF[]) arrayList.get(1);
            this.mGreenControlPoints = (PointF[]) arrayList.get(2);
            this.mBlueControlPoints = (PointF[]) arrayList.get(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private short readShort(InputStream inputStream) throws IOException {
        return (short) (inputStream.read() | (inputStream.read() << 8));
    }

    public void setRgbCompositeControlPoints(PointF[] pointFArr) {
        this.mRgbCompositeControlPoints = pointFArr;
        this.mRgbCompositeCurve = createSplineCurve(pointFArr);
        updateToneCurveTexture();
    }

    public void setRedControlPoints(PointF[] pointFArr) {
        this.mRedControlPoints = pointFArr;
        this.mRedCurve = createSplineCurve(pointFArr);
        updateToneCurveTexture();
    }

    public void setGreenControlPoints(PointF[] pointFArr) {
        this.mGreenControlPoints = pointFArr;
        this.mGreenCurve = createSplineCurve(pointFArr);
        updateToneCurveTexture();
    }

    public void setBlueControlPoints(PointF[] pointFArr) {
        this.mBlueControlPoints = pointFArr;
        this.mBlueCurve = createSplineCurve(pointFArr);
        updateToneCurveTexture();
    }

    private void updateToneCurveTexture() {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.vignette.GPUImageVignetteToneCurveFilter.1
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glActiveTexture(33987);
                GLES20.glBindTexture(3553, GPUImageVignetteToneCurveFilter.this.mToneCurveTexture[0]);
                if (GPUImageVignetteToneCurveFilter.this.mRedCurve.size() < 256 || GPUImageVignetteToneCurveFilter.this.mGreenCurve.size() < 256 || GPUImageVignetteToneCurveFilter.this.mBlueCurve.size() < 256 || GPUImageVignetteToneCurveFilter.this.mRgbCompositeCurve.size() < 256) {
                    return;
                }
                byte[] bArr = new byte[1024];
                for (int i = 0; i < 256; i++) {
                    int i2 = i * 4;
                    float f = i;
                    bArr[i2] = (byte) (((int) Math.min(Math.max(((Float) GPUImageVignetteToneCurveFilter.this.mRedCurve.get(i)).floatValue() + f + ((Float) GPUImageVignetteToneCurveFilter.this.mRgbCompositeCurve.get(i)).floatValue(), 0.0f), 255.0f)) & 255);
                    bArr[i2 + 1] = (byte) (((int) Math.min(Math.max(((Float) GPUImageVignetteToneCurveFilter.this.mGreenCurve.get(i)).floatValue() + f + ((Float) GPUImageVignetteToneCurveFilter.this.mRgbCompositeCurve.get(i)).floatValue(), 0.0f), 255.0f)) & 255);
                    bArr[i2 + 2] = (byte) (((int) Math.min(Math.max(f + ((Float) GPUImageVignetteToneCurveFilter.this.mBlueCurve.get(i)).floatValue() + ((Float) GPUImageVignetteToneCurveFilter.this.mRgbCompositeCurve.get(i)).floatValue(), 0.0f), 255.0f)) & 255);
                    bArr[i2 + 3] = -1;
                }
                GLES20.glTexImage2D(3553, 0, 6408, 256, 1, 0, 6408, 5121, ByteBuffer.wrap(bArr));
            }
        });
    }

    private ArrayList<Float> createSplineCurve(PointF[] pointFArr) {
        if (pointFArr == null || pointFArr.length <= 0) {
            return null;
        }
        PointF[] pointFArr2 = (PointF[]) pointFArr.clone();
        Arrays.sort(pointFArr2, new Comparator<PointF>() { // from class: com.picspool.lib.filter.gpu.vignette.GPUImageVignetteToneCurveFilter.2
            @Override // java.util.Comparator
            public int compare(PointF pointF, PointF pointF2) {
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
        ArrayList<Point> createSplineCurve2 = createSplineCurve2(pointArr);
        Point point = createSplineCurve2.get(0);
        if (point.x > 0) {
            for (int i2 = point.x; i2 >= 0; i2--) {
                createSplineCurve2.add(0, new Point(i2, 0));
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
                createSplineCurve2.add(new Point(i3, 255));
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

    private ArrayList<Point> createSplineCurve2(Point[] pointArr) {
        Point[] pointArr2 = pointArr;
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
                double d3 = point2.x - point.x;
                int i5 = size;
                Point point3 = point;
                ArrayList<Point> arrayList2 = arrayList;
                double d4 = (point.y * d2) + (point2.y * d) + (((d3 * d3) / 6.0d) * (((((d2 * d2) * d2) - d2) * dArr[i]) + ((((d * d) * d) - d) * dArr[i3])));
                if (d4 > 255.0d) {
                    d4 = 255.0d;
                } else if (d4 < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                    d4 = 0.0d;
                }
                arrayList = arrayList2;
                arrayList.add(new Point(i4, (int) Math.round(d4)));
                i4++;
                size = i5;
                point = point3;
            }
            pointArr2 = pointArr;
            i = i3;
        }
        if (arrayList.size() == 255) {
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

    public void setFromDatCurveFileInputStream(InputStream inputStream) {
        int[] iArr = new int[256];
        int[] iArr2 = new int[256];
        int[] iArr3 = new int[256];
        int[] iArr4 = new int[256];
        try {
            int available = inputStream.available();
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
            int i = 0;
            int i2 = 0;
            do {
                iArr2[i2] = dataInputStream.readByte();
                if (iArr2[i2] <= 0 && i2 > 0 && iArr2[i2 - 1] > 0) {
                    iArr2[i2] = iArr2[i2] + 256;
                }
                i2++;
            } while (i2 < 256);
            int i3 = 0;
            do {
                iArr3[i3] = dataInputStream.readByte();
                if (iArr3[i3] <= 0 && i3 > 0 && iArr3[i3 - 1] > 0) {
                    iArr3[i3] = iArr3[i3] + 256;
                }
                i3++;
            } while (i3 < 256);
            int i4 = 0;
            do {
                iArr4[i4] = dataInputStream.readByte();
                if (iArr4[i4] <= 0 && i4 > 0 && iArr4[i4 - 1] > 0) {
                    iArr4[i4] = iArr4[i4] + 256;
                }
                i4++;
            } while (i4 < 256);
            if (available > 768) {
                do {
                    iArr[i] = dataInputStream.readByte();
                    if (iArr[i] <= 0 && i > 0 && iArr[i - 1] > 0) {
                        iArr[i] = iArr[i] + 256;
                    }
                    i++;
                } while (i < 256);
                this.mRgbCompositeCurve = createSplineCurve(iArr2);
                this.mRedCurve = createSplineCurve(iArr3);
                this.mGreenCurve = createSplineCurve(iArr4);
                this.mBlueCurve = createSplineCurve(iArr);
                return;
            }
            this.mRgbCompositeCurve = createSplineCurve(this.mRgbCompositeControlPoints);
            this.mRedCurve = createSplineCurve(iArr2);
            this.mGreenCurve = createSplineCurve(iArr3);
            this.mBlueCurve = createSplineCurve(iArr4);
        } catch (Exception unused) {
        }
    }

    public void setFromMapCurveFileBitmap(Bitmap bitmap) {
        int[] iArr = new int[256];
        int[] iArr2 = new int[256];
        int[] iArr3 = new int[256];
        int[] iArr4 = new int[256];
        try {
            int[] iArr5 = new int[1024];
            bitmap.getPixels(iArr5, 0, 256, 0, 0, 256, 4);
            for (int i = 0; i < 4; i++) {
                for (int i2 = 0; i2 < 256; i2++) {
                    int i3 = iArr5[(i * 256) + i2];
                    if (i == 0) {
                        iArr2[i2] = Color.red(i3);
                    } else if (i == 1) {
                        iArr3[i2] = Color.green(i3);
                    } else {
                        iArr4[i2] = Color.blue(i3);
                    }
                }
            }
            this.mRgbCompositeCurve = createSplineCurve(iArr);
            this.mRedCurve = createSplineCurve(iArr2);
            this.mGreenCurve = createSplineCurve(iArr3);
            this.mBlueCurve = createSplineCurve(iArr4);
        } catch (Exception unused) {
        }
    }

    public void setFromMapCurveFileInputStream(InputStream inputStream) {
        setFromMapCurveFileBitmap(BitmapFactory.decodeStream(inputStream));
    }

    private ArrayList<Float> createSplineCurve(int[] iArr) {
        ArrayList<Float> arrayList = new ArrayList<>(iArr.length);
        for (int i = 0; i < 256; i++) {
            Point point = new Point(i, i);
            float sqrt = (float) Math.sqrt(Math.pow(point.x - i, 2.0d) + Math.pow(point.y - iArr[i], 2.0d));
            if (point.y > iArr[i]) {
                sqrt = -sqrt;
            }
            arrayList.add(Float.valueOf(sqrt));
        }
        return arrayList;
    }
}
