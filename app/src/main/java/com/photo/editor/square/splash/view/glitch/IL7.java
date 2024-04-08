package com.photo.editor.square.splash.view.glitch;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;
import com.picspool.lib.filter.gpu.father.GPUImageFilterGroup;
import com.picspool.lib.filter.gpu.util.OpenGlUtils;
import com.picspool.lib.filter.gpu.util.Rotation;
import com.picspool.lib.filter.gpu.util.TextureRotationUtil;

/* loaded from: classes2.dex */
public class IL7 implements GLSurfaceView.Renderer {
    public static final float[] IL1234 = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
    public static float[] IL2345;
    private FloatBuffer il1;
    private int il10;
    private IL34567 il12;
    private FloatBuffer il2;
    private GPUImageFilterGroup il4;
    private Queue<Runnable> il5;
    private int il7;
    private int il8;
    private int il9;
    private int il6 = -1;
    private volatile boolean il11 = false;

    /* loaded from: classes2.dex */
    public interface IL34567 {
        void captureBitmapDone(Bitmap bitmap);
    }

    public IL7(boolean z) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new GPUImageFilter());
        arrayList.add(new GPUImageFilter());
        this.il4 = new GPUImageFilterGroup(arrayList);
        this.il5 = new LinkedList();
        FloatBuffer asFloatBuffer = ByteBuffer.allocateDirect(IL1234.length * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.il1 = asFloatBuffer;
        asFloatBuffer.put(IL1234).position(0);
        if (z) {
            IL2345 = TextureRotationUtil.getRotation(Rotation.NORMAL, false, true);
        } else {
            IL2345 = TextureRotationUtil.TEXTURE_NO_ROTATION;
        }
        FloatBuffer asFloatBuffer2 = ByteBuffer.allocateDirect(IL2345.length * 4 * 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.il2 = asFloatBuffer2;
        asFloatBuffer2.put(IL2345).position(0);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.il4.init();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        GLES20.glViewport(0, 0, i, i2);
        this.il9 = i;
        this.il10 = i2;
        this.il4.onOutputSizeChanged(i, i2);
    }

    public void setTextureOriWH(int i, int i2) {
        this.il7 = i;
        this.il8 = i2;
    }

    public void setUserBitmap(final Bitmap bitmap) {
        Queue<Runnable> queue = this.il5;
        if (queue == null) {
            return;
        }
        queue.add(new Runnable() { // from class: com.photo.editor.square.splash.view.glitch.IL7.1
            @Override // java.lang.Runnable
            public void run() {
                IL7.this.il6 = OpenGlUtils.loadTexture(bitmap, -1, false);
            }
        });
    }

    public void replaceGlitch(final GPUImageFilter gPUImageFilter) {
        if (gPUImageFilter == null || this.il4.getFilters().size() <= 1) {
            return;
        }
        final GPUImageFilter gPUImageFilter2 = this.il4.getFilters().set(1, gPUImageFilter);
        this.il5.add(new Runnable() { // from class: com.photo.editor.square.splash.view.glitch.IL7.2
            @Override // java.lang.Runnable
            public void run() {
                GPUImageFilter gPUImageFilter3 = gPUImageFilter2;
                if (gPUImageFilter3 != null) {
                    gPUImageFilter3.destroy();
                }
                gPUImageFilter.init();
            }
        });
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) {
        Queue<Runnable> queue = this.il5;
        if (queue != null) {
            synchronized (queue) {
                while (!this.il5.isEmpty()) {
                    this.il5.poll().run();
                }
            }
        }
        if (this.il6 == -1) {
            return;
        }
        if (this.il4.getFilters().size() > 1) {
            GPUImageFilter gPUImageFilter = this.il4.getFilters().get(1);
            boolean z = gPUImageFilter instanceof IL123456;
            if (z) {
                ((IL123456) gPUImageFilter).il1(new float[]{this.il7, this.il8});
            }
            if (z) {
                ((IL123456) gPUImageFilter).il2(new float[]{this.il7, this.il8});
            }
        }
        this.il4.draw(this.il6, this.il1, this.il2);
        if (this.il11) {
            this.il11 = false;
            IL34567 il34567 = this.il12;
            if (il34567 != null) {
                il34567.captureBitmapDone(m80f1());
            }
        }
    }

    /* renamed from: f1 */
    private Bitmap m80f1() {
        Bitmap bitmap;
        String str = Build.VERSION.RELEASE;
        IntBuffer wrap = IntBuffer.wrap(new int[this.il9 * this.il10]);
        wrap.position(0);
        try {
            if (str.equals("2.3.6")) {
                GLES20.glPixelStorei(3333, 4);
            }
            GLES20.glReadPixels(0, 0, this.il9, this.il10, 6408, 5121, wrap);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            Bitmap createBitmap = Bitmap.createBitmap(this.il9, this.il10, Bitmap.Config.ARGB_8888);
            createBitmap.copyPixelsFromBuffer(wrap);
            Bitmap createScaledBitmap = Bitmap.createScaledBitmap(createBitmap, this.il7, this.il8, true);
            Matrix matrix = new Matrix();
            matrix.postScale(1.0f, -1.0f, this.il7 / 2.0f, this.il8 / 2.0f);
            bitmap = Bitmap.createBitmap(createScaledBitmap, 0, 0, this.il7, this.il8, matrix, true);
        } catch (Throwable unused) {
            bitmap = null;
        }
        try {
            wrap.clear();
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        return bitmap;
    }

    public boolean useCaptureMode() {
        return this.il7 < this.il9 || this.il8 < this.il10;
    }

    /* renamed from: f2 */
    public void m79f2() {
        this.il5.add(new Runnable() { // from class: com.photo.editor.square.splash.view.glitch.IL7.3
            @Override // java.lang.Runnable
            public void run() {
                GLES20.glDeleteTextures(1, new int[]{IL7.this.il6}, 0);
                IL7.this.il6 = -1;
            }
        });
    }

    public void setIl11(boolean z) {
        this.il11 = z;
    }

    public void setIl12(IL34567 il34567) {
        this.il12 = il34567;
    }
}
