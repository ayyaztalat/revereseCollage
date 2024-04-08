package com.picspool.lib.filter.gpu.season;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUSeasonSummerIndianFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n\n uniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 whiteColor = vec4(1.0);\n     lowp vec4 textureColor2 = whiteColor - ((whiteColor - textureColor) * (whiteColor - textureColor));\n     mediump vec4 layer1 = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*0.19), textureColor.a);\n     mediump vec4 overlay = vec4(170.0/255.0, 114.0/255.0, 26.0/255.0, 1.0);\n     \n     lowp vec4 textureColor3 = layer1 * (overlay.a * (layer1 / layer1.a) + (2.0 * overlay * (1.0 - (layer1 / layer1.a)))) + overlay * (1.0 - layer1.a) + layer1 * (1.0 - overlay.a);\n     mediump vec4 layer2 = vec4(mix(layer1.rgb, textureColor3.rgb, textureColor3.a*0.25), layer1.a);     mediump vec4 textureColor4 = vec4(206.0/255.0, 181.0/255.0, 80.0/255.0, 1.0);\n     lowp vec4 textureColor5 = whiteColor - ((whiteColor - textureColor4) * (whiteColor - layer2));\n     mediump vec4 layer3 = vec4(mix(layer2.rgb, textureColor5.rgb, textureColor5.a*0.26), layer2.a);\n     overlay = vec4(168.0/255.0, 70.0/255.0, 15.0/255.0, 1.0);\n     \n     lowp vec4 textureColor6 = layer3 * (overlay.a * (layer3 / layer3.a) + (2.0 * overlay * (1.0 - (layer3 / layer3.a)))) + overlay * (1.0 - layer3.a) + layer3 * (1.0 - overlay.a);\n     gl_FragColor = vec4(mix(layer3.rgb, textureColor6.rgb, textureColor6.a*0.25), layer3.a);     \n}";

    public GPUSeasonSummerIndianFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
