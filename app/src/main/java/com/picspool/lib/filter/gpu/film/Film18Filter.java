package com.picspool.lib.filter.gpu.film;

import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;

/* loaded from: classes3.dex */
public class Film18Filter extends GPUImageToneCurveFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\n uniform lowp float mixturePercent;\n\n void main()\n {\n    lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n    lowp float luminance = dot(textureColor.rgb, luminanceWeighting);\n    lowp vec3 greyScaleColor = vec3(luminance);\n    \n    lowp vec4 textureColor3  = vec4(mix(greyScaleColor, textureColor.rgb, 0.5), textureColor.w);\n     textureColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);\n     lowp float redCurveValue = texture2D(toneCurveTexture, vec2(textureColor.r, 0.0)).r;\n     lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(textureColor.g, 0.0)).g;\n     lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(textureColor.b, 0.0)).b;\n\n     lowp vec4 textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue,textureColor.a);\n    textureColor = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*mixturePercent), textureColor.a);\n    textureColor2 = vec4(35.0/255.0, 235.0/255.0, 214.0/255.0, 1.0);\n    \n    textureColor3 = max(textureColor, textureColor2);\n     textureColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*0.2*mixturePercent), textureColor.a);\n    luminance = dot(textureColor.rgb, luminanceWeighting);\n    greyScaleColor = vec3(luminance);\n    \n    textureColor3  = vec4(mix(greyScaleColor, textureColor.rgb, 0.7), textureColor.w);\n     gl_FragColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);\n     \n }";

    public Film18Filter() {
        super(FRAGMENT_SHADER);
    }
}
