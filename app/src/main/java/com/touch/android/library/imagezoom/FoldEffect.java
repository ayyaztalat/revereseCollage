package com.touch.android.library.imagezoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import androidx.core.view.ViewCompat;

/* loaded from: classes3.dex */
public class FoldEffect {
    private static final int NUM_OF_POINT = 8;
    private Context context;
    private int imgHeight;
    private int imgWidth;
    private Bitmap mBitmap;
    private float mFlodWidth;
    private Matrix mShadowGradientMatrix;
    private LinearGradient mShadowGradientShader;
    private Paint mShadowPaint;
    private Paint mSolidPaint;
    private float mTranslateDis;
    private float mTranslateDisPerFlod;
    protected float mFactor = 1.0f;
    private int mNumOfFolds = 8;
    private Matrix[] mMatrices = new Matrix[8];
    private float mAnchor = 0.0f;

    public FoldEffect(Context context) {
        this.context = context;
        for (int i = 0; i < this.mNumOfFolds; i++) {
            this.mMatrices[i] = new Matrix();
        }
        this.mSolidPaint = new Paint();
        Paint paint = new Paint();
        this.mShadowPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.5f, 0.0f, (int) ViewCompat.MEASURED_STATE_MASK, 0, Shader.TileMode.CLAMP);
        this.mShadowGradientShader = linearGradient;
        this.mShadowPaint.setShader(linearGradient);
        this.mShadowGradientMatrix = new Matrix();
    }

    private void updateFold() {
        float f;
        int i = this.imgWidth;
        int i2 = this.imgHeight;
        float f2 = i;
        float f3 = this.mFactor;
        float f4 = f2 * f3;
        this.mTranslateDis = f4;
        int i3 = this.mNumOfFolds;
        this.mFlodWidth = i / i3;
        this.mTranslateDisPerFlod = f4 / i3;
        int i4 = (int) ((1.0f - f3) * 255.0f);
        char c = 0;
        this.mSolidPaint.setColor(Color.argb((int) (i4 * 0.8f), 0, 0, 0));
        this.mShadowGradientMatrix.setScale(this.mFlodWidth, 1.0f);
        this.mShadowGradientShader.setLocalMatrix(this.mShadowGradientMatrix);
        this.mShadowPaint.setAlpha(i4);
        float f5 = this.mFlodWidth;
        float f6 = this.mTranslateDisPerFlod;
        float sqrt = (float) (Math.sqrt((f5 * f5) - (f6 * f6)) / 2.0d);
        float f7 = this.mAnchor * f2;
        float f8 = f7 / this.mFlodWidth;
        float[] fArr = new float[8];
        float[] fArr2 = new float[8];
        int i5 = 0;
        while (i5 < this.mNumOfFolds) {
            this.mMatrices[i5].reset();
            float f9 = i5;
            float f10 = this.mFlodWidth;
            fArr[c] = f9 * f10;
            fArr[1] = 0.0f;
            fArr[2] = fArr[c] + f10;
            fArr[3] = 0.0f;
            fArr[4] = fArr[2];
            float f11 = i2;
            fArr[5] = f11;
            fArr[6] = fArr[c];
            fArr[7] = fArr[5];
            boolean z = i5 % 2 == 0;
            fArr2[c] = this.mTranslateDisPerFlod * f9;
            fArr2[1] = z ? 0.0f : sqrt;
            float f12 = fArr2[c];
            float f13 = this.mTranslateDisPerFlod;
            fArr2[2] = f12 + f13;
            fArr2[c] = f7 > this.mFlodWidth * f9 ? ((f9 - f8) * f13) + f7 : f7 - ((f8 - f9) * f13);
            int i6 = i5 + 1;
            float f14 = i6;
            if (f7 > this.mFlodWidth * f14) {
                f = ((f14 - f8) * this.mTranslateDisPerFlod) + f7;
            } else {
                f = f7 - (((f8 - f9) - 1.0f) * this.mTranslateDisPerFlod);
            }
            fArr2[2] = f;
            fArr2[3] = z ? sqrt : 0.0f;
            fArr2[4] = fArr2[2];
            fArr2[5] = z ? f11 - sqrt : f11;
            c = 0;
            fArr2[6] = fArr2[0];
            if (!z) {
                f11 -= sqrt;
            }
            fArr2[7] = f11;
            for (int i7 = 0; i7 < 8; i7++) {
                fArr2[i7] = Math.round(fArr2[i7]);
            }
            this.mMatrices[i5].setPolyToPoly(fArr, 0, fArr2, 0, 4);
            i5 = i6;
        }
    }

    public Bitmap getFoldBitmap(Bitmap bitmap, float f) {
        Bitmap createBitmap = Bitmap.createBitmap((int) (bitmap.getWidth() * f), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        this.mBitmap = bitmap;
        this.imgWidth = bitmap.getWidth();
        this.imgHeight = this.mBitmap.getHeight();
        setFactor(f);
        draw(new Canvas(createBitmap));
        return createBitmap;
    }

    public void draw(Canvas canvas) {
        if (this.mFactor == 0.0f) {
            return;
        }
        for (int i = 0; i < this.mNumOfFolds; i++) {
            canvas.save();
            canvas.concat(this.mMatrices[i]);
            Bitmap bitmap = this.mBitmap;
            float f = this.mFlodWidth;
            float f2 = i;
            Rect rect = new Rect((int) (f * f2), 0, (int) ((f * f2) + f), this.imgHeight);
            float f3 = this.mFlodWidth;
            canvas.drawBitmap(bitmap, rect, new Rect((int) (f3 * f2), 0, (int) ((f3 * f2) + f3), this.imgHeight), (Paint) null);
            float f4 = this.mFlodWidth;
            canvas.clipRect(f4 * f2, 0.0f, (f4 * f2) + f4, this.imgHeight);
            canvas.translate(this.mFlodWidth * f2, 0.0f);
            if (i % 2 == 0) {
                canvas.drawRect(0.0f, 0.0f, this.mFlodWidth, this.imgHeight, this.mSolidPaint);
            } else {
                canvas.drawRect(0.0f, 0.0f, this.mFlodWidth, this.imgHeight, this.mShadowPaint);
            }
            canvas.restore();
        }
    }

    public void setFactor(float f) {
        this.mFactor = f;
        updateFold();
    }

    public float getFactor() {
        return this.mFactor;
    }

    public void setAnchor(float f) {
        this.mAnchor = f;
        updateFold();
    }
}
