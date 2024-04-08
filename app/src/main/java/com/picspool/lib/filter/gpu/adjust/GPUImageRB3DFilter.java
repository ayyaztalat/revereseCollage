package com.picspool.lib.filter.gpu.adjust;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageRB3DFilter extends GPUImageFilter {
    public static final String RB3D_FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform highp float xOffset;\n \n void main()\n {\n     highp vec2 textureCoordinateR = vec2(textureCoordinate.x + xOffset, textureCoordinate.y);\n     highp vec2 textureCoordinateB = vec2(textureCoordinate.x - xOffset, textureCoordinate.y);\n     highp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     highp float redValue = texture2D(inputImageTexture, textureCoordinateR).r;\n     highp float blueValue = texture2D(inputImageTexture, textureCoordinateB).b;\n     gl_FragColor = vec4(redValue,textureColor.g,blueValue,1.0);\n } ";
    private float mXOffset;
    private int mXOffsetLocation;

    public GPUImageRB3DFilter() {
        this(1.0f);
    }

    public GPUImageRB3DFilter(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, RB3D_FRAGMENT_SHADER);
        this.mXOffset = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mXOffsetLocation = GLES20.glGetUniformLocation(getProgram(), "xOffset");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setOffset(this.mXOffset);
    }

    public void setOffset(float f) {
        this.mXOffset = f;
        setFloat(this.mXOffsetLocation, f);
    }
}
