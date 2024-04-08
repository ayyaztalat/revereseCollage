package com.picspool.lib.filter.gpu.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import androidx.work.Data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.Queue;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.picspool.lib.filter.gpu.core.GPUImage;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.util.OpenGlUtils;
import com.picspool.lib.filter.gpu.util.Rotation;
import com.picspool.lib.filter.gpu.util.TextureRotationUtil;
import com.sky.testproject.GPUImageNativeLibrary;

/* loaded from: classes3.dex */
public class GPUImageRenderer implements GLSurfaceView.Renderer, Camera.PreviewCallback, SurfaceTexture.OnFrameAvailableListener {
    public static final float[] CUBE = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    public static final int NO_IMAGE = -1;
    private float[] gpuBgColors;
    private int mAddedPadding;
    private GPUImageFilter mFilter;
    private boolean mFlipHorizontal;
    private boolean mFlipVertical;
    private final FloatBuffer mGLCubeBuffer;
    private IntBuffer mGLRgbBuffer;
    private final FloatBuffer mGLTextureBuffer;
    private int mImageHeight;
    private int mImageWidth;
    private OnPreviewRendererListener mListener;
    private int mOutputHeight;
    private int mOutputWidth;
    private Rotation mRotation;
    public final Object mSurfaceChangedWaiter = new Object();
    private int mGLTextureId = -1;
    private SurfaceTexture mSurfaceTexture = null;
    private boolean changeCamera = false;
    private boolean isCameraRotation = false;
    private boolean isAdjustScale = false;
    private boolean isUseTexture = false;
    private GPUImage.ScaleType mScaleType = GPUImage.ScaleType.CENTER_CROP;
    private boolean isArm64V8a = false;
    private boolean isCleanBeforeDraw = true;
    private final Queue<Runnable> mRunOnDraw = new LinkedList();

    /* loaded from: classes3.dex */
    public interface OnPreviewRendererListener {
        void onRequestRenderer();
    }

    private float addDistance(float f, float f2) {
        return f == 0.0f ? f2 : 1.0f - f2;
    }

    public void setOnPreviewRendererListener(OnPreviewRendererListener onPreviewRendererListener) {
        this.mListener = onPreviewRendererListener;
    }

