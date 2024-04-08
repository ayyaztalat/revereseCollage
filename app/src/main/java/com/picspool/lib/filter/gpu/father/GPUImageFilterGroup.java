package com.picspool.lib.filter.gpu.father;

import android.opengl.GLES20;
import android.util.Log;
import androidx.work.Data;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;
import com.picspool.lib.filter.gpu.core.GPUImageRenderer;
import com.picspool.lib.filter.gpu.util.Rotation;
import com.picspool.lib.filter.gpu.util.TextureRotationUtil;

/* loaded from: classes3.dex */
public class GPUImageFilterGroup extends GPUImageFilter {
    protected final List<GPUImageFilter> mFilters;
    protected int[] mFrameBufferTextures;
    protected int[] mFrameBuffers;
    protected final FloatBuffer mGLCubeBuffer;
    protected final FloatBuffer mGLTextureBuffer;
    protected final FloatBuffer mGLTextureFlipBuffer;
    protected boolean mFiltersMixed = false;
    protected boolean useImageOnDraw = false;
    protected boolean isShoDebug = false;
    protected boolean isUseEconomizeMode = false;
    protected boolean isUseFirstFrameBuffer = true;

    protected static float flip(float f) {
        return f == 0.0f ? 1.0f : 0.0f;
    }

    public GPUImageFilterGroup(List<GPUImageFilter> list) {
        this.mFilters = list;
        FloatBuffer asFloatBuffer = ByteBuffer.allocateDirect(GPUImageRenderer.CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mGLCubeBuffer = asFloatBuffer;
        asFloatBuffer.put(GPUImageRenderer.CUBE).position(0);
        FloatBuffer asFloatBuffer2 = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mGLTextureBuffer = asFloatBuffer2;
        asFloatBuffer2.put(TextureRotationUtil.TEXTURE_NO_ROTATION).position(0);
        float[] rotation = TextureRotationUtil.getRotation(Rotation.NORMAL, false, true);
        FloatBuffer asFloatBuffer3 = ByteBuffer.allocateDirect(rotation.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mGLTextureFlipBuffer = asFloatBuffer3;
        asFloatBuffer3.put(rotation).position(0);
        setGpuBgColors(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        List<GPUImageFilter> list = this.mFilters;
        if (list != null) {
            synchronized (list) {
                for (int i = 0; i < this.mFilters.size(); i++) {
                    this.mFilters.get(i).init();
                }
            }
        }
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onDestroy() {
        destroyFramebuffers();
        List<GPUImageFilter> list = this.mFilters;
        if (list != null) {
            synchronized (list) {
                for (int i = 0; i < this.mFilters.size(); i++) {
                    if (this.mFilters.get(i) != null) {
                        this.mFilters.get(i).destroy();
                    }
                }
            }
        }
        super.onDestroy();
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void setMix(float f) {
        this.mMix = f;
        if (this.mFiltersMixed) {
            return;
        }
        try {
            synchronized (this.mFilters) {
                for (int i = 0; i < this.mFilters.size(); i++) {
                    this.mFilters.get(i).setMix(f);
                }
            }
        } catch (Exception unused) {
        }
    }

    protected void destroyFramebuffers() {
        int[] iArr = this.mFrameBufferTextures;
        if (iArr != null) {
            GLES20.glDeleteTextures(iArr.length, iArr, 0);
            this.mFrameBufferTextures = null;
        }
        int[] iArr2 = this.mFrameBuffers;
        if (iArr2 != null) {
            GLES20.glDeleteFramebuffers(iArr2.length, iArr2, 0);
            this.mFrameBuffers = null;
        }
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(final int i, final int i2) {
        super.onOutputSizeChanged(i, i2);
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilterGroup.1
            @Override // java.lang.Runnable
            public void run() {
                if (GPUImageFilterGroup.this.mFrameBuffers != null) {
                    GPUImageFilterGroup.this.destroyFramebuffers();
                }
                try {
                    synchronized (GPUImageFilterGroup.this.mFilters) {
                        if (!GPUImageFilterGroup.this.isUseEconomizeMode) {
                            if (GPUImageFilterGroup.this.mFilters.size() == 0) {
                                return;
                            }
                            GPUImageFilterGroup.this.mFrameBuffers = new int[GPUImageFilterGroup.this.mFilters.size() - 1];
                            GPUImageFilterGroup.this.mFrameBufferTextures = new int[GPUImageFilterGroup.this.mFilters.size() - 1];
                            for (int i3 = 0; i3 < GPUImageFilterGroup.this.mFilters.size() - 1; i3++) {
                                GPUImageFilterGroup.this.mFilters.get(i3).onOutputSizeChanged(i, i2);
                                GLES20.glGenFramebuffers(1, GPUImageFilterGroup.this.mFrameBuffers, i3);
                                GLES20.glGenTextures(1, GPUImageFilterGroup.this.mFrameBufferTextures, i3);
                                GLES20.glBindTexture(3553, GPUImageFilterGroup.this.mFrameBufferTextures[i3]);
                                GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, null);
                                GLES20.glTexParameterf(3553, Data.MAX_DATA_BYTES, 9729.0f);
                                GLES20.glTexParameterf(3553, 10241, 9729.0f);
                                GLES20.glTexParameterf(3553, 10242, 33071.0f);
                                GLES20.glTexParameterf(3553, 10243, 33071.0f);
                                GLES20.glBindFramebuffer(36160, GPUImageFilterGroup.this.mFrameBuffers[i3]);
                                GLES20.glFramebufferTexture2D(36160, 36064, 3553, GPUImageFilterGroup.this.mFrameBufferTextures[i3], 0);
                                GLES20.glBindTexture(3553, 0);
                                GLES20.glBindFramebuffer(36160, 0);
                            }
                            GPUImageFilterGroup.this.mFilters.get(GPUImageFilterGroup.this.mFilters.size() - 1).onOutputSizeChanged(i, i2);
                        } else if (GPUImageFilterGroup.this.mFilters.size() != 0) {
                            int size = GPUImageFilterGroup.this.mFilters.size() - 1;
                            if (size > 2) {
                                size = 2;
                            }
                            for (int i4 = 0; i4 < GPUImageFilterGroup.this.mFilters.size(); i4++) {
                                GPUImageFilterGroup.this.mFilters.get(i4).onOutputSizeChanged(i, i2);
                            }
                            if (size > 0) {
                                GPUImageFilterGroup.this.mFrameBuffers = new int[size];
                                GPUImageFilterGroup.this.mFrameBufferTextures = new int[size];
                                for (int i5 = 0; i5 < size; i5++) {
                                    GLES20.glGenFramebuffers(1, GPUImageFilterGroup.this.mFrameBuffers, i5);
                                    GLES20.glGenTextures(1, GPUImageFilterGroup.this.mFrameBufferTextures, i5);
                                    GLES20.glBindTexture(3553, GPUImageFilterGroup.this.mFrameBufferTextures[i5]);
                                    GLES20.glTexImage2D(3553, 0, 6408, i, i2, 0, 6408, 5121, null);
                                    GLES20.glTexParameterf(3553, Data.MAX_DATA_BYTES, 9729.0f);
                                    GLES20.glTexParameterf(3553, 10241, 9729.0f);
                                    GLES20.glTexParameterf(3553, 10242, 33071.0f);
                                    GLES20.glTexParameterf(3553, 10243, 33071.0f);
                                    GLES20.glBindFramebuffer(36160, GPUImageFilterGroup.this.mFrameBuffers[i5]);
                                    GLES20.glFramebufferTexture2D(36160, 36064, 3553, GPUImageFilterGroup.this.mFrameBufferTextures[i5], 0);
                                    GLES20.glBindTexture(3553, 0);
                                    GLES20.glBindFramebuffer(36160, 0);
                                }
                            } else {
                                GPUImageFilterGroup.this.mFrameBuffers = new int[0];
                                GPUImageFilterGroup.this.mFrameBufferTextures = new int[0];
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void draw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        List<GPUImageFilter> list;
        GPUImageFilter gPUImageFilter;
        GPUImageFilter gPUImageFilter2;
        runPendingOnDrawTasks();
        if (!isInitialized() || this.mFrameBuffers == null || this.mFrameBufferTextures == null || (list = this.mFilters) == null) {
            return;
        }
        int i2 = 1;
        this.isUseFirstFrameBuffer = true;
        synchronized (list) {
            if (this.mFilters == null) {
                return;
            }
            List<GPUImageFilter> list2 = this.mFilters;
            if (list2.size() == 0) {
                return;
            }
            int lastUseFilterIndex = getLastUseFilterIndex();
            int i3 = 36160;
            if (!this.useImageOnDraw) {
                if (this.isShoDebug) {
                    Log.i("Test", "GPUImageView高或宽大于图片时加暗角等会对背景产生影响，估计由于先使用cubeBuffer, textureBuffer造成的");
                }
                int i4 = i;
                int i5 = 0;
                boolean z = false;
                while (i5 < list2.size() - i2) {
                    GPUImageFilter gPUImageFilter3 = list2.get(i5);
                    if (i5 != lastUseFilterIndex && gPUImageFilter3 != null && gPUImageFilter3.isUseFilter) {
                        if (!this.isUseEconomizeMode && i5 >= this.mFrameBuffers.length) {
                            return;
                        }
                        if (!this.isUseEconomizeMode) {
                            GLES20.glBindFramebuffer(i3, this.mFrameBuffers[i5]);
                        } else if (this.mFrameBuffers.length > 0) {
                            if (!this.isUseFirstFrameBuffer && this.mFrameBuffers.length > i2) {
                                GLES20.glBindFramebuffer(i3, this.mFrameBuffers[i2]);
                            }
                            GLES20.glBindFramebuffer(i3, this.mFrameBuffers[0]);
                        }
                        gPUImageFilter3.setmRotation(this.mRotate, this.isFlipHorizontal, this.isFlipVertical2);
                        GLES20.glClearColor(this.gpuBgColors[0], this.gpuBgColors[i2], this.gpuBgColors[2], this.gpuBgColors[3]);
                        GLES20.glClear(16384);
                        if (i5 == 0) {
                            gPUImageFilter3.setFilpVertical(false);
                            if (gPUImageFilter3 instanceof GPUImageTwoSrcInputFilter) {
                                ((GPUImageTwoSrcInputFilter) gPUImageFilter3).setSecondTexture(i, floatBuffer, floatBuffer2, true);
                            }
                            gPUImageFilter3.draw(i4, floatBuffer, floatBuffer2);
                            z = true;
                        } else {
                            gPUImageFilter3.setFilpVertical(true);
                            if (gPUImageFilter3 instanceof GPUImageTwoSrcInputFilter) {
                                ((GPUImageTwoSrcInputFilter) gPUImageFilter3).setSecondTexture(i, floatBuffer, floatBuffer2, true);
                            }
                            gPUImageFilter3.draw(i4, this.mGLCubeBuffer, this.mGLTextureFlipBuffer);
                        }
                        if (gPUImageFilter3 instanceof GPUImageTwoSrcInputFilter) {
                            ((GPUImageTwoSrcInputFilter) gPUImageFilter3).setSecondTexture(-1, floatBuffer, floatBuffer2, false);
                        }
                        GLES20.glBindFramebuffer(36160, 0);
                        if (!this.isUseEconomizeMode) {
                            i4 = this.mFrameBufferTextures[i5];
                        } else if (this.mFrameBuffers.length > 0) {
                            if (!this.isUseFirstFrameBuffer && this.mFrameBuffers.length > 1) {
                                this.isUseFirstFrameBuffer = true;
                                i4 = this.mFrameBufferTextures[1];
                            }
                            this.isUseFirstFrameBuffer = false;
                            i4 = this.mFrameBufferTextures[0];
                        }
                    }
                    i5++;
                    i2 = 1;
                    i3 = 36160;
                }
                if (lastUseFilterIndex >= 0 && lastUseFilterIndex < list2.size() && (gPUImageFilter2 = list2.get(lastUseFilterIndex)) != null) {
                    gPUImageFilter2.setmRotation(this.mRotate, this.isFlipHorizontal, this.isFlipVertical2);
                    if (!z) {
                        gPUImageFilter2.setFilpVertical(false);
                        if (gPUImageFilter2 instanceof GPUImageTwoSrcInputFilter) {
                            ((GPUImageTwoSrcInputFilter) gPUImageFilter2).setSecondTexture(i, floatBuffer, floatBuffer2, true);
                        }
                        gPUImageFilter2.draw(i4, floatBuffer, floatBuffer2);
                    } else {
                        gPUImageFilter2.setFilpVertical(true);
                        if (gPUImageFilter2 instanceof GPUImageTwoSrcInputFilter) {
                            ((GPUImageTwoSrcInputFilter) gPUImageFilter2).setSecondTexture(i, floatBuffer, floatBuffer2, true);
                        }
                        gPUImageFilter2.draw(i4, this.mGLCubeBuffer, this.mGLTextureFlipBuffer);
                    }
                    if (gPUImageFilter2 instanceof GPUImageTwoSrcInputFilter) {
                        ((GPUImageTwoSrcInputFilter) gPUImageFilter2).setSecondTexture(-1, floatBuffer, floatBuffer2, false);
                    }
                }
            } else {
                if (this.isShoDebug) {
                    Log.i("Test", "用贴纸相机时会造成贴纸翻转90度的问题");
                }
                int i6 = i;
                for (int i7 = 0; i7 < list2.size() - 1; i7++) {
                    GPUImageFilter gPUImageFilter4 = list2.get(i7);
                    if (!this.isUseEconomizeMode && i7 >= this.mFrameBuffers.length) {
                        return;
                    }
                    if (i7 != lastUseFilterIndex && gPUImageFilter4.isUseFilter) {
                        gPUImageFilter4.setmRotation(this.mRotate, this.isFlipHorizontal, this.isFlipVertical2);
                        if (!this.isUseEconomizeMode) {
                            GLES20.glBindFramebuffer(36160, this.mFrameBuffers[i7]);
                        } else {
                            if (!this.isUseFirstFrameBuffer && this.mFrameBuffers.length > 1) {
                                GLES20.glBindFramebuffer(36160, this.mFrameBuffers[1]);
                            }
                            GLES20.glBindFramebuffer(36160, this.mFrameBuffers[0]);
                        }
                        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                        gPUImageFilter4.setFilpVertical(true);
                        if (gPUImageFilter4 instanceof GPUImageTwoSrcInputFilter) {
                            ((GPUImageTwoSrcInputFilter) gPUImageFilter4).setSecondTexture(i, floatBuffer, floatBuffer2, true);
                        }
                        gPUImageFilter4.draw(i6, this.mGLCubeBuffer, this.mGLTextureFlipBuffer);
                        if (gPUImageFilter4 instanceof GPUImageTwoSrcInputFilter) {
                            ((GPUImageTwoSrcInputFilter) gPUImageFilter4).setSecondTexture(-1, floatBuffer, floatBuffer2, false);
                        }
                        GLES20.glBindFramebuffer(36160, 0);
                        if (!this.isUseEconomizeMode) {
                            i6 = this.mFrameBufferTextures[i7];
                        } else {
                            if (!this.isUseFirstFrameBuffer && this.mFrameBuffers.length > 1) {
                                this.isUseFirstFrameBuffer = true;
                                i6 = this.mFrameBufferTextures[1];
                            }
                            this.isUseFirstFrameBuffer = false;
                            i6 = this.mFrameBufferTextures[0];
                        }
                    }
                }
                if (list2.size() <= 0) {
                    return;
                }
                if (lastUseFilterIndex >= 0 && lastUseFilterIndex < list2.size() && (gPUImageFilter = list2.get(lastUseFilterIndex)) != null) {
                    gPUImageFilter.setmRotation(this.mRotate, this.isFlipHorizontal, this.isFlipVertical2);
                    gPUImageFilter.setFilpVertical(false);
                    if (gPUImageFilter instanceof GPUImageTwoSrcInputFilter) {
                        ((GPUImageTwoSrcInputFilter) gPUImageFilter).setSecondTexture(i, floatBuffer, floatBuffer2, true);
                    }
                    gPUImageFilter.draw(i6, floatBuffer, floatBuffer2);
                    if (gPUImageFilter instanceof GPUImageTwoSrcInputFilter) {
                        ((GPUImageTwoSrcInputFilter) gPUImageFilter).setSecondTexture(-1, floatBuffer, floatBuffer2, false);
                    }
                }
            }
        }
    }

    protected int getLastUseFilterIndex() {
        List<GPUImageFilter> list = this.mFilters;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                GPUImageFilter gPUImageFilter = this.mFilters.get(size);
                if (gPUImageFilter != null && gPUImageFilter.isUseFilter) {
                    return size;
                }
            }
            return -1;
        }
        return -1;
    }

    public List<GPUImageFilter> getFilters() {
        return this.mFilters;
    }

    public void addFilter(GPUImageFilter gPUImageFilter) {
        addFilter(gPUImageFilter, -1);
    }

    public void addFilter(GPUImageFilter gPUImageFilter, int i) {
        List<GPUImageFilter> list = this.mFilters;
        if (list == null || gPUImageFilter == null) {
            return;
        }
        synchronized (list) {
            if (i >= 0) {
                if (i < this.mFilters.size()) {
                    this.mFilters.add(i, gPUImageFilter);
                }
            }
            this.mFilters.add(gPUImageFilter);
        }
    }

    public void removeAllFilters() {
        List<GPUImageFilter> list = this.mFilters;
        if (list != null) {
            synchronized (list) {
                while (this.mFilters.size() > 0) {
                    GPUImageFilter remove = this.mFilters.remove(0);
                    if (remove != null) {
                        remove.destroy();
                    }
                }
            }
        }
    }

    public void removeAllFiltersWithoutDestroy() {
        List<GPUImageFilter> list = this.mFilters;
        if (list != null) {
            synchronized (list) {
                this.mFilters.clear();
            }
        }
    }

    public void removeFilter(GPUImageFilter gPUImageFilter) {
        List<GPUImageFilter> list = this.mFilters;
        if (list != null) {
            synchronized (list) {
                int indexOf = this.mFilters.indexOf(gPUImageFilter);
                if (indexOf >= 0) {
                    removeFilter(indexOf);
                }
            }
        }
    }

    public void removeFilter(int i) {
        GPUImageFilter remove;
        List<GPUImageFilter> list = this.mFilters;
        if (list != null) {
            synchronized (list) {
                if (i >= 0) {
                    if (i < this.mFilters.size() && (remove = this.mFilters.remove(i)) != null) {
                        remove.destroy();
                    }
                }
            }
        }
    }

    public GPUImageFilter removeFilterWithoutDestroy(GPUImageFilter gPUImageFilter) {
        List<GPUImageFilter> list = this.mFilters;
        if (list != null) {
            synchronized (list) {
                int indexOf = this.mFilters.indexOf(gPUImageFilter);
                if (indexOf >= 0) {
                    return removeFilterWithoutDestroy(indexOf);
                }
                return null;
            }
        }
        return null;
    }

    public GPUImageFilter removeFilterWithoutDestroy(int i) {
        List<GPUImageFilter> list = this.mFilters;
        if (list != null) {
            synchronized (list) {
                if (i >= 0) {
                    if (i < this.mFilters.size()) {
                        return this.mFilters.remove(i);
                    }
                }
                return null;
            }
        }
        return null;
    }

    public boolean isContainFilter(GPUImageFilter gPUImageFilter) {
        List<GPUImageFilter> list = this.mFilters;
        return list != null && list.contains(gPUImageFilter);
    }

    public void setUseImageOnDraw(boolean z) {
        this.useImageOnDraw = z;
    }

    public boolean isUseImageOnDraw() {
        return this.useImageOnDraw;
    }

    public void setShowDebug(boolean z) {
        this.isShoDebug = z;
    }

    public void setUseEconomizeMode(boolean z) {
        if (this.isShoDebug && z) {
            Log.i("Test", "Use economize mode : use max 2 frame buffer process filter!");
        }
        this.isUseEconomizeMode = z;
    }
}
