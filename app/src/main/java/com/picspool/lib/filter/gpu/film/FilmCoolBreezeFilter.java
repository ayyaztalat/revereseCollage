package com.picspool.lib.filter.gpu.film;

import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;

/* loaded from: classes3.dex */
public class FilmCoolBreezeFilter extends GPUImageToneCurveFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\n uniform lowp float mixturePercent;\n\n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     lowp float redCurveValue = texture2D(toneCurveTexture, vec2(textureColor.r, 0.0)).r;\n     lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(textureColor.g, 0.0)).g;\n     lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(textureColor.b, 0.0)).b;\n\n     lowp vec4 textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue,textureColor.a);\n     textureColor = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*mixturePercent), textureColor.a);\n     textureColor = vec4( mix(vec3(10.0/255.0), vec3(1.0), pow(min(max(textureColor.rgb -vec3(0.0), vec3(0.0)) / (vec3(1.0) - vec3(0.0)  ), vec3(1.0)), 1.0 /vec3(1.0))) , textureColor.a);\n    lowp float luminance = dot(textureColor.rgb, luminanceWeighting);\n    lowp vec3 greyScaleColor = vec3(luminance);\n    \n    lowp vec4 textureColor3  = vec4(mix(greyScaleColor, textureColor.rgb, 0.8), textureColor.w);\n     mediump vec4 base =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);\n     mediump vec4 overlay = vec4(249.0/255.0, 239.0/255.0, 82.0/255.0, 1.0);\n     \n    textureColor3 = base * (overlay.a * (base / base.a) + (2.0 * overlay * (1.0 - (base / base.a)))) + overlay * (1.0 - base.a) + base * (1.0 - overlay.a);\n    textureColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*0.1*mixturePercent), base.a);\n     textureColor2 = vec4(129.0/255.0, 203.0/255.0, 246.0/255.0, 1.0);\n     \n     textureColor3 = vec4(clamp(textureColor.rgb + textureColor2.rgb - vec3(1.0), vec3(0.0), vec3(1.0)), textureColor.a);\n     gl_FragColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*0.1*mixturePercent), textureColor.a);\n }";

    public FilmCoolBreezeFilter() {
        super(FRAGMENT_SHADER);
    }
}
