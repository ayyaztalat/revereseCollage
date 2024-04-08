package com.picspool.lib.filter.gpu.normal;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImagePopArtFilter extends GPUImageFilter {
    public static final String POPART_FRAGMENT_SHADER = "precision highp float;\nvarying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform mediump float brightGap;\nconst highp vec4 kRGBToYPrime = vec4 (0.299, 0.587, 0.114, 0.0);\nconst highp vec4 kRGBToI = vec4 (0.595716, -0.274453, -0.321263, 0.0);\nconst highp vec4 kRGBToQ = vec4 (0.211456, -0.522591, 0.31135, 0.0);\n\nconst highp vec4 kYIQToR = vec4 (1.0, 0.9563, 0.6210, 0.0);\nconst highp vec4 kYIQToG = vec4 (1.0, -0.2721, -0.6474, 0.0);\nconst highp vec4 kYIQToB = vec4 (1.0, -1.1070, 1.7046, 0.0);\n\nvoid main ()\n{\n    highp vec4 color = texture2D(inputImageTexture, textureCoordinate);\n\n    highp float YPrime = dot (color, kRGBToYPrime);\n    highp vec4 artColor = vec4(0.0);    if (YPrime < brightGap){      artColor = vec4(1.0);    }    gl_FragColor = artColor;\n}\n";
    private float mBrightGapAmount;
    private int mBrightGapLocation;

    public GPUImagePopArtFilter() {
        this(0.5f);
    }

    public GPUImagePopArtFilter(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, POPART_FRAGMENT_SHADER);
        this.mBrightGapAmount = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mBrightGapLocation = GLES20.glGetUniformLocation(getProgram(), "brightGap");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setBrightGap(this.mBrightGapAmount);
    }

    public void setBrightGap(float f) {
        this.mBrightGapAmount = f;
        setFloat(this.mBrightGapLocation, f);
    }
}
