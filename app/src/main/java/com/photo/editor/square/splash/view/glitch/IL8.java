package com.photo.editor.square.splash.view.glitch;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class IL8 extends GPUImageFilter implements IL123456 {
    public static final String fragment = "precision highp float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform vec2 uTouch;\nuniform float uTime;\nuniform vec2 uResolution;\nuniform float amount;\nvoid main() {\nvec3 c[9];\nfor (int i=0; i < 3; ++i)\n{\n    for (int j=0; j < 3; ++j)\n    {\n        c[3*i+j] = texture2D(inputImageTexture, ((textureCoordinate*uResolution+vec2(i-1,j-1)) / uResolution.xy)).rgb;\n    }\n}\nvec3 Lx = amount * 10.0*(c[7]-c[1]) + c[6] + c[8] - c[2] - c[0];\nvec3 Ly = amount * 10.0*(c[3]-c[5]) + c[6] + c[0] - c[2] - c[8];\nvec3 G = sqrt(Lx*Lx+Ly*Ly);\ngl_FragColor = vec4(G, 1.0);\n}";
    private float il1;
    private int il2;
    private float[] il3;
    private int il4;
    private float[] il5;
    private int il6;
    private float il7;
    private int il8;

    public IL8() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, fragment);
        this.il3 = new float[]{720.0f, 720.0f};
        this.il5 = new float[]{720.0f, 720.0f};
        this.il7 = 1.0f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.il2 = GLES20.glGetUniformLocation(getProgram(), "uTime");
        this.il4 = GLES20.glGetUniformLocation(getProgram(), "uResolution");
        this.il6 = GLES20.glGetUniformLocation(getProgram(), "uTouch");
        this.il8 = GLES20.glGetUniformLocation(getProgram(), "amount");
    }

    public void setTime(float f) {
        this.il1 = f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il1(float[] fArr) {
        this.il3 = fArr;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il2(float[] fArr) {
        this.il5 = fArr;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il3(float f) {
        this.il7 = f * 5.0f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public GPUImageFilter il4() {
        IL8 il8 = new IL8();
        il8.il7 = this.il7;
        return il8;
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
            GLES20.glUniform1f(this.il2, this.il1);
            GLES20.glUniform1f(this.il8, this.il7);
            GLES20.glUniform2fv(this.il4, 1, FloatBuffer.wrap(this.il3));
            GLES20.glUniform2fv(this.il6, 1, FloatBuffer.wrap(this.il5));
            GLES20.glGetError();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
        }
    }
}
