package com.picspool.lib.filter.gpu.film;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class FilmNightFate3Filter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = "varying highp vec2 textureCoordinate;\nprecision highp float;\n\n uniform sampler2D inputImageTexture;\nconst highp vec3 W = vec3(0.2125, 0.7154, 0.0721);\n uniform lowp float mixturePercent;\n \n void main()\n {\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     float luminance = dot(textureColor.rgb, W);\n     mediump vec4 textureColor2 = vec4(vec3(luminance), textureColor.a);\n     mediump vec4 whiteColor = vec4(1.0);\n     lowp vec4 textureColor3 = whiteColor - ((whiteColor - textureColor2) * (whiteColor - textureColor));\n     gl_FragColor =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);\n }";

    public FilmNightFate3Filter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
