package com.photo.editor.square.splash.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import com.picspool.instafilter.GPUFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class AdjustInfo {
    private ColorMatrix adjustColorMatrix;
    private GPUImageFilter adjustImageFilter;
    private ColorMatrix colorColorMatrix;
    private ColorMatrixColorFilter colorMatrixColorFilter;
    private Context mContext;
    private float ratio;
    private Bitmap result;
    private Bitmap src;

    public AdjustInfo(Context context, Bitmap bitmap) {
        this.mContext = context;
        this.src = bitmap;
    }

    public void setAdjust(ColorMatrix colorMatrix, ColorMatrix colorMatrix2, GPUImageFilter gPUImageFilter) {
        ColorMatrix colorMatrix3 = new ColorMatrix();
        if (colorMatrix != null) {
            colorMatrix3.postConcat(colorMatrix);
        }
        if (colorMatrix2 != null) {
            colorMatrix3.postConcat(colorMatrix2);
        }
        this.colorMatrixColorFilter = new ColorMatrixColorFilter(colorMatrix3);
        this.adjustImageFilter = gPUImageFilter;
        updateImagePic();
    }

    public void setAdjust(ColorMatrix colorMatrix) {
        this.colorColorMatrix = colorMatrix;
        setAdjust(colorMatrix, this.adjustColorMatrix, this.adjustImageFilter);
    }

    public void setAdjust(ColorMatrix colorMatrix, GPUImageFilter gPUImageFilter) {
        this.adjustColorMatrix = colorMatrix;
        setAdjust(this.colorColorMatrix, colorMatrix, gPUImageFilter);
    }

    private void updateImagePic() {
        Bitmap bitmap = this.src;
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        this.result = this.src;
        if (this.colorMatrixColorFilter != null) {
            Paint paint = new Paint(1);
            paint.setColorFilter(this.colorMatrixColorFilter);
            Bitmap createBitmap = Bitmap.createBitmap(this.result.getWidth(), this.result.getHeight(), Bitmap.Config.ARGB_8888);
            new Canvas(createBitmap).drawBitmap(this.result, 0.0f, 0.0f, paint);
            this.result = createBitmap;
        }
        GPUImageFilter gPUImageFilter = this.adjustImageFilter;
        if (gPUImageFilter != null) {
            this.result = GPUFilter.filterForFilter(this.result, gPUImageFilter);
        }
    }

    public Bitmap getResult() {
        Bitmap bitmap = this.result;
        return bitmap == null ? this.src : bitmap;
    }

    public Bitmap getSrc() {
        return this.src;
    }
}
