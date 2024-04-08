package com.picspool.lib.filter.gpu.blend;

import com.picspool.lib.filter.gpu.father.GPUImageTwoInputFilter;

/* loaded from: classes3.dex */
public class GPUImageMapSelfBlendFilter extends GPUImageTwoInputFilter {
    public static final String SCREEN_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     mediump vec4 base = texture2D(inputImageTexture, textureCoordinate);\n     lowp float redCurveValue = texture2D(inputImageTexture2, vec2(base.r, base.r)).r;\n     lowp float greenCurveValue = texture2D(inputImageTexture2, vec2(base.g, base.g)).g;\n     lowp float blueCurveValue = texture2D(inputImageTexture2, vec2(base.b, base.b)).b;\n     lowp vec4 textureColor3 = vec4(redCurveValue,greenCurveValue,blueCurveValue, base.a);\n     gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), base.a);\n }";

    public GPUImageMapSelfBlendFilter() {
        super(SCREEN_BLEND_FRAGMENT_SHADER);
    }
}
