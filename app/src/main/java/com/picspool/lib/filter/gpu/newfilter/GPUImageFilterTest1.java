package com.picspool.lib.filter.gpu.newfilter;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageFilterTest1 extends GPUImageFilter {
    public static final String BRIGHTNESS_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float brightness;\n \n void main()\n {\n     gl_FragColor = vec4(brightness,0.0,0.0, textureColor.w);\n }";
    private float mBrightness;
    private int mBrightnessLocation;
    GPUImageFilter nextFilter;
    GPUImageFilter preFilter;

    public GPUImageFilterTest1() {
        this(0.0f);
    }

    public GPUImageFilterTest1(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, BRIGHTNESS_FRAGMENT_SHADER);
        this.mBrightness = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mBrightnessLocation = GLES20.glGetUniformLocation(getProgram(), "brightness");
        GPUImageFilter gPUImageFilter = this.nextFilter;
        if (gPUImageFilter != null) {
            gPUImageFilter.onInit();
        }
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setBrightness(this.mBrightness);
        GPUImageFilter gPUImageFilter = this.nextFilter;
        if (gPUImageFilter != null) {
            gPUImageFilter.onInitialized();
        }
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        GPUImageFilter gPUImageFilter = this.nextFilter;
        if (gPUImageFilter != null) {
            gPUImageFilter.onOutputSizeChanged(i, i2);
        }
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void draw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.mGLProgId);
        runPendingOnDrawTasks();
        if (this.mIsInitialized) {
            floatBuffer.position(0);
            GLES20.glVertexAttribPointer(this.mGLAttribPosition, 2, 5126, false, 0, (Buffer) floatBuffer);
            GLES20.glEnableVertexAttribArray(this.mGLAttribPosition);
            floatBuffer2.position(0);
            GLES20.glVertexAttribPointer(this.mGLAttribTextureCoordinate, 2, 5126, false, 0, (Buffer) floatBuffer2);
            GLES20.glEnableVertexAttribArray(this.mGLAttribTextureCoordinate);
            if (i != -1) {
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(3553, i);
                GLES20.glUniform1i(this.mGLUniformTexture, 0);
            }
            onDrawArraysPre();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
            GPUImageFilter gPUImageFilter = this.nextFilter;
            if (gPUImageFilter != null) {
                gPUImageFilter.draw(i, floatBuffer, floatBuffer2);
            }
        }
    }

    public void setBrightness(float f) {
        this.mBrightness = f;
        setFloat(this.mBrightnessLocation, f);
    }
}
