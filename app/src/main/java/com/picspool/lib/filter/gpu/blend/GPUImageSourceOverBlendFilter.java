package com.picspool.lib.filter.gpu.blend;

import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageSourceOverBlendFilter extends GPUImageTwoInputFilter {
    public static final String SOURCE_OVER_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n \n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n uniform lowp float mixturePercent;\n void main()\n {\n   lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n   lowp vec4 textureColor2 = texture2D(inputImageTexture2, textureCoordinate);\n   \n   lowp vec4 textureColor3 = mix(textureColor, textureColor2, textureColor2.a);\n   gl_FragColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);\n }";

    public GPUImageSourceOverBlendFilter() {
        super(SOURCE_OVER_BLEND_FRAGMENT_SHADER);
    }
}
