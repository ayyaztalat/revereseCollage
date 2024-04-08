package com.picspool.lib.filter.gpu.season;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUSeasonAutumnPremiumFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nvoid main()\n{\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     lowp vec4 textureColor2 = vec4(253.0/255.0, 171.0/255.0, 12.0/255.0, 1.0);\n          \n     lowp vec4 textureColor3 = textureColor2 * textureColor + textureColor2 * (1.0 - textureColor.a) + textureColor * (1.0 - textureColor2.a);\n     mediump vec4 layer1 =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*0.2), textureColor.a);\n     mediump vec4 overlay = vec4(4.0/255.0, 8.0/255.0, 84.0/255.0, 1.0);\n     textureColor3 = vec4((overlay.rgb * layer1.a + layer1.rgb * overlay.a - 2.0 * overlay.rgb * layer1.rgb) + overlay.rgb * (1.0 - layer1.a) + layer1.rgb * (1.0 - overlay.a), layer1.a);\n     gl_FragColor =vec4(mix(layer1.rgb, textureColor3.rgb, textureColor3.a*0.2), layer1.a);\n }";

    public GPUSeasonAutumnPremiumFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
