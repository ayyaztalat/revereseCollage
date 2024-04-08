package com.picspool.lib.filter.gpu.adjust;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageNewVibranceFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n    uniform sampler2D inputImageTexture;\n    uniform lowp float vibrance;\n \n    void main() {\n        lowp vec4 color = texture2D(inputImageTexture, textureCoordinate);\n        lowp float average = (color.r + color.g + color.b) / 3.0;\n        lowp float mx = max(color.r, max(color.g, color.b));\n        lowp float amt = (mx - average) * (-vibrance * 3.0);\n        color.rgb = mix(color.rgb, vec3(mx), amt);\n        gl_FragColor = color;\n    }";
    private float mSaturation;
    private int mSaturationLocation;

    public GPUImageNewVibranceFilter() {
        this(0.0f);
    }

    public GPUImageNewVibranceFilter(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
        this.mSaturation = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mSaturationLocation = GLES20.glGetUniformLocation(getProgram(), "vibrance");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setSaturation(this.mSaturation);
    }

    public void setSaturation(float f) {
        this.mSaturation = f;
        setFloat(this.mSaturationLocation, f);
    }
}
