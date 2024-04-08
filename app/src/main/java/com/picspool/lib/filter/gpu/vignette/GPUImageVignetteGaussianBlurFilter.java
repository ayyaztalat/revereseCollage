package com.picspool.lib.filter.gpu.vignette;

import android.graphics.PointF;
import android.opengl.GLES20;

/* loaded from: classes3.dex */
public class GPUImageVignetteGaussianBlurFilter extends GPUImageVignetteFilter {
    public static final String VIGNETTING_FRAGMENT_SHADER = " uniform sampler2D inputImageTexture;\n varying highp vec2 textureCoordinate;\n \n uniform lowp vec2 vignetteCenter;\n uniform highp float vignetteStart;\n uniform highp float vignetteEnd;\n uniform highp float blurSizeStart;\n uniform highp float blurSizeEnd;\n \n void main()\n {\n     lowp float d = distance(textureCoordinate, vec2(vignetteCenter.x, vignetteCenter.y));\n     lowp float percent = smoothstep(vignetteStart, vignetteEnd, d);\n     lowp float blurSize = (blurSizeEnd - blurSizeStart) * percent + blurSizeStart;    mediump vec4 sum = vec4(0.0);\n   sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y)) * 0.147761;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y)) * 0.118318;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y)) * 0.118318;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y + blurSize)) * 0.118318;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x, textureCoordinate.y - blurSize)) * 0.118318;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y + blurSize)) * 0.0947416;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x + blurSize, textureCoordinate.y - blurSize)) * 0.0947416;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y + blurSize)) * 0.0947416;    sum += texture2D(inputImageTexture, vec2(textureCoordinate.x - blurSize, textureCoordinate.y - blurSize)) * 0.0947416;     gl_FragColor = sum;\n }";
    private float mBlurSizeEnd;
    private int mBlurSizeEndLocation;
    private float mBlurSizeStart;
    private int mBlurSizeStartLocation;

    public GPUImageVignetteGaussianBlurFilter() {
        this(new PointF(), 0.3f, -0.3f, 0.3f, 0.75f);
    }

    public GPUImageVignetteGaussianBlurFilter(PointF pointF, float f, float f2, float f3, float f4) {
        super(VIGNETTING_FRAGMENT_SHADER, pointF, f3, f4);
        this.mBlurSizeStart = f;
        this.mBlurSizeEnd = f2;
    }

    @Override // com.picspool.lib.filter.gpu.vignette.GPUImageVignetteFilter, com.picspool.lib.filter.gpu.father.GPUImageFilter
    public void onInit() {
        super.onInit();
        this.mBlurSizeStartLocation = GLES20.glGetUniformLocation(getProgram(), "blurSizeStart");
        this.mBlurSizeEndLocation = GLES20.glGetUniformLocation(getProgram(), "blurSizeEnd");
        setBlurSizeStart(this.mBlurSizeStart);
        setBlurSizeEnd(this.mBlurSizeEnd);
    }

    public void setBlurSizeStart(float f) {
        this.mBlurSizeStart = f;
        setFloat(this.mBlurSizeStartLocation, f);
    }

    public void setBlurSizeEnd(float f) {
        this.mBlurSizeEnd = f;
        setFloat(this.mBlurSizeEndLocation, f);
    }
}
