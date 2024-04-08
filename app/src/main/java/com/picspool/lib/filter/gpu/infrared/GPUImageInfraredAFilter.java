package com.picspool.lib.filter.gpu.infrared;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageInfraredAFilter extends GPUImageFilter {
    public static final String INFRARED_FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\nuniform sampler2D inputImageTexture;\nuniform lowp float mixturePercent;\n highp float lum(lowp vec3 c) {\n     return dot(c, vec3(0.3, 0.59, 0.11));\n }\n \n lowp vec3 clipcolor(lowp vec3 c) {\n     highp float l = lum(c);\n     lowp float n = min(min(c.r, c.g), c.b);\n     lowp float x = max(max(c.r, c.g), c.b);\n     \n     if (n < 0.0) {\n         c.r = l + ((c.r - l) * l) / (l - n);\n         c.g = l + ((c.g - l) * l) / (l - n);\n         c.b = l + ((c.b - l) * l) / (l - n);\n     }\n     if (x > 1.0) {\n         c.r = l + ((c.r - l) * (1.0 - l)) / (x - l);\n         c.g = l + ((c.g - l) * (1.0 - l)) / (x - l);\n         c.b = l + ((c.b - l) * (1.0 - l)) / (x - l);\n     }\n     \n     return c;\n }\n\n lowp vec3 setlum(lowp vec3 c, highp float l) {\n     highp float d = l - lum(c);\n     c = c + vec3(d);\n     return clipcolor(c);\n }\nvoid main(){\tlowp vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);   lowp vec4 invertColor = vec4((1.0 - textureColor.rgb), textureColor.w);   lowp vec4 infraredColor = vec4(textureColor.rgb * (1.0 - invertColor.a) + setlum(invertColor.rgb, lum(textureColor.rgb)) * invertColor.a, textureColor.a);   infraredColor.g = infraredColor.b * infraredColor.g;   gl_FragColor = vec4(mix(textureColor.rgb, infraredColor.rgb, mixturePercent), textureColor.a);}";

    public GPUImageInfraredAFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, INFRARED_FRAGMENT_SHADER);
    }
}
