package com.picspool.lib.filter.gpu.retro;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class Retro17Filter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n \n const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\n \n uniform lowp float mixturePercent;\n void main()\n {\n    lowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n    lowp float luminance = dot(textureColor.rgb, luminanceWeighting);\n    lowp vec3 greyScaleColor = vec3(luminance);\n    \n    lowp vec4 textureColor3  = vec4(mix(greyScaleColor, textureColor.rgb, 0.8), textureColor.w);\n    textureColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);\n    lowp vec4 textureColor2 = vec4(135.0/255.0, 23.0/255.0, 134.0/255.0, 1.0);\n    \n    textureColor3 = max(textureColor, textureColor2);\n    mediump vec4 base =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*0.1*mixturePercent), textureColor.a);\n    mediump vec4 overlay = vec4(87.0/255.0, 71.0/255.0, 17.0/255.0, 1.0);\n     \n    textureColor3 = base * (overlay.a * (base / base.a) + (2.0 * overlay * (1.0 - (base / base.a)))) + overlay * (1.0 - base.a) + base * (1.0 - overlay.a);\n    gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*0.7*mixturePercent), base.a);\n     \n }";

    public Retro17Filter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
