package com.picspool.lib.filter.gpu.film;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class FilmBVolTemplateFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     lowp vec4 c2 = texture2D(inputImageTexture, textureCoordinate);\n     \n     lowp vec4 c1 = vec4((c2.rgb + vec3(0.06)), c2.w);\n     c1 = vec4(((c1.rgb - vec3(0.5)) * 0.41 + vec3(0.5)), c1.w);\n     lowp vec4 outputColor;\n     \n     outputColor.r = c1.r + c2.r * c2.a * (1.0 - c1.a);\n\n     outputColor.g = c1.g + c2.g * c2.a * (1.0 - c1.a);\n     \n     outputColor.b = c1.b + c2.b * c2.a * (1.0 - c1.a);\n     \n     outputColor.a = c1.a + c2.a * (1.0 - c1.a);\n     \n     lowp vec4 textureColor3 = outputColor;\n     gl_FragColor =vec4(mix(c2.rgb, textureColor3.rgb, textureColor3.a*0.54*mixturePercent), c2.a);\n }";

    public FilmBVolTemplateFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
