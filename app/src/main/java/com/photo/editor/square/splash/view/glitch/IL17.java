package com.photo.editor.square.splash.view.glitch;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class IL17 extends GPUImageFilter implements IL123456 {
    public static final String fragment = "        precision highp float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform vec2 uTouch;\nuniform float uTime;\nuniform vec2 uResolution;\nuniform int saveType;\nint shiftType = 0;\nint fixedChannel = 0;\nvec2 cosineDisplace(vec2 uv, vec2 touch, float scale){\n    float shiftX =  (touch.x-0.5) * 0.35 * pow(cos( 3.14159265/2.0 * (uv.y - touch.y)),75.0) ;\n    return vec2(uv.x-scale * shiftX, uv.y);\n}\nvoid main() {\n    vec2 uv = textureCoordinate;\n    vec2 uvShiftedLeft;\n    vec2 uvShiftedRight;\n    if (shiftType == 0) {\n        vec2 shift;\n        if(saveType == 0){\n          shift = vec2(0.3*(uTouch.x-0.5), 0.3*(uTouch.y-0.5));\n        }else{\n          shift = vec2(0.3*(uTouch.x-0.5), 0.3*(-uTouch.y+0.5));\n        }\n        uvShiftedRight = uv - shift;\n        uvShiftedLeft = uv + shift;\n    } else {\n        uvShiftedRight = cosineDisplace(uv, uTouch, 1.0);\n        uvShiftedLeft = cosineDisplace(uv, uTouch, -1.0);\n    }\n    vec4 fixed_color = texture2D(inputImageTexture, uv);\n    vec4 left_color = texture2D(inputImageTexture, uvShiftedLeft);\n    vec4 right_color = texture2D(inputImageTexture, uvShiftedRight);\n    vec4 final_color = vec4(1.0);\n    if (fixedChannel == 0) { // Fixed R\n        final_color.r = fixed_color.r;\n        final_color.g = right_color.g;\n        final_color.b = left_color.b;\n    } else if (fixedChannel == 1) { // Fixed G\n        final_color.r = left_color.r;\n        final_color.g = fixed_color.g;\n        final_color.b = right_color.b;\n    } else { // Fixed B\n        final_color.r = right_color.r;\n        final_color.g = left_color.g;\n        final_color.b = fixed_color.b;\n    }\n    gl_FragColor = final_color;\n}";
    private float[] resolution;
    private int saveType;
    private int saveTypePosition;
    private float timeS;
    private int timeSLocation;

    /* renamed from: touch  reason: collision with root package name */
    private float[] f2031touch;
    private int uResolutionPosition;
    private int uTouchPosition;

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il2(float[] fArr) {
    }

    public IL17() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, fragment);
        this.resolution = new float[]{720.0f, 720.0f};
        this.f2031touch = new float[]{0.45f, 0.45f};
        this.saveType = 0;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.timeSLocation = GLES20.glGetUniformLocation(getProgram(), "uTime");
        this.uResolutionPosition = GLES20.glGetUniformLocation(getProgram(), "uResolution");
        this.uTouchPosition = GLES20.glGetUniformLocation(getProgram(), "uTouch");
        this.saveTypePosition = GLES20.glGetUniformLocation(getProgram(), "saveType");
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
        float[] fArr = this.f2031touch;
        fArr[0] = f;
        fArr[1] = f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public GPUImageFilter il4() {
        IL17 il17 = new IL17();
        il17.f2031touch = this.f2031touch;
        il17.saveType = 1;
        return il17;
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
            GLES20.glUniform1i(this.saveTypePosition, this.saveType);
            GLES20.glUniform2fv(this.uResolutionPosition, 1, FloatBuffer.wrap(this.resolution));
            GLES20.glUniform2fv(this.uTouchPosition, 1, FloatBuffer.wrap(this.f2031touch));
            GLES20.glGetError();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
        }
    }
}
