package com.photo.editor.square.splash.view.glitch;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class IL14 extends GPUImageFilter implements IL123456 {
    public static final String fragment = "        precision highp float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform vec2 uTouch;\nuniform float uTime;\nuniform vec2 uResolution;\n#define steps 2.\n    void main(void)\n    {\n        vec2 uv = textureCoordinate;\n        vec4 c = texture2D(inputImageTexture,uv);\n        float g = max(c.r,max(c.g,c.b))*steps;\n        float fuck = 345.678+uTouch.x*uResolution.x;\n        float f = mod((uv.x+uv.y+500.)*fuck,1.);\n        if(mod(g,1.0)>f)\n            c.r = ceil(g);\n        else\n            c.r = floor(g);\n        c.r/=steps;\n        gl_FragColor = c.rrra;\n    }";
    private float[] resolution;
    private float timeS;
    private int timeSLocation;

    /* renamed from: touch  reason: collision with root package name */
    private float[] f2029touch;
    private int uResolutionPosition;
    private int uTouchPosition;

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il2(float[] fArr) {
    }

    public IL14() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, fragment);
        this.resolution = new float[]{720.0f, 720.0f};
        this.f2029touch = new float[]{0.1f, 0.0f};
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.timeSLocation = GLES20.glGetUniformLocation(getProgram(), "uTime");
        this.uResolutionPosition = GLES20.glGetUniformLocation(getProgram(), "uResolution");
        this.uTouchPosition = GLES20.glGetUniformLocation(getProgram(), "uTouch");
    }

    public void setTime(float f) {
        this.timeS = f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il1(float[] fArr) {
        this.resolution = fArr;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il3(float f) {
        this.f2029touch[0] = f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public GPUImageFilter il4() {
        IL14 il14 = new IL14();
        il14.f2029touch = this.f2029touch;
        return il14;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
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
            GLES20.glUniform1f(this.timeSLocation, this.timeS);
            GLES20.glUniform2fv(this.uResolutionPosition, 1, FloatBuffer.wrap(this.resolution));
            GLES20.glUniform2fv(this.uTouchPosition, 1, FloatBuffer.wrap(this.f2029touch));
            GLES20.glGetError();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
        }
    }
}
