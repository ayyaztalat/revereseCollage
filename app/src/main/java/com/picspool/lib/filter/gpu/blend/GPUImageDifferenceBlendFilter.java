package com.picspool.lib.filter.gpu.blend;

import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageDifferenceBlendFilter extends GPUImageTwoInputFilter {
    public static final String DIFFERENCE_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 textureColor2 = texture2D(inputImageTexture2, textureCoordinate2);\n     lowp vec4 textureColor3 = vec4(abs(textureColor2.rgb - textureColor.rgb), textureColor.a);\n     gl_FragColor = vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);\n }";

    public GPUImageDifferenceBlendFilter() {
        super(DIFFERENCE_BLEND_FRAGMENT_SHADER);
    }
}
