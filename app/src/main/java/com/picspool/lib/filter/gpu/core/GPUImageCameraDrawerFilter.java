package com.picspool.lib.filter.gpu.core;

import android.opengl.GLES20;
import android.util.Log;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageCameraDrawerFilter extends GPUImageFilter {
    private static final String CameraDrawerShaderCode = "#extension GL_OES_EGL_image_external : require\n#ifdef GL_FRAGMENT_PRECISION_HIGH \n   precision highp float;\n#else \n   precision mediump float;\n#endif \nvarying vec2 textureCoordinate;\nuniform samplerExternalOES inputImageTexture;\nvoid main() {  gl_FragColor = texture2D( inputImageTexture, textureCoordinate );\n}";
    public static final String CameraDrawer_VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 transformMatrix;\n\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = vec4(position.xyz, 1.0);\n    textureCoordinate = inputTextureCoordinate.xy;\n}";

    public GPUImageCameraDrawerFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, CameraDrawerShaderCode);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void draw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.mGLProgId);
        runPendingOnDrawTasks();
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            Log.e("GLES", "1 glGetError: 0x" + Integer.toHexString(glGetError) + " " + getClass().getName());
        }
        if (this.mIsInitialized) {
            if (i != -1) {
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(36197, i);
                GLES20.glUniform1i(this.mGLUniformTexture, 0);
            }
            onDrawArraysPre();
            int glGetError2 = GLES20.glGetError();
            if (glGetError2 != 0) {
                Log.e("GLES", "2 glGetError: 0x" + Integer.toHexString(glGetError2) + " " + getClass().getName());
            }
            floatBuffer.position(0);
            GLES20.glVertexAttribPointer(this.mGLAttribPosition, 2, 5126, false, 0, (Buffer) floatBuffer);
            GLES20.glEnableVertexAttribArray(this.mGLAttribPosition);
            floatBuffer2.position(0);
            GLES20.glVertexAttribPointer(this.mGLAttribTextureCoordinate, 2, 5126, false, 0, (Buffer) floatBuffer2);
            GLES20.glEnableVertexAttribArray(this.mGLAttribTextureCoordinate);
            int glGetError3 = GLES20.glGetError();
            if (glGetError3 != 0) {
                Log.e("GLES", "3 glGetError: 0x" + Integer.toHexString(glGetError3) + " " + getClass().getName());
            }
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.mGLAttribPosition);
            GLES20.glDisableVertexAttribArray(this.mGLAttribTextureCoordinate);
            GLES20.glBindTexture(3553, 0);
        }
    }
}
