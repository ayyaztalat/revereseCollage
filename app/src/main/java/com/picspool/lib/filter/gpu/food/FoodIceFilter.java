package com.picspool.lib.filter.gpu.food;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class FoodIceFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     lowp vec4 c2 = texture2D(inputImageTexture, textureCoordinate);\n     lowp vec4 c1 = vec4( mix(vec3(0.0), vec3(249.0/255.0, 1.0, 1.0), pow(min(max(c2.rgb -vec3(0.0), vec3(0.0)) / (vec3(247.0/255.0, 1.0, 241.0/255.0) - vec3(0.0)  ), vec3(1.0)), 1.0 /vec3(0.8, 1.0, 1.26))) , c2.a);\n     \n     lowp vec4 outputColor;\n     \n     outputColor.r = c1.r + c2.r * c2.a * (1.0 - c1.a);\n\n     outputColor.g = c1.g + c2.g * c2.a * (1.0 - c1.a);\n     \n     outputColor.b = c1.b + c2.b * c2.a * (1.0 - c1.a);\n     \n     outputColor.a = c1.a + c2.a * (1.0 - c1.a);\n     \n     lowp vec4 textureColor3 = outputColor;\n     mediump vec4 textureColor =vec4(mix(c2.rgb, textureColor3.rgb, textureColor3.a*0.83*mixturePercent), c2.a);\n     gl_FragColor = vec4( mix(vec3(0.0), vec3(1.0), pow(min(max(textureColor.rgb -vec3(3.0/255.0), vec3(0.0)) / (vec3(1.0) - vec3(3.0/255.0)  ), vec3(1.0)), 1.0 /vec3(1.05))) , textureColor.a);\n }";

    public FoodIceFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
