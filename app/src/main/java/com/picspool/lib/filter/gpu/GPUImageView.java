package com.picspool.lib.filter.gpu;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.View;
import java.io.File;
import com.picspool.lib.filter.gpu.core.GPUImage;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilterGroup;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;
import com.picspool.lib.filter.gpu.util.Rotation;

/* loaded from: classes3.dex */
public class GPUImageView extends GLSurfaceView {
    private GPUImageFilter mFilter;
    public GPUImage mGPUImage;
    private float mRatio;

    public GPUImageView(Context context) {
        super(context);
        this.mRatio = 0.0f;
        init();
    }

    public GPUImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRatio = 0.0f;
        init();
    }

    private void init() {
        GPUImage gPUImage = new GPUImage(getContext());
        this.mGPUImage = gPUImage;
        gPUImage.setGLSurfaceView(this, false);
        this.mGPUImage.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.mGPUImage.setBackgroundColor(i);
    }

    public void setRotate(Rotation rotation) {
        this.mGPUImage.setRotate(rotation);
    }

    public void setRotation(Rotation rotation, boolean z, boolean z2) {
        this.mGPUImage.setRotation(rotation, z, z2);
    }

    public void setFlipHorizontally(boolean z) {
        this.mGPUImage.setFlipHorizontally(z);
    }

    public void setFlipVertically(boolean z) {
        this.mGPUImage.setFlipVertically(z);
    }

    public boolean getFlipHorizontally() {
        return this.mGPUImage.getFlipHorizontally();
    }

    public boolean getFlipVertically() {
        return this.mGPUImage.getFlipVertically();
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onMeasure(int i, int i2) {
        if (this.mRatio == 0.0f) {
            super.onMeasure(i, i2);
            return;
        }
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        float f = size;
        float f2 = this.mRatio;
        float f3 = size2;
        if (f / f2 < f3) {
            size2 = Math.round(f / f2);
        } else {
            size = Math.round(f3 * f2);
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(size2, 1073741824));
    }

    public void setRatio(float f) {
        this.mRatio = f;
        requestLayout();
        this.mGPUImage.deleteImage();
    }

    public void setFilter(GPUImageFilter gPUImageFilter) {
        if (this.mFilter != null) {
            recycleFilter();
        }
        this.mFilter = gPUImageFilter;
        this.mGPUImage.setFilter(gPUImageFilter);
        requestRender();
    }

    public void setFilterWithOutRender(GPUImageFilter gPUImageFilter) {
        if (this.mFilter != null) {
            recycleFilter();
        }
        this.mFilter = gPUImageFilter;
        this.mGPUImage.setFilterWithoutRender(gPUImageFilter);
    }

    private void recycleFilter() {
        GPUImageFilter gPUImageFilter = this.mFilter;
        if (gPUImageFilter instanceof GPUImageFilterGroup) {
            for (GPUImageFilter gPUImageFilter2 : ((GPUImageFilterGroup) gPUImageFilter).getFilters()) {
                recycleTexture(gPUImageFilter2);
            }
            return;
        }
        recycleTexture(gPUImageFilter);
    }

    public void setFilterNotRecycle(GPUImageFilter gPUImageFilter) {
        this.mFilter = gPUImageFilter;
        this.mGPUImage.setFilter(gPUImageFilter);
        requestRender();
    }

    public void recycleTexture(GPUImageFilter gPUImageFilter) {
        Bitmap textureBitmap;
        if (!(gPUImageFilter instanceof GPUImageTwoInputFilter) || (textureBitmap = ((GPUImageTwoInputFilter) gPUImageFilter).getTextureBitmap()) == null || textureBitmap.isRecycled()) {
            return;
        }
        textureBitmap.recycle();
    }

    public GPUImageFilter getFilter() {
        return this.mFilter;
    }

    public void setImage(Bitmap bitmap) {
        this.mGPUImage.setImage(bitmap);
    }

    public void setImageWithOutRender(Bitmap bitmap) {
        this.mGPUImage.setImageWithOutRender(bitmap);
    }

    public void setImage(Uri uri) {
        this.mGPUImage.setImage(uri);
    }

    public void setImage(File file) {
        this.mGPUImage.setImage(file);
    }

    public Bitmap getImage() {
        GPUImage gPUImage = this.mGPUImage;
        if (gPUImage != null) {
            return gPUImage.getImage();
        }
        return null;
    }

    public int getmImageWidth() {
        return this.mGPUImage.getmImageWidth();
    }

    public int getmImageHeight() {
        return this.mGPUImage.getmImageHeight();
    }

    public void saveToPictures(String str, String str2, GPUImage.OnPictureSavedListener onPictureSavedListener) {
        this.mGPUImage.saveToPictures(str, str2, onPictureSavedListener);
    }

    public Bitmap getBitmap() {
        return this.mGPUImage.getBitmapWithFilterApplied();
    }

    public void destroy() {
        recycleFilter();
    }

    public void setCleanBeforeDraw(boolean z) {
        GPUImage gPUImage = this.mGPUImage;
        if (gPUImage != null) {
            gPUImage.setCleanBeforeDraw(z);
        }
    }
}
