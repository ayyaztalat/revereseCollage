package com.photo.editor.square.splash.view.glitch;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes2.dex */
public class IL16 extends GPUImageFilter implements IL123456 {
    public static final String fragment = "precision highp float;\nuniform sampler2D inputImageTexture;\nvarying vec2 textureCoordinate;\nuniform vec2 uTouch;\nuniform float uTime;\nuniform vec2 uResolution;\nint direction = 0;\nint colorMode = 1;\nint isFull = 1;\nvec2 cosineDisplace(vec2 uv, vec2 touch, vec2 dir) {\n    float shiftX =  (touch.x-0.5) * 0.5 * pow(cos( 3.14159265/2.0 * (uv.y - touch.y)),50.0) ;\n    float shiftY =  (touch.y-0.5) * 0.5 * pow(cos( 3.14159265/2.0 * (uv.x - touch.x)),50.0) ;\n    return uv - vec2(shiftX, shiftY) * dir;\n}\nvoid main() {\n    vec2 p = textureCoordinate;\n    vec2 dir = (direction == 1) ? vec2(0.0, 1.0) : vec2(1.0, 0.0);\n    vec2 dist;\n    if (isFull == 1) {\n        dist = uTouch - vec2(0.5);\n    } else {\n        dist = p - cosineDisplace(p, uTouch, dir);\n    }\n    vec2 push1 = dir * dist * vec2(fract(sin(dot(vec2(p.y), vec2(12.9, 78.2)))* 437.5), fract(sin(dot(vec2(p.x), vec2(12.9, 78.2)))* 437.5));\n    vec2 push07 = 0.7 * dir * dist * vec2(fract(sin(dot(vec2(p.y), vec2(12.9, 78.2)))* 437.5), fract(sin(dot(vec2(p.x), vec2(12.9, 78.2)))* 437.5));\n    vec2 push04 = 0.4 * dir * dist * vec2(fract(sin(dot(vec2(p.y), vec2(12.9, 78.2)))* 437.5), fract(sin(dot(vec2(p.x), vec2(12.9, 78.2)))* 437.5));\n    gl_FragColor = texture2D(inputImageTexture,p);\n    if (colorMode == 0) {\n        gl_FragColor = texture2D(inputImageTexture, (p-push1));\n    } else if (colorMode == 1) {\n        gl_FragColor.r = texture2D(inputImageTexture, (p-push04)).r;\n        gl_FragColor.g = texture2D(inputImageTexture, (p-push1)).g;\n        gl_FragColor.b = texture2D(inputImageTexture, (p-push1)).b;\n    } else if (colorMode == 2) {\n        gl_FragColor.r = texture2D(inputImageTexture, (p-push1)).r;\n        gl_FragColor.g = texture2D(inputImageTexture, (p-push04)).g;\n        gl_FragColor.b = texture2D(inputImageTexture, (p-push1)).b;\n    } else if (colorMode == 3) {\n        gl_FragColor.g = texture2D(inputImageTexture, (p-push1)).g;\n        gl_FragColor.r = texture2D(inputImageTexture, (p-push1)).r;\n        gl_FragColor.b = texture2D(inputImageTexture, (p-push04)).b;\n    } else {\n        gl_FragColor.r = texture2D(inputImageTexture, (p-push04)).r;\n        gl_FragColor.g = texture2D(inputImageTexture, (p-push07)).g;\n        gl_FragColor.b = texture2D(inputImageTexture, (p-push1)).b;\n    }\n}";
    private float[] resolution;
    private float timeS;
    private int timeSLocation;

    /* renamed from: touch  reason: collision with root package name */
    private float[] f2030touch;
    private int uResolutionPosition;
    private int uTouchPosition;

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public void il2(float[] fArr) {
    }

    public IL16() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, fragment);
        this.resolution = new float[]{720.0f, 720.0f};
        this.f2030touch = new float[]{0.47f, 0.47f};
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
        float[] fArr = this.f2030touch;
        fArr[0] = f;
        fArr[1] = f;
    }

    @Override // com.photo.editor.square.splash.view.glitch.IL123456
    public GPUImageFilter il4() {
        IL16 il16 = new IL16();
        il16.f2030touch = this.f2030touch;
        return il16;
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
            GLES20.glUniform2fv(this.uTouchPosition, 1, FloatBuffer.wrap(this.f2030touch));
            GLES20.glGetError();
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
        }
    }
}
