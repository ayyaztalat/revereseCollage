package com.picspool.lib.filter.gpu.normal;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageOpacityFilter extends GPUImageFilter {
    public static final String OPACITY_FRAGMENT_SHADER = "  varying highp vec2 textureCoordinate;\n  \n  uniform sampler2D inputImageTexture;\n  uniform lowp float opacity;\n  \n  void main()\n  {\n      lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n      \n      gl_FragColor = vec4(textureColor.rgb, textureColor.a * opacity);\n  }\n";
    private float mOpacity;
    private int mOpacityLocation;

    public GPUImageOpacityFilter() {
        this(1.0f);
    }

    public GPUImageOpacityFilter(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, OPACITY_FRAGMENT_SHADER);
        this.mOpacity = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mOpacityLocation = GLES20.glGetUniformLocation(getProgram(), "opacity");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setOpacity(this.mOpacity);
    }

    public void setOpacity(float f) {
        this.mOpacity = f;
        setFloat(this.mOpacityLocation, f);
    }
}
