package com.picspool.lib.filter.gpu.scene;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageSceneLowSaturationFilter extends GPUImageFilter {
    public static final String SCENE_FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n \n precision highp float;\n \n uniform float width;\n uniform float height;\n uniform sampler2D inputImageTexture;\n \n #define GammaCorrection(color, gamma)  pow(color, vec3(1.0 / gamma)) \n \n #define LevelsControlInputRange(color, minInput, maxInput)  min(max(color - vec3(minInput), vec3(0.0)) / (vec3(maxInput) - vec3(minInput)), vec3(1.0)) \n \n #define LevelsControlInput(color, minInput, gamma, maxInput)  GammaCorrection(LevelsControlInputRange(color, minInput, maxInput), gamma) \n \n #define LevelsControlOutputRange(color, minOutput, maxOutput)  mix(vec3(minOutput), vec3(maxOutput), color) \n \n #define LevelsControl(color, minInput, gamma, maxInput, minOutput, maxOutput)  LevelsControlOutputRange(LevelsControlInput(color, minInput, gamma, maxInput), minOutput, maxOutput) \n \n vec3 ContrastSaturationBrightness(vec3 color, float brt, float sat, float con) \n { \n    const float AvgLumR = 0.5; \n \n    const float AvgLumG = 0.5; \n \n    const float AvgLumB = 0.5; \n \n    const vec3 LumCoeff = vec3(0.2125, 0.7154, 0.0721); \n \n    vec3 AvgLumin = vec3(AvgLumR, AvgLumG, AvgLumB); \n \n    vec3 brtColor = color * brt; \n \n    vec3 intensity = vec3(dot(brtColor, LumCoeff)); \n \n    vec3 satColor = mix(intensity, brtColor, sat); \n \n    vec3 conColor = mix(AvgLumin, satColor, con); \n \n    return conColor; \n } \n void main()\n {\n     lowp vec3 color = texture2D(inputImageTexture, textureCoordinate).xyz;\n     \n     lowp vec3 levelColor = LevelsControl(color,0.0736, 0.87, 0.8549, 0.0, 1.0); \n     \n     color = ContrastSaturationBrightness(levelColor, 1.0, 0.3, 1.0); \n     gl_FragColor = vec4(color, 1.0);\n }";

    public GPUImageSceneLowSaturationFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, SCENE_FRAGMENT_SHADER);
    }
}