    public GPUImageRenderer(GPUImageFilter gPUImageFilter) {
        this.mFilter = gPUImageFilter;
        FloatBuffer asFloatBuffer = ByteBuffer.allocateDirect(CUBE.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mGLCubeBuffer = asFloatBuffer;
        asFloatBuffer.put(CUBE).position(0);
        this.mGLTextureBuffer = ByteBuffer.allocateDirect(TextureRotationUtil.TEXTURE_NO_ROTATION.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        setRotation(Rotation.NORMAL, false, false);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glDisable(2929);
        this.mFilter.init();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        this.mOutputWidth = i;
        this.mOutputHeight = i2;
        GLES20.glViewport(0, 0, i, i2);
        GLES20.glUseProgram(this.mFilter.getProgram());
        this.mFilter.onOutputSizeChanged(i, i2);
        synchronized (this.mSurfaceChangedWaiter) {
            this.mSurfaceChangedWaiter.notifyAll();
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClear(16640);
        synchronized (this.mRunOnDraw) {
            while (!this.mRunOnDraw.isEmpty()) {
                this.mRunOnDraw.poll().run();
            }
        }
        try {
            if (this.mSurfaceTexture != null) {
                this.mSurfaceTexture.updateTexImage();
            }
        } catch (Exception unused) {
        }
        if (this.mGLTextureId == -1 || !this.isAdjustScale) {
            return;
        }
        if (this.isCleanBeforeDraw) {
            float[] fArr = this.gpuBgColors;
            if (fArr == null || fArr.length < 4) {
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            } else {
                GLES20.glClearColor(fArr[0], fArr[1], fArr[2], fArr[3]);
                GLES20.glClear(16384);
                GPUImageFilter gPUImageFilter = this.mFilter;
                if (gPUImageFilter != null) {
                    float[] fArr2 = this.gpuBgColors;
                    gPUImageFilter.setGpuBgColors(fArr2[0], fArr2[1], fArr2[2], fArr2[3]);
                }
            }
        }
        GPUImageFilter gPUImageFilter2 = this.mFilter;
        if (gPUImageFilter2 != null) {
            gPUImageFilter2.setmRotation(this.mRotation, this.mFlipHorizontal, this.mFlipVertical);
            this.mFilter.draw(this.mGLTextureId, this.mGLCubeBuffer, this.mGLTextureBuffer);
        }
    }

    @Override // android.hardware.Camera.PreviewCallback
    public void onPreviewFrame(final byte[] bArr, final Camera camera) {
        final Camera.Size previewSize = camera.getParameters().getPreviewSize();
        if (this.mGLRgbBuffer == null) {
            this.mGLRgbBuffer = IntBuffer.allocate(previewSize.width * previewSize.height);
        }
        if (this.mRunOnDraw.isEmpty()) {
            runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.core.GPUImageRenderer.1
                @Override // java.lang.Runnable
                public void run() {
                    if (GPUImageRenderer.this.changeCamera || GPUImageRenderer.this.mImageWidth != previewSize.width) {
                        GPUImageRenderer.this.mImageWidth = previewSize.width;
                        GPUImageRenderer.this.mImageHeight = previewSize.height;
                        GPUImageRenderer.this.changeCamera = false;
                        GPUImageRenderer.this.adjustImageScaling();
                        GPUImageRenderer.this.isAdjustScale = true;
                    }
                    if (!GPUImageRenderer.this.isUseTexture) {
                        GPUImageNativeLibrary.YUVtoRBGA(bArr, previewSize.width, previewSize.height, GPUImageRenderer.this.mGLRgbBuffer.array());
                        GPUImageRenderer gPUImageRenderer = GPUImageRenderer.this;
                        gPUImageRenderer.mGLTextureId = OpenGlUtils.loadTexture(gPUImageRenderer.mGLRgbBuffer, previewSize, GPUImageRenderer.this.mGLTextureId);
                    }
                    camera.addCallbackBuffer(bArr);
                }
            });
        }
    }

    public void setUpSurfaceTexture(final Camera camera) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.core.GPUImageRenderer.2
            @Override // java.lang.Runnable
            public void run() {
                GPUImageRenderer.this.isUseTexture = true;
                if (GPUImageRenderer.this.mGLTextureId != -1) {
                    GLES20.glDeleteTextures(1, new int[]{GPUImageRenderer.this.mGLTextureId}, 0);
                }
                GPUImageRenderer gPUImageRenderer = GPUImageRenderer.this;
                gPUImageRenderer.mGLTextureId = gPUImageRenderer.createTextureID();
                GPUImageRenderer.this.mSurfaceTexture = new SurfaceTexture(GPUImageRenderer.this.mGLTextureId);
                GPUImageRenderer.this.mSurfaceTexture.setOnFrameAvailableListener(GPUImageRenderer.this);
                try {
                    GPUImageRenderer.this.isAdjustScale = false;
                    camera.setPreviewTexture(GPUImageRenderer.this.mSurfaceTexture);
                    camera.setPreviewCallback(GPUImageRenderer.this);
                    camera.startPreview();
                    GPUImageRenderer.this.changeCamera = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        OnPreviewRendererListener onPreviewRendererListener = this.mListener;
        if (onPreviewRendererListener != null) {
            onPreviewRendererListener.onRequestRenderer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int createTextureID() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, Data.MAX_DATA_BYTES, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        return iArr[0];
    }

    public void setFilter(final GPUImageFilter gPUImageFilter) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.core.GPUImageRenderer.3
            @Override // java.lang.Runnable
            public void run() {
                GPUImageFilter gPUImageFilter2 = GPUImageRenderer.this.mFilter;
                GPUImageRenderer.this.mFilter = gPUImageFilter;
                if (gPUImageFilter2 != null) {
                    gPUImageFilter2.destroy();
                }
                if (GPUImageRenderer.this.mFilter != null) {
                    GPUImageRenderer.this.mFilter.init();
                    GLES20.glUseProgram(GPUImageRenderer.this.mFilter.getProgram());
                    GPUImageRenderer.this.mFilter.onOutputSizeChanged(GPUImageRenderer.this.mOutputWidth, GPUImageRenderer.this.mOutputHeight);
                }
            }
        });
    }

