package com.picspool.lib.filter.gpu.season;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUSeasonSummerDayFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n\n uniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 whiteColor = vec4(1.0);\n     lowp vec4 textureColor2 = whiteColor - ((whiteColor - textureColor) * (whiteColor - textureColor));\n     mediump vec4 layer1 = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*0.13), textureColor.a);\n     mediump vec4 textureColor3 = vec4(100.0/255.0, 83.0/255.0, 93.0/255.0, 1.0);\n     lowp vec4 textureColor4 = whiteColor - ((whiteColor - textureColor3) * (whiteColor - layer1));\n     mediump vec4 layer2 = vec4(mix(layer1.rgb, textureColor4.rgb, textureColor4.a*0.70), layer1.a);\n     gl_FragColor = vec4( mix(vec3(0.0), vec3(1.0), pow(min(max(layer2.rgb -vec3(22.0/255.0), vec3(0.0)) / (vec3(1.0) - vec3(22.0/255.0)  ), vec3(1.0)), 1.0 /vec3(1.0))) , layer2.a);\n     \n}";

    public GPUSeasonSummerDayFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
