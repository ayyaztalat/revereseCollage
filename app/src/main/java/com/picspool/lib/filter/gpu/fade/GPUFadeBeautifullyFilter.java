package com.picspool.lib.filter.gpu.fade;

import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;

/* loaded from: classes3.dex */
public class GPUFadeBeautifullyFilter extends GPUImageToneCurveFilter {
    private static final String FRAGMENT_SHADE = " varying highp vec2 textureCoordinate;\n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\n uniform lowp float mixturePercent;\n\n void main()\n {\n     lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     lowp float redCurveValue = texture2D(toneCurveTexture, vec2(textureColor.r, 0.0)).r;\n     lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(textureColor.g, 0.0)).g;\n     lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(textureColor.b, 0.0)).b;\n\n     lowp vec4 textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue,textureColor.a);\n     lowp vec4 color = vec4(mix(textureColor.rgb, textureColor2.rgb, textureColor2.a*mixturePercent), textureColor.a);\n     lowp float average = (color.r + color.g + color.b) / 3.0;\n     lowp float mx = max(color.r, max(color.g, color.b));\n     lowp float amt = (mx - average) * (-0.1 * 3.0);\n     color.rgb = mix(color.rgb, vec3(mx), amt);\n     lowp float luminance = dot(color.rgb, luminanceWeighting);\n     lowp vec3 greyScaleColor = vec3(luminance);\n     \n     lowp vec4 textureColor3  = vec4(mix(greyScaleColor, color.rgb, 0.9), color.w);\n     color =vec4(mix(color.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), color.a);\n     color = vec4((color.rgb + vec3(0.1)), color.w);\n     color = vec4(((color.rgb - vec3(0.5)) * 1.24 + vec3(0.5)), color.w);\n     mediump vec4 base = vec4(color.rgb * pow(2.0, 0.024), color.w);\n     mediump vec4 overlay = vec4(238.0/255.0, 236.0/255.0, 203.0/255.0, 1.0);\n     \n     textureColor3 = base * (overlay.a * (base / base.a) + (2.0 * overlay * (1.0 - (base / base.a)))) + overlay * (1.0 - base.a) + base * (1.0 - overlay.a);\n     gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*0.15*mixturePercent), base.a);\n     \n }";

    public GPUFadeBeautifullyFilter() {
        super(FRAGMENT_SHADE);
    }
}
