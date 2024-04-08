package com.picspool.lib.filter.gpu.fade;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUFadeCoolHazeFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     lowp vec4 c2 = texture2D(inputImageTexture, textureCoordinate);\n     lowp vec4 c1 = vec4( mix(vec3(28.0/255.0), vec3(239.0/255.0), pow(min(max(c2.rgb -vec3(0.0), vec3(0.0)) / (vec3(1.0) - vec3(0.0)  ), vec3(1.0)), 1.0 /vec3(1.0))) , c2.a);\n     c1 = vec4( mix(vec3(0.0, 0.0, 11.0/255.0), vec3(1.0), pow(min(max(c1.rgb -vec3(0.0), vec3(0.0)) / (vec3(1.0) - vec3(0.0)  ), vec3(1.0)), 1.0 /vec3(0.92, 1.0, 1.0))) , c1.a);\n     \n     lowp vec4 outputColor;\n     \n     outputColor.r = c1.r + c2.r * c2.a * (1.0 - c1.a);\n\n     outputColor.g = c1.g + c2.g * c2.a * (1.0 - c1.a);\n     \n     outputColor.b = c1.b + c2.b * c2.a * (1.0 - c1.a);\n     \n     outputColor.a = c1.a + c2.a * (1.0 - c1.a);\n     \n     lowp vec4 textureColor3 = outputColor;\n     lowp vec4 layer1 =vec4(mix(c2.rgb, textureColor3.rgb, textureColor3.a*0.78*mixturePercent), c2.a);\n     layer1 = vec4( mix(vec3(6.0/255.0), vec3(1.0), pow(min(max(layer1.rgb -vec3(3.0/255.0), vec3(0.0)) / (vec3(1.0) - vec3(3.0/255.0)  ), vec3(1.0)), 1.0 /vec3(1.0))) , layer1.a);     gl_FragColor = vec4( mix(vec3(0.0), vec3(1.0, 252.0/255.0, 254.0/255.0), pow(min(max(layer1.rgb -vec3(0.0), vec3(0.0)) / (vec3(254.0/255.0, 1.0, 1.0) - vec3(0.0)  ), vec3(1.0)), 1.0 /vec3(1.0))) , layer1.a);\n }";

    public GPUFadeCoolHazeFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
