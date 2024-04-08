package com.picspool.lib.filter.gpu.normal;

import android.graphics.Color;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageColorFilter extends GPUImageFilter {
    public static final String COLOR_FRAGMENT_SHADER = "  varying highp vec2 textureCoordinate;\n  uniform sampler2D inputImageTexture;\n  uniform lowp vec4 color;\n  \n  uniform lowp float mixturePercent;\n  \n  void main()\n  {\n      lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n      \n      gl_FragColor = vec4(mix(textureColor.rgb, color.rgb, textureColor.a*0.42), textureColor.a);  }\n";
    private int mColor;
    private int mColorLocation;
    private float[] mColorRGBA;

    public GPUImageColorFilter(int i) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, COLOR_FRAGMENT_SHADER);
        this.mColor = i;
    }

    public GPUImageColorFilter(int i, float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, COLOR_FRAGMENT_SHADER);
        this.mColor = i;
        this.mMix = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mColorLocation = GLES20.glGetUniformLocation(getProgram(), "color");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setColor(this.mColor);
    }

    public void setColor(int i) {
        this.mColor = i;
        float[] fArr = {Color.red(i) / 255.0f, Color.green(this.mColor) / 255.0f, Color.blue(this.mColor) / 255.0f, 1.0f};
        this.mColorRGBA = fArr;
        setFloatVec4(this.mColorLocation, fArr);
        setMix(this.mMix);
    }
}
