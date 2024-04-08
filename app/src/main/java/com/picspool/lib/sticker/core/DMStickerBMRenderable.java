package com.picspool.lib.sticker.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Log;
import com.picspool.lib.border.res.DMWBBorderRes;
import com.picspool.lib.sticker.resource.DMSingleStickerRes;
import com.picspool.lib.sticker.util.DMRenderable;

/* loaded from: classes3.dex */
public class DMStickerBMRenderable extends DMRenderable implements Cloneable {
    private DMSticker mBMSticker;
    private Boolean mIsSelected;
    private DMTransformMatrix mTrans;

    public DMSticker getSticker() {
        return this.mBMSticker;
    }

    public void setSticker(DMSticker dMSticker) {
        this.mBMSticker = dMSticker;
        this.width = dMSticker.getWidth();
        this.height = dMSticker.getHeight();
    }

    public DMTransformMatrix getmTrans() {
        return this.mTrans;
    }

    public void setmTrans(DMTransformMatrix dMTransformMatrix) {
        this.mTrans = dMTransformMatrix;
    }

    public Boolean getmIsSelected() {
        return this.mIsSelected;
    }

    public void setmIsSelected(Boolean bool) {
        this.mIsSelected = bool;
    }

    public DMStickerBMRenderable(DMSticker dMSticker) {
        this.mBMSticker = dMSticker;
        this.width = dMSticker.getWidth();
        this.height = dMSticker.getHeight();
        init();
    }

    public DMStickerBMRenderable clone() throws CloneNotSupportedException {
        DMStickerBMRenderable dMStickerBMRenderable = (DMStickerBMRenderable) super.clone();
        dMStickerBMRenderable.mTrans = new DMTransformMatrix();
        return dMStickerBMRenderable;
    }

    protected void init() {
        if (this.mBMSticker != null) {
            this.mTrans = new DMTransformMatrix();
        }
    }

    public Matrix newScaleTransform() {
        return this.mTrans.newScaleTransform;
    }

    public void setNewScaleTransform(Matrix matrix) {
        this.mTrans.newScaleTransform = matrix;
    }

    public Matrix lastScaleTransform() {
        return this.mTrans.lastScaleTransform;
    }

    public void setLastScaleTransform(Matrix matrix) {
        this.mTrans.lastScaleTransform.postConcat(matrix);
    }

    public Matrix newPanTransform() {
        return this.mTrans.newPanTransform;
    }

    public void setNewPanTransform(Matrix matrix) {
        this.mTrans.newPanTransform = matrix;
    }

    public Matrix lastPanTransform() {
        return this.mTrans.lastPanTransform;
    }

    public void setLastPanTransform(Matrix matrix) {
        this.mTrans.lastPanTransform.postConcat(matrix);
    }

    public Matrix newRotateTransform() {
        return this.mTrans.newRotateTransform;
    }

    public void setNewRotateTransform(Matrix matrix) {
        this.mTrans.newRotateTransform = matrix;
    }

    public Matrix lastRotateTransform() {
        return this.mTrans.lastRotateTransform;
    }

    public void setLastRotateTransform(Matrix matrix) {
        this.mTrans.lastRotateTransform.postConcat(matrix);
    }

    public Matrix makeTransform() {
        Matrix matrix = new Matrix();
        matrix.setTranslate(this.width / 2.0f, this.height / 2.0f);
        matrix.preConcat(lastScaleTransform());
        matrix.preConcat(newScaleTransform());
        matrix.preConcat(lastRotateTransform());
        matrix.preConcat(newRotateTransform());
        matrix.preTranslate((-this.width) / 2.0f, (-this.height) / 2.0f);
        matrix.postConcat(lastPanTransform());
        matrix.postConcat(newPanTransform());
        return matrix;
    }

    public boolean containsPt(float f, float f2) {
        Matrix matrix = new Matrix();
        if (makeTransform().invert(matrix)) {
            float[] fArr = {f, f2};
            matrix.mapPoints(fArr);
            return new RectF(0.0f, 0.0f, this.width, this.height).contains(fArr[0], fArr[1]);
        }
        return false;
    }

    public void draw(Canvas canvas) {
        if (this.isVisible) {
            this.mBMSticker.transform = makeTransform();
            this.mBMSticker.drawInCanvas(canvas);
        }
    }

    public DMSingleStickerRes getResultSticker(int i, int i2, int i3) {
        DMSticker dMSticker = this.mBMSticker;
        if (dMSticker == null) {
            return null;
        }
        dMSticker.transform = makeTransform();
        Matrix matrix = new Matrix(this.mBMSticker.transform);
        float f = (i3 * 1.0f) / (i * 1.0f);
        matrix.postScale(f, f);
        DMSticker dMSticker2 = this.mBMSticker;
        if (dMSticker2 != null && dMSticker2.getBitmap() != null) {
            RectF rectF = new RectF(0.0f, 0.0f, this.mBMSticker.getBitmap().getWidth(), this.mBMSticker.getBitmap().getHeight());
            matrix.mapRect(rectF);
            int i4 = (int) (rectF.right - rectF.left);
            int i5 = (int) (rectF.bottom - rectF.top);
            if (i4 > 10 && i5 > 10) {
                Bitmap bitmap = this.mBMSticker.getBitmap();
                if (bitmap != null && !bitmap.isRecycled()) {
                    Bitmap createBitmap = Bitmap.createBitmap(i4, i5, Bitmap.Config.ARGB_8888);
                    if (createBitmap != null && !createBitmap.isRecycled()) {
                        Canvas canvas = new Canvas(createBitmap);
                        matrix.postTranslate(-rectF.left, -rectF.top);
                        canvas.drawBitmap(bitmap, matrix, this.mBMSticker.mPaint);
                        DMSingleStickerRes dMSingleStickerRes = new DMSingleStickerRes();
                        dMSingleStickerRes.setStickerBmp(createBitmap);
                        dMSingleStickerRes.setStickerPosition((int) rectF.left, (int) rectF.top, i4, i5);
                        return dMSingleStickerRes;
                    }
                    Log.i("InstaSticker", "crop bitmap is null");
                } else {
                    Log.i("InstaSticker", "bitmap is null");
                }
            } else {
                Log.i("InstaSticker", "nw or nh < 10");
            }
        }
        return null;
    }

    public void addWBBorderRes(DMWBBorderRes dMWBBorderRes) {
        this.mBMSticker.addWBBorderRes(dMWBBorderRes);
    }
}
