package com.picspool.lib.filter.gpu.film;

import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;

/* loaded from: classes3.dex */
public class FilmClassicBlueFilter extends GPUImageToneCurveFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n uniform lowp float mixturePercent;\n\n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     lowp float redCurveValue = texture2D(toneCurveTexture, vec2(textureColor.r, 0.0)).r;\n     lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(textureColor.g, 0.0)).g;\n     lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(textureColor.b, 0.0)).b;\n\n     lowp vec4 textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue,textureColor.a);\n     textureColor = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*mixturePercent), textureColor.a);\n     mediump vec4 base = vec4( mix(vec3(20.0/255.0), vec3(1.0), pow(min(max(textureColor.rgb -vec3(0.0), vec3(0.0)) / (vec3(1.0) - vec3(0.0)  ), vec3(1.0)), 1.0 /vec3(1.0))) , textureColor.a);\n     mediump vec4 overlay = vec4(11.0/255.0, 13.0/255.0, 53.0/255.0, 1.0);\n     lowp vec4 textureColor3 = vec4((overlay.rgb * base.a + base.rgb * overlay.a - 2.0 * overlay.rgb * base.rgb) + overlay.rgb * (1.0 - base.a) + base.rgb * (1.0 - overlay.a), base.a);\n     gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*0.7*mixturePercent), base.a);\n }";

    public FilmClassicBlueFilter() {
        super(FRAGMENT_SHADER);
    }
}
