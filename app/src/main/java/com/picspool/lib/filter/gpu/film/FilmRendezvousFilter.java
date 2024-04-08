package com.picspool.lib.filter.gpu.film;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class FilmRendezvousFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n const mediump vec3 luminanceWeighting = vec3(0.2125, 0.7154, 0.0721);\n uniform lowp float mixturePercent;\n \n void main()\n {\n     mediump vec4 textureColor = texture2D(inputImageTexture, textureCoordinate);\n     \n     mediump vec4 base = vec4( mix(vec3(0.0), vec3(1.0), pow(min(max(textureColor.rgb -vec3(25.0/255.0), vec3(0.0)) / (vec3(1.0) - vec3(25.0/255.0)  ), vec3(1.0)), 1.0 /vec3(0.92))) , textureColor.a);\n     textureColor = base;\n     lowp float luminance = dot(textureColor.rgb, luminanceWeighting);\n     lowp vec3 greyScaleColor = vec3(luminance);\n     \n     lowp vec4 textureColor3  = vec4(mix(greyScaleColor, textureColor.rgb, 0.5), textureColor.w);\n     mediump vec4 overlay =vec4(mix(textureColor.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), textureColor.a);\n     \n     textureColor3 = base * (overlay.a * (base / base.a) + (2.0 * overlay * (1.0 - (base / base.a)))) + overlay * (1.0 - base.a) + base * (1.0 - overlay.a);\n     gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*0.75*mixturePercent), base.a);\n }\n";

    public FilmRendezvousFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
