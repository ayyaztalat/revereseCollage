package com.picspool.lib.filter.gpu.season;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUSeasonWinterSnappyBabyFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n\n uniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 overlay = vec4(70.0/255.0, 61.0/255.0, 89.0/255.0, 1.0);\n     lowp vec4 textureColor2 = textureColor * (overlay.a * (textureColor / textureColor.a) + (2.0 * overlay * (1.0 - (textureColor / textureColor.a)))) + overlay * (1.0 - textureColor.a) + textureColor * (1.0 - overlay.a);\n     mediump vec4 layer1 = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*0.25), textureColor.a);     mediump vec4 whiteColor = vec4(1.0);\n     textureColor2 = vec4(101.0/255.0, 163.0/255.0, 199.0/255.0, 1.0);\n     lowp vec4 textureColor3 = whiteColor - ((whiteColor - textureColor2) * (whiteColor - layer1));\n     mediump vec4 layer2 = vec4(mix(layer1.rgb, textureColor3.rgb, textureColor3.a*0.25), layer1.a);\n     \n     mediump vec4 textureColor4 = vec4(89.0/255.0, 61.0/255.0, 83.0/255.0, 1.0);\n     lowp vec4 textureColor5 = whiteColor - ((whiteColor - textureColor4) * (whiteColor - layer2));\n     gl_FragColor = vec4(mix(layer2.rgb, textureColor5.rgb, textureColor5.a*0.25), layer2.a);\n     \n}";

    public GPUSeasonWinterSnappyBabyFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
