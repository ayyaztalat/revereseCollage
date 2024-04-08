package com.picspool.lib.filter.gpu.father;

import android.graphics.PointF;
import android.opengl.GLES20;
import android.util.Log;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import com.picspool.lib.filter.gpu.util.OpenGlUtils;
import com.picspool.lib.filter.gpu.util.Rotation;

/* loaded from: classes3.dex */
public class GPUImageFilter {
    public static final String NO_FILTER_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}";
    public static final String NO_FILTER_VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 transformMatrix;\n\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = transformMatrix * vec4(position.xyz, 1.0);\n    textureCoordinate = inputTextureCoordinate.xy;\n}";
    protected float[] gpuBgColors;
    protected boolean isFlipHorizontal;
    protected boolean isFlipVertical;
    protected boolean isFlipVertical2;
    protected boolean isUseFilter;
    private final String mFragmentShader;
    protected int mGLAttribPosition;
    protected int mGLAttribTextureCoordinate;
    protected int mGLProgId;
    protected int mGLUniformTexture;
    protected boolean mIsInitialized;
    protected float mMix;
    protected int mMixLocation;
    protected int mOutputHeight;
    protected int mOutputWidth;
    protected Rotation mRotate;
    private final LinkedList<Runnable> mRunOnDraw;
    protected float[] mTransform;
    protected int mTransformLocation;
    private final String mVertexShader;

    public static native void getTransform(float[] fArr);

    public void onDestroy() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDrawArraysPre() {
    }

    public GPUImageFilter() {
        this(NO_FILTER_VERTEX_SHADER, NO_FILTER_FRAGMENT_SHADER);
    }

    public GPUImageFilter(String str, String str2) {
        this.mGLProgId = -1;
        this.mMix = 1.0f;
        this.mTransform = new float[16];
        this.isUseFilter = true;
        this.isFlipVertical = false;
        this.isFlipVertical2 = false;
        this.isFlipHorizontal = false;
        this.mRotate = Rotation.NORMAL;
        this.mRunOnDraw = new LinkedList<>();
        this.mVertexShader = str;
        this.mFragmentShader = str2;
        getTransform(this.mTransform);
        setGpuBgColors(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void setGpuBgColors(float f, float f2, float f3, float f4) {
        this.gpuBgColors = new float[]{f, f2, f3, f4};
    }

    public final void init() {
        onInit();
        onInitialized();
    }

    public void onInit() {
        int loadProgram = OpenGlUtils.loadProgram(this.mVertexShader, this.mFragmentShader);
        this.mGLProgId = loadProgram;
        this.mGLAttribPosition = GLES20.glGetAttribLocation(loadProgram, "position");
        this.mGLUniformTexture = GLES20.glGetUniformLocation(this.mGLProgId, "inputImageTexture");
        this.mGLAttribTextureCoordinate = GLES20.glGetAttribLocation(this.mGLProgId, "inputTextureCoordinate");
        this.mMixLocation = GLES20.glGetUniformLocation(getProgram(), "mixturePercent");
        this.mTransformLocation = GLES20.glGetUniformLocation(getProgram(), "transformMatrix");
        this.mIsInitialized = true;
    }

    public void onInitialized() {
        setMix(this.mMix);
        setTransform(this.mTransform);
    }

    public final void destroy() {
        this.mIsInitialized = false;
        int i = this.mGLProgId;
        if (i != -1) {
            GLES20.glDeleteProgram(i);
            this.mGLProgId = -1;
        }
        onDestroy();
    }

    public void setMix(float f) {
        this.mMix = f;
        setFloat(this.mMixLocation, f);
    }

    public float getMix() {
        return this.mMix;
    }

    public void setTransform(float[] fArr) {
        this.mTransform = fArr;
        setUniformMatrix4f(this.mTransformLocation, fArr);
    }

    public void onOutputSizeChanged(int i, int i2) {
        this.mOutputWidth = i;
        this.mOutputHeight = i2;
    }

    public void draw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.mGLProgId);
        runPendingOnDrawTasks();
        GLES20.glGetError();
        if (this.mIsInitialized) {
            if (i != -1) {
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, i);
                GLES20.glUniform1i(this.mGLUniformTexture, 0);
            }
            onDrawArraysPre();
            GLES20.glGetError();
            floatBuffer.position(0);
            GLES20.glVertexAttribPointer(this.mGLAttribPosition, 2, 5126, false, 0, (Buffer) floatBuffer);
            GLES20.glEnableVertexAttribArray(this.mGLAttribPosition);
            floatBuffer2.position(0);
            GLES20.glVertexAttribPointer(this.mGLAttribTextureCoordinate, 2, 5126, false, 0, (Buffer) floatBuffer2);
            GLES20.glEnableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glGetError();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void runPendingOnDrawTasks() {
        synchronized (this.mRunOnDraw) {
            while (!this.mRunOnDraw.isEmpty()) {
                this.mRunOnDraw.removeFirst().run();
            }
        }
    }

    public void isUsedThisFilter(boolean z) {
        this.isUseFilter = z;
    }

    public boolean isUsedThisFilter() {
        return this.isUseFilter;
    }

    public boolean isInitialized() {
        return this.mIsInitialized;
    }

    public int getOutputWidth() {
        return this.mOutputWidth;
    }

    public int getOutputHeight() {
        return this.mOutputHeight;
    }

    public int getProgram() {
        return this.mGLProgId;
    }

    public int getAttribPosition() {
        return this.mGLAttribPosition;
    }

    public int getAttribTextureCoordinate() {
        return this.mGLAttribTextureCoordinate;
    }

    public int getUniformTexture() {
        return this.mGLUniformTexture;
    }

    public void setInteger(final int i, final int i2) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.1
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform1i(i, i2);
            }
        });
    }

    public void setFloat(final int i, final float f) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.2
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform1f(i, f);
            }
        });
    }

    public void setFloatVec2(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.3
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform2fv(i, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    public void setFloatVec3(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.4
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform3fv(i, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    public void setFloatVec4(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.5
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform4fv(i, 1, FloatBuffer.wrap(fArr));
            }
        });
    }

    public void setFloat(final String str, final int i, final float f) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.6
            @Override // java.lang.Runnable
            public void run() {
                int glGetError;
                Log.i("GLES", "" + str);
                GLES20.glUniform1f(i, f);
                if (GLES20.glGetError() != 0) {
                    //Log.e("GLES_" + str, "glGetError: 0x" + Integer.toHexString(glGetError));
                }
            }
        });
    }

    public void setFloatVec2(final String str, final int i, final float[] fArr) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.7
            @Override // java.lang.Runnable
            public void run() {
                int glGetError;
                Log.i("GLES", "" + str);
                GLES20.glUniform2fv(i, 1, FloatBuffer.wrap(fArr));
                if (GLES20.glGetError() != 0) {
                   // Log.e("GLES_" + str, "glGetError: 0x" + Integer.toHexString(glGetError));
                }
            }
        });
    }

    public void setFloatVec3(final String str, final int i, final float[] fArr) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.8
            @Override // java.lang.Runnable
            public void run() {
                int glGetError;
                Log.i("GLES", "" + str);
                GLES20.glUniform3fv(i, 1, FloatBuffer.wrap(fArr));
                if (GLES20.glGetError() != 0) {
                  //  Log.e("GLES_" + str, "glGetError: 0x" + Integer.toHexString(glGetError));
                }
            }
        });
    }

    public void setFloatVec4(final String str, final int i, final float[] fArr) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.9
            @Override // java.lang.Runnable
            public void run() {
                int glGetError;
                Log.i("GLES", "" + str);
                GLES20.glUniform4fv(i, 1, FloatBuffer.wrap(fArr));
                if (GLES20.glGetError() != 0) {
                   // Log.e("GLES_" + str, "glGetError: 0x" + Integer.toHexString(glGetError));
                }
            }
        });
    }

    public void setFloatArray(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.10
            @Override // java.lang.Runnable
            public void run() {
                int i2 = i;
                float[] fArr2 = fArr;
                GLES20.glUniform1fv(i2, fArr2.length, FloatBuffer.wrap(fArr2));
            }
        });
    }

    public void setPoint(final int i, final PointF pointF) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.11
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniform2fv(i, 1, new float[]{pointF.x, pointF.y}, 0);
            }
        });
    }

    public void setUniformMatrix3f(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.12
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniformMatrix3fv(i, 1, false, fArr, 0);
            }
        });
    }

    public void setUniformMatrix4f(final int i, final float[] fArr) {
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageFilter.13
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glUniformMatrix4fv(i, 1, false, fArr, 0);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void runOnDraw(Runnable runnable) {
        synchronized (this.mRunOnDraw) {
            this.mRunOnDraw.addLast(runnable);
        }
    }

    static {
        System.loadLibrary("gpuimage");
    }

    public GPUImageFilter copyFilter() {
        return new GPUImageFilter();
    }

    public void setFilpVertical(boolean z) {
        this.isFlipVertical = z;
    }

    public void setFilpVertical2(boolean z) {
        this.isFlipVertical2 = z;
    }

    public boolean isFlipVertical() {
        return this.isFlipVertical;
    }

    public boolean isFlipVertical2() {
        return this.isFlipVertical2;
    }

    public boolean isFlipHorizontal() {
        return this.isFlipHorizontal;
    }

    public void setFlipHorizontal(boolean z) {
        this.isFlipHorizontal = z;
    }

    public Rotation getmRotate() {
        return this.mRotate;
    }

    public void setmRotate(Rotation rotation) {
        this.mRotate = rotation;
    }

    public void setmRotation(Rotation rotation, boolean z, boolean z2) {
        this.mRotate = rotation;
        this.isFlipHorizontal = z;
        this.isFlipVertical2 = z2;
    }
}
