package com.picspool.lib.filter.gpu.blend;

import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageColorBurnBlendFilter extends GPUImageTwoInputFilter {
    public static final String COLOR_BURN_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n uniform lowp float mixturePercent;\n void main()\n {\n    mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n    mediump vec4 textureColor2 = texture2D(inputImageTexture2, textureCoordinate2);\n    mediump vec4 whiteColor = vec4(1.0);\n     lowp vec4 textureColor3 = whiteColor - (whiteColor - textureColor) / textureColor2;\n     gl_FragColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);\n }";

    public GPUImageColorBurnBlendFilter() {
        super(COLOR_BURN_BLEND_FRAGMENT_SHADER);
    }
}
