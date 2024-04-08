package com.picspool.lib.filter.gpu.father;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.util.Log;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.util.OpenGlUtils;
import com.picspool.lib.filter.gpu.util.Rotation;
import com.picspool.lib.filter.gpu.util.TextureRotationUtil;

/* loaded from: classes3.dex */
public class GPUImageTwoInputFilter extends GPUImageFilter {
    private static final String VERTEX_SHADER = "attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\n \nuniform mat4 transformMatrix;\nuniform mat4 transformMatrix2;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\n \nvoid main()\n{\n    gl_Position = transformMatrix * vec4(position.xyz, 1.0);\n    textureCoordinate = inputTextureCoordinate.xy;\n    vec4 pos2 = transformMatrix2*vec4(inputTextureCoordinate2.xy,1.0,1.0);    textureCoordinate2 = pos2.xy;\n}";
    public int filterInputTextureUniform2;
    public int filterSecondTextureCoordinateAttribute;
    public int filterSourceTexture2;
    public int filterTransformLocation2;
    private Bitmap mBitmap;
    public ByteBuffer mTexture2CoordinatesBuffer;
    protected float[] mTransform2;

    public GPUImageTwoInputFilter(String str) {
        this(VERTEX_SHADER, str);
    }

    public GPUImageTwoInputFilter(String str, String str2) {
        super(str, str2);
        this.filterSourceTexture2 = -1;
        this.mTransform2 = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        setRotation(Rotation.NORMAL, false, false);
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.filterSecondTextureCoordinateAttribute = GLES20.glGetAttribLocation(getProgram(), "inputTextureCoordinate2");
        this.filterInputTextureUniform2 = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture2");
        this.filterTransformLocation2 = GLES20.glGetUniformLocation(getProgram(), "transformMatrix2");
        GLES20.glEnableVertexAttribArray(this.filterSecondTextureCoordinateAttribute);
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            setBitmap(bitmap);
        }
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setTransform2(this.mTransform2);
    }

    public void setBitmap(final Bitmap bitmap) {
        this.mBitmap = bitmap;
        synchronized (bitmap) {
            runOnDraw(new Runnable() { // from class: com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter.1
                @Override // java.lang.Runnable
                public void run() {
                    if (GPUImageTwoInputFilter.this.filterSourceTexture2 == -1) {
                        GLES20.glActiveTexture(33987);
                        Bitmap bitmap2 = bitmap;
                        if (bitmap2 == null || bitmap2.isRecycled()) {
                            return;
                        }
                        GPUImageTwoInputFilter.this.filterSourceTexture2 = OpenGlUtils.loadTexture(bitmap, -1, false);
                        return;
                    }
                    Bitmap bitmap3 = bitmap;
                    if (bitmap3 == null || bitmap3.isRecycled()) {
                        return;
                    }
                    GLES20.glDeleteTextures(1, new int[]{GPUImageTwoInputFilter.this.filterSourceTexture2}, 0);
                    GPUImageTwoInputFilter.this.filterSourceTexture2 = OpenGlUtils.loadTexture(bitmap, -1, false);
                }
            });
        }
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onDestroy() {
        super.onDestroy();
        int i = this.filterSourceTexture2;
        if (i != -1) {
            GLES20.glDeleteTextures(1, new int[]{i}, 0);
            this.filterSourceTexture2 = -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onDrawArraysPre() {
        if (this.filterSourceTexture2 == -1) {
            Log.i("GLES", "" + getClass().getName() + " filterSourceTexture2:" + this.filterSourceTexture2);
        }
        GLES20.glEnableVertexAttribArray(this.filterSecondTextureCoordinateAttribute);
        GLES20.glActiveTexture(33987);
        GLES20.glBindTexture(3553, this.filterSourceTexture2);
        GLES20.glUniform1i(this.filterInputTextureUniform2, 3);
        this.mTexture2CoordinatesBuffer.position(0);
        GLES20.glVertexAttribPointer(this.filterSecondTextureCoordinateAttribute, 2, 5126, false, 0, (Buffer) this.mTexture2CoordinatesBuffer);
    }

    public void setRotation(Rotation rotation, boolean z, boolean z2) {
        float[] rotation2 = TextureRotationUtil.getRotation(rotation, z, z2);
        ByteBuffer order = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = order.asFloatBuffer();
        asFloatBuffer.put(rotation2);
        asFloatBuffer.flip();
        this.mTexture2CoordinatesBuffer = order;
    }

    public Bitmap getTextureBitmap() {
        return this.mBitmap;
    }

    public void setTransform2(float[] fArr) {
        this.mTransform2 = fArr;
        setUniformMatrix4f(this.filterTransformLocation2, fArr);
    }
}
