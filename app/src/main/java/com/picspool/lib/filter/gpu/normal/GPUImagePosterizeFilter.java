package com.picspool.lib.filter.gpu.normal;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImagePosterizeFilter extends GPUImageFilter {
    public static final String POSTERIZE_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n\nuniform sampler2D inputImageTexture;\nuniform highp float colorLevels;\n\nvoid main()\n{\n   highp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n   \n   gl_FragColor = floor((textureColor * colorLevels) + vec4(0.5)) / colorLevels;\n}";
    private int mColorLevels;
    private int mGLUniformColorLevels;

    public GPUImagePosterizeFilter() {
        this(10);
    }

    public GPUImagePosterizeFilter(int i) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, POSTERIZE_FRAGMENT_SHADER);
        this.mColorLevels = i;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mGLUniformColorLevels = GLES20.glGetUniformLocation(getProgram(), "colorLevels");
        setColorLevels(this.mColorLevels);
    }

    public void setColorLevels(int i) {
        this.mColorLevels = i;
        setFloat(this.mGLUniformColorLevels, i);
    }
}
