package com.picspool.lib.filter.gpu.adjust;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageGammaFilter extends GPUImageFilter {
    public static final String GAMMA_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float gamma;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     gl_FragColor = vec4(pow(textureColor.rgb, vec3(gamma)), textureColor.w);\n }";
    private float mGamma;
    private int mGammaLocation;

    public GPUImageGammaFilter() {
        this(1.2f);
    }

    public GPUImageGammaFilter(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, GAMMA_FRAGMENT_SHADER);
        this.mGamma = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mGammaLocation = GLES20.glGetUniformLocation(getProgram(), "gamma");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setGamma(this.mGamma);
    }

    public void setGamma(float f) {
        this.mGamma = f;
        setFloat(this.mGammaLocation, f);
    }
}
