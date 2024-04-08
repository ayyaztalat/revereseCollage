package com.picspool.lib.filter.gpu.season;

import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;

/* loaded from: classes3.dex */
public class GPUSeasonWinterIcedFilter extends GPUImageToneCurveFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n\n void main()\n {\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     lowp float redCurveValue = texture2D(toneCurveTexture, vec2(textureColor.r, 0.0)).r;\n     lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(textureColor.g, 0.0)).g;\n     lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(textureColor.b, 0.0)).b;\n\n     lowp vec4 textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue,textureColor.a);\n     mediump vec4 layer1 = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*0.73), textureColor.a);\n     mediump vec4 overlay = vec4(41.0/255.0, 50.0/255.0, 227.0/255.0, 1.0);\n     \n     lowp vec4 textureColor3 = layer1 * (overlay.a * (layer1 / layer1.a) + (2.0 * overlay * (1.0 - (layer1 / layer1.a)))) + overlay * (1.0 - layer1.a) + layer1 * (1.0 - overlay.a);\n     gl_FragColor =vec4(mix(layer1.rgb, textureColor3.rgb, textureColor3.a*0.25), layer1.a);\n }";

    public GPUSeasonWinterIcedFilter() {
        super(FRAGMENT_SHADER);
    }
}
