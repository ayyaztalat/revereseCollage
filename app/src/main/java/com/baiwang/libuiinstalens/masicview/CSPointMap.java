package com.baiwang.libuiinstalens.masicview;

import android.graphics.Matrix;
import com.baiwang.libuiinstalens.masicview.CSSgImageView;

/* loaded from: classes.dex */
public class CSPointMap {
    private float[] mCurLeftTop;
    private float[] mCurRightBottom;
    private float[] mMapInTexture;
    private float[] mMapInView;
    private float[] mSrcCubeLeftTop;
    private float[] mSrcCubeRightBottom;
    private float[] mSrcLeftTop;
    private float[] mSrcRightBottom;
    private float mViewH;
    private float mViewW;

    private CSPointMap() {
        this.mCurLeftTop = new float[2];
        this.mCurRightBottom = new float[2];
        this.mMapInView = new float[2];
        this.mMapInTexture = new float[2];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setViewW(float f) {
        this.mViewW = f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setViewH(float f) {
        this.mViewH = f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSrcCubeLeftTop(float[] fArr) {
        this.mSrcCubeLeftTop = fArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSrcCubeRightBottom(float[] fArr) {
        this.mSrcCubeRightBottom = fArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSrcLeftTop(float[] fArr) {
        this.mSrcLeftTop = fArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSrcRightBottom(float[] fArr) {
        this.mSrcRightBottom = fArr;
    }

    public float[] getMapInView() {
        return this.mMapInView;
    }

    public float[] getMapInTexture() {
        return this.mMapInTexture;
    }

    public float[] mapToView(float f, float f2) {
        float[] fArr = this.mMapInView;
        fArr[0] = ((f / this.mViewW) * 2.0f) - 1.0f;
        fArr[1] = 1.0f - ((f2 / this.mViewH) * 2.0f);
        return fArr;
    }

    public float[] mapToTexture(float f, float f2, CSSgMatrix cSSgMatrix) {
        System.arraycopy(this.mSrcCubeLeftTop, 0, this.mCurLeftTop, 0, 2);
        cSSgMatrix.mapPoint(this.mCurLeftTop);
        System.arraycopy(this.mSrcCubeRightBottom, 0, this.mCurRightBottom, 0, 2);
        cSSgMatrix.mapPoint(this.mCurRightBottom);
        mapToView(f, f2);
        float[] fArr = this.mMapInTexture;
        float[] fArr2 = this.mMapInView;
        float f3 = fArr2[0];
        float[] fArr3 = this.mCurLeftTop;
        float f4 = f3 - fArr3[0];
        float[] fArr4 = this.mCurRightBottom;
        fArr[0] = f4 / (fArr4[0] - fArr3[0]);
        fArr[1] = (fArr2[1] - fArr3[1]) / (fArr4[1] - fArr3[1]);
        return fArr;
    }

    public float[] mapToTexture(float f, float f2, Matrix matrix) {
        System.arraycopy(this.mSrcLeftTop, 0, this.mCurLeftTop, 0, 2);
        matrix.mapPoints(this.mCurLeftTop);
        System.arraycopy(this.mSrcRightBottom, 0, this.mCurRightBottom, 0, 2);
        matrix.mapPoints(this.mCurRightBottom);
        float[] fArr = this.mMapInTexture;
        float[] fArr2 = this.mCurLeftTop;
        float f3 = f - fArr2[0];
        float[] fArr3 = this.mCurRightBottom;
        fArr[0] = f3 / (fArr3[0] - fArr2[0]);
        fArr[1] = (f2 - fArr2[1]) / (fArr3[1] - fArr2[1]);
        return fArr;
    }

    public float[] mapToTexture(float f, float f2, CSSgImageView.ImageLocation imageLocation) {
        System.arraycopy(imageLocation.getCurLeftTop(), 0, this.mCurLeftTop, 0, 2);
        System.arraycopy(imageLocation.getCurRightBottom(), 0, this.mCurRightBottom, 0, 2);
        float[] fArr = this.mMapInTexture;
        float[] fArr2 = this.mCurLeftTop;
        float f3 = f - fArr2[0];
        float[] fArr3 = this.mCurRightBottom;
        fArr[0] = f3 / (fArr3[0] - fArr2[0]);
        fArr[1] = (f2 - fArr2[1]) / (fArr3[1] - fArr2[1]);
        return fArr;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private CSPointMap mPointMap = new CSPointMap();

        public Builder setViewWidth(float f) {
            this.mPointMap.setViewW(f);
            return this;
        }

        public Builder setViewHeight(float f) {
            this.mPointMap.setViewH(f);
            return this;
        }

        public Builder setSrcCubeLeftTop(float[] fArr) {
            this.mPointMap.setSrcCubeLeftTop(fArr);
            return this;
        }

        public Builder setSrcCubeRightBottom(float[] fArr) {
            this.mPointMap.setSrcCubeRightBottom(fArr);
            return this;
        }

        public Builder setSrcLeftTop(float[] fArr) {
            this.mPointMap.setSrcLeftTop(fArr);
            return this;
        }

        public Builder setSrcRightBottom(float[] fArr) {
            this.mPointMap.setSrcRightBottom(fArr);
            return this;
        }

        public CSPointMap build() {
            return this.mPointMap;
        }
    }
}
