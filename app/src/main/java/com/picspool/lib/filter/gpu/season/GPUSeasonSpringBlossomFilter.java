package com.picspool.lib.filter.gpu.season;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUSeasonSpringBlossomFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n\n uniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 whiteColor = vec4(1.0);\n     lowp vec4 textureColor2 = whiteColor - ((whiteColor - textureColor) * (whiteColor - textureColor));\n     mediump vec4 layer1 = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*0.15), textureColor.a);\n     mediump vec4 layer2 = vec4( mix(vec3(16.0/255.0, 8.0/255.0, 0.0), vec3(1.0, 248.0/255.0, 248.0/255.0), pow(min(max(layer1.rgb -vec3(0.0, 0.0, 5.0/255.0), vec3(0.0)) / (vec3(248.0/255.0, 1.0, 1.0) - vec3(0.0, 0.0, 5.0/255.0)  ), vec3(1.0)), 1.0 /vec3(1.0))) , layer1.a);\n     mediump vec4 textureColor3 = vec4(204.0/255.0, 164.0/255.0, 248.0/255.0, 1.0);     lowp vec4 textureColor4 = whiteColor - ((whiteColor - textureColor3) * (whiteColor - layer2));\n     gl_FragColor =vec4(mix(layer2.rgb, textureColor4.rgb, textureColor4.a*0.18), layer2.a);\n     \n}";

    public GPUSeasonSpringBlossomFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