    public void deleteImage() {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.core.GPUImageRenderer.4
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glDeleteTextures(1, new int[]{GPUImageRenderer.this.mGLTextureId}, 0);
                GPUImageRenderer.this.mGLTextureId = -1;
            }
        });
    }

    public void setImageBitmap(Bitmap bitmap) {
        setImageBitmap(bitmap, true);
    }

    public void setImageBitmap(final Bitmap bitmap, final boolean z) {
        if (bitmap == null || bitmap.isRecycled()) {
            deleteImage();
            return;
        }
        this.isAdjustScale = true;
        this.isUseTexture = false;
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.core.GPUImageRenderer.5
            @Override // java.lang.Runnable
            public void run() {
                Bitmap bitmap2 = null;
                if (bitmap.getWidth() % 2 != 1) {
                    GPUImageRenderer.this.mAddedPadding = 0;
                } else {
                    Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth() - 1, bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(createBitmap);
                    canvas.drawARGB(0, 0, 0, 0);
                    Bitmap bitmap3 = bitmap;
                    if (bitmap3 != null && !bitmap3.isRecycled()) {
                        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                    }
                    GPUImageRenderer.this.mAddedPadding = 1;
                    bitmap2 = createBitmap;
                }
                GPUImageRenderer.this.mGLTextureId = OpenGlUtils.loadTexture(bitmap2 != null ? bitmap2 : bitmap, GPUImageRenderer.this.mGLTextureId, z);
                if (bitmap2 != null) {
                    bitmap2.recycle();
                }
                GPUImageRenderer.this.mImageWidth = bitmap.getWidth();
                GPUImageRenderer.this.mImageHeight = bitmap.getHeight();
                GPUImageRenderer.this.adjustImageScaling();
            }
        });
    }

    public void setScaleType(GPUImage.ScaleType scaleType) {
        this.mScaleType = scaleType;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getFrameWidth() {
        return this.mOutputWidth;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getFrameHeight() {
        return this.mOutputHeight;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void adjustImageScaling() {
        float f;
        float f2;
        float f3 = this.mOutputWidth;
        float f4 = this.mOutputHeight;
        if (this.mRotation == Rotation.ROTATION_270 || this.mRotation == Rotation.ROTATION_90) {
            f3 = this.mOutputHeight;
            f4 = this.mOutputWidth;
        }
        float min = Math.min(f3 / this.mImageWidth, f4 / this.mImageHeight);
        int round = Math.round(this.mImageWidth * min);
        int round2 = Math.round(this.mImageHeight * min);
        float f5 = round;
        if (f5 != f3) {
            f2 = f5 / f3;
            f = 1.0f;
        } else {
            float f6 = round2;
            f = f6 != f4 ? f6 / f4 : 1.0f;
            f2 = 1.0f;
        }
        float[] fArr = CUBE;
        float[] rotation = TextureRotationUtil.getRotation(this.mRotation, this.mFlipHorizontal, this.mFlipVertical);
        if (this.mScaleType == GPUImage.ScaleType.CENTER_CROP) {
            float f7 = (1.0f - f2) / 2.0f;
            float f8 = (1.0f - f) / 2.0f;
            rotation = new float[]{addDistance(rotation[0], f8), addDistance(rotation[1], f7), addDistance(rotation[2], f8), addDistance(rotation[3], f7), addDistance(rotation[4], f8), addDistance(rotation[5], f7), addDistance(rotation[6], f8), addDistance(rotation[7], f7)};
        } else if (!this.isCameraRotation) {
            if (this.mRotation == Rotation.ROTATION_90 || this.mRotation == Rotation.ROTATION_270) {
                float[] fArr2 = CUBE;
                fArr = new float[]{fArr2[0] * f, fArr2[1] * f2, fArr2[2] * f, fArr2[3] * f2, fArr2[4] * f, fArr2[5] * f2, fArr2[6] * f, fArr2[7] * f2};
            } else {
                float[] fArr3 = CUBE;
                fArr = new float[]{fArr3[0] * f2, fArr3[1] * f, fArr3[2] * f2, fArr3[3] * f, fArr3[4] * f2, fArr3[5] * f, fArr3[6] * f2, fArr3[7] * f};
            }
        } else {
            float[] fArr4 = CUBE;
            fArr = new float[]{fArr4[0] * f2, fArr4[1] * f, fArr4[2] * f2, fArr4[3] * f, fArr4[4] * f2, fArr4[5] * f, fArr4[6] * f2, fArr4[7] * f};
        }
        this.mGLCubeBuffer.clear();
        this.mGLCubeBuffer.put(fArr).position(0);
        this.mGLTextureBuffer.clear();
        this.mGLTextureBuffer.put(rotation).position(0);
    }

    public void setRotationCamera(Rotation rotation, boolean z, boolean z2) {
        this.mRotation = rotation;
        this.mFlipHorizontal = z2;
        this.mFlipVertical = z;
        this.isCameraRotation = true;
    }

    public void setRotate(Rotation rotation) {
        this.mRotation = rotation;
        adjustImageScaling();
    }

    public void setFlipHorizontally(boolean z) {
        this.mFlipHorizontal = z;
        adjustImageScaling();
    }

    public void setFlipVertically(boolean z) {
        this.mFlipVertical = z;
        adjustImageScaling();
    }

    public void setRotation(Rotation rotation, boolean z, boolean z2) {
        this.mRotation = rotation;
        this.mFlipHorizontal = z;
        this.mFlipVertical = z2;
        adjustImageScaling();
    }

    public Rotation getRotation() {
        return this.mRotation;
    }

    public boolean isFlippedHorizontally() {
        return this.mFlipHorizontal;
    }

    public boolean isFlippedVertically() {
        return this.mFlipVertical;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void runOnDraw(Runnable runnable) {
        synchronized (this.mRunOnDraw) {
            this.mRunOnDraw.add(runnable);
        }
    }

    public int getmImageWidth() {
        if (this.mRotation == Rotation.ROTATION_270 || this.mRotation == Rotation.ROTATION_90) {
            return this.mImageHeight;
        }
        return this.mImageWidth;
    }

    public int getmImageHeight() {
        if (this.mRotation == Rotation.ROTATION_270 || this.mRotation == Rotation.ROTATION_90) {
            return this.mImageWidth;
        }
        return this.mImageHeight;
    }

    public float[] getGpuBgColors() {
        return this.gpuBgColors;
    }

    public void setGpuBgColors(float f, float f2, float f3, float f4) {
        this.gpuBgColors = new float[]{f, f2, f3, f4};
    }

    public static boolean isArm64CpuArchitecture() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/proc/cpuinfo");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    inputStreamReader.close();
                    fileInputStream.close();
                    break;
                }
                String[] split = readLine.split(":");
                if (split.length == 2) {
                    String trim = split[0].trim();
                    String trim2 = split[1].trim();
                    Log.i("Test", "Key:" + trim + " : " + trim2);
                    if (trim.equalsIgnoreCase("Processor") && trim2.contains("64")) {
                        bufferedReader.close();
                        inputStreamReader.close();
                        fileInputStream.close();
                        return true;
                    } else if (trim.contains("Processor") || trim.contains("processor")) {
                        if (trim2.contains("64")) {
                            bufferedReader.close();
                            inputStreamReader.close();
                            fileInputStream.close();
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setCleanBeforeDraw(boolean z) {
        this.isCleanBeforeDraw = z;
    }
}
