package com.picspool.lib.filter.gpu.newfilter;

import android.graphics.Color;
import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageMakeUpMouthBlendFilter extends GPUImageTwoInputFilter {
    public static final String ALPHA_BLEND_FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n\n uniform vec3 nColor;\n uniform lowp float mixturePercent;\n\n void main()\n {\n   lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n   lowp vec4 textureColor2 = texture2D(inputImageTexture2, textureCoordinate2);\n\n   lowp float nr = 0.0;    if (nColor.r < 0.5) {         nr = (2.0 * nColor.r - 1.0) * (textureColor.r - textureColor.r * textureColor.r) + textureColor.r;    } else {        nr = (2.0 * nColor.r - 1.0) * (sqrt(textureColor.r) - textureColor.r) + textureColor.r;    }    lowp float ng = 0.0;    if (nColor.g < 0.5) {        ng = (2.0 * nColor.g - 1.0) * (textureColor.g - textureColor.g * textureColor.g) + textureColor.g;    } else {        ng = (2.0 * nColor.g - 1.0) * (sqrt(textureColor.g) - textureColor.g) + textureColor.g;    }    lowp float nb = 0.0;    if (nColor.b < 0.5) {        nb = (2.0 * nColor.b - 1.0) * (textureColor.b - textureColor.b * textureColor.b) + textureColor.b;    } else {        nb = (2.0 * nColor.b - 1.0) * (sqrt(textureColor.b) - textureColor.b) + textureColor.b;    }    lowp vec3 newc = vec3(nr,ng,nb);    gl_FragColor = vec4(mix(textureColor.rgb, newc.rgb, mixturePercent * textureColor2.a), textureColor.a);\n }";
    private int mNewColorLocation;
    private int newColor;

    public GPUImageMakeUpMouthBlendFilter() {
        super(ALPHA_BLEND_FRAGMENT_SHADER);
        this.newColor = -1;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mNewColorLocation = GLES20.glGetUniformLocation(getProgram(), "nColor");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setNewColor(this.newColor);
    }

    public void setNewColor(int i) {
        this.newColor = i;
        setFloatVec3(this.mNewColorLocation, new float[]{Color.red(i) / 255.0f, Color.green(i) / 255.0f, Color.blue(i) / 255.0f});
    }
}
