package com.picspool.lib.filter.gpu.father;

import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import com.picspool.lib.filter.gpu.util.Rotation;

/* loaded from: classes3.dex */
public class GPUImageTwoSrcInputFilter extends GPUImageTwoInputFilter {
    public GPUImageTwoSrcInputFilter(String str) {
        super(str);
    }

    public GPUImageTwoSrcInputFilter(String str, String str2) {
        super(str, str2);
    }

    public void setSecondTexture(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2, boolean z) {
        if (z && this.filterSourceTexture2 != -1) {
            GLES20.glDeleteTextures(1, new int[]{this.filterSourceTexture2}, 0);
        }
        this.filterSourceTexture2 = i;
        if (i == -1) {
            setRotation(Rotation.NORMAL, false, false);
            return;
        }
        float[] fArr = new float[8];
        floatBuffer2.position(0);
        floatBuffer2.get(fArr);
        floatBuffer2.position(0);
        ByteBuffer order = ByteBuffer.allocateDirect(32).order(ByteOrder.nativeOrder());
        FloatBuffer asFloatBuffer = order.asFloatBuffer();
        asFloatBuffer.put(fArr);
        asFloatBuffer.flip();
        this.mTexture2CoordinatesBuffer = order;
    }
}
