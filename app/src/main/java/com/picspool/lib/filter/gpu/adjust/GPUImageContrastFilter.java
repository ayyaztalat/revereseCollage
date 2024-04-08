package com.picspool.lib.filter.gpu.adjust;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageContrastFilter extends GPUImageFilter {
    public static final String CONTRAST_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform lowp float contrast;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     gl_FragColor = vec4(((textureColor.rgb - vec3(0.5)) * contrast + vec3(0.5)), textureColor.w);\n }";
    private float mContrast;
    private int mContrastLocation;

    public GPUImageContrastFilter() {
        this(1.0f);
    }

    public GPUImageContrastFilter(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, CONTRAST_FRAGMENT_SHADER);
        this.mContrast = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mContrastLocation = GLES20.glGetUniformLocation(getProgram(), "contrast");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setContrast(this.mContrast);
    }

    public void setContrast(float f) {
        this.mContrast = f;
        setFloat(this.mContrastLocation, f);
    }
}
