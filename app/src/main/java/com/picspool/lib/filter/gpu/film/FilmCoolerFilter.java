package com.picspool.lib.filter.gpu.film;

import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;

/* loaded from: classes3.dex */
public class FilmCoolerFilter extends GPUImageToneCurveFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n uniform lowp float mixturePercent;\n\n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     textureColor = vec4((textureColor.rgb + vec3(0.022)), textureColor.w);\n     textureColor = vec4(((textureColor.rgb - vec3(0.5)) * 1.084 + vec3(0.5)), textureColor.w);\n    lowp vec4 textureColor2 = vec4(43.0/255.0, 148.0/255.0, 214.0/255.0, 1.0);\n    \n    lowp vec4 textureColor3 = max(textureColor, textureColor2);\n     textureColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*0.31*mixturePercent), textureColor.a);\n     lowp float redCurveValue = texture2D(toneCurveTexture, vec2(textureColor.r, 0.0)).r;\n     lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(textureColor.g, 0.0)).g;\n     lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(textureColor.b, 0.0)).b;\n\n     textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue,textureColor.a);\n   gl_FragColor = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*mixturePercent), textureColor.a);\n }";

    public FilmCoolerFilter() {
        super(FRAGMENT_SHADER);
    }
}
