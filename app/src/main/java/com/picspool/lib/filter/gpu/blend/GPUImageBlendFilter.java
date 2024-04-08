package com.picspool.lib.filter.gpu.blend;

import com.picspool.lib.filter.gpu.father.GPUImageThreeInputFilter;

/* loaded from: classes3.dex */
public class GPUImageBlendFilter extends GPUImageThreeInputFilter {
    public static final String BLEND_FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n varying highp vec2 textureCoordinate2;\n varying highp vec2 textureCoordinate3;\n\n uniform sampler2D inputImageTexture;\n uniform sampler2D inputImageTexture2;\n uniform sampler2D inputImageTexture3;\n \n uniform lowp float mixturePercent;\n uniform lowp float blendType; uniform lowp float gradiented; void main()\n {\n   lowp vec4 base = texture2D(inputImageTexture, textureCoordinate);\n   lowp vec4 overlay = texture2D(inputImageTexture2, textureCoordinate2);\n\n   if(gradiented == 1.0){\n   lowp vec4 gradient = texture2D(inputImageTexture3, textureCoordinate);\n   lowp float a = gradient.a + base.a * (1.0 - gradient.a);\n   lowp float alphaDivisor = a + step(a, 0.0);\n   overlay.r = (overlay.r * gradient.a + base.r * base.a * (1.0 - gradient.a))/alphaDivisor;\n   overlay.g = (overlay.g * gradient.a + base.g * base.a * (1.0 - gradient.a))/alphaDivisor;\n   overlay.b = (overlay.b * gradient.a + base.b * base.a * (1.0 - gradient.a))/alphaDivisor;\n   overlay.a = a;\n    }\n\n   mediump float r;\n   if (overlay.r * base.a + base.r * overlay.a >= overlay.a * base.a) {\n     r = overlay.a * base.a + overlay.r * (1.0 - base.a) + base.r * (1.0 - overlay.a);\n   } else {\n     r = overlay.r + base.r;\n   }\n\n   mediump float g;\n   if (overlay.g * base.a + base.g * overlay.a >= overlay.a * base.a) {\n     g = overlay.a * base.a + overlay.g * (1.0 - base.a) + base.g * (1.0 - overlay.a);\n   } else {\n     g = overlay.g + base.g;\n   }\n\n   mediump float b;\n   if (overlay.b * base.a + base.b * overlay.a >= overlay.a * base.a) {\n     b = overlay.a * base.a + overlay.b * (1.0 - base.a) + base.b * (1.0 - overlay.a);\n   } else {\n     b = overlay.b + base.b;\n   }\n\n   mediump float a = overlay.a + base.a - overlay.a * base.a;\n   \n     lowp vec4 textureColor3 = vec4(r, g, b, a);\n     gl_FragColor =vec4(mix(base.rgb, textureColor3.rgb, textureColor3.a*mixturePercent), base.a);\n }";

    public GPUImageBlendFilter() {
        super(BLEND_FRAGMENT_SHADER);
    }
}
