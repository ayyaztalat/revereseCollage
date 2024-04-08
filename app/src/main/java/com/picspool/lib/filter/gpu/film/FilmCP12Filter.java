package com.picspool.lib.filter.gpu.film;

import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;

/* loaded from: classes3.dex */
public class FilmCP12Filter extends GPUImageToneCurveFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n uniform lowp float mixturePercent;\n\n void main()\n {\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 textureColor2 = vec4(17.0/255.0, 116.0/255.0, 201.0/255.0, 1.0);\n     \n     lowp vec4 textureColor3 = vec4(clamp(textureColor.rgb + textureColor2.rgb - vec3(1.0), vec3(0.0), vec3(1.0)), textureColor.a);\n     textureColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*0.15*mixturePercent), textureColor.a);\n     lowp float redCurveValue = texture2D(toneCurveTexture, vec2(textureColor.r, 0.0)).r;\n     lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(textureColor.g, 0.0)).g;\n     lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(textureColor.b, 0.0)).b;\n\n     textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue,textureColor.a);\n     textureColor = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*mixturePercent), textureColor.a);\n     gl_FragColor = vec4( mix(vec3(12.0/255.0), vec3(1.0), pow(min(max(textureColor.rgb -vec3(0.0), vec3(0.0)) / (vec3(1.0) - vec3(0.0)  ), vec3(1.0)), 1.0 /vec3(1.18))) , textureColor.a);\n }";

    public FilmCP12Filter() {
        super(FRAGMENT_SHADER);
    }
}
