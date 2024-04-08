package com.picspool.lib.filter.gpu.adjust;

import android.opengl.GLES20;
import com.picspool.lib.filter.gpu.father.GPUImageFilter;

/* loaded from: classes3.dex */
public class GPUImageVibranceFilter extends GPUImageFilter {
    public static final String SATURATION_FRAGMENT_SHADER = " varying highp vec2 textureCoordinate;\n \n uniform sampler2D inputImageTexture;\n uniform mediump float saturation;\n \n void main()\n {\n    mediump vec4 t1 = texture2D(inputImageTexture, textureCoordinate);\n    lowp float minRG = min(t1.r,t1.g);    lowp float minGB = min(t1.g,t1.b);    lowp float minRGB = min(minRG,minGB);    mediump float sa = saturation;    if( minRGB < 0.2) { if(saturation < 1.0) {sa = 1.0 ; }else {sa = 1.0;} }    mediump vec3 bw = vec3(dot(t1.rgb, vec3(0.299,0.587,0.114)));\n    mediump vec3 c = mix(bw, t1.rgb, clamp(sa, 0.0, 1.05));\n    c=mix(c, bw, -distance(c, bw)*2.0*clamp(sa-1.0, 0.0, 1.0));\n    gl_FragColor = vec4(c,t1.a);\n     \n }";
    private float mSaturation;
    private int mSaturationLocation;

    public GPUImageVibranceFilter() {
        this(1.0f);
    }

    public GPUImageVibranceFilter(float f) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, SATURATION_FRAGMENT_SHADER);
        this.mSaturation = f;
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mSaturationLocation = GLES20.glGetUniformLocation(getProgram(), "saturation");
    }

    @Override // com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInitialized() {
        super.onInitialized();
        setSaturation(this.mSaturation);
    }

    public void setSaturation(float f) {
        this.mSaturation = f;
        setFloat(this.mSaturationLocation, f);
    }
}
