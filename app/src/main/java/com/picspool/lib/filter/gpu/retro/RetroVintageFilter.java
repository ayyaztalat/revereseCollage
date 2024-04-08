package com.picspool.lib.filter.gpu.retro;

import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class RetroVintageFilter extends GPUImageFilter {
    private static final String FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n\n uniform sampler2D inputImageTexture;\n \n uniform lowp float mixturePercent;\n void main()\n {\n     mediump vec4 base = texture2D(inputImageTexture, textureCoordinate);\n     \n     lowp vec4 textureColor3 = base * (base.a * (base / base.a) + (2.0 * base * (1.0 - (base / base.a)))) + base * (1.0 - base.a) + base * (1.0 - base.a);\n     base =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), base.a);\n     mediump vec4 overlay = vec4(0.0, 15.0/255.0, 106.0/255.0, 1.0);\n     textureColor3 = vec4((overlay.rgb * base.a + base.rgb * overlay.a - 2.0 * overlay.rgb * base.rgb) + overlay.rgb * (1.0 - base.a) + base.rgb * (1.0 - overlay.a), base.a);\n     gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), base.a);\n }";

    public RetroVintageFilter() {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, FRAGMENT_SHADER);
    }
}
