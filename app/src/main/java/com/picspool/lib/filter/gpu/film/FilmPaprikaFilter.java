package com.picspool.lib.filter.gpu.film;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class FilmPaprikaFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n uniform lowp float mixturePercent;\n \n void main()\n {\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     lowp vec4 c1 = vec4( mix(vec3(0.0, 0.0, 28.0/255.0), vec3(1.0, 225.0/255.0, 197.0/255.0), pow(min(max(textureColor.rgb -vec3(3.0/255.0), vec3(0.0)) / (vec3(253.0/255.0) - vec3(3.0/255.0)  ), vec3(1.0)), 1.0 /vec3(1.13))) , textureColor.a);\n     lowp vec4 c2 = textureColor;\n     \n     lowp vec4 outputColor;\n     \n     outputColor.r = c1.r + c2.r * c2.a * (1.0 - c1.a);\n\n     outputColor.g = c1.g + c2.g * c2.a * (1.0 - c1.a);\n     \n     outputColor.b = c1.b + c2.b * c2.a * (1.0 - c1.a);\n     \n     outputColor.a = c1.a + c2.a * (1.0 - c1.a);\n     \n     lowp vec4 textureColor3 = outputColor;\n     gl_FragColor =vec4(mix(c2.rgb, textureColor3.rgb, textureColor3.a*0.76*mixturePercent), c2.a);\n }\n";

    public FilmPaprikaFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
