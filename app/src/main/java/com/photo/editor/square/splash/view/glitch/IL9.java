package com.photo.editor.square.splash.view.glitch;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class IL9 extends GPUImageFilter implements IL123456 {
    public static final String fragment = "precision highp float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform vec2 uTouch;\nuniform float uTime;\nuniform vec2 uResolution;\nuniform float slice1;\nfloat slice2 = 0.0;\nconst float ring1 = 60.0;\nconst float ring2 = 10.0;\nconst float diminish = 0.05;\nfloat rand(vec2 co){\n    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);\n}\nvoid main() {\n    float push1 = 2.0 * slice1;\n    float push2 = 4.0 * slice2;\n    float r1 = rand(floor(textureCoordinate.yy*ring1 )/ring1);\n    float r2 = rand(floor(textureCoordinate.yy*ring2 )/ring2);\n    r1 = -1.0 + 2.0 * r1;\n    r2 = -1.0 + 2.0 * r2;\n    r1 *= push1;\n    r2 *= push2;\n    r1 += r2;\n    r1 *= diminish;\n    vec4 tex = texture2D(inputImageTexture, (textureCoordinate + vec2(r1, 0.0)));\n    if(textureCoordinate.x+r1 > 1.0 || textureCoordinate.x+r1 <= 0.0){\n        gl_FragColor = vec4(vec3(0.0),1.0);\n    } else {\n        gl_FragColor =tex;\n    }\n}";
    private float il1;
    private int il2;
    private float[] il3;
    private int il4;
    private float[] il5;
    private int il6;
    private float il7;
    private int il8;

    public IL9() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, fragment);
        this.il3 = new float[]{720.0f, 720.0f};
        this.il5 = new float[]{720.0f, 720.0f};
        this.il7 = 0.2f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.il2 = GLES20.glGetUniformLocation(getProgram(), "uTime");
        this.il4 = GLES20.glGetUniformLocation(getProgram(), "uResolution");
        this.il6 = GLES20.glGetUniformLocation(getProgram(), "uTouch");
        this.il8 = GLES20.glGetUniformLocation(getProgram(), "slice1");
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
        this.il7 = f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public GPUImageFilter il4() {
        IL9 il9 = new IL9();
        il9.il7 = this.il7;
        return il9;
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
