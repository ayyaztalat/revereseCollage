package com.picspool.lib.filter.gpu.util;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes3.dex */
public class PixelBuffer {
    static final boolean LIST_CONFIGS = false;
    static final String TAG = "PixelBuffer";
    Bitmap mBitmap;
    EGL10 mEGL;
    EGLConfig mEGLConfig;
    EGLConfig[] mEGLConfigs;
    EGLContext mEGLContext;
    EGLDisplay mEGLDisplay;
    EGLSurface mEGLSurface;
    GL10 mGL;
    int mHeight;
    GLSurfaceView.Renderer mRenderer;
    String mThreadOwner;
    int mWidth;

    public PixelBuffer(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        int[] iArr = {12375, i, 12374, i2, 12344};
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        this.mEGL = egl10;
        EGLDisplay eglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.mEGLDisplay = eglGetDisplay;
        this.mEGL.eglInitialize(eglGetDisplay, new int[2]);
        EGLConfig chooseConfig = chooseConfig();
        this.mEGLConfig = chooseConfig;
        this.mEGLContext = this.mEGL.eglCreateContext(this.mEGLDisplay, chooseConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
        EGLSurface eglCreatePbufferSurface = this.mEGL.eglCreatePbufferSurface(this.mEGLDisplay, this.mEGLConfig, iArr);
        this.mEGLSurface = eglCreatePbufferSurface;
        this.mEGL.eglMakeCurrent(this.mEGLDisplay, eglCreatePbufferSurface, eglCreatePbufferSurface, this.mEGLContext);
        this.mGL = (GL10) this.mEGLContext.getGL();
        this.mThreadOwner = Thread.currentThread().getName();
    }

    public void setRenderer(GLSurfaceView.Renderer renderer) {
        this.mRenderer = renderer;
        if (!Thread.currentThread().getName().equals(this.mThreadOwner)) {
            Log.e(TAG, "setRenderer: This thread does not own the OpenGL context.");
            return;
        }
        this.mRenderer.onSurfaceCreated(this.mGL, this.mEGLConfig);
        this.mRenderer.onSurfaceChanged(this.mGL, this.mWidth, this.mHeight);
    }

    public Bitmap getBitmap() {
        if (this.mRenderer == null) {
            Log.e(TAG, "getBitmap: Renderer was not set.");
            return null;
        } else if (!Thread.currentThread().getName().equals(this.mThreadOwner)) {
            Log.e(TAG, "getBitmap: This thread does not own the OpenGL context.");
            return null;
        } else {
            this.mRenderer.onDrawFrame(this.mGL);
            this.mRenderer.onDrawFrame(this.mGL);
            convertToBitmap();
            return this.mBitmap;
        }
    }

    public void destroy() {
        this.mRenderer.onDrawFrame(this.mGL);
        this.mRenderer.onDrawFrame(this.mGL);
        this.mEGL.eglMakeCurrent(this.mEGLDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        this.mEGL.eglDestroySurface(this.mEGLDisplay, this.mEGLSurface);
        this.mEGL.eglDestroyContext(this.mEGLDisplay, this.mEGLContext);
        this.mEGL.eglTerminate(this.mEGLDisplay);
    }

    private EGLConfig chooseConfig() {
        int[] iArr = {12325, 0, 12326, 0, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344};
        int[] iArr2 = new int[1];
        this.mEGL.eglChooseConfig(this.mEGLDisplay, iArr, null, 0, iArr2);
        int i = iArr2[0];
        EGLConfig[] eGLConfigArr = new EGLConfig[i];
        this.mEGLConfigs = eGLConfigArr;
        this.mEGL.eglChooseConfig(this.mEGLDisplay, iArr, eGLConfigArr, i, iArr2);
        return this.mEGLConfigs[0];
    }

    private void listConfig() {
        EGLConfig[] eGLConfigArr;
        Log.i(TAG, "Config List {");
        for (EGLConfig eGLConfig : this.mEGLConfigs) {
            Log.i(TAG, "    <d,s,r,g,b,a> = <" + getConfigAttrib(eGLConfig, 12325) + "," + getConfigAttrib(eGLConfig, 12326) + "," + getConfigAttrib(eGLConfig, 12324) + "," + getConfigAttrib(eGLConfig, 12323) + "," + getConfigAttrib(eGLConfig, 12322) + "," + getConfigAttrib(eGLConfig, 12321) + ">");
        }
        Log.i(TAG, "}");
    }

    private int getConfigAttrib(EGLConfig eGLConfig, int i) {
        int[] iArr = new int[1];
        if (this.mEGL.eglGetConfigAttrib(this.mEGLDisplay, eGLConfig, i, iArr)) {
            return iArr[0];
        }
        return 0;
    }

    private void convertToBitmap() {
        String str = Build.VERSION.RELEASE;
        IntBuffer wrap = IntBuffer.wrap(new int[this.mWidth * this.mHeight]);
        wrap.position(0);
        try {
            if (str.equals("2.3.6")) {
                GLES20.glPixelStorei(3333, 4);
            }
            GLES20.glReadPixels(0, 0, this.mWidth, this.mHeight, 6408, 5121, wrap);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            this.mBitmap = null;
        } catch (Throwable th) {
            System.out.print(th.getMessage());
            this.mBitmap = null;
        }
        try {
            Bitmap createBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Bitmap.Config.ARGB_8888);
            this.mBitmap = createBitmap;
            createBitmap.copyPixelsFromBuffer(wrap);
        } catch (Throwable unused) {
            this.mBitmap = null;
        }
        wrap.clear();
    }
}
