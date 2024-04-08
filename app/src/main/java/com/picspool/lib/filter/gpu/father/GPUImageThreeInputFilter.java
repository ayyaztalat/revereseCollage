package com.picspool.lib.filter.gpu.father;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.util.OpenGlUtils;
import com.picspool.lib.filter.gpu.util.Rotation;
import com.picspool.lib.filter.gpu.util.TextureRotationUtil;

/* loaded from: classes3.dex */
public class GPUImageThreeInputFilter extends GPUImageFilter {
    private static final String VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\nattribute vec4 inputTextureCoordinate3;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\nvarying vec2 textureCoordinate3;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n    textureCoordinate3 = inputTextureCoordinate3.xy;\n}";
    public int filterInputTextureUniform2;
    public int filterInputTextureUniform3;
    public int filterSourceTexture2;
    public int filterSourceTexture3;
    public int filterTextureCoordinateAttribute2;
    public int filterTextureCoordinateAttribute3;
    private Bitmap mBitmap2;
    private Bitmap mBitmap3;
    private ByteBuffer mTextureCoordinatesBuffer2;
    private ByteBuffer mTextureCoordinatesBuffer3;

    public GPUImageThreeInputFilter(String str) {
        this(VERTEX_SHADER, str);
    }

    public GPUImageThreeInputFilter(String str, String str2) {
        super(str, str2);
        this.filterSourceTexture2 = -1;
        this.filterSourceTexture3 = -1;
        setRotation(Rotation.NORMAL, false, false);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.filterTextureCoordinateAttribute2 = GLES20.glGetAttribLocation(getProgram(), "inputTextureCoordinate2");
        this.filterInputTextureUniform2 = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture2");
        GLES20.glEnableVertexAttribArray(this.filterTextureCoordinateAttribute2);
        Bitmap bitmap = this.mBitmap2;
        if (bitmap != null) {
            setBitmap2(bitmap);
        }
        this.filterTextureCoordinateAttribute3 = GLES20.glGetAttribLocation(getProgram(), "inputTextureCoordinate3");
        this.filterInputTextureUniform3 = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture3");
        GLES20.glEnableVertexAttribArray(this.filterTextureCoordinateAttribute3);
        Bitmap bitmap2 = this.mBitmap3;
        if (bitmap2 != null) {
            setBitmap3(bitmap2);
        }
    }

    public void setBitmap2(final Bitmap bitmap) {
        this.mBitmap2 = bitmap;
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageThreeInputFilter.1
            @Override // java.lang.Runnable
            public void run() {
                if (GPUImageThreeInputFilter.this.filterSourceTexture2 == -1) {
                    GLES20.glActiveTexture(33987);
                    Bitmap bitmap2 = bitmap;
                    if (bitmap2 == null || bitmap2.isRecycled()) {
                        return;
                    }
                    GPUImageThreeInputFilter.this.filterSourceTexture2 = OpenGlUtils.loadTexture(bitmap, -1, false);
                    return;
                }
                Bitmap bitmap3 = bitmap;
                if (bitmap3 == null || bitmap3.isRecycled()) {
                    return;
                }
                GLES20.glDeleteTextures(1, new int[]{GPUImageThreeInputFilter.this.filterSourceTexture2}, 0);
                GPUImageThreeInputFilter.this.filterSourceTexture2 = OpenGlUtils.loadTexture(bitmap, -1, false);
            }
        });
    }

    public void setBitmap3(final Bitmap bitmap) {
        this.mBitmap3 = bitmap;
        runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageThreeInputFilter.2
            @Override // java.lang.Runnable
            public void run() {
                if (GPUImageThreeInputFilter.this.filterSourceTexture3 == -1) {
                    GLES20.glActiveTexture(33988);
                    Bitmap bitmap2 = bitmap;
                    if (bitmap2 == null || bitmap2.isRecycled()) {
                        return;
                    }
                    GPUImageThreeInputFilter.this.filterSourceTexture3 = OpenGlUtils.loadTexture(bitmap, -1, false);
                    return;
                }
                Bitmap bitmap3 = bitmap;
                if (bitmap3 == null || bitmap3.isRecycled()) {
                    return;
                }
                GLES20.glDeleteTextures(1, new int[]{GPUImageThreeInputFilter.this.filterSourceTexture3}, 0);
                GPUImageThreeInputFilter.this.filterSourceTexture3 = OpenGlUtils.loadTexture(bitmap, -1, false);
            }
        });
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onDestroy() {
        super.onDestroy();
        int i = this.filterSourceTexture2;
        if (i != -1) {
            GLES20.glDeleteTextures(1, new int[]{i}, 0);
            this.filterSourceTexture2 = -1;
        }
        int i2 = this.filterSourceTexture3;
        if (i2 != -1) {
            GLES20.glDeleteTextures(1, new int[]{i2}, 0);
            this.filterSourceTexture3 = -1;
        }
    }

    public void deleteTexture2() {
        int i = this.filterSourceTexture2;
        if (i != -1) {
            GLES20.glDeleteTextures(1, new int[]{i}, 0);
            this.filterSourceTexture2 = -1;
        }
    }

    public void deleteTexture3() {
        int i = this.filterSourceTexture3;
        if (i != -1) {
            GLES20.glDeleteTextures(1, new int[]{i}, 0);
            this.filterSourceTexture3 = -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onDrawArraysPre() {
        GLES20.glEnableVertexAttribArray(this.filterTextureCoordinateAttribute2);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.filterSourceTexture2);
        GLES20.glUniform1i(this.filterInputTextureUniform2, 3);
        GLES20.glEnableVertexAttribArray(this.filterTextureCoordinateAttribute3);
        GLES20.glActiveTexture(33988);
        GLES20.glBindTexture(3553, this.filterSourceTexture3);
        GLES20.glUniform1i(this.filterInputTextureUniform3, 4);
        this.mTextureCoordinatesBuffer2.position(0);
        this.mTextureCoordinatesBuffer3.position(0);
        GLES20.glVertexAttribPointer(this.filterTextureCoordinateAttribute2, 2, 5126, false, 0, (Buffer) this.mTextureCoordinatesBuffer2);
        GLES20.glVertexAttribPointer(this.filterTextureCoordinateAttribute3, 2, 5126, false, 0, (Buffer) this.mTextureCoordinatesBuffer3);
    }

    public void setRotation(Rotation rotation, boolean z, boolean z2) {
        float[] rotation2 = TextureRotationUtil.getRotation(rotation, z, z2);
        ByteBuffer order = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = order.asFloatBuffer();
        asFloatBuffer.put(rotation2);
        asFloatBuffer.flip();
        this.mTextureCoordinatesBuffer2 = order;
        float[] rotation3 = TextureRotationUtil.getRotation(rotation, z, z2);
        ByteBuffer order2 = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer2 = order.asFloatBuffer();
        asFloatBuffer2.put(rotation3);
        asFloatBuffer2.flip();
        this.mTextureCoordinatesBuffer3 = order2;
    }
}
