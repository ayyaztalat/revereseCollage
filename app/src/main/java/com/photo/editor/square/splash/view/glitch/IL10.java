package com.photo.editor.square.splash.view.glitch;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class IL10 extends GPUImageFilter implements IL123456 {
    public static final String fragment = "        precision highp float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform vec2 uTouch;\nuniform float uTime;\nuniform vec2 uResolution;\nuniform float scale;\n#define DOTSIZE 1.48\n        #define D2R(d) radians(d)\n        #define MIN_S 2.5\n        #define MAX_S 19.0\n        #define SPEED 0.57\n        #define SST 0.888\n        #define SSQ 0.288\n        #define ORIGIN (0.5 * uResolution.xy)\nvec4 rgb2cmyki(in vec3 c)\n{\n    float k = max(max(c.r, c.g), c.b);\n    return min(vec4(c.rgb / k, k), 1.0);\n}\nvec3 cmyki2rgb(in vec4 c)\n{\n    return c.rgb * c.a;\n}\nvec2 px2uv(in vec2 px)\n{\n    return vec2(px / uResolution.xy);\n}\nvec2 grid(in vec2 px, float S)\n{\n    return px - mod(px,S);\n}\nvec4 ss(in vec4 v)\n{\n    return smoothstep(SST-SSQ, SST+SSQ, v);\n}\nvec4 halftone(in vec2 fc,in mat2 m, float S)\n{\n    vec2 smp = (grid(m*fc, S) + 0.5*S) * m;\n    float s = min(length(fc-smp) / (DOTSIZE*0.5*S), 1.0);\n    vec3 texc = texture2D(inputImageTexture, px2uv(smp+ORIGIN)).rgb;\n    texc = pow(texc, vec3(2.2)); // Gamma decode.\n    vec4 c = rgb2cmyki(texc);\n    return c+s;\n}\nmat2 rotm(in float r)\n{\n    float cr = cos(r);\n    float sr = sin(r);\n    return mat2(\n            cr,-sr,\n            sr,cr\n    );\n}\nvoid main() {\n    float R = SPEED*0.333*uTime;\n    float S = MIN_S + (MAX_S-MIN_S) * (0.5 - 0.5*cos(SPEED*uTime));\n    S = MIN_S + (MAX_S-MIN_S) * 4.0*abs(scale*uResolution.x) / uResolution.x;\n    R = D2R(360.0 * (uTouch.y*uResolution.y-ORIGIN.y) / uResolution.y);\n    vec2 fc = textureCoordinate * uResolution - ORIGIN;\n    mat2 mc = rotm(R + D2R(15.0));\n    mat2 mm = rotm(R + D2R(75.0));\n    mat2 my = rotm(R);\n    mat2 mk = rotm(R + D2R(45.0));\n    float k = halftone(fc, mk, S).a;\n    vec3 c = cmyki2rgb(ss(vec4(\n            halftone(fc, mc, S).r,\n            halftone(fc, mm, S).g,\n            halftone(fc, my, S).b,\n            halftone(fc, mk, S).a\n    )));\n    c = pow(c, vec3(1.0/2.2)); // Gamma encode.\n    gl_FragColor = vec4(c, 1.0);\n}";
    private float il1;
    private int il2;
    private float[] il3;
    private int il4;
    private float[] il5;
    private int il6;
    private float il7;
    private int il8;

    public IL10() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, fragment);
        this.il3 = new float[]{720.0f, 720.0f};
        this.il5 = new float[]{720.0f, 720.0f};
        this.il7 = 0.05f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.il2 = GLES20.glGetUniformLocation(getProgram(), "uTime");
        this.il4 = GLES20.glGetUniformLocation(getProgram(), "uResolution");
        this.il6 = GLES20.glGetUniformLocation(getProgram(), "uTouch");
        this.il8 = GLES20.glGetUniformLocation(getProgram(), "scale");
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
        this.il7 = f * 2.0f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public GPUImageFilter il4() {
        IL10 il10 = new IL10();
        il10.il7 = this.il7;
        return il10;
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
