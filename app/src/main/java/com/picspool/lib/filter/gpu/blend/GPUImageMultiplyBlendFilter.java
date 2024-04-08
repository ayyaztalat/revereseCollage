package com.picspool.lib.filter.gpu.blend;

import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageMultiplyBlendFilter extends GPUImageTwoInputFilter {
    public static final String MULTIPLY_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n uniform lowp float mixturePercent;\n \n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     lowp vec4 textureColor2 = texture2D(inputImageTexture2, textureCoordinate2);\n          \n     lowp vec4 textureColor3 = textureColor2 * textureColor + textureColor2 * (1.0 - textureColor.a) + textureColor * (1.0 - textureColor2.a);\n     gl_FragColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);\n }";

    public GPUImageMultiplyBlendFilter() {
        super(MULTIPLY_BLEND_FRAGMENT_SHADER);
    }
}
