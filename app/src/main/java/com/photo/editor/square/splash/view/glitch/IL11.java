package com.photo.editor.square.splash.view.glitch;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class IL11 extends GPUImageFilter implements IL123456 {
    public static final String fragment = "        precision highp float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform vec2 uTouch;\nuniform float uTime;\nuniform vec2 uResolution;\nfloat scan = 0.5;\nint colorMode = 0;\nint direction = 0;\nvoid main() {\n    vec2 dist = vec2(0.0);\n    if (direction == 0) {\n        dist.x = 0.2 * (uTouch.x - 0.5);\n    } else {\n        dist.y = 0.2 * (uTouch.y - 0.5);\n    }\n    vec3 col;\n    if (colorMode == 0) {\n        col.r = texture2D( inputImageTexture, vec2(textureCoordinate.x+dist.x,textureCoordinate.y+dist.y)).r;\n        col.g = texture2D( inputImageTexture, textureCoordinate).g;\n        col.b = texture2D( inputImageTexture, vec2(textureCoordinate.x-dist.x,textureCoordinate.y-dist.y)).b;\n    } else if (colorMode == 1) {\n        col.r = texture2D( inputImageTexture, textureCoordinate).r;\n        col.g = texture2D( inputImageTexture, vec2(textureCoordinate.x-dist.x,textureCoordinate.y-dist.y)).g;\n        col.b = texture2D( inputImageTexture, vec2(textureCoordinate.x+dist.x,textureCoordinate.y+dist.y)).b;\n    } else {\n        col.r = texture2D( inputImageTexture, vec2(textureCoordinate.x-dist.x,textureCoordinate.y-dist.y)).r;\n        col.g = texture2D( inputImageTexture, vec2(textureCoordinate.x+dist.x,textureCoordinate.y+dist.y)).g;\n        col.b = texture2D( inputImageTexture, textureCoordinate).b;\n    }\n    float scanline = sin((direction == 0 ? textureCoordinate.y : textureCoordinate.x)*400.0 * scan + 1.0)*0.08;\n    col -= scanline;\n    float d = length(textureCoordinate - vec2(0.5,0.5));\n    col *= 1.0 - d * 0.5;\n    gl_FragColor = vec4(col,1.0);\n}";
    private float[] resolution;
    private float timeS;
    private int timeSLocation;

    /* renamed from: touch  reason: collision with root package name */
    private float[] f2027touch;
    private int uResolutionPosition;
    private int uTouchPosition;

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il2(float[] fArr) {
    }

    public IL11() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, fragment);
        this.resolution = new float[]{720.0f, 720.0f};
        this.f2027touch = new float[]{0.4f, 0.4f};
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
        float[] fArr = this.f2027touch;
        fArr[0] = f;
        fArr[1] = f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public GPUImageFilter il4() {
        IL11 il11 = new IL11();
        il11.f2027touch = this.f2027touch;
        return il11;
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
            GLES20.glUniform2fv(this.uTouchPosition, 1, FloatBuffer.wrap(this.f2027touch));
            GLES20.glGetError();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
        }
    }
}
