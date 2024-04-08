package com.picspool.lib.filter.gpu.blend;

import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageDarkenBlendFilter extends GPUImageTwoInputFilter {
    public static final String DARKEN_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n uniform lowp float mixturePercent;\n void main()\n {\n    lowp vec4 base = texture2D(inputImageTexture, textureCoordinate);\n    lowp vec4 overlayer = texture2D(inputImageTexture2, textureCoordinate2);\n    \n     lowp vec4 textureColor3 = vec4(min(overlayer.rgb * base.a, base.rgb * overlayer.a) + overlayer.rgb * (1.0 - base.a) + base.rgb * (1.0 - overlayer.a), 1.0);\n     gl_FragColor = vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), base.a);\n }";

    public GPUImageDarkenBlendFilter() {
        super(DARKEN_BLEND_FRAGMENT_SHADER);
    }
}
