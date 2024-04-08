package com.picspool.lib.filter.gpu.sweet;

import com.picspool.lib.filter.gpu.normal.GPUImageToneCurveFilter;

/* loaded from: classes3.dex */
public class GPUSweetRustyTintFilter extends GPUImageToneCurveFilter {
    private static final String FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform sampler2D toneCurveTexture;\n \n uniform lowp float mixturePercent;\n const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\nconst highp vec4 kRGBToYPrime = vec4 (0.299, 0.587, 0.114, 0.0);\nconst highp vec4 kRGBToI = vec4 (0.595716, -0.274453, -0.321263, 0.0);\nconst highp vec4 kRGBToQ = vec4 (0.211456, -0.522591, 0.31135, 0.0);\n\nconst highp vec4 kYIQToR = vec4 (1.0, 0.9563, 0.6210, 0.0);\nconst highp vec4 kYIQToG = vec4 (1.0, -0.2721, -0.6474, 0.0);\nconst highp vec4 kYIQToB = vec4 (1.0, -1.1070, 1.7046, 0.0);\n highp float lum(lowp vec3 c) {\n     return dot(c, vec3(0.3, 0.59, 0.11));\n }\n \n lowp vec3 clipcolor(lowp vec3 c) {\n     highp float l = lum(c);\n     lowp float n = min(min(c.r, c.g), c.b);\n     lowp float x = max(max(c.r, c.g), c.b);\n     \n     if (n < 0.0) {\n         c.r = l + ((c.r - l) * l) / (l - n);\n         c.g = l + ((c.g - l) * l) / (l - n);\n         c.b = l + ((c.b - l) * l) / (l - n);\n     }\n     if (x > 1.0) {\n         c.r = l + ((c.r - l) * (1.0 - l)) / (x - l);\n         c.g = l + ((c.g - l) * (1.0 - l)) / (x - l);\n         c.b = l + ((c.b - l) * (1.0 - l)) / (x - l);\n     }\n     \n     return c;\n }\n\n lowp vec3 setlum(lowp vec3 c, highp float l) {\n     highp float d = l - lum(c);\n     c = c + vec3(d);\n     return clipcolor(c);\n }\n \n void main()\n {\n   highp vec4 baseColor = texture2D(inputImageTexture, textureCoordinate);\n   highp vec4 color = baseColor;\n    highp float YPrime = dot (color, kRGBToYPrime);\n    highp float I = dot (color, kRGBToI);\n    highp float Q = dot (color, kRGBToQ);\n\n    highp float hue = atan (Q, I);\n    highp float chroma = sqrt (I * I + Q * Q);\n\n    hue += (0.26179939);\n\n    Q = chroma * sin (hue);\n    I = chroma * cos (hue);\n\n    highp vec4 yIQ = vec4 (YPrime, I, Q, 0.0);\n    color.r = dot (yIQ, kYIQToR);\n    color.g = dot (yIQ, kYIQToG);\n    color.b = dot (yIQ, kYIQToB);\n\n    lowp float luminance = dot(color.rgb, luminanceWeighting);\n    lowp vec3 greyScaleColor = vec3(luminance);\n    \n    lowp vec4 textureColor3  = vec4(mix(greyScaleColor, color.rgb, 1.04), color.w);\n    lowp vec4 layer1 =vec4(mix(color.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), color.a);\n    lowp vec4 layer2 = vec4(layer1.rgb * pow(2.0, 0.11), layer1.w);\n    textureColor3 = vec4(baseColor.rgb * (1.0 - layer2.a) + setlum(layer2.rgb, lum(baseColor.rgb)) * layer2.a, baseColor.a);\n    lowp vec4 c2 =vec4(mix(baseColor.rgb, textureColor3.rgb, textureColor3.a*0.6*mixturePercent), baseColor.a);\n    lowp vec4 c1 = vec4( mix(vec3(22.0/255.0), vec3(233.0/255.0), pow(min(max(c2.rgb -vec3(0.0), vec3(0.0)) / (vec3(1.0) - vec3(0.0)  ), vec3(1.0)), 1.0 /vec3(1.23))) , c2.a);\n    c1 = vec4( mix(vec3(13.0/255.0, 0.0, 0.0), vec3(1.0, 246.0/255.0, 218.0/255.0), pow(min(max(c1.rgb -vec3(0.0), vec3(0.0)) / (vec3(1.0) - vec3(0.0)  ), vec3(1.0)), 1.0 /vec3(1.0, 1.0, 1.11))) , c1.a);\n     lowp vec4 outputColor;\n     \n     outputColor.r = c1.r + c2.r * c2.a * (1.0 - c1.a);\n\n     outputColor.g = c1.g + c2.g * c2.a * (1.0 - c1.a);\n     \n     outputColor.b = c1.b + c2.b * c2.a * (1.0 - c1.a);\n     \n     outputColor.a = c1.a + c2.a * (1.0 - c1.a);\n     \n     textureColor3 = outputColor;\n     lowp vec4 layer3 =vec4(mix(c2.rgb, textureColor3.rgb, textureColor3.a*0.7*mixturePercent), c2.a);\n     lowp float redCurveValue = texture2D(toneCurveTexture, vec2(layer3.r, 0.0)).r;\n     lowp float greenCurveValue = texture2D(toneCurveTexture, vec2(layer3.g, 0.0)).g;\n     lowp float blueCurveValue = texture2D(toneCurveTexture, vec2(layer3.b, 0.0)).b;\n\n     lowp vec4 textureColor2 = vec4(redCurveValue,greenCurveValue,blueCurveValue,layer3.a);\n     c2 = vec4(mix(layer3.rgb, textureColor2.rgb, textureColor2.a*0.6*mixturePercent), layer3.a);\n     c1 = vec4(((c2.rgb - vec3(0.5)) * 1.11 + vec3(0.5)), c2.w);\n     outputColor.r = c1.r + c2.r * c2.a * (1.0 - c1.a);\n\n     outputColor.g = c1.g + c2.g * c2.a * (1.0 - c1.a);\n     \n     outputColor.b = c1.b + c2.b * c2.a * (1.0 - c1.a);\n     \n     outputColor.a = c1.a + c2.a * (1.0 - c1.a);\n     \n     textureColor3 = outputColor;\n     gl_FragColor =vec4(mix(c2.rgb, textureColor3.rgb, textureColor3.a*0.5*mixturePercent), c2.a);\n }";

    public GPUSweetRustyTintFilter() {
        super(FRAGMENT_SHADER);
    }
}
