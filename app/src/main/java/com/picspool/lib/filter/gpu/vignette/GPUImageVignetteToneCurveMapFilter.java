package com.picspool.lib.filter.gpu.vignette;

import android.graphics.PointF;

/* loaded from: classes3.dex */
public class GPUImageVignetteToneCurveMapFilter extends GPUImageVignetteTwoInputFilter {
    public static final String VIGNETTING_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\nvarying highp vec2 textureCoordinate2;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n \n uniform highp vec2 vignetteCenter;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n uniform highp float vignetteInvert;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     highp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     highp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     highp vec4 base =  texture2D(inputImageTexture, textureCoordinate);\n     highp float redCurveValue = texture2D(inputImageTexture2, vec2(base.r, 0.0)).r;\n     highp float greenCurveValue = texture2D(inputImageTexture2, vec2(base.g, 0.5)).g;\n     highp float blueCurveValue = texture2D(inputImageTexture2, vec2(base.b, 1.0)).b;\n     highp vec4 textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue, base.a);\n     highp vec4 textureColor3 = vec4(mix(base.rgb, textureColor2.rgb, textureColor2.a*(1.0-percent)), base.a);\n     if(vignetteInvert == 0.0) textureColor3 = vec4(mix(base.rgb, textureColor2.rgb, textureColor2.a*percent), base.a);\n     gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), base.a);\n }";

    public GPUImageVignetteToneCurveMapFilter() {
        this(new PointF(), 0.3f, 0.75f);
    }

    public GPUImageVignetteToneCurveMapFilter(PointF pointF, float f, float f2) {
        super(VIGNETTING_FRAGMENT_SHADER, pointF, f, f2);
    }
}
