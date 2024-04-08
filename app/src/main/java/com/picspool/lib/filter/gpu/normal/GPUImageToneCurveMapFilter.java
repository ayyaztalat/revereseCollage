package com.picspool.lib.filter.gpu.normal;

import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageToneCurveMapFilter extends GPUImageTwoInputFilter {
    public static final String TONE_CURVE_BLENDMAP_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n uniform lowp float mixturePercent;\n \n void main()\n {\n     highp vec4 base = texture2D(inputImageTexture, textureCoordinate);\n     highp vec4 overlay = texture2D(inputImageTexture2, textureCoordinate2);\n     highp float redCurveValue = texture2D(inputImageTexture2, vec2(base.r, 0.0)).r;\n     highp float greenCurveValue = texture2D(inputImageTexture2, vec2(base.g, 0.5)).g;\n     highp float blueCurveValue = texture2D(inputImageTexture2, vec2(base.b, 1.0)).b;\n     highp vec4 textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue, base.a);\n     gl_FragColor = vec4(mix(base.rgb, textureColor2.rgb, textureColor2.a*mixturePercent), base.a);\n }";

    public GPUImageToneCurveMapFilter() {
        super(TONE_CURVE_BLENDMAP_FRAGMENT_SHADER);
    }
}
