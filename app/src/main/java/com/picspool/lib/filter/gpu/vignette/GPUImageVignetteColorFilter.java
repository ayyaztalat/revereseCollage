package com.picspool.lib.filter.gpu.vignette;

import android.graphics.PointF;
import android.opengl.GLES20;

/* loaded from: classes3.dex */
public class GPUImageVignetteColorFilter extends GPUImageVignetteFilter {
    public static final String VIGNETTING_FRAGMENT_SHADER = " uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\n \n uniform lowp vec2 vignetteCenter;\n uniform lowp vec3 vignetteColor;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n \n void main()\n {\n     /*\n     lowp vec3 rgb = texture2D(inputImageTexture, textureCoordinate).rgb;\n     lowp float d = distance(textureCoordinate, vec2(0.5,0.5));\n     rgb *= (1.0 - smoothstep(vignetteStart, vignetteEnd, d));\n     gl_FragColor = vec4(vec3(rgb),1.0);\n      */\n     \n     lowp vec3 rgb = texture2D(inputImageTexture, textureCoordinate).rgb;\n     lowp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     lowp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     gl_FragColor = vec4(mix(rgb.x, vignetteColor.x, percent), mix(rgb.y, vignetteColor.y, percent), mix(rgb.z, vignetteColor.z, percent), 1.0);\n }";
    private float[] mVignetteColor;
    private int mVignetteColorLocation;

    public GPUImageVignetteColorFilter() {
        this(new float[]{0.0f, 0.0f, 0.0f}, new PointF(), 0.3f, 0.75f);
    }

    public GPUImageVignetteColorFilter(float[] fArr, PointF pointF, float f, float f2) {
        super(VIGNETTING_FRAGMENT_SHADER, pointF, f, f2);
        this.mVignetteColor = fArr;
    }

    @Override // com.picspool.lib.filter.gpu.vignette.GPUImageVignetteFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mVignetteColorLocation = GLES20.glGetUniformLocation(getProgram(), "vignetteColor");
        setVignetteColor(this.mVignetteColor);
    }

    @Override // com.picspool.lib.filter.gpu.vignette.GPUImageVignetteFilter
    public void setVignetteColor(float[] fArr) {
        this.mVignetteColor = fArr;
        setFloatVec3(this.mVignetteColorLocation, fArr);
    }
}
