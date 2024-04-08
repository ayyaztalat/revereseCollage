package com.picspool.lib.filter.gpu.blend;

import com.picspool.lib.filter.gpu.father.GPUImageThreeInputFilter;

/* loaded from: classes3.dex */
public class GPUImageMapBlendFilter extends GPUImageThreeInputFilter {
    public static final String SCREEN_BLEND_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n varying highp vec2 textureCoordinate3;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n uniform sampler2D inputImageTexture3;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     mediump vec4 base = texture2D(inputImageTexture, textureCoordinate);\n     mediump vec4 overlay = texture2D(inputImageTexture2, textureCoordinate2);\n     lowp float redCurveValue = texture2D(inputImageTexture3, vec2(overlay.r, base.r)).r;\n     lowp float greenCurveValue = texture2D(inputImageTexture3, vec2(overlay.g, base.g)).g;\n     lowp float blueCurveValue = texture2D(inputImageTexture3, vec2(overlay.b, base.b)).b;\n     lowp vec4 textureColor3 = vec4(redCurveValue,greenCurveValue,blueCurveValue, base.a);\n     gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), base.a);\n }";

    public GPUImageMapBlendFilter() {
        super(SCREEN_BLEND_FRAGMENT_SHADER);
    }
}
